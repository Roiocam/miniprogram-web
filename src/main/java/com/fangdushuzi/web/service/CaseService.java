package com.fangdushuzi.web.service;

import com.fangdushuzi.web.dao.HttpDao;
import com.fangdushuzi.web.entity.Case;
import com.fangdushuzi.web.enums.DBName;
import com.fangdushuzi.web.to.Result;
import com.fangdushuzi.web.utils.JSONUtils;
import com.fangdushuzi.web.vo.Response;
import org.springframework.stereotype.Service;

/**
 * @author Andy Chen
 * @date 2020/5/12 上午6:02
 */
@Service
public class CaseService implements IService<Case>{
    @Override
    public Response getList(int page) {
        if (page <= 0){
            page = 1;
        }
        Result result = HttpDao.getList(DBName.CASE, page);
        return Response.getInstance(result, Case.class);
    }

    @Override
    public Response get(String id) {
        Result get = HttpDao.get(DBName.CASE, id);
        return Response.getInstance(get,Case.class);
    }

    @Override
    public Response add(Case obj) {
        Result add = HttpDao.add(DBName.CASE, JSONUtils.toJSONString(obj));
        return Response.getInstance(add,Case.class);

    }

    @Override
    public Response update(Case obj) {
        Result update = HttpDao.update(DBName.CASE, obj.get_id(), JSONUtils.toJSONString(obj));
        return Response.getInstance(update,Case.class);
    }

    @Override
    public Response delete(String id) {
        Result delete = HttpDao.delete(DBName.CASE, id);
        return Response.getInstance(delete,Case.class);
    }
}
