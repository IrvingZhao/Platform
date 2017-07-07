package cn.irving.zhao.util.poi.enums;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 输出类型枚举
 */
public enum OutputType {
    DOC {
        @Override
        public Workbook createWorkbook() {
            return new HSSFWorkbook();
        }
    }, DOCX {
        @Override
        public Workbook createWorkbook() {
            return new XSSFWorkbook();
        }
    }, SDOCX {
        @Override
        public Workbook createWorkbook() {
            return new SXSSFWorkbook(1000);
        }
    };

    public abstract Workbook createWorkbook();
}
