package com.fangdushuzi.web.enums;

/**
 * @author Andy Chen
 * @date 2020/5/13 上午5:06
 */

public enum DBName {
    /**

     数据库名称
     */
    ABOUT("about"),
    CASE("case"),
    FUNCTION("function"),
    SERVICE("service"),
    STYLE("style"),
    SWIPER("swiper"),
    TABS("tabs");
    private String name;
    private DBName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
