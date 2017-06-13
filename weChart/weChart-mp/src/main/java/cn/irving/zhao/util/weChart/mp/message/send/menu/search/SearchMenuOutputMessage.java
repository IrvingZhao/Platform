package cn.irving.zhao.util.weChart.mp.message.send.menu.search;

import com.dianpu.platform.tools.wechart.mp.message.send.BaseMpSendOutputMessage;

/**
 * 查询菜单-输出消息
 */
public class SearchMenuOutputMessage extends BaseMpSendOutputMessage<SearchMenuInputMessage> {

    public SearchMenuOutputMessage(String token) {
        super(token);
    }

    @Override
    public Class<SearchMenuInputMessage> getInputMessageClass() {
        return SearchMenuInputMessage.class;
    }

    @Override
    public String getUrl() {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=%s";
        return String.format(url,accessToken);
    }
}
