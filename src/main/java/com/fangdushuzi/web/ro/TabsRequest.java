package com.fangdushuzi.web.ro;

import com.fangdushuzi.web.entity.Tabs;
import com.fangdushuzi.web.utils.NotBlank;
import lombok.Data;

/**
 * @author Andy Chen
 * @date 2020/5/14 上午8:46
 */
@Data
public class TabsRequest implements Parse<Tabs> {
    private int tabs;
    private String imageUrl;
    @NotBlank
    private String title;



    @Override
    public Tabs parse(Tabs obj) {
        obj.setTitle(title);
        if (obj.getTabs()!=tabs && tabs != 0) obj.setTabs(tabs);
        if (imageUrl != null) obj.setImage(imageUrl);
        return obj;
    }

    @Override
    public Tabs parse() {
        Tabs obj = new Tabs();
        obj.setImage(imageUrl);
        obj.setTabs(tabs);
        obj.setTitle(title);
        return obj;
    }
}

