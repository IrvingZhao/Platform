package cn.irving.zhao.util.remote.mina.core.message;

import com.fasterxml.jackson.annotation.*;

import java.util.UUID;

/**
 * mina 交互消息
 */
public final class MinaMessage {

    @JsonCreator
    private MinaMessage(@JsonProperty("messageId") String messageId) {
        this.messageId = messageId;
    }

    private MinaMessage(String method, Boolean needSign, MinaMessageData data) {
        this.messageId = UUID.randomUUID().toString();
        this.clientId = data.getClientId();
        this.method = method;
        this.needSign = needSign;
        this.data = data;
        this.dataType = data.getClass().getName();
    }

    private final String messageId;

    private String clientId;

    private String method;//执行方法

    private Boolean needSign;//是否签名

    private String dataType;//数据class type

    @JsonIgnore
    private MinaMessageData data;//数据对象

    private String sign;//签名

    private Long sendDate;//发送时间

    private String transData;//交互数据

    /**
     * 构建mina消息
     *
     * @param needSign 是否签名
     * @param data     消息数据
     */
    public static MinaMessage createMinaMessage(Boolean needSign, MinaMessageData data) {
        return new MinaMessage(data.getMethod(), needSign, data);
    }

    public String getMessageId() {
        return messageId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Boolean getNeedSign() {
        return needSign;
    }

    public void setNeedSign(Boolean needSign) {
        this.needSign = needSign;
    }

    public MinaMessageData getData() {
        return data;
    }

    public void setData(MinaMessageData data) {
        this.data = data;
        this.dataType = data.getClass().getName();
    }

    //=================================================== Jackson 属性注入使用

    @JsonGetter
    String getDataType() {
        return dataType;
    }

    @JsonSetter
    void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @JsonGetter
    String getSign() {
        return sign;
    }

    @JsonSetter
    void setSign(String sign) {
        this.sign = sign;
    }

    @JsonGetter
    Long getSendDate() {
        return sendDate;
    }

    @JsonSetter
    void setSendDate(Long sendDate) {
        this.sendDate = sendDate;
    }

    @JsonGetter
    String getTransData() {
        return transData;
    }

    @JsonSetter
    void setTransData(String transData) {
        this.transData = transData;
    }

    @JsonGetter
    String getClientId() {
        return clientId;
    }

    @JsonSetter
    void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
