package cn.irving.zhao.util.poi.config;

import cn.irving.zhao.util.poi.annotation.*;
import cn.irving.zhao.util.poi.config.cell.CellConfig;
import cn.irving.zhao.util.poi.config.sheet.InnerSheetConfig;
import cn.irving.zhao.util.poi.config.sheet.SheetConfig;
import org.apache.commons.collections.map.ReferenceMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * excel配置工厂类
 */
public final class ExcelConfigFactory {
    protected final static ReferenceMap configCache = new ReferenceMap();
    private Logger logger = LoggerFactory.getLogger(ExcelConfigFactory.class);
    private static final ExcelConfigFactory me = new ExcelConfigFactory();

    /**
     * 获得一个类的所有配置信息
     *
     * @param clazz 类对象
     * @return 工作簿配置信息
     */
    public static SheetConfig getConfig(Class<?> clazz) {
        SheetConfig sheetConfig = (SheetConfig) configCache.get(clazz.getName());
        if (sheetConfig == null) {
            sheetConfig = new SheetConfig();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Cell.class)) {
                    me.addCell(sheetConfig, field);
                } else if (field.isAnnotationPresent(InnerSheet.class)) {
                    me.addInnerSheet(sheetConfig, field);
                }
            }
            Class<?> superClazz = clazz.getSuperclass();
            if (superClazz != Object.class) {
                SheetConfig superConfig = getConfig(superClazz);
                sheetConfig.addAllCell(superConfig.getCells());
                sheetConfig.addAllInnerSheet(superConfig.getInnerSheets());
            }
            configCache.put(clazz.getName(), sheetConfig);
        }
        return sheetConfig;
    }

    private void addCell(SheetConfig sheetConfig, Field field) {
        Cell cell = field.getAnnotation(Cell.class);
        Repeatable repeatable = field.getAnnotation(Repeatable.class);
        MergedRegion mergedRegion = field.getAnnotation(MergedRegion.class);
        //TODO 添加 获取数据的方法添加
        sheetConfig.addCell(new CellConfig(cell, repeatable, mergedRegion, field));
    }

    private void addInnerSheet(SheetConfig sheetConfig, Field field) {
        InnerSheet innerSheet = field.getAnnotation(InnerSheet.class);
        SheetConfig innerSheetConfig = getConfig(field.getType());
        //TODO 添加对象获取方法
        sheetConfig.addInnerSheet(new InnerSheetConfig(innerSheet, innerSheetConfig, field));
    }

}
