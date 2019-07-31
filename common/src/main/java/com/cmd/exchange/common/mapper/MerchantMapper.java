package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.enums.MerchantOrderType;
import com.cmd.exchange.common.model.Merchant;
import com.cmd.exchange.common.model.MerchantBill;
import com.cmd.exchange.common.model.MerchantCoin;
import com.cmd.exchange.common.model.MerchantCoinConf;
import com.cmd.exchange.common.vo.MerchantVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface MerchantMapper {
    Merchant getMerchant(@Param("id") int id);

    Merchant lockMerchant(@Param("id") int id);

    Merchant getMerchantByUserId(@Param("userId") int userId);

    Merchant lockMerchantByUserId(@Param("userId") int userId);

    Page<MerchantVo> getMerchantListForUser(@Param("amount") BigDecimal amount, @Param("coinName") String coinName, @Param("orderType") MerchantOrderType orderType,
                                            @Param("type") Integer type, @Param("merchantName") String merchantName, @Param("exceptId") Integer exceptId, RowBounds rowBounds);

    Merchant getUserMerchant(@Param("userId") int userId);

    void updateMerchant(Merchant merchant);

    // 增加新的兑换商，兑换商状态是禁用，等管理员修改状态后才变成可用
    void addMerchant(Merchant merchant);

    Page<Merchant> searchMerchant(@Param("type") Integer type, @Param("userId") Integer userId, @Param("merchantName") String merchantName, @Param("status") Integer status, RowBounds rowBounds);

    //////////////////////////////////////////////////////////////////////////////////////
    MerchantCoinConf getMerchantCoinConf(@Param("coinName") String coinName);

    MerchantCoinConf getMerchantCoinConfById(@Param("id") int id);

    // 获取交易币列表，status为null那么返回所有的
    List<MerchantCoinConf> getMerchantCoinConfByStatus(@Param("status") Integer status);

    // 增加交易币种
    void addMerchantCoinConf(MerchantCoinConf conf);

    int updateMerchantCoinConf(MerchantCoinConf conf);

    int delMerchantCoinConf(@Param("id") int id);

    ///////////////////////////////////////////////////////////////////////////////////
    MerchantCoin getMerchantCoin(@Param("merchantId") int merchantId, @Param("coinName") String coinName);

    void addMerchantCoin(MerchantCoin merchantCoin);

    List<MerchantCoin> getMerchantCoins(int merchantId);

    int changeMerchantBalance(@Param("merchantId") int merchantId, @Param("coinName") String coinName,
                              @Param("changeAvailableBalance") BigDecimal changeAvailableBalance,
                              @Param("changeFreezeBalance") BigDecimal changeFreezeBalance);

    /////////////////////////////////////////////////////////////////////////////////////////
    int insertMerchantBill(@Param("merchantId") int merchantId, @Param("coinName") String coinName, @Param("subType") int subType,
                           @Param("reason") String reason, @Param("changeAmount") BigDecimal changeAmount, @Param("comment") String comment);

    Page<MerchantBill> getMerchantBills(@Param("merchantId") Integer merchantId, @Param("coinName") String coinName, @Param("reason") String reason,
                                        @Param("beginTime") Integer beginTime, @Param("endTime") Integer endTime, RowBounds rowBounds);
}
