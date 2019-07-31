package com.cmd.exchange.admin.controller;

import com.cmd.exchange.admin.vo.ConfigVO;
import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.JSONUtil;
import com.cmd.exchange.common.vo.UserRewardVO;
import com.cmd.exchange.service.ConfigService;
import com.cmd.exchange.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "全局配置接口")
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Autowired
    private ConfigService configService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "配置全局参数")
    @PostMapping("system")
    public CommonResponse<String> updateSystemConfig(
            @Valid @RequestBody ConfigVO values) {

        Map<String, String> map = new HashMap<>();
        map.put(ConfigKey.REGISTER_REWARD, values.getRegisterReward() + "");
        map.put(ConfigKey.REFERRER_REWARD, values.getReferrerReward() + "");
        //map.put(ConfigKey.BCN_PRICE, values.getBcnPrice().toPlainString());
        //map.put(ConfigKey.TRADE_MINING_REC_REWARD, values.getTradeReward().toPlainString());

        /*List<UserRewardVO> userReward = new ArrayList<>();
        userReward.add(new UserRewardVO(values.getFirstRewardPhone(), values.getFirstRewardAmount()));
        userReward.add(new UserRewardVO(values.getSecondRewardPhone(), values.getSecondRewardAmount()));
        userReward.add(new UserRewardVO(values.getThirdRewardPhone(), values.getThirdRewardAmount()));

        map.put(ConfigKey.REFERRER_REWARD_RANKING, JSONUtil.toJSONString(userReward));*/

        //map.put(ConfigKey.OFFICIAL_WX_LINK, values.getWxImageLink());
        //map.put(ConfigKey.OFFICAL_QQ_LINK, values.getQqLink());

        Assert.check(values.getRecUserFeeRate().compareTo(BigDecimal.ONE) >= 0 || values.getRecUserFeeRate().compareTo(BigDecimal.ZERO) < 0,
                500, "返佣比例必须大于等于0小于1");
        map.put(ConfigKey.TRADE_FEE_REC_USER, values.getRecUserFeeRate().toString());
        Assert.check(values.getRec2UserFeeRate().compareTo(BigDecimal.ONE) >= 0 || values.getRec2UserFeeRate().compareTo(BigDecimal.ZERO) < 0,
                500, "返佣比例必须大于等于0小于1");
        map.put(ConfigKey.TRADE_FEE_REC_REC_USER, values.getRec2UserFeeRate().toString());
        /*Assert.check(values.getReceivedReleaseRate().compareTo(BigDecimal.ZERO) < 0,
                500, "转入锁仓币种买入时释放比例大于等于0");
        map.put(ConfigKey.TRADE_RECEIVED_RELEASE_RATE, values.getReceivedReleaseRate().toString());
        Assert.check(values.getVipReceivedReleaseRate().compareTo(BigDecimal.ZERO) < 0,
                500, "VIP转入锁仓币种买入时释放比例大于等于0");
        map.put(ConfigKey.TRADE_RECEIVED_RELEASE_RATE_VIP, values.getVipReceivedReleaseRate().toString());*/
        Assert.check(values.getSuperUserGetFeeRate().compareTo(BigDecimal.ONE) >= 0 || values.getSuperUserGetFeeRate().compareTo(BigDecimal.ZERO) < 0,
                500, "返佣比例必须大于等于0小于1");
        map.put(ConfigKey.TRADE_FEE_SUPER_USER_RATE, values.getSuperUserGetFeeRate().toString());
        Assert.check(values.getCoinSavingUserGetFeeRate().compareTo(BigDecimal.ONE) >= 0 || values.getCoinSavingUserGetFeeRate().compareTo(BigDecimal.ZERO) < 0,
                500, "返佣比例必须大于等于0小于1");
        map.put(ConfigKey.TRADE_FEE_SAVING_RATE, values.getCoinSavingUserGetFeeRate().toString());
        Assert.check(values.getSameTradeRewardMaxTimes() < 0, 500, "次数必须大于等于0");
        map.put(ConfigKey.TRADE_SAME_REWARD_MAX_TIMES, values.getSameTradeRewardMaxTimes().toString());
        Assert.check(values.getLockWarehouseTransferFeeFixed() < 0, 500, "次数必须大于等于0");
        map.put(ConfigKey.LOCK_WAREHOUSE_TRANSFER_FEE_FIXED, values.getLockWarehouseTransferFeeFixed().toString());

        /**
         * 用户组兑换比例设置
         */


        if (values.getEthToPlatNum() != null) {
            map.put(ConfigKey.ETH_TO_PLAT_NUM, values.getEthToPlatNum().toString());
        }
        if (values.getUsdtToPlatNum() != null) {
            map.put(ConfigKey.USDT_TO_PLAT_NUM, values.getUsdtToPlatNum().toString());
        }

        Assert.check(values.getRegisterRewardIntegrationA().compareTo(BigDecimal.ONE) > 0
                        || values.getRegisterRewardIntegrationA().compareTo(BigDecimal.ZERO) < 0,
                500, "注册赠送积分系数A必须大于等于0小于等于1");
        map.put(ConfigKey.REGISTER_REWARD_INTEGRATION_A, values.getRegisterRewardIntegrationA().toString());
        Assert.check(values.getRegisterRewardIntegrationB().compareTo(BigDecimal.ONE) > 0
                        || values.getRegisterRewardIntegrationB().compareTo(BigDecimal.ZERO) < 0,
                500, "注册赠送积分系数B必须大于等于0小于等于1");
        map.put(ConfigKey.REGISTER_REWARD_INTEGRATION_B, values.getRegisterRewardIntegrationB().toString());
        Assert.check(values.getRegisterRewardIntegrationC().compareTo(BigDecimal.ONE) > 0
                        || values.getRegisterRewardIntegrationC().compareTo(BigDecimal.ZERO) < 0,
                500, "注册赠送积分系数C必须大于等于0小于等于1");
        map.put(ConfigKey.REGISTER_REWARD_INTEGRATION_C, values.getRegisterRewardIntegrationC().toString());
        Assert.check(values.getRegisterRewardIntegrationD().compareTo(BigDecimal.ONE) > 0
                        || values.getRegisterRewardIntegrationD().compareTo(BigDecimal.ZERO) < 0,
                500, "注册赠送积分系数D必须大于等于0小于等于1");
        map.put(ConfigKey.REGISTER_REWARD_INTEGRATION_D, values.getRegisterRewardIntegrationD().toString());
        Assert.check(values.getInvestRewardIntegrationA().compareTo(BigDecimal.ONE) > 0
                        || values.getInvestRewardIntegrationA().compareTo(BigDecimal.ZERO) < 0,
                500, "投资赠送积分系数A必须大于等于0小于等于1");
        map.put(ConfigKey.INVEST_REWARD_INTEGRATION_A, values.getInvestRewardIntegrationA().toString());
        Assert.check(values.getInvestRewardIntegrationB().compareTo(BigDecimal.ONE) > 0
                        || values.getInvestRewardIntegrationB().compareTo(BigDecimal.ZERO) < 0,
                500, "投资赠送积分系数B必须大于等于0小于等于1");
        map.put(ConfigKey.INVEST_REWARD_INTEGRATION_B, values.getInvestRewardIntegrationB().toString());
        Assert.check(values.getInvestRewardIntegrationC().compareTo(BigDecimal.ONE) > 0
                        || values.getInvestRewardIntegrationC().compareTo(BigDecimal.ZERO) < 0,
                500, "投资赠送积分系数C必须大于等于0小于等于1");
        map.put(ConfigKey.INVEST_REWARD_INTEGRATION_C, values.getInvestRewardIntegrationC().toString());
        Assert.check(values.getInvestRewardIntegrationD().compareTo(BigDecimal.ONE) > 0
                        || values.getInvestRewardIntegrationD().compareTo(BigDecimal.ZERO) < 0,
                500, "投资赠送积分系数D必须大于等于0小于等于1");
        map.put(ConfigKey.INVEST_REWARD_INTEGRATION_D, values.getInvestRewardIntegrationD().toString());

        Assert.check(values.getReferrerLevel1().compareTo(BigDecimal.ONE) > 0
                        || values.getReferrerLevel1().compareTo(BigDecimal.ZERO) < 0,
                500, "上1级推荐人分润系数1必须大于等于0小于等于1");
        map.put(ConfigKey.REFERRER_DIVIDED_LEVEL1, values.getReferrerLevel1().toString());
        Assert.check(values.getReferrerLevel2().compareTo(BigDecimal.ONE) > 0
                        || values.getReferrerLevel2().compareTo(BigDecimal.ZERO) < 0,
                500, "上1级推荐人分润系数1必须大于等于0小于等于1");
        map.put(ConfigKey.REFERRER_DIVIDED_LEVEL2, values.getReferrerLevel2().toString());
        Assert.check(values.getReferrerLevel3().compareTo(BigDecimal.ONE) > 0
                        || values.getReferrerLevel3().compareTo(BigDecimal.ZERO) < 0,
                500, "上1级推荐人分润系数1必须大于等于0小于等于1");
        map.put(ConfigKey.REFERRER_DIVIDED_LEVEL3, values.getReferrerLevel3().toString());
        Assert.check(values.getReferrerLevel4().compareTo(BigDecimal.ONE) > 0
                        || values.getReferrerLevel4().compareTo(BigDecimal.ZERO) < 0,
                500, "上1级推荐人分润系数1必须大于等于0小于等于1");
        map.put(ConfigKey.REFERRER_DIVIDED_LEVEL4, values.getReferrerLevel4().toString());
        Assert.check(values.getReferrerLevel5().compareTo(BigDecimal.ONE) > 0
                        || values.getReferrerLevel5().compareTo(BigDecimal.ZERO) < 0,
                500, "上1级推荐人分润系数1必须大于等于0小于等于1");
        map.put(ConfigKey.REFERRER_DIVIDED_LEVEL5, values.getReferrerLevel5().toString());
        Assert.check(values.getReferrerLevel6().compareTo(BigDecimal.ONE) > 0
                        || values.getReferrerLevel6().compareTo(BigDecimal.ZERO) < 0,
                500, "上1级推荐人分润系数1必须大于等于0小于等于1");
        map.put(ConfigKey.REFERRER_DIVIDED_LEVEL6, values.getReferrerLevel6().toString());
        Assert.check(values.getReferrerLevel7().compareTo(BigDecimal.ONE) > 0
                        || values.getReferrerLevel7().compareTo(BigDecimal.ZERO) < 0,
                500, "上1级推荐人分润系数1必须大于等于0小于等于1");
        map.put(ConfigKey.REFERRER_DIVIDED_LEVEL7, values.getReferrerLevel7().toString());
        Assert.check(values.getReferrerLevel8().compareTo(BigDecimal.ONE) > 0
                        || values.getReferrerLevel8().compareTo(BigDecimal.ZERO) < 0,
                500, "上1级推荐人分润系数1必须大于等于0小于等于1");
        map.put(ConfigKey.REFERRER_DIVIDED_LEVEL8, values.getReferrerLevel8().toString());
        Assert.check(values.getReferrerLevel9().compareTo(BigDecimal.ONE) > 0
                        || values.getReferrerLevel9().compareTo(BigDecimal.ZERO) < 0,
                500, "上1级推荐人分润系数1必须大于等于0小于等于1");
        map.put(ConfigKey.REFERRER_DIVIDED_LEVEL9, values.getReferrerLevel9().toString());
        Assert.check(values.getReferrerLevel10().compareTo(BigDecimal.ONE) > 0
                        || values.getReferrerLevel10().compareTo(BigDecimal.ZERO) < 0,
                500, "上1级推荐人分润系数1必须大于等于0小于等于1");
        map.put(ConfigKey.REFERRER_DIVIDED_LEVEL10, values.getReferrerLevel10().toString());

        configService.updateConfigValue(map);
        return new CommonResponse<>();
    }

    @ApiOperation(value = "获取全局参数")
    @GetMapping("system")
    public CommonResponse<ConfigVO> getSystemConfig() {

        ConfigVO values = new ConfigVO();
        values.setBcnPrice(new BigDecimal(configService.getConfigValue(ConfigKey.BCN_PRICE)));
        values.setReferrerReward(Integer.valueOf(configService.getConfigValue(ConfigKey.REFERRER_REWARD)));
        values.setRegisterReward(Integer.valueOf(configService.getConfigValue(ConfigKey.REGISTER_REWARD)));
        //values.setTradeReward(new BigDecimal(configService.getConfigValue(ConfigKey.TRADE_MINING_REC_REWARD)));
        values.setEthToPlatNum(getBigDecimalValue(ConfigKey.ETH_TO_PLAT_NUM));
        values.setUsdtToPlatNum(getBigDecimalValue(ConfigKey.USDT_TO_PLAT_NUM));

        values.setRecUserFeeRate(configService.getConfigValue(ConfigKey.TRADE_FEE_REC_USER, BigDecimal.ZERO, true));
        values.setRec2UserFeeRate(configService.getConfigValue(ConfigKey.TRADE_FEE_REC_REC_USER, BigDecimal.ZERO, true));
        //values.setReceivedReleaseRate(configService.getConfigValue(ConfigKey.TRADE_RECEIVED_RELEASE_RATE, BigDecimal.ZERO, true));
        //values.setVipReceivedReleaseRate(configService.getConfigValue(ConfigKey.TRADE_RECEIVED_RELEASE_RATE_VIP, BigDecimal.ZERO, true));
        values.setSuperUserGetFeeRate(configService.getConfigValue(ConfigKey.TRADE_FEE_SUPER_USER_RATE, BigDecimal.ZERO, true));
        values.setCoinSavingUserGetFeeRate(configService.getConfigValue(ConfigKey.TRADE_FEE_SAVING_RATE, BigDecimal.ZERO, true));
        values.setSameTradeRewardMaxTimes(configService.getConfigValue(ConfigKey.TRADE_SAME_REWARD_MAX_TIMES, 5));

        /*List<UserRewardVO> rewardList = userService.getUserRewardList();
        values.setFirstRewardPhone(rewardList.get(0).getPhone());
        values.setFirstRewardAmount(rewardList.get(0).getReward());
        values.setSecondRewardPhone(rewardList.get(1).getPhone());
        values.setSecondRewardAmount(rewardList.get(1).getReward());
        values.setThirdRewardPhone(rewardList.get(2).getPhone());
        values.setThirdRewardAmount(rewardList.get(2).getReward());*/

        values.setWxImageLink(configService.getOfficialWxImageLink());
        values.setQqLink(configService.getOfficalQQLink());
        values.setLockWarehouseTransferFeeFixed(configService.getConfigValue(ConfigKey.LOCK_WAREHOUSE_TRANSFER_FEE_FIXED, 5));

        values.setRegisterRewardIntegrationA(getBigDecimalValue(ConfigKey.REGISTER_REWARD_INTEGRATION_A));
        values.setRegisterRewardIntegrationB(getBigDecimalValue(ConfigKey.REGISTER_REWARD_INTEGRATION_B));
        values.setRegisterRewardIntegrationC(getBigDecimalValue(ConfigKey.REGISTER_REWARD_INTEGRATION_C));
        values.setRegisterRewardIntegrationD(getBigDecimalValue(ConfigKey.REGISTER_REWARD_INTEGRATION_D));
        values.setInvestRewardIntegrationA(getBigDecimalValue(ConfigKey.INVEST_REWARD_INTEGRATION_A));
        values.setInvestRewardIntegrationB(getBigDecimalValue(ConfigKey.INVEST_REWARD_INTEGRATION_B));
        values.setInvestRewardIntegrationC(getBigDecimalValue(ConfigKey.INVEST_REWARD_INTEGRATION_C));
        values.setInvestRewardIntegrationD(getBigDecimalValue(ConfigKey.INVEST_REWARD_INTEGRATION_D));

        return new CommonResponse<>(values);
    }

    private BigDecimal getBigDecimalValue(String key) {
        String value = configService.getConfigValue(key);
        if (value != null) {
            return new BigDecimal(value);
        }
        return null;
    }
}
