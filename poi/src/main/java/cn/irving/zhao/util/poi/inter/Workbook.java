package cn.irving.zhao.util.poi.inter;

import cn.irving.zhao.util.poi.config.workbook.WorkbookConfig;
import cn.irving.zhao.util.poi.annotation.OuterSheet;

/**
 * excel文件接口,继承此接口的类中，只会读取添加{@link OuterSheet}注解的属性
 */
public interface Workbook {
    default WorkbookConfig getConfig() {
        
        return null;
    }
}
