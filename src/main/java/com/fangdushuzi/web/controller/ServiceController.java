package com.fangdushuzi.web.controller;

import com.fangdushuzi.web.entity.Service;
import com.fangdushuzi.web.enums.ImagePath;
import com.fangdushuzi.web.ro.CaseRequest;
import com.fangdushuzi.web.ro.DeleteRequest;
import com.fangdushuzi.web.ro.ServiceRequest;
import com.fangdushuzi.web.service.ServiceService;
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
public class ServiceController implements IController<ServiceRequest>{
    @Autowired
    private ServiceService service;

    @RequestMapping(value = "/service")
    public String list(HttpServletRequest request, Model model) {
        int page = 0;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        Response response = service.getList(page);
        List<Table> tables = Arrays.asList(
                new Table("#id", "width: 10px"),
                new Table("服务范围标题"),
                new Table("图片链接"),
                new Table("服务范围描述"),
                new Table("操作", "width: 80px"));

        //page
        model.addAttribute("title", "方都数字科技 | 服务范围列表");
        model.addAttribute("header", "服务范围列表");
        model.addAttribute("page", "服务范围页");
        model.addAttribute("pageDesc", "服务范围列表");
        //narbar
        model.addAttribute("menu", "service");
        model.addAttribute("active", "service");
        //table
        model.addAttribute("table", tables);
        model.addAttribute("response", response);

        return "pages/list";
    }

    @RequestMapping(value = "/service-form")
    public String form(HttpServletRequest request,  Model model) {
        List<Form> forms = Arrays.asList(
                new Form("ID", "id", "number", "填写服务范围id,只能是数字."),
                new Form("图片链接", "image", "file", "选择上传服务范围的大图"),
                new Form("服务范围标题", "title", "text", "填写服务范围的标题"),
                new Form("服务范围描述", "desc", "text", "填写服务范围的描述文字")
        );
        model.addAttribute("href", "/service-update");
        String id = request.getParameter("rid");
        if (id != null) {
            List<Service> list = (List<Service>) service.get(id).getData();
            Service service = list.get(0);
            forms = Arrays.asList(
                    new Form("ID", "id", "number", "填写服务范围id,只能是数字.", String.valueOf(service.getId())),
                    new Form("图片链接", "image", "file", "选择上传服务范围的大图", service.getImage()),
                    new Form("服务范围标题", "title", "text", "填写服务范围的标题", service.getTitle()),
                    new Form("服务范围描述", "desc", "text", "填写服务范围的描述文字", service.getDesc())
            );
            model.addAttribute("href", "/service-update?rid=" + id);
        }
        //page
        model.addAttribute("title", "方都数字科技 | 服务范围列表");
        model.addAttribute("header", "服务范围列表");
        model.addAttribute("page", "服务范围页");
        model.addAttribute("pageDesc", "服务范围列表");
        //narbar
        model.addAttribute("menu", "service");
        model.addAttribute("active", "service-form");
        //table
        model.addAttribute("forms", forms);
        return "pages/form";
    }

    @PostMapping(value = "/service-update")
    @ResponseBody
    public ResponseEntity update(HttpServletRequest request, @Validated ServiceRequest serviceRequest, BindingResult bindingResult) {
        ThrowableUtil.checkRequestArgument(bindingResult);
        Service obj = null;
        if (request.getParameter("rid") != null) {
            String id = request.getParameter("rid");
            List<Service> list = (List<Service>) service.get(id).getData();
            obj = list.get(0);
        }
        if (request instanceof MultipartHttpServletRequest && ((MultipartHttpServletRequest) request).getFile("image") != null
                && ((MultipartHttpServletRequest) request).getFile("image").getSize() != 0) {
            if (obj != null) CloudFileUtils.deleteImage(obj.getImage());
            UploadResult result = CloudFileUtils.uploadImage(ImagePath.SERVICE, request, "image");
            if (result.getCode() == 0) {
                serviceRequest.setImageUrl(result.getImageMap().get("image"));
            }
        }
        if (obj != null) service.update(serviceRequest.parse(obj));
        else service.add(serviceRequest.parse());
        return ResponseEntity.ok(JsonResponse.getInstance());
    }

    @PostMapping(value = "/service-delete")
    @ResponseBody
    public ResponseEntity delete(HttpServletRequest request, @Validated DeleteRequest deleteRequest, BindingResult bindingResult) {
        ThrowableUtil.checkRequestArgument(bindingResult);
        List<Service> list = (List<Service>) service.get(deleteRequest.getRid()).getData();
        Service obj = list.get(0);
        CloudFileUtils.deleteImage(obj.getImage());
        Response delete = service.delete(deleteRequest.getRid());
        return ResponseEntity.ok(JsonResponse.getInstance());
    }

}
