package com.codeshuai.thinwater.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 数据库中，收到的信息，保存至数据库中
 * receive_msg
 *
 * @author
 */
@Data
@TableName("receive_msg")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveMsg implements Serializable {
    /**
     * 自增id
     */
    @TableId(value = "id",type = IdType.INPUT)
    private Integer id;

    /**
     * 开发者微信号
     */
    private String toUserName;

    /**
     * 发送方帐号（一个OpenID）
     */
    private String fromUserName;

    /**
     * 消息创建时间 （整型）
     */
    private String createTime;

    /**
     * 消息类型，文本为text
     */
    private String msgType;

    /**
     * 文本消息内容
     */
    private String content;

    /**
     * 消息id，64位整型
     */
    private String msgId;

    private static final long serialVersionUID = 1L;
}