package com.cmd.exchange.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CandleResponse {
    private List<CandleVo> kline;
    private String type;
    private String symbol;
    private String resolution;
}
