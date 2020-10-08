package com.cfc.uid.generate.core;

/**
 * @author zhangliang
 * @date 2020/9/25
 */
import com.cfc.uid.common.enums.ErrorCodeEnum;
import com.cfc.uid.common.exception.UidGenerateException;
import com.cfc.uid.common.utils.JacksonUtils;
import com.cfc.uid.generate.utils.NetUtils;
import com.cfc.workerid.api.GetWorkerIdRequest;
import com.cfc.workerid.api.GetWorkerIdResponse;
import com.cfc.workerid.api.TransInput;
import com.cfc.workerid.api.TransOutput;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
public class WorkerIdAssigner {

    @Value("${server.port:null}")
    private Short port;

    @Value("${spring.application.name:null}")
    private String name;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient client;

    private static final String WORKER_SERVER_INSTANCE_ID = "workerid-server";
    private static final String GET_WORKER_ID_URL = "/api/workerId/getWorkerId";

    public WorkerIdAssigner() {
        log.info("Initialized WorkerIdAssigner");
    }

    public Long getWorkerId() {
        List<ServiceInstance> instances = client.getInstances(WORKER_SERVER_INSTANCE_ID);

        if (instances == null || instances.isEmpty()) {
            throw new UidGenerateException("Get worker id from worker server failed! Eureka service " + WORKER_SERVER_INSTANCE_ID + " do not exist！");
        }

        TransInput<GetWorkerIdRequest> request = new TransInput<>();
        request.setAppName(name);

        GetWorkerIdRequest getWorkerIdRequest = new GetWorkerIdRequest();
        String ip = NetUtils.getLocalAddress();
        getWorkerIdRequest.setIp(ip);
        getWorkerIdRequest.setPort(port);
        request.setData(getWorkerIdRequest);

        String message = null;
        for (ServiceInstance instance : instances) {
            try {
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(instance.getUri().toString() + GET_WORKER_ID_URL,
                        request, String.class);
                message = responseEntity.getBody();
                log.info("Get worker id from worker server, appName: {}, ip: {}, response: {}", name, ip, message);
                break;
            } catch (Exception ex) {
                log.error("Get worker id from worker server failed!", ex);
            }
        }

        TransOutput<GetWorkerIdResponse> response = JacksonUtils.fromJsonToObject(message,
                new TypeReference<TransOutput<GetWorkerIdResponse>>() {
                });

        if (response == null || response.getData() == null || response.getTransCode() != ErrorCodeEnum.SUCCESS.getCode()) {
            throw new UidGenerateException("Get worker id from worker server failed!");
        }

        return response.getData().getWorkerId();
    }
}
