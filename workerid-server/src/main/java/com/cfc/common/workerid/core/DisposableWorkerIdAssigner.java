package com.cfc.common.workerid.core;

import com.cfc.common.workerid.api.WorkerNodeType;
import com.cfc.common.workerid.model.WorkerNode;
import com.cfc.common.workerid.utils.DockerUtils;
import com.cfc.common.workerid.utils.NetUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 生成 workerID
 *
 * @author zhangliang
 * @date 2020/9/23
 */
@Service
public class DisposableWorkerIdAssigner implements WorkerIdAssigner {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisposableWorkerIdAssigner.class);



    @Transactional
    public long assignWorkerId() {
        // build worker node entity
        WorkerNode workerNode = new WorkerNode();

        // add worker node for new (ignore the same IP + PORT)
        //workerNodeDAO.addWorkerNode(workerNodeEntity);
        LOGGER.info("Add worker node:" + workerNode);

        return workerNode.getId();
    }


}
