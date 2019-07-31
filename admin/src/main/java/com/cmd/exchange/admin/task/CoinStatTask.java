package com.cmd.exchange.admin.task;

import com.cmd.exchange.common.mapper.ReceivedCoinMapper;
import com.cmd.exchange.common.model.UserCoin;
import com.cmd.exchange.service.CoinService;
import com.cmd.exchange.service.CoinStatService;
import com.cmd.exchange.service.MonitorDataService;
import com.cmd.exchange.service.SendCoinService;
import com.cmd.exchange.service.TradeStatService;
import com.cmd.exchange.service.UserCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CoinStatTask {
    @Autowired
    private CoinStatService coinStatService;

    @Autowired
    private CoinService coinService;

    @Autowired
    private TradeStatService tradeStatService;
    @Autowired
    private MonitorDataService monitorDataService;

    @Autowired
    private SendCoinService sendCoinService;

    @Autowired
    private UserCoinService userCoinService;

    @Autowired
    private ReceivedCoinMapper receivedCoinMapper;


}
