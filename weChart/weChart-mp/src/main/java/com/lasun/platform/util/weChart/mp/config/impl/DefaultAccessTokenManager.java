package com.lasun.platform.util.weChart.mp.config.impl;

import com.lasun.platform.util.weChart.mp.config.AccessTokenManager;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 默认token管理器
 */
public class DefaultAccessTokenManager implements AccessTokenManager {

    /**
     * 初始化数据
     */
    @Override
    public void init() {

    }

    /**
     * 刷新指定name值的token
     *
     * @param name 配置的name值
     */
    @Override
    public void refreshToken(String name) {

    }

    /**
     * 获得指定name值的token
     *
     * @param name 配置的name值
     */
    @Override
    public String getToken(String name) {
        return null;
    }

    /**
     * 设置指定name的token
     *
     * @param name  配置的name值
     * @param token token值
     */
    @Override
    public void setToken(String name, String token) {

    }
}
