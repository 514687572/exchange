package com.cmd.exchange.common.excel;

/**
 * Created by Acewill on 2017/5/15.
 */
public enum ReportColumnType {
    DATE, //日期类型，显示时需要显示成 "2017-05-15 15:23:34"
    NUMERIC; //数值， 主要用于导出成excel时能设置对应列的样式
}
