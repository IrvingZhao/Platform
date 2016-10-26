package cn.irving.zhao.util.weChart.mp.config;

/**
 * Token管理器接口
 */
public interface AccessTokenManager {

    /**
     * 初始化数据
     */
    void init();

    /**
     * 刷新指定name值的token
     *
     * @param name 配置的name值
     */
    void refreshToken(String name);

    /**
     * 获得指定name值的token
     *
     * @param name 配置的name值
     */
    String getToken(String name);

    /**
     * 设置指定name的token
     *
     * @param name  配置的name值
     * @param token token值
     */
    void setToken(String name, String token);
}
