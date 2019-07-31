package com.cmd.exchange.task.crawler;


import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 */
public class HuobiMarketResponse {
    private List<HuobiMarketData> data;

    public static class HuobiMarketData {
        private Long id; //时间， 单位秒
        private BigDecimal open;
        private BigDecimal close;
        private BigDecimal low;
        private BigDecimal high;
        private BigDecimal amount; //成交量 -- 火币的volume是成交额

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public BigDecimal getOpen() {
            return open;
        }

        public void setOpen(BigDecimal open) {
            this.open = open;
        }

        public BigDecimal getClose() {
            return close;
        }

        public void setClose(BigDecimal close) {
            this.close = close;
        }

        public BigDecimal getLow() {
            return low;
        }

        public void setLow(BigDecimal low) {
            this.low = low;
        }

        public BigDecimal getHigh() {
            return high;
        }

        public void setHigh(BigDecimal high) {
            this.high = high;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }

    public List<HuobiMarketData> getData() {
        return data;
    }

    public void setData(List<HuobiMarketData> data) {
        this.data = data;
    }
}
