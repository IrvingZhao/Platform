package cn.irving.zhao.util.poi.annotation.cell;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 单元格对应属性配置信息
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Cell {
    /**
     * 列号
     */
    int col();

    /**
     * 行号
     */
    int row();


}
