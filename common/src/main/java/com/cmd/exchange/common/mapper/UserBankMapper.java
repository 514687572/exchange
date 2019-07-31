package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.UserBank;
import com.cmd.exchange.common.model.UserBill;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface UserBankMapper {

    int add(UserBank userBank);

    int updateUserBank(UserBank userBank);

    int del(@Param("id") Integer id);

    UserBank getUserBankById(@Param("id") Integer id);

    List<UserBank> checkUserBankById(@Param("id") Integer id);

    List<UserBank> getUserBankByUserId(@Param("userId") Integer userId, @Param("bankType") Integer bankType, @Param("status") Integer status);

    int updateUserBankByUserId(@Param("userId") Integer userId, @Param("bankType") Integer bankType, @Param("status") Integer status);

}
