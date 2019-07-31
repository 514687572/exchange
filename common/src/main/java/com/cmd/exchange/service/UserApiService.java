package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.enums.UserApiStatus;
import com.cmd.exchange.common.mapper.UserApiMapper;
import com.cmd.exchange.common.model.UserApi;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.CageUtil;
import com.cmd.exchange.common.utils.TokenGeneratorUtil;
import com.cmd.exchange.common.vo.ReferrerVO;
import com.cmd.exchange.common.vo.UserApiVO;
import com.cmd.exchange.common.vo.UserApiVOReqVO;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.abi.datatypes.Int;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserApiService {
    @Autowired
    UserApiMapper userApiMapper;

    public UserApi addApi(Integer userId, String whiteIpList, String comment) {
        UserApi userApi = new UserApi();
        if (StringUtils.isBlank(whiteIpList)) {
            userApi.setExpiredTime(DateUtils.addMonths(new Date(), 3));
        }
        userApi.setUserId(userId).setWhiteIpList(whiteIpList).setComment(comment);
        userApi.setStatus(UserApiStatus.PENDING);


        userApi.setApiKey(TokenGeneratorUtil.generateValue()).setApiSecret(UUID.randomUUID().toString());
        userApiMapper.addApiKey(userApi);
        return userApi;
    }

    public UserApiVO getUserApiByKey(String apiKey) {
        return userApiMapper.getUserApiByApiKey(apiKey);
    }

    public Page<UserApiVO> getUserApiList(UserApiVOReqVO reqVO) {
        if (reqVO.getStatus() != null && reqVO.getStatus() != "") {
            //0：禁用，1：审核通过 2: 审核不通过，3 审核中',
            //PASS:通过 DENY：审核失败 PENDING：审核中 DISABLED：禁用
            if (reqVO.getStatus().equals("PASS")) {
                reqVO.setStatus("1");
            } else if (reqVO.getStatus().equals("DENY")) {
                reqVO.setStatus("2");
            } else if (reqVO.getStatus().equals("PENDING")) {
                reqVO.setStatus("3");
            } else if (reqVO.getStatus().equals("DISABLED")) {
                reqVO.setStatus("0");
            }
        }
        return userApiMapper.getUserApiList(reqVO, new RowBounds(reqVO.getPageNo(), reqVO.getPageSize()));
    }

    public int updateUserApiStatus(List<Integer> ids, UserApiStatus status) {
        return userApiMapper.updateUserApiStatus(ids, status.getValue());
    }

    public void deleteApi(Integer userId, Integer apiId) {
        UserApiVO api = userApiMapper.getApiById(apiId);
        Assert.check(api == null, ErrorCode.ERR_RECORD_NOT_EXIST);
        Assert.check(!api.getUserId().equals(userId), ErrorCode.ERR_RECORD_NOT_EXIST);

        int rows = userApiMapper.deleteApiById(apiId);
    }

    public void refreshApi(Integer userId, Integer apiId) {
        UserApiVO api = userApiMapper.getApiById(apiId);
        Assert.check(api == null, ErrorCode.ERR_RECORD_NOT_EXIST);
        Assert.check(!api.getUserId().equals(userId), ErrorCode.ERR_RECORD_NOT_EXIST);

        UserApi newApi = new UserApi();
        newApi.setId(apiId);
        newApi.setApiKey(TokenGeneratorUtil.generateValue()).setApiSecret(UUID.randomUUID().toString());

        if (StringUtils.isBlank(api.getWhiteIpList())) {
            newApi.setExpiredTime(DateUtils.addMonths(new Date(), 3));
        }

        int rows = userApiMapper.updateUserApiById(newApi);
        Assert.check(rows == 0, ErrorCode.ERR_RECORD_UPDATE);
    }

    public void updateApiWhiteIpList(Integer userId, Integer apiId, String whiteIpList, String comment) {
        UserApiVO api = userApiMapper.getApiById(apiId);
        Assert.check(api == null, ErrorCode.ERR_RECORD_NOT_EXIST);
        Assert.check(!api.getUserId().equals(userId), ErrorCode.ERR_RECORD_NOT_EXIST);

        UserApi newApi = new UserApi();
        newApi.setId(apiId);
        newApi.setWhiteIpList(whiteIpList).setComment(comment);
        newApi.setStatus(UserApiStatus.PENDING);

        int rows = userApiMapper.updateUserApiById(newApi);
        Assert.check(rows == 0, ErrorCode.ERR_RECORD_UPDATE);
    }

    @Transactional
    public void delUserApiByIds(List<Integer> ids) {
        for (Integer id : ids) {
            userApiMapper.delUserApiById(id);
        }
    }
}
