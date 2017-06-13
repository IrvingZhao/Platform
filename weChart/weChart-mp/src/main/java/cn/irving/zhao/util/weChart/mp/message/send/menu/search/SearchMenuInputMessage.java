package cn.irving.zhao.util.weChart.mp.message.send.menu.search;

import com.dianpu.platform.tools.wechart.mp.message.send.BaseMpSendInputMessage;
import com.dianpu.platform.tools.wechart.mp.message.send.menu.entity.Menu;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 菜单查询-输入消息
 */
public class SearchMenuInputMessage extends BaseMpSendInputMessage<SearchMenuOutputMessage> {

    private Menu menu;//普通菜单

    @JsonProperty("conditionalmenu")
    private List<Menu> conditionalMenu;//个性化菜单

    /**
     * 获得输入消息所对应的输出消息
     */
    @Override
    public Class<SearchMenuOutputMessage> getOutputMessageClass() {
        return SearchMenuOutputMessage.class;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Menu> getConditionalMenu() {
        return conditionalMenu;
    }

    public void setConditionalMenu(List<Menu> conditionalMenu) {
        this.conditionalMenu = conditionalMenu;
    }
}
