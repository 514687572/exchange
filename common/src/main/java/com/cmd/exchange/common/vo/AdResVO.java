package com.cmd.exchange.common.vo;

import com.cmd.exchange.common.enums.AdvertisementStatus;
import com.cmd.exchange.common.enums.AdvertisementType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 广告VO
 * Created by Administrator on 2018/5/31.
 */
@Getter
@Setter
public class AdResVO implements Serializable {

    private static final long serialVersionUID = 7092452864417787407L;

    public static final Integer ClientType_WEB = 1;     // 网页
    public static final Integer ClientType_PHONE = 2;     // 手机客户端

    @ApiModelProperty("id(不用传)")
    private Integer id;

    @ApiModelProperty("广告标题")
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;
    @ApiModelProperty("显示位置，如果有多个地方显示广告，可以指定位置")
    private String position;
    @ApiModelProperty("语言： zh_CN：中文简体， en_US:英文 zh_TW: 繁体")
    @NotBlank
    private String locale;

    @ApiModelProperty("客户端类型，1表示网页，2表示手机")
    private Integer clientType;

    @ApiModelProperty("广告图片")
    @NotBlank
    private String url;

    @ApiModelProperty("广告链接")
    private String link;

    @ApiModelProperty("状态：0:上线 1:下线")
    private AdvertisementStatus status;

    @ApiModelProperty("类型：0:链接 1:图文")
    @NotNull
    private AdvertisementType type;

    @ApiModelProperty(value = "图文富文本")
    @NotBlank
    private String content;

    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date lastTime;
    @ApiModelProperty(value = "广告开始时间, 格式yyyy-mm-dd hh:mm:ss")
    private String startTime;
    @ApiModelProperty(value = "广告结束时间 格式yyyy-mm-dd hh:mm:ss")
    private String endTime;

}
