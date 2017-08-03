package cn.irving.zhao.util.poi.config;

import org.apache.commons.collections.map.ReferenceMap;

/**
 * 工作表名称格式化对象工厂
 */
public class SheetNameFormatterFactory {
    private SheetNameFormatterFactory() {
    }

    private final ReferenceMap FORMATTERCACHE = new ReferenceMap();

    private static final SheetNameFormatterFactory me = new SheetNameFormatterFactory();

    public static SheetNameFormatter getSheetNameFormatter(Class<? extends SheetNameFormatter> clazz) {
        SheetNameFormatter result = (SheetNameFormatter) me.FORMATTERCACHE.get(clazz.getName());
        if (result == null) {
            synchronized (me) {
                if (me.FORMATTERCACHE.get(clazz.getName()) == null) {
                    try {
                        result = clazz.newInstance();
                        me.FORMATTERCACHE.put(clazz.getName(), result);
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;

    }

}
