package com.cfc.uid.gen.core;

import com.cfc.uid.common.exception.UidGenerateException;

/**
 * @author zhangliang
 * @date 2020/9/25
 */
public interface UidGenerator {

    /**
     * Get a unique ID
     *
     * @return UID
     * @throws UidGenerateException
     */
    long getUID() throws UidGenerateException;

    /**
     * Parse the UID into elements which are used to generate the UID. <br>
     * Such as timestamp & workerId & sequence...
     *
     * @param uid
     * @return Parsed info
     */
    String parseUID(long uid);
}
