package cn.irving.zhao.util.weChart.mp.message.send.menu.entity;

import java.util.List;

/**
 * 菜单信息
 */
public class Menu {
    private String menuid;
    private List<Button> button;
    private MatchRule matchrule;

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public List<Button> getButton() {
        return button;
    }

    public void setButton(List<Button> button) {
        this.button = button;
    }

    public MatchRule getMatchrule() {
        return matchrule;
    }

    public void setMatchrule(MatchRule matchrule) {
        this.matchrule = matchrule;
    }
}
