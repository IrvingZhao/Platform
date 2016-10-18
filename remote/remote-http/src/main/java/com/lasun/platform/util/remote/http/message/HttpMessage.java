package com.lasun.platform.util.remote.http.message;

import com.lasun.platform.util.remote.http.config.HttpMethod;
import com.lasun.platform.util.remote.http.config.RequestType;
import com.lasun.platform.util.remote.message.RemoteMessage;

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
public class HttpMessage implements RemoteMessage {

    private String url;
    private Map<String, Object> requestParams;
    private HttpMethod method = HttpMethod.GET;
    private RequestType requestType = RequestType.NORMAL;
    private InputStream requestStream;

    private int resultCode;
    private Map<String, String> responseHead;
    private InputStream responseStream;

    /**
     * 构建消息，默认以GET方式提交，NORMAL提交形式
     *
     * @param url 请求地址
     */
    public HttpMessage(String url) {
        this(url, HttpMethod.GET, RequestType.NORMAL);
    }

    /**
     * 构建消息
     *
     * @param url         请求地址
     * @param method      请求提交方法
     * @param requestType 请求提交形式
     */
    public HttpMessage(String url, HttpMethod method, RequestType requestType) {
        this(url, method, requestType, new HashMap<>());
    }

    /**
     * @param url           请求地址
     * @param method        请求提交方法
     * @param requestType   请求提交形式
     * @param requestParams 请求提交参数
     */
    public HttpMessage(String url, HttpMethod method, RequestType requestType, Map<String, Object> requestParams) {
        this.url = url;
        this.method = method;
        this.requestType = requestType;
        this.requestParams = requestParams;
    }

    /**
     * 设置请求地址
     *
     * @param url 请求地址
     */
    @Override
    public RemoteMessage setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * 设置一个请求参数
     *
     * @param key   参数名
     * @param value 参数值
     */
    @Override
    public RemoteMessage addParameter(String key, Object value) {
        requestParams.put(key, value);
        return this;
    }

    /**
     * 设置一组参数
     *
     * @param params 参数键值对
     */
    @Override
    public RemoteMessage addParameters(Map<String, Object> params) {
        requestParams.putAll(params);
        return this;
    }

    /**
     * 设置请求流，只有请求形式为流请求时才会使用
     *
     * @param stream 请求流
     */
    @Override
    public RemoteMessage setRequestStream(InputStream stream) {
        this.requestStream = stream;
        return this;
    }

    /**
     * 设置请求流对象，默认将字符串内容转为ByteArrayInputStream
     *
     * @param content 请求内容
     */
    public HttpMessage setRequestStream(String content) {
        this.requestStream = new ByteArrayInputStream(content.getBytes());
        return this;
    }

    /**
     * 获得响应状态
     */
    @Override
    public int getResultCode() {
        return resultCode;
    }

    /**
     * 获得响应流
     */
    @Override
    public InputStream getResponseStream() {
        return responseStream;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, Object> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(Map<String, Object> requestParams) {
        this.requestParams = requestParams;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public InputStream getRequestStream() {
        return requestStream;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public void setResponseStream(InputStream responseStream) {
        this.responseStream = responseStream;
    }

    public Map<String, String> getResponseHead() {
        return responseHead;
    }

    public void setResponseHead(Map<String, String> responseHead) {
        this.responseHead = responseHead;
    }
}
