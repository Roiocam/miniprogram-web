package com.fangdushuzi.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Andy Chen
 * @date 2020/5/13 下午10:39
 */
@Data
@AllArgsConstructor
public class Form {
    private String text;
    private String name;
    private String type;
    private String tips;
    private String value;
    public Form(String text,String name,String type,String tips){
        this.text = text;
        this.name = name;
        this.type = type;
        this.tips = tips;
    }
}
