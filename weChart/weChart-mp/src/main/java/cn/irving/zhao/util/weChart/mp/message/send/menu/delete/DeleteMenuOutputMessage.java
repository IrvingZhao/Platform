package cn.irving.zhao.util.weChart.mp.message.send.menu.delete;

import com.dianpu.platform.tools.wechart.mp.message.send.BaseMpSendOutputMessage;

/**
 * 删除全部自定义菜单，包括个性化菜单
 */
public class DeleteMenuOutputMessage extends BaseMpSendOutputMessage<DeleteMenuInputMessage> {

    protected DeleteMenuOutputMessage(String token) {
        super(token);
    }

    @Override
    public Class<DeleteMenuInputMessage> getInputMessageClass() {
        return DeleteMenuInputMessage.class;
    }

    @Override
    public String getUrl() {
        return String.format("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=%s", accessToken);
    }
}
