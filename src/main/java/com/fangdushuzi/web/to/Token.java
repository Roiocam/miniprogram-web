package com.fangdushuzi.web.to;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author Andy Chen
 * @date 2020/5/12 下午8:26
 */
@Data
public class Token {
    private String access_token;
    private int expires_in;
    private int errcode;
    private String errmsg;
    private long createTime;
    public Token(){
        this.createTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    }
}
