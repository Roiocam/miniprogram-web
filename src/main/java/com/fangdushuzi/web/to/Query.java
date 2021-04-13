package com.fangdushuzi.web.to;

import lombok.Data;

/**
 * @author Andy Chen
 * @date 2020/5/12 下午8:30
 */
@Data
public class Query {
    private String env = "fangdu-y00yc";
    private String query;

    public static Query getInstance(String sql){
        Query query = new Query();
        query.setQuery(sql);
        return query;
    }
}
