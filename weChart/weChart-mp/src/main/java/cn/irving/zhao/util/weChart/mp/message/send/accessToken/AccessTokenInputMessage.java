package cn.irving.zhao.util.weChart.mp.message.send.accessToken;

import cn.irving.zhao.util.weChart.base.config.message.WeChartMessage;
import cn.irving.zhao.util.weChart.base.message.send.BaseSendInputMessage;
import cn.irving.zhao.util.weChart.base.message.send.BaseSendOutputMessage;

/**
 * Created by irving on 2016/8/12.
 */
@WeChartMessage
public class AccessTokenInputMessage extends BaseSendInputMessage {

    private String access_token;
    private String expires_in;

    @Override
    public Class<? extends BaseSendOutputMessage> getOutputMessageClass() {
        return AccessTokenOutputMessage.class;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }
}
