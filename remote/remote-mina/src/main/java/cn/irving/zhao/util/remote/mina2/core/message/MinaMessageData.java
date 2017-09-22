package cn.irving.zhao.util.remote.mina2.core.message;

public interface MinaMessageData {

    String getClientId();

    String getMethod();

    Object getData();

}
