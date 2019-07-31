package com.cmd.exchange.admin.controller;

import com.cmd.exchange.admin.service.UserRewardService;
import com.cmd.exchange.admin.vo.ReferralRewardVO;
import com.cmd.exchange.admin.vo.RegisterRewardReqVO;
import com.cmd.exchange.admin.vo.RegisterRewardVO;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.vo.ShareOutBonusLogRequestVO;
import com.cmd.exchange.common.vo.ShareOutBonusLogVO;
import com.cmd.exchange.common.vo.TradeBonusLogRequestVO;
import com.cmd.exchange.common.vo.TradeBonusLogVO;
import com.cmd.exchange.service.UserBonusService;
import com.cmd.exchange.service.UserService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@Api(tags = "用户奖励接口")
@RestController
@RequestMapping("/reward")
public class RewardController {
    @Autowired
    private UserRewardService userRewardService;
    @Autowired
    private UserBonusService userBonusService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取注册奖励列表")
    @GetMapping(value = "register")
    public CommonListResponse<RegisterRewardVO> getRegisterReward(
            @ApiParam("不支持模糊查询") @RequestParam(required = false) String mobile,
            @ApiParam(value = "开始时间, 格式 yyyy-mm-dd HH:mm:ss, 比如 2018-06-29 12:00:00") @RequestParam(required = false) String startTime,
            @ApiParam(value = "结束时间") @RequestParam(required = false) String endTime,
            @RequestParam Integer pageNo,
            @RequestParam Integer pageSize
    ) {

        RegisterRewardReqVO req = new RegisterRewardReqVO();
        req.setMobile(mobile).setStartTime(startTime)
                .setEndTime(endTime).setPageNo(pageNo).setPageSize(pageSize);

        Page<RegisterRewardVO> articleList = userRewardService.getUserRegisterReward(req);

        return CommonListResponse.fromPage(articleList);
    }

    @ApiOperation(value = "获取推荐奖励列表")
    @GetMapping(value = "referral")
    public CommonListResponse<ReferralRewardVO> getRegisterReward(
            @ApiParam("推荐人电话, 不支持模糊查询") @RequestParam(required = false) String referrerMobile,
            @ApiParam("被推荐人电话， 不支持模糊查询") @RequestParam(required = false) String mobile,
            @ApiParam(value = "开始时间, 格式 yyyy-mm-dd HH:mm:ss, 比如 2018-06-29 12:00:00") @RequestParam(required = false) String startTime,
            @ApiParam(value = "结束时间") @RequestParam(required = false) String endTime,
            @RequestParam Integer pageNo,
            @RequestParam Integer pageSize
    ) {
        RegisterRewardReqVO req = new RegisterRewardReqVO();
        req.setMobile(mobile).setStartTime(startTime)
                .setEndTime(endTime).setPageNo(pageNo).setPageSize(pageSize);
        req.setReferrerMobile(referrerMobile);

        Page<ReferralRewardVO> articleList = userRewardService.getUserReferralRewardVO(req);

        return CommonListResponse.fromPage(articleList);
    }

    @ApiOperation(value = "获取挖矿返佣列表")
    @GetMapping(value = "mining-bonus")
    public CommonListResponse<TradeBonusLogVO> getMiningBohus(
            @Valid TradeBonusLogRequestVO request
    ) {
        Page<TradeBonusLogVO> articleList = userBonusService.getTradeBonusList(request);
        return CommonListResponse.fromPage(articleList);
    }

    @ApiOperation(value = "分红奖励列表")
    @GetMapping(value = "share-out-bonus")
    public CommonListResponse<ShareOutBonusLogVO> get(
            @Valid ShareOutBonusLogRequestVO request
    ) {
        Page<ShareOutBonusLogVO> articleList = userBonusService.getShareBonusList(request);
        return CommonListResponse.fromPage(articleList);
    }

    @ApiOperation(value = "获取交易返佣列表")
    @GetMapping(value = "trade-reward-rec")
    public CommonListResponse<RegisterRewardVO> getTradeRewardRec(
            @ApiParam("用户名，不支持模糊查询") @RequestParam(required = false) String userName,
            @ApiParam("币种名称，不支持模糊查询") @RequestParam(required = false) String coinName,
            @ApiParam(value = "格式 yyyy-mm-dd HH:mm:ss, 比如 2018-06-29 00:00:00", required = false) @RequestParam(name = "startTime", required = false) Date startTime,
            @ApiParam(value = "结束时间(不包括这个时间点）", required = false) @RequestParam(name = "endTime", required = false) Date endTime,
            @RequestParam Integer pageNo,
            @RequestParam Integer pageSize
    ) {
        Integer userId = null;
        if (userName != null) {
            User user = userService.getUserByUserName(userName);
            if (user != null) {
                userId = user.getId();
            }
        }

        Page<RegisterRewardVO> articleList = userRewardService.getTradeRewardRec(userId, coinName, startTime, endTime, pageNo, pageSize);
        return CommonListResponse.fromPage(articleList);
    }

}
