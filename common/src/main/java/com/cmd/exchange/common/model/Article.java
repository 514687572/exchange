package com.cmd.exchange.common.model;

import com.cmd.exchange.common.enums.ArticleStatus;
import com.cmd.exchange.common.enums.ArticleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Article implements Serializable {
    private static final long serialVersionUID = -1L;
    private Integer id;
    // 管理员
    private String title;
    // 操作类型
    private ArticleType type;
    // 操作时间
    private String locale;
    // 操作地址
    private String content;
    // 备注信息
    private ArticleStatus status;
    private long creator;
    private Date createTime;
    //app上显示的时间，如果没有设置， 则使用最后更新时间
    private Date displayTime;
    private Date updatedAt;


}
