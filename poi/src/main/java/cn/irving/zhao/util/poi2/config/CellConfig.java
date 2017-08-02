package cn.irving.zhao.util.poi2.config;

import cn.irving.zhao.util.poi2.annonation.Cell;
import cn.irving.zhao.util.poi2.annonation.MergedRegion;
import cn.irving.zhao.util.poi2.annonation.Repeatable;
import cn.irving.zhao.util.poi2.config.entity.MergedConfig;
import cn.irving.zhao.util.poi2.config.entity.RepeatConfig;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 单元格配置信息
 */
public class CellConfig {
    public CellConfig() {
    }

    public CellConfig(Cell cell, Repeatable repeatable, MergedRegion mergedRegion, Function dataGetter) {
        this(cell.rowIndex(), cell.colIndex(),
                repeatable == null ? null : new RepeatConfig(repeatable),
                mergedRegion == null ? null : new MergedConfig(cell.rowIndex(), cell.colIndex(), mergedRegion.endRowIndex(), mergedRegion.endColIndex()),
                dataGetter);
    }

    public CellConfig(int rowIndex, int cellIndex, Function dataGetter) {
        this(rowIndex, cellIndex, null, null, dataGetter);
    }

    public CellConfig(int rowIndex, int cellIndex, RepeatConfig repeatConfig, Function dataGetter) {
        this(rowIndex, cellIndex, repeatConfig, null, dataGetter);
    }

    public CellConfig(int rowIndex, int cellIndex, MergedConfig mergedConfig, Function dataGetter) {
        this(rowIndex, cellIndex, null, mergedConfig, dataGetter);
    }

    public CellConfig(int rowIndex, int cellIndex, RepeatConfig repeatConfig, MergedConfig mergedConfig, Function dataGetter) {
        this.rowIndex = rowIndex;
        this.cellIndex = cellIndex;
        this.repeatConfig = repeatConfig;
        this.mergedConfig = mergedConfig;
        this.dataGetter = dataGetter;
    }

    private int rowIndex;//行坐标
    private int cellIndex;//列坐标

    private RepeatConfig repeatConfig;//循环配置信息

    private MergedConfig mergedConfig;//合并单元格配置

    private Function<Object, Object> dataGetter;//数据获取器

    public Object getData(Object source) {
        return dataGetter.apply(source);
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getCellIndex() {
        return cellIndex;
    }

    public void setCellIndex(int cellIndex) {
        this.cellIndex = cellIndex;
    }

    public RepeatConfig getRepeatConfig() {
        return repeatConfig;
    }

    public void setRepeatConfig(RepeatConfig repeatConfig) {
        this.repeatConfig = repeatConfig;
    }

    public MergedConfig getMergedConfig() {
        return mergedConfig;
    }

    public void setMergedConfig(MergedConfig mergedConfig) {
        this.mergedConfig = mergedConfig;
    }

    public void setDataGetter(Function<Object, Object> dataGetter) {
        this.dataGetter = dataGetter;
    }
}
