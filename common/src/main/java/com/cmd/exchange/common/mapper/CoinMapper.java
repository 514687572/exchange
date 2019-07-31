package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.Coin;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CoinMapper {

    int add(Coin coin);

    int updateCoin(Coin coin);

    int deleteCoin(@Param("coinName") String coinName);

    Coin getCoinByName(@Param("coinName") String coinName);

    List<Coin> getCoin();

    @Select("select * from t_coin where category='eth' or category='token'")
    public List<Coin> getAllEthCoins();

    @Select("select * from t_coin where category=#{category}")
    public List<Coin> getCoinsByCategory(String category);

    public List<Coin> getCoinListByStatus(@Param("status") Integer status);

    @Select("select * from t_coin where 1=1")
    List<Coin> getCoinAll();

    @Select("select image from t_coin where name=#{coinName}")
    String getCoinsImage(String coinName);
}
