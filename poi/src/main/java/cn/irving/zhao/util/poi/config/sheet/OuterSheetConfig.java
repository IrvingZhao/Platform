package cn.irving.zhao.util.poi.config.sheet;

import cn.irving.zhao.util.poi.config.cell.CellConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 表格配置信息
 */
public class OuterSheetConfig {

    public OuterSheetConfig(String sheetName, SheetConfig sheetConfig) {
        this.sheetName = sheetName;
        this.sheetConfig = sheetConfig;
    }

    private String sheetName;
    private SheetConfig sheetConfig;

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
