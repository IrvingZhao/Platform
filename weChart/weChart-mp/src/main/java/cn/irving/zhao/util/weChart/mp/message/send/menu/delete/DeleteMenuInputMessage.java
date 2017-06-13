package cn.irving.zhao.util.weChart.mp.message.send.menu.delete;

import com.dianpu.platform.tools.wechart.mp.message.send.BaseMpSendInputMessage;

/**
 * 删除全部菜单输入消息
 */
public class DeleteMenuInputMessage extends BaseMpSendInputMessage<DeleteMenuOutputMessage> {


    /**
     * 获得输入消息所对应的输出消息
     */
    @Override
    public Class<DeleteMenuOutputMessage> getOutputMessageClass() {
        return DeleteMenuOutputMessage.class;
    }
}
