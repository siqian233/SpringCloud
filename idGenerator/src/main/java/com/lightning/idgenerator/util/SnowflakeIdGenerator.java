package com.lightning.idgenerator.util;

public class SnowflakeIdGenerator {

    private final long workerId;             // 工作节点 ID（机器 ID）
    private final long datacenterId;         // 数据中心 ID（机房 ID）
    private final long sequenceBits = 12L;   // 序列号位数
    private final long workerIdBits = 5L;    // 机器 ID 位数
    private final long datacenterIdBits = 5L;// 数据中心位数

    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);         // 支持的最大机器 ID
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits); // 支持的最大数据中心 ID

    private final long workerIdShift = sequenceBits;                      // 机器 ID 左移位数
    private final long datacenterIdShift = sequenceBits + workerIdBits;  // 数据中心 ID 左移位数
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long lastTimestamp = -1L;
    private long sequence = 0L;

    private final long twepoch = 1609459200000L; // 起始时间：2021-01-01

    public SnowflakeIdGenerator(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0)
            throw new IllegalArgumentException("workerId 超出范围");
        if (datacenterId > maxDatacenterId || datacenterId < 0)
            throw new IllegalArgumentException("datacenterId 超出范围");

        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp)
            throw new RuntimeException("系统时钟回退了");

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0)
                timestamp = waitNextMillis(lastTimestamp);
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp)
            timestamp = timeGen();
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}
