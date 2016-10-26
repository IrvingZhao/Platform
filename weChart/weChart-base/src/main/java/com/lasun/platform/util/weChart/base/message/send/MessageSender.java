package com.lasun.platform.util.weChart.base.message.send;


import cn.irving.zhao.util.base.serial.SerialUtil;
import cn.irving.zhao.util.remote.http.HttpClient;
import cn.irving.zhao.util.remote.http.config.HttpMethod;
import cn.irving.zhao.util.remote.http.message.HttpMessage;
import com.lasun.platform.util.weChart.base.config.enums.WeChartMessageFormat;
import com.lasun.platform.util.weChart.base.config.message.WeChartMessageConfig;

import java.io.InputStream;

/**
 * 消息发送器
 *
 * @author Irving.Zhao
 * @version 1.0
 * @since 1.0
 */
public class MessageSender {
    private SerialUtil serialUtil = SerialUtil.getSerialUtil();
    private HttpClient httpUtil = new HttpClient();

    public BaseSendInputMessage sendMessage(BaseSendOutputMessage outputMessage) {
        WeChartMessageConfig messageConfig = outputMessage.getMessageConfig();
        HttpMessage httpMessage = new HttpMessage(outputMessage.getUrl());


        switch (messageConfig.getRequestMethod()) {
            case GET:
                httpMessage.setMethod(HttpMethod.GET);
                break;
            case POST:
                httpMessage.setMethod(HttpMethod.POST);
                break;
        }
        WeChartMessageFormat requestFormat = messageConfig.getRequestType();
        switch (requestFormat) {
            case FORM:
                httpMessage.addParameters(outputMessage.getParamMap());
                break;
            case XML:
                httpMessage.setRequestStream(serialUtil.serial(outputMessage, SerialUtil.SerialType.XML));
                break;
            case JSON:
                httpMessage.setRequestStream(serialUtil.serial(outputMessage, SerialUtil.SerialType.JSON));
                break;
        }
        httpUtil.sendMessage(httpMessage);
        InputStream responseStream = httpMessage.getResponseStream();
        SerialUtil.SerialType serialType = SerialUtil.SerialType.JSON;
        switch (messageConfig.getResponseType()) {
            case JSON:
                serialType = SerialUtil.SerialType.JSON;
                break;
            case XML:
                serialType = SerialUtil.SerialType.XML;
                break;
        }
        Class<? extends BaseSendInputMessage> outputClass = outputMessage.getInputMessageClass();
        BaseSendInputMessage result = serialUtil.parse(responseStream, outputClass, serialType);
        return result;
    }
}