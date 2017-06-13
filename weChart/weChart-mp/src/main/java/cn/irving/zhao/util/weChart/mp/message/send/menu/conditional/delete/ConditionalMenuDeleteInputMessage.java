package cn.irving.zhao.util.weChart.mp.message.send.menu.conditional.delete;

import com.dianpu.platform.tools.wechart.mp.message.send.BaseMpSendInputMessage;

/**
 * 删除自定义菜单输入消息
 */
public class ConditionalMenuDeleteInputMessage extends BaseMpSendInputMessage<ConditionalMenuDeleteOutputMessage> {
    /**
     * 获得输入消息所对应的输出消息
     */
    @Override
    public Class<ConditionalMenuDeleteOutputMessage> getOutputMessageClass() {
        return ConditionalMenuDeleteOutputMessage.class;
    }
}
