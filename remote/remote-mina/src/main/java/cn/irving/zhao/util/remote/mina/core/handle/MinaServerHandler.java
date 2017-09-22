package cn.irving.zhao.util.remote.mina.core.handle;

import cn.irving.zhao.util.base.serial.ObjectStringSerialUtil;
import cn.irving.zhao.util.remote.mina.core.message.MinaMessageData;
import cn.irving.zhao.util.remote.mina.core.method.MinaMessageMethod;
import cn.irving.zhao.util.remote.mina.core.method.MinaMessageMethodFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinaServerHandler extends IoHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(MinaServerHandler.class);

    private static final ObjectStringSerialUtil SERIAL_UTIL = ObjectStringSerialUtil.getSerialUtil();

    private MinaMessageMethodFactory methodFactory;

    public MinaServerHandler(MinaMessageMethodFactory methodFactory) {
        this.methodFactory = methodFactory;
    }

    public void messageReceived(IoSession session, Object message) throws Exception {
        if (MinaMessageData.class.isInstance(message)) {
            MinaMessageData messageData = (MinaMessageData) message;
            MinaMessageMethod<MinaMessageData, MinaMessageData> method = methodFactory.getMethod(messageData.getMethod());
            method.execute(messageData, session);
        }
    }

    public void messageSent(IoSession session, Object message) throws Exception {
        logger.info("message-sent:" + session.getId() + ":" + message);
    }

}
