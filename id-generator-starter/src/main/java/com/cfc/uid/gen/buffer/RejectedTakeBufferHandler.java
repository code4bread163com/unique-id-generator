package com.cfc.uid.gen.buffer;

/**
 * @author zhangliang
 * @date 2020/10/09
 */
@FunctionalInterface
public interface RejectedTakeBufferHandler {

    /**
     * Reject take buffer request
     * 
     * @param ringBuffer
     */
    void rejectTakeBuffer(RingBuffer ringBuffer);
}
