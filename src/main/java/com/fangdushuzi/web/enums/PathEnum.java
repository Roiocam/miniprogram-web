package com.fangdushuzi.web.enums;

/**
 * @author Andy Chen
 * @date 2020/5/12 下午6:09
 */
public enum PathEnum {
    /**
     * QUERY 查数据库
     * INSERT 插入
     * UPDATE 更新
     * DELETE 删除
     * COUNT 总数
     * TOKEN 获取token
     * UPLOAD_URL
     */
    QUERY("/tcb/databasequery"),
    INSERT("/tcb/databaseadd"),
    UPDATE("/tcb/databaseupdate"),
    DELETE("/tcb/databasedelete"),
    COUNT("/tcb/databasecount"),
    TOKEN("/cgi-bin/token"),
    UPLOAD_URL("/tcb/uploadfile"),
    DELETE_URL("/tcb/batchdeletefile");

    private String path;

    /**
     *
     * @param path
     */
    private PathEnum(String path){
        this.path=path;
    }

    public String getPath() {
        return path;
    }
}
