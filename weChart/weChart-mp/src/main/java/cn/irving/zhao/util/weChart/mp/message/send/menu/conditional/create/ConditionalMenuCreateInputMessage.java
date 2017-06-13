package cn.irving.zhao.util.weChart.mp.message.send.menu.conditional.create;

import com.dianpu.platform.tools.wechart.mp.message.send.BaseMpSendInputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 个性化菜单创建输入消息
 */
public class ConditionalMenuCreateInputMessage extends BaseMpSendInputMessage<ConditionalMenuCreateOutputMessage> {

    @JsonProperty("menuid")
    private String menuId;

    /**
     * 获得输入消息所对应的输出消息
     */
    @Override
    public Class<ConditionalMenuCreateOutputMessage> getOutputMessageClass() {
        return ConditionalMenuCreateOutputMessage.class;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
