package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.model.LockCoinRecord;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.vo.UserBillVO;
import com.cmd.exchange.service.LockUserCoinService;
import com.cmd.exchange.service.UserService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Api(tags = "锁住用户的币分期释放")
@RestController
@Slf4j
@RequestMapping("lock-coin")
public class LockUserCoinController {
    @Autowired
    private LockUserCoinService lockUserCoinService;
    @Autowired
    private UserService userService;

    @ApiOperation("锁定用户资产")
    @PostMapping(value = "lock-user-coin")
    public CommonResponse lockUserCoin(
            @ApiParam("用户账号列表,逗号分隔") @RequestParam String userNames,
            @ApiParam("币种名称") @RequestParam String coinName,
            @ApiParam("锁仓比例,小数，必须小于等于1") @RequestParam double lockRate,
            @ApiParam("每次释放比例（占锁仓的比例）,小数，必须小于等于1") @RequestParam double releaseRate,
            @ApiParam("释放策略") @RequestParam int cronId) {
        lockUserCoinService.lockUserCoin(userNames, coinName, lockRate, releaseRate, cronId);
        return new CommonResponse();
    }

    @ApiOperation("锁定用户组资产")
    @PostMapping(value = "lock-group-coin")
    public CommonResponse lockGroupCoin(
            @ApiParam("用户组类别，例如（GROUP_TYPE_BASE）") @RequestParam String groupType,
            @ApiParam("币种名称") @RequestParam String coinName,
            @ApiParam("锁仓比例,小数，必须小于等于1") @RequestParam double lockRate,
            @ApiParam("每次释放比例（占锁仓的比例）,小数，必须小于等于1") @RequestParam double releaseRate,
            @ApiParam("释放策略") @RequestParam int cronId) {
        lockUserCoinService.lockUserGroupCoin(groupType, coinName, lockRate, releaseRate, cronId);
        return new CommonResponse();
    }

    @ApiOperation("获取锁仓历史记录")
    @GetMapping(value = "lock-history")
    public CommonListResponse<LockCoinRecord> getLockHistory(
            @ApiParam(value = "用户名") @RequestParam(name = "userName", required = false) String userName,
            @ApiParam("币种") @RequestParam(name = "coinName", required = false) String coinName,
            @ApiParam("批次") @RequestParam(name = "lockNo", required = false) String lockNo,
            @ApiParam(value = "格式 yyyy-mm-dd HH:mm:ss, 比如 2018-06-29 00:00:00", required = false) @RequestParam(name = "startTime", required = false) String startTime,
            @ApiParam(value = "结束时间(不包括这个时间点）", required = false) @RequestParam(name = "endTime", required = false) String endTime,
            @ApiParam("pageNo") @RequestParam Integer pageNo,
            @ApiParam("pageSize") @RequestParam Integer pageSize) {
        Integer userId = null;
        if (userName != null) {
            User user = userService.getUserByUserName(userName);
            if (user != null) {
                userId = user.getId();
            }
        }
        Page<LockCoinRecord> ls = lockUserCoinService.findRecord(userId, coinName, lockNo, startTime, endTime, pageNo, pageSize);
        // 填充用户信息
        for (LockCoinRecord vo : ls) {
            User user = userService.getUserByUserId(vo.getUserId());
            if (user != null) {
                vo.setUserName(user.getUserName());
            }
        }
        return new CommonListResponse().fromPage(ls);
    }

    @ApiOperation(value = "获取锁仓返还明细")
    @GetMapping(value = "lock-return-list")
    public CommonListResponse<UserBillVO> lockCoinReturnList(
            @ApiParam("用户名，不支持模糊查询") @RequestParam(required = false) String userName,
            @ApiParam("币种名称，不支持模糊查询") @RequestParam(required = false) String coinName,
            @ApiParam(value = "格式 yyyy-mm-dd HH:mm:ss, 比如 2018-06-29 00:00:00", required = false) @RequestParam(name = "startTime", required = false) String startTime,
            @ApiParam(value = "结束时间(不包括这个时间点）", required = false) @RequestParam(name = "endTime", required = false) String endTime,
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

        Page<UserBillVO> list = lockUserCoinService.getLockCoinReturnList(userId, coinName, startTime, endTime, pageNo, pageSize);
        // 填充用户信息
        for (UserBillVO vo : list) {
            User user = userService.getUserByUserId(vo.getUserId());
            if (user != null) {
                vo.setUserName(user.getUserName());
                vo.setRealName(user.getRealName());
                vo.setGroupType(user.getGroupType());
            }
        }
        return CommonListResponse.fromPage(list);
    }
}
