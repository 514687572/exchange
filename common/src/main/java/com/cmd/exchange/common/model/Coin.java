package com.cmd.exchange.common.model;

import com.cmd.exchange.common.constants.CoinStatus;
import com.cmd.exchange.common.constants.CoinStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(value = {"serverAddress", "serverPort", "serverUser", "serverPassword", "coinSelfParameter"})
public class Coin implements Serializable {
    public static final int RECEIVED_FREEZE_YES = 1;    // 转入冻结
    public static final int RECEIVED_FREEZE_NO = 0;     // 转入不冻结

    private static final long serialVersionUID = -1L;
    private Integer id;
    // 币种名称
    private String name;
    // 币种符号
    private String symbol;
    // 币种类别,参考CoinCategory
    private String category;
    // 默认显示名
    private String displayName;
    // 所有的显示名，用于多语言
    private String displayNameAll;
    // 大图片
    private String image;
    // 小图片
    private String icon;
    // 排序顺序
    private Integer sort;
    // 币的状态
    private Integer status;
    // 区块链服务器地址
    private String serverAddress;
    // 服务器断开
    private Integer serverPort;
    // 区块链服务器用户
    private String serverUser;
    // 区块链服务器密码
    private String serverPassword;
    // 对外发送币所需要收的手续费
    private float sendFee;
    // 从外接收币所需要收的手续费
    private float receivedFee;
    // 区块链服务器合约地址，目前只有类型是token才有用
    private String contractAddress;
    // 区块链服务器其它参数信息
    private String coinSelfParameter;
    //创建时间
    private Date createTime;
    // 最后一次更新时间
    private Date lastTime;
    //主账户地址(提现时使用)
    private String coinBase;
    // 是否是转入直接冻结，是的话这个币转入的时候，会到转入冻结的资金里面,0 表示否，1表示是
    private Integer receivedFreeze;
    // 普通用户交易时释放锁仓金额的比例
    private BigDecimal releaseRate;
    // VIP用户交易时释放锁仓金额的比例
    private BigDecimal releaseRateVip;
    // 每天释放的最多次数
    private Integer maxDayRelNum;
    // 每月最大释放的量
    private BigDecimal maxMonthRel;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 小数点位数，这个不在数据库里面
    private int decimals = 18;
    //释放锁仓金额的比例
    private BigDecimal revFreezeRate;
    //每天释放锁仓金额比例上限
    private BigDecimal releaseDayLimitRate;
    //锁仓/转入冻结释放策略,交易时释放策略，0：不释放，1买释放，2卖释放，3买卖都释放
    private Integer releasePolicy;
    //卖出时普通用户不同价格释放比例配置
    private BigDecimal sellReleaseConf;
    //用户挂卖单是判断用户的卖单是否大于等于下单时间卖一的5%（比例在后台设置），若小于于卖一5%则此单不释放
    private BigDecimal sellPriceRate;
    //智能合约每日释放锁仓上限
    private BigDecimal smartReleaseRate;
}
