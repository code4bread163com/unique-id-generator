package com.cfc.common.uid;

/**
 * @author zhangliang
 * @date 2020/9/25
 */

import com.cfc.common.uid.util.DockerUtils;
import com.cfc.common.uid.util.NetUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WorkerIdUtil {

    @Value("${server.port:}")
    private String port;

    @Value("${spring.application.name}")
    private String name;

    public String getWorkerId() {
        if (DockerUtils.isDocker()) {
            workerNodeEntity.setType(WorkerNodeType.CONTAINER.value());
            workerNodeEntity.setHostName(DockerUtils.getDockerHost());
            workerNodeEntity.setPort(DockerUtils.getDockerPort());

        } else {
            workerNodeEntity.setType(WorkerNodeType.ACTUAL.value());
            workerNodeEntity.setHostName(NetUtils.getLocalAddress());
            workerNodeEntity.setPort(System.currentTimeMillis() + "-" + RandomUtils.nextInt(100000));
        }

        OkHttpCli okHttpCli = ctx.getBean(OkHttpCli.class);
        TransInput<GetWorkerIdRequest> request = new TransInput();
        request.setAppId(name);

        GetWorkerIdRequest getWorkerIdRequest = new GetWorkerIdRequest();
        getWorkerIdRequest.setHostName();

        String message = okHttpCli.doPostJson("http://127.0.0.1:11111/api/workerId/getWorkerId", "{}");

    }
}
