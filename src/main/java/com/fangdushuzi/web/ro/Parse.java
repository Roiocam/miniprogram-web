package com.fangdushuzi.web.ro;

/**
 * @author Andy Chen
 * @date 2020/5/15 上午8:49
 */
public interface Parse<T> {
    T parse(T obj);
    T parse();
}
