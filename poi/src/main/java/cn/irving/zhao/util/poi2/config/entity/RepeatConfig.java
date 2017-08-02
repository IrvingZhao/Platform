package cn.irving.zhao.util.poi2.config.entity;

import cn.irving.zhao.util.poi2.annonation.Repeatable;
import cn.irving.zhao.util.poi2.enums.Direction;

/**
 * 循环配置信息
 */
public class RepeatConfig {

    public RepeatConfig() {
    }

    public RepeatConfig(Repeatable repeatable) {
        this.identity = repeatable.identity();
        this.direction = repeatable.direction();
        this.max = repeatable.max();
    }

    public RepeatConfig(int identity, Direction direction) {
        this(identity, direction, -1);
    }

    public RepeatConfig(int identity, Direction direction, int max) {
        this.identity = identity;
        this.direction = direction;
        this.max = max;
    }

    private int identity;//递增值

    private Direction direction;//方向

    private int max;

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
