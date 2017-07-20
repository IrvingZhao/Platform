package cn.irving.zhao.util.poi.annotation.sheet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 一个Sheet引入另一个sheet配置
 * <p>
 * 两个实体类属于包含关系，则被包含的实体类需要进行baseRow和baseCol设置，被包含的实体类中的cell设置的行列坐标将增加base坐标
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface InnerSheet {

    /**
     * 基础行
     */
    int baseRow();

    /**
     * 基础列
     */
    int baseCol();
}
