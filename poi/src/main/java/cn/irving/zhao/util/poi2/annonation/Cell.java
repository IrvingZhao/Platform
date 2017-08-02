package cn.irving.zhao.util.poi2.annonation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 单元格配置信息
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cell {
    /**
     * 行坐标
     */
    int rowIndex();

    /**
     * 列坐标
     */
    int colIndex();
}
