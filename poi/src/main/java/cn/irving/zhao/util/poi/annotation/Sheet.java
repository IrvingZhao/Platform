package cn.irving.zhao.util.poi.annotation;

import java.lang.annotation.*;

/**
 * 单元页配置信息
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Sheet {
    /**
     * 表格页签名称
     */
    String name();
}
