package com.cmd.exchange.common.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Data
public class TimeSeriesVo {
    private Long time;
    private BigDecimal price;

    public static TimeSeriesVo fromCandle(CandleVo candle) {
        TimeSeriesVo timeSeries = new TimeSeriesVo();
        timeSeries.setPrice(candle.getClose());
        timeSeries.setTime(candle.getTime());

        return timeSeries;
    }

    public static List<TimeSeriesVo> fromCandleList(List<CandleVo> candleList) {
        List<TimeSeriesVo> timeSeriesList = new ArrayList<>();
        for (CandleVo c : candleList) {
            timeSeriesList.add(fromCandle(c));
        }

        return timeSeriesList;
    }
}
