package com.fangdushuzi.web.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Andy Chen
 * @date 2020/5/12 下午8:55
 */
@Data
public class Pager {
    @JsonProperty("Offset")
    private int Offset;
    @JsonProperty("Limit")
    private int Limit;
    @JsonProperty("Total")
    private int Total;
}
