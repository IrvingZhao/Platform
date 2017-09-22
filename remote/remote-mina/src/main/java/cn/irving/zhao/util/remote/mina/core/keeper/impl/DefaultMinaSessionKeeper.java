package cn.irving.zhao.util.remote.mina.core.keeper.impl;

import cn.irving.zhao.util.remote.mina.core.keeper.MinaSessionKeeper;
import cn.irving.zhao.util.remote.mina.core.session.MinaSession;
import org.apache.mina.core.session.IoSession;

import java.util.HashMap;
import java.util.Map;

public class DefaultMinaSessionKeeper implements MinaSessionKeeper {

    private static Map<String, IoSession> anonymousSessionCache = new HashMap<>();//匿名session保存位置

    private static Map<String, MinaSession> clientSessionCache = new HashMap<>();//指定client的session保存位置

    @Override
    public MinaSession getMinaSession(String clientId) {
        if (clientId == null || clientId.trim().isEmpty()) {
            return null;
        }
        //TODO hack 做default设置
        return clientSessionCache.getOrDefault(clientId, new MinaSession(anonymousSessionCache.get(clientId)));
    }

    @Override
    public void addMinaSession(String clientId, MinaSession session) {
        clientSessionCache.put(clientId, session);
    }

    @Override
    public void removeMinaSession(String clientId) {
        clientSessionCache.remove(clientId);
    }

    @Override
    public void bindClientId(String sessionId, String clientId) {
        IoSession ioSession = anonymousSessionCache.remove(sessionId);
        if (ioSession != null) {
            MinaSession minaSession = new MinaSession(ioSession);
            clientSessionCache.put(clientId, minaSession);
        }
    }

    @Override
    public void addAnonymousSession(IoSession ioSession) {
        anonymousSessionCache.put(String.valueOf(ioSession.getId()), ioSession);
    }

    @Override
    public void removeAnonymousSession(String sessionId) {
        anonymousSessionCache.remove(sessionId);
    }
}
