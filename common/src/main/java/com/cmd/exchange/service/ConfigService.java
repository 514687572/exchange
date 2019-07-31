package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.mapper.ConfigMapper;
import com.cmd.exchange.common.model.Config;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service
public class ConfigService {
    private static Log log = LogFactory.getLog(ConfigService.class);
    @Autowired
    private ConfigMapper configMapper;

    private String platformCoinName;

    /**
     * 获取配置值，如果值不存在，返回默认值
     *
     * @param name         属性名
     * @param defaultValue 默认值
     * @return 配置值/默认值
     */
    public String getConfigValue(String name, String defaultValue) {
        Config conf = configMapper.getConfigByName(name);
        if (conf == null) {
            return defaultValue;
        }
        return conf.getConfValue();
    }

    /**
     * 获取配置值，如果值不存在，返回默认值
     *
     * @param name         属性名
     * @param defaultValue 默认值
     * @param isPercent    是否是百分比，如果是百分比，那么小于1的时候，会除以100返回
     * @return 配置值/默认值
     */
    public BigDecimal getConfigValue(String name, BigDecimal defaultValue, boolean isPercent) {
        BigDecimal value;
        Config conf = configMapper.getConfigByName(name);
        if (conf == null) {
            value = defaultValue;
        } else {
            value = new BigDecimal(conf.getConfValue());
        }
        if (isPercent) {
            if (value.compareTo(BigDecimal.ONE) >= 0) {
                value = value.divide(new BigDecimal(100), 8, RoundingMode.HALF_UP);
            }
        }
        return value;
    }

    public int getConfigValue(String name, int defaultValue) {
        Config conf = configMapper.getConfigByName(name);

        if (conf == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(conf.getConfValue());
        } catch (NumberFormatException ex) {
            log.error("", ex);
            return defaultValue;
        }
    }

    public BigInteger getConfigValue(String name, BigInteger defaultValue) {
        Config conf = configMapper.getConfigByName(name);
        if (conf == null) {
            return defaultValue;
        }
        try {
            return new BigInteger(conf.getConfValue());
        } catch (NumberFormatException ex) {
            log.error("", ex);
            return defaultValue;
        }
    }

    /**
     * 获取配置值，如果值不存在，返回null
     *
     * @param name 属性名
     * @return 配置值/null
     */
    public String getConfigValue(String name) {
        return getConfigValue(name, (String) null);
    }

    public void setConfigValue(String name, String value) {
        int row = configMapper.updateConfigValue(name, value);
        if (row == 0) {
            Config conf = new Config();
            conf.setConfName(name);
            conf.setConfValue(value);
            conf.setComment("");
            configMapper.insertConfig(conf);
        }
    }

    @Transactional
    public void updateConfigValue(Map<String, String> values) {
        for (String key : values.keySet()) {
            setConfigValue(key, values.get(key));
        }
    }

    public List<Config> getConfigList() {
        return configMapper.getConfigList();
    }

    //////////////////////////////////////////////////////////////////////////////////
    //注册奖励
    public Integer getUserRegisterReward() {
        return getConfigValue(ConfigKey.REGISTER_REWARD, 500);
    }

    //推荐奖励
    public Integer getUserReferrerReward() {
        return getConfigValue(ConfigKey.REFERRER_REWARD, 200);
    }

    //推荐链接
    public String getUserReferrerUrl() {
        return getConfigValue(ConfigKey.REFERRER_URL);
    }

    //平台币名称
    public String getPlatformCoinName() {
        if (platformCoinName == null) {
            platformCoinName = getConfigValue(ConfigKey.PLATFORM_COIN_NAME);
        }
        return platformCoinName;
    }

    //基本市场币种名称
    public String getMarketBaseCoinName() {
        return getConfigValue(ConfigKey.MARKET_BASE_COIN_NAME);
    }

    public String getUserRewardRanking() {
        return getConfigValue(ConfigKey.REFERRER_REWARD_RANKING);
    }

    public void updateUserRewardRanking(String userReward) {
        setConfigValue(ConfigKey.REFERRER_REWARD_RANKING, userReward);
    }

    public BigDecimal getPlatformCoinCnyPrice() {
        String price = getConfigValue(ConfigKey.BCN_PRICE);
        if (StringUtils.isNotBlank(price)) {
            return new BigDecimal(price);
        }

        return null;
    }

    public String getOfficialWxImageLink() {
        return getConfigValue(ConfigKey.OFFICIAL_WX_LINK);
    }

    public String getOfficalQQLink() {
        return getConfigValue(ConfigKey.OFFICAL_QQ_LINK);
    }
}
