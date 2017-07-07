package cn.irving.zhao.util.poi;

import cn.irving.zhao.util.poi.config.*;
import cn.irving.zhao.util.poi.enums.OutputType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;

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

            cn.irving.zhao.util.poi.config.Sheet sheetConfig = data.getClass().getAnnotation(cn.irving.zhao.util.poi.config.Sheet.class);

            Sheet sheet = getSheet(workbook, sheetConfig.name());

            writeData(sheet, data);

            workbook.write(new FileOutputStream(file));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeData(Sheet sheet, Object data) {
        try {
            Field[] fields = data.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field item = fields[i];
                item.setAccessible(true);
                cn.irving.zhao.util.poi.config.Cell cellConfig = item.getAnnotation(cn.irving.zhao.util.poi.config.Cell.class);
                if (cellConfig != null) {
                    //TODO  是否循环判断
                    //TODO  是否为实体类 判断
                    //TODO  判断是否为新sheet判断
                    //TODO  判断行号是否叠加判断   判断 列号 是否叠加 判断
                    int rowIndex = cellConfig.row();
                    int cellIndex = cellConfig.col();
                    Row row = getRow(sheet, rowIndex);
                    Cell cell = getCell(row, cellIndex);
                    cell.setCellValue(String.valueOf(item.get(data)));
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
