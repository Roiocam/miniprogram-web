package com.fangdushuzi.web.service;

import com.fangdushuzi.web.dao.HttpDao;
import com.fangdushuzi.web.entity.Swiper;
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
public class SwiperService implements IService<Swiper> {

    @Override
    public Response getList(int page) {
        if (page <= 0){
            page = 1;
        }
        Result result = HttpDao.getList(DBName.SWIPER, page);
        return Response.getInstance(result, Swiper.class);
    }

    @Override
    public Response get(String id) {
        Result get = HttpDao.get(DBName.SWIPER, id);
        return Response.getInstance(get,Swiper.class);
    }

    @Override
    public Response add(Swiper obj) {
        Result add = HttpDao.add(DBName.SWIPER, JSONUtils.toJSONString(obj));
        return Response.getInstance(add,Swiper.class);

    }

    @Override
    public Response update(Swiper obj) {
        Result update = HttpDao.update(DBName.SWIPER, obj.get_id(), JSONUtils.toJSONString(obj));
        return Response.getInstance(update,Swiper.class);
    }

    @Override
    public Response delete(String id) {
        Result delete = HttpDao.delete(DBName.SWIPER, id);
        return Response.getInstance(delete,Swiper.class);
    }
}
