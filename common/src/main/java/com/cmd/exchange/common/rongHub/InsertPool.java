package com.cmd.exchange.common.rongHub;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 单例线程池
 * tomcat有全局线程管理，停止应用时线程也会被关闭，故在此不做关闭线程池处理
 *
 * @Author: lsl
 * @Date: Created in 2018/8/8
 */
public class InsertPool {

    private InsertPool() {

    }

    private static class Inner {
        private static InsertPool insertPool = new InsertPool();
    }

    public static InsertPool getInstance() {
        return Inner.insertPool;
    }

    private static int corePoolSize = 2;
    private static int maximumPoolSize = Integer.MAX_VALUE;
    private static long keepAliveTime = 5;

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new SynchronousQueue<>());

    public static void addRunnable(Runnable runnable) {

        threadPoolExecutor.execute(runnable);

    }
}
