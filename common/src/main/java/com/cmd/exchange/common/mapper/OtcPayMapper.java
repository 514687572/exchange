package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.Otc;
import com.cmd.exchange.common.model.OtcPay;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;

@Mapper
public interface OtcPayMapper {
    int add(OtcPay otcPay);
}
