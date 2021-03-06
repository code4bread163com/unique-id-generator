package com.cfc.uid.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.type.ArrayType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

/**
 * JSON 工具类
 *
 * @author zhangliang
 * @date 2020/9/23
 */
@Slf4j
public class JacksonUtils {

    private static final ObjectMapper mapper;

    private JacksonUtils() {
    }

    static {
        mapper = new ObjectMapper();
        SimpleFilterProvider filters = new SimpleFilterProvider();
        filters.setFailOnUnknownId(false);
        mapper.setFilterProvider(filters);
    }

    /**
     * 对象转JSON
     *
     * @param object
     * @return
     */
    public static String objectToJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("ObjectToJson exception: {}", e.getMessage(), e);
        }
        return null;
    }

    public static <T> T[] stringJsonToArray(String stringListJson, Class<T> tClass) {
        try {
            ArrayType arrayType = mapper.getTypeFactory().constructArrayType(tClass);
            return mapper.readValue(stringListJson, arrayType);
        } catch (Exception e) {
            log.error("ObjectToJson exception: {}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * json字符串转换为Java对象
     * 默认忽略不识别的字段
     *
     * @param json      json字符串
     * @param valueType 转换对象的类型
     * @return
     */
    public static <T> T fromJsonToObject(String json, Class<T> valueType) {
        return fromJsonToObject(json, valueType, true);
    }

    /**
     * json字符串转换为Java对象
     *
     * @param json                    json字符串
     * @param valueType               转换对象的类型
     * @param ignoreUnknownProperties 是否忽略不识别的字段，true表示出现不识别的字段时忽略此字段，false表示出现不识别的字段时解析失败
     * @return
     */
    public static <T> T fromJsonToObject(String json, Class<T> valueType, boolean ignoreUnknownProperties) {
        if (StringUtils.isEmpty(json)) {
            log.error("ObjectToJson error, parameter is empty");
            return null;
        }

        //设置是否忽略不识别的字段
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, !ignoreUnknownProperties);
        try {
            return mapper.readValue(json, valueType);
        } catch (Exception e) {
            log.error("ObjectToJson exception: {}", e.getMessage(), e);
        }

        return null;
    }

    /**
     * json字符串转换为Java对象
     *
     * @param <T>           转换为的java对象
     * @param json          json字符串
     * @param typeReference jackjson自定义的类型
     * @return 返回Java对象
     */
    public static <T> T fromJsonToObject(String json, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(json)) {
            log.error("ObjectToJson error, parameter is empty");
            return null;
        }

        try {
            return mapper.readValue(json, typeReference);
        } catch (Exception e) {
            log.error("ObjectToJson exception: {}", e.getMessage(), e);
        }

        return null;
    }

}
