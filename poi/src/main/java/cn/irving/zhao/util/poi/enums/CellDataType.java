package cn.irving.zhao.util.poi.enums;

import org.apache.poi.ss.usermodel.Cell;

/**
 * 单元格数据类型
 */
public enum CellDataType {
    /**
     * 自动获取
     */
    AUTO(-1),
    /**
     * 数字
     */
    NUMERIC(Cell.CELL_TYPE_NUMERIC),
    /**
     * 时间
     */
    DATE(Cell.CELL_TYPE_NUMERIC),
    /**
     * 字符串
     */
    STRING(Cell.CELL_TYPE_STRING),
    /**
     * 表达式
     */
    FORMULA(Cell.CELL_TYPE_FORMULA);

    CellDataType(int code) {
        this.code = code;
    }

    private final int code;

    public int getCode() {
        return code;
    }
}
