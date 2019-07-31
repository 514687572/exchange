package com.cmd.exchange.common.mapper;


import com.cmd.exchange.common.model.Machine;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.model.UserMachine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMachineMapper {

    UserMachine getOneInfo(UserMachine userMachine);

    //分页
  //  List<UserMachine> getInfo(UserMachine userMachine, RowBounds rowBounds);

    List<UserMachine> getInfo(UserMachine userMachine);

    int add(UserMachine userMachine);

    int mod(UserMachine userMachine);


    //更新挖矿收益 初始化挖矿状态
    @Update("update t_user_machine set total_income=total_income+yesterday_income," +
            "yesterday_income=IFNULL((select machine_give from t_machine where id=t_user_machine.machine_id and `status`=2),0)," +
            "is_open = 1," +
            "yesterday_time=NOW()," +
            "machine_name=(select machine_name from t_machine where id=t_user_machine.machine_id and `status`=2)," +
            "machine_coin=(select machine_coin from t_machine where id=t_user_machine.machine_id and `status`=2)" +
            " where is_open=2")
    int updateUserMachineIncomeAndOpen();

    int machineOpem(UserMachine userMachine);



}
