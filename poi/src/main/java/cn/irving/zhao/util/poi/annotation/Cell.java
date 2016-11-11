package cn.irving.zhao.util.poi.annotation;

import cn.irving.zhao.util.poi.annotation.config.CellPosition;
import cn.irving.zhao.util.poi.enums.RepeatMethod;
import cn.irving.zhao.util.poi.formatter.ColumnFormatter;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Cell {
    CellPosition[] value();//坐标
}
