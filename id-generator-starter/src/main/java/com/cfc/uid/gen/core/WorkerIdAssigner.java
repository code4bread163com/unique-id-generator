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
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
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
    private LoadBalancerClient loadBalancerClient;

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
        ServiceInstance serviceInstance = loadBalancerClient.choose(WORKER_SERVER_INSTANCE_ID);
        if (serviceInstance == null) {
            throw new UidGenerateException("Get worker id from worker server failed! Eureka provider " + WORKER_SERVER_INSTANCE_ID + " do not exist！");
        }

        String url = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort());
        log.info("Get workerId server url: " + url);

        TransOutput<GetWorkerIdResponse> response;
        try {
            response = getWorkerId(url);
            checkResponse(response);
        } catch (Exception ex) {
            throw new UidGenerateException("Get worker id from worker server failed! errorMsg:" + ex.toString(), ex);
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

    private static final String GET_WORKER_ID_URL1 = "http://workerid-server/api/workerId/getWorkerId";

    /**
     * 远程获取 workerId
     *
     * @param uri
     * @return
     */
    private TransOutput<GetWorkerIdResponse> getWorkerId(String uri) {

//        ResponseEntity<String> responseEntity1 = restTemplate.postForEntity(GET_WORKER_ID_URL1, createRequest(), String.class);
//        String message1 = responseEntity1.getBody();
//
//        log.info("Get worker id from worker server, appName: {}, response: {}", name,  message1);
//
//        TransOutput<GetWorkerIdResponse> response1 = JacksonUtils.fromJsonToObject(message1,
//                new TypeReference<TransOutput<GetWorkerIdResponse>>() {
//                });
//
//        if (response1 == null || response1.getData() == null || response1.getTransCode() != ErrorCodeEnum.SUCCESS.getCode()) {
//            throw new UidGenerateException("Get worker id from worker server failed!");
//        }
//       ResponseEntity<String> responseEntity1 = restTemplate.postForEntity(GET_WORKER_ID_URL1, createRequest(), String.class);
////        String message1 = responseEntity1.getBody();
////
////        log.info("Get worker id from worker server, appName: {}, response: {}", name,  message1);
////
////        TransOutput<GetWorkerIdResponse> response1 = JacksonUtils.fromJsonToObject(message1,
////                new TypeReference<TransOutput<GetWorkerIdResponse>>() {
////                });
////
////        if (response1 == null || response1.getData() == null || response1.getTransCode() != ErrorCodeEnum.SUCCESS.getCode()) {
////            throw new UidGenerateException("Get worker id from worker server failed!");
////        }
////
////
//


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
