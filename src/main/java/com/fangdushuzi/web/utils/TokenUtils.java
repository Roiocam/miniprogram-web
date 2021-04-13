package com.fangdushuzi.web.utils;

import com.fangdushuzi.web.enums.PathEnum;
import com.fangdushuzi.web.to.Token;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author Andy Chen
 * @date 2020/5/12 下午11:32
 */
public class TokenUtils {
    private static Token token;
    public static Token getToken(){
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        if (token != null && now > (token.getCreateTime() + token.getExpires_in())){
            return token;
        }
        Mono<Token> resp = WebClient.create()
                                    .get()
                                    .uri(uriBuilder -> uriBuilder
                                            .scheme(Constant.SCHEME)
                                            .host(Constant.HOST)
                                            .path(PathEnum.TOKEN.getPath())
                                            .queryParam("grant_type", "client_credential")
                                            .queryParam("appid", Constant.APPID)
                                            .queryParam("secret", Constant.APPSECRET)
                                            .build())
                                    .retrieve()
                                    .bodyToMono(Token.class);
         token = resp.block();
         return token;
    }
}
