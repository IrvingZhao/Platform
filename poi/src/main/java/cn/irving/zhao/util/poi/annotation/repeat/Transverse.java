package cn.irving.zhao.util.poi.annotation.repeat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface Transverse {
    int identity() default 1;//每次坐标增长值

    int max() default -1;//坐标增长最大值
}
