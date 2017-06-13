package cn.irving.zhao.util.remote.http.message;

import cn.irving.zhao.util.remote.http.enums.HttpMethod;
import cn.irving.zhao.util.remote.http.enums.RequestType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Http消息类
 *
 * @author Irving.Zhao
 * @version 1.0
 * @since 1.0
 */
public interface HttpMessage {
    /**
     * 获得请求地址
     *
     * @return 请求地址
     */
    String getRequestUrl();

    /**
     * get请求时需重新替换url
     *
     * @param url 新的请求地址
     */
    void replaceRequestUrl(String url);

    /**
     * 获得请求方式
     *
     * @return 请求方式
     */
    default HttpMethod getRequestMethod() {
        return HttpMethod.GET;
    }

    /**
     * 获得请求体提交方式
     *
     * @return 请求提交方式
     */
    default RequestType getRequestType() {
        return RequestType.NORMAL;
    }

    /**
     * 获得请求头
     *
     * @return 请求头
     */
    default Map<String, String> getRequestHead() {
        return new HashMap<>();
    }

    /**
     * 获得请求参数列表
     *
     * @return 请求参数
     */
    default Map<String, Object> getRequestParams() {
        return new HashMap<>();
    }

    /**
     * 获得请求体流
     *
     * @return 请求输入流
     */
    default InputStream getRequestStream() {
        return new ByteArrayInputStream(new byte[]{});
    }

    /**
     * 设置响应代码
     *
     * @param code 响应代码
     */
    void setResponseCode(int code);

    /**
     * 设置响应头
     *
     * @param head 响应头
     */
    void setResponseHead(Map<String, String> head);

    /**
     * 设置响应流
     *
     * @param inputStream 相应流
     */
    void setResponseStream(InputStream inputStream);

}
