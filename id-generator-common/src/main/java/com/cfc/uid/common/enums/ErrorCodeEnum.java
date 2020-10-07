package com.cfc.uid.common.enums;

/**
 * 错误异常码
 *
 * @author zhangliang
 * @date 2020/9/23
 */
public enum ErrorCodeEnum {

    SUCCESS(100000, "成功"),
    INVALID_PARAM(200000, "入参不合法"),
    SERVICE_ERROR(300000, "服务失败"),

    INITIALIZED_WORKER_ID_ERROR(300001, "初始化workerId失败"),
    GENERATE_UID_ERROR(300002, "生成唯一Id失败");


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
