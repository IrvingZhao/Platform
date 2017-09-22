package cn.irving.zhao.util.remote.mina.core.method.impl;

import cn.irving.zhao.util.remote.mina.core.message.MinaMessageData;
import cn.irving.zhao.util.remote.mina.core.method.MinaMessageMethod;
import cn.irving.zhao.util.remote.mina.core.method.MinaMessageMethodFactory;

import java.util.HashMap;
import java.util.Map;

public class DefaultMinaMessageMethodFactory implements MinaMessageMethodFactory {

    private final Map<String, MinaMessageMethod<MinaMessageData, MinaMessageData>> methodCache = new HashMap<>();

    @Override
    public MinaMessageMethod<MinaMessageData, MinaMessageData> getMethod(String methodName) {
        return methodCache.get(methodName);
    }

    @Override
    public void registerMethod(String methodName, MinaMessageMethod<MinaMessageData, MinaMessageData> method) {
        methodCache.put(methodName, method);
    }
}
