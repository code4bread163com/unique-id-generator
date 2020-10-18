package com.cfc.uid.gen.buffer;

import java.util.List;

/**
 * @author zhangliang
 * @date 2020/10/09
 */
@FunctionalInterface
public interface BufferedUidProvider {

    /**
     * Provides UID in one second
     * 
     * @param momentInSecond
     * @return
     */
    List<Long> provide(long momentInSecond);
}
