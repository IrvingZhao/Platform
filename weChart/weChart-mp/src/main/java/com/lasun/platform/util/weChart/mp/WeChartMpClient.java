package com.lasun.platform.util.weChart.mp;

import com.lasun.platform.util.weChart.mp.config.AccessTokenManager;
import com.lasun.platform.util.weChart.mp.config.WeChartConfigManager;
import com.lasun.platform.util.weChart.mp.config.impl.DefaultAccessTokenManager;
import com.lasun.platform.util.weChart.mp.config.impl.DefaultWeChartConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 微信公众号客户端
 * <p>初始化读取propertyPath中的配置文件内容，默认值为classpath:/conf/wx.client.properties</p>
 * <p>配置项包括</p>
 * <ul>
 * <li>wx.client.tokenManager   token管理器类全名，默认为：{@link DefaultAccessTokenManager}</li>
 * <li>wx.client.configManager  微信账号配置管理器类全名，默认为：{@link DefaultWeChartConfigManager}</li>
 * </ul>
 */
public final class WeChartMpClient {

    private Logger logger = LoggerFactory.getLogger(WeChartMpClient.class);
    /**
     * 微信配置文件
     */
    private String propertyPath = "/conf/wx.client.properties";

    private AccessTokenManager tokenManager;
    private WeChartConfigManager configManager;

    private Map<String, String> configProperties = new HashMap<>();

    //微信公众号配置文件
    //AccessTokenManager类
    //WeChartMpConfigManager类
    //构建manager对象，初始化配置信息、初始化token
    //  token刷新器的实现、配置以及默认实现

    /**
     * 初始化bean类、token对象
     */
    public void init() {

    }

    private void loadProperties() {
        //TODO 加载配置文件   读取配置项，设置默认值
        try {
            Properties properties = new Properties();
            properties.load(WeChartMpClient.class.getResourceAsStream(propertyPath));
            try {
                Class<?> tokenManagerClass = Class.forName(properties.getProperty("wx.client.tokenManager", "com.lasun.platform.util.weChart.mp.config.impl.DefaultAccessTokenManager"));
                if (AccessTokenManager.class.isAssignableFrom(tokenManagerClass)) {
                    this.tokenManager = (AccessTokenManager) tokenManagerClass.newInstance();
                }else{
                    logger.warn(tokenManagerClass.getName()+"不是AccessTokenManager的子类");
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

            Class<? extends WeChartConfigManager> configManagerClass = (Class<? extends WeChartConfigManager>) Class.forName(properties.getProperty("wx.client.configManager", "com.lasun.platform.util.weChart.mp.config.impl.DefaultWeChartConfigManager"));

        } catch (IOException e) {
            this.tokenManager = new DefaultAccessTokenManager();
            this.configManager = new DefaultWeChartConfigManager();
            logger.warn("客户端配置文件加载异常，启用默认配置", e);
        }
    }

}
