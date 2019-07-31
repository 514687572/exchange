package com.cmd.exchange.common.vo;

import com.cmd.exchange.common.model.UserBank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OtcOrderVO {
    private static final long serialVersionUID = -1661830493897252605L;

    private Long id;
    private String orderNo;
    private Long depositApplicationId;
    private Long withdrawalApplicationId;
    private Integer depositUserId;
    private Integer withdrawalUserId;
    private String depositUserName;
    private String withdrawalUserName;
    private Integer status;
    private String coinName;
    private String legalName;
    private BigDecimal price;
    private BigDecimal amount;
    private BigDecimal feeRate;
    private String withdrawalDesc;
    private String depositDesc;
    private String credentialComment;
    private String credentialUrls;
    private String cancelDesc;
    private String remark;
    private Date acceptTime;
    private Date uploadCredentialTime;
    private Date cancelTime;
    private Date expireTime;
    private Date finishTime;
    private Date createTime;
    private Date lastTime;

    //申诉相关
    private Integer appealRole;
    private String appealDesc;
    private String appealCode;
    private Integer payType;
    private Integer type;

    private List<UserBank> list;
}
