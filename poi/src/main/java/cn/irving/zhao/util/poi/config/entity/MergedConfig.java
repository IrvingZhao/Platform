package cn.irving.zhao.util.poi.config.entity;

/**
 * 单元格合并配置信息
 */
public class MergedConfig {

    public MergedConfig() {
    }

    public MergedConfig(int startRowIndex, int startColIndex, int endRowIndex, int endColIndex) {
        this.startRowIndex = startRowIndex;
        this.startColIndex = startColIndex;
        this.endRowIndex = endRowIndex;
        this.endColIndex = endColIndex;
    }

    private int startRowIndex;
    private int startColIndex;

    private int endRowIndex;
    private int endColIndex;

    public int getStartRowIndex() {
        return startRowIndex;
    }

    public void setStartRowIndex(int startRowIndex) {
        this.startRowIndex = startRowIndex;
    }

    public int getStartColIndex() {
        return startColIndex;
    }

    public void setStartColIndex(int startColIndex) {
        this.startColIndex = startColIndex;
    }

    public int getEndRowIndex() {
        return endRowIndex;
    }

    public void setEndRowIndex(int endRowIndex) {
        this.endRowIndex = endRowIndex;
    }

    public int getEndColIndex() {
        return endColIndex;
    }

    public void setEndColIndex(int endColIndex) {
        this.endColIndex = endColIndex;
    }
}
