package com.lasun.platform.util.weChart.base.message;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lasun.platform.util.base.serial.SerialUtil;
import com.lasun.platform.util.weChart.base.config.message.WeChartMessageConfig;
import com.lasun.platform.util.weChart.base.config.enums.WeChartMessageFormat;

/**
 * <p>基础输出消息</p>
 *
 * @author Irving.Zhao
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseOutputMessage extends BaseMessage {

    @JsonIgnore
    public abstract Class<? extends BaseInputMessage> getInputMessageClass();

    private final WeChartMessageConfig messageConfig;

    protected BaseOutputMessage() {
        messageConfig = new WeChartMessageConfig(this);
    }

    @JsonIgnore
    public String getSerialContent() {
        String result = null;
        if (messageConfig.getRequestType() == WeChartMessageFormat.JSON) {
            result = serialUtil.serial(this, SerialUtil.SerialType.JSON);
        } else if (messageConfig.getRequestType() == WeChartMessageFormat.XML) {
            result = serialUtil.serial(this, SerialUtil.SerialType.XML);
        }
        return result;
    }

    @JsonIgnore
    public WeChartMessageConfig getMessageConfig() {
        return messageConfig;
    }
}
