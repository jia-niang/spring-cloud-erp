package com.kabunx.erp.constraints;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.kabunx.erp.util.SensitiveUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * 脱敏序列化
 */
public class SensitiveSerialize extends JsonSerializer<String> implements ContextualSerializer {
    /**
     * 脱敏类型
     */
    private final SensitiveTypeEnum type;

    public SensitiveSerialize(SensitiveTypeEnum type) {
        this.type = type;
    }

    @Override
    public void serialize(String str, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        switch (this.type) {
            case CHINESE_NAME: {
                jsonGenerator.writeString(SensitiveUtils.chineseName(str));
                break;
            }
            case ID_CARD: {
                jsonGenerator.writeString(SensitiveUtils.idCardNum(str));
                break;
            }
            case FIXED_PHONE: {
                jsonGenerator.writeString(SensitiveUtils.fixedPhone(str));
                break;
            }
            case MOBILE_PHONE: {
                jsonGenerator.writeString(SensitiveUtils.mobilePhone(str));
                break;
            }
            case ADDRESS: {
                jsonGenerator.writeString(SensitiveUtils.address(str, 4));
                break;
            }
            case EMAIL: {
                jsonGenerator.writeString(SensitiveUtils.email(str));
                break;
            }
            case BANK_CARD: {
                jsonGenerator.writeString(SensitiveUtils.bankCard(str));
                break;
            }
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        // 为空直接跳过
        if (beanProperty != null) {
            // 非 String 类直接跳过
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
                SensitiveAnnotation sensitiveAnnotation = beanProperty.getAnnotation(SensitiveAnnotation.class);
                if (sensitiveAnnotation == null) {
                    sensitiveAnnotation = beanProperty.getContextAnnotation(SensitiveAnnotation.class);
                }
                if (sensitiveAnnotation != null) {
                    // 如果能得到注解，就将注解的 value 传入 SensitiveSerialize
                    return new SensitiveSerialize(sensitiveAnnotation.type());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(null);
    }
}
