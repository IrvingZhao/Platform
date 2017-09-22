package cn.irving.zhao.util.remote.mina.core.method;

import cn.irving.zhao.util.remote.mina.core.message.MinaMessageData;
import org.apache.mina.core.session.IoSession;

/**
 * mina 消息方法
 */
@FunctionalInterface
public interface MinaMessageMethod<T extends MinaMessageData, R extends MinaMessageData> {

    R execute(T data, IoSession ioSession);

}
