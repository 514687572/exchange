package com.cmd.exchange.common.csvfile;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * To change this template use File | Settings | File Templates.
 */
public class ThreadPools {
    public static void createThreadPool() {
        Random random = new Random();
        /**
         * 产生一个 ExecutorService 对象，这个对象带有一个大小为 poolSize 的线程池， 若任 务数量大于 poolSize,任务会被放在一个 queue 里顺序执行。这里开启10个线程对象，放入线程池
         */
        ExecutorService executor = Executors.newFixedThreadPool(12);
        int waitTime = 500;
        if (CsvExportThread.queue.size() > 0) {
            //这个for循环里的代码就是从线程池里获得线程对象，并执行多线程操作 www.it165.net
            for (int i = 0; i < CsvExportThread.queue.size() + 2; i++) {
                int time = random.nextInt(1000);
                waitTime += time;
                Runnable runner = new ExecutorThread((List<Object>) CsvExportThread.queue.poll(), time);
                executor.execute(runner);
            }
        }

        try {
            Thread.sleep(waitTime);
            executor.shutdown();// 只是将线程池置于关闭状态，不接受新任务，对正在运行的任务不影响
            executor.awaitTermination(waitTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ignored) {
        }
    }
}