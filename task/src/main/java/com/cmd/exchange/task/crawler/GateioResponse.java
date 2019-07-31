package com.cmd.exchange.task.crawler;


import sun.nio.cs.ext.Big5;

import java.math.BigDecimal;
import java.util.List;

public class GateioResponse {

    /* time: 时间戳
     volume: 交易量
     close: 收盘价
     high: 最高价
     low: 最低价
     open: 开盘价*/
    private List<String[]> data;

    public List<String[]> getData() {
        return data;
    }

    public void setData(List<String[]> data) {
        this.data = data;
    }

    public static class GateIoData {
        private Long time;//时间戳
        private BigDecimal volume;// 交易量
        private BigDecimal close; //收盘价
        private BigDecimal high; ///最高价
        private BigDecimal low; //最低价
        private BigDecimal open; //开盘价

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        public BigDecimal getVolume() {
            return volume;
        }

        public void setVolume(BigDecimal volume) {
            this.volume = volume;
        }

        public BigDecimal getClose() {
            return close;
        }

        public void setClose(BigDecimal close) {
            this.close = close;
        }

        public BigDecimal getHigh() {
            return high;
        }

        public void setHigh(BigDecimal high) {
            this.high = high;
        }

        public BigDecimal getLow() {
            return low;
        }

        public void setLow(BigDecimal low) {
            this.low = low;
        }

        public BigDecimal getOpen() {
            return open;
        }

        public void setOpen(BigDecimal open) {
            this.open = open;
        }
    }

}
