package cn.irving.zhao.util.remote.mina2.core.handler;

import cn.irving.zhao.util.remote.mina2.core.exception.MinaUtilException;
import cn.irving.zhao.util.remote.mina2.core.method.MinaMessageMethod;
import cn.irving.zhao.util.remote.mina2.core.method.MinaMessageMethodFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class MinaMessageHandler extends IoHandlerAdapter {

    public MinaMessageHandler(MinaMessageMethodFactory methodFactory) {
        this.methodFactory = methodFactory;
    }

    private MinaMessageMethodFactory methodFactory;

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        //TODO 类型变更
//        MinaMessageMethod<MinaMessageData, MinaMessageData> method;
//        if (MinaMessageData.class.isInstance(message)) {
//            MinaMessageData messageData = (MinaMessageData) message;
//            method = methodFactory.getMethod(messageData.getMethod());
//            if (method == null) {
//                throw new MinaUtilException("未找到执行方法");
//            }
//            method.execute(messageData);//  执行
//        }
    }
}
