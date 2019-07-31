package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.TradeNoWarningUser;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TradeNoWarningUserMapper {

    Page<TradeNoWarningUser> getTradeNoWarningUserList(@Param("userName") String userName, @Param("noWarningType") String noWarningType, RowBounds rowBounds);

    void delTradeNoWarningUserById(@Param("id") Integer id);

    int addTradeNoWarningUser(TradeNoWarningUser tradeNoWarningUser);

    TradeNoWarningUser getTradeNoWarningByuserName(@Param("userName") String userName, @Param("noWarningType") String noWarningType);

    List<TradeNoWarningUser> getTradeNoWarningUserAll();
}
