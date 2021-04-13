package com.fangdushuzi.web.controller;

import com.fangdushuzi.web.entity.Swiper;
import com.fangdushuzi.web.enums.ImagePath;
import com.fangdushuzi.web.ro.DeleteRequest;
import com.fangdushuzi.web.ro.SwiperRequest;
import com.fangdushuzi.web.service.SwiperService;
import com.fangdushuzi.web.to.UploadResult;
import com.fangdushuzi.web.utils.CloudFileUtils;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andy Chen
 * @date 2020/5/12 上午1:14
 */
@Controller
public class SwiperController implements IController<SwiperRequest>{
    @Autowired
    private SwiperService service;

    @RequestMapping(value = "/swiper")
    public String list(HttpServletRequest request, Model model) {
        int page = 0;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        Response response = service.getList(page);
        List<Table> tables = Arrays.asList(
                new Table("#id", "width: 10px"),
                new Table("图片链接"),
                new Table("跳转链接"),
                new Table("跳转参数"),
                new Table("跳转", "width: 60px"),
                new Table("操作", "width: 80px"));
        //page
        model.addAttribute("title", "方都数字科技 | 主页轮播图列表");
        model.addAttribute("header", "轮播图列表");
        model.addAttribute("page", "主页");
        model.addAttribute("pageDesc", "轮播图");
        //narbar
        model.addAttribute("menu", "index");
        model.addAttribute("active", "swiper");
        //table
        model.addAttribute("table", tables);
        model.addAttribute("response", response);
        return "pages/list";
    }

    @GetMapping(value = "/swiper-form")
    public String form(HttpServletRequest request, Model model){
        List<Form> forms = Arrays.asList(
                new Form("ID", "id", "number", "填写轮播图id,只能是数字."),
                new Form("图片链接", "image", "file", "选择上传轮播图"),
                new Form("跳转链接", "href_url", "text", "轮播图跳转链接/pages/xxxx/index"),
                new Form("跳转参数", "href_parameter", "text", "填写轮播图跳转参数"),
                new Form("跳转", "is_href", "select", "是否跳转")
        );
        model.addAttribute("href", "/swiper-update");
        String id = request.getParameter("rid");
        if (id != null) {
            List<Swiper> list = (List<Swiper>) service.get(id).getData();
            Swiper swiper = list.get(0);
            forms = Arrays.asList(
                    new Form("ID", "id", "number", "填写轮播图id,只能是数字.", String.valueOf(swiper.getId())),
                    new Form("图片链接", "image", "file", "选择上传轮播图", swiper.getImage()),
                    new Form("跳转链接", "href_url", "text", "轮播图跳转链接/pages/xxxx/index", swiper.getHref_url()),
                    new Form("跳转参数", "href_parameter", "text", "填写轮播图跳转参数", swiper.getHref_parameter()),
                    new Form("跳转", "is_href", "select", "是否跳转", String.valueOf(swiper.getIs_href()))
            );
            model.addAttribute("href", "/swiper-update?rid=" + id);
        }
        //page
        model.addAttribute("title", "方都数字科技 | 主页轮播图列表");
        model.addAttribute("header", "轮播图表单");
        model.addAttribute("page", "主页");
        model.addAttribute("pageDesc", "轮播图");
        //narbar
        model.addAttribute("menu", "index");
        model.addAttribute("active", "swiper-form");
        //table
        model.addAttribute("forms", forms);
        return "pages/form";
    }

    @PostMapping(value = "/swiper-update")
    @ResponseBody
    public ResponseEntity update(HttpServletRequest request, @Validated SwiperRequest swiperRequest, BindingResult bindingResult) {
        ThrowableUtil.checkRequestArgument(bindingResult);
        Swiper obj = null;
        if (request.getParameter("rid") != null) {
            String id = request.getParameter("rid");
            List<Swiper> list = (List<Swiper>) service.get(id).getData();
            obj = list.get(0);
        }
        if (request instanceof MultipartHttpServletRequest && ((MultipartHttpServletRequest) request).getFile("image") != null
                &&  ((MultipartHttpServletRequest) request).getFile("image").getSize() != 0) {
            if (obj != null) CloudFileUtils.deleteImage(obj.getImage());
            UploadResult result = CloudFileUtils.uploadImage(ImagePath.SWIPER, request, "image");
            if (result.getCode() == 0) {
                swiperRequest.setImage_url(result.getImageMap().get("image"));
            }
        }
        if (obj != null) service.update(swiperRequest.parse(obj));
        else service.add(swiperRequest.parse());
        return ResponseEntity.ok(JsonResponse.getInstance());
    }

    @PostMapping(value = "/swiper-delete")
    @ResponseBody
    public ResponseEntity delete(HttpServletRequest request, @Validated DeleteRequest deleteRequest, BindingResult bindingResult) {
        ThrowableUtil.checkRequestArgument(bindingResult);
        List<Swiper> list = (List<Swiper>) service.get(deleteRequest.getRid()).getData();
        Swiper swiper = list.get(0);
        CloudFileUtils.deleteImage(swiper.getImage());
        Response delete = service.delete(deleteRequest.getRid());
        return ResponseEntity.ok(JsonResponse.getInstance());
    }

}
