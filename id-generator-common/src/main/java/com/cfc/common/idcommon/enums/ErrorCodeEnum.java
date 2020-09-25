package com.cfc.common.idcommon.enums;

/**
 * 错误异常码
 *
 * @author zhangliang
 * @date 2020/9/23
 */
public enum ErrorCodeEnum {

    SUCCESS(100000, "成功"),
    INVALID_PARAM(200000, "入参不合法"),
    SERVICE_ERROR(300000, "服务失败");

    private int code;
    private String text;

    ErrorCodeEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    /**
     * 错误码类型
     *
     * @param code
     * @return
     */
    public static ErrorCodeEnum valueOf(int code) {
        for (ErrorCodeEnum errorCodeEnum : values()) {
            if (code == errorCodeEnum.getCode()) {
                return errorCodeEnum;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
