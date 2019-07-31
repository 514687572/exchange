package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.enums.ArticleStatus;
import com.cmd.exchange.common.enums.ArticleType;
import com.cmd.exchange.common.mapper.ArticleMapper;
import com.cmd.exchange.common.model.Article;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.ArticleVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Transactional
    public void addArticle(ArticleVo article) {
        article.setStatus(ArticleStatus.SHOW);
        if (article.getDisplayTime() == null) {
            article.setDisplayTime(new Date());
        }
        articleMapper.add(article.toModel());
    }

    public Page<ArticleVo> getArticleList(ArticleType type, String locale, int pageNo, int pageSize) {
        if (type != null && type == ArticleType.ALL) {
            type = null;
        }

        return articleMapper.getListByLocale(type != null ? type.getValue() : null, locale, new RowBounds(pageNo, pageSize));
    }

    public ArticleVo getAgreement(String locale) {
        return articleMapper.getAgreement(locale);
    }

    public ArticleType[] getArticleType() {
        return ArticleType.values();
    }

    @Transactional
    public void setArticleStatus(Integer[] articleIds, ArticleStatus status) {
        for (Integer articleId : articleIds) {
            int rows = articleMapper.updateStatus(new Article().setId(articleId).setStatus(status));
            Assert.check(rows == 0, ErrorCode.ERR_RECORD_UPDATE);
        }
    }

    @Transactional
    public void deleteArticle(Integer[] articleIds) {
        for (Integer articleId : articleIds) {
            int rows = articleMapper.delete(articleId);
            Assert.check(rows == 0, ErrorCode.ERR_RECORD_UPDATE);
        }

    }

    public void updateArticle(ArticleVo article) {
        Assert.check(article.getId() == null, ErrorCode.ERR_ARTICLE_NOT_EXIST);
        int rows = articleMapper.update(article.toModel());
        Assert.check(rows == 0, ErrorCode.ERR_RECORD_UPDATE);
    }

    public ArticleVo getArticleById(Integer articleId) {
        return articleMapper.getArticleById(articleId);
    }

    //获取启用状态的文章
    public Page<ArticleVo> getActiveArticleList(ArticleType type, String locale, Integer pageNo, Integer pageSize) {
        if (type != null && type == ArticleType.ALL) {
            type = null;
        }
        if (null == pageNo || null == pageSize) {
            return articleMapper.getActiveListByLocale(type != null ? type.getValue() : null, locale, new RowBounds());
        }
        return articleMapper.getActiveListByLocale(type != null ? type.getValue() : null, locale, new RowBounds(pageNo, pageSize));
    }
}
