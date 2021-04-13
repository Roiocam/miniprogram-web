package com.fangdushuzi.web.to;

import lombok.Data;

import java.util.Map;

/**
 * @author Andy Chen
 * @date 2020/5/15 上午7:36
 */
@Data
public class UploadResult {
    private int code;
    private Map<String,String> imageMap;
    private String msg;

    public static UploadResult getInstance(Map<String,String> map){
        UploadResult result = new UploadResult();
        result.setCode(0);
        result.setImageMap(map);
        result.setMsg("成功");
        return result;
    }
    public static UploadResult getInstance(String msg){
        UploadResult result = new UploadResult();
        result.setCode(-1);
        result.setMsg(msg);
        return result;
    }
}
