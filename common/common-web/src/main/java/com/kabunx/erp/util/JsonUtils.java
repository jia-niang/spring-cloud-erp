package com.kabunx.erp.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * jackson处理工具，建议使用来DO<->VO
 */
@Slf4j
public class JsonUtils {

    private static ObjectMapper objectMapper;

    /**
     * 初始化ObjectMapper
     */
    private static ObjectMapper getObjectMapper() {
        if (objectMapper != null) {
            return objectMapper;
        }
        objectMapper = new ObjectMapper();
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 不存在的属性，不转化，否则报错：com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    /**
     * 将Java对象转换为Json String
     */
    public static String stringify(Object object) {
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            log.error("Java转Json异常", e);
            return null;
        }
    }

    /**
     * 将JSON字符串转换为java对象
     */
    public static <T> T toObject(String jsonStr, Class<T> clazz) {
        try {
            return getObjectMapper().readValue(jsonStr, clazz);
        } catch (Exception e) {
            log.error("Json转Java异常", e);
            return null;
        }
    }

    /**
     * 将JSON字符串转换为java对象
     */
    public static <T> T toObject(Object object, Class<T> clazz) {
        try {
            return getObjectMapper().readValue(stringify(object), clazz);
        } catch (Exception e) {
            log.error("Json转Java异常", e);
            return null;
        }
    }

    /**
     * 将JSON字符串转换为Map<String, Object></>对象
     */
    public static Map<String, Object> parseObject(String jsonStr) {
        try {
            JavaType javaType = getObjectMapper()
                    .getTypeFactory()
                    .constructParametricType(Map.class, String.class, Object.class);
            return getObjectMapper().readValue(jsonStr, javaType);
        } catch (Exception e) {
            log.error("Json转Java异常", e);
            return null;
        }
    }

    /**
     * 将JSON字符串转换为java对象
     */
    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        return toObject(jsonStr, clazz);
    }

    /**
     * 将JSON字符串转换为复杂类型的Java对象
     */
    public static <T> T parseObject(String jsonStr, TypeReference<T> typeReference) {
        try {
            return getObjectMapper().readValue(jsonStr, typeReference);
        } catch (Exception e) {
            log.error("Json转Java异常", e);
            return null;
        }
    }

    /**
     * 将JSON字符串转换为list对象
     */
    public static <T> List<T> parseArray(String jsonStr, Class<T> clazz) {
        try {
            JavaType javaType = getObjectMapper()
                    .getTypeFactory()
                    .constructParametricType(List.class, clazz);
            return getObjectMapper().readValue(jsonStr, javaType);
        } catch (Exception e) {
            log.error("Json转Java异常", e);
            return null;
        }
    }
}
