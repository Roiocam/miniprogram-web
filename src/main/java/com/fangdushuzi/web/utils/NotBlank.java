package com.fangdushuzi.web.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 非空校验
 * <p>
 *     使用{ElementType.PARAMETER, ElementType.FIELD}作用在属性和参数上.
 * </p>
 * @author Andy Chen
 * @date 2020/5/15 下午7:07
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NotBlankValidator.class})
public @interface NotBlank {
    /**
     * 校验失败的信息
     *
     * @return
     */
    String message() default ValidMessage.CK_NOT_BLANK;

    String value() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}