package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.Article;
import com.cmd.exchange.common.vo.ArticleVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;


public interface ArticleMapper {
    int add(Article article);

    Page<ArticleVo> getListByLocale(@Param("type") Integer type, @Param("locale") String locale, RowBounds rowBounds);

    //获取 启用状态 （app端使用）的 文章列表
    Page<ArticleVo> getActiveListByLocale(@Param("type") Integer type, @Param("locale") String locale, RowBounds rowBounds);

    ArticleVo getAgreement(@Param("locale") String locale);


    int updateStatus(Article article);

    int update(Article article);

    int delete(int id);

    ArticleVo getArticleById(@Param("articleId") Integer articleId);
}
