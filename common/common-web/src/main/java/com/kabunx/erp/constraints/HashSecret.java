package com.kabunx.erp.constraints;

import com.kabunx.erp.validator.HashSecretValidator;
import com.kabunx.erp.validator.ValidatorEnum;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {HashSecretValidator.class}) //指向实现验证的类
public @interface HashSecret {
    ValidatorEnum type() default ValidatorEnum.SMS_CODE_SECRET;

    String key() default "";

    String message() default "参数异常";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
