package cn.irving.zhao.util.poi;

import cn.irving.zhao.util.poi.annotation.MergedRegion;
import cn.irving.zhao.util.poi.annotation.Repeatable;
import cn.irving.zhao.util.poi.enums.Direction;
import cn.irving.zhao.util.poi.enums.OutputType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * POI工具类
 */
public class POIUtil {

    public void writeExcel(Object data, String output, OutputType outputType) {
        try {
            File file = new File(output);
            if (!file.exists()) {
                file.createNewFile();
            }
            Workbook workbook = outputType.createWorkbook();

            cn.irving.zhao.util.poi.annotation.Sheet sheetConfig = data.getClass().getAnnotation(cn.irving.zhao.util.poi.annotation.Sheet.class);

            Sheet sheet = getSheet(workbook, sheetConfig.name());

            writeData(sheet, data);

            workbook.write(new FileOutputStream(file));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void writeObject(Sheet sheet, Object data, int startRow, int startCol) {

    }

    private void writeValue(Sheet sheet, Object data, int startRow, int startCol) {

    }

    private void writeList(Sheet sheet, Object data) {

    }

    private void writeData(Sheet sheet, Object data) {
        try {
            Field[] fields = data.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field item = fields[i];
                item.setAccessible(true);
                cn.irving.zhao.util.poi.annotation.Cell cellConfig = item.getAnnotation(cn.irving.zhao.util.poi.annotation.Cell.class);
                MergedRegion mergedRegion = item.getAnnotation(MergedRegion.class);
                if (mergedRegion != null) {
                    cn.irving.zhao.util.poi.annotation.Cell start = mergedRegion.start();
                    cn.irving.zhao.util.poi.annotation.Cell end = mergedRegion.end();
                    Repeatable repeatable = item.getAnnotation(Repeatable.class);
                    if (repeatable != null) {
                        Collection<?> collection = (Collection<?>) item.get(data);
                        //TODO 根据跨行跨列的大小 推算每次叠加值
                        //TODO 根据方向推算叠加行  或 叠加列
                        int identity = repeatable.identity();//TODO 根据是否为反向  计算值
                        if (repeatable.direction() == Direction.HERIZONTAL) {
                            if (identity < end.col() - start.col() + 1) {
                                identity = end.col() - start.col() + 1;
                            }
                        } else if (repeatable.direction() == Direction.VERTICALLY) {
                            if (identity < end.row() - start.row() + 1) {
                                identity = end.row() - start.row() + 1;
                            }
                        }
                        for (int j = 0; j < collection.size(); j++) {
                            //TODO 根据横向循环 或属相循环 进行不同的索引增加
                            if (repeatable.direction() == Direction.HERIZONTAL) {
                                sheet.addMergedRegion(new CellRangeAddress(start.row(), end.row(), start.col() + (j * identity), end.col() + (j * identity)));
                                Cell cell = getCell(getRow(sheet, start.row()), start.col() + identity * j);
                                cell.setCellValue(j);
                            } else if (repeatable.direction() == Direction.VERTICALLY) {
                                sheet.addMergedRegion(new CellRangeAddress(start.row() + identity * j, end.row() + identity * j, start.col(), end.col()));
                                Cell cell = getCell(getRow(sheet, start.row() + identity * j), start.col());
                                cell.setCellValue(j);
                            }
                        }

                    } else {
                        sheet.addMergedRegion(new CellRangeAddress(start.row(), end.row(), start.col(), end.col()));
                        Row row = getRow(sheet, start.row());
                        Cell cell = getCell(row, start.col());
                        cell.setCellValue(String.valueOf(item.get(data)));
                    }
                } else if (cellConfig != null) {
                    //TODO  是否循环判断
                    //TODO  是否为实体类 判断
                    //TODO  判断是否为新sheet判断
                    //TODO  判断行号是否叠加判断   判断 列号 是否叠加 判断
                    final int[] rowIndex = {cellConfig.row()};
                    final int[] cellIndex = {cellConfig.col()};
                    Repeatable repeatable = item.getAnnotation(Repeatable.class);
                    if (repeatable != null) {
                        Collection<?> collection = (Collection<?>) item.get(data);
                        int identity = repeatable.identity();
                        final Direction[] direction = {repeatable.direction()};
                        collection.forEach((dataItem) -> {
                            Row row = getRow(sheet, rowIndex[0]);
                            Cell cell = getCell(row, cellIndex[0]);
                            cell.setCellValue(String.valueOf(dataItem));
                            if (direction[0] == Direction.HERIZONTAL) {
                                cellIndex[0] += identity;
                            } else if (direction[0] == Direction.VERTICALLY) {
                                rowIndex[0] = rowIndex[0] + identity;
                            }
                            System.out.println(rowIndex[0]);
                        });
                    } else {
                        Row row = getRow(sheet, rowIndex[0]);
                        Cell cell = getCell(row, cellIndex[0]);
                        cell.setCellValue(String.valueOf(item.get(data)));//TODO 不同类型的数据
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
