package com.cfc.common.workerid.controller;

import com.cfc.common.idcommon.enums.ErrorCodeEnum;
import com.cfc.common.workerid.annotation.MonitorAnnotation;
import com.cfc.common.workerid.annotation.TransOutputAnnotation;
import com.cfc.common.workerid.api.GetWorkerIdRequest;
import com.cfc.common.workerid.api.GetWorkerIdResponse;
import com.cfc.common.workerid.api.TransInput;
import com.cfc.common.workerid.api.TransOutput;
import com.cfc.common.workerid.core.DisposableWorkerIdAssigner;
import com.cfc.common.workerid.dao.WorkerNodeDAO;
import com.cfc.common.workerid.util.ValidatorUtil;
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
import org.springframework.web.bind.annotation.ResponseBody;
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

    private final WorkerNodeDAO WorkerNodeDAO;

    @Autowired
    private DisposableWorkerIdAssigner disposableWorkerIdAssigner;

    public WorkerIdController(WorkerNodeDAO workerNodeDAO) {
        this.WorkerNodeDAO = workerNodeDAO;
    }


    /**
     * 获取workerID
     *
     * @param request
     * @return
     */
    @MonitorAnnotation(name = "查询客户账户余额，用于将多充值的溢缴款提现")
    @RequestMapping(value = "getWorkerId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "获取workerID", notes = "获取workerID", httpMethod = "POST")
    public TransOutput<GetWorkerIdResponse> getWorkerId(
            //@ApiParam(name = "request", required = true, value = "获取workerID入参")
            @RequestBody TransInput<GetWorkerIdRequest> request) {

        ValidatorUtil.validateBasicAndBusiParam(request);

        disposableWorkerIdAssigner.assignWorkerId();
        //WorkerNodeDAO.addWorkerNode();
        return new TransOutput<>(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getText(),
                new GetWorkerIdResponse());
    }


    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public String getWorkerId1() {
        disposableWorkerIdAssigner.assignWorkerId();

        return "succ";
    }
}
