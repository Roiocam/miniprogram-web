const canvas = document.createElement('canvas');
const ctx = canvas.getContext('2d');
let finished = 0;
let workers = []
const facePositions = {
    pz: {x: 1, y: 1},
    nz: {x: 3, y: 1},
    px: {x: 2, y: 1},
    nx: {x: 0, y: 1},
    py: {x: 1, y: 0},
    ny: {x: 1, y: 2}
};

function loadImage() {
    const file = dom.imageInput.files[0];
    if (!file) {
        return;
    }
    const img = new Image();
    img.src = URL.createObjectURL(file);

    img.addEventListener('load', () => {
        const {width, height} = img;
        canvas.width = width;
        canvas.height = height;
        ctx.drawImage(img, 0, 0);
        const data = ctx.getImageData(0, 0, width, height);
        processImage(data);
    });

}

class Input {
    constructor(id, onChange) {
        this.input = document.getElementById(id);
        if (this.input) {
            this.input.addEventListener('change', onChange);
            this.valueAttrib = this.input.type === 'checkbox' ? 'checked' : 'value';
        }
    }

    get value() {
        return this.input[this.valueAttrib];
    }
}

class RadioInput {
    constructor(name, onChange) {
        this.inputs = document.querySelectorAll(`input[name=${name}]`);
        for (let input of this.inputs) {
            input.addEventListener('change', onChange);
        }
    }

    get value() {
        for (let input of this.inputs) {
            if (input.checked) {
                return input.value;
            }
        }
    }
}

const settings = {
    cubeRotation: new Input('cubeRotation', loadImage),
    interpolation: new RadioInput('interpolation', loadImage),
    format: new RadioInput('format', loadImage),
};


class CubeFace {
    constructor(faceName) {
        this.faceName = faceName;

        this.anchor = document.createElement('a');
        this.anchor.style.position = 'absolute';
        this.anchor.title = faceName;

        this.img = document.createElement('img');
        this.img.style.filter = 'blur(4px)';

        this.anchor.appendChild(this.img);
    }

    setPreview(url, x, y) {
        this.img.src = url;
        this.anchor.style.left = `${x}px`;
        this.anchor.style.top = `${y}px`;
    }

    setDownload(url, fileExtension) {
        this.anchor.href = url;
        this.anchor.download = `${this.faceName}.${fileExtension}`;
        this.img.style.filter = '';
    }
}

function removeChildren(node) {
    while (node.firstChild) {
        node.removeChild(node.firstChild);
    }
}

const mimeType = {
    'jpg': 'image/jpeg',
    'png': 'image/png'
};

function append(imgData, extension, faceName) {
    canvas.width = imgData.width;
    canvas.height = imgData.height;
    ctx.putImageData(imgData, 0, 0);
    canvas.toBlob(blob => {
        globalFormData.append(faceName, blob);
    }, mimeType[extension], 1);
}

function getDataURL(imgData, extension) {
    canvas.width = imgData.width;
    canvas.height = imgData.height;
    ctx.putImageData(imgData, 0, 0);
    return new Promise(resolve => {
        canvas.toBlob(blob => {
            return resolve(URL.createObjectURL(blob))
        }, mimeType[extension], 1);
    });
}

const dom = {
    imageInput: document.getElementById('imageInput'),
    faces: document.getElementById('faces'),
    generating: document.getElementById('generating')
};

dom.imageInput.addEventListener('change', loadImage);


function processImage(data) {
    removeChildren(dom.faces);
    let btn = document.getElementById('btn');
    btn.disabled = true

    dom.generating.style.visibility = 'visible';
    if (workers !== undefined) {
        for (let worker of workers) {
            worker.terminate();
        }
    }
    for (let [faceName, position] of Object.entries(facePositions)) {
        renderFace(data, faceName, position);
    }
}


function renderFace(data, faceName, position) {
    const face = new CubeFace(faceName);
    dom.faces.appendChild(face.anchor);
    const options = {
        data: data,
        face: faceName,
        rotation: Math.PI * settings.cubeRotation.value / 180,
        interpolation: settings.interpolation.value,
    };
    const worker = new Worker('/static/plugins/skybox/convert.js');
    const setDownload = ({data: obj}) => {
        var imageData = obj.data
        const extension = settings.format.value;
        append(imageData, extension, obj.face)
        getDataURL(imageData, extension)
            .then(url => {
                face.setDownload(url, extension)
            });
        finished++;

        if (finished === 6) {
            let btn = document.getElementById('btn');
            btn.disabled = false
            dom.generating.style.visibility = 'hidden';
            finished = 0;
            workers = [];
        }
    };

    const setPreview = ({data: obj}) => {
        var imageData = obj.data
        const x = imageData.width * position.x;
        const y = imageData.height * position.y;

        getDataURL(imageData, 'jpg')
            .then(url => face.setPreview(url, x, y));

        worker.onmessage = setDownload;
        worker.postMessage(options);
    };
    worker.onmessage = setPreview;
    worker.postMessage(Object.assign({}, options, {
        maxWidth: 200,
        interpolation: 'linear',
    }));

    workers.push(worker);
}
