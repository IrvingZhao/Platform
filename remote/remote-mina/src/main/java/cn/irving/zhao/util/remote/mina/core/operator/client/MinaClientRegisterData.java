package cn.irving.zhao.util.remote.mina.core.operator.client;

import cn.irving.zhao.util.remote.mina.core.message.MinaMessageData;

public class MinaClientRegisterData implements MinaMessageData {

    private String clientId;

    private String method = "register-client";

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }
}
