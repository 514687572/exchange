package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.enums.MerchantOrderStatus;
import com.cmd.exchange.common.enums.MerchantOrderType;
import com.cmd.exchange.common.model.OrderComplaint;
import com.cmd.exchange.common.model.UserMerchantOrder;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface UserMerchantOrderMapper {
    UserMerchantOrder lockOrder(@Param("id") int id);

    UserMerchantOrder getOrder(@Param("id") int id);

    void addOrder(UserMerchantOrder order);

    /**
     * 查询订单
     *
     * @param userId     用户id
     * @param merchantId 商人id
     * @param status     当前状态
     * @param type       类型
     * @param rowBounds  分页说明
     * @return
     */
    Page<UserMerchantOrder> getOrders(@Param("userId") Integer userId, @Param("merchantId") Integer merchantId,
                                      @Param("status") MerchantOrderStatus status, @Param("type") MerchantOrderType type,
                                      @Param("orderNo") String orderNo, RowBounds rowBounds);

    int cancelOrder(@Param("id") int id, @Param("cancelType") String cancelType, @Param("cancelComment") String cancelComment);

    int acceptOrder(@Param("id") int id);

    int uploadVoucher(@Param("id") int orderId, @Param("credentialComment") String credentialComment, @Param("credentialUrls") String credentialUrls);

    int completeOrder(@Param("id") int id);

    int appealOrder(@Param("id") int id);

    int updateOrderStatus(@Param("id") int orderId, @Param("status") MerchantOrderStatus status);

    // 获取超期订单
    UserMerchantOrder getNextTimeoutOrder(@Param("lastId") int lastId, @Param("timeOut") int timeOut);

    void addComplaint(OrderComplaint oc);

    OrderComplaint getOrderComplaint(@Param("id") int id);

    OrderComplaint lockOrderComplaint(@Param("id") int id);

    // 获取当前的订单申诉，不会获取已经取消的订单申诉
    OrderComplaint getOrderComplaintByOrder(@Param("orderId") int orderId);

    Page<OrderComplaint> getOrderComplaintList(@Param("userId") Integer userId, RowBounds rowBounds);

    void updateComplaintStatus(@Param("id") int id, @Param("status") int status);

}
