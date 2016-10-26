package com.lasun.platform.util.weChart.mp.config.impl;

import com.lasun.platform.util.weChart.mp.config.WeChartMpConfig;
import com.lasun.platform.util.weChart.mp.config.WeChartConfigManager;

import java.util.Map;

/**
 * Created by irving on 2016/10/17.
 */
public class DefaultWeChartConfigManager implements WeChartConfigManager {
    /**
     * 管理器初始化
     */
    @Override
    public void init() {

    }

    /**
     * 初始化/重新加载 微信配置信息
     */
    @Override
    public void loadWeChartConfig() {

    }

    /**
     * 获得指定name值的微信配置信息
     *
     * @param name name值
     */
    @Override
    public WeChartMpConfig getConfig(String name) {
        return null;
    }

    /**
     * 获得所有微信账户配置
     */
    @Override
    public Map<String, WeChartConfigManager> getConfigs() {
        return null;
    }
}
