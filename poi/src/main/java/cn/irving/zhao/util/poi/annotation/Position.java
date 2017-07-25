package cn.irving.zhao.util.poi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 单元格位置，内嵌工作簿基础位置
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Position {

    /**
     * 行坐标，从0开始
     */
    int rowIndex();

    /**
     * 列坐标，从0开始
     */
    int colIndex();
}
