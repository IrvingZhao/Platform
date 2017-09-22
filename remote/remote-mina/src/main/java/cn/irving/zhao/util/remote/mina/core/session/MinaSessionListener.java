package cn.irving.zhao.util.remote.mina.core.session;

import cn.irving.zhao.util.remote.mina.MinaUtil;
import cn.irving.zhao.util.remote.mina.core.keeper.MinaSessionKeeper;
import cn.irving.zhao.util.remote.mina.core.operator.client.MinaClientRegisterData;
import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class MinaSessionListener implements IoServiceListener {

    public MinaSessionListener(MinaSessionKeeper sessionKeeper, MinaUtil.Type type) {
        this.sessionKeeper = sessionKeeper;
        this.type = type;
    }

    private MinaSessionKeeper sessionKeeper;
    private MinaUtil.Type type;

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        if (type == MinaUtil.Type.CLIENT) {
            sessionKeeper.addMinaSession(MinaUtil.MINA_CLIENT_KEY, new MinaSession(session));

//            MinaClientRegisterData registerClient = new MinaClientRegisterData();
//            registerClient.setClientId("1");//TODO 获得 clientId
//            session.write(registerClient);

        } else if (type == MinaUtil.Type.SERVER) {
            sessionKeeper.addAnonymousSession(session);
        }
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        if (type == MinaUtil.Type.CLIENT) {
            sessionKeeper.removeMinaSession(MinaUtil.MINA_CLIENT_KEY);
        } else if (type == MinaUtil.Type.SERVER) {
            //TODO session 销毁时，需销毁 对应的 minasession 对象
            sessionKeeper.removeAnonymousSession(String.valueOf(session.getId()));
        }
    }

    @Override
    public void sessionDestroyed(IoSession session) throws Exception {
        this.sessionClosed(session);
    }

    @Override
    public void serviceActivated(IoService service) throws Exception {

    }

    @Override
    public void serviceIdle(IoService service, IdleStatus idleStatus) throws Exception {

    }

    @Override
    public void serviceDeactivated(IoService service) throws Exception {

    }

}
