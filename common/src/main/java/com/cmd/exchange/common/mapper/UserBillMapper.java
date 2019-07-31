package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.UserBill;
import com.cmd.exchange.common.vo.UserBillVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserBillMapper {
    @Insert("insert into t_user_bill(user_id,coin_name,sub_type,reason,change_amount,last_time,comment)"
            + "values(#{userId},#{coinName},#{subType},#{reason},#{changeAmount},NOW(),#{comment})")
    int insertUserBill(@Param("userId") int userId, @Param("coinName") String coinName, @Param("subType") int subType,
                       @Param("reason") String reason, @Param("changeAmount") BigDecimal changeAmount, @Param("comment") String comment);

    BigDecimal getTotalUserBillReward(@Param("userId") int userId, @Param("array") String[] array);

    Page<UserBill> getUserBillReward(@Param("userId") int userId, @Param("array") String[] array,
                                     @Param("coinName") String coinName,
                                     @Param("beginTime") String beginTime,
                                     @Param("endTime") String endTime, RowBounds rowBounds);

    //Page<UserBillVO> getBillListByUser(@Param("userId")Integer userId,
    //                                   @Param("coinName") String coinName, RowBounds rowBounds);

    Page<UserBillVO> getUserBills(@Param("userId") Integer userId, @Param("type") String type, @Param("coinName") String coinName,
                                  @Param("beginTime") String beginTime, @Param("endTime") String endTime, RowBounds rowBounds);

    Page<UserBillVO> getUserBills5(@Param("userId") Integer userId, @Param("subType") Integer subType, @Param("reason") String reason, @Param("coinName") String coinName,
                                   @Param("beginTime") String beginTime, @Param("endTime") String endTime, RowBounds rowBounds);

    Page<UserBillVO> getUserBillsPage(@Param("userId") Integer userId, @Param("type") String type,
                                      @Param("startTime") String startTime, @Param("endTime") String endTime,
                                      @Param("coinName") String coinName, @Param("subType") Integer subType, @Param("reason") String reason, RowBounds rowBounds);

    Page<UserBillVO> getUserBillsByTime(@Param("userId") Integer userId, @Param("type") String type,
                                        @Param("startTime") String startTime, @Param("endTime") String endTime,
                                        @Param("coinName") String coinName, @Param("groupType") String groupType,
                                        @Param("subType") Integer subType, @Param("reason") String reason,
                                        @Param("realName") String realName,
                                        RowBounds rowBounds);

    // 计算某个账单变化原因某个时间内变化的金额总额
    BigDecimal getTotalByReasonAndTime(@Param("reason") String reason, @Param("begin") int begin, @Param("end") int end);

    // 获取第一个用户清单，这个主要是用来获取系统交易的开始时间的，有写地方需要对资金进行统计，获取开始时间进行统计
    @Select("select * from t_user_bill order by id limit 0,1")
    UserBill getFirstBill();

    // 统计指定某个用户的各个币的推荐返佣个数
    @Select("select coin_name as name,sum(change_amount) as total from t_user_bill where user_id=#{userId} and reason='TxRecFe' group by coin_name")
    List<Map<String, Object>> statUserTradeFeeRewards(@Param("userId") int userId);


    @Select("select sum(change_amount) from t_user_bill where reason=#{reason} and last_time >= #{timeBegin} and #{timeEnd} > last_time")
    BigDecimal smartUsdtFee(@Param("timeBegin") Date timeBegin, @Param("timeEnd") Date timeEnd,@Param("reason") String reason);
}
