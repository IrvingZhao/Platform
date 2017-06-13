package cn.irving.zhao.util.weChart.base.config.message;

import cn.irving.zhao.util.weChart.base.config.enums.WeChartMessageFormat;
import cn.irving.zhao.util.weChart.base.config.enums.WeChartMessageRequestMethod;

/**
 * <p>微信消息体配置信息</p>
 *
 * @author Irving.Zhao
 * @version 1.0
 * @since 1.0
 */
public class WeChartMessageConfig {
    private final boolean isSecurity;
    private final WeChartMessageRequestMethod requestMethod;
    private final WeChartMessageFormat requestType;
    private final WeChartMessageFormat responseType;

    /**
     * 根据请求输出类获得bean类配置
     */
    public WeChartMessageConfig(WeChartMessage weChartMessage) {
        if (weChartMessage == null) {
            isSecurity = false;
            requestMethod = WeChartMessageRequestMethod.GET;
            requestType = WeChartMessageFormat.FORM;
            responseType = WeChartMessageFormat.JSON;
        } else {
            isSecurity = weChartMessage.isSecurity();
            requestMethod = weChartMessage.requestMethod();
            requestType = weChartMessage.requestType();
            responseType = weChartMessage.responseType();
        }
    }

    public boolean isSecurity() {
        return isSecurity;
    }

    public WeChartMessageRequestMethod getRequestMethod() {
        return requestMethod;
    }

    public WeChartMessageFormat getRequestType() {
        return requestType;
    }

    public WeChartMessageFormat getResponseType() {
        return responseType;
    }
}
