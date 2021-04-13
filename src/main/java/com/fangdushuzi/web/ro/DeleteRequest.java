package com.fangdushuzi.web.ro;

import com.fangdushuzi.web.utils.NotBlank;
import lombok.Data;

/**
 * @author Andy Chen
 * @date 2020/5/16 上午3:51
 */
@Data
public class DeleteRequest {
    @NotBlank
    private String rid;
}
