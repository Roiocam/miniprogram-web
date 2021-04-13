package com.fangdushuzi.web.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Andy Chen
 * @date 2020/5/15 下午7:09
 */
public class NotBlankValidator implements ConstraintValidator<NotBlank, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (value !=null && !"".equals(value));
    }
}