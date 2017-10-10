package cn.irving.zhao.util.remote.mina.core.filter;

import cn.irving.zhao.util.remote.mina.core.BaseMinaOperator;
import cn.irving.zhao.util.remote.mina.core.exception.MinaUtilException;
import cn.irving.zhao.util.remote.mina.core.message.MinaMessage;
import cn.irving.zhao.util.remote.mina.core.paired.PairedMessageLock;
import cn.irving.zhao.util.remote.mina.core.serial.MinaMessageSerialExecutor;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;

import java.util.Map;

/**
 * 成对消息接收响应的过滤器
 */
public class MinaMessagePairedResponseFilter extends IoFilterAdapter {

    public MinaMessagePairedResponseFilter(Map<String, PairedMessageLock> messageLockMap, MinaMessageSerialExecutor serialExecutor) {
        this.messageLockMap = messageLockMap;
        this.serialExecutor = serialExecutor;
    }

    private Map<String, PairedMessageLock> messageLockMap;

    private MinaMessageSerialExecutor serialExecutor;

    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {

        if (MinaMessage.class.isInstance(message)) {
            MinaMessage minaMessage = (MinaMessage) message;
            if (BaseMinaOperator.MESSAGE_PAIRED_RESULT_METHOD_NAME.equals(minaMessage.getMethod())) {
                PairedMessageLock<Object> messageLock = messageLockMap.remove(minaMessage.getPairedId());
                if (messageLock != null) {
                    synchronized (messageLock) {

                        if (minaMessage.getErrorId() != null) {
                            messageLock.setException(new MinaUtilException("服务器出现异常，异常id：" + minaMessage.getErrorId() + "，异常信息：" + minaMessage.getErrorMessage()));
                        } else {
                            Object result = serialExecutor.parse(minaMessage.getData(), messageLock.getResultType());
                            messageLock.setResult(result);
                        }
                        messageLock.notifyAll();//通知所有等待线程启动
                        return;
                    }
                }
            }
        }
        nextFilter.messageReceived(session, message);
    }
}
