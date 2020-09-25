package com.cfc.common.workerid.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取workerid返回报文
 *
 * @author zhangliang
 * @date 2020/9/23
 */
@Data
public class GetWorkerIdResponse {
    @ApiModelProperty(notes = "生成workId")
    private long workerId;
}
