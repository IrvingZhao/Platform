package modal;

import cn.irving.zhao.util.remote.mina.core.message.MinaMessageData;

public class DemoMessage implements MinaMessageData {

    public DemoMessage() {
    }

    public DemoMessage(String clientId, String method, String data) {
        this.clientId = clientId;
        this.method = method;
        this.data = data;
    }

    private String clientId;
    private String method;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
