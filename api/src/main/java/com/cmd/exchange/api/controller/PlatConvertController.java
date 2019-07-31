package com.cmd.exchange.api.controller;

import com.cmd.exchange.common.annotation.OpLog;
import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.constants.OpLogType;
import com.cmd.exchange.common.oauth2.ShiroUtils;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.PlatCoinConvertVO;
import com.cmd.exchange.common.vo.UserCoinVO;
import com.cmd.exchange.service.ConfigService;
import com.cmd.exchange.service.PlatCoinConvertService;
import com.cmd.exchange.service.UserCoinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;

@Api(tags = "平台币（BON）兑换，用户将eth和usdt换成bon")
@RestController
@RequestMapping("/bon-convert")
public class PlatConvertController {
    private Log log = LogFactory.getLog(this.getClass());
    @Autowired
    private PlatCoinConvertService platCoinConvertService;
    @Autowired
    private UserCoinService userCoinService;

    // 检查当前是否可以申购，不可以抛出异常
    private void checkBuyTime() {
        try {
            PlatCoinConvertVO vo = platCoinConvertService.getNextTimeConvert();
            Assert.check(!vo.isNowCanBuy(), ErrorCode.ERR_CON_CAN_NOT_BUY_NOW);
        } catch (ParseException ex) {
            log.error("", ex);
        }
    }

    @ApiOperation(value = "将eth兑换成bon")
    @PostMapping("/eth-to-bon")
    @OpLog(type = OpLogType.OP_ETH_TO_BON, comment = "'eth=' + #eth")
    public CommonResponse<Integer> ethToPlatCoin(@ApiParam(name = "eth", value = "要兑换的eth个数", required = true) @RequestParam BigDecimal eth) {
        Assert.check(eth.compareTo(BigDecimal.ONE) < 0, ErrorCode.ERR_TRANSFER_AMOUNT_TO_LOW);
        checkBuyTime();
        int userId = ShiroUtils.getUser().getId();
        UserCoinVO coin = userCoinService.getUserCoinByUserIdAndCoinName(userId, "ETH");
        Assert.check(coin == null || coin.getAvailableBalance().compareTo(eth) < 0, ErrorCode.ERR_BALANCE_INSUFFICIENT);
        platCoinConvertService.ethToPlatCoin(userId, eth);
        return new CommonResponse<Integer>(0);
    }

    @ApiOperation(value = "将usdt兑换成bon")
    @PostMapping("/usdt-to-bon")
    @OpLog(type = OpLogType.OP_USDT_TO_BON, comment = "'usdt=' + #usdt")
    public CommonResponse<Integer> usdtToPlatCoin(@ApiParam(name = "usdt", value = "要兑换的usdt个数", required = true) @RequestParam int usdt) {
        Assert.check(usdt < 500, ErrorCode.ERR_TRANSFER_AMOUNT_TO_LOW);
        checkBuyTime();
        int userId = ShiroUtils.getUser().getId();
        UserCoinVO coin = userCoinService.getUserCoinByUserIdAndCoinName(userId, "USDT");
        Assert.check(coin == null || coin.getAvailableBalance().compareTo(new BigDecimal(usdt)) < 0, ErrorCode.ERR_BALANCE_INSUFFICIENT);
        platCoinConvertService.usdtToPlatCoin(userId, new BigDecimal(usdt));
        return new CommonResponse<Integer>(0);
    }

    @ApiOperation(value = "获取下次申购时间，如果当前处于申购中，返回的是当前时间")
    @GetMapping("/bon-buy-time")
    public CommonResponse<PlatCoinConvertVO> getNextTimeConvert() throws ParseException {
        PlatCoinConvertVO vo = platCoinConvertService.getNextTimeConvert();
        return new CommonResponse(vo);
    }

}
