package com.cfc.uid.gen.core;

import com.cfc.uid.common.enums.ErrorCodeEnum;
import com.cfc.uid.common.exception.UidGenerateException;
import com.cfc.uid.common.utils.JacksonUtils;
import com.cfc.uid.gen.utils.NetUtils;
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

/**
 * workerID生成器，调用远程服务
 *
 * @author zhangliang
 * @date 2020/9/25
 */
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

    /**
     * 获取 workerId
     *
     * @return
     */
    public Long getWorkerId() {
        List<ServiceInstance> instances = client.getInstances(WORKER_SERVER_INSTANCE_ID);

        if (instances == null || instances.isEmpty()) {
            throw new UidGenerateException("Get worker id from worker server failed! Eureka provider " + WORKER_SERVER_INSTANCE_ID + " do not exist！");
        }

        TransOutput<GetWorkerIdResponse> response = null;
        for (ServiceInstance instance : instances) {
            try {
                response = getWorkerId(instance.getUri().toString());
                checkResponse(response);
                break;
            } catch (Exception ex) {
                log.error("Get worker id from worker server failed!", ex);
            }
        }

        checkResponse(response);
        return response.getData().getWorkerId();
    }

    /**
     * 创建请求
     *
     * @return
     */
    private TransInput<GetWorkerIdRequest> createRequest() {
        TransInput<GetWorkerIdRequest> request = new TransInput<>();
        request.setAppName(name);

        GetWorkerIdRequest getWorkerIdRequest = new GetWorkerIdRequest();
        getWorkerIdRequest.setIp(NetUtils.getLocalAddress());
        getWorkerIdRequest.setPort(port);
        request.setData(getWorkerIdRequest);

        return request;
    }

    /**
     * 远程获取 workerId
     *
     * @param uri
     * @return
     */
    private TransOutput<GetWorkerIdResponse> getWorkerId(String uri) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri + GET_WORKER_ID_URL,
                createRequest(), String.class);
        String message = responseEntity.getBody();
        log.info("Get worker id from worker server, response: {}", message);

        return JacksonUtils.fromJsonToObject(message,
                new TypeReference<TransOutput<GetWorkerIdResponse>>() {
                });
    }

    /**
     * check response
     *
     * @param response
     */
    private void checkResponse(TransOutput<GetWorkerIdResponse> response) {
        if (response == null || response.getData() == null || response.getTransCode() != ErrorCodeEnum.SUCCESS.getCode()) {
            throw new UidGenerateException("Get worker id from worker server failed!");
        }
    }
}
