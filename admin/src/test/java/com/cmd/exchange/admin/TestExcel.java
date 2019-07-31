package com.cmd.exchange.admin;

import com.cmd.exchange.admin.excel.ExcelReader;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TestExcel {

    public static void main(String[] args) {
        try {
            ExcelReader eh = new ExcelReader("D:\\桌面\\userTest.xlsx", "Sheet1");
            System.out.println("列的数量" + ExcelReader.cellNumber);
            System.out.println("行的数量" + ExcelReader.rowNumber);
            List<String> titles = eh.getTitleList(eh, ExcelReader.cellNumber);
            List<Object> userList = eh.getDataList(UserDTO.class, eh, ExcelReader.cellNumber, +ExcelReader.rowNumber + 1, titles);
            List<UserDTO> list = new ArrayList<>();
            for (Object object : userList) {
                UserDTO userDTO = get(UserDTO.class, object);
                list.add(userDTO);
                System.out.println(object);
            }

            for (UserDTO userDTO : list) {
                System.out.println(userDTO.getName());
            }

        } catch (Exception e) {
            log.error("Exception", e);
        }
    }


    public static <T> T get(Class<T> clz, Object o) {
        if (clz.isInstance(o)) {
            return clz.cast(o);
        }
        return null;
    }

}
