package com.fangdushuzi.web.service;

import com.fangdushuzi.web.dao.HttpDao;
import com.fangdushuzi.web.entity.Tabs;
import com.fangdushuzi.web.enums.DBName;
import com.fangdushuzi.web.to.Result;
import com.fangdushuzi.web.utils.JSONUtils;
import com.fangdushuzi.web.vo.Response;
import org.springframework.stereotype.Service;

/**
 * @author Andy Chen
 * @date 2020/5/12 上午7:06
 */
@Service
public class TabsService implements IService<Tabs> {

    @Override
    public Response getList(int page) {
        if (page <= 0){
            page = 1;
        }
        Result result = HttpDao.getList(DBName.TABS, page);
        return Response.getInstance(result, Tabs.class);
    }

    @Override
    public Response get(String id) {
        Result get = HttpDao.get(DBName.TABS, id);
        return Response.getInstance(get,Tabs.class);
    }

    @Override
    public Response add(Tabs obj) {
        Result add = HttpDao.add(DBName.TABS, JSONUtils.toJSONString(obj));
        return Response.getInstance(add,Tabs.class);

    }

    @Override
    public Response update(Tabs obj) {
        Result update = HttpDao.update(DBName.TABS, obj.get_id(), JSONUtils.toJSONString(obj));
        return Response.getInstance(update,Tabs.class);
    }

    @Override
    public Response delete(String id) {
        Result delete = HttpDao.delete(DBName.TABS, id);
        return Response.getInstance(delete,Tabs.class);
    }
}
