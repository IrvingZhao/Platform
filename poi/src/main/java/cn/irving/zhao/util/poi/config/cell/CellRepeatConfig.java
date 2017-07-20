package cn.irving.zhao.util.poi.config.cell;

import cn.irving.zhao.util.poi.annotation.cell.Repeatable;
import cn.irving.zhao.util.poi.enums.Direction;

/**
 * 单元格是否重复配置
 */
public class CellRepeatConfig {

    /**
     * @param direction 重复方向，可选值：{@link Direction#HERIZONTAL}，{@link Direction#VERTICALLY}
     * @param identity  每次递增值
     */
    public CellRepeatConfig(Direction direction, Integer identity) {
        this.direction = direction;
        this.identity = identity;
    }

    /**
     * @param repeatable 单元格重复注解配置
     */
    public CellRepeatConfig(Repeatable repeatable) {
        this.direction = repeatable.direction();
        this.identity = repeatable.identity();
    }

    private Direction direction;// 重复递增方向
    private Integer identity;//重复递增值

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }
}
