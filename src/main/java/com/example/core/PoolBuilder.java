package com.example.core;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Pool;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @Author:letg(pz)
 * @Date: 2023/2/7 16:58
 * @Description:
 */


@Slf4j(topic = "c.PoolBuilder")
public class PoolBuilder {
    private int corePoolSize = 10;
    private int maximumPoolSize = 100;
    private long keepAliveTime = 10;
    private TimeUnit timeUnit = TimeUnit.MINUTES;
    private long waitTime = 2000;
    private Queue<Runnable> workQueue = new LinkedBlockingQueue<>(100);
    private String containerName = "default";
    public Map<String, PoolContainer> containerMap = new HashMap<>();
    private RejectedExecutionHandler handler;

    class PoolContainer {
        private ThreadPoolExecutor pool;
        private String name;

        public PoolContainer(String name) {
            this.name = name;
        }

        public ThreadPoolExecutor getPool() {
            return pool;
        }

        public String getName() {
            return name;
        }
    }

    private PoolBuilder() {

    }

    public static PoolBuilder createPoolBulider() {
        if (ObjectUtils.isEmpty(ExecutorPoolUtil.builder)) {
            synchronized (PoolBuilder.class) {
                if (ObjectUtils.isEmpty(ExecutorPoolUtil.builder)) {
                    return new PoolBuilder();
                }
            }
        }

        return ExecutorPoolUtil.builder;
    }

    public PoolContainer build() {
        PoolContainer poolContainer = new PoolContainer(containerName);
        if (ObjectUtils.isEmpty(this.handler)) {
            this.handler = new FailRetryHandler(waitTime);
        }
        poolContainer.pool = new ThreadPoolExecutor(this.corePoolSize, this.maximumPoolSize,
                this.keepAliveTime, this.timeUnit, (BlockingQueue<Runnable>) this.workQueue, this.handler);
        if (containerMap.containsKey(containerName)) {
            log.error("{}线程池容器已存在", containerName);
            throw new RuntimeException();
        }
        return poolContainer;
    }

    public PoolBuilder corePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        return this;
    }

    public PoolBuilder maximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
        return this;
    }

    public PoolBuilder keepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
        return this;
    }

    public PoolBuilder timeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }

    public PoolBuilder workQueue(Queue<Runnable> workQueue) {
        this.workQueue = workQueue;
        return this;
    }

    public PoolBuilder handler(RejectedExecutionHandler handler) {
        this.handler = handler;
        return this;
    }

    public PoolBuilder containerName(String containerName) {
        this.containerName = containerName;
        return this;
    }


}
