package cn.irving.zhao.util.remote;

import cn.irving.zhao.util.remote.message.RemoteMessage;

/**
 * <p>远程交互工具类</p>
 * <p>使用 sendMessage 方法发送远程交互消息</p>
 *
 * @author Irving.Zhao
 * @version 1.0
 * @since 1.0
 */
public interface RemoteClient<T extends RemoteMessage> {

    /**
     * 发送交互消息
     *
     * @param message 消息对象
     */
    void sendMessage(T message);
}
