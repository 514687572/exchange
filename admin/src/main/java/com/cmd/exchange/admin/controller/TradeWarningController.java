package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.TradeWarning;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.TradeWarningVO;
import com.cmd.exchange.service.TradeWarningService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "交易警告配置模块")
@RestController
@RequestMapping("/trade-warning")
public class TradeWarningController {

    @Autowired
    private TradeWarningService tradeWarningService;

    @ApiOperation(value = "交易报警置列表")
    @GetMapping("get-trade-warning-list")
    public CommonListResponse<List<TradeWarningVO>> getMarketGroupConfigList(@ApiParam(value = "交易对的Id", required = false) @RequestParam(required = false) Integer marketId,
                                                                             @ApiParam(value = "页数") @RequestParam Integer pageNo,
                                                                             @ApiParam(value = "页码") @RequestParam Integer pageSize) {
        Page<TradeWarningVO> marketGroupConfigList = tradeWarningService.getTradeWarningList(marketId, pageNo, pageSize);
        return CommonListResponse.fromPage(marketGroupConfigList);

    }

    @ApiOperation(value = "获取报警配置详情")
    @GetMapping("get-trade-warning-byId")
    public CommonResponse getTradeWarningById(@ApiParam(value = "警告配置Id", required = true) @RequestParam(required = true) Integer id) {
        TradeWarningVO tradeWarningVO = tradeWarningService.getTradeWarningById(id);
        return new CommonResponse(tradeWarningVO);
    }

    @ApiOperation(value = "增加交易对报警配置")
    @PostMapping("add-trade-warning")
    public CommonResponse addTradeWarning(@RequestBody TradeWarning tradeWarning) {
        TradeWarning tradeWarning1 = tradeWarningService.getTradeWarningByMarketId(tradeWarning.getMarketId(), tradeWarning.getType());
        if (tradeWarning1 != null) {
            return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE, "请勿重复添加");
        }
        tradeWarningService.addTradeWarning(tradeWarning);

        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }


    @ApiOperation(value = "修改交易对手续配置列表")
    @PutMapping("update-trade-warning")
    public CommonResponse updateTradeWarning(@RequestBody TradeWarning tradeWarning) {
        tradeWarningService.updateTradeWarningById(tradeWarning);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation(value = "一个或者多个删除交易对报警配置")
    @DeleteMapping("del-trade-warning-byIds")
    public CommonResponse deleteTradeWarningByIds(@RequestBody Integer[] Ids) {
        Assert.check(Ids == null || Ids.length == 0, ErrorCode.ERR_RECORD_NOT_EXIST);
        for (Integer id : Ids) {
            tradeWarningService.deleteTradeWarningById(id);
        }
        return new CommonResponse();
    }

}
