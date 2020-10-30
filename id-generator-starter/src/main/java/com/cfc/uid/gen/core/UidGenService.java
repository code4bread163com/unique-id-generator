package com.cfc.uid.gen.core;

import org.springframework.stereotype.Service;

/**
 * ID生成服务
 *
 * @author zhangliang
 * @date 2020/9/25
 */

public class UidGenService {

    private UidGenerator uidGenerator;

    public long getUid() {
        return uidGenerator.getUID();
    }

    public UidGenerator getUidGenerator() {
        return uidGenerator;
    }

    public void setUidGenerator(UidGenerator uidGenerator) {
        this.uidGenerator = uidGenerator;
    }
}
