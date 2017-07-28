package cn.irving.zhao.util.poi.config.sheet;

import cn.irving.zhao.util.poi.config.cell.CellConfig;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 表格配置信息
 */
public class OuterSheetConfig {

    public OuterSheetConfig(String sheetName, SheetConfig sheetConfig, Field field) {
        this.sheetName = sheetName;
        this.sheetConfig = sheetConfig;
        this.field = field;
    }

    private String sheetName;
    private SheetConfig sheetConfig;
    private Field field;//获取数据使用

    /**
     * 获取工作簿对应数据
     *
     * @param source 数据存放位置
     * @return 数据值
     */
    public Object getData(Object source) {
        field.setAccessible(true);
        try {
            return field.get(source);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public SheetConfig getSheetConfig() {
        return sheetConfig;
    }

    public void setSheetConfig(SheetConfig sheetConfig) {
        this.sheetConfig = sheetConfig;
    }
}
