package com.codeshuai.thinwater.controller;

import com.codeshuai.thinwater.pojo.Constant;
import com.codeshuai.thinwater.pojo.ReceiveMsg;
import com.codeshuai.thinwater.service.MsgService;
import com.codeshuai.thinwater.util.DateUtil;
import com.codeshuai.thinwater.util.MessageUtil;
import com.codeshuai.thinwater.util.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author ：codeshuai
 * @date ：Created in 2020/11/8 1:23
 */
@RestController
@RequestMapping("/weixin")
public class MsgController {

    @Autowired
    MsgService msgService;

    /**
     * 微信消息接收和token验证
     */
    @RequestMapping(value = "/msg", method = RequestMethod.GET)
    public String get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("----------------验证微信服务号信息开始----------");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            System.out.println("----验证服务号结束.........");
            return echostr;
        } else {
            return null;
        }
    }

    /**
     * 微信的post的请求接口，主要用于消息的回复！
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/msg", method = RequestMethod.POST)
    public String dopost(HttpServletRequest request, HttpServletResponse response) {

        response.setCharacterEncoding("utf-8");
        MessageUtil textMessage = new MessageUtil();
        //将微信请求xml转为map格式，获取所需的参数
        Map<String, String> map = MessageUtil.xmlToMap(request);

//        获取是否是纯文字
        String MsgType = map.get("MsgType");
        String ToUserName = map.get("ToUserName");
        String FromUserName = map.get("FromUserName");
        if (!"text".equals(MsgType)) {
            return textMessage.initMessage(FromUserName, ToUserName, Constant.TEXTERROR + Constant.ERROR);
        }

        //将微信传入的值，解析成实体类
        ReceiveMsg receiveMsg = new ReceiveMsg();
        receiveMsg.setToUserName(ToUserName);
        receiveMsg.setFromUserName(FromUserName);
        receiveMsg.setCreateTime(DateUtil.timeStamp2Date(map.get("CreateTime"), null));
        receiveMsg.setMsgType(MsgType);
        receiveMsg.setContent(map.get("Content"));
        receiveMsg.setMsgId(map.get("MsgId"));

        String result = msgService.dealData(receiveMsg);

        return textMessage.initMessage(FromUserName, ToUserName, result);
    }


}
