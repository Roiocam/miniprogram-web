package com.fangdushuzi.web.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Andy Chen
 * @date 2020/5/13 上午4:46
 */
public class JSONUtils {
    private static ObjectMapper mapper = new ObjectMapper();
    public static String toJSONString(Object obj){
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static <T> T parseObject(String content, Class<T> valueType) {
        try {
            return mapper.readValue(content,valueType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
