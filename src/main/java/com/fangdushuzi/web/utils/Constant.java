package com.fangdushuzi.web.utils;

/**
 * @author Andy Chen
 * @date 2020/5/13 上午5:10
 */
public interface Constant {
    /**
     * config
     */
    final String APPID = "小程序appid";
    final String APPSECRET = "小程序appSecret";
    /**
     * URL
     */
    final String HOST = "api.weixin.qq.com";
    final String SCHEME = "https";
    /**
     * SQL
     */
    final String QUERY_SQL = "db.collection('%s').orderBy('id','asc').limit(10).skip(%d).get()";
    final String UPDATE_SQL = "db.collection('%s').doc('%s').update({data:%s})";
    final String INSERT_SQL = "db.collection('%s').add({data:%s})";
    final String DELETE_SQL = "db.collection('%s').doc('%s').remove()";
    final String GET_SQL = "db.collection('%s').doc('%s').get()";

    final String DELIMITER_TO = ":";
    final String DELIMITER_COLON = "@";
    final String DELIMITER_UNDERLINE = "_";
    final String PNG_SUFFIX = ".png";
}
