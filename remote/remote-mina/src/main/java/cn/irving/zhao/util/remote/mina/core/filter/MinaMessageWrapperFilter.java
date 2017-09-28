package cn.irving.zhao.util.remote.mina.core.filter;

import cn.irving.zhao.util.remote.mina.core.message.MinaMessage;
import cn.irving.zhao.util.remote.mina.core.serial.MinaMessageSerialExecutor;
import cn.irving.zhao.util.remote.mina.core.message.MinaMessageData;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.DefaultWriteRequest;
import org.apache.mina.core.write.WriteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息发送封装过滤器
 */
public class MinaMessageWrapperFilter extends IoFilterAdapter {

    private final Logger logger = LoggerFactory.getLogger(MinaMessageWrapperFilter.class);

    public MinaMessageWrapperFilter(MinaMessageSerialExecutor serialExecutor) {
        this.serialExecutor = serialExecutor;
    }

    private MinaMessageSerialExecutor serialExecutor;

    @Override
    public void filterWrite(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
        Object message = writeRequest.getMessage();
        if (MinaMessageData.class.isInstance(message)) {
            MinaMessageData serverMessage = (MinaMessageData) message;
            Object data = serverMessage.getData();
            MinaMessage minaMessage = MinaMessage.createMinaMessage(serverMessage.getClientId(), serverMessage.getMethod(), serialExecutor.serial(data));
            minaMessage.setSendDate(System.currentTimeMillis());
            logger.info("mina-writeWrapper-success-{}" + minaMessage.getMessageId());
            nextFilter.filterWrite(session, new DefaultWriteRequest(minaMessage));
        }
    }

}
