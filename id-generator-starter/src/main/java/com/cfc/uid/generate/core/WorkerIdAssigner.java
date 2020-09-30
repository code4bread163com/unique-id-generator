package com.cfc.uid.generate.core;

/**
 * @author zhangliang
 * @date 2020/9/25
 */

import com.cfc.uid.common.utils.JacksonUtils;
import com.cfc.uid.generate.config.OkHttpCli;
import com.cfc.uid.generate.utils.NetUtils;
import com.cfc.workerid.api.GetWorkerIdRequest;
import com.cfc.workerid.api.GetWorkerIdResponse;
import com.cfc.workerid.api.TransInput;
import com.cfc.workerid.api.TransOutput;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WorkerIdAssigner {

    @Value("${server.port:null}")
    private Short port;

    @Value("${spring.application.name:null}")
    private String name;

    @Autowired
    OkHttpCli okHttpCli;

    public String getWorkerId() {
        TransInput<GetWorkerIdRequest> request = new TransInput<>();
        request.setAppName(name);

        GetWorkerIdRequest getWorkerIdRequest = new GetWorkerIdRequest();
        getWorkerIdRequest.setIp(NetUtils.getLocalAddress());
        getWorkerIdRequest.setPort(port);
        request.setData(getWorkerIdRequest);

        String message = okHttpCli.doPostJson("http://127.0.0.1:11111/api/workerId/getWorkerId",
                JacksonUtils.objectToJson(request));

        TransOutput<GetWorkerIdResponse> response = JacksonUtils.fromJsonToObject(message,
                new TypeReference<TransOutput<GetWorkerIdResponse>>() {
                });

        return "";
    }
}
