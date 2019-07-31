package com.cmd.exchange.admin.controller;

import com.cmd.exchange.admin.componet.excelbean.PatchSenCoinBean;
import com.cmd.exchange.admin.excel.ClassCasrUtil;
import com.cmd.exchange.admin.excel.ExcelReader;
import com.cmd.exchange.admin.service.ExcelPatchCoinService;
import com.cmd.exchange.admin.vo.PatchErrorVO;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.service.DispatchService;
import com.cmd.exchange.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "excel处理控制模块（目前支持发币发币、以及发币结果记录查询（此方法支持后期关于excel的其他操作））")
@RestController
@RequestMapping("/excel-operator")
@Slf4j
public class ExcelOperatorController {

    @Autowired
    private ExcelPatchCoinService excelPatchCoinService;

    @ApiOperation(value = "批量拨币")
    @GetMapping("patch-send-coin")
    public CommonResponse patchSendCoin(@ApiParam(value = "文件的绝对入境，包含文件具体名字") @RequestParam(name = "filePath", required = true) String filePath,
                                        @ApiParam(value = "币种名字") @RequestParam(name = "coinName", required = true) String coinName,
                                        @ApiParam(value = "拨币编号配置Id") @RequestParam(name = "dispatchId", required = true) Integer dispatchId
    ) {
        if (filePath == null || filePath == "" || coinName == null || coinName == "") {
            return new CommonResponse(ErrorCode.ERR_PARAM_ERROR, "文件的绝对路径为空或者币种名为空，发币失败");
        }
        List<PatchErrorVO> patchErrorVOS = new ArrayList<>();
        excelPatchCoinService.patchService(filePath, coinName, dispatchId);
        return new CommonResponse(patchErrorVOS);
    }

}
