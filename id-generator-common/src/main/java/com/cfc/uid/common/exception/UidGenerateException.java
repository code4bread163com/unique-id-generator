package com.cfc.uid.common.exception;

import com.cfc.uid.common.enums.ErrorCodeEnum;
import lombok.Data;

/**
 * 异常
 *
 * @author zhangliang
 * @date 2020/9/23
 */
@Data
public class UidGenerateException extends RuntimeException {

    /**
     * 异常错误码
     */
    private int errorCode;
    /**
     * 异常信息
     */
    private String errorMessage;

    public UidGenerateException() {
        this(ErrorCodeEnum.GENERATE_UID_ERROR.getCode(), ErrorCodeEnum.GENERATE_UID_ERROR.getText());
    }

    public UidGenerateException(String message) {
        super(message);
    }

    public UidGenerateException(int errorCode, String errorMessage) {
        super("(" + errorCode + ")" + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public UidGenerateException(String message, Throwable cause) {
        this(ErrorCodeEnum.SERVICE_ERROR.getCode(), message, cause);
    }

    public UidGenerateException(int errorCode, String errorMessage, Throwable cause) {
        super("(" + errorCode + ")" + errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
