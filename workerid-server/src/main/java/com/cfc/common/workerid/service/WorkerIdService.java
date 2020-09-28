package com.cfc.common.workerid.service;

import com.cfc.common.idcommon.exception.BizException;
import com.cfc.common.workerid.api.GetWorkerIdRequest;
import com.cfc.common.workerid.api.TransInput;
import com.cfc.common.workerid.dao.WorkerNodeMapper;
import com.cfc.common.workerid.model.WorkerNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhangliang
 * @date 2020/9/28
 */
@Service
public class WorkerIdService {

    @Autowired
    private WorkerNodeMapper workerNodeMapper;

    public Long getWorkerId(TransInput<GetWorkerIdRequest> request) {

        WorkerNode workerNode = new WorkerNode();
        workerNode.setAppName(request.getAppName());
        workerNode.setIp(request.getData().getIp());
        workerNode.setPort(request.getData().getPort());
        workerNode.setCreateTime(new Date());
        workerNode.setUpdateTime(new Date());
        workerNodeMapper.insert(workerNode);

        if (workerNode.getId() == null) {
            throw new BizException("create worker id failed!");
        }

        return workerNode.getId();
    }
}
