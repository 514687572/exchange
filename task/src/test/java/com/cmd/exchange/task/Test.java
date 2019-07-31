package com.cmd.exchange.task;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Test {


    public static void main(String[] s) {
        BigDecimal v1 = new BigDecimal(123.58456932545);
        BigDecimal v2 = new BigDecimal(10);

        BigDecimal divide = v1.divide(v2, 3, RoundingMode.FLOOR);
        System.out.println("金额：" + divide);
    }
}
