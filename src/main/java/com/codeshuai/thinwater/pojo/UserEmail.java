package com.codeshuai.thinwater.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户与邮箱的关联，自增id为userId
 * user_email
 *
 * @author
 */
@Data
@TableName("user_email")
public class UserEmail implements Serializable {
    /**
     * 自增id
     */

    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;


    /**
     * 发送方帐号（一个OpenID）
     */
    private String fromUserName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 消息创建时间 （整型）
     */
    private String createTime;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDel;

    private static final long serialVersionUID = 1L;
}