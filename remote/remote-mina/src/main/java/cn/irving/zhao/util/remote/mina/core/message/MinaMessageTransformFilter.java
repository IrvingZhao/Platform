package cn.irving.zhao.util.remote.mina.core.message;

import cn.irving.zhao.util.base.security.CryptoUtils;
import cn.irving.zhao.util.base.serial.ObjectStringSerialUtil;
import cn.irving.zhao.util.remote.mina.core.sign.ClientHashSaltHolder;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.DefaultWriteRequest;
import org.apache.mina.core.write.WriteRequest;

public class MinaMessageTransformFilter extends IoFilterAdapter {

    private static final ObjectStringSerialUtil SERIAL_UTIL = ObjectStringSerialUtil.getSerialUtil();

    private Boolean needSign;

    private ClientHashSaltHolder saltHolder;

    public MinaMessageTransformFilter() {
        this(false, null);
    }

    public MinaMessageTransformFilter(Boolean needSign, ClientHashSaltHolder saltHolder) {
        this.needSign = needSign;
        this.saltHolder = saltHolder;
    }

    @Override
    public void filterWrite(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
        //TODO 写入
        Object requestMessage = writeRequest.getMessage();
        if (MinaMessageData.class.isInstance(requestMessage)) {
            MinaMessage minaMessage = MinaMessage.createMinaMessage(needSign, (MinaMessageData) requestMessage);
            String transData = SERIAL_UTIL.serial(requestMessage, ObjectStringSerialUtil.SerialType.JSON);
            minaMessage.setSendDate(System.currentTimeMillis());
            minaMessage.setTransData(transData);
            if (needSign) {
                String salt = this.saltHolder.getSalt(minaMessage.getClientId());
                minaMessage.setSign(CryptoUtils.getHash(transData, salt));//设置sign
            }
            //调用下一个filter
            nextFilter.filterWrite(session, new DefaultWriteRequest(SERIAL_UTIL.serial(minaMessage, ObjectStringSerialUtil.SerialType.JSON)));
        }

    }

    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
        MinaMessage minaMessage = SERIAL_UTIL.parse(String.valueOf(message), MinaMessage.class, ObjectStringSerialUtil.SerialType.JSON);
        if (minaMessage.getNeedSign()) {
            String salt = this.saltHolder.getSalt(minaMessage.getClientId());
            if (!CryptoUtils.verify(minaMessage.getSign(), minaMessage.getTransData(), salt)) {
                return;
            }
        }
        Object messageData = SERIAL_UTIL.parse(minaMessage.getTransData(), Class.forName(minaMessage.getDataType()), ObjectStringSerialUtil.SerialType.JSON);
        nextFilter.messageReceived(session, messageData);//调用下一个filter
    }
}
