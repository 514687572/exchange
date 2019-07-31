package com.cmd.exchange.common.csvfile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date:
 * Time:
 * To change this template use File | Settings | File Templates.
 */
public class CsvExport {

    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();
        // 设置表格头
        Object[] head = {"序号", "小说名称", "作者", "出版日期"};
        List<Object> headList = Arrays.asList(head);
        List<List<Object>> dataList = getNovel();
        // 导出文件路径
        String downloadFilePath = "C:" + File.separator + "cap4j" + File.separator + "download" + File.separator;
        // 导出文件名称
        String fileName = "download";
        // 导出CSV文件
        File csvFile = CSVUtils.createCSVFile(headList, dataList, downloadFilePath, fileName);
        long endTime = System.currentTimeMillis();
        System.out.println("整个CSV导出" + (endTime - startTime));
    }

    private static List<List<Object>> getNovel() {
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        List<Object> rowList = null;
        for (int i = 0; i < 1651000; i++) {
            rowList = new ArrayList<Object>();
            Object[] row = new Object[4];
            row[0] = i;
            row[1] = "风云第一刀" + i + "";
            row[2] = "古龙" + i + "";
            row[3] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            for (int j = 0; j < row.length; j++) {
                rowList.add(row[j]);
            }
            dataList.add(rowList);
        }
        return dataList;
    }
}
