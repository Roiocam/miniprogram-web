package com.fangdushuzi.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Andy Chen
 * @date 2020/5/13 下午3:22
 */
@Data
@AllArgsConstructor
public class Insert {
    private String msg;
    private List<String> idList;
}
