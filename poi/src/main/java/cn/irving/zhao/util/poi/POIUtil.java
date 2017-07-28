package cn.irving.zhao.util.poi;

import cn.irving.zhao.util.poi.config.cell.CellConfig;
import cn.irving.zhao.util.poi.config.cell.CellMergedConfig;
import cn.irving.zhao.util.poi.config.sheet.SheetConfig;
import cn.irving.zhao.util.poi.config.workbook.WorkbookConfig;
import cn.irving.zhao.util.poi.enums.Direction;
import cn.irving.zhao.util.poi.enums.OutputType;
import cn.irving.zhao.util.poi.inter.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * POI工具类
 */
public class POIUtil {

    private Logger logger = LoggerFactory.getLogger(POIUtil.class);

    public void writeExcel(IWorkbook data, String output, OutputType outputType) {
        try {
            WorkbookConfig workbookConfig = data.getConfig();
            File file = new File(output);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new RuntimeException(output + "文件创建失败");
                }
            }
            Workbook workbook = outputType.createWorkbook();
            workbookConfig.getOuterSheetConfigs().forEach((config) -> {
                Sheet sheet = getSheet(workbook, config.getSheetName());
                writeSheet(config.getSheetConfig(), sheet, config.getData(data), 0, 0);
            });
            workbook.write(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    //TODO write innerSheet 需要设置 baseRow 和 baseCol
    //TODO  二次改版  将 获取 Cell方法 进行提出  想写入数据方法进行提出

    private void writeSheet(SheetConfig sheetConfig, Sheet sheet, Object data, int baseRow, int baseCol) {
        sheetConfig.getCells().forEach((cellConfig -> {
            Object cellData = cellConfig.getData(data);
            if (cellConfig.getRepeatConfig() != null) {
                if (Iterable.class.isAssignableFrom(cellData.getClass())) {
                    writeRepeatCell(cellConfig, sheet, (Iterable<Object>) cellData, baseRow, baseCol);
                } else {
                    throw new RuntimeException(cellData.getClass() + "不是一个有效的Iterable对象");
                }
            } else if (cellConfig.getMergedConfig() != null) {
                CellMergedConfig mergedConfig = cellConfig.getMergedConfig();
                Cell cell = getMergedCell(sheet, mergedConfig.getStartRowIndex() + baseRow, mergedConfig.getStartColIndex() + baseCol, mergedConfig.getEndRowIndex() + baseRow, mergedConfig.getEndColIndex() + baseCol);
                //TODO 根据不同的类型设置不同的 CellType
                cell.setCellValue(cellData.toString());
            } else {
                Cell cell = getCell(getRow(sheet, cellConfig.getRow() + baseRow), cellConfig.getCol() + baseCol);
                //TODO 根据不同的类型设置不同的 CellType
                cell.setCellValue(cellData.toString());
            }
        }));
        sheetConfig.getInnerSheets().forEach((innerSheetConfig) -> {
            writeSheet(innerSheetConfig.getSheetConfig(), sheet, innerSheetConfig.getData(data), innerSheetConfig.getRowIndex(), innerSheetConfig.getColIndex());
        });
    }

    private void writeRepeatCell(CellConfig cellConfig, Sheet sheet, Iterable<Object> data, int baseRow, int baseCol) {
        int index = 0;
        int identity = cellConfig.getRepeatConfig().getIdentity();
        Direction direction = cellConfig.getRepeatConfig().getDirection();
        for (Object itemData : data) {
            Cell cell = null;
            if (cellConfig.getMergedConfig() == null) {
                if (direction == Direction.HERIZONTAL) {
                    cell = getCell(getRow(sheet, cellConfig.getRow() + baseRow), cellConfig.getCol() + baseCol + (identity * index));
                } else if (direction == Direction.VERTICALLY) {
                    cell = getCell(getRow(sheet, cellConfig.getRow() + baseRow + (identity * index)), cellConfig.getCol() + baseCol);
                }
            } else {
                CellMergedConfig mergedConfig = cellConfig.getMergedConfig();
                if (direction == Direction.HERIZONTAL) {
                    cell = getMergedCell(sheet, mergedConfig.getStartRowIndex() + baseRow, mergedConfig.getStartColIndex() + baseCol + (identity * index), mergedConfig.getEndRowIndex() + baseRow, mergedConfig.getEndColIndex() + baseCol + (identity * index));
                } else if (direction == Direction.VERTICALLY) {
                    cell = getMergedCell(sheet, mergedConfig.getStartRowIndex() + baseRow + (identity * index), mergedConfig.getStartColIndex() + baseCol, mergedConfig.getEndRowIndex() + baseRow + (identity * index), mergedConfig.getEndColIndex() + baseCol);
                }
            }
            if (cell != null) {
                //TODO 根据不同的类型设置不同的CellType
                cell.setCellValue(itemData.toString());
            }
            index++;
        }
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
