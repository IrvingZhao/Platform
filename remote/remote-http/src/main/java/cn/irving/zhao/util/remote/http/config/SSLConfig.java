package cn.irving.zhao.util.remote.http.config;

/**
 * https自定义证书配置
 * 仅在HTTPS请求时有效
 *
 * @author 赵嘉楠
 * @version 1.0
 * @since 1.0
 */
public class SSLConfig {
    /**
     * 构建配置信息
     *
     * @param path         证书路径，支持classpath:
     * @param key          证书key
     * @param keyStoreType 证书生成类型
     * @see KeyStoreType
     */
    SSLConfig(String path, String key, KeyStoreType keyStoreType) {
        this.path = path;
        this.key = key;
        this.keyStoreType = keyStoreType;
    }

    private final String path;
    private final String key;
    private final KeyStoreType keyStoreType;

    public String getPath() {
        return path;
    }

    public String getKey() {
        return key;
    }

    public KeyStoreType getKeyStoreType() {
        return keyStoreType;
    }
}
