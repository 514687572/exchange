package com.cmd.exchange.admin.controller;

import com.cmd.exchange.admin.oauth2.ShiroUtils;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.Airdrop;
import com.cmd.exchange.common.model.InvestIncomeConfig;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.vo.AirdropVO;
import com.cmd.exchange.common.vo.InvestIncomeConfigVO;
import com.cmd.exchange.service.AirdropService;
import com.cmd.exchange.service.InvestIncomeConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Api(tags = "空投任务配置模块")
@RestController
@RequestMapping("/airdrop")
public class AirdropController {
    @Autowired
    private AirdropService airdropService;

    @ApiOperation(value = "添加空投任务")
    @PostMapping(value = "/addAirdrop")
    public CommonResponse addAirdrop(@Valid @RequestBody AirdropVO airdropVO) {
        airdropVO.setUserId(ShiroUtils.getUser().getId());
        int i = airdropService.addAirdrop(airdropVO);
        if (i > 0) {
            return new CommonResponse(ErrorCode.ERR_SUCCESS);
        }
        return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE);
    }

    @ApiOperation(value = "删除空投任务")
    @PostMapping(value = "/deleteAirdrop")
    public CommonResponse deleteAirdrop(@Valid @RequestParam Integer id) {
        airdropService.deleteAirdrop(id);
        return new CommonResponse();
    }

    @ApiOperation(value = "关闭空投任务")
    @PutMapping(value = "/closeAirdrop")
    public CommonResponse closeAirdrop(
            @ApiParam(value = "id", required = true) @RequestParam(required = true) Integer id) {
        airdropService.closeAirdrop(id);
        return new CommonResponse();
    }

    @ApiOperation(value = "修改空投任务")
    @PutMapping(value = "/updateAirdrop")
    public CommonResponse updateAirdrop(@Valid @RequestBody AirdropVO airdropVO) {
        airdropService.updateAirdrop(airdropVO);
        return new CommonResponse();
    }

    @ApiOperation(value = "获取空投任务列表")
    @GetMapping(value = "/getAirdropList")
    public CommonResponse getAirdropList(
            @ApiParam(value = "页数") @RequestParam Integer pageNo,
            @ApiParam(value = "页码") @RequestParam Integer pageSize) {
        List<Airdrop> airdropList = airdropService.getAirdropList(pageNo,pageSize);
        return new CommonResponse(airdropList);
    }

    @ApiOperation(value = "获取空投详情")
    @GetMapping(value = "/getAirdropById")
    public CommonResponse getAirdropById(@ApiParam(value = "id", required = true) @RequestParam(required = true) Integer id) {
        return new CommonResponse(airdropService.getAirdropById(id));
    }

}
