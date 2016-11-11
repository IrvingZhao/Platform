package cn.irving.zhao.util.poi.annotation.config;

import cn.irving.zhao.util.poi.annotation.Cell;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Cell.class)
public @interface CellPosition {
    int rowIndex() default 0;

    int cellIndex() default 0;
}
