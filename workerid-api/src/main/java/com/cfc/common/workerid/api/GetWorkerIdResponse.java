package com.cfc.common.workerid.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取workerid返回报文
 *
 * @author zhangliang
 * @date 2020/9/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetWorkerIdResponse {
    @ApiModelProperty(notes = "生成workId")
    private long workerId;
}
