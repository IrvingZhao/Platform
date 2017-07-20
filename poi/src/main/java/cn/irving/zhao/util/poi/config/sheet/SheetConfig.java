package cn.irving.zhao.util.poi.config.sheet;

import cn.irving.zhao.util.poi.annotation.cell.Cell;
import cn.irving.zhao.util.poi.annotation.cell.MergedRegion;
import cn.irving.zhao.util.poi.annotation.cell.Repeatable;
import cn.irving.zhao.util.poi.config.cell.CellConfig;
import cn.irving.zhao.util.poi.config.cell.CellMergedConfig;
import cn.irving.zhao.util.poi.config.cell.CellRepeatConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 表格配置信息
 */
public class SheetConfig {
    private String sheetName;
    private final List<CellConfig> cells = new ArrayList<>();
    private final List<InnerSheetConfig> innerSheets = new ArrayList<>();

    public SheetConfig addCell(CellConfig cellConfig) {
        cells.add(cellConfig);
        return this;
    }

    public SheetConfig addInnerSheet(InnerSheetConfig innerSheetConfig) {
        innerSheets.add(innerSheetConfig);
        return this;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<CellConfig> getCells() {
        return cells;
    }

    public List<InnerSheetConfig> getInnerSheets() {
        return innerSheets;
    }
}
