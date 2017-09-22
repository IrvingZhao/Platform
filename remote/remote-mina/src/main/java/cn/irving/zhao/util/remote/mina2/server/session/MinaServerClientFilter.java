package cn.irving.zhao.util.remote.mina2.server.session;

import cn.irving.zhao.util.remote.mina2.core.BaseMinaOperator;
import cn.irving.zhao.util.remote.mina2.core.message.MinaMessage;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * mina 客户端验证过滤器
 */
public class MinaServerClientFilter extends IoFilterAdapter {

    public MinaServerClientFilter(MinaClientHolder sessionHolder, Long clearTime) {
        this(Boolean.TRUE, sessionHolder, clearTime);
    }

    public MinaServerClientFilter(Boolean enableClientSign, MinaClientHolder sessionHolder, Long clearTime) {
        this.enableClientSign = enableClientSign;
        this.clientHolder = sessionHolder;
        //TODO 删除 enableClientSign 属性，此类实例化添加  则表示 开启
        //TODO clearTime 在 MinaServer 中进行设置
        if (enableClientSign) {
            sessionCleanTaskMap = new HashMap<>();
            timer = new Timer();
            sessionClientIdMapper = new HashMap<>();
            this.clearTime = clearTime;
        }
    }

    private Boolean enableClientSign;// 是否开启客户端校验

    private MinaClientHolder clientHolder;

    private Map<Long, String> sessionClientIdMapper;// session id 和 客户端之间的关联映射

    private Map<Long, TimerTask> sessionCleanTaskMap;// 未认证session 清理任务

    private Timer timer;

    private Long clearTime;//清理时间

    @Override
    public void sessionCreated(NextFilter nextFilter, IoSession session) throws Exception {
        if (enableClientSign) {
            TimerTask task = new SessionCleanTask(session);
            sessionCleanTaskMap.put(session.getId(), task);// 开启 会话超时认证
            timer.schedule(task, clearTime);
        } else {
            clientHolder.addClient(String.valueOf(session.getId()), new MinaClientModel(session));//使用sessionId 作为 session Key
        }
        nextFilter.sessionCreated(session);
    }

    @Override
    public void sessionClosed(NextFilter nextFilter, IoSession session) throws Exception {
        if (enableClientSign) {
            clientHolder.delSession(sessionClientIdMapper.get(session.getId()));
        } else {
            clientHolder.delSession(String.valueOf(session.getId()));
        }
    }

    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
        if (enableClientSign) {
            if (MinaMessage.class.isInstance(message)) {
                MinaMessage minaMessage = (MinaMessage) message;
                if (BaseMinaOperator.CLIENT_AUTH_METHOD_NAME.equals(minaMessage.getMethod())) {
                    TimerTask task = sessionCleanTaskMap.get(session.getId());
                    boolean result = true;
                    if (task != null) {
                        result = task.cancel();//停止 清理会话 任务
                    }
                    if (result) {//清理成功，执行客户端注册
                        MinaClientModel client = new MinaClientModel(session);
                        client.setClientId(minaMessage.getClientId());
                        client.setSign(true);
                        clientHolder.addClient(minaMessage.getClientId(), client);
                    }
                } else {
                    MinaClientModel client = clientHolder.getClient(minaMessage.getClientId());//获得客户端
                    if (client != null && client.getSign()) { //检查客户端是否存在以及客户端是否认证
                        nextFilter.messageReceived(session, message);
                    }
                }
            }
        } else {
            nextFilter.messageReceived(session, message);
        }

    }

    private final class SessionCleanTask extends TimerTask {

        private IoSession session;

        private SessionCleanTask(IoSession session) {
            this.session = session;
        }

        @Override
        public void run() {
            sessionCleanTaskMap.remove(session.getId());
            session.closeNow().awaitUninterruptibly();//立即关闭
        }
    }

    public Boolean getEnableClientSign() {
        return enableClientSign;
    }

    public MinaServerClientFilter setEnableClientSign(Boolean enableClientSign) {
        this.enableClientSign = enableClientSign;
        return this;
    }

    public MinaClientHolder getClientHolder() {
        return clientHolder;
    }

    public MinaServerClientFilter setClientHolder(MinaClientHolder clientHolder) {
        this.clientHolder = clientHolder;
        return this;
    }

    public Long getClearTime() {
        return clearTime;
    }

    public MinaServerClientFilter setClearTime(Long clearTime) {
        this.clearTime = clearTime;
        return this;
    }
}
