package com.cmd.exchange.admin.controller;

import com.cmd.exchange.admin.service.OnlineCountService;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Api(tags = "在线人数获取模块")
@RestController
@RequestMapping("/online_count")
@Slf4j
public class OnlineCountController {
    @Autowired
    private OnlineCountService onlineCountService;

    @ApiOperation(value = "获取在线人数")
    @GetMapping("/get_online_count")
    public CommonResponse<Integer> getOnlineCount() {
        try {
            Integer count = onlineCountService.getOnlineCount();
            return new CommonResponse(count);
        } catch (Exception e) {
            log.error("", e);
        }
        return new CommonResponse(ErrorCode.ERR_RECORD_DATA_ERROR, "请求错误");
    }
}
