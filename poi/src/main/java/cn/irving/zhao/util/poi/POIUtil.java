package cn.irving.zhao.util.poi;

import cn.irving.zhao.util.poi.config.CellConfig;
import cn.irving.zhao.util.poi.config.SheetConfig;
import cn.irving.zhao.util.poi.config.WorkBookConfig;
import cn.irving.zhao.util.poi.config.entity.MergedConfig;
import cn.irving.zhao.util.poi.config.entity.RepeatConfig;
import cn.irving.zhao.util.poi.config.entity.SheetCellConfig;
import cn.irving.zhao.util.poi.enums.Direction;
import cn.irving.zhao.util.poi.enums.SheetType;
import cn.irving.zhao.util.poi.enums.WorkbookType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.InputStream;
import java.util.List;

/**
 * POI 工具包
 */
public class POIUtil {


    /**
     * 获取WorkBook方法
     *
     * @param type           导出类型
     * @param workBookConfig 导出配置信息
     * @param data           被导出的数据
     * @return 工作簿对象
     */
    public Workbook export(WorkbookType type, WorkBookConfig workBookConfig, Object data, InputStream template) {
        Workbook result = type.getWorkbook(template);

        SheetConfig defaultSheetConfig = workBookConfig.getDefaultSheetConfig();
        if (defaultSheetConfig != null) {
            //TODO 写入默认sheet
        }
        List<SheetConfig> sheetConfigs = workBookConfig.getSheetConfigs();
        if (sheetConfigs != null) {
            sheetConfigs.forEach((sheetConfig) -> {
                //TODO 写入单个Sheet
            });
        }

        return result;
    }

    /**
     * 写入工作表数据
     *
     * @param sheet       工作表对象
     * @param sheetConfig 工作表配置信息
     * @param data        工作表对应的数据对象
     * @param loopIndex   循环调用时的索引
     */
    private void writeSheetData(Sheet sheet, SheetConfig sheetConfig, Object data, int rowIv, int colIv, int loopIndex) {
        Sheet writeDataSheet;
        if (sheetConfig.getSheetType() == SheetType.INNER) {
            writeDataSheet = sheet;
        } else {
            //TODO 加入sheet名称构建器
            //TODO 获得当前循环索引，调用sheet名称构建
            writeDataSheet = getSheet(sheet.getWorkbook(), sheetConfig.getName());
            // 新创建 sheet 对象时，重置 rowIv 和 colIv
            rowIv = colIv = 0;
        }
        if (writeDataSheet == null) {
            throw new RuntimeException("数据写入工作表创建异常");
        }

        SheetCellConfig sheetCellConfig = sheetConfig.getSheetCellConfig(); // 获得 工作表 配置信息
        List<CellConfig> cellConfigs = sheetCellConfig.getCellConfigs();// 获得单元格配置
        for (CellConfig item : cellConfigs) {
            writeCell(writeDataSheet, item, data, rowIv, colIv);//写入单元格数据
        }
        List<SheetConfig> sheetConfigs = sheetCellConfig.getRefSheetConfigs();//获得关联工作表配置信息
        for (SheetConfig itemSheetConfig : sheetConfigs) {
            // 在一个单元表 中 的 多个单元表配置信息，每次进入新的单元表配置时，该单元表的 rowIv、colIv 应与最初rowIv、colIv 一致
            int itemSheetRowIv = rowIv;
            int itemSheetColIv = colIv;
            //判断是否为循环引入
            RepeatConfig repeatConfig = itemSheetConfig.getRepeatConfig();
            Object itemSheetData = itemSheetConfig.getData(data);
            if (repeatConfig == null) {
                //写入工作表
                writeSheetData(writeDataSheet, itemSheetConfig, data, itemSheetConfig.getBaseRow() + itemSheetRowIv, itemSheetConfig.getBaseCol() + itemSheetColIv, 0);
            } else {
                Direction direction = repeatConfig.getDirection();
                int identity = repeatConfig.getIdentity();
                int max = repeatConfig.getMax();
                int currentIndex = 0;
                if (Iterable.class.isInstance(itemSheetData)) {
                    for (Object itemSheetDataItem : ((Iterable) itemSheetData)) {
                        if (max > 0 && currentIndex >= max) {//最大循环次数判断
                            break;
                        }
                        writeSheetData(writeDataSheet, itemSheetConfig, itemSheetDataItem, itemSheetRowIv, itemSheetColIv, currentIndex);
                        if (direction == Direction.HERIZONTAL) {
                            itemSheetColIv += identity;
                        } else if (direction == Direction.VERTICALLY) {
                            itemSheetRowIv += identity;
                        } else if (direction == Direction.BOTH) {
                            itemSheetRowIv += identity;
                            itemSheetColIv += identity;
                        }
                        currentIndex++;
                    }
                } else {
                    throw new RuntimeException(itemSheetData.getClass().getName() + "不是一个有效的 Iterable 对象");
                }

            }
        }
    }

    /**
     * 写入单元格数据信息，判断是否为Repeat
     *
     * @param sheet      工作表对象
     * @param cellConfig 单元格配置信息
     * @param data       单元格数据所属对象
     */
    private void writeCell(Sheet sheet, CellConfig cellConfig, Object data, int rowIv, int colIv) {
        RepeatConfig repeatConfig = cellConfig.getRepeatConfig();
        Object cellData = cellConfig.getData(data);
        if (repeatConfig == null) {
            writeCellData(sheet, cellConfig, cellData, rowIv, colIv);
        } else {
            int currentIndex = 0;
            int max = repeatConfig.getMax();
            int identity = repeatConfig.getIdentity();
            Direction dir = repeatConfig.getDirection();
            if (Iterable.class.isInstance(cellData)) {
                for (Object dataItem : ((Iterable) cellData)) {
                    if (max > 0 && currentIndex >= max) {//最大循环次数判断
                        break;
                    }
                    writeCellData(sheet, cellConfig, dataItem, rowIv, colIv);
                    if (dir == Direction.HERIZONTAL) {
                        colIv += identity;
                    } else if (dir == Direction.VERTICALLY) {
                        rowIv += identity;
                    } else if (dir == Direction.BOTH) {
                        rowIv += identity;
                        colIv += identity;
                    }
                    currentIndex++;
                }
            } else {
                throw new RuntimeException(cellData.getClass().getName() + "不是一个有效的 Iterable 对象");
            }
        }
    }


    /**
     * 写入单元格内容
     *
     * @param sheet      数据所在单元表
     * @param cellConfig 单元格配置信息
     * @param cellData   单元格对象
     * @param rowIv      行坐标修正值
     * @param colIv      列坐标修正值
     */
    private void writeCellData(Sheet sheet, CellConfig cellConfig, Object cellData, int rowIv, int colIv) {//写入cell 数据
        MergedConfig mergedConfig = cellConfig.getMergedConfig();
        Cell cell;
        if (mergedConfig == null) {
            cell = getCell(getRow(sheet, cellConfig.getRowIndex() + rowIv), cellConfig.getCellIndex() + colIv);
        } else {
            cell = getMergedCell(sheet,
                    mergedConfig.getStartRowIndex() + rowIv,
                    mergedConfig.getStartColIndex() + colIv,
                    mergedConfig.getEndRowIndex() + rowIv,
                    mergedConfig.getEndColIndex() + colIv);
        }
        //TODO 设置CellFormat
        cell.setCellValue(String.valueOf(cellData));
    }


    /**
     * 获得合并单元格
     *
     * @param sheet    工作表对象
     * @param startRow 合并开始行
     * @param startCol 合并开始列
     * @param endRow   合并结束行
     * @param endCol   合并结束列
     * @return 单元格对象
     */
    private Cell getMergedCell(Sheet sheet, int startRow, int startCol, int endRow, int endCol) {
        sheet.addMergedRegion(new CellRangeAddress(startRow, endRow, startCol, endCol));
        return getCell(getRow(sheet, startRow), startCol);
    }

    /**
     * 获取工作簿中的工作表
     *
     * @param workbook  工作簿对象
     * @param sheetName 工作表名称
     * @return 工作表对象
     */
    private Sheet getSheet(Workbook workbook, String sheetName) {
        return workbook.getSheet(sheetName) == null ? workbook.createSheet(sheetName) : workbook.getSheet(sheetName);
    }

    /**
     * 获取工作表中的行
     *
     * @param sheet    工作表
     * @param rowIndex 行坐标
     * @return 行对象
     */
    private Row getRow(Sheet sheet, int rowIndex) {
        return sheet.getRow(rowIndex) == null ? sheet.createRow(rowIndex) : sheet.getRow(rowIndex);
    }

    /**
     * 获取单元格对象
     *
     * @param row       单元格所在行
     * @param cellIndex 单元格纵坐标
     * @return 单元格对象
     */
    private Cell getCell(Row row, int cellIndex) {
        return row.getCell(cellIndex) == null ? row.createCell(cellIndex) : row.getCell(cellIndex);
    }


}
