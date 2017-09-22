package cn.irving.zhao.util.remote.mina.core.message;

/**
 * mina 交互数据
 */
public interface MinaMessageData {

    String getClientId();

    void setClientId(String clientId);

    String getMethod();

    void setMethod(String method);

}
