package com.cmd.exchange.admin.controller;

import com.cmd.exchange.admin.vo.EditCoinGroupConfigDetailVO;
import com.cmd.exchange.admin.vo.EditCoinGroupConfigSVO;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.CoinGroupConfig;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.vo.CoinGroupConfigVO;
import com.cmd.exchange.service.CoinGroupConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "币种分组配置模块")
@RestController
@RequestMapping("/coinGroup")
public class CoinGroupConfigController {
    @Autowired
    private CoinGroupConfigService coinGroupConfigService;

    @ApiOperation(value = "获取币种分组配置列表")
    @GetMapping("coin-group-config-list")
    public CommonResponse getCoinGroupConfigList(@ApiParam(value = "分组Id", required = false) @RequestParam(name = "groupId", required = false) Integer groupId) {

        List<CoinGroupConfigVO> list = coinGroupConfigService.getCionGroupConfigList(groupId);
        return new CommonResponse(list);
    }

    @ApiOperation(value = "单个或者批量修改币种分组配置")
    @PutMapping("update-coin-group-configs")
    public CommonResponse updateCoinGroupConfig(@RequestBody EditCoinGroupConfigDetailVO vo) {

        if (vo != null) {
            // if(editCoinGroupConfigSVO.getList()!=null&&editCoinGroupConfigSVO.getList().size()>0){
            List<CoinGroupConfig> list = new ArrayList<>();
            // List<EditCoinGroupConfigDetailVO> voList = editCoinGroupConfigSVO.getList();
            // for(EditCoinGroupConfigDetailVO vo:voList){
            CoinGroupConfig coinGroupConfig = new CoinGroupConfig();
            coinGroupConfig.setId(vo.getId());
            coinGroupConfig.setRemark(vo.getRemark());
            coinGroupConfig.setConValue(vo.getConValue());
            list.add(coinGroupConfig);
            // }
            coinGroupConfigService.updateCoinGroupConfig(list);
            // }

        }

        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }


    @ApiOperation(value = "单个或者批量修改币种分组配置")
    @GetMapping("get-coin-group-config-detail")
    public CommonResponse updateCoinGroupConfig(@ApiParam(value = "主键Id", required = false) @RequestParam(name = "id", required = false) Integer id) {
        return new CommonResponse(coinGroupConfigService.getCoinGroupConfigDetailById(id));
    }

}
