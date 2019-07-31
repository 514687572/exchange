package com.cmd.exchange.service;

import com.cmd.exchange.common.mapper.CoinConfigMapper;
import com.cmd.exchange.common.mapper.CoinGroupConfigMapper;
import com.cmd.exchange.common.mapper.CoinMapper;
import com.cmd.exchange.common.mapper.UserGroupConfigMapper;
import com.cmd.exchange.common.model.Coin;
import com.cmd.exchange.common.model.CoinGroupConfig;
import com.cmd.exchange.common.model.UserGroupConfig;
import com.cmd.exchange.common.vo.CoinGroupConfigDetailVO;
import com.cmd.exchange.common.vo.CoinGroupConfigVO;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CoinGroupConfigService {

    @Autowired
    private CoinGroupConfigMapper coinGroupConfigMapper;
    @Autowired
    private UserGroupConfigMapper userGroupConfigMapper;

    /**
     * 获取分组对应币币匹配的详细列表
     *
     * @param groupId
     * @return
     */
    public List<CoinGroupConfigVO> getCionGroupConfigList(Integer groupId) {
        List<CoinGroupConfigVO> list = new ArrayList<CoinGroupConfigVO>();
        List<CoinGroupConfig> coinGroupConfigList = coinGroupConfigMapper.getCionGroupConfigList(groupId);

        if (coinGroupConfigList != null && coinGroupConfigList.size() > 0) {
            List<UserGroupConfig> userGroupList = userGroupConfigMapper.getUserGroupConfigList();
            Map<Integer, UserGroupConfig> userGroupConfigMap = new HashMap<>();
            //构造分组基本信息
            for (UserGroupConfig userGroupConfig : userGroupList) {
                userGroupConfigMap.put(userGroupConfig.getId(), userGroupConfig);
            }

            Map<Integer, List<CoinGroupConfig>> coinGroupConfigMap = new HashMap<Integer, List<CoinGroupConfig>>();

            for (CoinGroupConfig data : coinGroupConfigList) {
                if (coinGroupConfigMap.containsKey(data.getGroupId())) {
                    List<CoinGroupConfig> coinGroupConfigs = coinGroupConfigMap.get(data.getGroupId());
                    coinGroupConfigs.add(data);
                    coinGroupConfigMap.put(data.getGroupId(), coinGroupConfigs);
                } else {
                    List<CoinGroupConfig> coinGroupConfigs = new ArrayList<CoinGroupConfig>();
                    coinGroupConfigs.add(data);
                    coinGroupConfigMap.put(data.getGroupId(), coinGroupConfigs);
                }
            }

            //构造页面所需的数据结构数据
            if (coinGroupConfigMap.size() > 0 && userGroupConfigMap.size() > 0) {
                for (Integer groupConfigId : userGroupConfigMap.keySet()) {


                    //List<CoinGroupConfigDetailVO> coinGroupConfigDetailVOS = new ArrayList<CoinGroupConfigDetailVO>();

                    List<CoinGroupConfig> coinGroupConfigs = coinGroupConfigMap.get(groupConfigId);
                    if (coinGroupConfigs != null && coinGroupConfigs.size() > 0) {
                        for (CoinGroupConfig coinGroupConfig : coinGroupConfigs) {

                            CoinGroupConfigVO coinGroupConfigVO = new CoinGroupConfigVO();
                            UserGroupConfig userGroupConfig = userGroupConfigMap.get(groupConfigId);
                            coinGroupConfigVO.setGroupId(groupConfigId);
                            coinGroupConfigVO.setGroupName(userGroupConfig.getGroupName());
                            coinGroupConfigVO.setGroupType(userGroupConfig.getGroupType());
                            //CoinGroupConfigDetailVO coinGroupConfigDetailVO = new CoinGroupConfigDetailVO();

                            coinGroupConfigVO.setId(coinGroupConfig.getId());
                            coinGroupConfigVO.setConValue(coinGroupConfig.getConValue());
                            coinGroupConfigVO.setRemark(coinGroupConfig.getRemark());


                            coinGroupConfigVO.setCoinName(coinGroupConfig.getCoinName());
                            list.add(coinGroupConfigVO);
                        }
                    }
                    // coinGroupConfigVO.setCoinGroupConfigDetailVOList(coinGroupConfigDetailVOS);

                    // list.add(coinGroupConfigVO);

                }
            }

        }
        return list;
    }

    //增加币种分组配置
    @Transactional
    public int addCoinGroupConfig(CoinGroupConfig coinGroupConfig) {
        return coinGroupConfigMapper.addCoinGroupConfig(coinGroupConfig);
    }


    //修改币种分组配置
    @Transactional
    public void updateCoinGroupConfig(List<CoinGroupConfig> coinGroupConfigs) {

        for (CoinGroupConfig coinGroupConfig : coinGroupConfigs) {
            coinGroupConfigMapper.updateCoinGroupConfig(coinGroupConfig);
        }
    }

    //获取分组配置详情
    @Transactional
    public CoinGroupConfigVO getCoinGroupConfigDetailById(Integer id) {
        CoinGroupConfig coinGroupConfig = coinGroupConfigMapper.getCoinGroupConfigById(id);


        UserGroupConfig userGroupConfig = userGroupConfigMapper.getUserGroupConfigById(coinGroupConfig.getGroupId());
        CoinGroupConfigVO coinGroupConfigVO = new CoinGroupConfigVO();
        coinGroupConfigVO.setGroupId(coinGroupConfig.getGroupId());

        coinGroupConfigVO.setGroupType(userGroupConfig.getGroupType());
        coinGroupConfigVO.setGroupName(userGroupConfig.getGroupName());

        coinGroupConfigVO.setId(coinGroupConfig.getId());

        coinGroupConfigVO.setCoinName(coinGroupConfig.getCoinName());
        coinGroupConfigVO.setRemark(coinGroupConfig.getRemark());

        coinGroupConfigVO.setConValue(coinGroupConfig.getConValue());

        return coinGroupConfigVO;
    }
}
