package com.cfc.uid.generate.core;

import com.cfc.uid.common.enums.ErrorCodeEnum;
import com.cfc.uid.common.exception.UidGenerateException;
import com.cfc.uid.generate.utils.BitsAllocator;
import com.cfc.uid.generate.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DefaultUidGenerator implements UidGenerator, InitializingBean {
    /**
     * Bits allocate
     */
    protected int timeBits = 30;
    protected int workerBits = 20;
    protected int seqBits = 13;

    /**
     * Customer epoch, unit as second. For example 2016-05-20 (ms: 1463673600000)
     */
    protected String epochStr = "2016-05-20";
    protected long epochSeconds = TimeUnit.MILLISECONDS.toSeconds(1463673600000L);

    /**
     * Stable fields after spring bean initializing
     */
    protected BitsAllocator bitsAllocator;
    protected long workerId;

    /**
     * Volatile fields caused by nextId()
     */
    protected long sequence = 0L;
    protected long lastSecond = -1L;

    protected static final long MAX_CLOCK_BACKWARDS_SECONDS = 3L;

    @Autowired
    protected WorkerIdAssigner workerIdAssigner;

    @Override
    public void afterPropertiesSet() {
        log.info("Initialized DefaultUidGenerator");

        // initialize bits allocator
        bitsAllocator = new BitsAllocator(timeBits, workerBits, seqBits);

        // initialize worker id
        workerId = getWorkerId();

        log.info("Initialized id generator bits(1, {}, {}, {}) for workerID: {}", timeBits, workerBits, seqBits, workerId);
    }

    private Long getWorkerId() {
        Long workerId = workerIdAssigner.getWorkerId();
        if (workerId > bitsAllocator.getMaxWorkerId()) {
            log.error("Initialized id generator failed, worker id {} exceeds the max {}", workerId, bitsAllocator.getMaxWorkerId());
            throw new UidGenerateException(ErrorCodeEnum.INITIALIZED_WORKER_ID_ERROR.getCode(), ErrorCodeEnum.INITIALIZED_WORKER_ID_ERROR.getText());
        }

        log.info("Initialized id generator workerId, workerId:{}", workerId);
        return workerId;
    }

    @Override
    public long getUID() throws UidGenerateException {
        try {
            return nextId();
        } catch (Exception e) {
            log.error("Generate unique id exception. ", e);
            throw new UidGenerateException("Generate unique id exception. ", e);
        }
    }

    @Override
    public String parseUID(long uid) {
        long totalBits = BitsAllocator.TOTAL_BITS;
        long signBits = bitsAllocator.getSignBits();
        long timestampBits = bitsAllocator.getTimestampBits();
        long workerIdBits = bitsAllocator.getWorkerIdBits();
        long sequenceBits = bitsAllocator.getSequenceBits();

        // parse UID
        long sequence = (uid << (totalBits - sequenceBits)) >>> (totalBits - sequenceBits);
        long workerId = (uid << (timestampBits + signBits)) >>> (totalBits - workerIdBits);
        long deltaSeconds = uid >>> (workerIdBits + sequenceBits);

        Date thatTime = new Date(TimeUnit.SECONDS.toMillis(epochSeconds + deltaSeconds));
        String thatTimeStr = DateUtils.formatByDateTimePattern(thatTime);

        // format as string
        return String.format("{\"UID\":\"%d\",\"timestamp\":\"%s\",\"workerId\":\"%d\",\"sequence\":\"%d\"}",
                uid, thatTimeStr, workerId, sequence);
    }

    /**
     * Get UID
     *
     * @return UID
     * @throws UidGenerateException in the case: Clock moved backwards; Exceeds the max timestamp
     */
    protected synchronized long nextId() throws Exception {
        long currentSecond = getCurrentSecond();

        // Clock moved backwards, refuse to generate uid
        if (currentSecond < lastSecond) {
            log.error("Clock moved backwards, current seconds: {}, last seconds: {}",
                    currentSecond, lastSecond);

            long backwardsSeconds = lastSecond - currentSecond;
            if (backwardsSeconds <= MAX_CLOCK_BACKWARDS_SECONDS) {
                currentSecond = retry(currentSecond, lastSecond);

            } else {
                log.info("Clock moved backwards, reacquire workerId:{}", workerId);
                workerId = getWorkerId();

                sequence = 0L;
                lastSecond = -1L;
            }
        }

        // At the same second, increase sequence
        if (currentSecond == lastSecond) {
            sequence = (sequence + 1) & bitsAllocator.getMaxSequence();
            // Exceed the max sequence, we wait the next second to generate uid
            if (sequence == 0) {
                currentSecond = getNextSecond(lastSecond);
            }

            // At the different second, sequence restart from zero
        } else {
            sequence = 0L;
        }

        lastSecond = currentSecond;

        // Allocate bits for UID
        return bitsAllocator.allocate(currentSecond - epochSeconds, workerId, sequence);
    }

    private long retry(long currentSecond, long lastSecond) throws Exception {
        long temp = currentSecond;
        do {
            Thread.sleep(500);
            currentSecond = getCurrentSecond();
            log.error("Clock moved backwards, retry, current seconds: {}, last seconds: {}",
                    currentSecond, lastSecond);

            if (currentSecond < temp) {
                throw new UidGenerateException("Clock moved backwards. current seconds: " + currentSecond);
            }
            temp = currentSecond;
        } while (currentSecond < lastSecond);

        return currentSecond;
    }

    /**
     * Get next millisecond
     */
    private long getNextSecond(long lastTimestamp) {
        long timestamp = getCurrentSecond();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentSecond();
        }

        return timestamp;
    }

    /**
     * Get current second
     */
    private long getCurrentSecond() {
        long currentSecond = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        if (currentSecond - epochSeconds > bitsAllocator.getMaxDeltaSeconds()) {
            throw new UidGenerateException("Timestamp bits is exhausted. Refusing UID generate. Now: " + currentSecond);
        }

        return currentSecond;
    }

    public void setTimeBits(int timeBits) {
        if (timeBits > 0) {
            this.timeBits = timeBits;
        }
    }

    public void setWorkerBits(int workerBits) {
        if (workerBits > 0) {
            this.workerBits = workerBits;
        }
    }

    public void setSeqBits(int seqBits) {
        if (seqBits > 0) {
            this.seqBits = seqBits;
        }
    }

    public void setEpochStr(String epochStr) {
        if (StringUtils.isNotBlank(epochStr)) {
            this.epochStr = epochStr;
            this.epochSeconds = TimeUnit.MILLISECONDS.toSeconds(DateUtils.parseByDayPattern(epochStr).getTime());
        }
    }

    public WorkerIdAssigner getWorkerIdAssigner() {
        return workerIdAssigner;
    }

    public void setWorkerIdAssigner(WorkerIdAssigner workerIdAssigner) {
        this.workerIdAssigner = workerIdAssigner;
    }
}
