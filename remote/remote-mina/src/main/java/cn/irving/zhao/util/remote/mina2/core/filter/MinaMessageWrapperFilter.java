package cn.irving.zhao.util.remote.mina2.core.filter;

import cn.irving.zhao.util.remote.mina2.core.message.MinaMessage;
import cn.irving.zhao.util.remote.mina2.core.serial.MinaMessageSerialExecutor;
import cn.irving.zhao.util.remote.mina2.core.message.MinaMessageData;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.DefaultWriteRequest;
import org.apache.mina.core.write.WriteRequest;

public class MinaMessageWrapperFilter extends IoFilterAdapter {

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
            nextFilter.filterWrite(session, new DefaultWriteRequest(minaMessage));
        }
    }

}
