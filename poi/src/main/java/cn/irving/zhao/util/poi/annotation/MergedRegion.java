package cn.irving.zhao.util.poi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 单元格合并配置
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MergedRegion {

    /**
     * 合并单元格开始位置
     */
    Cell start();

    /**
     * 合并单元格结束位置
     */
    Cell end();
}
