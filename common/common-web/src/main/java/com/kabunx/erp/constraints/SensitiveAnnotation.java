package com.kabunx.erp.constraints;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveSerialize.class)
public @interface SensitiveAnnotation {
    /**
     * 脱敏类型(规则)
     */
    SensitiveTypeEnum type();

    /**
     * 判断注解是否生效的方法
     */
    String isEffectiveMethod() default "";
}
