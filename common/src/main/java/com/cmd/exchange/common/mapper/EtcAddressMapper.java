package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.EthAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EtcAddressMapper {

    int add(EthAddress ethAddress);

    EthAddress getEtcAddressByUserId(@Param("userId") Integer userId);

    EthAddress getEtcAddressByAddress(@Param("address") String address);

    /**
     * 获取比指定id大的最小的一个id的地址
     *
     * @param id 指定id
     * @return 比id大的最小id的地址或者null
     */
    public EthAddress getNextAddress(@Param("id") int id);
}
