package com.cmd.exchange.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class GlobalMonitor {
    @ApiModelProperty(value = "挂单监控 true ：表示正常 false:表示不正常")
    private Boolean tradeBool = true;
    @ApiModelProperty(value = "挂单监控的交易对")
    private Set<String> tradeList = new HashSet<>();

    @ApiModelProperty(value = "持币监控 true ：表示正常 false:表示不正常")
    private Boolean cashBool = true;

    @ApiModelProperty(value = "持仓监控 true ：表示正常 false:表示不正常")
    private Boolean positionsBool = true;

    @ApiModelProperty(value = "高频交易监控 true ：表示正常 false:表示不正常")
    private Boolean hfTradeBool = true;

    @ApiModelProperty(value = "高频交易的交易对")
    private Set<String> hfTradeList = new HashSet<>();

    @ApiModelProperty(value = "在线人数")
    private Integer onlieNumber = 0;

    @ApiModelProperty(value = "服务器监控 true ：表示正常 false:表示不正常")
    private Boolean serviceBool = true;


}
