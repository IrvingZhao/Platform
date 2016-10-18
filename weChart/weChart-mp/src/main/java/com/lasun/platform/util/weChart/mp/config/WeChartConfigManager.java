package com.lasun.platform.util.weChart.mp.config;

/**
 * 微信配置管理器
 */
public interface WeChartConfigManager {

    /**
     * 初始化/重新加载 微信配置信息
     */
    void loadWeChartConfig();

    /**
     * 获得指定name值的微信配置信息
     *
     * @param name name值
     */
    WeChartMpConfig getConfig(String name);

}
