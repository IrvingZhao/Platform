package cn.irving.zhao.util.weChart.mp.message.send;

import com.dianpu.platform.tools.wechart.base.message.send.BaseSendInputMessage;

/**
 * 公众号-发送消息-输入消息 基础类
 */
public abstract class BaseMpSendInputMessage<T extends BaseMpSendOutputMessage> extends BaseSendInputMessage<T> {

    private String errcode;

    private String errmsg;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
