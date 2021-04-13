package com.fangdushuzi.web.service;

import com.fangdushuzi.web.vo.Response;

/**
 * @author Andy Chen
 * @date 2020/5/12 上午6:56
 */
public interface IService<T> {
    /**
     * 获取集合
     * @param page
     * @return
     */
    Response getList(int page);
    /**
     * 获取单个
     * @param id
     * @return
     */
    Response get(String id);


    /**
     * 添加
     * @param obj
     */
    Response add(T obj);

    /**
     * 更新
     * @param obj
     */
    Response update(T obj);

    /**
     * 删除
     * @param id
     * @return
     */
    Response delete(String id);
}
