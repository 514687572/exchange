package com.cmd.exchange.common.vo;

import com.cmd.exchange.common.model.ProjectIntroduction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProjectIntroductionVo implements Serializable {
    private static final long serialVersionUID = -4247999277428744814L;

//    @ApiModelProperty(value = "id主键", required = true)
    @JsonIgnore
    private Integer id;
    // 类型
    @ApiModelProperty(value = "文本类型", required = true)
    private Integer type;
    // 状态
    @ApiModelProperty(value = "文本状态", required = true)
    private Integer status;
    @JsonIgnore
    private Integer creatorId;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;
    @ApiModelProperty(value = "文本内容", required = true)
    private String content;

    public ProjectIntroduction toModel() {
        ProjectIntroduction model = new ProjectIntroduction();
        BeanUtils.copyProperties(this, model);
        return model;
    }
}
