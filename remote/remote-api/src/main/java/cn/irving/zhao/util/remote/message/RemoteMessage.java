package cn.irving.zhao.util.remote.message;

import java.io.InputStream;
import java.util.Map;

/**
 * 远程交互消息基类
 *
 * @author Irving.Zhao
 * @version 1.0
 * @since 1.0
 */
public interface RemoteMessage {

    /**
     * 设置请求地址
     *
     * @param url 请求地址
     */
    RemoteMessage setUrl(String url);

    /**
     * 设置一个请求参数
     *
     * @param key   参数名
     * @param value 参数值
     */
    RemoteMessage addParameter(String key, Object value);

    /**
     * 设置一组参数
     *
     * @param params 参数键值对
     */
    RemoteMessage addParameters(Map<String, Object> params);

    /**
     * 设置请求流
     *
     * @param stream 请求流
     */
    RemoteMessage setRequestStream(InputStream stream);

    /**
     * 获得响应状态
     */
    int getResultCode();

    /**
     * 获得响应流
     */
    InputStream getResponseStream();


}
