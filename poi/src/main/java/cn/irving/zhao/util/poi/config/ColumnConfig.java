package cn.irving.zhao.util.poi.config;

import cn.irving.zhao.util.poi.enums.RepeatMethod;
import cn.irving.zhao.util.poi.formatter.ColumnFormatter;

import java.util.ArrayList;
import java.util.List;

public class ColumnConfig {

    private final List<ColumnPosition> columnPositions = new ArrayList<>();
    private RepeatMethod repeat = RepeatMethod.NONE;
    private ColumnFormatter formatter;
    private boolean isPrimary;
    private int transverseIdentity;//横向增长
    private int portraitIdentity;//纵向增长

    public ColumnConfig addPosition(int rowIndex, int cellIndex) {
        columnPositions.add(new ColumnPosition(rowIndex, cellIndex));
        return this;
    }

    public List<ColumnPosition> getColumnPositions() {
        return columnPositions;
    }

    public ColumnFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(ColumnFormatter formatter) {
        this.formatter = formatter;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public int getTransverseIdentity() {
        return transverseIdentity;
    }

    public void setTransverseIdentity(int transverseIdentity) {
        this.transverseIdentity = transverseIdentity;
    }

    public int getPortraitIdentity() {
        return portraitIdentity;
    }

    public void setPortraitIdentity(int portraitIdentity) {
        this.portraitIdentity = portraitIdentity;
    }

    public RepeatMethod getRepeat() {
        return repeat;
    }
}
