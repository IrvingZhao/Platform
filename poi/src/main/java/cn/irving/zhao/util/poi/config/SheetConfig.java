package cn.irving.zhao.util.poi.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SheetConfig {
    private InputStream template;//TODO 更改 template用法
    private String sheetName;
    private int sheetIndex;
    private final List<CellConfig> columns = new ArrayList<>();

    public SheetConfig addColumn(CellConfig column) {
        columns.add(column);
        return this;
    }

    public InputStream getTemplate() {
        return template;
    }

    public void setTemplate(InputStream template) {
        this.template = template;
    }
    public void setTemplate(String template) {
//        this.template = template;
        //TODO 根据地址读取模板流
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public List<CellConfig> getColumns() {
        return columns;
    }
}