package com.cmd.exchange.common.constants;

/**
 * 错误码定义，使用8位数字表示，前面3个数字表示模块，后面5个数字表示模块内错误
 */
public class ErrorCode {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 系统和通用模块定义，模块号是0
    // 成功操作
    public static final int ERR_SUCCESS = 0;
    public static final int ERR_PARAM_ERROR = 10;        //参数错误
    public static final int ERR_RECORD_NOT_EXIST = 11;    //记录不存在
    public static final int ERR_RECORD_EXIST = 12;        //记录已经存在
    public static final int ERR_RECORD_UPDATE = 13;     //更新记录失败
    public static final int ERR_DB_CONFIG_NOT_EXIST = 14; //数据库配置记录不存在
    public static final int ERR_RECORD_DATA_ERROR = 15;        //记录数据错误
    public static final int ERR_USDT_CLIENT_RPC_ERROR = 16;   //USDT客户端请求错误
    public static final int ERR_ETH_CLIENT_RPC_ERROR = 17;    //ETH客户端请求错误
    public static final int ERR_BTC_CLIENT_RPC_ERROR = 18;    //BTC客户端请求错误
    public static final int ERR_COIN_BASE_ERROR = 19;         //主账户地址错误
    public static final int ERR_NOT_SUPPORT_COIN = 20;        //不支持的币种
    public static final int ERR_WALLET_ERROR = 22;           //钱包调用失败
    public static final int ERR_CODE_NOT_SUPPORT = 23;    //不支持的验证码类型
    public static final int ERR_PAGE_SIZE_TOO_LARGE = 24;    //分页每页的数量太大
    public static final int ERR_INVALID_ADDRESS = 25;     //无效的地址
    public static final int ERR_INVALID_TXID = 26;        //无效的交易ID


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 普通用户模块定义，包括用户注册，登录，修改用户基本信息，认证信息等，模块号是1
    public static final int ERR_TOKEN_NOT_EXIST = 1_00001;       //token不存在(在别处登录)
    public static final int ERR_TOKEN_EXPIRE_TIME = 1_00002;     //token失效

    public static final int ERR_USER_DISABLE = 1_01001;          //用户禁用
    public static final int ERR_USER_MOBILE_ERROR = 1_01002;     //手机号码错误
    public static final int ERR_USER_PASSWORD_ERROR = 1_01003;   //用户密码错误
    public static final int ERR_USER_MOBILE_EXIST = 1_01004;     //手机号码已经存在
    public static final int ERR_USER_NOT_EXIST = 1_01005;        //用户不存在
    public static final int ERR_USER_SMSCODE_ERROR = 1_01006;    //短信验证码错误
    public static final int ERR_TRANSFER_FAIL = 1_01007;                  //转账失败
    public static final int ERR_TRANSFER_AMOUNT_TO_HIGH = 1_01008;        //转账数太大
    public static final int ERR_TRANSFER_AMOUNT_TO_LOW = 1_01009;         //转账数太小
    public static final int ERR_USER_ID_CARD_NOT_UPLOAD = 1_01010;    //尚未上传身份证件
    public static final int ERR_REFERRER_NOT_EXIST = 1_01011;         //推荐人不存在
    public static final int ERR_USER_NOT_CERTIFICATED = 1_01012;         //用户尚未实名
    public static final int ERR_USER_PAY_PASSWORD_NOT_FOUND = 1_01013;         //支付密码未设置
    public static final int ERR_USER_CAPTCHA_ERROR = 1_01014;           //图形验证码错误
    public static final int ERR_USER_EMAIL_EXIST = 1_01015;             //email已经存在
    public static final int ERR_USER_EMAIL_NOT_EXIST = 1_01016;         //email不存在
    public static final int ERR_GOOGLE_SECRET_ERROR = 1_01017;            //google验证码错误
    public static final int ERR_USER_CERTIFICATED = 1_01018;              //已经实名认证
    public static final int ERR_USER_ACCOUNT_ERROR = 1_01019;             //用户账户错误
    public static final int ERR_USER_IDCARD_ERROR = 1_01020;             //用户身份证号码已存在
    public static final int ERR_USER_NOT_BIND_BANK = 1_01021;                 //未绑定支付方式
    public static final int ERR_PUBLISH_OTC = 1_01022;                    //联系客户开通发布OTC权限


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 用户资产管理模块，包括查看用户钱包，充值提币等（不包括跟兑换商的交易），模块号10
    public static final int ERR_BALANCE_INSUFFICIENT = 10_00001;    //余额不足
    public static final int ERR_USER_COIN_NOT_EXIST = 10_00002;    //用户资产不存在
    public static final int ERR_COIN_ADDRESS_NOT_EXIST = 10_00003;    //提币地址不存在
    public static final int ERR_COIN_ADDRESS_IS_EXISTED = 10_00004;    //提币地址重复
    public static final int ERR_COIN_NAME_IS_EXISTED = 10_00005;    //用户币种不存在


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 交易市场模块，包括在交易市场下单买卖，查看交易市场行情等，模块号20
    public static final int ERR_MARKET_NOT_EXIST = 20_00001;    //市场不存在
    public static final int ERR_MARKET_CLOSED = 20_00002;    //市场已经关闭
    public static final int ERR_TRADE_PRICE_TOO_LOW = 20_00003;    //下单价格太低，超过市场限制
    public static final int ERR_TRADE_PRICE_TOO_HIGH = 20_00004;    //下单价格太高，超过市场限制
    public static final int ERR_TRADE_AMOUNT_TOO_LOW = 20_00005;    //下单数量太少，超过市场限制
    public static final int ERR_TRADE_AMOUNT_TOO_HIGH = 20_00006;    //下单数量太多，超过市场限制
    public static final int ERR_MARKET_LAST_DAY_PRICE_NOT_EXIST = 20_00007;    //昨天收盘价不存在
    public static final int ERR_TRADE_NOT_EXIST = 20_00008;    //交易记录不存在
    public static final int ERR_MARKET_ALREADY_EXIST = 20_00009;    //同名市场已经存在
    public static final int ERR_COIN_NOT_EXIST = 20_00010;    //交易币种不存在
    public static final int ERR_CURRENCY_NOT_EXIST = 20_00011;    //结算币种不存在
    public static final int ERR_PRICE_REQUIRED = 20_00012;    //限价订单的价格必须填写
    public static final int ERR_MARKET_TRADE_PRICE_NOT_FOUND = 20_00013;    //市价单的价格无法指定
    public static final int ERR_MARKET_TRADE_PRICE_LESS_THAN_ZERO = 20_00014;    //下单价格不能是负数
    public static final int ERR_TRADE_TOO_FREQUENT = 20_00015;    // 下单太频繁
    public static final int ERR_TRADE_DECIMAL_POINT_4 = 20_00018;     // 小数点后面最多支持4位小数
    public static final int ERR_TRADE_CURRENCY_TOO_HIGH = 20_00019;    // 挂单总额过高

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 兑换商模块，包括普通用户跟兑换商的交易等，模块号30
    public static final int ERR_SAVING_COUNT_INVALID = 30_00001;    //  存储或取出的钱数量不合法

    // 文章模块， 模块号 40
    public static final int ERR_ARTICLE_NOT_EXIST = 40_00001;    //文章不存在

    //第三方接口， 模块号码 50
    public static final int ERR_API_INVALID_KEY = 50_00001;    //api key无效
    public static final int ERR_API_KEY_DISABLED = 50_00002;    //api key禁用
    public static final int ERR_API_INVALID_SIGN = 50_00003;    //请求签名错误
    public static final int ERR_API_INVALID_TIMESTAMP = 50_00004;    //请求时间戳错误
    public static final int ERR_API_RATE_LIMIT = 50_00005;    //请求时间戳错误
    public static final int ERR_API_INVALID_IP = 50_00006;    //请求ip不在白名单中


    //C2C交易模块， 模块号 60
    public static final int ERR_C2C_ORDER_AMOUNT_TOO_LOW = 60_00001;            //下单数量太小
    public static final int ERR_C2C_ORDER_AMOUNT_TOO_HIGH = 60_00002;           //下单数量太多
    public static final int ERR_C2C_BUY_APPLICATIN_NOT_EXIST = 60_00003;        //买单申请不存在
    public static final int ERR_C2C_SELL_APPLICATION_NOT_EXIST = 60_00004;        //卖单申请不存在
    public static final int ERR_C2C_ORDER_NOT_EXIST = 60_00005;                    //该笔订单不存在
    public static final int ERR_C2C_APPLICATION_STATUS_ERROR = 60_00006;          //挂单状态错误(不能进行此操作)
    public static final int ERR_C2C_ORDER_STATUS_ERROR = 60_00007;                 //订单状态错误(不能进行此操作)
    public static final int ERR_C2C_APPLICATION_OWNER = 60_00008;                  //不能自己和自己交易
    public static final int ERR_C2C_ORDER_INVALID = 60_00009;                       //订单无效
    public static final int ERR_C2C_APPLICATION_INVALID = 60_00010;                //挂单无效
    public static final int ERR_C2C_UPDATE_FAILURE = 60_00011;                //更新失败
    public static final int ERR_C2C_MAX_APPLICATION_BUY_COUNT = 60_00012;     //超过最大挂买单数
    public static final int ERR_C2C_MAX_APPLICATION_SELL_COUNT = 60_00013;    //超过最大挂卖单数
    public static final int ERR_C2C_ORDER_MANAGER_DEAL = 60_00014;            //这个超时订单是需要后台处理了

    // 兑换平台币模块， 模块号 70
    public static final int ERR_CON_CAN_NOT_BUY_NOW = 70_00001;                 // 当前时间不能购买

    // C2B交易模块,模块号80
    public static final int ERR_MER_NOT_BALANCE = 80_00001;           //兑换商余额不足
    public static final int ERR_MER_STATUS_WRONG = 80_00002;           //订单状态不匹配
    public static final int ERR_MER_INVALID_USER = 80_00003;           //当前用户无权限操作
    public static final int ERR_MER_NO_ORDER = 80_00004;           //订单不存在
    public static final int ERR_MER_NO_MERCHANT = 80_00005;           //兑换商不存在或者不是兑换商
    public static final int ERR_MER_COIN_INVALID = 80_00006;           //币种不支持买卖
    public static final int ERR_MER_OC_EXISTS = 80_00007;           //申诉单已经存在，不能重复申诉
    public static final int ERR_MER_MER_EXISTS = 80_00008;           //兑换商已经存在，不能重复申请
    public static final int ERR_MER_ACCOUNT_WRONG = 80_00009;           //兑换商已经存在，不能重复申请
    public static final int ERR_MER_SET_CUR_INVALID = 80_00010;           //不支持使用该币种进行买卖

    //用户分组模块
    public static final int ERE_GROUP_STATUS_NOT_WARNING = 90_00001;//该分组已经使用，无法禁用

    public static final int ERE_GROUP_USING = 90_00002;//该分组已经使用，无法删除

    public static final int ERE_MARKET_GROUP_CONFIG_NOT_EXIST = 11_00001; //你还未配置过交易对分组配置

    public static final int ERE_APPLY_ILLEGEAL = 12_00001; //非法操作

    //外部用户转入金额记录
    public static final int ERR_USER_AMOUNT_TRANSFERRED_INTO = 100_00001;          //插入记录失败
    public static final int ERR_USER_REPETITIVE_OPERATION = 100_00002;          //请勿重复操作
    public static final int ERR_USER_COINNAME_RESTRICT = 100_00003;          //禁止用户扣该币种积分

    //---------------------------智能合约start-------------------------------------------------
    public static final int ERR_SMART_CONTRACTST_LIST = 101_00001;          //已售罄
    public static final int ERR_USER_CONTRACTST_BUY = 101_00002;          //同一币种每天只能购买一次




}
