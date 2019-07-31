package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.DelayShareOut;
import com.cmd.exchange.common.vo.ShareOutBonusLogRequestVO;
import com.cmd.exchange.common.vo.ShareOutBonusLogVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.math.BigInteger;

@Mapper
public interface ShareOutBonusLogMapper {
    @Insert("insert into t_share_out_bonus_log(user_id,coin_name,user_base_coin,user_bonus,create_time)"
            + "values(#{userId},#{coinName},#{userBaseCoin},#{userBonus},NOW())")
    int addLog(@Param("userId") int userId, @Param("coinName") String coinName, @Param("userBaseCoin") BigDecimal userBaseCoin, @Param("userBonus") BigDecimal userBonus);

    Page<ShareOutBonusLogVO> getShareOutBonusList(ShareOutBonusLogRequestVO request, RowBounds rowBounds);

    @Insert("insert into t_delay_shareout(user_id,coin_name,user_base_coin,change_amount,comment,add_time)"
            + "values(#{userId},#{coinName},#{userBaseCoin},#{changeAmount},#{comment}, NOW())")
    void addDelayShareOut(@Param("userId") int userId, @Param("coinName") String coinName, @Param("userBaseCoin") BigDecimal userBaseCoin,
                          @Param("changeAmount") BigDecimal changeAmount, @Param("comment") String comment);

    @Select("select * from t_delay_shareout order by id asc limit 0,1 for update")
    DelayShareOut getFirstDelayShareOut();

    @Delete("delete from t_delay_shareout where id=#{id}")
    int deleteDelayShareOut(@Param("id") BigInteger id);
}
