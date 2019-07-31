package com.cmd.exchange.common.constants;

/**
 * 定义各种配置Key的值
 */
public class ConfigKey {

    // 在交易平台撮合交易的任务名称
    public static final String TASK_NAME_TRADE_MATCH = "TradeBgMatchTask";

    // 后台分红的任务名称
    public static final String TASK_NAME_SHARE_OUT_BONUS = "ShareOutBonusTask";

    // 上次更新市场昨天最后成交价的时间
    public static final String UPDATE_MARKET_LAST_DAY_PRICE = "UpdateMarketLastDayPrice";

    // 上次撮合到的trade id
    public static final String LAST_MATCH_TRADE_ID = "LastMatchTradeId";

    // 上次取消过期交易的任务
    public static final String LAST_CANCEL_TRADE_TASK = "LastCancelTradeTask";

    // 没有完成的交易有效期，单位是秒
    public static final String TRADE_VALID_TIME = "TradeValidTime";

    // 上次同步到的区块高度前缀，后面接币种名称才是真正的key名称
    public static final String BC_LAST_SYNC_BLOCK_PRE = "LastSyncBlock_";

    // 上次同步到的eth区块高度前缀，后面接币种名称才是真正的key名称
    public static final String BC_LAST_SYNC_ETH_PRE = "LastSyncEth_";

    // 最后一个已经挖矿的交易id，避免重复挖矿
    public static final String LAST_MINE_TRADE_ID = "trade.mining.last_id";
    // 挖矿后释放相对挖矿而外释放平台币的比例数
    public static final String MINE_EXT_RELEASE_RATE = "mining.ext.release.rate";
    // 挖矿后释放相对挖矿而外释放平台币的ID
    public static final String MINE_EXT_RELEASE_ID = "mining.ext.release.id";

    public static final String REGISTER_REWARD = "user.register.reward";
    public static final String REFERRER_REWARD = "user.referrer.reward";
    public static final String REFERRER_URL = "user.referrer.url";
    public static final String PLATFORM_COIN_NAME = "platform.coin.name";
    // 交易市场的基本币种
    public static final String MARKET_BASE_COIN_NAME = "market.base.coin";
    public static final String BCN_PRICE = "bcn.price";
    // 交易挖矿返佣（返平台币）百分比， 20代表20%
    public static final String TRADE_MINING_REWARD = "trade.mining.reward";
    // 交易挖矿推荐返佣（返平台币）百分比， 20代表20%，就是一个交易的后有奖励，他的推荐人也有奖励
    public static final String TRADE_MINING_REC_REWARD = "trade.mining.recommend.reward";
    // 交易(分红)返还手续费用（交易币种）百分比， 20代表20%
    public static final String TRADE_FEE_BONUS = "trade.fee.reward";
    // 已经分红的截止时间
    public static final String TRADE_SHARE_OUT_END_TIME = "trade.share.out.end.time";
    // 交易(分红)返还手续费用（交易币种）百分比， 20代表20%
    public static final String TRADE_MIN_SHOUT_COIN = "trade.min.shout.coin";
    //推荐奖励排名 -- 后台配置排名数据
    public static final String REFERRER_REWARD_RANKING = "referrer.reward.ranking";
    // 某个账号余额大于等于这个金额的时候将会回收这个账号的以太币到基础账号(前缀）
    public static final String GATHER_MIN_BALANCE_PRE = "gather.min.balance.";
    // 账号以太币的时候，剩余这个数量，主要用于做交易费用的，避免交易失败(前缀）
    public static final String GATHER_REMAIN_PRE = "gather.remain.";
    // 以太坊某个账号余额大于等于这个金额的时候将会回收这个账号的以太币到基础账号
    public static final String USDT_GATHER_MIN_BALANCE = "usdt.gather.min.balance";
    //官方微信名片图片链接
    public static final String OFFICIAL_WX_LINK = "official_group.wx_image_link";
    //官方qq群链接
    public static final String OFFICAL_QQ_LINK = "official_group.qq_link";
    // 1个ETH兑换成平台币（BON）的数量（属性非通用）
    public static final String ETH_TO_PLAT_NUM = "convert.eth.to.plat";
    // 1个USDT兑换成平台币（BON）的数量（属性非通用）
    public static final String USDT_TO_PLAT_NUM = "convert.usdt.to.plat";
    // 兑换成平台币的时候冻结的百分比，20表示20%，小于1的话直接当做百分比使用
    public static final String CONVERT_FREEZE_PERCENT = "convert.freeze.percent";
    // 支持兑换的时间
    public static final String CONVERT_BON_TIME = "convert.plat.time";
    // 支持释放的开始时间
    public static final String CONVERT_BON_RELEASE_TIME = "convert.release.time";
    // 收集各种币所扫描到的最后一个id前缀
    public static final String GATHER_LAST_RECV_ID_PRE = "gather.last.id.";
    // 收到币的时候发送短信，0表示不发送
    public static final String SEND_MSG_ON_RECV_COIN = "send.msg.recv.coin";
    // 收到币的时候发送短信格式，例如“平台已经收到你的币:%f”
    public static final String SEND_MSG_ON_RECV_COIN_FORMAT = "send.msg.recv.coin.format";
    // ETH转账指定gas price
    public static final String ETH_GAS_PRICE = "eth.gas.price";
    public static final String ETH_GAS_LIMIT = "eth.gas.limit";
    // 兑换商充值提币超时时间（秒）
    public static final String MERCHANT_ORDER_TIMEOUT = "merchant.order.timeout";
    // 通用的显示法币，默认是人民币（CNY）
    public static final String COM_LEGAL_MONEY_DISP = "com.legal.money.display";
    // 汇聚以太币大白的时候，需要往账户里面转的eth手续费
    public static final String GATHER_TRANSFER_PRE_PRE = "gather.transfer.fee.";
    // 汇聚以太币大白的时候，需要往账户里面转的eth手续费
    public static final String USER_TRADE_STAT_LAST_LOG_ID = "user.trade.stat.last.log.id";
    // 返还交易费用分润到超级节点用户的时间
    public static final String REWARD_TRADE_FEE_LAST_TIME = "reward.trade.fee.last.time";
    // 返还交易费用到推荐用户的比例
    public static final String TRADE_FEE_REC_USER = "reward.trade.fee.rec.user";
    // 返还交易费用到推荐用户的推荐用户的比例
    public static final String TRADE_FEE_REC_REC_USER = "reward.trade.fee.rec.rec.user";
    // 超级节点分润比例（USDT交易手续费费）
    public static final String TRADE_FEE_SUPER_USER_RATE = "trade.fee.super.user.rate";
    // 社区节点分润比例（USDT交易手续费费）
    public static final String TRADE_FEE_COMMUNITY_USER_RATE = "trade.fee.community.user.rate";
    // 高级节点分润比例（USDT交易手续费费）
    public static final String TRADE_FEE_SENIOR_USER_RATE = "trade.fee.senior.user.rate";
    // 分润宝(存钱用户）分润比例（USDT交易手续费费）
    public static final String TRADE_FEE_SAVING_RATE = "trade.fee.coin.saving.rate";
    // 返还交易费用分润到分润宝用户的时间
    public static final String REWARD_COIN_SAVING_LAST_TIME = "reward.coin.saving.last.time";
    // 存钱分润宝参与分红的最小金额
    public static final String REWARD_MIN_COIN_SAVING = "reward.min.saving.coin";


    // 挖矿难度系数,就是每单位数量的锁仓币能挖矿的金额
    public static final String TRADE_MINING_QUOTIETY = "trade.mining.quotiety";
    // 挖矿锁仓的单位数量
    public static final String TRADE_MINING_SAVE_UNIT = "trade.mining.saving.unit";
    // 转入冻结金在交易的时候释放占交易总额的比例
    public static final String TRADE_RECEIVED_RELEASE_RATE = "trade.received.release.rate";
    // 转入冻结金在交易的时候释放占交易总额的比例
    public static final String TRADE_RECEIVED_RELEASE_RATE_VIP = "trade.received.release.rate.vip";
    // 每个交易对2个相同的人每天获取奖励的最多交易奖励次数,默认5次
    public static final String TRADE_SAME_REWARD_MAX_TIMES = "trade.same.reward.max.times";
    //同步区块转入冻结规则币种名称，多个逗号隔开
    public static final String SYN_FREEZE_RULE_COIN_NAME = "syn.freeze.rule.coin.name";
    //是否开启同步区块转入冻结规则(0不开启，1开启)
    public static final String SYN_FREEZE_RULE_COIN_IS = "syn.freeze.rule.coin.is";
    //同步区块转入冻结规则阈值(锁仓0.9，活动金额0.1)
    public static final String SYN_FREEZE_RULE_THRESHOLD = "syn.freeze.rule.threshold";
    //扣积分规则 币种名称,多个逗号隔开
    public static final String SYN_DEDUCTION_INTEGRALS_COIN_NAME = "syn.deduction.integrals.coin.name";
    // 锁仓转账开关
    public static final String LOCK_WAREHOUSE_TRANSFER_SWITCH = "lock.warehouse.transfer.switch";
    // 锁仓转账手续费，比例
    public static final String LOCK_WAREHOUSE_TRANSFER_FEE_RATIO = "lock.warehouse.transfer.fee.ratio";
    // 锁仓转账手续费选择 ，0 固定，1 比例
    public static final String LOCK_WAREHOUSE_TRANSFER_TRANSFERFEESELECT = "lock.warehouse.transfer.transferFeeSelect";
    // 锁仓转账手续费固定大小
    public static final String LOCK_WAREHOUSE_TRANSFER_FEE_FIXED = "lock.warehouse.transfer.fee.fixed";

    public static final String TASK_NAME_SMART_CONTRACTST = "SmartContractstTask";

    //注册赠送积分给自己的系数
    public static final String REGISTER_REWARD_INTEGRATION_A = "register.reward.integration.a";
    //注册赠送积分给上1级的系数
    public static final String REGISTER_REWARD_INTEGRATION_B = "register.reward.integration.b";
    //注册赠送积分给上2-3级的系数
    public static final String REGISTER_REWARD_INTEGRATION_C = "register.reward.integration.c";
    //注册赠送积分给上4-10级的系数
    public static final String REGISTER_REWARD_INTEGRATION_D = "register.reward.integration.d";
    //投资赠送积分给自己的系数
    public static final String INVEST_REWARD_INTEGRATION_A = "invest.reward.integration.a";
    //投资赠送积分给上1级的系数
    public static final String INVEST_REWARD_INTEGRATION_B = "invest.reward.integration.b";
    //投资赠送积分给上2-3级的系数
    public static final String INVEST_REWARD_INTEGRATION_C = "invest.reward.integration.c";
    //投资赠送积分给上4级及以上无限级的系数(产生的赠送积分往上级送完为止)
    public static final String INVEST_REWARD_INTEGRATION_D = "invest.reward.integration.d";

    //上级需要达到的积分可参与第1种情况分润
    public static final String DIVIDED_NEED_INTEGRATION1 = "divided.need.integration1";
    //上级需要达到的积分可参与第2种情况分润
    public static final String DIVIDED_NEED_INTEGRATION2 = "divided.need.integration2";
    //上级需要达到的积分可参与第3种情况分润
    public static final String DIVIDED_NEED_INTEGRATION3 = "divided.need.integration3";
    //上级需要达到的积分可参与第4种情况分润
    public static final String DIVIDED_NEED_INTEGRATION4 = "divided.need.integration4";
    //上级需要达到的积分可参与第5种情况分润
    public static final String DIVIDED_NEED_INTEGRATION5 = "divided.need.integration5";
    //上级需要达到的积分可参与第6种情况分润
    public static final String DIVIDED_NEED_INTEGRATION6 = "divided.need.integration6";
    //上级需要达到的积分可参与第7种情况分润
    public static final String DIVIDED_NEED_INTEGRATION7 = "divided.need.integration7";
    //上级需要达到的积分可参与第8种情况分润
    public static final String DIVIDED_NEED_INTEGRATION8 = "divided.need.integration8";
    //上级需要达到的积分可参与第9种情况分润
    public static final String DIVIDED_NEED_INTEGRATION9 = "divided.need.integration9";
    //上级需要达到的积分可参与第10种情况分润
    public static final String DIVIDED_NEED_INTEGRATION10 = "divided.need.integration10";
    //上级达到第1种情况分润的分润系数
    public static final String DIVIDED_NEED_INTEGRATION_LEVEL1 = "divided.need.integration.level1";
    //上级达到第2种情况分润的分润系数
    public static final String DIVIDED_NEED_INTEGRATION_LEVEL2 = "divided.need.integration.level2";
    //上级达到第3种情况分润的分润系数
    public static final String DIVIDED_NEED_INTEGRATION_LEVEL3 = "divided.need.integration.level3";
    //上级达到第4种情况分润的分润系数
    public static final String DIVIDED_NEED_INTEGRATION_LEVEL4 = "divided.need.integration.level4";
    //上级达到第5种情况分润的分润系数
    public static final String DIVIDED_NEED_INTEGRATION_LEVEL5 = "divided.need.integration.level5";
    //上级达到第6种情况分润的分润系数
    public static final String DIVIDED_NEED_INTEGRATION_LEVEL6 = "divided.need.integration.level6";
    //上级达到第7种情况分润的分润系数
    public static final String DIVIDED_NEED_INTEGRATION_LEVEL7 = "divided.need.integration.level7";
    //上级达到第8种情况分润的分润系数
    public static final String DIVIDED_NEED_INTEGRATION_LEVEL8 = "divided.need.integration.level8";
    //上级达到第9种情况分润的分润系数
    public static final String DIVIDED_NEED_INTEGRATION_LEVEL9 = "divided.need.integration.level9";
    //上级达到第10种情况分润的分润系数
    public static final String DIVIDED_NEED_INTEGRATION_LEVEL10 = "divided.need.integration.level10";

    //上1级推荐人分润系数
    public static final String REFERRER_DIVIDED_LEVEL1 = "referrer.divided.level1";
    //上2级推荐人分润系数
    public static final String REFERRER_DIVIDED_LEVEL2 = "referrer.divided.level2";
    //上3级推荐人分润系数
    public static final String REFERRER_DIVIDED_LEVEL3 = "referrer.divided.level3";
    //上4级推荐人分润系数
    public static final String REFERRER_DIVIDED_LEVEL4 = "referrer.divided.level4";
    //上5级推荐人分润系数
    public static final String REFERRER_DIVIDED_LEVEL5 = "referrer.divided.level5";
    //上6级推荐人分润系数
    public static final String REFERRER_DIVIDED_LEVEL6 = "referrer.divided.level6";
    //上7级推荐人分润系数
    public static final String REFERRER_DIVIDED_LEVEL7 = "referrer.divided.level7";
    //上8级推荐人分润系数
    public static final String REFERRER_DIVIDED_LEVEL8 = "referrer.divided.level8";
    //上9级推荐人分润系数
    public static final String REFERRER_DIVIDED_LEVEL9 = "referrer.divided.level9";
    //上10级推荐人分润系数
    public static final String REFERRER_DIVIDED_LEVEL10 = "referrer.divided.level10";

    //下1级参与分润的积分要求
    public static final String NEED_INTEGRATION_CHILD1 = "need.integration.child1";
    //下2级参与分润的积分要求
    public static final String NEED_INTEGRATION_CHILD2 = "need.integration.child2";
    //下3级参与分润的积分要求
    public static final String NEED_INTEGRATION_CHILD3 = "need.integration.child3";
    //下4级参与分润的积分要求
    public static final String NEED_INTEGRATION_CHILD4 = "need.integration.child4";
    //下5级参与分润的积分要求
    public static final String NEED_INTEGRATION_CHILD5 = "need.integration.child5";
    //下6级参与分润的积分要求
    public static final String NEED_INTEGRATION_CHILD6 = "need.integration.child6";
    //下7级参与分润的积分要求
    public static final String NEED_INTEGRATION_CHILD7 = "need.integration.child7";
    //下8级参与分润的积分要求
    public static final String NEED_INTEGRATION_CHILD8 = "need.integration.child8";
    //下9级参与分润的积分要求
    public static final String NEED_INTEGRATION_CHILD9 = "need.integration.child9";
    //下10级参与分润的积分要求
    public static final String NEED_INTEGRATION_CHILD10 = "need.integration.child10";



}
