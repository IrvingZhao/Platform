package cn.irving.zhao.util.poi.config.cell;

import cn.irving.zhao.util.poi.annotation.cell.Cell;
import cn.irving.zhao.util.poi.annotation.cell.MergedRegion;
import cn.irving.zhao.util.poi.annotation.cell.Repeatable;

/**
 * 单元格配置信息
 */
public class CellConfig {

    public CellConfig(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public CellConfig(int row, int col, CellRepeatConfig repeatConfig) {
        this.row = row;
        this.col = col;
        this.repeatConfig = repeatConfig;
    }

    public CellConfig(int row, int col, CellMergedConfig mergedConfig) {
        this.row = row;
        this.col = col;
        this.mergedConfig = mergedConfig;
    }

    public CellConfig(int row, int col, CellRepeatConfig repeatConfig, CellMergedConfig mergedConfig) {
        this.row = row;
        this.col = col;
        this.repeatConfig = repeatConfig;

        this.mergedConfig = mergedConfig;
    }

    public CellConfig(Cell config) {
        this.row = config.row();
        this.col = config.col();
    }

    private int row;
    private int col;
    private CellRepeatConfig repeatConfig;
    private CellMergedConfig mergedConfig;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public CellRepeatConfig getRepeatConfig() {
        return repeatConfig;
    }

    public void setRepeatConfig(Repeatable repeatConfig) {
        this.repeatConfig = new CellRepeatConfig(repeatConfig);
    }

    public void setRepeatConfig(CellRepeatConfig repeatConfig) {
        this.repeatConfig = repeatConfig;
    }

    public CellMergedConfig getMergedConfig() {
        return mergedConfig;
    }

    public void setMergedConfig(MergedRegion mergedConfig) {
        this.mergedConfig = new CellMergedConfig(mergedConfig);
    }

    public void setMergedConfig(CellMergedConfig mergedConfig) {
        this.mergedConfig = mergedConfig;
    }
}
