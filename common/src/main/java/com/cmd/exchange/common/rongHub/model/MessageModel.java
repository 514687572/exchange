package com.cmd.exchange.common.rongHub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: lsl
 * @Date: Created in 2018/8/6
 * @Desc: 用于接收从服务器同步的消息记录
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MessageModel {
    /**
     * @Desc: 发送用户id
     */
    private int fromUserId;
    /**
     * @Desc: 接收用户Id，即为客户端 targetId
     */
    private int toUserId;
    /**
     * @Desc: 消息类型，文本消息 RC:TxtMsg 、 图片消息 RC:ImgMsg 、语音消息 RC:VcMsg 、图文消息 RC:ImgTextMsg 、位置消息 RC:LBSMsg 、
     * 添加联系人消息 RC:ContactNtf 、提示条通知消息 RC:InfoNtf 、资料通知消息 RC:ProfileNtf 、通用命令通知消息 RC:CmdNtf ，详细请参见消息类型说明文档。
     */
    private String objectName;
    /**
     * @Desc: 发送消息内容，参考融云消息类型表.示例说明
     */
    private String content;

    /**
     * 附加消息，冗余
     */
    private String extra;

    /**
     * @Desc: 会话类型，二人会话是 PERSON 、讨论组会话是 PERSONS 、群组会话是 GROUP 、
     * 聊天室会话是 TEMPGROUP 、客服会话是 CUSTOMERSERVICE 、 系统通知是 NOTIFY 、
     * 应用公众服务是 MC 、公众服务是 MP。 对应客户端 SDK 中 ConversationType 类型，
     * 二人会话是 1 、讨论组会话是 2 、群组会话是 3 、聊天室会话是 4 、客服会话是 5 、 系统通知是 6 、应用公众服务是 7 、公众服务是 8。
     */
    private String channelType;
    /**
     * @Desc: 服务端收到客户端发送消息时的服务器时间（1970年到现在的毫秒数）
     */
    private Date msgTimestamp;
    /**
     * @Desc: 可通过 msgUID 确定消息唯一
     */
    private String msgUID;
    /**
     * @Desc: 消息中是否含有敏感词标识，0 为不含有敏感词，1 为含有屏蔽敏感词，2 为含有替换敏感词。消息路由功能默认含有屏蔽敏感词的消息不进行路由，
     * 可提交工单开通含有敏感词的消息路由功能， 未开通情况下 sensitiveType 值默认为 0 不代表任何意义。
     * 开通后可通过该属性判断消息中是否含有敏感词。目前支持单聊、群聊、聊天室会话类型，其他会话类型默认为 0 ，
     * 开通后含有屏蔽敏感词的消息也不会进行下发，只会进行消息路由。
     */
    private int sensitiveType;
    /**
     * @Desc: 标识消息的发送源头，包括：iOS、Android、Websocket、MiniProgram（小程序）。目前支持单聊、群聊会话类型，其他会话类型为空。
     */
    private String source;
    /**
     * @Desc: channelType 为 GROUP 时此参数有效，显示为群组中指定接收消息的用户 ID 数组，
     * 该条消息为群组定向消息。非定向消息时内容为空，如指定的用户不在群组中内容也为空。
     */
    private String[] groupUserIds;
}
