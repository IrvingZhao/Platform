package cn.irving.zhao.util.poi.annotation;

import cn.irving.zhao.util.poi.enums.RepeatMethod;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Sheet {
    String sheetName() default "";

    int sheetIndex() default -1;
}
