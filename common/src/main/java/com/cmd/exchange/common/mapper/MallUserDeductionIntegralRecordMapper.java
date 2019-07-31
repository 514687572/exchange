package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.MallUserDeductionIntegralRecord;
import com.cmd.exchange.common.model.UserAmountTransferredIntoLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商城积分扣除业务 记录
 */
@Mapper
public interface MallUserDeductionIntegralRecordMapper {

    int add(MallUserDeductionIntegralRecord mallUserDeductionIntegralRecord);

    MallUserDeductionIntegralRecord getByOrderNum(String orderNum);
}
