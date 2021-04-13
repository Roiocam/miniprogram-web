package com.fangdushuzi.web.controller;

import com.fangdushuzi.web.entity.Tabs;
import com.fangdushuzi.web.enums.ImagePath;
import com.fangdushuzi.web.ro.CaseRequest;
import com.fangdushuzi.web.ro.DeleteRequest;
import com.fangdushuzi.web.ro.TabsRequest;
import com.fangdushuzi.web.service.TabsService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andy Chen
 * @date 2020/5/12 上午1:14
 */
@Controller
public class TabsController implements IController<TabsRequest>{
    @Autowired
    private TabsService service;
    @GetMapping(value = "/tabs")
    public String list(HttpServletRequest request, Model model) {
        int page = 0;
        if(request.getParameter("page") != null){
            page = Integer.parseInt(request.getParameter("page"));
        }        Response response = service.getList(page);
        Map<Integer,String> map = new HashMap<>(4);
        map.put(0,"家装效果图");
        map.put(1,"工装效果图");
        map.put(2,"建筑效果图");
        map.put(3,"CAD施工图");
        List<Table> tables = Arrays.asList(
                new Table("案例标题"),
                new Table("案例图片链接"),
                new Table("所属标签"),
                new Table("操作", "width: 80px"));
        //page
        model.addAttribute("title","方都数字科技 | 首页案例列表");
        model.addAttribute("header","首页案例列表");
        model.addAttribute("page","主页");
        model.addAttribute("pageDesc","首页案例列表");
        //narbar
        model.addAttribute("menu","index");
        model.addAttribute("active","tabs");
        //table
        model.addAttribute("table",tables);
        model.addAttribute("response", response);
        model.addAttribute("map",map);
        return "pages/list";
    }
    @RequestMapping(value = "/tabs-form")
    public String form(HttpServletRequest request, Model model){
        List<Form> forms = Arrays.asList(
                new Form("标签","tabs","select","选择案例标签"),
                new Form("图片链接","image","file","选择上传首页案例的展示图"),
                new Form("首页案例标题","title","text","填写首页案例标题")
        );
        model.addAttribute("href", "/tabs-update");
        String id = request.getParameter("rid");
        if (id !=null){
            List<Tabs> list = (List<Tabs>) service.get(id).getData();
            Tabs obj = list.get(0);
            forms = Arrays.asList(
                    new Form("标签","tabs","select","选择案例标签",String.valueOf(obj.getTabs())),
                    new Form("图片链接","image","file","选择上传首页案例的展示图",obj.getImage()),
                    new Form("首页案例标题","title","text","填写首页案例标题",obj.getTitle())
            );
            model.addAttribute("href", "/tabs-update?rid="+id);
        }
        //page
        model.addAttribute("title","方都数字科技 | 首页案例列表");
        model.addAttribute("header","首页案例列表");
        model.addAttribute("page","主页");
        model.addAttribute("pageDesc","首页案例列表");
        //narbar
        model.addAttribute("menu","index");
        model.addAttribute("active","tabs-form");
        //table
        model.addAttribute("forms",forms);
        return "pages/form";
    }

    @PostMapping(value = "/tabs-update")
    @ResponseBody
    public ResponseEntity update(HttpServletRequest request, @Validated TabsRequest tabsRequest, BindingResult bindingResult) {
        ThrowableUtil.checkRequestArgument(bindingResult);
        Tabs obj = null;
        if (request.getParameter("rid") != null) {
            String id = request.getParameter("rid");
            List<Tabs> list = (List<Tabs>) service.get(id).getData();
            obj = list.get(0);
        }
        if (request instanceof MultipartHttpServletRequest && ((MultipartHttpServletRequest) request).getFile("image") != null
                &&  ((MultipartHttpServletRequest) request).getFile("image").getSize() != 0) {
            if (obj!=null) CloudFileUtils.deleteImage(obj.getImage());
            UploadResult result = CloudFileUtils.uploadImage(ImagePath.TABS, request, "image");
            if (result.getCode() == 0) {
                tabsRequest.setImageUrl(result.getImageMap().get("image"));
            }
        }
        if (obj != null) service.update(tabsRequest.parse(obj));
        else service.add(tabsRequest.parse());
        return ResponseEntity.ok(JsonResponse.getInstance());
    }
    @PostMapping(value = "/tabs-delete")
    @ResponseBody
    public ResponseEntity delete(HttpServletRequest request, @Validated DeleteRequest deleteRequest, BindingResult bindingResult) {
        ThrowableUtil.checkRequestArgument(bindingResult);
        List<Tabs> list = (List<Tabs>) service.get(deleteRequest.getRid()).getData();
        Tabs obj = list.get(0);
        CloudFileUtils.deleteImage(obj.getImage());
        Response delete = service.delete(deleteRequest.getRid());
        return ResponseEntity.ok(JsonResponse.getInstance());
    }
}
