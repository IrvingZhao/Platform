package cn.irving.zhao.util.poi.annotation.config;

import cn.irving.zhao.util.poi.formatter.ColumnFormatter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Formatter {
    Class<? extends ColumnFormatter> value();
}
