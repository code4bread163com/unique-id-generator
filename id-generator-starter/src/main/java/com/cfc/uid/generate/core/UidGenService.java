package com.cfc.uid.generate.core;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ID生成服务
 *
 * @author zhangliang
 * @date 2020/9/25
 */
@Service
public class UidGenService {

    @Resource
    private UidGenerator uidGenerator;

    public long getUid() {
        return uidGenerator.getUID();
    }
}
