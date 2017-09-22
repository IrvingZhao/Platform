package cn.irving.zhao.util.remote.mina2.core.filter;

import cn.irving.zhao.util.remote.mina2.core.message.MinaMessage;
import cn.irving.zhao.util.remote.mina2.core.serial.MinaMessageSerialExecutor;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.DefaultWriteRequest;
import org.apache.mina.core.write.WriteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinaMessageSerialFilter extends IoFilterAdapter {

    public MinaMessageSerialFilter(MinaMessageSerialExecutor serialExecutor) {
        this.serialExecutor = serialExecutor;
    }

    private final Logger logger = LoggerFactory.getLogger(MinaMessageSerialFilter.class);
    private MinaMessageSerialExecutor serialExecutor;

    @Override
    public void filterWrite(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
        Object requestMessage = writeRequest.getMessage();
        if (MinaMessage.class.isInstance(requestMessage)) {//如果是指定消息类型，则进行数据格式化后传输
            String data = serialExecutor.serial(requestMessage);
            nextFilter.filterWrite(session, new DefaultWriteRequest(data));
            logger.info("发送固定类型消息-{}-{}", session.getId(), data);
        }
    }

    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
        MinaMessage minaMessage = serialExecutor.parse(String.valueOf(message), MinaMessage.class);
        nextFilter.messageReceived(session, minaMessage);
        logger.info("接收固定类型消息-{}-{}", session.getId(), message);
    }
}
