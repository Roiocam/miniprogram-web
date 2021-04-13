package com.fangdushuzi.web.to;

import lombok.Data;

/**
 * @author Andy Chen
 * @date 2020/5/14 下午1:03
 */
@Data
public class FileUpload {
    private String env = "fangdu-y00yc";
    private String path;

    public static FileUpload getInstance(String path){
        FileUpload upload = new FileUpload();
        upload.setPath(path);
        return upload;
    }
}
