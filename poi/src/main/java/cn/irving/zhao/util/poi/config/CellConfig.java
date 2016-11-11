package cn.irving.zhao.util.poi.config;

import cn.irving.zhao.util.poi.enums.RepeatMethod;
import cn.irving.zhao.util.poi.formatter.ColumnFormatter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CellConfig {

    private final List<CellPosition> columnPositions = new ArrayList<>();
    private RepeatMethod repeat = RepeatMethod.NONE;
    private ColumnFormatter formatter;
    private boolean isPrimary;
    private int transverseIdentity;//横向增长
    private int portraitIdentity;//纵向增长
    private Field cellField;

    CellConfig addPosition(int rowIndex, int cellIndex) {
        columnPositions.add(new CellPosition(rowIndex, cellIndex));
        return this;
    }

    public List<CellPosition> getColumnPositions() {
        return columnPositions;
    }

    public RepeatMethod getRepeat() {
        return repeat;
    }

    void setRepeat(RepeatMethod repeat) {
        this.repeat = repeat;
    }

    public ColumnFormatter getFormatter() {
        return formatter;
    }

    void setFormatter(ColumnFormatter formatter) {
        this.formatter = formatter;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public int getTransverseIdentity() {
        return transverseIdentity;
    }

    void setTransverseIdentity(int transverseIdentity) {
        this.transverseIdentity = transverseIdentity;
    }

    public int getPortraitIdentity() {
        return portraitIdentity;
    }

    void setPortraitIdentity(int portraitIdentity) {
        this.portraitIdentity = portraitIdentity;
    }

    public Field getCellField() {
        return cellField;
    }

    void setCellField(Field cellField) {
        this.cellField = cellField;
    }
}
