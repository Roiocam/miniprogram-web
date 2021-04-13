package com.fangdushuzi.web.vo;

import lombok.Data;

/**
 * @author Andy Chen
 * @date 2020/5/15 下午6:49
 */
@Data
public class JsonResponse {
    private int code;
    private String message;
    public static JsonResponse getInstance() {
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setCode(0);
        jsonResponse.setMessage("success");
        return jsonResponse;
    }

    public static JsonResponse getInstance(String msg) {
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setCode(-1);
        jsonResponse.setMessage(msg);
        return jsonResponse;
    }
}