package cn.irving.zhao.util.poi.enums;

import org.apache.poi.ss.usermodel.Cell;

/**
 * Cell类型
 */
public enum CellType {

    NUMERIC(Cell.CELL_TYPE_NUMERIC),
    STRING(Cell.CELL_TYPE_STRING),
    FORMULA(Cell.CELL_TYPE_FORMULA),
    BLANK(Cell.CELL_TYPE_BLANK),
    BOOLEAN(Cell.CELL_TYPE_BOOLEAN),
    ERROR(Cell.CELL_TYPE_ERROR);

    CellType(int cellType) {
        this.cellType = cellType;
    }

    private int cellType;

    public int getCellType() {
        return this.cellType;
    }

}
