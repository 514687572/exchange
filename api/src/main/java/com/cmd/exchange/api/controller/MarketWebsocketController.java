package com.cmd.exchange.api.controller;

import com.cmd.exchange.api.vo.CandleRequestVO;
import com.cmd.exchange.common.model.Market;
import com.cmd.exchange.common.utils.JSONUtil;
import com.cmd.exchange.common.vo.CandleResponse;
import com.cmd.exchange.common.vo.CandleVo;
import com.cmd.exchange.service.MarketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

@Api(tags = "交易市场Websocket接口")
@Controller
//必须配置endpoint, 直接在MessageMapping配置券路径无法收到send的数据
@MessageEndpoint("/ws/market")
public class MarketWebsocketController {
    @Autowired
    private MarketService marketService;
    @Autowired
    private SimpMessagingTemplate template;

    @ApiOperation("获取指定市场的k线数据 -- 和TradeView的参数保持一致")
    @MessageMapping(value = "candles/{nonce}")
    //@SendTo注解不会发送消息到动态的url上
    public void getCandles(
            String requestString,
            //通过随机数来接收send的返回
            @DestinationVariable String nonce
    ) {
        CandleRequestVO requestVO = JSONUtil.parseToObject(requestString, CandleRequestVO.class);
        Market market = new Market();
        String[] coinNameAndCurrency = requestVO.getSymbol().split("/");
        market.setCoinName(coinNameAndCurrency[0]).setSettlementCurrency(coinNameAndCurrency[1]);

        Date startTime = new Date(requestVO.getFrom() * 1000l);
        Date endTime = new Date(requestVO.getTo() * 1000l);

        send(market, requestVO.getResolution(), startTime, endTime, nonce);
    }

    private void send(Market m, String resolution, Date startTime, Date endTime, String nonce) {

        List<CandleVo> candles = marketService.getCandles(resolution, m.getCoinName(), m.getSettlementCurrency(), startTime, endTime);
        CandleResponse response = new CandleResponse();
        response.setKline(candles).setResolution(resolution).setSymbol(m.getCoinName() + "/" + m.getSettlementCurrency()).setType("kline");
        //通过随机数来发送到指定的连接上
        String destination = "/ws/market/candles?symbol=" + m.getCoinName() + "/" + m.getSettlementCurrency() + "&resolution=" + resolution + "&type=kline&nonce=" + nonce;
        template.convertAndSend(destination, response);
    }
}
