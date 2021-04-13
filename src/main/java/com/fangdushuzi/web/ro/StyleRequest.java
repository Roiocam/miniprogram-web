package com.fangdushuzi.web.ro;

import com.fangdushuzi.web.entity.Style;
import com.fangdushuzi.web.utils.NotBlank;
import lombok.Data;

/**
 * @author Andy Chen
 * @date 2020/5/14 上午10:31
 */
@Data
public class StyleRequest implements Parse<Style>{
    @NotBlank
    private String id;
    @NotBlank
    private String style;


    @Override
    public Style parse(Style obj) {
        obj.setStyle(style);
        obj.setId(Integer.parseInt(id));
        return obj;
    }

    @Override
    public Style parse() {
        Style style = new Style();
        style.setId(Integer.parseInt(id));
        style.setStyle(this.style);
        return style;
    }
}
