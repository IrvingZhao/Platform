package com.lasun.platform.util.base.serial;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.Map;

/**
 * 序列化与反序列化工具类
 *
 * @author 赵嘉楠
 * @version 1.0
 * @since 1.0
 */
public final class SerialUtil {
    private SerialUtil() {
    }

    private final static SerialUtil me = new SerialUtil();
    private final static ObjectMapper OBJECT_MAPPER;
    private final static XmlMapper XML_MAPPER;
    private final static Logger logger;
    private final static SimpleFilterProvider JSON_FILTER_PROVIDER;
    private final static SimpleFilterProvider XML_FILTER_PROVIDER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        JSON_FILTER_PROVIDER = new SimpleFilterProvider();

        XML_MAPPER = new XmlMapper();
        XML_FILTER_PROVIDER = new SimpleFilterProvider();

        logger = LoggerFactory.getLogger(SerialUtil.class);

        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        JSON_FILTER_PROVIDER.setFailOnUnknownId(false);
        OBJECT_MAPPER.setFilterProvider(JSON_FILTER_PROVIDER);

        XML_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        XML_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        XML_FILTER_PROVIDER.setFailOnUnknownId(false);
        XML_MAPPER.setFilterProvider(XML_FILTER_PROVIDER);
    }


    public enum SerialType {
        JSON {
            protected ObjectMapper getMapper() {
                return OBJECT_MAPPER;
            }

            protected SimpleFilterProvider getFilterProvider() {
                return JSON_FILTER_PROVIDER;
            }
        }, XML {
            protected ObjectMapper getMapper() {
                return XML_MAPPER;
            }

            protected SimpleFilterProvider getFilterProvider() {
                return XML_FILTER_PROVIDER;
            }
        };

        protected abstract ObjectMapper getMapper();

        protected abstract SimpleFilterProvider getFilterProvider();
    }

    public enum PropertyFilterType {
        EXCEPT, INCLUDE;
    }

    public static SerialUtil getSerialUtil() {
        return me;
    }


    public void addFilter(String filterId, SerialType type, PropertyFilterType filterType, String... props) {
        if (filterType == PropertyFilterType.EXCEPT) {
            type.getFilterProvider().addFilter(filterId, SimpleBeanPropertyFilter.serializeAllExcept(props));
        } else if (filterType == PropertyFilterType.INCLUDE) {
            type.getFilterProvider().addFilter(filterId, SimpleBeanPropertyFilter.filterOutAllExcept(props));
        }
    }

    public void addFilter(String filterId, PropertyFilterType filterType, String... props) {
        for (int i = 0; i < SerialType.values().length; i++) {
            addFilter(filterId, SerialType.values()[i], filterType, props);
        }
    }


    public String serial(Object value, SerialType formatType) {
        try {
            return formatType.getMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            logger.error("序列化失败", e);
            throw new RuntimeException(e);
        }
    }

    public void serial(OutputStream outputStream, Object value, SerialType formatType) {
        try {
            formatType.getMapper().writeValue(outputStream, value);
        } catch (IOException e) {
            logger.error("序列化失败", e);
            throw new RuntimeException(e);
        }
    }

    public void serial(Writer writer, Object value, SerialType formatType) {
        try {
            formatType.getMapper().writeValue(writer, value);
        } catch (IOException e) {
            logger.error("序列化失败", e);
            throw new RuntimeException(e);
        }
    }

    public Map parse(String content, SerialType formatType) {
        return parse(content, Map.class, formatType);
    }

    public Map parse(Reader content, SerialType formatType) {
        return parse(content, Map.class, formatType);
    }

    public Map parse(InputStream content, SerialType formatType) {
        return parse(content, Map.class, formatType);
    }

    public Map parse(URL content, SerialType formatType) {
        return parse(content, Map.class, formatType);
    }

    public <T> T parse(String content, Class<T> type, SerialType formatType) {
        try {
            return formatType.getMapper().readValue(content, type);
        } catch (IOException e) {
            logger.error("序列化失败", e);
            throw new RuntimeException(e);
        }
    }

    public <T> T parse(Reader content, Class<T> type, SerialType formatType) {
        try {
            return formatType.getMapper().readValue(content, type);
        } catch (IOException e) {
            logger.error("序列化失败", e);
            throw new RuntimeException(e);
        }
    }

    public <T> T parse(InputStream content, Class<T> type, SerialType formatType) {
        try {
            return formatType.getMapper().readValue(content, type);
        } catch (IOException e) {
            logger.error("序列化失败", e);
            throw new RuntimeException(e);
        }
    }

    public <T> T parse(URL content, Class<T> type, SerialType formatType) {
        try {
            return formatType.getMapper().readValue(content, type);
        } catch (IOException e) {
            logger.error("序列化失败", e);
            throw new RuntimeException(e);
        }
    }
}
