package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.OtcBill;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface OtcBillMapper {

    int add(OtcBill otcBill);

    Page<OtcBill> getApplicationBillByUserId(@Param("userId") int userId,
                                             @Param("coinName") String coinName,
                                             @Param("type") Integer type,
                                             RowBounds rowBounds);
}
