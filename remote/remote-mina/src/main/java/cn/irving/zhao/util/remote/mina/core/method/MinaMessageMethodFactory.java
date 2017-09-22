package cn.irving.zhao.util.remote.mina.core.method;

import cn.irving.zhao.util.remote.mina.core.message.MinaMessageData;

/**
 * mina 消息执行器 工厂
 */
public interface MinaMessageMethodFactory {

    MinaMessageMethod<MinaMessageData, MinaMessageData> getMethod(String methodName);

    void registerMethod(String methodName, MinaMessageMethod<MinaMessageData, MinaMessageData> method);

}
