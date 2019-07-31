package com.cmd.exchange.admin.controller;

import com.cmd.exchange.admin.model.AdminEntity;
import com.cmd.exchange.admin.oauth2.ShiroUtils;
import com.cmd.exchange.common.enums.ArticleStatus;
import com.cmd.exchange.common.enums.ArticleType;
import com.cmd.exchange.common.model.Admin;
import com.cmd.exchange.common.model.Article;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.vo.ArticleVo;
import com.cmd.exchange.service.ArticleService;
import com.github.pagehelper.Page;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "公告和资讯接口")
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "获取文章类型列表")
    @GetMapping(value = "type")
    public CommonResponse<List<ArticleType>> getArticleType() {
        ArticleType[] types = articleService.getArticleType();
        return new CommonResponse(types);
    }

    @ApiOperation(value = "添加文章")
    @PostMapping(value = "")
    public CommonResponse<String> addArticle(
            @Valid @RequestBody ArticleVo article) {
        Gson gson = new Gson();
        String result = gson.toJson(article);
        ArticleVo article1 = gson.fromJson(result, ArticleVo.class);
        System.out.println("====" + article1);
        AdminEntity creator = ShiroUtils.getUser();
        article.setCreator(creator.getId());
        articleService.addArticle(article);
        return new CommonResponse<>();
    }

    @ApiOperation(value = "修改文章")
    @PutMapping(value = "")
    public CommonResponse<String> updateArticle(
            @Valid @RequestBody ArticleVo article) {
        articleService.updateArticle(article);
        return new CommonResponse<>();
    }

    @ApiOperation(value = "禁用文章")
    @PostMapping(value = "disable")
    public CommonResponse<String> disableArticle(
            @RequestBody Integer[] articleIds) {
        articleService.setArticleStatus(articleIds, ArticleStatus.HIDE);
        return new CommonResponse<>();
    }

    @ApiOperation(value = "删除文章")
    @PostMapping(value = "delete")
    public CommonResponse<String> deleteArticle(
            @RequestBody Integer[] articleIds) {
        articleService.deleteArticle(articleIds);
        return new CommonResponse<>();
    }

    @ApiOperation(value = "启用文章")
    @PostMapping(value = "enable")
    public CommonResponse<String> enableArticle(
            @Valid @RequestBody Integer[] articleIds) {
        articleService.setArticleStatus(articleIds, ArticleStatus.SHOW);
        return new CommonResponse<>();
    }

    @ApiOperation(value = "获取文章列表")
    @GetMapping(value = "")
    public CommonListResponse<ArticleVo> getArticleByLocale(
            @ApiParam("NOTICE: 公告, NEWS：资讯, HELP：帮助, AGREEMENT:  注册协议, ALL:所有") @RequestParam(required = false) ArticleType type,
            @ApiParam("zh_CN: 中文简体 en_US: 英文") @RequestParam(required = false) String locale,
            @RequestParam Integer pageNo,
            @RequestParam Integer pageSize
    ) {
        Page<ArticleVo> articleList = articleService.getArticleList(type, locale, pageNo, pageSize);

        return CommonListResponse.fromPage(articleList);
    }

    @ApiOperation(value = "获取文章详情")
    @GetMapping(value = "detail")
    public CommonResponse<ArticleVo> getArticleByLocale(
            @ApiParam(value = "articleId", required = true) @RequestParam Integer articleId
    ) {
        ArticleVo article = articleService.getArticleById(articleId);

        return new CommonResponse(article);
    }

    @ApiOperation(value = "获取帮助列表")
    @GetMapping(value = "help")
    public CommonListResponse<ArticleVo> getArticleByLocale(
            @RequestParam String locale,
            @RequestParam Integer pageNo,
            @RequestParam Integer pageSize
    ) {
        Page<ArticleVo> articleList = articleService.getArticleList(ArticleType.HELP, locale, pageNo, pageSize);

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
