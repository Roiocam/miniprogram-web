package com.fangdushuzi.web.to;

import lombok.Data;

import java.util.List;

/**
 * @author Andy Chen
 * @date 2020/5/14 下午12:58
 */
@Data
public class FileResult {
    private int errcode;
    private String errmsg;
    /**
     * 上传
     */
    private String url;
    private String token;
    private String authorization;
    private String file_id;
    private String cos_file_id;
    /**
     * 删除
     */
    private List<DeleteList> delete_list;
}
