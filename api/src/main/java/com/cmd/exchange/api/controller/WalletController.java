package com.cmd.exchange.api.controller;

import com.cmd.exchange.common.oauth2.ShiroUtils;
import com.cmd.exchange.common.model.Coin;
import com.cmd.exchange.common.model.TransferAddress;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.service.WalletService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户数字货币钱包相关接口")
@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;

    @ApiOperation(value = "新增数字货币转账地址")
    @PostMapping(value = "add-transfer-address")
    public CommonResponse addTransferAddress(
            @ApiParam(required = true, name = "coinname", value = "币种") @RequestParam(value = "coinname") String coinname,
            @ApiParam(required = true, name = "name", value = "标识") @RequestParam(value = "name") String name,
            @ApiParam(required = true, name = "addr", value = "地址") @RequestParam(value = "addr") String addr
    ) {
        walletService.addTransferAddress(ShiroUtils.getUser().getId(), coinname, name, addr);
        return new CommonResponse();
    }

    @ApiOperation(value = "查询数字货币转账地址")
    @GetMapping(value = "transfer-addresses")
    public CommonListResponse<TransferAddress> transferAddresses(
            @ApiParam(required = false, name = "coinname", value = "币种(ltc,bbt,sobit)") @RequestParam(value = "coinname", required = false) String coinname,
            @ApiParam(name = "pageNo", value = "当前页(默认从 1 开始)") @RequestParam(value = "pageNo") Integer pageNo,
            @ApiParam(name = "pageSize", value = "每页条数") @RequestParam(value = "pageSize") Integer pageSize) {
        Page<TransferAddress> rst = walletService.getTransferList(ShiroUtils.getUser().getId(), coinname, pageNo, pageSize);
        return CommonListResponse.fromPage(rst);
    }

    @ApiOperation(value = "查询系统中可用的币种")
    @GetMapping(value = "coins")
    public CommonResponse<List<Coin>> getAllCoins() {
        List<Coin> rst = walletService.getAllCoins();
        // 禁止返回币种服务器信息
        for (Coin coin : rst) {
            coin.setServerAddress(null);
            coin.setServerPort(0);
            coin.setServerUser(null);
            coin.setServerPassword(null);
            coin.setCoinSelfParameter(null);
            coin.setDisplayNameAll(null);
        }
        return new CommonResponse(rst);
    }

    @ApiOperation(value = "修改数字货币转账地址")
    @PutMapping(value = "update-transfer-address")
    public CommonResponse updateTransferAddress(
            @ApiParam(required = true, name = "id", value = "ID") @RequestParam(value = "id") Integer id,
            @ApiParam(required = true, name = "name", value = "标识") @RequestParam(value = "name") String name,
            @ApiParam(required = true, name = "coinname", value = "币种") @RequestParam(value = "coinname") String coinname,
            @ApiParam(required = true, name = "addr", value = "地址") @RequestParam(value = "addr") String addr
    ) {
        walletService.updateTransferAddress(ShiroUtils.getUser().getId(), id, name, coinname, addr);
        return new CommonResponse();
    }

    @ApiOperation(value = "删除数字货币转账地址")
    @DeleteMapping(value = "delete-transfer-address")
    public CommonResponse deleteTransferAddress(
            @ApiParam(required = true, name = "id", value = "ID") @RequestParam(value = "id") Integer id
    ) {
        walletService.delTransferAddress(ShiroUtils.getUser().getId(), id);
        return new CommonResponse();
    }
}
