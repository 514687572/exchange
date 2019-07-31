package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.Otc;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;

@Mapper
public interface OtcMapper {

    int add(Otc application);

    int frozenAmount(@Param("id") Long id, @Param("amount") BigDecimal amount);

    int unFrozenAmount(@Param("id") Long id, @Param("amount") BigDecimal amount);

    int increaseAmountSuccess(@Param("id") Long id, @Param("amount") BigDecimal amount);

    int updateApplicationStatus(@Param("id") Long id, @Param("status") Integer status);

    Otc getApplicationById(@Param("id") Long id);

    Otc lockApplicationById(@Param("id") Long id);

    Page<Otc> getApplicationByUserId(@Param("userId") Integer userId, @Param("coinName") String coinName, @Param("type") Integer type, @Param("status") Integer[] status, RowBounds rowBounds);

    Page<Otc> getApplication(@Param("coinName") String coinName, @Param("type") Integer type, @Param("status") Integer[] status, RowBounds rowBounds);

    int getApplicationBuyCount(@Param("userId") Integer userId, @Param("coinName") String coinName);

    int getApplicationSellCount(@Param("userId") Integer userId, @Param("coinName") String coinName);
}
