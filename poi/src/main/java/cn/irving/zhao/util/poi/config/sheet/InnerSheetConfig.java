package cn.irving.zhao.util.poi.config.sheet;

import cn.irving.zhao.util.poi.annotation.sheet.InnerSheet;

/**
 * 内嵌表格配置信息
 */
public class InnerSheetConfig extends SheetConfig {

    /**
     * @param config 注解配置信息
     */
    public InnerSheetConfig(InnerSheet config) {
        this.baseRow = config.baseRow();
        this.baseCol = config.baseCol();
    }

    /**
     * @param baseRow 基准行
     * @param baseCol 基准列
     */
    public InnerSheetConfig(Integer baseRow, Integer baseCol) {
        this.baseRow = baseRow;
        this.baseCol = baseCol;
    }

    private Integer baseRow;//基准行

    private Integer baseCol;//基准列

    public Integer getBaseRow() {
        return baseRow;
    }

    public void setBaseRow(Integer baseRow) {
        this.baseRow = baseRow;
    }

    public Integer getBaseCol() {
        return baseCol;
    }

    public void setBaseCol(Integer baseCol) {
        this.baseCol = baseCol;
    }
}
