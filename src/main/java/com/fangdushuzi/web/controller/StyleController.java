package com.fangdushuzi.web.controller;

import com.fangdushuzi.web.entity.Style;
import com.fangdushuzi.web.ro.DeleteRequest;
import com.fangdushuzi.web.ro.StyleRequest;
import com.fangdushuzi.web.service.StyleService;
import com.fangdushuzi.web.utils.ThrowableUtil;
import com.fangdushuzi.web.vo.Form;
import com.fangdushuzi.web.vo.JsonResponse;
import com.fangdushuzi.web.vo.Response;
import com.fangdushuzi.web.vo.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andy Chen
 * @date 2020/5/12 上午1:14
 */
@Controller
public class StyleController implements IController<StyleRequest>{
    @Autowired
    private StyleService service;

    @RequestMapping(value = "/style")
    public String list(HttpServletRequest request,  Model model) {
        int page = 0;
        if(request.getParameter("page") != null){
            page = Integer.parseInt(request.getParameter("page"));
        }
        Response response = service.getList(page);
        List<Table> tables = Arrays.asList(
                new Table("#id","width: 10px"),
                new Table("风格标签标题"),
                new Table("操作", "width: 80px"));
        //page
        model.addAttribute("title","方都数字科技 | 作品页风格列表");
        model.addAttribute("header","风格标签列表");
        model.addAttribute("page","作品页");
        model.addAttribute("pageDesc","风格标签");
        //narbar
        model.addAttribute("menu","case");
        model.addAttribute("active","style");
        //table
        model.addAttribute("table",tables);
        model.addAttribute("response", response);
        return "pages/list";
    }
    @RequestMapping(value = "/style-form")
    public String form(HttpServletRequest request,  Model model){
        List<Form> forms = Arrays.asList(
                new Form("ID","id","number","填写风格标签id,只能是数字."),
                new Form("标题","style","text","填写风格标签名称")
        );
        model.addAttribute("href", "/style-update");
        String id = request.getParameter("rid");
        if (id !=null){
            List<Style> list = (List<Style>) service.get(id).getData();
            Style obj = list.get(0);
            forms = Arrays.asList(
                    new Form("ID","id","number","填写风格标签id,只能是数字.",String.valueOf(obj.getId())),
                    new Form("标题","style","text","填写风格标签名称",obj.getStyle())
            );
            model.addAttribute("href", "/style-update?rid="+id);
        }
        //page
        model.addAttribute("title","方都数字科技 | 作品页功能标签表单");
        model.addAttribute("header","功能标签表单");
        model.addAttribute("page","作品页");
        model.addAttribute("pageDesc","功能标签表单");
        //narbar
        model.addAttribute("menu","case");
        model.addAttribute("active","style-form");
        //table
        model.addAttribute("forms",forms);
        return "pages/form";
    }
    @PostMapping(value = "/style-update")
    @ResponseBody
    public ResponseEntity update(HttpServletRequest request, @Validated StyleRequest styleRequest, BindingResult bindingResult) {
        ThrowableUtil.checkRequestArgument(bindingResult);
        if (request.getParameter("rid") != null) {
            String id = request.getParameter("rid");
            List<Style> list = (List<Style>) service.get(id).getData();
            Style obj = list.get(0);
            Response response = service.update(styleRequest.parse(obj));
        } else {
            Response response = service.add(styleRequest.parse());
        }
        return ResponseEntity.ok(JsonResponse.getInstance());
    }
    @PostMapping(value = "/style-delete")
    @ResponseBody
    public ResponseEntity delete(HttpServletRequest request, @Validated DeleteRequest deleteRequest, BindingResult bindingResult) {
        ThrowableUtil.checkRequestArgument(bindingResult);
        Response delete = service.delete(deleteRequest.getRid());
        return ResponseEntity.ok(JsonResponse.getInstance());
    }
}
