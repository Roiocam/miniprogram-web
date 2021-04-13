package com.fangdushuzi.web.service;

import com.fangdushuzi.web.dao.HttpDao;
import com.fangdushuzi.web.entity.Service;
import com.fangdushuzi.web.enums.DBName;
import com.fangdushuzi.web.to.Result;
import com.fangdushuzi.web.utils.JSONUtils;
import com.fangdushuzi.web.vo.Response;

/**
 * @author Andy Chen
 * @date 2020/5/12 上午7:06
 */
@org.springframework.stereotype.Service
public class ServiceService implements IService<Service> {

    @Override
    public Response getList(int page) {
        if (page <= 0){
            page = 1;
        }
        Result result = HttpDao.getList(DBName.SERVICE, page);
        return Response.getInstance(result, Service.class);
    }

    @Override
    public Response get(String id) {
        Result get = HttpDao.get(DBName.SERVICE, id);
        return Response.getInstance(get,Service.class);
    }

    @Override
    public Response add(Service obj) {
        Result add = HttpDao.add(DBName.SERVICE, JSONUtils.toJSONString(obj));
        return Response.getInstance(add,Service.class);

    }

    @Override
    public Response update(Service obj) {
        Result update = HttpDao.update(DBName.SERVICE, obj.get_id(), JSONUtils.toJSONString(obj));
        return Response.getInstance(update,Service.class);
    }

    @Override
    public Response delete(String id) {
        Result delete = HttpDao.delete(DBName.SERVICE, id);
        return Response.getInstance(delete,Service.class);
    }
}
