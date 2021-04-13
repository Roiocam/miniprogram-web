package com.fangdushuzi.web.service;

import com.fangdushuzi.web.dao.HttpDao;
import com.fangdushuzi.web.entity.Function;
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
public class FunctionService implements IService<Function> {

    @Override
    public Response getList(int page) {
       if (page <= 0){
           page = 1;
       }
        Result result = HttpDao.getList(DBName.FUNCTION, page);
        return Response.getInstance(result,Function.class);
    }

    @Override
    public Response get(String id) {
        Result get = HttpDao.get(DBName.FUNCTION, id);
        return Response.getInstance(get,Function.class);
    }

    @Override
    public Response add(Function obj) {
        Result add = HttpDao.add(DBName.FUNCTION, JSONUtils.toJSONString(obj));
        return Response.getInstance(add,Function.class);

    }

    @Override
    public Response update(Function obj) {
        Result update = HttpDao.update(DBName.FUNCTION, obj.get_id(), JSONUtils.toJSONString(obj));
        return Response.getInstance(update,Function.class);
    }

    @Override
    public Response delete(String id) {
        Result delete = HttpDao.delete(DBName.FUNCTION, id);
        return Response.getInstance(delete,Function.class);
    }
}
