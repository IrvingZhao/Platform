package cn.irving.zhao.util.poi2;

import cn.irving.zhao.util.poi2.config.CellConfig;
import cn.irving.zhao.util.poi2.config.SheetConfig;
import cn.irving.zhao.util.poi2.config.WorkBookConfig;
import cn.irving.zhao.util.poi2.config.WorkBookConfigFactory;
import cn.irving.zhao.util.poi2.config.entity.MergedConfig;
import cn.irving.zhao.util.poi2.config.entity.RepeatConfig;
import cn.irving.zhao.util.poi2.config.entity.SheetCellConfig;
import cn.irving.zhao.util.poi2.enums.Direction;
import cn.irving.zhao.util.poi2.inter.IWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * POI 工具包
 */
public class POIUtil {

    public void export(IWorkbook data, String output) {

        Workbook workbook = data.getWorkbookType().getWorkbook(null);

        WorkBookConfig workBookConfig = WorkBookConfigFactory.getWorkbookConfig(data.getClass());
        SheetConfig defaultSheetConfig = workBookConfig.getDefaultSheetConfig();
        if (defaultSheetConfig != null) {
            //TODO 写入DefaultSheet
            writeSheetData(workbook, defaultSheetConfig, data);//默认工作表的数据来源为 IWorkbook 对象
        }
        List<SheetConfig> configList = workBookConfig.getSheetConfigs();
        if (configList != null) {
            configList.forEach((sheetConfig) -> {
                //TODO 写入单个Sheet
            });
        }
    }

    private void writeSheetData(Workbook workbook, SheetConfig sheetConfig, Object data) {
        Sheet sheet = getSheet(workbook, sheetConfig.getName());//获得Sheet
        RepeatConfig repeatConfig = sheetConfig.getRepeatConfig();
    }

    private void writeSheetData(Sheet sheet, SheetCellConfig sheetCellConfig, Object data) {
        List<CellConfig> cellConfigs = sheetCellConfig.getCellConfigs();
        for (CellConfig item : cellConfigs) {
            writeCellData(sheet, item, data);
        }
    }

    private void writeCellData(Sheet sheet, CellConfig cellConfig, Object data) {
        RepeatConfig repeatConfig = cellConfig.getRepeatConfig();
        MergedConfig mergedConfig = cellConfig.getMergedConfig();
        Object cellData = cellConfig.getData(data);
        int rowIndex = cellConfig.getRowIndex();
        int cellIndex = cellConfig.getCellIndex();
        if (repeatConfig == null) {

        } else {
            int currentIndex = 0;
            int max = repeatConfig.getMax();
            int identity = repeatConfig.getIdentity();
            Direction dir = repeatConfig.getDirection();
            Iterator cellIteratorData = ((Iterable) data).iterator();
            while (cellIteratorData.hasNext()) {
                if (max > 0 && currentIndex >= max) {//最大循环次数判断
                    break;
                }
                //TODO 判断是否需要合并单元格
            }
        }
    }


    private void writeCellData(Cell cell, CellConfig cellConfig, Object data) {//写入cell 数据

    }


    private Cell getMergedCell(Sheet sheet, int startRow, int startCol, int endRow, int endCol) {
        sheet.addMergedRegion(new CellRangeAddress(startRow, endRow, startCol, endCol));
        return getCell(getRow(sheet, startRow), startCol);
    }

    private Sheet getSheet(Workbook workbook, String sheetName) {
        return workbook.getSheet(sheetName) == null ? workbook.createSheet(sheetName) : workbook.getSheet(sheetName);
    }

    private Row getRow(Sheet sheet, int rowIndex) {
        return sheet.getRow(rowIndex) == null ? sheet.createRow(rowIndex) : sheet.getRow(rowIndex);
    }

    private Cell getCell(Row row, int cellIndex) {
        return row.getCell(cellIndex) == null ? row.createCell(cellIndex) : row.getCell(cellIndex);
    }


}
