package com.fangdushuzi.web.to;

import lombok.Data;

import java.util.List;

/**
 * @author Andy Chen
 * @date 2020/5/12 下午8:52
 */
@Data
public class Result {
    private int errcode;
    private String errmsg;
    //查询
    private Pager pager;
    private List<String> data;
    //插入
    private List<String> id_list;
    //更新
    private int matched;
    private int modified;
    private String id;
    //删除
    private int deleted;
}
