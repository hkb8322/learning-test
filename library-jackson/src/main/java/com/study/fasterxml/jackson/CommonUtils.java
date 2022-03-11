package com.study.fasterxml.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED;

public class CommonUtils {

    public static Map<String, Object> toMap(ObjectMapper objectMapper, Object obj) {
        objectMapper.enable(WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
        return objectMapper.convertValue(obj, new TypeReference<Map<String, Object>>() {});
    }
}
