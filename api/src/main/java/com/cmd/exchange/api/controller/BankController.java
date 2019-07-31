package com.cmd.exchange.api.controller;

import com.cmd.exchange.api.vo.AtomVO;
import com.cmd.exchange.common.enums.BankType;
import com.cmd.exchange.common.oauth2.ShiroUtils;
import com.cmd.exchange.common.model.UserBank;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.service.UserBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户银行钱包（现实世界）相关接口")
@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    UserBankService userBankService;

    @ApiOperation(value = "新增银行钱包转账地址")
    @PostMapping(value = "add-bank-address")
    public CommonResponse<UserBank> addTransferAddress(
            @ApiParam(required = true, name = "name", value = "标识") @RequestParam(value = "name") String name,
            @ApiParam(required = true, name = "bankType", value = "银行类型，0：普通银行，1：支付宝，2：微信支付") @RequestParam(value = "bankType") Integer bankType,
            @ApiParam(required = true, name = "legalName", value = "法币名称") @RequestParam(value = "legalName") String legalName,
            @ApiParam(required = true, name = "bankUser", value = "银行/支付宝等账户名(真实姓名)") @RequestParam(value = "bankUser") String bankUser,
            @ApiParam(required = true, name = "bankName", value = "银行名称/支付宝/微信等") @RequestParam(value = "bankName") String bankName,
            @ApiParam(required = true, name = "bankNo", value = "银行卡号/支付宝微信账号") @RequestParam(value = "bankNo") String bankNo,
            @ApiParam(required = false, name = "receiptCode", value = "付款码") @RequestParam(value = "receiptCode", required = false) String receiptCode
    ) {
        UserBank userBank = new UserBank().setUserId(ShiroUtils.getUser().getId()).setName(name).setBankType(bankType).setLegalName(legalName)
                .setBankUser(bankUser).setBankName(bankName).setBankNo(bankNo).setReceiptCode(receiptCode);
        userBankService.add(userBank);
        return new CommonResponse(userBank);
    }

    @ApiOperation(value = "查询银行钱包转账地址")
    @GetMapping(value = "bank-addresses")
    public CommonResponse<UserBank> bankAddresses(@RequestParam(value = "bankType", required = false) Integer bankType) {
        if (bankType != null && bankType.intValue() != BankType.BANK.getValue()
                && bankType.intValue() != BankType.ALIPAY.getValue() && bankType.intValue() != BankType.WEIXIN.getValue())
            bankType = null;
        List<UserBank> list = userBankService.getUserBankByUserId(ShiroUtils.getUser().getId(), bankType);
        return new CommonResponse(list);
    }

    @ApiOperation(value = "修改银行钱包转账地址")
    @PostMapping(value = "update-bank-address")
    public CommonResponse updateTransferAddress(
            @ApiParam(required = true, name = "id", value = "id") @RequestParam(value = "id") Integer id,
            @ApiParam(required = true, name = "name", value = "标识") @RequestParam(value = "name") String name,
            @ApiParam(required = true, name = "legalName", value = "法币名称") @RequestParam(value = "legalName") String legalName,
            @ApiParam(required = true, name = "bankName", value = "银行名称/支付宝/微信等") @RequestParam(value = "bankName") String bankName,
            @ApiParam(required = true, name = "bankNo", value = "银行卡号/支付宝微信账号") @RequestParam(value = "bankNo") String bankNo
    ) {
        UserBank userBank = new UserBank().setId(id).setUserId(ShiroUtils.getUser().getId()).setName(name).setLegalName(legalName).setBankName(bankName).setBankNo(bankNo);
        userBankService.updateUserBank(ShiroUtils.getUser().getId(), userBank);
        return new CommonResponse();
    }

    @ApiOperation(value = "删除银行钱包转账地址")
    @DeleteMapping(value = "delete-bank-address")
    public CommonResponse deleteTransferAddress(
            @ApiParam(required = true, name = "id", value = "id") @RequestParam(value = "id") Integer id
    ) {
        userBankService.del(id, ShiroUtils.getUser().getId());
        return new CommonResponse();
    }

    @ApiOperation(value = "开启关闭，1开启0关闭")
    @PostMapping(value = "enable-disable-bank")
    public CommonResponse enableDisableBank(@ApiParam(required = true, name = "id") @RequestParam(value = "id") Integer id,
                                            @ApiParam(required = true, name = "status:1开启，0关闭") @RequestParam(value = "status") Integer status) {
        userBankService.updateUserBankStatus(ShiroUtils.getUser().getId(), id, status);
        return new CommonResponse();
    }

    @ApiOperation(value = "开启关闭，1开启0关闭")
    @PostMapping(value = "enable-disable")
    public CommonResponse enableDisable(@ApiParam(required = true, name = "bankType") @RequestParam(value = "bankType") Integer bankType,
                                        @ApiParam(required = true, name = "status:1开启，0关闭") @RequestParam(value = "status") Integer status) {
        userBankService.updateUserBankStatusByBankType(ShiroUtils.getUser().getId(), bankType, status);
        return new CommonResponse();
    }

    @ApiOperation("查看用户是否存在有效支付方式0：不存在，1：存在")
    @GetMapping("check-pay")
    public CommonResponse checkPay(
//            @ApiParam(required = true, name = "id", value = "id") @RequestParam(value = "id") Integer id
    ) {
        int id = ShiroUtils.getUser().getId();
        int result = userBankService.checkPay(id);

        return new CommonResponse(new AtomVO(String.valueOf(result)));
    }

}
