package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.EthAddress;
import com.cmd.exchange.common.model.TransferAddress;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.bind.annotation.PostMapping;

@Mapper
public interface TransferAddressMapper {

    int add(TransferAddress transferAddress);

    int del(@Param("userId") Integer userId, @Param("id") Integer id);

    int updateTransferAddress(@Param("userId") Integer userId, @Param("id") Integer id, @Param("name") String name, @Param("coinName") String coinName, @Param("address") String address);

    Page<TransferAddress> getTransferAddressByUserId(@Param("userId") Integer userId, @Param("coinName") String coinName, RowBounds rowBounds);

    int countTransferAddress(@Param("userId") Integer userId, @Param("coinName") String coinName, @Param("address") String address);

}
