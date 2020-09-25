package com.cfc.common.workerid.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回信息
 *
 * @author zhangliang
 * @date 2020/9/23
 */
@ApiModel(description = "通用返回信息")
@Data
public class TransOutput<T> implements Serializable {

    /**
     * 错误码
     */
    @ApiModelProperty(value = "错误码。\n" +
            "100000 成功\n" +
            "200000 入参不合法\n" +
            "300000 服务失败", required = true)
    private int transCode;

    /**
     * 错误信息
     */
    @ApiModelProperty(value = "错误信息", required = true)
    private String transMessage;

    public TransOutput() {
    }

    public TransOutput(int transCode, String transMessage, T data) {
        this.transCode = transCode;
        this.transMessage = transMessage;
        this.data = data;
    }

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据")
    private T data;
}
