package com.cmd.exchange.api;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;


public class UserSqlGenerator {
    public static void main(String[] args) throws IOException {
        //generateUserCoinSql();
    }

    private static void generateUserSql() throws IOException {
        URL fileUrl = UserSqlGenerator.class.getResource("user-template.sql");
        File file = new File(fileUrl.getFile());

        //#性能测试模拟用户数据, 在代码中替换用户名和电话即可, 密码a123456
        String content = new String(Files.readAllBytes(Paths.get(file.getPath())));

        FileWriter writer = new FileWriter("d:/data/user.sql");
        BufferedWriter bw = new BufferedWriter(writer);

        for (int i = 0; i < 10000; ++i) {
            bw.write(String.format(content, "test-" + i, "test-" + i) + "\n");
        }

        bw.close();
        writer.close();
    }

    private static void generateUserCoinSql(Integer startUserId, Integer endUserId) throws IOException {
        URL fileUrl = UserSqlGenerator.class.getResource("user-coin-template.sql");
        File file = new File(fileUrl.getFile());

        //#性能测试模拟用户数据, 在代码中替换用户名和电话即可, 密码a123456
        String content = new String(Files.readAllBytes(Paths.get(file.getPath())));

        FileWriter writer = new FileWriter("d:/data/user-coin.sql");
        BufferedWriter bw = new BufferedWriter(writer);

        for (int i = startUserId; i <= endUserId; ++i) {
            bw.write(String.format(content, i, i) + "\n");
        }

        bw.close();
        writer.close();
    }
}
