package cn.irving.zhao.util.weChart.mp.message.send.menu.create;

import com.dianpu.platform.tools.wechart.mp.message.send.BaseMpSendInputMessage;

/**
 * 创建菜单请求输入消息
 */
public class CreateMenuInputMessage extends BaseMpSendInputMessage<CreateMenuOutputMessage> {
    /**
     * 获得输入消息所对应的输出消息
     */
    @Override
    public Class<CreateMenuOutputMessage> getOutputMessageClass() {
        return CreateMenuOutputMessage.class;
    }
}
