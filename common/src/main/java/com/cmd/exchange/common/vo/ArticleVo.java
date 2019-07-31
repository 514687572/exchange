package com.cmd.exchange.common.vo;

import com.cmd.exchange.common.enums.ArticleStatus;
import com.cmd.exchange.common.enums.ArticleType;
import com.cmd.exchange.common.model.Article;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;


import javax.security.auth.login.LoginContext;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ArticleVo implements Serializable {
    private static final long serialVersionUID = -4247999277428744812L;
    private Integer id;
    @Size(min = 1, max = 120, message = "文章标题长度必须在1-120个字符之间")
    private String title;

    private String titleEn;
    // 类型
    @NotNull(message = "文档类型不能为空")
    @ApiModelProperty()
    private ArticleType type;
    //
    @NotBlank
    @NotNull(message = "文档地区不能为空")
    private String locale;
    // 内容
    // @NotBlank
    // @NotNull(message = "文档内容不能为空")
    @ApiModelProperty(value = "文档内容")
    private String content;


    // 状态
    private ArticleStatus status;


    private Integer sort;//显示排序， 数值越小越靠前
    @JsonIgnore
    private long creator;
    private String creatorName;
    private Date createTime;
    private Date updatedAt;
    @ApiModelProperty(value = "显示时间，不配置则使用最后更新时间")
    private Date displayTime;

    public Article toModel() {
        Article model = new Article();
        BeanUtils.copyProperties(this, model);
        return model;
    }

    public Date getDisplayTime() {
        return displayTime != null ? displayTime : updatedAt;
    }
}
