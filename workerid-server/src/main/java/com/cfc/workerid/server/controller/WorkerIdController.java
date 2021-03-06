package com.cfc.workerid.server.controller;

import com.cfc.uid.common.enums.ErrorCodeEnum;
import com.cfc.workerid.server.annotation.MonitorAnnotation;
import com.cfc.workerid.server.annotation.TransOutputAnnotation;
import com.cfc.workerid.api.GetWorkerIdRequest;
import com.cfc.workerid.api.GetWorkerIdResponse;
import com.cfc.workerid.api.TransInput;
import com.cfc.workerid.api.TransOutput;
import com.cfc.workerid.server.service.WorkerIdService;
import com.cfc.workerid.server.utils.ValidatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * workerID controller
 *
 * @author zhangliang
 * @date 2020/9/23
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/workerId/")
@Api(value = "获取workerId服务", tags = "获取workerId服务", produces = "JSON")
@TransOutputAnnotation
public class WorkerIdController {

    @Autowired
    private WorkerIdService workerIdService;

    /**
     * 获取workerID
     *
     * @param request
     * @return
     */
    @MonitorAnnotation
    @RequestMapping(value = "getWorkerId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "获取workerID", notes = "获取workerID", httpMethod = "POST")
    public TransOutput<GetWorkerIdResponse> getWorkerId(
            @ApiParam(name = "request", required = true, value = "获取workerID入参")
            @RequestBody TransInput<GetWorkerIdRequest> request) {

        ValidatorUtil.validateBasicAndBusiParam(request);
        return new TransOutput<>(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getText(),
                new GetWorkerIdResponse(workerIdService.getWorkerId(request)));
    }

}
