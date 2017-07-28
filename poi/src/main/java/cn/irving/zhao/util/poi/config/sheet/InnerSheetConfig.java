package cn.irving.zhao.util.poi.config.sheet;

import cn.irving.zhao.util.poi.annotation.InnerSheet;

import java.lang.reflect.Field;

/**
 * 内嵌表格配置信息
 */
public class InnerSheetConfig {

    /**
     * @param config 注解配置信息
     */
    public InnerSheetConfig(InnerSheet config, Field field) {
        this.rowIndex = config.position().rowIndex();
        this.colIndex = config.position().colIndex();
        this.field = field;
    }

    public InnerSheetConfig(InnerSheet config, SheetConfig sheetConfig, Field field) {
        this(config, field);
        this.sheetConfig = sheetConfig;
    }

    /**
     * @param rowIndex 基准行
     * @param colIndex 基准列
     * @param field    获取数据的field
     */
    public InnerSheetConfig(Integer rowIndex, Integer colIndex, Field field) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.field = field;
    }

    private SheetConfig sheetConfig;// 工作簿配置信息

    private Integer rowIndex;//基准行

    private Integer colIndex;//基准列

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

    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public Integer getColIndex() {
        return colIndex;
    }

    public void setColIndex(Integer colIndex) {
        this.colIndex = colIndex;
    }

    public SheetConfig getSheetConfig() {
        return sheetConfig;
    }

    public void setSheetConfig(SheetConfig sheetConfig) {
        this.sheetConfig = sheetConfig;
    }

}
