package com.example.core;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author:letg(pz)
 * @Date: 2023/2/7 14:38
 * @Description:
 */


@Slf4j(topic = "c.FailRetryHandler")
public class FailRetryHandler implements RejectedExecutionHandler {
    private long waitTime;

    public FailRetryHandler(long waitTime) {
        this.waitTime = waitTime;
    }
    @SneakyThrows
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        log.info("执行异常{},{}秒后重试",r,this.waitTime);

        executor.execute(r);
    }
}
