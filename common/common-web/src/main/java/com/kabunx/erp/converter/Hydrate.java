package com.kabunx.erp.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

/**
 * 简单的转化工具
 */
public class Hydrate {

    public static <T> T map(String content, Class<T> target) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(content, target);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T> T map(Object source, Class<T> target) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(source), target);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T> Optional<T> map2Optional(String content, Class<T> target) {
        T result = map(content, target);
        if (result == null) {
            return Optional.empty();
        }
        return Optional.of(result);
    }

    public static <T> Optional<T> map2Optional(Object source, Class<T> target) {
        T result = map(source, target);
        if (result == null) {
            return Optional.empty();
        }
        return Optional.of(result);
    }

    public static String map2String(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
