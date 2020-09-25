package com.cfc.common.workerid.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 获取workerid请求报文
 *
 * @author zhangliang
 * @date 2020/9/23
 */
@Data
public class GetWorkerIdRequest {
    /**
     * 服务器地址
     */
    @ApiModelProperty(notes = "服务器地址", required = true)
    @NotBlank(message = "服务器地址不能为空")
    private String hostName;

    /**
     * 服务器端口
     */
    @ApiModelProperty(notes = "服务器端口", required = true)
    @NotBlank(message = "服务器端口不能为空")
    private String port;

    /**
     * 服务器类型
     */
    @ApiModelProperty(notes = "服务器类型", required = true)
    @NotNull(message = "服务器类型不能为空")
    private WorkerNodeType type;

    /**
     * 请求时间
     */
    @ApiModelProperty(notes = "请求时间")
    private Date launchDate = new Date();
}
