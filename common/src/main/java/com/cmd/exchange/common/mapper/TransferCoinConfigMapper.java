package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.TransferCoinConfig;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface TransferCoinConfigMapper {

    int addTransferCoinConfig(TransferCoinConfig transferCoinConfig);

    void delTransferCoinConfig(@Param("id") Integer id);

    TransferCoinConfig getTransferCoinConfigById(@Param("id") Integer id);

    TransferCoinConfig getTransferCoinConfigByCoinName(@Param("coinName") String coinName);

    List<TransferCoinConfig> getTransferCoinConfigList();

    Page<TransferCoinConfig> getTransferCoinConfigPage(@Param("coinName") String coinName, RowBounds rowBounds);

    int updateTransferCoinConfig(TransferCoinConfig transferCoinConfig);
}
