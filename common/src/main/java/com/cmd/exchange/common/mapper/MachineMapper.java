package com.cmd.exchange.common.mapper;


import com.cmd.exchange.common.model.Machine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.hibernate.validator.constraints.EAN;

import java.util.List;

@Mapper
public interface MachineMapper {

    Machine getOneInfo(Machine machine);

    List<Machine> getInfo(Machine machine, RowBounds rowBounds);

    int add(Machine machine);

    int mod(Machine machine);


}
