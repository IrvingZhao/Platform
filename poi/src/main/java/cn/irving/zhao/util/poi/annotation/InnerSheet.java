package cn.irving.zhao.util.poi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 内嵌工作簿，在一个工作簿中嵌入另一个工作簿
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InnerSheet {
    /**
     * 基准坐标
     */
    Position position();
}
