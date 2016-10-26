package cn.irving.zhao.util.weChart.mp.config;

import cn.irving.zhao.util.weChart.mp.config.enums.WeChartMessageSecurityType;

/**
 * 微信配置实体类
 *
 * @author Irving.Zhao
 */
public final class WeChartMpConfig {

    private String appId;
    private String appSecurity;
    private String securityToken;
    private String encodingAesKey;
    private WeChartMessageSecurityType messageType;

    public WeChartMpConfig() {
    }

    /**
     * 初始化微信配置
     *
     * @param appId          微信配置项：appId
     * @param appSecurity    微信配置项：appSecurity
     * @param securityToken  微信配置项：securityToken
     * @param encodingAesKey 微信配置项：encodingAesKey
     * @param messageType    微信配置项：消息加密类型
     */
    public WeChartMpConfig(String appId, String appSecurity, String securityToken, String encodingAesKey, WeChartMessageSecurityType messageType) {
        this.appId = appId;
        this.appSecurity = appSecurity;
        this.securityToken = securityToken;
        this.encodingAesKey = encodingAesKey;
        this.messageType = messageType;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppSecurity() {
        return appSecurity;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public String getEncodingAesKey() {
        return encodingAesKey;
    }

    public WeChartMessageSecurityType getMessageType() {
        return messageType;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppSecurity(String appSecurity) {
        this.appSecurity = appSecurity;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public void setEncodingAesKey(String encodingAesKey) {
        this.encodingAesKey = encodingAesKey;
    }

    public void setMessageType(WeChartMessageSecurityType messageType) {
        this.messageType = messageType;
    }
}
