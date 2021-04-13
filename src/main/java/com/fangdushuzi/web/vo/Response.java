package com.fangdushuzi.web.vo;

import com.fangdushuzi.web.to.Pager;
import com.fangdushuzi.web.to.Result;
import com.fangdushuzi.web.utils.JSONUtils;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Andy Chen
 * @date 2020/5/12 上午6:57
 */
@Data
public class Response {
    private int offset;
    private int limit;
    private int total;
    private Object data;
    private Response(){
    }
    private void setQuery(int offset,int limit,int total,Object data){
        this.offset=offset;
        this.limit=limit;
        this.total=total;
        this.data=data;
    }
    private void setDeleted(int deleted){
        this.data = new Deleted("删除成功",deleted);
    }
    private void setUpdate(int matched,int modified,String id){
        this.data = new Update("更新成功",matched,modified,id);
    }
    private void setInsert(List<String> idList){
        this.data = new Insert("插入成功",idList);
    }
    public static <T> Response getInstance(Result result, Class<T> elementClass){
        Response response = new Response();
        if (null != result.getPager() && null != result.getData()){
            Pager pager = result.getPager();
            List<String> data = result.getData();
            List<T> collect = data.stream().map((item) -> {
                return JSONUtils.parseObject(item, elementClass);
            }).collect(Collectors.toList());
            response.setQuery(pager.getOffset(),pager.getLimit(),pager.getTotal(),collect);
        }
        if (result.getId_list()!=null){
            response.setInsert(result.getId_list());
        }
        if (result.getMatched() != 0 && result.getId() != null &&result.getModified() != 0){
            response.setUpdate(result.getMatched(),result.getModified(),result.getId());
        }
        if(result.getDeleted() != 0){
            response.setDeleted(result.getDeleted());
        }
        return response;
    }

}
