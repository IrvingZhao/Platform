package cn.irving.zhao.util.remote.mina2.core.method;

/**
 * mina 消息方法
 */
@FunctionalInterface
public interface MinaMessageMethod<T, R> {

    R execute(T data);

}
