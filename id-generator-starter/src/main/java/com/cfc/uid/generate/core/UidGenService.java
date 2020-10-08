package com.cfc.uid.generate.core;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UidGenService {

    @Resource
    private UidGenerator uidGenerator;

    public long getUid() {
        return uidGenerator.getUID();
    }
}
