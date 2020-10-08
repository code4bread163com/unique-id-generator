package com.cfc.uid.generate.interfaces;

import com.cfc.workerid.api.GetWorkerIdRequest;
import com.cfc.workerid.api.GetWorkerIdResponse;
import com.cfc.workerid.api.TransInput;
import com.cfc.workerid.api.TransOutput;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author zhangliang
 * @date 2020/10/6
 */
//@FeignClient(name = "workerid-server")
//@Component
public interface WorkerIdInterface {

    @RequestMapping(value = "getWorkerId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    TransOutput<GetWorkerIdResponse> getWorkerId(TransInput<GetWorkerIdRequest> request);
}
