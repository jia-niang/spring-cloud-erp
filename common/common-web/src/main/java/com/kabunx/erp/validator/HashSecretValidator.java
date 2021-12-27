package com.kabunx.erp.validator;

import com.kabunx.erp.constant.GlobalConstant;
import com.kabunx.erp.entity.HashSecret;
import com.kabunx.erp.property.SecretProperties;
import com.kabunx.erp.util.AESUtils;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HashSecretValidator implements ConstraintValidator<com.kabunx.erp.constraints.HashSecret, String> {

    @Resource
    SecretProperties secretProperties;

    private com.kabunx.erp.constraints.HashSecret hashSecret;

    private ValidatorEnum validatorEnum;

    @Override
    public void initialize(com.kabunx.erp.constraints.HashSecret HashSecretAnnotation) {
        this.hashSecret = HashSecretAnnotation;
        this.validatorEnum = HashSecretAnnotation.type();
    }

    /**
     * 1、解密数据
     * 2、获取参数和时间戳
     * 3、比对是否正确
     *
     * @param value   数据
     * @param context 上下文
     * @return 是否通过验证
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        HashSecret entity = decrypt2Entity(value);
        if (entity == null) {
            return false;
        }
        return entity.getType().equals(validatorEnum.getType());
    }

    private String getKey() {
        return hashSecret.key().isEmpty()
                ? secretProperties.getSmsCodeKey()
                : hashSecret.key();
    }

    private HashSecret decrypt2Entity(String value) {
        String original = AESUtils.decrypt(value, getKey());
        if (original != null) {
            String[] data = original.split(GlobalConstant.SPLIT_REGEX, 2);
            if (data.length == 2) {
                return new HashSecret(data[0], Long.parseLong(data[1]));
            }
        }
        return null;
    }
}
