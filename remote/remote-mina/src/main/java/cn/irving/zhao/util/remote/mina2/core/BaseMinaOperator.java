package cn.irving.zhao.util.remote.mina2.core;

import cn.irving.zhao.util.remote.mina2.core.filter.MinaMessageSerialFilter;
import cn.irving.zhao.util.remote.mina2.core.filter.MinaMessageSignFilter;
import cn.irving.zhao.util.remote.mina2.core.filter.MinaMessageWrapperFilter;
import cn.irving.zhao.util.remote.mina2.core.handler.MinaMessageHandler;
import cn.irving.zhao.util.remote.mina2.core.method.MinaMessageMethodFactory;
import cn.irving.zhao.util.remote.mina2.core.serial.MinaMessageSerialExecutor;
import cn.irving.zhao.util.remote.mina2.core.sign.ClientHashSaltHolder;
import cn.irving.zhao.util.remote.mina2.core.sign.MinaMessageSignExecutor;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoService;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;

import java.nio.charset.Charset;

public abstract class BaseMinaOperator {

    public static final String CODEC_FILTER_NAME = "mina_codec";

    public static final String SERIAL_FILTER_NAME = "mina_serial";

    public static final String SIGN_FILTER_NAME = "mina_sign";

    public static final String MESSAGE_WRAPPER_FILTER_NAME = "mina_message_wrapper";

    public static final String CLIENT_AUTH_METHOD_NAME = "client_auth";

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

    protected Charset charset = Charset.forName("UTF-8");//编码

    protected void init() {

        DefaultIoFilterChainBuilder filterChainBuilder = new DefaultIoFilterChainBuilder();

        IoService ioService = getService();

        filterChainBuilder.addLast(CODEC_FILTER_NAME, new ProtocolCodecFilter(new TextLineCodecFactory(charset)));
        if (serialExecutor != null) {
            filterChainBuilder.addLast(SERIAL_FILTER_NAME, new MinaMessageSerialFilter(serialExecutor));
            if (saltHolder != null && signExecutor != null) {
                filterChainBuilder.addLast(SIGN_FILTER_NAME, new MinaMessageSignFilter(saltHolder, signExecutor));
            }
            filterChainBuilder.addLast(MESSAGE_WRAPPER_FILTER_NAME, new MinaMessageWrapperFilter(serialExecutor));
            if (ioService.getHandler() == null) {
                if (methodFactory != null) {
                    ioService.setHandler(new MinaMessageHandler(methodFactory));
                }
            }
        }

        ioService.getFilterChain().getAll().forEach(item -> {
            filterChainBuilder.addLast(item.getName(), item.getFilter());
        });

        ioService.setFilterChainBuilder(filterChainBuilder);

        start();

    }

    protected abstract IoService getService();

    protected abstract void start();

}
