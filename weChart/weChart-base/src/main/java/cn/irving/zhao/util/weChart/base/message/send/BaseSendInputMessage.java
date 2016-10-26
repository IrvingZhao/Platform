package cn.irving.zhao.util.weChart.base.message.send;


import cn.irving.zhao.util.weChart.base.message.BaseInputMessage;

/**
 * 主动发送消息-基础输入消息
 *
 * @author Irving.Zhao
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseSendInputMessage extends BaseInputMessage {
    private String errcode;
    private String errmsg;

    public abstract Class<? extends BaseSendOutputMessage> getOutputMessageClass();


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
