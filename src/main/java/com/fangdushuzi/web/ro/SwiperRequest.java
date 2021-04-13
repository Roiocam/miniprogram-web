package com.fangdushuzi.web.ro;

import com.fangdushuzi.web.entity.Swiper;
import com.fangdushuzi.web.utils.NotBlank;
import lombok.Data;

/**
 * @author Andy Chen
 * @date 2020/5/12 上午6:47
 */
@Data
public class SwiperRequest implements Parse<Swiper> {
    private String _csrf;
    @NotBlank
    private String id;
    private String image_url;
    @NotBlank
    private String href_url;
    @NotBlank
    private String href_parameter;
    private int is_href;


    @Override
    public Swiper parse(Swiper swiper) {
        swiper.setIs_href(false);
        swiper.setId(Integer.parseInt(id));
        swiper.setHref_parameter(href_parameter);
        swiper.setHref_url(href_url);
        if (image_url != null) swiper.setImage(image_url);
        if (is_href == 1) swiper.setIs_href(true);
        return swiper;
    }

    @Override
    public Swiper parse() {
        Swiper swiper = new Swiper();
        swiper.setId(Integer.parseInt(id));
        swiper.setHref_url(href_url);
        swiper.setHref_parameter(href_parameter);
        swiper.setImage(image_url);
        swiper.setIs_href(false);
        if (is_href == 1) {
            swiper.setIs_href(true);
        }
        return swiper;
    }
}
