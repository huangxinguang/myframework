package com.starlight.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by huangxinguang on 2017/6/30 下午4:48.
 */
public class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper OBJECT_MAPPER =  new ObjectMapper();

    public static <T> String toJson(T t) {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            LOGGER.error("convert pojo to json failure",e);
            throw new RuntimeException(e);
        }
        return json;
    }

    public static <T> T fromJson(String json,Class<T> type) {
        T pojo;
        try {
            pojo = OBJECT_MAPPER.readValue(json,type);
        } catch (IOException e) {
            LOGGER.error("convert json to pojo failure",e);
            throw new RuntimeException(e);
        }
        return pojo;
    }
}
