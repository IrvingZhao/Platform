package cn.irving.zhao.util.remote.mina.core.session;

import cn.irving.zhao.util.remote.mina.core.message.MinaMessageData;
import org.apache.mina.core.session.IoSession;

/**
 * 会话保存器
 */
public class MinaSession {

    private IoSession session;

    public MinaSession(IoSession session) {
        this.session = session;
    }

    /**
     * 发送消息
     */
    public void sendMessage(MinaMessageData messageData) {
        session.write(messageData);
    }

}
