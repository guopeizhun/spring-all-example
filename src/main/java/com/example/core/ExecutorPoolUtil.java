package com.example.core;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @Author:letg(pz)
 * @Date: 2023/2/7 11:12
 * @Description:
 */


@Slf4j(topic = "c.executorPoolUtil")
public class ExecutorPoolUtil {
    public static final PoolBuilder builder = PoolBuilder.createPoolBulider();

    /**
     * 初始化线程池
     */
    public static void initPool(String containerName) {
        PoolBuilder.PoolContainer container = builder.containerName(containerName).build();
        if (builder.containerMap.containsKey(container.getName())) {
            log.info("线程池容器已经存在");
            throw new RuntimeException();
        }
        builder.containerMap.put(container.getName(), container);
        log.info("线程池初始化成功");
    }

    public static void initPool() {
        PoolBuilder.PoolContainer container = builder.build();
        if (builder.containerMap.containsKey(container.getName())) {
            log.info("线程池容器已经存在");
            throw new RuntimeException();
        }
        builder.containerMap.put(container.getName(), container);
        log.info("线程池初始化成功");
    }

    public static String initPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                  TimeUnit timeUnit, Queue<Runnable> queue, RejectedExecutionHandler handler,
                                  String containerName) {
        PoolBuilder.PoolContainer container = builder.corePoolSize(corePoolSize)
                .maximumPoolSize(maximumPoolSize)
                .keepAliveTime(keepAliveTime)
                .timeUnit(timeUnit)
                .workQueue(queue)
                .handler(handler)
                .containerName(containerName)
                .build();
        if (builder.containerMap.containsKey(container.getName())) {
            log.info("线程池容器已经存在");
            throw new RuntimeException();
        }
        builder.containerMap.put(container.getName(), container);
        log.info("线程池初始化成功");
        return container.getName();
    }

    /**
     * 执行单个任务
     *
     * @param r
     */
    public static void exec(Runnable r, String containerName) {
        getPool(containerName).execute(r);
    }

    public static ThreadPoolExecutor getPool(String containerName) {
        if (!builder.containerMap.containsKey(containerName)) {
            log.error("不存在{}线程池容器", containerName);
            throw new RuntimeException();
        }
        return builder.containerMap.get(containerName).getPool();
    }

    /**
     * 执行任务组
     *
     * @param list
     */
    public static void exec(List<Runnable> list, String containerName) {
        list.forEach(runnable -> {
            try {
                getPool(containerName).execute(runnable);
            } catch (RejectedExecutionException e) {
                log.info("阻塞队列已满");
            }
        });
    }

    /**
     * 执行回调任务
     *
     * @param callback
     * @param <T>
     * @return
     */
    public static <T> T execCallback(Callable<T> callback, String containerName) {
        T t = null;
        try {
            t = getPool(containerName).submit(callback).get();
            return t;
        } catch (InterruptedException | ExecutionException e) {
            log.info("执行任务异常{}", e.toString());
            return null;
        }
    }

    /**
     * 创建线程组
     *
     * @param groupName
     * @return
     */
    public static ThreadGroup createThreadGroup(String groupName) {
        ThreadGroup mainGroup = getMainGroup();
        if (ObjectUtils.isEmpty(mainGroup)) {
            log.error("操作线程错误");
            throw new RuntimeException();
        }
        if (exsitGroup(groupName)) {
            log.error("线程组已存在");
            throw new RuntimeException();
        }
        ThreadGroup group = new ThreadGroup(mainGroup, groupName);
        group.setDaemon(true);
        return group;
    }

    public static List<Thread> createThreadGroup(String groupName, List<Runnable> runnableList) {
        ThreadGroup group = createThreadGroup(groupName);
        List<Thread> list = new ArrayList<>();
        runnableList.forEach(runnable -> {
            list.add(new Thread(group, runnable));
        });
        return list;
    }

    private static boolean exsitGroup(String groupName) {
        ThreadGroup mainGroup = getMainGroup();
        if (ObjectUtils.isEmpty(mainGroup)) {
            log.error("操作线程错误");
            throw new RuntimeException();
        }
        int activeGroupCount = mainGroup.activeGroupCount();
        ThreadGroup[] groups = new ThreadGroup[activeGroupCount];
        mainGroup.enumerate(groups);
        Set<String> set = Arrays.stream(groups).map(ThreadGroup::getName).collect(Collectors.toSet());
        return set.contains(groupName);
    }

    public static ThreadGroup getMainGroup() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        while (ObjectUtils.isEmpty(group)) {
            if (group.getName().equals("main")) {
                break;
            } else {
                group = group.getParent();
            }
        }
        return group;
    }


    public static long getFinishedCount(String containerName) {
        return getPool(containerName).getCompletedTaskCount();
    }

    public static void shutdown(String containerName) {
        getPool(containerName).shutdown();
    }

}
