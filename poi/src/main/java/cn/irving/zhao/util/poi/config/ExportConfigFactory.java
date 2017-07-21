package cn.irving.zhao.util.poi.config;

import cn.irving.zhao.util.poi.annotation.cell.Cell;
import cn.irving.zhao.util.poi.annotation.cell.MergedRegion;
import cn.irving.zhao.util.poi.annotation.cell.Repeatable;
import cn.irving.zhao.util.poi.annotation.sheet.InnerSheet;
import cn.irving.zhao.util.poi.annotation.sheet.Sheet;
import cn.irving.zhao.util.poi.config.cell.CellConfig;
import cn.irving.zhao.util.poi.config.sheet.InnerSheetConfig;
import cn.irving.zhao.util.poi.config.sheet.SheetConfig;
import org.apache.commons.collections.map.ReferenceMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * 导出配置信息工厂类
 */
public class ExportConfigFactory {

    protected static final ReferenceMap configCache = new ReferenceMap();
    private Logger logger = LoggerFactory.getLogger(ExportConfigFactory.class);

    public SheetConfig getSheetConfig(Class<?> clazz) {
        SheetConfig sheetConfig = (SheetConfig) configCache.get(clazz.getName());
        if (sheetConfig == null) {
            sheetConfig = generateSheetConfig(clazz);
            if (sheetConfig != null) {
                configCache.put(clazz.getName(), sheetConfig);
            }
        }
        return sheetConfig;
    }

    private SheetConfig generateSheetConfig(Class<?> clazz) {
        Sheet sheet = clazz.getAnnotation(Sheet.class);
        if (sheet == null) {
            logger.warn("{}未找到{}注解", clazz.getName(), Sheet.class.getName());
            return null;
        }
        SheetConfig result = new SheetConfig();
        result.setSheetName(sheet.name());
        return result;
    }

    private void addCell(Class<?> clazz, SheetConfig sheetConfig) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Cell cell = field.getAnnotation(Cell.class);
            InnerSheet innerSheet = field.getAnnotation(InnerSheet.class);
            //TODO  设置 获得值的方法
            if (cell != null) {
                Repeatable repeatable = field.getAnnotation(Repeatable.class);
                MergedRegion mergedRegion = field.getAnnotation(MergedRegion.class);
                sheetConfig.addCell(new CellConfig(cell, repeatable, mergedRegion));
            } else if (innerSheet != null) {
                Class<?> innerSheetClazz = field.getType();
                SheetConfig tempConfig = (SheetConfig) configCache.get(innerSheetClazz.getName());
                if (tempConfig == null) {
                    tempConfig = new SheetConfig();
                    Sheet innerSheetName = field.getType().getAnnotation(Sheet.class);
                    if (innerSheetName != null) {
                        tempConfig.setSheetName(innerSheetName.name());
                    }
                    addCell(field.getType(), tempConfig);//添加子sheet 单元格配置
                    configCache.put(field.getType().getName(), tempConfig);
                }
                //TODO innert sheet config  变更设置方式
                sheetConfig.addInnerSheet(new InnerSheetConfig(innerSheet, tempConfig));
            }
        }
    }


}
