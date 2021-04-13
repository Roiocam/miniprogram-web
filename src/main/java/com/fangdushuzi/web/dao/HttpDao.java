package com.fangdushuzi.web.dao;

import com.fangdushuzi.web.enums.DBName;
import com.fangdushuzi.web.enums.PathEnum;
import com.fangdushuzi.web.to.Query;
import com.fangdushuzi.web.to.Result;
import com.fangdushuzi.web.to.Token;
import com.fangdushuzi.web.utils.Constant;
import com.fangdushuzi.web.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.fangdushuzi.web.utils.Constant.HOST;
import static com.fangdushuzi.web.utils.Constant.SCHEME;

/**
 * @author Andy Chen
 * @date 2020/5/12 下午11:10
 */
@Component
@Slf4j
public class HttpDao {
    /**
     * 请求操作数据库
     * @param pathEnum 操作类型
     * @param token 密钥
     * @param query 操作
     * @return
     */
    public static Result collectionRequest(PathEnum pathEnum, String token, Query query){
        Mono<Result> resp = WebClient.create()
                                               .post()
                                               .uri(uriBuilder -> uriBuilder
                                             .scheme(SCHEME)
                                             .host(HOST)
                                             .path(pathEnum.getPath())
                                             .queryParam("access_token", token)
                                             .build())
                                               .contentType(MediaType.APPLICATION_JSON_UTF8)
                                               .body(Mono.just(query), Query.class)
                                               .retrieve().bodyToMono(Result.class);
        return resp.block();
    }
    /**
     * 获取列表
     * @param dbName
     * @return
     */
    public static Result getList(DBName dbName, int page){
        Token token = TokenUtils.getToken();
        Query query = Query.getInstance(String.format(Constant.QUERY_SQL,dbName.getName(),pageToOffset(page)));
        return collectionRequest(PathEnum.QUERY, token.getAccess_token(), query);
    }

    /**
     * 单个
     * @param dbName
     * @param id
     * @return
     */
    public static Result get(DBName dbName,String id){
        Token token = TokenUtils.getToken();
        Query query = Query.getInstance(String.format(Constant.GET_SQL,dbName.getName(),id));
        return collectionRequest(PathEnum.QUERY,token.getAccess_token(),query);
    }

    /**
     * 添加
     * @param dbName
     * @param rawData
     * @return
     */
    public static Result add(DBName dbName,String rawData){
        Token token = TokenUtils.getToken();
        Query query = Query.getInstance(String.format(Constant.INSERT_SQL,dbName.getName(),rawData));
        log.info("数据库新增.{}",rawData);
       return collectionRequest(PathEnum.INSERT, token.getAccess_token(), query);
    }

    /**
     * 更新
     * @param dbName
     * @param id
     * @param rawData
     * @return
     */
    public static Result update(DBName dbName, String id, String rawData){
        Token token = TokenUtils.getToken();
        Query query = Query.getInstance(String.format(Constant.UPDATE_SQL,dbName.getName(),id,rawData));
        log.info("数据库更新.{}",rawData);
        return collectionRequest(PathEnum.UPDATE, token.getAccess_token(), query);
    }

    /**
     * 删除
     * @param dbName
     * @param id
     * @return
     */
    public static Result delete(DBName dbName,String id){
        Token token = TokenUtils.getToken();
        Query query = Query.getInstance(String.format(Constant.DELETE_SQL,dbName.getName(),id));
        log.info("数据库删除.{}",id);
        return collectionRequest(PathEnum.DELETE,token.getAccess_token(),query);
    }
    /**
     *
     * @param page
     * @return
     */
    public static int pageToOffset(int page){
        return (page - 1) * 10;
    }
}
