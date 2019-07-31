package com.cmd.exchange.common.utils;

import java.lang.management.ManagementFactory;
import java.net.NetworkInterface;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 生成序列号. 参考: https://github.com/mongodb/mongo-java-driver/blob/master/bson/src/main/org/bson/types/ObjectId.java
 * <br><br>
 * <p>
 * 由以下几个部分组成:<br>
 * 1. 乱序的时间规则.<br>
 * 2. 生成序列号的行为. 如 1 表示订单, 2 表示提现, 3 表示退款 等<br>
 * 3. 机器码. 主要指当前运行机器 mac 地址的 hashcode 值. 当项目部署在多台不同的机器时, 此规则可以区分<br>
 * 4. 进程号. 主要指当前运行机器的此 jvm 进程的 hashcode 值. 当项目部署在一台机器的多个不同进程时, 此规则可以区分<br>
 * 5. 自增值. 这个值基于当前进程是同步的. 基于 concurrent 下的 atomic 类实现, 避免 synchronized 锁<br><br>
 *
 * <table border="1">
 * <caption>ObjectID layout</caption>
 * <tr>
 * <td>1</td>
 * <td>2</td>
 * <td>3</td>
 * <td>4</td>
 * <td>5</td>
 * </tr>
 * <tr>
 * <td>time</td>
 * <td>behavior</td>
 * <td>machine</td>
 * <td>pid</td>
 * <td>inc</td>
 * </tr>
 * </table>
 * <br>
 */
public final class NoUtil {

    private static final Logger LOGGER = Logger.getLogger(NoUtil.class.getName());

    /**
     * 机器码 加 进程号 会导致生成的序列号很长, 基于这两个值做一些截取
     */
    private static final String MP;
    /**
     * 截取长度: 从最后面开始截取
     */
    private static final int MP_LEN = 4;

    static {
        // 机器码 --> 本机 mac 地址的 hashcode 值
        int machineIdentifier = createMachineIdentifier();
        // 进程号 --> 当前运行的 jvm 进程号的 hashcode 值
        int processIdentifier = createProcessIdentifier();

        String mp = Integer.toString(Math.abs((machineIdentifier + "" + processIdentifier).hashCode()));
        MP = (mp.length() > MP_LEN) ? mp.substring(mp.length() - MP_LEN, mp.length()) : mp;
    }

    private static int createMachineIdentifier() {
        // build a 2-byte machine piece based on NICs info
        int machinePiece;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface ni = e.nextElement();
                sb.append(ni.toString());
                byte[] mac = ni.getHardwareAddress();
                if (mac != null) {
                    ByteBuffer bb = ByteBuffer.wrap(mac);
                    try {
                        sb.append(bb.getChar());
                        sb.append(bb.getChar());
                        sb.append(bb.getChar());
                    } catch (BufferUnderflowException shortHardwareAddressException) { //NOPMD
                        // mac with less than 6 bytes. continue
                    }
                }
            }
            machinePiece = sb.toString().hashCode();
        } catch (Throwable t) {
            // exception sometimes happens with IBM JVM, use random
            machinePiece = new SecureRandom().nextInt();
            LOGGER.log(Level.WARNING, "Failed to get machine identifier from network interface, using random number instead", t);
        }
        return machinePiece;
    }

    // Creates the process identifier. This does not have to be unique per class loader because
    // NEXT_COUNTER will provide the uniqueness.
    private static int createProcessIdentifier() {
        int processId;
        try {
            String processName = ManagementFactory.getRuntimeMXBean().getName();
            if (processName.contains("@")) {
                processId = Integer.parseInt(processName.substring(0, processName.indexOf('@')));
            } else {
                processId = processName.hashCode();
            }
        } catch (Throwable t) {
            processId = new SecureRandom().nextInt();
            LOGGER.log(Level.WARNING, "Failed to get process identifier from JMX, using random number instead", t);
        }
        return processId;
    }


    /**
     * 生成序列号的类型
     */
    private enum Category {
        /**
         * 订单: 标识, 初始值, 步长, 最大值(只要保证一秒之内, 从初始化值加步长不会超时最大值就不会有重复)
         */
        Order(1, 16, 3, 10000000);

        /**
         * 每种类型的 标识 不要设置成相同!
         */
        int behavior, init, step, max;
        AtomicInteger counter;
        Lock lock;

        Category(int behavior, int init, int step, int max) {
            this.behavior = behavior;
            this.init = init;
            this.step = step;
            this.max = max - step;

            counter = new AtomicInteger(init);
            lock = new ReentrantLock();
        }

        public String no() {
            int increment = counter.addAndGet(step);
            if (increment >= max) {
                lock.lock();
                try {
                    if (increment >= max) {
                        increment = counter.getAndSet(init);
                    }
                } finally {
                    lock.unlock();
                }
            }
            // 只要保证一秒之内, 从初始化值加步长不会超时最大值就不会有重复
            return minUnit() + behavior + MP + increment;
        }
    }

    /**
     * 一个自旋规则内不会重复的最小单位. 乱序时间规则(避免被看出生成时间). 时年秒月分日 HHyyssMMmmdd 年有 2 位, 最后到秒
     */
    private static String minUnit() {
        return new SimpleDateFormat("HHyyssMMmmdd").format(new Date());
    }

    /**
     * 生成订单号. 最低 19 位: (到秒的 12 位 + 1 个占位 + 4 位网卡及进程 + 2 位进程自增值)
     */
    public static String getOrderNo() {
        return Category.Order.no();
    }
}