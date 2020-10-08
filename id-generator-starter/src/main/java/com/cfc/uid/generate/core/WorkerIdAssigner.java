package com.cfc.uid.generate.core;

/**
 * @author zhangliang
 * @date 2020/9/25
 */

import com.cfc.uid.generate.interfaces.WorkerIdInterface;
import com.cfc.uid.generate.utils.NetUtils;
import com.cfc.workerid.api.GetWorkerIdRequest;
import com.cfc.workerid.api.GetWorkerIdResponse;
import com.cfc.workerid.api.TransInput;
import com.cfc.workerid.api.TransOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class WorkerIdAssigner {

    @Value("${server.port:null}")
    private Short port;

    @Value("${spring.application.name:null}")
    private String name;

    @Autowired
    private WorkerIdInterface workerIdInterface;

    public WorkerIdAssigner() {
        log.info("Initialized WorkerIdAssigner");
    }

    public Long getWorkerId() {
        TransInput<GetWorkerIdRequest> request = new TransInput<>();
        request.setAppName(name);

        GetWorkerIdRequest getWorkerIdRequest = new GetWorkerIdRequest();
        getWorkerIdRequest.setIp(NetUtils.getLocalAddress());
        getWorkerIdRequest.setPort(port);
        request.setData(getWorkerIdRequest);

//        String message = okHttpCli.doPostJson("http://127.0.0.1:11112/api/workerId/getWorkerId",
//                JacksonUtils.objectToJson(request));

        TransOutput<GetWorkerIdResponse> response = workerIdInterface.getWorkerId(request);

//        TransOutput<GetWorkerIdResponse> response = JacksonUtils.fromJsonToObject(message,
//                new TypeReference<TransOutput<GetWorkerIdResponse>>() {
//                });

//        System.out.println(message);

        return response.getData().getWorkerId();
    }

    public WorkerIdInterface getWorkerIdInterface() {
        return workerIdInterface;
    }

    public void setWorkerIdInterface(WorkerIdInterface workerIdInterface) {
        this.workerIdInterface = workerIdInterface;
    }
}
