package com.fangdushuzi.web.controller;

import com.fangdushuzi.web.ro.DeleteRequest;
import com.fangdushuzi.web.service.IService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 通用接口
 * @author Andy Chen
 * @date 2020/5/21 下午3:51
 */
public interface IController<T> {
    /**
     * 查询分页查询列表
     * @param request
     * @param model
     * @return
     */
    String list(HttpServletRequest request, Model model);

    /**
     * 表单
     * @param request
     * @param model
     * @return
     */
    String form(HttpServletRequest request, Model model);

    /**
     * 更新
     * @param request
     * @param objReuqest
     * @param bindingResult
     * @return
     */
    ResponseEntity update(HttpServletRequest request, @Validated T objReuqest, BindingResult bindingResult);

    /**
     * 删除
     * @param request
     * @param deleteRequest
     * @param bindingResult
     * @return
     */
    ResponseEntity delete(HttpServletRequest request, @Validated DeleteRequest deleteRequest, BindingResult bindingResult);

    default Object getData(String rid, IService service){
        List<Object> list = (List<Object>) service.get(rid).getData();
        return list.get(0);
    }
}
