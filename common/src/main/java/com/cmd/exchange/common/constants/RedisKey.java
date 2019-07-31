package com.cmd.exchange.common.constants;

/**
 * 定义各种配置缓存相应 模块_业务_Key值
 */
public class RedisKey {


    //------------------------------start外部接口模块--------------------------------------
    // 外部用户转入对应账户金额
    public static final String EXTERNAL_USERCHANGE = "external_userchange_";
    public static final String EXTERNAL_DEDUCTIONINTEGRAL = "external_deductionintegral_";


    //------------------------------外部接口模块end--------------------------------------
    //-----------------------------智能合约------------------------------------------------

    //每个合约订单上限smart_contractst_contractslimit_年月日时期+币种名称+合约订单ID
    public static final String SMART_CONTRACTST_CONTRACTSLIMIT_ = "smart_contractst_contractslimit_";
    public static final String SMART_CONTRACTST_LOCK_ = "smart_contractst_lock_";
    public static final String SMART_CONTRACTST_USERDAYLIMIT_ = "smart_contractst_userdaylimit_";

    //-----------------------------智能合约------------------------------------------------
}
