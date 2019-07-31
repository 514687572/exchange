package com.cmd.exchange.api.controller;


import com.cmd.exchange.api.vo.OfficialGroupVO;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.vo.AdResVO;
import com.cmd.exchange.service.AdService;
import com.cmd.exchange.service.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;


/**
 * Created by Administrator on 2018/5/31.
 */
@Api(tags = "广告管理")
@RequestMapping("/advertise")
@RestController
public class AdvertiseController {

    @Autowired
    private AdService adService;
    @Autowired
    private ConfigService configService;

    @ApiOperation(value = "查询广告列表")
    @GetMapping("")
    public CommonResponse<List<AdResVO>> getAdList(
            @ApiParam(value = "en_US: 英文, zh_CN: 中文简体 zh_TW:中文繁体") @RequestParam(required = false) String locale,
            @ApiParam(value = "客户端类型，1表示网页，2表示手机端，不填写表示网页") @RequestParam(required = false) Integer clientType
    ) {
        if (StringUtils.isBlank(locale)) {
            locale = Locale.SIMPLIFIED_CHINESE.toString();
        }
        if (clientType == null) {
            clientType = AdResVO.ClientType_WEB;
        }
        List<AdResVO> voList = adService.queryADList(locale, clientType);
        return new CommonResponse<>(voList);
    }

    @ApiOperation(value = "查询广告详情")
    @GetMapping("detail")
    public CommonResponse<AdResVO> getAdList(@ApiParam(value = "广告id") @RequestParam Integer adId) {
        AdResVO adResVO = adService.getAdDetail(adId);
        return new CommonResponse(adResVO);
    }

    @ApiOperation(value = "查询官方群信息")
    @GetMapping("official_group")
    public CommonResponse<OfficialGroupVO> getOfficialGroupInfo() {
        String qqLink = configService.getOfficalQQLink();
        String wxImageLink = configService.getOfficialWxImageLink();

        OfficialGroupVO vo = new OfficialGroupVO();
        vo.setQqLink(qqLink);
        vo.setWxImageLink(wxImageLink);
        return new CommonResponse(vo);
    }

}
