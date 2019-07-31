package com.cmd.exchange.service;

import com.cmd.exchange.common.mapper.ChatMapper;
import com.cmd.exchange.common.model.ChatKey;
import com.cmd.exchange.common.rongHub.InsertPool;
import com.cmd.exchange.common.rongHub.RongHelp;
import com.cmd.exchange.common.rongHub.model.MessageModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Author: lsl
 * @Date: Created in 2018/8/6
 */
@Slf4j
@Service
public class ChatService {


    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private RongHelp rongHelp;

    /**
     * @Desc: 获取融云sdk聊天token, 先查询数据库，如不存在则调用注册方法生成
     */
    public ChatKey getKey(int userId, String userName) {
        String key;
        ChatKey chatKey = chatMapper.getChatInfoByUserId(userId);
        if (chatKey == null) {
            try {

                key = rongHelp.registerRongHub(userId, userName);

                chatKey = new ChatKey().setKey(key).setUserId(userId).setUpdateTime(new Date());

                chatMapper.addChatInfo(userId, key);

            } catch (Exception e) {
                log.error("获取聊天秘钥失败：{}, {}", userId, userName);
            }
        }
        return chatKey;
    }

    /**
     * @Desc: 将融云服务端同步过来的消息写入数据库。
     */
    @Transactional
    public void SyncChatInfo(HttpServletRequest request, MessageModel messageModel) {

        InsertPool insertPool = InsertPool.getInstance();

        insertPool.addRunnable(() -> {

            int result = 0;
            /**
             *校验请求是否合法
             */
            boolean flag = rongHelp.checkRequestHeader(request);

            if (flag) result = chatMapper.insertChatLog(messageModel);

            if (result == 1) return;

            /**
             * 融云只要看到我们的响应就会认为我们同步成功，
             * 因此我们这里对储存失败的历史消息暂时打印日志
             */
            log.error("储存聊天记录错误{}，{}", flag, messageModel.toString());
        });
    }
}
