package com.fangdushuzi.web.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Objects;

/**
 * @author Andy Chen
 * @date 2020/5/15 下午7:02
 */
public class ThrowableUtil {

    /**
     * 检查{@link org.springframework.validation.annotation.Validated}参数校验结果.
     * 校验失败时，抛出参数错误异常，让统一异常处理器捕获。
     *
     * @param result
     */
    public static void checkRequestArgument(BindingResult result) {
        if (result != null && result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> errors = result.getFieldErrors();
            if (!errors.isEmpty()) {
                FieldError error = errors.get(0);
                String rejectedValue = Objects.toString(error.getRejectedValue(), "");
                String defMsg = error.getDefaultMessage();
                // 排除类上面的注解提示
                if (rejectedValue.contains(Constant.DELIMITER_TO)) {
                    sb.append(defMsg);
                } else {
                    if (defMsg.contains(Constant.DELIMITER_COLON)) {
                        sb.append(error.getField()).append(" ").append(defMsg);
                    } else {
                        sb.append(error.getField()).append(" ").append(defMsg).append(":").append(rejectedValue);
                    }
                }
            } else {
                String msg = result.getAllErrors().get(0).getDefaultMessage();
                sb.append(msg);
            }
            throw new ParamterException(sb.toString());
        }
    }
}
