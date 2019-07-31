package com.cmd.exchange.admin.controller;

import com.cmd.exchange.admin.oauth2.ShiroUtils;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.InvestIncomeConfig;
import com.cmd.exchange.common.model.ProjectIntroduction;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.vo.InvestIncomeConfigVO;
import com.cmd.exchange.service.InvestIncomeConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Api(tags = "投资收益配置模块（PHP未用此模块接口）")
@RestController
@RequestMapping("/investIncomeConfig")
public class InvestIncomeConfigController {
    @Autowired
    private InvestIncomeConfigService investIncomeConfigService;

    @ApiOperation(value = "添加投资收益配置")
    @PostMapping(value = "/addInvestIncomeConfig")
    public CommonResponse addInvestIncomeConfig(@Valid @RequestBody InvestIncomeConfigVO investIncomeConfigVO) {
        investIncomeConfigVO.setCreatorId(ShiroUtils.getUser().getId());
        int i = investIncomeConfigService.addInvestIncomeConfig(investIncomeConfigVO);
        if (i > 0) {
            return new CommonResponse(ErrorCode.ERR_SUCCESS);
        }
        return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE);
    }

    @ApiOperation(value = "删除投资收益配置")
    @PostMapping(value = "/deleteInvestIncomeConfig")
    public CommonResponse deleteInvestIncomeConfig(@Valid @RequestParam Integer id) {
        investIncomeConfigService.deleteInvestIncomeConfig(id);
        return new CommonResponse();
    }

    @ApiOperation(value = "修改投资收益配置")
    @PutMapping(value = "/updateInvestIncomeConfig")
    public CommonResponse updateInvestIncomeConfig(@Valid @RequestBody InvestIncomeConfigVO investIncomeConfigVO) {
        investIncomeConfigService.updateInvestIncomeConfig(investIncomeConfigVO);
        return new CommonResponse();
    }

    @ApiOperation(value = "获取投资收益配置列表")
    @GetMapping(value = "/getInvestIncomeConfigList")
    public CommonResponse getInvestIncomeConfigList(
            @ApiParam(value = "页数") @RequestParam Integer pageNo,
            @ApiParam(value = "页码") @RequestParam Integer pageSize) {
        List<InvestIncomeConfigVO> investIncomeConfigList = investIncomeConfigService.getInvestIncomeConfigList(null,pageNo,pageSize);
        return new CommonResponse(investIncomeConfigList);
    }

}
