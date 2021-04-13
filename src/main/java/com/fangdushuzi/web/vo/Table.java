package com.fangdushuzi.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Andy Chen
 * @date 2020/5/13 下午7:55
 */
@Data
@AllArgsConstructor
public class Table {
    private String text;
    private String style;
    public Table(String text){
        this.text = text;
        this.style = "";
    }
}
