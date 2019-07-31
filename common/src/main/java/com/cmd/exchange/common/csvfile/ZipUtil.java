package com.cmd.exchange.common.csvfile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


public class ZipUtil {

    private static final Log log = LogFactory.getLog(ZipUtil.class);


    /**
     * 压缩文件
     *
     * @param srcfile File[] 需要压缩的文件列表
     * @param zipfile File 压缩后的文件
     */
    public static void zipFiles(List<File> srcfile, File zipfile) {
        byte[] buf = new byte[1024];
        try {
            // Create the ZIP file
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
            // Compress the files
            for (int i = 0; i < srcfile.size(); i++) {
                File file = srcfile.get(i);
                FileInputStream in = new FileInputStream(file);
                // Add ZIP entry to output stream.
                out.putNextEntry(new ZipEntry(file.getName()));
                // Transfer bytes from the file to the ZIP file
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                // Complete the entry
                out.closeEntry();
                in.close();
            }
            // Complete the ZIP file
            out.close();
        } catch (IOException e) {
            log.error("ZipUtil zipFiles exception:" + e);
        }
    }

    /**
     * 解压缩
     *
     * @param zipfile File 需要解压缩的文件
     * @param descDir String 解压后的目标目录
     */
    public static void unZipFiles(File zipfile, String descDir) {
        try {
            // Open the ZIP file
            ZipFile zf = new ZipFile(zipfile);
            for (Enumeration entries = zf.entries(); entries.hasMoreElements(); ) {
                // Get the entry name
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String zipEntryName = entry.getName();
                InputStream in = zf.getInputStream(entry);
                // System.out.println(zipEntryName);
                OutputStream out = new FileOutputStream(descDir + zipEntryName);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                // Close the file and stream
                in.close();
                out.close();
            }
        } catch (IOException e) {
            log.error("ZipUtil unZipFiles exception:" + e);
        }
    }

    /**
     * 将指定文件夹打包成zip
     *
     * @param folder
     * @throws IOException
     */
    public static void zipFile(String folder) throws IOException {
        File zipFile = new File(folder + ".zip");
        if (zipFile.exists()) {
            zipFile.delete();
        }
        ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(zipFile));
        File dir = new File(folder);
        File[] fs = dir.listFiles();
        byte[] buf = null;
        if (fs != null) {
            for (File f : fs) {
                zipout.putNextEntry(new ZipEntry(f.getName()));
                FileInputStream fileInputStream = new FileInputStream(f);
                buf = new byte[2048];
                BufferedInputStream origin = new BufferedInputStream(fileInputStream, 2048);
                int len;
                while ((len = origin.read(buf, 0, 2048)) != -1) {
                    zipout.write(buf, 0, len);
                }
                zipout.flush();
                origin.close();
                fileInputStream.close();
            }
        }
        zipout.flush();
        zipout.close();
    }

    /**
     * 删除文件夹
     *
     * @param file
     */
    public static void dropFolderOrFile(File file) {
        if (file.isDirectory()) {
            File[] fs = file.listFiles();
            for (File f : fs) {
                dropFolderOrFile(f);
            }
        }
        file.delete();
    }
    /**导出
     * @param response
     * @param fileName
     * @return
     * @throws IOException
     */
//    public static void downloadFile(HttpServletRequest request,HttpServletResponse response,StringBuilder uri)
//            throws IOException {
//        //获取服务其上的文件名称
//        StringBuffer filename = new StringBuffer();
//      filename.append(request.getSession().getServletContext().getRealPath("/"));
//        filename.append(uri);
//        File file = new File(filename.toString());
//
//        StringBuffer sb = new StringBuffer();
//        sb.append("attachment;  filename=").append(uri.substring(uri.lastIndexOf("//")+1));
//        response.setHeader("Expires", "0");
//        response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
//        response.setHeader("Pragma", "public");
//        response.setContentType("application/x-msdownload;charset=UTF-8");
//        response.setHeader("Content-Disposition", new String( sb.toString().getBytes(), "ISO8859-1"));
//
//        //将此文件流写入到response输出流中
//        FileInputStream inputStream = new FileInputStream(file);
//        OutputStream outputStream = response.getOutputStream();
//        byte[] buffer = new byte[1024];
//        int i = -1;
//        while ((i = inputStream.read(buffer)) != -1) {
//            outputStream.write(buffer, 0, i);
//        }
//        outputStream.flush();
//        outputStream.close();
//        inputStream.close();
//    }


    /**
     * Main
     *
     * @param args
     */
    public static void main(String[] args) {
        List<File> srcfile = new ArrayList<File>();
        srcfile.add(new File("C:\\cap4j\\download\\客户列表3.csv"));
        srcfile.add(new File("C:\\cap4j\\download\\客户列表4.csv"));
        File zipfile = new File("C:\\cap4j\\download\\edm.zip");
        ZipUtil.zipFiles(srcfile, zipfile);
    }
}
