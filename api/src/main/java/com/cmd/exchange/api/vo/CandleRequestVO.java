package com.cmd.exchange.api.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CandleRequestVO {
    private String resolution;

    private String symbol;
    private String type;
    private Long from;
    private Long to;
}
