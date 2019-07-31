package com.cmd.exchange.common.mapper;


import com.cmd.exchange.common.model.Machine;
import com.cmd.exchange.common.model.TransferAgents;
import com.cmd.exchange.common.model.TransferUsers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface TransferUsersMapper {

    TransferUsers getOneInfo(TransferUsers transferUsers);

    List<TransferUsers> getInfo(TransferUsers transferUsers);

    int add(TransferUsers transferUsers);

    int mod(TransferUsers transferUsers);

}
