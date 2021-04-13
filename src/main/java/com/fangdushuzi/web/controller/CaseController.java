package com.fangdushuzi.web.controller;

import com.fangdushuzi.web.entity.Case;
import com.fangdushuzi.web.entity.Function;
import com.fangdushuzi.web.entity.Style;
import com.fangdushuzi.web.enums.ImagePath;
import com.fangdushuzi.web.ro.CaseRequest;
import com.fangdushuzi.web.ro.DeleteRequest;
import com.fangdushuzi.web.service.CaseService;
import com.fangdushuzi.web.service.FunctionService;
import com.fangdushuzi.web.service.StyleService;
import com.fangdushuzi.web.to.UploadResult;
import com.fangdushuzi.web.utils.CloudFileUtils;
import com.fangdushuzi.web.utils.ThrowableUtil;
import com.fangdushuzi.web.vo.Form;
import com.fangdushuzi.web.vo.JsonResponse;
import com.fangdushuzi.web.vo.Response;
import com.fangdushuzi.web.vo.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Andy Chen
 * @date 2020/5/12 上午1:14
 */
@Controller
public class CaseController implements IController<CaseRequest>{
    @Autowired
    private CaseService service;
    @Autowired
    private FunctionService functionService;
    @Autowired
    private StyleService styleService;

    @RequestMapping(value = "/case")
    public String list(HttpServletRequest request,  Model model) {
        int page = 0;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        Response response = service.getList(page);
        List<Table> tables = Arrays.asList(
                new Table("#id", "width: 10px"),
                new Table("标题"),
                new Table("功能id"),
                new Table("风格id"),
                // new Table("标签id"),
                new Table("缩略图"),
                new Table("VR图列表"),
                new Table("操作", "width: 80px"));
        //page
        model.addAttribute("title", "方都数字科技 | 作品页作品列表");
        model.addAttribute("header", "作品列表");
        model.addAttribute("page", "作品页");
        model.addAttribute("pageDesc", "作品列表");
        //narbar
        model.addAttribute("menu", "case");
        model.addAttribute("active", "case");
        //table
        model.addAttribute("table", tables);
        model.addAttribute("response", response);
        return "pages/list";
    }

    @RequestMapping(value = "/case-form")
    public String form(HttpServletRequest request,  Model model) {
        List<Function> functions = (List<Function>) functionService.getList(0).getData();
        List<Style> styles = (List<Style>) styleService.getList(0).getData();
        List<Form> forms = Arrays.asList(
                new Form("ID", "id", "number", "填写作品id,只能是数字."),
                new Form("标题", "title", "text", "填写作品标题"),
                new Form("功能", "function", "select", "选择作品功能标签"),
                new Form("风格", "style", "select", "选择作品风格标签"),
                new Form("缩略图", "preview", "file", "上传VR图的缩略图"),
                new Form("VR图", "imageInput", "file", "上传完整的VR图，2:1")
        );
        //select
        model.addAttribute("styles", styles);
        model.addAttribute("functions", functions);
        model.addAttribute("href", "/case-update");
        String id = request.getParameter("rid");
        if (id != null) {
            List<Case> list = (List<Case>) service.get(id).getData();
            Case aCase = list.get(0);
            forms = Arrays.asList(
                    new Form("ID", "id", "number", "填写作品id,只能是数字.", String.valueOf(aCase.getId())),
                    new Form("标题", "title", "text", "填写作品标题", aCase.getTitle()),
                    new Form("功能", "function", "select", "选择功能标签", String.valueOf(aCase.getFunction())),
                    new Form("风格", "style", "select", "选择风格标签", String.valueOf(aCase.getStyle())),
                    new Form("缩略图", "preview", "file", "上传VR图的缩略图", aCase.getPreview()),
                    new Form("VR图", "imageInput", "file", "上传完整的VR图，2:1")
            );
            model.addAttribute("href", "/case-update?rid=" + id);
        }

        //page
        model.addAttribute("title", "方都数字科技 | 作品页作品表单");
        model.addAttribute("header", "作品表单");
        model.addAttribute("page", "作品页");
        model.addAttribute("pageDesc", "作品表单");
        //narbar
        model.addAttribute("menu", "case");
        model.addAttribute("active", "case-form");
        //table
        model.addAttribute("forms", forms);
        return "pages/form";
    }


    @PostMapping(value = "/case-update")
    @ResponseBody
    public ResponseEntity update(HttpServletRequest request, @Validated CaseRequest caseRequest, BindingResult bindingResult) {
        ThrowableUtil.checkRequestArgument(bindingResult);
        Case obj = null;
        if (request.getParameter("rid") != null) {
            String id = request.getParameter("rid");
            List<Case> list = (List<Case>) service.get(id).getData();
            obj = list.get(0);
        }
        //缩略图
        if (request instanceof MultipartHttpServletRequest && ((MultipartHttpServletRequest) request).getFile("preview") != null
        &&  ((MultipartHttpServletRequest) request).getFile("preview").getSize() != 0) {
            if (obj != null)  CloudFileUtils.deleteImage(obj.getPreview());
            UploadResult result = CloudFileUtils.uploadImage(ImagePath.CASE, request, "preview");
            if (result.getCode() == 0) {
                caseRequest.setPreviewUrl(result.getImageMap().get("preview"));
            }
        }
        //VR图
        if (request instanceof MultipartHttpServletRequest && ((MultipartHttpServletRequest) request).getFile("px") != null) {
            if (obj != null) CloudFileUtils.deleteImage(obj.getVr().toArray(new String[0]));
            UploadResult vrResult = CloudFileUtils.uploadImage(ImagePath.VR, request, "px", "nx", "py", "ny", "pz", "nz");
            if (vrResult.getCode() == 0) {
                List<String> vrList = new LinkedList<>();
                vrList.add(vrResult.getImageMap().get("px"));
                vrList.add(vrResult.getImageMap().get("nx"));
                vrList.add(vrResult.getImageMap().get("py"));
                vrList.add(vrResult.getImageMap().get("ny"));
                vrList.add(vrResult.getImageMap().get("pz"));
                vrList.add(vrResult.getImageMap().get("nz"));
                caseRequest.setVrList(vrList);
            }
        }
        if (obj != null) service.update(caseRequest.parse(obj));
        else service.add(caseRequest.parse());
        return ResponseEntity.ok(JsonResponse.getInstance());
    }

    @PostMapping(value = "/case-delete")
    @ResponseBody
    public ResponseEntity delete(HttpServletRequest request, @Validated DeleteRequest deleteRequest, BindingResult bindingResult) {
        ThrowableUtil.checkRequestArgument(bindingResult);
        List<Case> list = (List<Case>) service.get(deleteRequest.getRid()).getData();
        Case obj = list.get(0);
        String[] vrList = new String[obj.getVr().size() + 1];
        List<String> vr = obj.getVr();
        vr.add(obj.getPreview());
        CloudFileUtils.deleteImage(vr.toArray(new String[0]));
        Response delete = service.delete(deleteRequest.getRid());
        return ResponseEntity.ok(JsonResponse.getInstance());
    }
}
