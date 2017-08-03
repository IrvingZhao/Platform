package cn.irving.zhao.util.poi.config;

import cn.irving.zhao.util.poi.annonation.Sheet;
import cn.irving.zhao.util.poi.config.entity.RepeatConfig;
import cn.irving.zhao.util.poi.config.entity.SheetCellConfig;
import cn.irving.zhao.util.poi.enums.SheetType;

import java.util.function.Function;

/**
 * 工作表配置信息
 */
public class SheetConfig {

    private SheetType sheetType;//工作表类型

    private String name;//工作表名称

    private int baseRow;//内嵌工作表位移横坐标
    private int baseCol;//内嵌工作表位移列坐标

    private RepeatConfig repeatConfig;//循环配置项

    private SheetCellConfig sheetCellConfig;//对应类对象配置信息

    private Function<Object, Object> dataGetter;

    public static SheetConfig createSheetConfig(Sheet sheet, Function<Object, Object> dataGetter) {
        SheetConfig result = new SheetConfig();
        if (sheet.type() == SheetType.INNER) {
            result.baseRow = sheet.baseRow();
            result.baseCol = sheet.baseCol();
        } else if (sheet.type() == SheetType.OUTER) {
            result.name = sheet.name();
        }
        result.sheetType = sheet.type();
        result.dataGetter = dataGetter;
        return result;
    }

    public static SheetConfig createOuterSheet(String name, SheetCellConfig sheetCellConfig, Function<Object, Object> dataGetter) {
        SheetConfig result = new SheetConfig();
        result.sheetType = SheetType.OUTER;
        result.name = name;
        result.sheetCellConfig = sheetCellConfig;
        result.dataGetter = dataGetter;
        return result;
    }

    public static SheetConfig createInnerSheet(int baseRow, int baseCol, SheetCellConfig sheetCellConfig, Function<Object, Object> dataGetter) {
        SheetConfig result = new SheetConfig();
        result.baseRow = baseRow;
        result.baseCol = baseCol;
        result.sheetCellConfig = sheetCellConfig;
        result.sheetType = SheetType.INNER;
        result.dataGetter = dataGetter;
        return result;
    }

    public SheetConfig addCellConfig(CellConfig cellConfig) {
        if (sheetCellConfig == null) {
            synchronized (this) {
                if (sheetCellConfig == null) {
                    sheetCellConfig = new SheetCellConfig();
                }
            }
        }
        sheetCellConfig.addCellConfig(cellConfig);
        return this;
    }

    public SheetConfig addSheetConfig(SheetConfig sheetConfig) {
        if (sheetCellConfig == null) {
            synchronized (this) {
                if (sheetCellConfig == null) {
                    sheetCellConfig = new SheetCellConfig();
                }
            }
        }
        sheetCellConfig.addSheetConfig(sheetConfig);
        return this;
    }

    public Object getData(Object source) {
        return dataGetter.apply(source);
    }

    public SheetType getSheetType() {
        return sheetType;
    }

    public void setSheetType(SheetType sheetType) {
        this.sheetType = sheetType;
    }

    public SheetCellConfig getSheetCellConfig() {
        return sheetCellConfig;
    }

    public void setSheetCellConfig(SheetCellConfig sheetCellConfig) {
        this.sheetCellConfig = sheetCellConfig;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBaseRow() {
        return baseRow;
    }

    public void setBaseRow(int baseRow) {
        this.baseRow = baseRow;
    }

    public int getBaseCol() {
        return baseCol;
    }

    public void setBaseCol(int baseCol) {
        this.baseCol = baseCol;
    }

    public RepeatConfig getRepeatConfig() {
        return repeatConfig;
    }

    public void setRepeatConfig(RepeatConfig repeatConfig) {
        this.repeatConfig = repeatConfig;
    }

    public Function getDataGetter() {
        return dataGetter;
    }

}


