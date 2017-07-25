package cn.irving.zhao.util.poi.config.sheet;

import cn.irving.zhao.util.poi.annotation.InnerSheet;

/**
 * 内嵌表格配置信息
 */
public class InnerSheetConfig {

    /**
     * @param config 注解配置信息
     */
    public InnerSheetConfig(InnerSheet config) {
        this.rowIndex = config.position().rowIndex();
        this.colIndex = config.position().colIndex();
    }

    public InnerSheetConfig(InnerSheet config, SheetConfig sheetConfig) {
        this(config);
        this.sheetConfig = sheetConfig;
    }

    /**
     * @param rowIndex 基准行
     * @param colIndex 基准列
     */
    public InnerSheetConfig(Integer rowIndex, Integer colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    private SheetConfig sheetConfig;// 工作簿配置信息

    private Integer rowIndex;//基准行

    private Integer colIndex;//基准列

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
