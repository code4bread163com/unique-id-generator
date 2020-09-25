package com.cfc.common.idcommon.exception;

import com.cfc.common.idcommon.enums.ErrorCodeEnum;
import lombok.Data;

/**
 * 异常
 *
 * @author zhangliang
 * @date 2020/9/23
 */
@Data
public class BizException extends RuntimeException {

    /**
     * 异常错误码
     */
    private int errorCode;
    /**
     * 异常信息
     */
    private String errorMessage;

    public BizException(String message) {
        super(message);
    }

    public BizException(int errorCode, String errorMessage) {
        super("(" + errorCode + ")" + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BizException(String message, Throwable cause) {
        this(ErrorCodeEnum.SERVICE_ERROR.getCode(), message, cause);
    }

    public BizException(int errorCode, String errorMessage, Throwable cause) {
        super("(" + errorCode + ")" + errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
