package com.fangdushuzi.web.enums;

/**
 * @author Andy Chen
 * @date 2020/5/14 下午7:42
 */
public enum ImagePath {
    SWIPER("swiper/"),
    SERVICE("service/"),
    CASE("case/"),
    TABS("tabs/"),
    VR("vr/");

    private String path;

    /**
     *
     * @param path
     */
    private ImagePath(String path){
        this.path=path;
    }

    public String getPath() {
        return path;
    }
}
