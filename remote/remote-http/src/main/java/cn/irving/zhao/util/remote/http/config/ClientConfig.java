package cn.irving.zhao.util.remote.http.config;

import java.nio.charset.Charset;

/**
 * 客户端配置信息，包括：
 * <ul>
 * <li>SSL证书配置，默认无配置</li>
 * <li>字符集编码，默认UTF-8</li>
 * </ul>
 *
 * @author 赵嘉楠
 * @version 1.0
 * @since 1.0
 */
public class ClientConfig {
    private SSLConfig sslConfig;
    private Charset charset = Charset.forName("UTF-8");

    /***
     * 添加SSL证书配置信息
     * @param path 证书路径，支持classpath:
     * @param key 证书秘钥
     * @param keyStoreType 证书生成类型
     * */
    public ClientConfig setSSLConfig(String path, String key, KeyStoreType keyStoreType) {
        sslConfig = new SSLConfig(path, key, keyStoreType);
        return this;
    }

    /**
     * 设置字符集编码
     *
     * @param charsetName 字符集名称
     */
    public ClientConfig setCharset(String charsetName) {
        return this.setCharset(Charset.forName(charsetName));
    }

    /**
     * 设置字符集编码
     *
     * @param charset 编码对象
     */
    public ClientConfig setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    public SSLConfig getSSlConfig() {
        return sslConfig;
    }

    public Charset getCharset() {
        return charset;
    }
}
