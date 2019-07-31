package com.cmd.exchange.common.mapper;


import com.cmd.exchange.common.model.Machine;
import com.cmd.exchange.common.model.TransferAgents;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface TransferAgentsMapper {

    TransferAgents getOneInfo(TransferAgents transferAgents);

    List<Machine> getInfo(TransferAgents transferAgents, RowBounds rowBounds);

    int add(TransferAgents transferAgents);

    int mod(TransferAgents transferAgents);

}
