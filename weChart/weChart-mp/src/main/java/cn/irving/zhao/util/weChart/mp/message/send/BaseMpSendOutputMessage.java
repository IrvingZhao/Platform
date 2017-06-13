package cn.irving.zhao.util.weChart.mp.message.send;

import com.dianpu.platform.tools.wechart.base.message.send.BaseSendOutputMessage;

/**
 * 公众号-发送消息-输出消息 基础类
 */
public abstract class BaseMpSendOutputMessage<T extends BaseMpSendInputMessage> extends BaseSendOutputMessage<T> {

    protected String accessToken;

    protected BaseMpSendOutputMessage(String token) {
        this.accessToken = token;
    }

}
