package cn.irving.zhao.util.poi.config;

public class ColumnPosition {
    private int rowIndex;
    private int cellIndex;

    public ColumnPosition() {
    }

    public ColumnPosition(int rowIndex, int cellIndex) {
        this.rowIndex = rowIndex;
        this.cellIndex = cellIndex;
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
}