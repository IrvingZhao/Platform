package cn.irving.zhao.util.remote.mina2.core.filter;

import cn.irving.zhao.util.remote.mina2.core.message.MinaMessage;
import cn.irving.zhao.util.remote.mina2.core.sign.ClientHashSaltHolder;
import cn.irving.zhao.util.remote.mina2.core.sign.MinaMessageSignExecutor;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;

public class MinaMessageSignFilter extends IoFilterAdapter {

    public MinaMessageSignFilter(ClientHashSaltHolder saltHolder, MinaMessageSignExecutor signExecutor) {
        this.saltHolder = saltHolder;
        this.signExecutor = signExecutor;
    }

    private ClientHashSaltHolder saltHolder;

    private MinaMessageSignExecutor signExecutor;

    @Override
    public void filterWrite(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
        Object message = writeRequest.getMessage();
        if (MinaMessage.class.isInstance(message)) {// 如果是 mina message，写入sign
            MinaMessage minaMessage = (MinaMessage) message;
            String salt = saltHolder.getSalt(minaMessage.getClientId());
            minaMessage.setSign(signExecutor.getMessageSign(minaMessage.getData(), salt));
            super.filterWrite(nextFilter, session, writeRequest);
        }
    }

    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
        if (MinaMessage.class.isInstance(message)) {// 如果是 mina message，校验sign
            MinaMessage minaMessage = (MinaMessage) message;
            String salt = saltHolder.getSalt(minaMessage.getClientId());
            if (signExecutor.validMessage(minaMessage.getData(), minaMessage.getSign(), salt)) {//校验通过，执行下一个过滤器
                nextFilter.messageReceived(session, message);
            }
        }
    }

}
