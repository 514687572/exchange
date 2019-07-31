package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.enums.MerchantOrderStatus;
import com.cmd.exchange.common.enums.MerchantOrderType;
import com.cmd.exchange.common.model.SuperMerchantOrder;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface SuperMerchantOrderMapper {
    SuperMerchantOrder lockOrder(@Param("id") int id);

    SuperMerchantOrder getOrder(@Param("id") int id);

    void addOrder(SuperMerchantOrder order);

    /**
     * 查询订单
     *
     * @param merchantId      兑换商id
     * @param superMerchantId 超级兑换商id
     * @param status          当前状态
     * @param type            类型
     * @param rowBounds       分页说明
     * @return
     */
    Page<SuperMerchantOrder> getOrders(@Param("merchantId") Integer merchantId, @Param("superMerchantId") Integer superMerchantId,
                                       @Param("status") MerchantOrderStatus status, @Param("type") MerchantOrderType type,
                                       @Param("orderNo") String orderNo, RowBounds rowBounds);

    int cancelOrder(@Param("id") int id, @Param("cancelType") String cancelType, @Param("cancelComment") String cancelComment);

    int acceptOrder(@Param("id") int id);

    int uploadVoucher(@Param("id") int orderId, @Param("credentialComment") String credentialComment, @Param("credentialUrls") String credentialUrls);

    int completeOrder(@Param("id") int id);

    int appealOrder(@Param("id") int id);

    // 获取超期订单
    SuperMerchantOrder getNextTimeoutOrder(@Param("lastId") int lastId, @Param("timeOut") int timeOut);

    //void addComplaint(OrderComplaint oc);
    //OrderComplaint getOrderComplaint(@Param("id") int id);
    //OrderComplaint lockOrderComplaint(@Param("id") int id);
    //OrderComplaint getOrderComplaintByOrder(@Param("orderId") int orderId);
}
