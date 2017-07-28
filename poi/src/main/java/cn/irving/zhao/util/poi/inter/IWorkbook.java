package cn.irving.zhao.util.poi.inter;

import cn.irving.zhao.util.poi.config.ExcelConfigFactory;
import cn.irving.zhao.util.poi.config.sheet.OuterSheetConfig;
import cn.irving.zhao.util.poi.config.sheet.SheetConfig;
import cn.irving.zhao.util.poi.config.workbook.WorkbookConfig;
import cn.irving.zhao.util.poi.annotation.OuterSheet;

import java.lang.reflect.Field;

/**
 * excel文件接口,继承此接口的类中，只会读取添加{@link OuterSheet}注解的属性
 */
public interface IWorkbook {
    default WorkbookConfig getConfig() {
        WorkbookConfig result = new WorkbookConfig();
        Class<?> type = this.getClass();
        while (type != Object.class) {
            Field[] fields = type.getDeclaredFields();
            for (Field field : fields) {
                OuterSheet outerSheet = field.getAnnotation(OuterSheet.class);
                if (outerSheet != null) {
                    SheetConfig sheetConfig = ExcelConfigFactory.getConfig(field.getType());
                    OuterSheetConfig outerSheetConfig = new OuterSheetConfig(outerSheet.name(), sheetConfig, field);
                    result.addOuterSheetConfig(outerSheetConfig);
                }
            }
            type = type.getSuperclass();
        }
        return result;
    }
}
