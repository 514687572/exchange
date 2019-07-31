package com.cmd.exchange.common.csvfile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * CSV导出之大量数据-导出压缩包
 * eg:http://blog.csdn.net/soconglin/article/details/6297534
 */
public class CsvExportBatch {

    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();
        // 设置表格头
        Object[] head = {"序号", "小说名称", "作者", "出版日期"};
        List<Object> headList = Arrays.asList(head);
        // 设置数据
        int listCount = 16510000;
        //导出6万以上数据。。。
        int pageSize = 50000;//设置每一个excel文件导出的数量
        int quotient = listCount / pageSize + (listCount % pageSize > 0 ? 1 : 0);//循环次数
        List<File> srcfile = new ArrayList<File>();
        for (int i = 0; i < quotient; i++) {
            int startCount = ((i > 0 ? i : 0) * pageSize);
            if ((listCount % pageSize) > 0) {
                if (i == (quotient - 1)) {
                    pageSize = (int) (listCount % pageSize);//余数
                }
            }
            List<List<Object>> dataList = getNovel(startCount, pageSize);
            // 导出文件路径
            String downloadFilePath = "C:" + File.separator + "cap4j" + File.separator + "download" + File.separator;
            // 导出文件名称
            String fileName = String.valueOf(i);
            // 导出CSV文件
            File csvFile = CSVUtils.createCSVFile(headList, dataList, downloadFilePath, fileName);
            srcfile.add(csvFile);
        }
        ZipUtil.zipFiles(srcfile, new File("C:\\cap4j\\download.zip"));
        ZipUtil.dropFolderOrFile(new File("C:\\cap4j\\download"));
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
