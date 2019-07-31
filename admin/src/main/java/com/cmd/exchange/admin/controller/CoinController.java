package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.model.Coin;
import com.cmd.exchange.common.model.ReceivedCoin;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.vo.CoinVO;
import com.cmd.exchange.service.CoinService;
import com.cmd.exchange.service.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "币种配置接口")
@RestController
@RequestMapping("/coin")
public class CoinController {

    @Autowired
    CoinService coinService;
    @Autowired
    TransferService transferService;

    @ApiOperation(value = "添加币种")
    @PostMapping(value = "")
    public CommonResponse<String> addCoin(@Valid @RequestBody CoinVO coinVO) {
        coinService.add(coinVO);
        return new CommonResponse<>();
    }

    @ApiOperation(value = "启用禁止币种")
    @GetMapping(value = "/enableOrDisable")
    public CommonResponse<String> enableOrDisableCoin(
            @ApiParam(value = "币种名") @RequestParam("name") String name,
            @ApiParam(value = "0:正常 1:禁止") @RequestParam("status") Integer status) {
        coinService.updateCoinStatus(name, status);
        return new CommonResponse<>();
    }

    @ApiOperation(value = "修改币种")
    @PutMapping(value = "")
    public CommonResponse<String> modifyCoin(@RequestBody CoinVO coinVO) {
        coinService.updateCoin(coinVO);
        return new CommonResponse<>();
    }

    @ApiOperation(value = "删除币种")
    @DeleteMapping(value = "")
    public CommonResponse<String> deleteCoin(@ApiParam(value = "币种名") @RequestParam("name") String name) {
        coinService.deleteCoin(name);
        return new CommonResponse<>();
    }

    @ApiOperation(value = "获取币种列表")
    @GetMapping("")
    public CommonResponse<List<CoinVO>> list() {
        List<Coin> list = coinService.getCoin();
        List<CoinVO> vos = new ArrayList<CoinVO>();
        for (Coin coin : list) {
            CoinVO vo = new CoinVO();
            BeanUtils.copyProperties(coin, vo);
            vos.add(vo);
        }
        return new CommonResponse<>(vos);
    }

    @ApiOperation(value = "币种详情")
    @GetMapping("/coin-info")
    public CommonResponse<CoinVO> getCoinInfo(@ApiParam(value = "币种名") @RequestParam("name") String name) {
        return new CommonResponse<>(coinService.getCoinInfo(name));
    }

    @ApiOperation(value = "统计所有收到的币")
    @GetMapping("/total-recv-coin")
    public CommonResponse<List<ReceivedCoin>> getTotalReceiveFromExternal() {
        List<ReceivedCoin> coin = transferService.getTotalReceiveFromExternal();
        return new CommonResponse<>(coin);
    }

    @ApiOperation(value = "统计今天收到的币")
    @GetMapping("/today-recv-coin")
    public CommonResponse<List<ReceivedCoin>> getTodayReceiveFromExternal() {
        List<ReceivedCoin> coin = transferService.getTodayReceiveFromExternal();
        return new CommonResponse<>(coin);
    }
}
