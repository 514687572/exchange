package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.vo.WalletVo;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.vo.WalletVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户数字货币钱包相关接口")
@RestController
@RequestMapping("/wallet")
public class WalletController {

    @ApiOperation(value = "新增数字货币转账地址")
    @PostMapping(value = "add-transfer-address")
    public CommonResponse<WalletVo> addTransferAddress(
            @ApiParam(required = true, name = "coinname", value = "币种") @RequestParam(value = "coinname") String coinname,
            @ApiParam(required = true, name = "name", value = "标识") @RequestParam(value = "name") String name,
            @ApiParam(required = true, name = "addr", value = "地址") @RequestParam(value = "addr") String addr
    ) {
        return null;
    }

    @ApiOperation(value = "查询数字货币转账地址")
    @RequestMapping(value = "transfer-addresses")
    public CommonListResponse<WalletVo> transferAddresses(
            @ApiParam(required = true, name = "coinname", value = "币种(ltc,bbt,sobit)") @RequestParam(value = "coinname") String coinname,
            @ApiParam(name = "page", value = "当前页(默认从 0 开始)") @RequestParam(value = "page", required = false) String page,
            @ApiParam(name = "size", value = "每页条数") @RequestParam(value = "size", required = false) String size) {
        return null;
    }

    @ApiOperation(value = "修改数字货币转账地址")
    @RequestMapping(value = "update-transfer-address")
    public CommonResponse<WalletVo> updateTransferAddress(
            @ApiParam(required = true, name = "id", value = "ID") @RequestParam(value = "id") String id,
            @ApiParam(required = true, name = "name", value = "标识") @RequestParam(value = "name") String name
    ) {
        return null;
    }

    @ApiOperation(value = "删除数字货币转账地址")
    @RequestMapping(value = "delete-transfer-address")
    public CommonResponse<WalletVo> deleteTransferAddress(
            @ApiParam(required = true, name = "id", value = "ID") @RequestParam(value = "id") String id
    ) {
        return null;
    }
}
