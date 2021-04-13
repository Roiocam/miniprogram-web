package com.fangdushuzi.web.service;

import com.fangdushuzi.web.dao.HttpDao;
import com.fangdushuzi.web.entity.Style;
import com.fangdushuzi.web.enums.DBName;
import com.fangdushuzi.web.to.Result;
import com.fangdushuzi.web.utils.JSONUtils;
import com.fangdushuzi.web.vo.Response;
import org.springframework.stereotype.Service;

/**
 * @author Andy Chen
 * @date 2020/5/12 上午7:05
 */
@Service
public class StyleService implements IService<Style> {

    @Override
    public Response getList(int page) {
        if (page <= 0){
            page = 1;
        }
        Result result = HttpDao.getList(DBName.STYLE, page);
        return Response.getInstance(result, Style.class);
    }

    @Override
    public Response get(String id) {
        Result get = HttpDao.get(DBName.STYLE, id);
        return Response.getInstance(get,Style.class);
    }

    @Override
    public Response add(Style obj) {
        Result add = HttpDao.add(DBName.STYLE, JSONUtils.toJSONString(obj));
        return Response.getInstance(add,Style.class);

    }

    @Override
    public Response update(Style obj) {
        Result update = HttpDao.update(DBName.STYLE, obj.get_id(), JSONUtils.toJSONString(obj));
        return Response.getInstance(update,Style.class);
    }

    @Override
    public Response delete(String id) {
        Result delete = HttpDao.delete(DBName.STYLE, id);
        return Response.getInstance(delete,Style.class);
    }
}
