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

    private MinaMessage(String clientId, String method, String data) {
        this.messageId = UUID.randomUUID().toString();
        this.clientId = clientId;
        this.method = method;
        this.data = data;
    }

    private final String messageId;

    private String clientId;

    private String method;//执行方法

    private String data;//交互数据

    private String sign;//签名

    private Long sendDate;//发送时间

    /**
     * 构建mina消息
     *
     * @param clientId 客户端id
     * @param method   执行方法
     * @param data     消息数据
     */
    public static MinaMessage createMinaMessage(String clientId, String method, String data) {
        return new MinaMessage(clientId, method, data);
    }

    public String getMessageId() {
        return messageId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getSendDate() {
        return sendDate;
    }

    public void setSendDate(Long sendDate) {
        this.sendDate = sendDate;
    }
}
