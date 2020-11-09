package com.codeshuai.thinwater.pojo;

import lombok.*;

/**
 * 微信发给后台的xml格式数据
 *
 * @author ：codeshuai
 * @date ：Created in 2020/11/8 2:25
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageText {

    private String Content;
    private String MsgId;
    private String ToUserName;
    private String FromUserName;
    private long CreateTime;
    private String MsgType;
}
