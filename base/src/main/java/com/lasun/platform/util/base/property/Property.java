package com.lasun.platform.util.base.property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 属性自动加载类<br />
 * 类将自动加载Classpath下的所有.properties文件。<br />
 * 文件中key值冲突时，后加载的文件将覆盖先加载的文件。<br />
 *
 * @author Irving.Zhao
 * @version 1.0
 * @since 1.0
 */
public final class Property {

    private static final Properties properties = new Properties();
    private static Logger logger = LoggerFactory.getLogger(Property.class);

    static {
        loadProperties();
    }

    private Property() {
    }

    /**
     * <p>加载根目录下的所有配置文件</p>
     * <p>加载为覆盖式更新，会将新文件内的属性覆盖原有同名属性</p>
     */
    public static void loadProperties() {
        try {
            File file = new File(Property.class.getResource("/").toURI());
            loadProperties(file);
        } catch (URISyntaxException e) {
            logger.error("初始化根目录异常", e);
            e.printStackTrace();
        }
    }

    /**
     * 加载制定目录下的配置文件
     * <p>加载为覆盖式更新，会将新文件内的属性覆盖原有同名属性</p>
     *
     * @param file 目录
     */
    public static void loadProperties(File file) {
        File item = null;
        try {
            File[] propertyFiles = file.listFiles((pathname) -> pathname.isDirectory() || (pathname.isFile() && pathname.getName().indexOf("properties") > 0));
            for (int i = 0; i < propertyFiles.length; i++) {
                item = propertyFiles[i];
                if (item.isFile()) {
                    properties.load(new FileReader(item));
                } else if (item.isDirectory()) {
                    loadProperties(item);
                }
            }
        } catch (IOException e) {
            logger.error("文件不存在（" + item.getName() + "）", e);
            e.printStackTrace();
        }
    }


    /**
     * 通过键获得配置文件中的值
     *
     * @param key 键
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }

    /**
     * 通过正则匹配键，获得键值
     *
     * @param pattern 正则表达式
     * @return 所有匹配到的值数组
     */
    public static String[] getValues(String pattern) {
        Pattern pat = Pattern.compile(pattern);
        Set<Map.Entry<Object, Object>> entries = properties.entrySet();
        List<String> values = new ArrayList<>(entries.size());
        entries.forEach((entry) -> {
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (key != null && value != null) {
                if (pat.matcher(key.toString()).matches()) {
                    values.add(value.toString());
                }
            }
        });
        return values.toArray(new String[]{});
    }

    /**
     * 通过正则表达式获得属性键值对
     *
     * @param pattern key值正则表达式
     * @return 所有匹配到的键值对
     */
    public static Map<String, String> getKeyValues(String pattern) {
        Pattern pat = Pattern.compile(pattern);
        return getKeyValues(pat);
    }

    /**
     * 通过正则查找属性值
     *
     * @param pattern 正则
     * @return 所有匹配到的键值对
     */
    public static Map<String, String> getKeyValues(Pattern pattern) {
        Map<String, String> result = new HashMap<>();
        Set<Map.Entry<Object, Object>> entries = properties.entrySet();
        entries.forEach((entry) -> {
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (key != null && value != null) {
                if (pattern.matcher(key.toString()).matches()) {
                    result.put(String.valueOf(key), String.valueOf(value));
                }
            }
        });
        return result;
    }
}
