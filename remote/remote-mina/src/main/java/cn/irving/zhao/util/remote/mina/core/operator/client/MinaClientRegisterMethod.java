package cn.irving.zhao.util.remote.mina.core.operator.client;

import cn.irving.zhao.util.remote.mina.core.keeper.MinaSessionKeeper;
import cn.irving.zhao.util.remote.mina.core.message.MinaMessageData;
import cn.irving.zhao.util.remote.mina.core.method.MinaMessageMethod;
import org.apache.mina.core.session.IoSession;

public class MinaClientRegisterMethod implements MinaMessageMethod<MinaMessageData, MinaMessageData> {

    public MinaClientRegisterMethod(MinaSessionKeeper sessionKeeper) {
        this.sessionKeeper = sessionKeeper;
    }

    private MinaSessionKeeper sessionKeeper;

    @Override
    public MinaMessageData execute(MinaMessageData data, IoSession session) {
        sessionKeeper.bindClientId(String.valueOf(session.getId()), data.getClientId());
        return null;
    }
}
