package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.OtcOrder;
import com.cmd.exchange.common.vo.OtcOrderVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper
public interface OtcOrderMapper {

    int add(OtcOrder otcOrder);

    int updateApplicationOrder(OtcOrder otcOrder);

    int setApplyRoleNull(@Param("id") Long id);

    OtcOrder getApplicationOrderById(@Param("id") Long id);

    OtcOrder lockApplicationOrderById(@Param("id") Long id);

    Page<OtcOrderVO> getApplicationOrderByUserId(@Param("userId") Integer userId, @Param("coinName") String coinName, @Param("type") Integer type, @Param("status") Integer[] status, RowBounds rowBounds);

    Page<OtcOrder> getApplicationOrder(@Param("coinName") String coinName, @Param("status") Integer[] status, RowBounds rowBounds);

    List<OtcOrder> getApplicationOrderExpire(@Param("coinName") String coinName, @Param("status") Integer status, @Param("now") Date now);


    //获取某个用户30天内的成交订单笔数
    //获取某个用户30天内的成交数
    //获取某个用户30天内的取消数
    Integer getOtcOrderTradePass(@Param("userId") Integer userId, @Param("coinName") String coinName);

    Integer getOtcOrderTradeFail(@Param("userId") Integer userId, @Param("coinName") String coinName);

    BigDecimal getOtcOrderAmountPass(@Param("userId") Integer userId, @Param("coinName") String coinName);

    BigDecimal getOtcOrderAmountFail(@Param("userId") Integer userId, @Param("coinName") String coinName);

}
