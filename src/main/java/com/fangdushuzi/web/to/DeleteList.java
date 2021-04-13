package com.fangdushuzi.web.to;

import lombok.Data;

/**
 * @author Andy Chen
 * @date 2020/5/17 上午10:51
 */
@Data
public class DeleteList {
    private String fileid;
    private int status;
    private String errmsg;
}
