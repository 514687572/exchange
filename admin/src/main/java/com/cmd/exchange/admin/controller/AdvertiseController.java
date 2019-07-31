package com.cmd.exchange.admin.controller;


import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.enums.AdvertisementStatus;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.AdResVO;
import com.cmd.exchange.service.AdService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;


/**
 * Created by Administrator on 2018/5/31.
 */
@Api(tags = "广告管理")
@RequestMapping("/advertise")
@RestController
public class AdvertiseController {

    @Autowired
    private AdService adService;

    @ApiOperation(value = "添加广告")
    @PostMapping("")
    public CommonResponse addAdvertise(@Valid @ApiParam(value = "status(状态：SHOW:上线 HIDE:下线)、type(类型：LINK:链接 TEXT:图文)、content富文本") @RequestBody AdResVO adResVO) {
        Assert.check(adResVO.getUrl() == null || adResVO.getUrl().equals(""), 1, "请上传广告图片");

        adResVO.setCreateTime(new Date());
        if (adResVO.getClientType() == null) {
            adResVO.setClientType(AdResVO.ClientType_WEB);
        }
        adService.add(adResVO);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation(value = "查询广告列表")
    @GetMapping("")
    public CommonListResponse<AdResVO> getAdList(@ApiParam(value = "创建开始时间") @RequestParam(name = "start", required = false) String start,
                                                 @ApiParam(value = "创建结束时间") @RequestParam(name = "end", required = false) String end,
                                                 @ApiParam(value = "页数") @RequestParam Integer pageNo,
                                                 @ApiParam(value = "页码") @RequestParam Integer pageSize) {
        Page<AdResVO> voList = adService.getADList(start, end, pageNo, pageSize);
        return CommonListResponse.fromPage(voList);
    }

    @ApiOperation(value = "查询广告详情")
    @GetMapping("detail")
    public CommonResponse<AdResVO> getAdList(@ApiParam(value = "广告id") @RequestParam Integer adId) {
        AdResVO adResVO = adService.getAdDetail(adId);
        return new CommonResponse(adResVO);
    }

    @ApiOperation(value = "上线下线")
    @GetMapping("onlineOrOffline")
    public CommonResponse onlineOrOffline(@ApiParam(value = "SHOW:上线 HIDE:下线") @RequestParam AdvertisementStatus status,
                                          @ApiParam(value = "广告id") @RequestParam Integer adId) {
        adService.updateStatus(status, adId);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation(value = "批量删除广告")
    @DeleteMapping("")
    public CommonResponse delAdvertiseBatch(@ApiParam(value = "广告id列表") @RequestBody Integer[] ids) {
        adService.delAdvertiseBatch(ids);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation(value = "编辑广告")
    @PutMapping("")
    public CommonResponse updateADInfo(@RequestBody AdResVO adResVO) {
        adResVO.setLastTime(new Date());
        adService.updateADInfo(adResVO);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }
}
