package com.cmd.exchange.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmd.exchange.common.model.ChatKey;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.oauth2.ShiroUtils;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.rongHub.model.MessageModel;
import com.cmd.exchange.service.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Author: lsl
 * @Date: Created in 2018/8/6
 */
@Api(tags = "聊天相关")
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @ApiOperation("应用sdk获取授权key")
    @PostMapping("/key")
    public CommonResponse<ChatKey> getChatKey() {

        User user = ShiroUtils.getUser();
        ChatKey chatKey = chatService.getKey(user.getId(), user.getUserName());

        return new CommonResponse(chatKey);
    }

    @ApiOperation("从服务端同步消息记录")
    @PostMapping("/sync/messages")
    public CommonResponse SyncMessages(
            @RequestParam String fromUserId, @RequestParam String toUserId, @RequestParam String objectName, @RequestParam String content, @RequestParam String channelType,
            @RequestParam String msgTimestamp, @RequestParam String msgUID, @RequestParam(required = false) String source, @RequestParam(required = false) String[] groupUserIds,
            HttpServletRequest request) {

        /**
         * 不确定groupUserIds是否存在，故未在请求域内使用MessageModel对象
         * 本系统暂时只使用融云sdk的单对单聊天功能，因此忽略groupUserIds
         * 垃圾平台，说有sensitiveType,结果没有，暂时先统一写0
         * source好像也没有...
         */
        if (source == null) source = "default";

        if (fromUserId.contains("ID")) {
            /**
             *测试时返回id为XXXID字样，无法转为数字
             * 测试完后取消
             */
            fromUserId = "1";
            toUserId = "2";
        }

        JSONObject json = JSON.parseObject(content);
        String message = String.valueOf(json.get("content"));
        String extra = String.valueOf(json.getJSONObject("user"));

        MessageModel messageModel = new MessageModel().setFromUserId(Integer.parseInt(fromUserId)).setToUserId(Integer.parseInt(toUserId)).setObjectName(objectName)
                .setContent(message).setExtra(extra).setChannelType(channelType).setMsgTimestamp(new Date(Long.parseLong(msgTimestamp)))
                .setMsgUID(msgUID).setSensitiveType(0).setSource(source);

        chatService.SyncChatInfo(request, messageModel);

        return new CommonResponse();
    }

}
