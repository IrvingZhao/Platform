package cn.irving.zhao.util.poi2.config.entity;

import cn.irving.zhao.util.poi2.config.CellConfig;
import cn.irving.zhao.util.poi2.config.SheetConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作表内单元格配置信息
 */
public class SheetCellConfig {
    private List<CellConfig> cellConfigs;//单元格配置信息

    private List<SheetConfig> refSheetConfigs;//关联单元表配置信息

    public SheetCellConfig addCellConfig(CellConfig cellConfig) {
        if (cellConfigs == null) {
            synchronized (this) {
                if (cellConfigs == null) {
                    cellConfigs = new ArrayList<>();
                }
            }
        }
        cellConfigs.add(cellConfig);
        return this;
    }

    public SheetCellConfig addSheetConfig(SheetConfig sheetConfig) {
        if (refSheetConfigs == null) {
            synchronized (this) {
                if (refSheetConfigs == null) {
                    refSheetConfigs = new ArrayList<>();
                }
            }
        }
        refSheetConfigs.add(sheetConfig);
        return this;
    }

    public List<CellConfig> getCellConfigs() {
        return cellConfigs;
    }

    public void setCellConfigs(List<CellConfig> cellConfigs) {
        this.cellConfigs = cellConfigs;
    }

    public List<SheetConfig> getRefSheetConfigs() {
        return refSheetConfigs;
    }

    public void setRefSheetConfigs(List<SheetConfig> refSheetConfigs) {
        this.refSheetConfigs = refSheetConfigs;
    }
}
