package com.codeshuai.thinwater.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 每天香用户发送的信息
 * receive_msg
 *
 * @author
 */
@Data
@TableName("send_msg")
public class SendMsg implements Serializable {
    /**
     * 自增id
     */

    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 接收方
     */
    private String email;

    /**
     * 每天回复的文本
     */
    private String msg;

    /**
     * 消息创建时间 （整型）
     */
    private String createTime;

    private static final long serialVersionUID = 1L;
}