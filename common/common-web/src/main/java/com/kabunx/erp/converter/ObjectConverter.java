package com.kabunx.erp.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class ObjectConverter {
    public static <T> T map(Object source, Class<T> target) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(source), target);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T> Optional<T> optionalMap(Object source, Class<T> target) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return Optional.of(objectMapper.readValue(objectMapper.writeValueAsString(source), target));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }
}
