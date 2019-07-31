package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.ChatKey;
import com.cmd.exchange.common.rongHub.model.MessageModel;
import com.cmd.exchange.common.vo.ChatLogVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;

/**
 * @Author: lsl
 * @Date: Created in 2018/8/6
 */
@Mapper
public interface ChatMapper {

    /**
     * @Desc: 查询用户key
     */
    ChatKey getChatInfoByUserId(@Param("userId") int userId);

    /**
     * @Desc: 储存聊天秘钥
     */
    int addChatInfo(@Param("userId") int userId, @Param("key") String key);

    /**
     * @Desc: 储存聊天记录
     */
    int insertChatLog(@Param("record") MessageModel record);

    /**
     * @Desc: 根据订单查看相关聊天记录
     */
    Page<ChatLogVo> getChatLogByOrder(@Param("sellId") int sellId,
                                      @Param("buyId") int buyId,
                                      @Param("createTime") Date createTime,
                                      @Param("dealTime") Date dealTime,
                                      RowBounds rowBounds);

}
