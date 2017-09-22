package cn.irving.zhao.util.remote.mina;

import cn.irving.zhao.util.remote.mina.core.exception.MinaUtilException;
import cn.irving.zhao.util.remote.mina.core.method.MinaMessageMethodFactory;
import cn.irving.zhao.util.remote.mina.core.message.MinaMessageTransformFilter;
import cn.irving.zhao.util.remote.mina.core.handle.MinaServerHandler;
import cn.irving.zhao.util.remote.mina.core.keeper.MinaSessionKeeper;
import cn.irving.zhao.util.remote.mina.core.message.MinaMessageData;
import cn.irving.zhao.util.remote.mina.core.operator.client.MinaClientRegisterMethod;
import cn.irving.zhao.util.remote.mina.core.session.MinaSession;
import cn.irving.zhao.util.remote.mina.core.session.MinaSessionListener;
import cn.irving.zhao.util.remote.mina.core.sign.ClientHashSaltHolder;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * mina 工具类
 */
public class MinaUtil {

    public static final String MINA_CLIENT_KEY = "mina_client_key";

    protected static final Logger logger = LoggerFactory.getLogger(MinaUtil.class);

    /**
     * 连接或绑定地址
     */
    private String host;

    /**
     * 连接端口
     */
    private Integer port;

    /**
     * 是否需要签名
     */
    private Boolean needSign = false;

    /**
     * 签名使用的密钥信息，仅在needSign为true时使用
     */
    private ClientHashSaltHolder saltHolder;

    /**
     * 会话保存器
     */
    private MinaSessionKeeper sessionKeeper;

    /**
     * 方法执行器
     */
    private MinaMessageMethodFactory methodFactory;

    /**
     * 类型
     */
    private Type type = Type.SERVER;

    /**
     * 交互编码设置，默认为 utf8
     */
    private Charset charset = Charset.forName("UTF-8");

    private IoService service;

    private boolean isInit = false;

    /**
     * 初始化
     */
    public synchronized void init() {
        if (isInit) {
            return;
        }
        logger.info("mina 初始化");
        if (port == null || port < 1) {
            throw new MinaUtilException("[port]未指定");
        }
        if (sessionKeeper == null) {
            throw new MinaUtilException("[sessionKeeper]未定义");
        }
        if (methodFactory == null) {
            throw new MinaUtilException("[methodFactory]未定义");
        }
        if (needSign) {
            if (saltHolder == null) {
                throw new MinaUtilException("签名模式下，需定义[saltHolder]");
            }
        }
        try {
            InetAddress address = null;
            if (type == Type.SERVER) {
                service = new NioSocketAcceptor();
                if (host != null && !host.trim().isEmpty()) {
                    address = InetAddress.getByName(host);
                }
                //服务器模式下，添加 client 注册 方法
                methodFactory.registerMethod(MINA_CLIENT_KEY, new MinaClientRegisterMethod(sessionKeeper));
            } else if (type == Type.CLIENT) {
                service = new NioSocketConnector();
                if (host == null || host.trim().isEmpty()) {
                    throw new MinaUtilException("客户端模式下[host]不能为空");
                }
                address = InetAddress.getByName(host);
            } else {
                throw new MinaUtilException("未知[type]值");
            }

            //设置编码格式化
            service.getFilterChain().addLast("code", new ProtocolCodecFilter(new TextLineCodecFactory(charset)));
            //消息转换器
            service.getFilterChain().addLast("messageFilter", new MinaMessageTransformFilter(needSign, saltHolder));
            service.getFilterChain().addLast("logger", new LoggingFilter());//设置logger
            //设置消息执行器
            service.setHandler(new MinaServerHandler(methodFactory));
            //会话监听器
            service.addListener(new MinaSessionListener(sessionKeeper, type));

            if (type == Type.SERVER) {
                ((IoAcceptor) service).bind(new InetSocketAddress(address, port));
            } else if (type == Type.CLIENT) {
                ConnectFuture future = ((IoConnector) service).connect(new InetSocketAddress(address, port));
                future.awaitUninterruptibly();
                IoSession session = future.getSession();
                sessionKeeper.addMinaSession(MINA_CLIENT_KEY, new MinaSession(session));
            }
            isInit = true;
        } catch (IOException | ClassCastException e) {
            throw new MinaUtilException("初始化异常", e);
        }
        logger.info("mina 初始化 结束");
    }

    /**
     * 发送数据
     *
     * @param minaMessageData 发送的数据
     */
    public void sendMessage(MinaMessageData minaMessageData) {
        String minaSessionName;
        MinaSession minaSession;
        if (type == Type.SERVER) {
            minaSessionName = minaMessageData.getClientId();
        } else if (type == Type.CLIENT) {
            minaSessionName = MINA_CLIENT_KEY;
        } else {
            throw new MinaUtilException("非法类型");
        }
        minaSession = sessionKeeper.getMinaSession(minaSessionName);
        if (minaSession == null) {
            throw new MinaUtilException("未找到[" + minaSessionName + "]会话");
        }
        minaSession.sendMessage(minaMessageData);//TODO 返回值
    }

    public void addFilter(String filterName, IoFilter filter) {
        service.getFilterChain().addLast(filterName, filter);
    }

    public void removeFilter(String filterName) {
        service.getFilterChain().remove(filterName);
    }

    public void addListener(IoServiceListener listener) {
        service.addListener(listener);
    }

    public void removeListener(IoServiceListener listener) {
        service.removeListener(listener);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Boolean getNeedSign() {
        return needSign;
    }

    public void setNeedSign(Boolean needSign) {
        this.needSign = needSign;
    }

    public MinaSessionKeeper getSessionKeeper() {
        return sessionKeeper;
    }

    public void setSessionKeeper(MinaSessionKeeper sessionKeeper) {
        this.sessionKeeper = sessionKeeper;
    }

    public MinaMessageMethodFactory getMethodFactory() {
        return methodFactory;
    }

    public void setMethodFactory(MinaMessageMethodFactory methodFactory) {
        this.methodFactory = methodFactory;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public ClientHashSaltHolder getSaltHolder() {
        return saltHolder;
    }

    public void setSaltHolder(ClientHashSaltHolder saltHolder) {
        this.saltHolder = saltHolder;
    }

    public boolean isInit() {
        return isInit;
    }

    public enum Type {
        SERVER, CLIENT;
    }


}
