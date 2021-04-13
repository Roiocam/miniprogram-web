package com.fangdushuzi.web.entity;

import lombok.Data;

import java.util.List;

/**
 * @author Andy Chen
 * @date 2020/5/12 上午5:58
 */
@Data
public class Case {
    private String _id;
    private int id;
    private int function;
    private int style;
    private int tabs;
    private String title;
    private String preview;
    private List<String> vr;
}
