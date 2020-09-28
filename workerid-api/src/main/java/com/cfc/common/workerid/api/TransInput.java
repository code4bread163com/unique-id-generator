package com.cfc.common.workerid.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 通用请求信息
 *
 * @author zhangliang
 * @date 2020/9/23
 */
@Data
@ApiModel(description = "通用请求信息")
public class TransInput<T> implements Serializable {

    /**
     * 应用名
     */
    @ApiModelProperty(required = true, notes = "应用名")
    @NotBlank(message = "应用名不能为空")
    private String appName;

    /**
     * 具体请求参数
     */
    @ApiModelProperty(required = true, notes = "具体请求参数")
    @NotNull(message = "具体请求参数不能为空")
    private T data;
}