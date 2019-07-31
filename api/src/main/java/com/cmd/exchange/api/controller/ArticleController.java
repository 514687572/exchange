package com.cmd.exchange.api.controller;

import com.cmd.exchange.common.enums.ArticleType;
import com.cmd.exchange.common.model.Article;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.vo.ArticleVo;
import com.cmd.exchange.service.ArticleService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "公告和资讯接口")
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "获取文章详情")
    @GetMapping(value = "detail")
    public CommonResponse<ArticleVo> getArticleByLocale(
            @ApiParam(value = "articleId", required = true) @RequestParam Integer articleId
    ) {
        ArticleVo article = articleService.getArticleById(articleId);

        return new CommonResponse(article);
    }

    @ApiOperation(value = "获取文章列表")
    @GetMapping(value = "")
    public CommonResponse<List<ArticleVo>> getArticleByLocale(
            @ApiParam(value = "NOTICE: 公告， NEWS：资讯") @RequestParam ArticleType type,
            @ApiParam(value = "en_US: 英文, zh_CN: 中文简体 zh_TW:中文繁体") @RequestParam String locale,
            @RequestParam Integer pageNo,
            @RequestParam Integer pageSize
    ) {
        Page<ArticleVo> articleList = articleService.getActiveArticleList(type, locale, pageNo, pageSize);

        return CommonListResponse.fromPage(articleList);
    }

    @ApiOperation(value = "获取指定类型的文档，只返回一条数据")
    @GetMapping(value = "type")
    public CommonResponse<ArticleVo> getDetails(
            @ApiParam(value = "FEE:费用说明, SERVICE: 服务条款, PRIVACY:隐私说明, JOIN_US:加入我们， PLATFORM：平台说明, API: api文档. TICKET: 工单支持， APPLICATION: 上市申请， MERCHANT: 商家认证公告") @RequestParam ArticleType type,
            @ApiParam(value = "en_US: 英文, zh_CN: 中文简体 zh_TW:中文繁体") @RequestParam String locale
    ) {
        Page<ArticleVo> articleList = articleService.getActiveArticleList(type, locale, 1, 1);

        return articleList.size() > 0 ? new CommonResponse(articleList.get(0)) : new CommonResponse<>();
    }

    @ApiOperation(value = "获取帮助列表")
    @GetMapping(value = "help")
    public CommonResponse<List<ArticleVo>> getArticleByLocale(
            @RequestParam String locale,
            @RequestParam Integer pageNo,
            @RequestParam Integer pageSize
    ) {
        Page<ArticleVo> articleList = articleService.getActiveArticleList(ArticleType.HELP, locale, pageNo, pageSize);

        return CommonListResponse.fromPage(articleList);
    }

    @ApiOperation(value = "获取注册协议")
    @GetMapping(value = "agreement")
    public CommonResponse<ArticleVo> getArticleByLocale(
            @RequestParam String locale
    ) {
        ArticleVo agreement = articleService.getAgreement(locale);

        return new CommonResponse<>(agreement);
    }
}
