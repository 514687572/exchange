package com.cmd.exchange.common.csvfile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-2-28
 * Time: 下午4:32
 * To change this template use File | Settings | File Templates.
 */
public class DownLoad {

    public static void down(HttpServletResponse response) throws Exception {
        File csvFile = new File("D:\\software\\相关文档\\FreeMarker_Manual_zh_CN.pdf");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(csvFile.getName(), "UTF-8"));
        response.setHeader("Content-Length", String.valueOf(csvFile.length()));
        bis = new BufferedInputStream(new FileInputStream(csvFile));
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        while (true) {
            int bytesRead;
            if (-1 == (bytesRead = bis.read(buff, 0, buff.length))) break;
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();
    }

    public static void main(String[] args) throws Exception {
        HttpServletResponse response = null;
        down(response);
    }
}
