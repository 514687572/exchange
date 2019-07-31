package com.cmd.exchange.common.csvfile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * CSV导出之大量数据-导出压缩包
 * eg:http://blog.csdn.net/soconglin/article/details/6297534
 */
public class CsvExportThread extends Thread {

    public static Queue<Object> queue;//Queue是java自己的队列，具体可看API，是同步安全的

    static {
        queue = new ConcurrentLinkedQueue<Object>();
    }

    private static boolean isRunning = false;
    private ServletContext context = null;

    public CsvExportThread() {
    }

    public CsvExportThread(ServletContext context) {
        this.context = context;
    }

    public void run() {
        if (!isRunning) {
            isRunning = true;
            System.out.println("开始执行查询并放入queue队列");
            try {
                CsvExportThread();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            System.out.println("查询并放入queue队列结束");
            ThreadPools.createThreadPool();//唤起线程池
            isRunning = false;
        } else {
            System.out.println("上一次任务执行还未结束");
        }
    }


    public static void CsvExportThread() throws IOException {

        // 设置数据
        int listCount = 165100;
        //导出6万以上数据。。。
        int pageSize = 50000;//设置每一个excel文件导出的数量
        int quotient = listCount / pageSize + (listCount % pageSize > 0 ? 1 : 0) + 1;//循环次数
        for (int i = 0; i < quotient; i++) {
            List<Object> list = new ArrayList<Object>();
            int startCount = ((i > 0 ? i : 0) * pageSize);
            if ((listCount % pageSize) > 0) {
                if (i == (quotient - 1)) {
                    pageSize = (int) (listCount % pageSize);//余数
                }
            }
            list.add(i);
            list.add(startCount);
            list.add(pageSize);
            queue.offer(list);
            System.out.println(startCount + "----------------" + pageSize);
        }
//        ZipUtil.zipFiles(srcfile, new File("C:\\cap4j\\download.zip"));
//        ZipUtil.dropFolderOrFile(new File("C:\\cap4j\\download"));
        long endTime = System.currentTimeMillis();
        //分批CSV导出96715
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

    public static void main(String[] args) throws IOException {
        new CsvExportThread().start();
    }
}
