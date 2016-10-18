package com.lasun.platform.util.remote.http;

import com.lasun.platform.util.remote.RemoteClient;
import com.lasun.platform.util.remote.exception.RemoteException;
import com.lasun.platform.util.remote.http.config.ClientConfig;
import com.lasun.platform.util.remote.http.config.HttpMethod;
import com.lasun.platform.util.remote.http.config.RequestType;
import com.lasun.platform.util.remote.http.config.SSLConfig;
import com.lasun.platform.util.remote.http.message.HttpMessage;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.sync.CloseableHttpClient;
import org.apache.hc.client5.http.impl.sync.HttpClientBuilder;
import org.apache.hc.client5.http.impl.sync.HttpClients;
import org.apache.hc.client5.http.methods.CloseableHttpResponse;
import org.apache.hc.client5.http.methods.HttpRequestBase;
import org.apache.hc.client5.http.methods.HttpUriRequest;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.entity.ContentType;
import org.apache.hc.core5.http.entity.EntityUtils;
import org.apache.hc.core5.http.entity.InputStreamEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.ssl.SSLContexts;

import javax.activation.MimetypesFileTypeMap;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.*;

/**
 * 请求发送类
 * <p>
 * 同一个对象视为同一个回话
 * </p>
 *
 * @author 赵嘉楠
 * @version 1.0
 * @since 1.0
 */
public class HttpClient implements RemoteClient<HttpMessage> {

    private CloseableHttpClient client;
    private Charset clientCharset;
    private MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();

    /**
     * 默认构建
     */
    public HttpClient() {
        this(new ClientConfig());
    }

    /**
     * 构建请求发送类
     *
     * @param clientConfig 发送类配置
     */
    public HttpClient(ClientConfig clientConfig) {
        HttpClientBuilder builder = HttpClients.custom();
        if (clientConfig.getSSlConfig() != null) {
            SSLConnectionSocketFactory sslConnectionSocketFactory = getSSLFactory(clientConfig.getSSlConfig());
            if (sslConnectionSocketFactory != null) {
                builder.setSSLSocketFactory(sslConnectionSocketFactory);
            }
        }
        this.clientCharset = clientConfig.getCharset();
        client = builder.build();
    }

    /**
     * 发送消息
     *
     * @param message 消息对象
     */
    public void sendMessage(HttpMessage message) {
        try {
            HttpUriRequest request;
            HttpEntity entity = generateHttpEntity(message);
            if (message.getMethod() == HttpMethod.GET) {
                try {
                    String paramStr = EntityUtils.toString(entity, clientCharset);
                    String url = message.getUrl();
                    url = url + (url.contains("?") ? "&" : "?") + paramStr;
                    message.setUrl(url);
                    entity = null;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            request = generateRequest(message.getUrl(), message.getMethod());
            if (entity != null) {
                request.setEntity(entity);
            }
            CloseableHttpResponse response = client.execute(request);
            message.setResultCode(response.getCode());
            Header[] headers = response.getAllHeaders();
            Map<String, String> responseHeader = new HashMap<>(headers.length);
            for (Header item : headers) {
                responseHeader.put(item.getName(), item.getValue());
            }
            message.setResponseHead(responseHeader);
            message.setResponseStream(response.getEntity().getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据消息构建请求体
     */
    private HttpEntity generateHttpEntity(HttpMessage message) {
        HttpEntity result = null;
        RequestType type = message.getRequestType();
        if (message.getMethod() == HttpMethod.GET) {
            result = buildUrlEncodingEntity(message.getRequestParams());
        } else if (type == RequestType.NORMAL) {
            result = buildUrlEncodingEntity(message.getRequestParams());
        } else if (type == RequestType.MULTIPART) {
            result = buildMultipartEntity(message.getRequestParams());
        } else if (type == RequestType.STREAM) {
            result = buildStreamEntity(message.getRequestStream());
        }
        return result;
    }

    /**
     * 构建Multipart/Form 请求体
     */
    private HttpEntity buildMultipartEntity(Map<String, Object> requestParams) {
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.setCharset(clientCharset);
        requestParams.entrySet().forEach((item) -> {
            Object value = item.getValue();
            if (value == null) {
                return;
            }
            if (File.class.isAssignableFrom(value.getClass())) {
                entityBuilder.addBinaryBody(item.getKey(), (File) value, ContentType.create(mimetypesFileTypeMap.getContentType((File) value)), ((File) value).getName());
            } else if (InputStream.class.isAssignableFrom(value.getClass())) {
                entityBuilder.addBinaryBody(item.getKey(), (InputStream) value);
            } else if (String.class.isAssignableFrom(value.getClass())) {
                entityBuilder.addTextBody(item.getKey(), (String) value, ContentType.create("text/plan", clientCharset));
            } else {
                entityBuilder.addTextBody(item.getKey(), value.toString(), ContentType.create("text/plan", clientCharset));
            }
        });
        return entityBuilder.build();
    }

    /**
     * 构建URL请求体
     */
    private UrlEncodedFormEntity buildUrlEncodingEntity(Map<String, Object> requestParams) {
        List<NameValuePair> params = new ArrayList<>(requestParams.size());
        requestParams.entrySet().forEach((item) -> params.add(new BasicNameValuePair(item.getKey(), Objects.toString(item.getValue(), ""))));
        return new UrlEncodedFormEntity(params, clientCharset);
    }

    /**
     * 构建流请求体
     */
    private InputStreamEntity buildStreamEntity(InputStream stream) {
        return new InputStreamEntity(stream, ContentType.create("application/octet-stream", clientCharset));
    }

    /**
     * 构建请求方法
     */
    private HttpRequestBase generateRequest(String url, HttpMethod method) {
        try {
            HttpRequestBase result = method.getMethodClass().newInstance();
            result.setURI(URI.create(url));
            return result;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RemoteException("无法创建[%s]的请求方法", e, method.name());
        }
    }

    /**
     * 构建自定义证书配置
     */
    private SSLConnectionSocketFactory getSSLFactory(SSLConfig sslConfig) {
        try {
            KeyStore trustStore = KeyStore.getInstance(sslConfig.getKeyStoreType().name());
            InputStream keyStream;
            if (sslConfig.getPath().contains("classpath:")) {
                keyStream = HttpClient.class.getResourceAsStream(sslConfig.getPath().replace("classpath:", ""));
            } else {
                keyStream = new FileInputStream(sslConfig.getPath());
            }
            trustStore.load(keyStream, sslConfig.getKey().toCharArray());
            keyStream.close();

            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
            return new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }
}

