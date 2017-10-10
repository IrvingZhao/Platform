package cn.irving.zhao.util.remote.mina.core;

import cn.irving.zhao.util.remote.mina.client.MinaClient;
import cn.irving.zhao.util.remote.mina.core.filter.*;
import cn.irving.zhao.util.remote.mina.core.handler.MinaMessageHandler;
import cn.irving.zhao.util.remote.mina.core.message.MinaMessage;
import cn.irving.zhao.util.remote.mina.core.method.MinaMessageMethodFactory;
import cn.irving.zhao.util.remote.mina.core.paired.PairedMessageLock;
import cn.irving.zhao.util.remote.mina.core.serial.MinaMessageSerialExecutor;
import cn.irving.zhao.util.remote.mina.core.sign.ClientHashSaltHolder;
import cn.irving.zhao.util.remote.mina.core.sign.MinaMessageSignExecutor;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoService;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseMinaOperator {

    protected final Logger logger = LoggerFactory.getLogger(BaseMinaOperator.class);

    public static final String CODEC_FILTER_NAME = "mina_codec";

    public static final String SERIAL_FILTER_NAME = "mina_serial";

    public static final String SIGN_FILTER_NAME = "mina_sign";

    public static final String MESSAGE_WRAPPER_FILTER_NAME = "mina_message_wrapper";

    public static final String CLIENT_AUTH_METHOD_NAME = "client_auth";

    public static final String MESSAGE_VALID_FILTER_NAME = "message_valid";

    public static final String MESSAGE_PAIRED_RESULT_METHOD_NAME = "message_paired_result";

    public static final String MESSAGE_PAIRED_RESULT_FILTER_NAME = "message_paired_filter";

    /**
     * 执行方法工厂
     * <p>只有在 {@link BaseMinaOperator#serialExecutor} 存在时才会启用</p>
     */
    protected MinaMessageMethodFactory methodFactory;// 方法执行器

    /**
     * 对象序列化执行器
     */
    protected MinaMessageSerialExecutor serialExecutor;

    /**
     * 签名 盐 管理器
     * <p>与 {@link BaseMinaOperator#signExecutor} 同时使用</p>
     * <p>只有在 {@link BaseMinaOperator#serialExecutor} 存在时才会启用</p>
     */
    protected ClientHashSaltHolder saltHolder;

    /**
     * 签名执行器
     * <p>与 {@link BaseMinaOperator#saltHolder} 同时使用</p>
     * <p>只有在 {@link BaseMinaOperator#serialExecutor} 存在时才会启用</p>
     */
    protected MinaMessageSignExecutor signExecutor;

    /**
     * 开启消息过滤模式，可避免同一条消息被执行多次，默认开启
     * <p>只有在 {@link BaseMinaOperator#serialExecutor} 存在时才会启用</p>
     */
    private Boolean enableMessageFilter = true;

    /**
     * 消息过期时间
     * <p>只有在 {@link MinaClient#enableMessageFilter} 为true时才会有效</p>
     */
    private Long messageExpireTime = 2 * 60 * 1000L;

    /**
     * 消息执行器，为空时，则使用{@link MinaMessageHandler}
     * <p>{@link MinaMessageHandler} 只有在 {@link #serialExecutor} 不为空 时才会有效</p>
     */
    private IoHandler messageHandle;

    protected Charset charset = Charset.forName("UTF-8");//编码

    private Map<String, PairedMessageLock> messageLockMap = new ConcurrentHashMap<>();//pairedMessageLock

    protected void init() {
        logger.info("mina-base-start");

        DefaultIoFilterChainBuilder filterChainBuilder = new DefaultIoFilterChainBuilder();

        IoService ioService = getService();

        logger.info("mina-base-addCodeFilter");
        filterChainBuilder.addLast(CODEC_FILTER_NAME, new ProtocolCodecFilter(new TextLineCodecFactory(charset)));
        if (serialExecutor != null) {
            logger.info("mina-base-addSerialFilter");
            filterChainBuilder.addLast(SERIAL_FILTER_NAME, new MinaMessageSerialFilter(serialExecutor));//序列化过滤器
            if (saltHolder != null && signExecutor != null) {//签名过滤器
                logger.info("mina-base-addSignFilter");
                filterChainBuilder.addLast(SIGN_FILTER_NAME, new MinaMessageSignFilter(saltHolder, signExecutor));
            }
            if (enableMessageFilter) {//消息是否有效的过滤器
                logger.info("mina-base-addMessageFilter");
                ioService.getFilterChain().addLast(MESSAGE_VALID_FILTER_NAME, new MinaMessageFilter(messageExpireTime));
            }
            if (messageHandle == null) {
                if (methodFactory != null) {
                    logger.info("mina-base-setDefaultHandler");
                    ioService.setHandler(new MinaMessageHandler(methodFactory, serialExecutor));
                }
            }
            filterChainBuilder.addLast(MESSAGE_PAIRED_RESULT_FILTER_NAME, new MinaMessagePairedResponseFilter(messageLockMap, serialExecutor));
        }
        if (messageHandle != null) {
            logger.info("mina-base-setCustomHandler");
            ioService.setHandler(messageHandle);
        }
        logger.info("mina-base-addCustomFilter");
        ioService.getFilterChain().getAll().forEach(item -> {
            filterChainBuilder.addLast(item.getName(), item.getFilter());
        });

        ioService.setFilterChainBuilder(filterChainBuilder);
        logger.info("mina-base-starting");
        start();
        logger.info("mina-base-success");

    }

    protected abstract IoService getService();

    protected abstract void start();

    protected abstract void sendMessage(MinaMessage minaMessage);

    protected <T> T sendPairedMessage(MinaMessage minaMessage, Class<T> resultType) {
        PairedMessageLock<T> messageResult = getLock(minaMessage.getPairedId(), resultType);
        synchronized (messageResult) {
            this.sendMessage(minaMessage);
            try {
                messageResult.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (messageResult.getException() == null) {
                return messageResult.getResult();
            } else {
                throw messageResult.getException();
            }
        }
    }

    /**
     * 获得成对消息锁
     *
     * @param pairedId   消息对id
     * @param resultType 消息返回值类型
     */
    protected <T> PairedMessageLock<T> getLock(String pairedId, Class<T> resultType) {
        PairedMessageLock<T> result;
        if ((result = messageLockMap.get(pairedId)) == null) {
            result = new PairedMessageLock<T>(resultType);
            messageLockMap.put(pairedId, result);
        }
        return result;
    }

    public Boolean getEnableMessageFilter() {
        return enableMessageFilter;
    }

    public BaseMinaOperator setEnableMessageFilter(Boolean enableMessageFilter) {
        this.enableMessageFilter = enableMessageFilter;
        return this;
    }

    public Long getMessageExpireTime() {
        return messageExpireTime;
    }

    public BaseMinaOperator setMessageExpireTime(Long messageExpireTime) {
        this.messageExpireTime = messageExpireTime;
        return this;
    }

    public MinaMessageMethodFactory getMethodFactory() {
        return methodFactory;
    }

    public void setMethodFactory(MinaMessageMethodFactory methodFactory) {
        this.methodFactory = methodFactory;
    }

    public MinaMessageSerialExecutor getSerialExecutor() {
        return serialExecutor;
    }

    public void setSerialExecutor(MinaMessageSerialExecutor serialExecutor) {
        this.serialExecutor = serialExecutor;
    }

    public ClientHashSaltHolder getSaltHolder() {
        return saltHolder;
    }

    public void setSaltHolder(ClientHashSaltHolder saltHolder) {
        this.saltHolder = saltHolder;
    }

    public MinaMessageSignExecutor getSignExecutor() {
        return signExecutor;
    }

    public void setSignExecutor(MinaMessageSignExecutor signExecutor) {
        this.signExecutor = signExecutor;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public IoHandler getMessageHandle() {
        return messageHandle;
    }

    public void setMessageHandle(IoHandler messageHandle) {
        this.messageHandle = messageHandle;
    }
}
