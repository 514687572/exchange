package com.cmd.exchange.admin.controller;

import com.cmd.exchange.admin.service.componet.NoWarningUserComponet;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.TradeNoWarningUser;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.service.TradeNoWarningUserService;
import com.cmd.exchange.service.UserService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Api(tags = "警告特殊账号处理模块")
@RestController
@RequestMapping("/tradeNowarningUser")
public class TradeNoWarningUserController {

    @Autowired
    private TradeNoWarningUserService tradeNoWarningUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private NoWarningUserComponet noWarningUserComponet;

    @ApiOperation(value = "根据类型获取特殊账号列表")
    @GetMapping("get-no-warning-user-list")
    public CommonListResponse<List<TradeNoWarningUser>> getMarketGroupConfigList(@ApiParam(value = "用户名", required = false) @RequestParam(required = false) String userName,
                                                                                 @ApiParam(value = "特殊账号的类型：挂单类型、高频交易类型", required = true) @RequestParam(required = true) String noWarningType,
                                                                                 @ApiParam(value = "页数") @RequestParam Integer pageNo,
                                                                                 @ApiParam(value = "页码") @RequestParam Integer pageSize) {
        Page<TradeNoWarningUser> nowarningUserList = tradeNoWarningUserService.getTadeNoWarningUserList(userName, noWarningType, pageNo, pageSize);
        return CommonListResponse.fromPage(nowarningUserList);

    }


    @ApiOperation(value = "增加特殊账号,值需要传入手机号或者邮箱")
    @PostMapping("add-no-warning-user")
    public CommonResponse addNoWarningUser(@RequestBody TradeNoWarningUser tradeNoWarningUser) {
        if (tradeNoWarningUser != null) {
            TradeNoWarningUser tradeNoWarningUser1 = tradeNoWarningUserService.getTradeNoWarningByuserName(tradeNoWarningUser.getUserName(), tradeNoWarningUser.getNoWarningType());
            if (tradeNoWarningUser1 == null) {
                TradeNoWarningUser tradeNoWarningUser2 = new TradeNoWarningUser();
                User user = userService.getUserByMobile(tradeNoWarningUser.getUserName());

                if (user != null) {

                    tradeNoWarningUser2.setNoWarningType(tradeNoWarningUser.getNoWarningType());
                    tradeNoWarningUser2.setUserId(user.getId());
                    tradeNoWarningUser2.setUserName(tradeNoWarningUser.getUserName());
                    tradeNoWarningUser2.setCreateTime(new Date());

                } else {
                    user = userService.getUserByEmail(tradeNoWarningUser.getUserName());
                    if (user != null) {
                        tradeNoWarningUser2.setNoWarningType(tradeNoWarningUser.getNoWarningType());
                        tradeNoWarningUser2.setUserId(user.getId());
                        tradeNoWarningUser2.setUserName(tradeNoWarningUser.getUserName());
                        tradeNoWarningUser2.setCreateTime(new Date());

                    } else {
                        return new CommonResponse(ErrorCode.ERR_USER_NOT_EXIST);
                    }
                }
                tradeNoWarningUserService.addTradeNoWarningUser(tradeNoWarningUser2);
                //初始化缓存
                noWarningUserComponet.initNoWarningUser();
                return new CommonResponse(ErrorCode.ERR_SUCCESS);
            }
            return new CommonResponse(ErrorCode.ERR_PARAM_ERROR);
        }
        return new CommonResponse(ErrorCode.ERR_PARAM_ERROR);
    }

    @ApiOperation(value = "删除一个或者多个特殊账号")
    @DeleteMapping("del-no-warning-user")
    public CommonResponse delTradeNowarningUser(@ApiParam(value = "需要删除的id", required = true) @RequestBody(required = true) Integer[] Ids) {
        Assert.check(Ids == null || Ids.length == 0, ErrorCode.ERR_RECORD_NOT_EXIST);
        for (Integer id : Ids) {
            tradeNoWarningUserService.delTradeNoWarningUserById(id);
        }
        //初始化缓存
        noWarningUserComponet.initNoWarningUser();
        return new CommonResponse();
    }
}
