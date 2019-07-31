package com.cmd.exchange.common.csvfile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-2-5
 * Time: 下午12:20
 * To change this template use File | Settings | File Templates.
 */
/*
 *线程类实现Runnable接口
 */
public class ExecutorThread implements Runnable {
    private List<Object> obj;
    private int delay;

    public ExecutorThread(List<Object> obj, int delay) {
        this.obj = obj;
        this.delay = delay;
    }

    long startTime = System.currentTimeMillis();

    public void run() {
        /*
         *这里就是线程需要处理的业务了，可以换成自己的业务
         */
        try {
            if (obj != null) {
                // 设置表格头
                Object[] head = {"序号", "小说名称", "作者", "出版日期"};
                List<Object> headList = Arrays.asList(head);
                List<List<Object>> dataList = getNovel(Integer.parseInt(obj.get(1).toString()), Integer.parseInt(obj.get(2).toString()));
                // 导出文件路径
                String downloadFilePath = "C:" + File.separator + "cap4j" + File.separator + "download" + File.separator;
                // 导出文件名称
                System.out.println("运行的线程数" + Thread.activeCount() + "----------------" + obj.get(0).toString() + "-----------" + obj.get(2).toString());
                String fileName = obj.get(0).toString();
                // 导出CSV文件
                File csvFile = CSVUtils.createCSVFile(headList, dataList, downloadFilePath, fileName);
                long endTime = System.currentTimeMillis();
            }
            Thread.sleep(delay);
        } catch (InterruptedException ignored) {
        }
        long endTime = System.currentTimeMillis();
        System.out.println("分批CSV导出" + (endTime - startTime));
    }

    private static List<List<Object>> getNovel(int startCount, int pagesize) {
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        List<Object> rowList = null;
        for (int i = 0; i < pagesize; i++) {
            rowList = new ArrayList<Object>();
            Object[] row = new Object[4];
            int endCount = startCount + i;
            row[0] = endCount;
            row[1] = "风云第一刀" + endCount + "";
            row[2] = "古龙" + endCount + "";
            row[3] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            for (int j = 0; j < row.length; j++) {
                rowList.add(row[j]);
            }
            dataList.add(rowList);
        }
        return dataList;
    }
}
