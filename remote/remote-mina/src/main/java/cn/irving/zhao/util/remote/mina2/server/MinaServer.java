package cn.irving.zhao.util.remote.mina2.server;

import cn.irving.zhao.util.remote.mina2.core.BaseMinaOperator;
import cn.irving.zhao.util.remote.mina2.core.exception.MinaUtilException;
import cn.irving.zhao.util.remote.mina2.core.method.MinaMessageMethodFactory;
import cn.irving.zhao.util.remote.mina2.core.serial.MinaMessageSerialExecutor;
import cn.irving.zhao.util.remote.mina2.core.sign.ClientHashSaltHolder;
import cn.irving.zhao.util.remote.mina2.core.sign.MinaMessageSignExecutor;
import cn.irving.zhao.util.remote.mina2.core.message.MinaMessageData;
import cn.irving.zhao.util.remote.mina2.server.session.MinaClientHolder;
import cn.irving.zhao.util.remote.mina2.server.session.MinaClientModel;
import cn.irving.zhao.util.remote.mina2.server.session.MinaServerClientFilter;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoService;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;

public class MinaServer extends BaseMinaOperator {

    public static final String CLIENT_SIGN_FILTER_NAME = "client_sign";

    public MinaServer(Integer port) {
        this.port = port;
    }

    public MinaServer(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 绑定的host
     */
    private String host;

    /**
     * 监听的端口
     */
    private Integer port;

    /**
     * 自定义消息处理器
     */
    private IoHandler serviceHandler;

    /**
     * 添加的额外过滤器
     */
    private LinkedHashMap<String, IoFilter> filters;

    /**
     * 会话保存器
     */
    private MinaClientHolder sessionHolder;

    /**
     * 开启客户端认证模式，默认开启
     * <p>只有在 {@link BaseMinaOperator#serialExecutor} 存在时才会启用</p>
     */
    private Boolean enableClientSign = true;

    private IoAcceptor service;

    @Override
    protected void init() {
        if (this.service == null) {
            this.service = new NioSocketAcceptor();
        }
        if (sessionHolder == null) {
            sessionHolder = new MinaClientHolder();
        }
        if (filters != null && !filters.isEmpty()) {
            filters.forEach(this.service.getFilterChain()::addLast);
        }
        if (serviceHandler != null) {
            this.service.setHandler(serviceHandler);
        }
        super.init();
        if (enableClientSign && super.serialExecutor != null) {
            this.service.getFilterChain().addBefore(MESSAGE_WRAPPER_FILTER_NAME, CLIENT_SIGN_FILTER_NAME, new MinaServerClientFilter(sessionHolder));
        }
    }

    @Override
    protected IoService getService() {
        return service;
    }

    @Override
    protected void start() {

        try {
            InetAddress address = null;

            if (host != null && !host.trim().isEmpty()) {
                address = InetAddress.getByName(host);
            }

            service.bind(new InetSocketAddress(address, port));
        } catch (IOException e) {
            throw new MinaUtilException("mina server 启动异常", e);
        }

    }

    /**
     * 发送消息
     *
     * @param clientId 接收消息的客户端的id，或对应的sessionId
     * @param method   客户端执行的方法
     * @param data     数据
     */
    public void sendMessage(String clientId, String method, Object data) {
        this.sendMessage(new MinaMessageDataDataWrapper(clientId, method, data));
    }

    /**
     * 给客户端发送消息
     *
     * @param message 消息对象
     */
    public void sendMessage(MinaMessageData message) {
        MinaClientModel client = sessionHolder.getClient(message.getClientId());
        client.sendMessage(message);
    }


    private static class MinaMessageDataDataWrapper implements MinaMessageData {
        MinaMessageDataDataWrapper(String clientId, String method, Object data) {
            this.clientId = clientId;
            this.method = method;
            this.data = data;
        }

        private final String clientId;
        private final String method;
        private final Object data;

        @Override
        public String getClientId() {
            return clientId;
        }

        @Override
        public String getMethod() {
            return method;
        }

        @Override
        public Object getData() {
            return data;
        }
    }


    public MinaClientHolder getSessionHolder() {
        return sessionHolder;
    }

    public MinaServer setSessionHolder(MinaClientHolder sessionHolder) {
        this.sessionHolder = sessionHolder;
        return this;
    }

    public String getHost() {
        return host;
    }

    public MinaServer setHost(String host) {
        this.host = host;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public MinaServer setPort(Integer port) {
        this.port = port;
        return this;
    }

    public IoHandler getServiceHandler() {
        return serviceHandler;
    }

    public MinaServer setServiceHandler(IoHandler serviceHandler) {
        this.serviceHandler = serviceHandler;
        return this;
    }

    public LinkedHashMap<String, IoFilter> getFilters() {
        return filters;
    }

    public MinaServer setFilters(LinkedHashMap<String, IoFilter> filters) {
        this.filters = filters;
        return this;
    }

    public MinaMessageMethodFactory getMethodFactory() {
        return methodFactory;
    }

    public MinaServer setMethodFactory(MinaMessageMethodFactory methodFactory) {
        super.methodFactory = methodFactory;
        return this;
    }

    public MinaMessageSerialExecutor getSerialExecutor() {
        return serialExecutor;
    }

    public MinaServer setSerialExecutor(MinaMessageSerialExecutor serialExecutor) {
        super.serialExecutor = serialExecutor;
        return this;
    }

    public ClientHashSaltHolder getSaltHolder() {
        return saltHolder;
    }

    public MinaServer setSaltHolder(ClientHashSaltHolder saltHolder) {
        super.saltHolder = saltHolder;
        return this;
    }

    public MinaMessageSignExecutor getSignExecutor() {
        return signExecutor;
    }

    public MinaServer setSignExecutor(MinaMessageSignExecutor signExecutor) {
        super.signExecutor = signExecutor;
        return this;
    }

    public Charset getCharset() {
        return charset;
    }

    public MinaServer setCharset(Charset charset) {
        super.charset = charset;
        return this;
    }


}
