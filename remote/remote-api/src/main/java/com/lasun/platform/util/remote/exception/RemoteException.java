package com.lasun.platform.util.remote.exception;

import com.lasun.platform.util.base.exception.BaseUnCheckException;

/**
 * 请求异常
 *
 * @author Irving.Zhao
 * @version 1.0
 * @since 1.0
 */
public class RemoteException extends BaseUnCheckException {
    /**
     * <p>异常信息可使用占位符的形式进行格式化输出</p>
     * <p>格式化语句为<code>String.format</code></p>
     *
     * @param message
     * @param cause
     * @param param
     */
    public RemoteException(String message, Throwable cause, Object... param) {
        super(message, cause, param);
    }
}
