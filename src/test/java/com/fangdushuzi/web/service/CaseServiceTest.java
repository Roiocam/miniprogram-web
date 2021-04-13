package com.fangdushuzi.web.service;

import com.fangdushuzi.web.utils.JSONUtils;
import com.fangdushuzi.web.vo.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Andy Chen
 * @date 2020/5/13 下午4:59
 */
@SpringBootTest
class CaseServiceTests {
    @Autowired
    private CaseService caseService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private StyleService styleService;
    @Autowired
    private SwiperService swiperService;
    @Autowired
    private TabsService tabsService;
    @Test
    public void test(){
        Response list = caseService.getList(1);
        System.out.println("case:{"+ JSONUtils.toJSONString(list));
        Response serviceList = serviceService.getList(1);
        System.out.println("service:{"+ JSONUtils.toJSONString(serviceList));
        Response serviceList1 = styleService.getList(1);
        System.out.println("style:{"+ JSONUtils.toJSONString(serviceList1));
        Response swiperServiceList = swiperService.getList(1);
        System.out.println("swiper:{"+ JSONUtils.toJSONString(swiperServiceList));
        Response tabsServiceList = tabsService.getList(1);
        System.out.println("tabs:{"+ JSONUtils.toJSONString(tabsServiceList));
    }

}