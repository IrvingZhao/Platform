package cn.irving.zhao.util.remote.mina.core.handler;

import cn.irving.zhao.util.remote.mina.core.BaseMinaOperator;
import cn.irving.zhao.util.remote.mina.core.exception.MinaUtilException;
import cn.irving.zhao.util.remote.mina.core.message.MinaMessage;
import cn.irving.zhao.util.remote.mina.core.method.MinaMessageMethod;
import cn.irving.zhao.util.remote.mina.core.method.MinaMessageMethodFactory;
import cn.irving.zhao.util.remote.mina.core.serial.MinaMessageSerialExecutor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息处理器
 */
public class MinaMessageHandler extends IoHandlerAdapter {

    public MinaMessageHandler(MinaMessageMethodFactory methodFactory, MinaMessageSerialExecutor serialExecutor) {
        this.methodFactory = methodFactory;
        this.serialExecutor = serialExecutor;
    }

    protected final Logger logger = LoggerFactory.getLogger(BaseMinaOperator.class);

    private MinaMessageMethodFactory methodFactory;

    private MinaMessageSerialExecutor serialExecutor;

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

        if (MinaMessage.class.isInstance(message)) {
            logger.info("mina-executeMethod-start");
            MinaMessage minaMessage = (MinaMessage) message;
            MinaMessageMethod method = methodFactory.getMethod(minaMessage.getMethod());
            //TODO 变更 method 为null 时候的信息
            if (method == null) {
                throw new MinaUtilException("未找到[" + minaMessage.getMethod() + "]的方法");
            }
            Class<?> paramType = method.getDataType();
            Object param = serialExecutor.parse(minaMessage.getData(), paramType);
            Object result = method.execute(param);
            logger.info("mina-executeMethod-end");
            //TODO 在成对消息中，写入返回值
        }

    }
}
