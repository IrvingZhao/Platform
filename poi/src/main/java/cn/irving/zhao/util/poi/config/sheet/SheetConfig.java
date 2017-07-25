package cn.irving.zhao.util.poi.config.sheet;

import cn.irving.zhao.util.poi.annotation.InnerSheet;
import cn.irving.zhao.util.poi.config.cell.CellConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * 工作簿配置信息
 */
public class SheetConfig {
    private final List<CellConfig> cells = new Vector<>();
    private final List<InnerSheetConfig> innerSheets = new Vector<>();

    public SheetConfig addAllInnerSheet(Collection<InnerSheetConfig> innerSheets) {
        this.innerSheets.addAll(innerSheets);
        return this;
    }

    public SheetConfig addAllCell(Collection<CellConfig> cellConfigs) {
        cells.addAll(cellConfigs);
        return this;
    }

    /**
     * 添加单元格配置信息
     *
     * @param cellConfig 单元格配置信息
     */
    public SheetConfig addCell(CellConfig cellConfig) {
        cells.add(cellConfig);
        return this;
    }

    /**
     * 添加内嵌工作簿配置信息
     *
     * @param innerSheetConfig 内嵌工作簿配置信息
     */
    public SheetConfig addInnerSheet(InnerSheetConfig innerSheetConfig) {
        innerSheets.add(innerSheetConfig);
        return this;
    }

    public List<CellConfig> getCells() {
        return cells;
    }

    public List<InnerSheetConfig> getInnerSheets() {
        return innerSheets;
    }
}
