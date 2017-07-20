package cn.irving.zhao.util.poi.annotation.cell;

import cn.irving.zhao.util.poi.enums.Direction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 循环写入column
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Repeatable {

    /**
     * 循环方向
     * <p>默认为，{@link Direction#HERIZONTAL}</p>
     * <p>可选值：</p>
     * <ul>
     * <li>{@link Direction#HERIZONTAL} 横向，从左向右</li>
     * <li>{@link Direction#VERTICALLY} 纵向，从上到下</li>
     * </ul>
     */
    Direction direction() default Direction.HERIZONTAL;

    /**
     * 增长值
     * <p>
     * 负值时，逆向排列
     * </p>
     */
    int identity() default 1;
}
