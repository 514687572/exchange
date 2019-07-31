package com.cmd.exchange.common.constants;

/**
 * 定义资金变化的各种原因
 */
public class UserBillReason {
    public static final String TRADE_MATCH = "TxMatch";    // 交易撮合
    public static final String TRADE_CREATE = "TxTrade";    // 交易市场下单
    public static final String TRADE_MATCH_FEE = "TxMatFee";   // 交易撮合费用
    public static final String TRADE_MATCH_RELEASE = "TxRel";     // 交易撮合返还冻结金
    public static final String TRADE_MATCH_RET = "TxMatRet";   // 交易撮合完成后返回的冻结金
    public static final String TRADE_BG_CANCEL = "TxBgCanc";   // 后台取消交易，因为交易超时
    public static final String TRADE_USER_CANCEL = "TxUsrCc";    // 用户取消交易
    public static final String TRADE_BONUS = "TxBonus";    // 交易费用奖励（挖矿）
    public static final String TRADE_BONUS_REA = "TxMinRe";    // 交易费用奖励（挖矿）额外释放
    public static final String TRADE_REC_BONUS = "TxRecBo";    // 交易费用推荐奖励（挖矿）（返回给推荐人的奖励）
    public static final String TRADE_REC_FEE = "TxRecFe";    // 交易费用返还推荐人（返回给推荐人的奖励）
    public static final String TRADE_SHARE_BONUS = "ShareOut";   // 交易费用分红返还(超级节点）
    public static final String TRADE_SAVING_BONUS = "SavBonus";   // 交易费用分红返还(存钱/分润宝）
    public static final String BC_RECEIVED_COIN = "BcRev";      // 从区块链中收到转账（外部进入）
    public static final String REGISTER_REWARD = "Register";    //注册赠送
    public static final String REFERRER_REWARD = "Referrer";    //推荐奖励
    public static final String FREEZE = "Freeze";       //冻结
    public static final String UNFREEZE = "UnFreeze";     //解冻
    public static final String TRANSFER = "Transfer";     //内部转账
    public static final String WITHDRAW = "Withdraw";     //外部提现
    public static final String C2C_FREEZE = "C2Freeze";     //c2c冻结
    public static final String C2C_FREE = "C2Free";        //c2c手续费
    public static final String C2C_SUCCESS = "C2Succes";      //c2c交易成功
    public static final String C2C_UNFREEZE = "C2UnFree";      //c2c解冻
    public static final String CON_TO_PLAT = "ConToPl";       //将数字货币转换成平台币（针对私募）
    public static final String CON_TO_PLAT_RELEASE = "ToPlRel";       //释放数字货币转换成平台币的冻结金（针对私募）
    public static final String DISPATCH_RELEASE = "disRel";         //拨币释放
    public static final String C2B_USER_SELL_COIN = "SellCoin";         // 普通用户卖出数字货币冻结
    public static final String C2B_USER_CC_SELL = "CcSell";       // 普通用户取消卖出数字货币
    public static final String C2B_USER_BUY_COIN = "BuyCoin";       // 普通用户买入数字货币

    public static final String LOCK_COIN = "LckCoin";       // 锁定用户可用金
    public static final String LOCK_COIN_RELEASE = "LckRel";        // 锁仓释放，就是释放LckCoin的锁定钱

    public static final String SAVING_ADD = "AddSav";       // 增加存款/锁仓
    public static final String SAVING_SUB = "SubSav";       // 减少存款/锁仓


    // 活动返还，这个临时出现
    public static final String TRADE_AWARD = "TxAward";    // 交易大赛奖金


    public static final String USER_AMOUNT_TRANSFERRED_INTO = "UATInto";      // 用户锁仓金额（外部转入）
    public static final String USER_DEDUCTION_INTEGRAL = "Integral";      // 用户活动金额扣除（商城对接业务）


    public static final String USER_MACHINE_BONUS = "MacBonus";   // 用户矿机收益 存入锁仓

    public static final String LOCK_TRANSFER_FEE = "LockTransferFee";   // 锁仓转账手续费

    public static final String TRADE_CONTRACTST = "TxContr";   //智能合约存入
    public static final String CONTRACTST_FEE = "TxCoFee";   //智能合约 每日返还所需手续费
    public static final String CONTRACTST_RECEIVE_FREEZE = "TxCoRF";   //智能合约 智能合约释放锁仓

    public static final String INVEST_SUB = "Invest";   //投资，减少可用资产
    public static final String INVEST_ADD = "InvestIncome";   //投资收益，增加可用资产
    public static final String DYNAMIC_INCOME = "dynamicIncome";   //动态分润
}
