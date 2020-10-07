package com.cfc.workerid.server.service;

import com.cfc.uid.common.enums.ErrorCodeEnum;
import com.cfc.uid.common.exception.UidGenerateException;
import com.cfc.workerid.api.GetWorkerIdRequest;
import com.cfc.workerid.api.TransInput;
import com.cfc.workerid.server.dao.WorkerNodeMapper;
import com.cfc.workerid.server.model.WorkerNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhangliang
 * @date 2020/9/28
 */
@Service
@Slf4j
public class WorkerIdService {

    @Autowired
    private WorkerNodeMapper workerNodeMapper;

    @Value("${max.workerId}")
    private Integer maxWorkerId;

    public Long getWorkerId(TransInput<GetWorkerIdRequest> request) {
        WorkerNode workerNode = new WorkerNode();
        workerNode.setAppName(request.getAppName());
        workerNode.setIp(request.getData().getIp());
        workerNode.setPort(request.getData().getPort());
        workerNode.setCreateTime(new Date());
        workerNode.setUpdateTime(new Date());
        workerNodeMapper.insert(workerNode);

        if (workerNode.getId() == null) {
            log.error("数据库返回自增Id为空！appName：{}", request.getAppName());
            throw new UidGenerateException(ErrorCodeEnum.GENERATE_UID_ERROR.getCode(), ErrorCodeEnum.GENERATE_UID_ERROR.getText());
        }

        if (workerNode.getId() > maxWorkerId.longValue()) {
            synchronized (WorkerIdService.class) {
                Long maxId = workerNodeMapper.selectMaxId();
                if (maxId != null && maxId >= maxWorkerId.longValue()) {
                    int result = workerNodeMapper.clearWorkerId();
                    log.info("Clear worker id, result:{}", result);
                }

                workerNodeMapper.insert(workerNode);
                if (workerNode.getId() > maxWorkerId.longValue()) {
                    log.error("无法清空数据库中的worker id！appName：{}", request.getAppName());
                    throw new UidGenerateException(ErrorCodeEnum.GENERATE_UID_ERROR.getCode(), ErrorCodeEnum.GENERATE_UID_ERROR.getText());
                }
            }
        }
        return workerNode.getId();
    }
}