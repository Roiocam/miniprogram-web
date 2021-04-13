package com.fangdushuzi.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Andy Chen
 * @date 2020/5/13 下午3:22
 */
@Data
@AllArgsConstructor
public class Update {
    private String msg;
    private int matched;
    private int modified;
    private String id;
}
