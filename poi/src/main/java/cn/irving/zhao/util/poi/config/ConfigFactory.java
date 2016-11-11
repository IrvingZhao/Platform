package cn.irving.zhao.util.poi.config;

import cn.irving.zhao.util.poi.annotation.Cell;
import cn.irving.zhao.util.poi.annotation.Sheet;
import cn.irving.zhao.util.poi.annotation.Template;
import cn.irving.zhao.util.poi.annotation.config.CellPosition;
import cn.irving.zhao.util.poi.annotation.config.Formatter;
import cn.irving.zhao.util.poi.annotation.config.Primary;
import cn.irving.zhao.util.poi.annotation.repeat.Portrait;
import cn.irving.zhao.util.poi.annotation.repeat.Transverse;
import cn.irving.zhao.util.poi.enums.RepeatMethod;
import cn.irving.zhao.util.poi.exception.ConfigLoadException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

//TODO 提出配置公共方法内容，添加多配置源
public final class ConfigFactory {
    private static final Map<String, SheetConfig> CONFIG_CACHE = new HashMap<>();

    private ConfigFactory() {
    }

    public static SheetConfig getSheetConfig(Class<?> clazz) {
        String className = clazz.getName();
        if (CONFIG_CACHE.get(className) == null) {
            SheetConfig config = generateSheetConfig(clazz);
            CONFIG_CACHE.put(className, config);
            return config;
        }
        return CONFIG_CACHE.get(className);
    }

    private static SheetConfig generateSheetConfig(Class<?> clazz) {
        SheetConfig result = new SheetConfig();

        Sheet sheetConfig = clazz.getAnnotation(Sheet.class);
        Template template = clazz.getAnnotation(Template.class);

        //设置工作簿配置信息
        if (sheetConfig == null || sheetConfig.sheetIndex() == -1 || sheetConfig.sheetName().equals("")) {
            throw new ConfigLoadException("加载类配置信息异常");
        }
        result.setSheetIndex(sheetConfig.sheetIndex());
        result.setSheetName(sheetConfig.sheetName());

        //设置template模板
        if (template != null) {
            result.setTemplate(template.value());
        }

        //遍历类中所有的属性
        //TODO 修改多注解加载方案
        Class<?> tempClass = clazz;
        while (tempClass != Object.class) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field item : fields) {
                Cell cell = item.getAnnotation(Cell.class);
                Portrait portrait = item.getAnnotation(Portrait.class);
                Transverse transverse = item.getAnnotation(Transverse.class);
                Primary primary = item.getAnnotation(Primary.class);
                Formatter formatter = item.getAnnotation(Formatter.class);
                if (cell == null) {
                    break;
                }
                //设置单元格坐标
                CellConfig config = new CellConfig();
                for (CellPosition position : cell.value()) {
                    config.addPosition(position.rowIndex(), position.cellIndex());
                }
                //设置循环递增
                if (portrait != null) {
                    config.setRepeat(RepeatMethod.PORTRAIT);
                    config.setPortraitIdentity(portrait.identity());
                    //TODO 根据max是否使用进行设置
                }
                if (transverse != null) {
                    config.setRepeat(RepeatMethod.TRANSVERSE);
                    config.setTransverseIdentity(transverse.identity());
                }
                if (transverse != null && portrait != null) {
                    config.setRepeat(RepeatMethod.BOTH);
                }
                if (primary != null) {
                    config.setPrimary(true);
                }
                if (formatter != null) {
                    config.setFormatter(null);//TODO 构建formatter
                }
                //设置field
                item.setAccessible(true);//TODO 后续替换为动态字节码
                config.setCellField(item);

                result.addColumn(config);
            }
            tempClass = clazz.getSuperclass();
        }
        return result;
    }

}
