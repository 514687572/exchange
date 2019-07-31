package com.cmd.exchange.api;

import com.cmd.exchange.common.utils.DateUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class Test {

    public static void main(String[] str) {


        System.out.println("divide2:" +  new Date(1000l * DateUtil.getDayBeginTimestamp(System.currentTimeMillis())));
    }
}
