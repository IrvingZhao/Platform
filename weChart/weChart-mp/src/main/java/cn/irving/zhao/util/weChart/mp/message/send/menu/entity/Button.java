package cn.irving.zhao.util.weChart.mp.message.send.menu.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 按钮配置信息
 */
public final class Button {

    /**
     * 创建单击事件菜单
     *
     * @param name 菜单名称
     * @param key  单击事件下发参数
     * @return 菜单按钮实例
     */
    public static Button generateClickButton(String name, String key) {
        Button result = new Button();
        result.name = name;
        result.key = key;
        result.type = MenuType.click;
        return result;
    }

    /**
     * 创建打开链接菜单
     *
     * @param name 菜单名称
     * @param url  网页链接
     * @return 菜单按钮实例
     */
    public static Button generateViewButton(String name, String url) {
        Button result = new Button();
        result.name = name;
        result.url = url;
        result.type = MenuType.view;
        return result;
    }

    /**
     * 创建扫码事件菜单
     *
     * @param name 菜单名称
     * @param key  事件下发参数
     * @return 菜单按钮实例
     */
    public static Button generateScanCodePushButton(String name, String key) {
        Button result = new Button();
        result.name = name;
        result.key = key;
        result.type = MenuType.scancode_push;
        return result;
    }

    /**
     * 创建扫码事件，并提示消息接受中菜单
     *
     * @param name 菜单名称
     * @param key  事件下发参数
     * @return 菜单按钮实例
     */
    public static Button generateScanCodeWaitMsgButton(String name, String key) {
        Button result = new Button();
        result.name = name;
        result.key = key;
        result.type = MenuType.scancode_waitmsg;
        return result;
    }

    /**
     * 创建调用相机拍照菜单
     *
     * @param name 菜单名称
     * @param key  事件下发参数
     * @return 菜单按钮实例
     */
    public static Button generatePicSysPhotoButton(String name, String key) {
        Button result = new Button();
        result.name = name;
        result.key = key;
        result.type = MenuType.pic_sysphoto;
        return result;
    }

    /**
     * 创建调用相机或相册发图菜单
     *
     * @param name 菜单名称
     * @param key  事件下发参数
     * @return 菜单按钮实例
     */
    public static Button generatePicPhotoOrAlbumButton(String name, String key) {
        Button result = new Button();
        result.name = name;
        result.key = key;
        result.type = MenuType.pic_photo_or_album;
        return result;
    }

    /**
     * 创建调用微信相册菜单
     *
     * @param name 菜单名称
     * @param key  事件下发参数
     * @return 菜单按钮实例
     */
    public static Button generatePicWeixinButton(String name, String key) {
        Button result = new Button();
        result.name = name;
        result.key = key;
        result.type = MenuType.pic_weixin;
        return result;
    }

    /**
     * 创建地理位置发送菜单
     *
     * @param name 菜单名称
     * @param key  事件下发参数
     * @return 菜单按钮实例
     */
    public static Button generateLocationSelectButton(String name, String key) {
        Button result = new Button();
        result.name = name;
        result.key = key;
        result.type = MenuType.location_select;
        return result;
    }

    /**
     * 创建素材发送菜单
     *
     * @param name    菜单名称
     * @param mediaId 素材id
     * @return 菜单按钮实例
     */
    public static Button generateMediaIdButton(String name, String mediaId) {
        Button result = new Button();
        result.name = name;
        result.media_id = mediaId;
        result.type = MenuType.media_id;
        return result;
    }

    /**
     * 创建图文素材消息发送菜单
     *
     * @param name    菜单名称
     * @param mediaId 素材id
     * @return 菜单按钮实例
     */
    public static Button generateViewLimitedButton(String name, String mediaId) {
        Button result = new Button();
        result.name = name;
        result.media_id = mediaId;
        result.type = MenuType.view_limited;
        return result;
    }

    /**
     * 创建打开小程序菜单
     *
     * @param name     菜单名称
     * @param url      不支持小程序的客户端打开的地址
     * @param appId    小程序appId
     * @param pagePath 小程序地址
     */
    public static Button generateMiniProgramButton(String name, String url, String appId, String pagePath) {
        Button result = new Button();
        result.name = name;
        result.url = url;
        result.appid = appId;
        result.pagepath = pagePath;
        result.type = MenuType.miniprogram;
        return result;
    }

    /**
     * 添加子菜单
     *
     * @param button 子菜单对象
     */
    public Button addSubButton(Button button) {
        if (sub_button == null) {
            synchronized (this) {
                if (sub_button == null) {
                    sub_button = new ArrayList<>();
                }
            }
        }
        sub_button.add(button);
        return this;
    }


    private Button() {
    }

    private String name;//菜单标题
    private MenuType type;//菜单类型
    private String key;//菜单key
    private String url;//网页链接地址
    private String media_id;//素材id
    private String appid;//小程序appid
    private String pagepath;//小程序页面路径
    private List<Button> sub_button;//子菜单

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPagepath() {
        return pagepath;
    }

    public void setPagepath(String pagepath) {
        this.pagepath = pagepath;
    }

    public List<Button> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<Button> sub_button) {
        this.sub_button = sub_button;
    }
}
