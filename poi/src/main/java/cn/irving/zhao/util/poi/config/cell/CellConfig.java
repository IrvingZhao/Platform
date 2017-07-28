package cn.irving.zhao.util.poi.config.cell;

import cn.irving.zhao.util.poi.annotation.Cell;
import cn.irving.zhao.util.poi.annotation.MergedRegion;
import cn.irving.zhao.util.poi.annotation.Repeatable;

import java.lang.reflect.Field;

/**
 * 单元格配置信息
 */
public class CellConfig {

    public CellConfig(int row, int col, Field field) {
        this(row, col, null, null, field);
    }

    public CellConfig(int row, int col, CellRepeatConfig repeatConfig, Field field) {
        this(row, col, repeatConfig, null, field);
    }

    public CellConfig(int row, int col, CellMergedConfig mergedConfig, Field field) {
        this(row, col, null, mergedConfig, field);
    }

    public CellConfig(int row, int col, CellRepeatConfig repeatConfig, CellMergedConfig mergedConfig, Field field) {
        this.row = row;
        this.col = col;
        this.repeatConfig = repeatConfig;
        this.mergedConfig = mergedConfig;
        this.field = field;
    }

    public CellConfig(Cell config, Repeatable repeatable, MergedRegion mergedRegion, Field field) {
        this.row = config.value().rowIndex();
        this.col = config.value().colIndex();
        if (repeatable != null) {
            this.repeatConfig = new CellRepeatConfig(repeatable);
        }
        if (mergedRegion != null) {
            this.mergedConfig = new CellMergedConfig(mergedRegion);
        }
        this.field = field;
    }

    private int row;
    private int col;
    private CellRepeatConfig repeatConfig;
    private CellMergedConfig mergedConfig;
    private Field field;//获取数据使用

    /**
     * 获取cell对应数据对象
     *
     * @param source 数据存放对象
     * @return 数据取回对象
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
