package com.codeshuai.thinwater.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户基金的代码
 * user_fund_code
 *
 * @author
 */
@Data
@TableName("user_fund_code")
public class UserFundCode implements Serializable {
    /**
     * 自增id
     */

    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;


    /**
     * 接收方
     */
    private Integer userId;

    /**
     * 基金Id
     */
    private String fundCode;

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