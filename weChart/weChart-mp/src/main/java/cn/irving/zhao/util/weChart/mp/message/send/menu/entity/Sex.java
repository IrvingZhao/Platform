package cn.irving.zhao.util.weChart.mp.message.send.menu.entity;

import com.dianpu.platform.tools.base.serial.custom.CustomEnumValue;

/**
 * 性别
 */
public enum Sex implements CustomEnumValue<Sex, Integer> {
    Male(1), Female(2);
    private int code;

    Sex(int code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    public Sex[] getValues() {
        return Sex.values();
    }
}
