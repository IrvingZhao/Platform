package cn.irving.zhao.util.weChart.mp.message.send.accessToken;


import cn.irving.zhao.util.weChart.base.config.message.WeChartMessage;
import cn.irving.zhao.util.weChart.base.message.send.BaseSendInputMessage;
import cn.irving.zhao.util.weChart.base.message.send.BaseSendOutputMessage;

/**
 * Created by irving on 2016/8/12.
 */
@WeChartMessage
public class AccessTokenOutputMessage extends BaseSendOutputMessage {

    private final String URL = "https://api.weixin.qq.com/cgi-bin/token";

    private final String appid;
    private final String secret;
    private final String grant_type = "client_credential";

    public AccessTokenOutputMessage(String appId, String appSecret) {
        this.appid = appId;
        this.secret = appSecret;
    }

    @Override
    public Class<? extends BaseSendInputMessage> getInputMessageClass() {
        return AccessTokenInputMessage.class;
    }

    @Override
    public String getUrl() {
        return URL;
    }

    public String getAppid() {
        return appid;
    }

    public String getSecret() {
        return secret;
    }

    public String getGrant_type() {
        return grant_type;
    }
}
