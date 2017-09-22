package cn.irving.zhao.util.remote.mina.core.keeper;

import cn.irving.zhao.util.remote.mina.core.session.MinaSession;
import org.apache.mina.core.session.IoSession;

/**
 * mina session 保存器
 */
public interface MinaSessionKeeper {

    /**
     * 根据客户端id获得session
     */
    MinaSession getMinaSession(String clientId);

    /**
     * 注册一个客户端的session
     */
    void addMinaSession(String clientId, MinaSession session);

    /**
     * 移除一个客户端session
     */
    void removeMinaSession(String clientId);

    /**
     * 给未绑定client的匿名session绑定clientId
     */
    void bindClientId(String sessionId, String clientId);

    /**
     * 添加一个未绑定client的匿名session
     */
    void addAnonymousSession(IoSession ioSession);

    /**
     * 根据sessionId 移除一个未绑定客户端的匿名session
     */
    void removeAnonymousSession(String sessionId);

}
