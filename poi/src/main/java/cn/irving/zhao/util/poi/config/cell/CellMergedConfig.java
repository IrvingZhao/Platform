package cn.irving.zhao.util.poi.config.cell;

import cn.irving.zhao.util.poi.annotation.MergedRegion;

/**
 * 合并单元格配置
 */
public class CellMergedConfig {

    /**
     * @param mergedRegion 单元格合并注解配置
     */
    public CellMergedConfig(MergedRegion mergedRegion) {
        this.startRowIndex = mergedRegion.start().row();
        this.startColIndex = mergedRegion.start().col();
        this.endRowIndex = mergedRegion.end().row();
        this.endColIndex = mergedRegion.end().col();
    }

    /**
     * @param startRowIndex 合并开始单元格行坐标
     * @param startColIndex 合并开始单元格列坐标
     * @param endRowIndex   合并结束单元格行坐标
     * @param endColIndex   合并结束单元格列坐标
     */
    public CellMergedConfig(Integer startRowIndex, Integer startColIndex, Integer endRowIndex, Integer endColIndex) {
        this.startRowIndex = startRowIndex;
        this.startColIndex = startColIndex;
        this.endRowIndex = endRowIndex;
        this.endColIndex = endColIndex;
    }

    private Integer startRowIndex;//开始单元格行坐标
    private Integer startColIndex;//开始单元格列坐标
    private Integer endRowIndex;//结束单元格行坐标
    private Integer endColIndex;//结束单元格列坐标

    public Integer getStartRowIndex() {
        return startRowIndex;
    }

    public void setStartRowIndex(Integer startRowIndex) {
        this.startRowIndex = startRowIndex;
    }

    public Integer getStartColIndex() {
        return startColIndex;
    }

    public void setStartColIndex(Integer startColIndex) {
        this.startColIndex = startColIndex;
    }

    public Integer getEndRowIndex() {
        return endRowIndex;
    }

    public void setEndRowIndex(Integer endRowIndex) {
        this.endRowIndex = endRowIndex;
    }

    public Integer getEndColIndex() {
        return endColIndex;
    }

    public void setEndColIndex(Integer endColIndex) {
        this.endColIndex = endColIndex;
    }
}
