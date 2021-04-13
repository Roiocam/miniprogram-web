package com.fangdushuzi.web.config;

import com.fangdushuzi.web.enums.ResponseEnum;
import com.fangdushuzi.web.utils.ParamterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andy Chen
 * @date 2020/5/15 下午7:12
 */
@RestControllerAdvice
@Slf4j
public class ErrorController {
        /**
         * 处理未捕获异常
         *
         * @param request
         * @param respones
         * @param ex
         * @return
         */
        @ExceptionHandler(Exception.class)
        public ResponseEntity handleException(HttpServletRequest request, HttpServletResponse respones, Exception ex) {
            int code = ResponseEnum.SERVER_ERROR.getCode();
            String message = ResponseEnum.SERVER_ERROR.getMessage();
            if (ex instanceof ParamterException) {
                code = ResponseEnum.PARAMTER_ERROR.getCode();
                message = ex.getMessage();
            }
            log.error("未处理异常:{}", ex.toString());
            return ResponseEntity.ok(getErrorMap(request, code, message));
        }

        /**
         * 构建异常响应
         *
         * @param request
         * @param code
         * @param errorMessage
         * @return
         */
        private Map<String, Object> getErrorMap(HttpServletRequest request, int code, String errorMessage) {
            String requestUri = request.getRequestURI();
            HashMap<String, Object> map = new HashMap<>();
            map.put("message", errorMessage);
            map.put("code", code);
            map.put("path", requestUri);
            return map;
        }
    }