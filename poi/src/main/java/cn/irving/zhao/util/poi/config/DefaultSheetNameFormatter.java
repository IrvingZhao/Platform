package cn.irving.zhao.util.poi.config;

/**
 * 默认处理
 */
public class DefaultSheetNameFormatter implements SheetNameFormatter {
    @Override
    public String getSheetName(String sheetName, int loopIndex) {
        return sheetName;
    }
}
