package com.codeshuai.thinwater.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.codeshuai.thinwater.dao.SendMsgDao;
import com.codeshuai.thinwater.dao.UserEmailDao;
import com.codeshuai.thinwater.dao.UserFundCodeDao;
import com.codeshuai.thinwater.pojo.Constant;
import com.codeshuai.thinwater.pojo.SendMsg;
import com.codeshuai.thinwater.pojo.UserEmail;
import com.codeshuai.thinwater.pojo.UserFundCode;
import com.codeshuai.thinwater.util.DateUtil;
import com.codeshuai.thinwater.util.RegexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务，周一至周五23点执行，用户所选的基金，发送其状况！
 *
 * @author ：codeshuai
 * @date ：Created in 2020/11/8 19:47
 */
@Service
public class SendMsgService {

    @Resource
    SendMsgDao sendMsgDao;
    @Resource
    UserEmailDao userEmailDao;
    @Resource
    UserFundCodeDao userFundCodeDao;
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 设置定时任务
     * 周一至周五，每天晚上23点执行
     */
    @Scheduled(cron = "0 0 23 ? * MON-FRI")
    public void sendMsg() {
        sendMessages();
    }

    public void sendMessages() {
//        先获取当前库中所有存在可用的用户信息
        Map<String, Object> selectMap = new HashMap<>(2);
        selectMap.put("is_del", 0);
        List<UserEmail> userEmails = userEmailDao.selectByMap(selectMap);
//        增强for循环获取每个用户的信息包括email和用户ID
        for (UserEmail userEmail : userEmails) {
            QueryWrapper<UserFundCode> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userEmail.getId());
            List<UserFundCode> userFundCodes = userFundCodeDao.selectList(queryWrapper);

            String sendEmailMsg = new String();
            Integer index = 0;
            // for循环获取该人下的基金ID
            for (UserFundCode userFundCode : userFundCodes) {
//                获取用户的基金code
                String fundCode = userFundCode.getFundCode();
                String data = sendHttpFundCode(fundCode);
                if (data != null && data != "") {
                    index++;
                    sendEmailMsg = sendEmailMsg + index + ": " + data + "\n";
                }
            }
            String tet = sendEmailMsg + "\n\n" + Constant.ENDMSG;
            SendMsg sendMsg = new SendMsg();
            sendMsg.setEmail(userEmail.getEmail());
            sendMsg.setMsg(tet);
            sendMessage(sendMsg);
            System.out.println("=============邮件发送结束=============");
            sendMsg.setCreateTime(DateUtil.NowDate());
            sendMsgDao.insert(sendMsg);
        }
    }

    /**
     * 发送邮件
     *
     * @param sendMsg
     */
    public void sendMessage(SendMsg sendMsg) {
        //创建简单邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        // 接收邮箱账号
        message.setTo(sendMsg.getEmail());
        // yml配置文件中邮箱账号
        message.setFrom(from);
        message.setText(sendMsg.getMsg());
        message.setSubject(DateUtil.NowMonthDate() + Constant.EMAILSUBJECT);
        mailSender.send(message);
    }

    /**
     * http 根据基金的编号获取当日信息
     *
     * @param fundCode
     * @return
     */
    public String sendHttpFundCode(String fundCode) {

        String URL = Constant.INFORMFUNDDATAURL.replace(Constant.REPLACEFUNDCODE, fundCode);
        String fundData = HttpUtil.get(URL);
        if (!fundData.isEmpty()) {
            String dataString = RegexUtil.queryOutBraces(fundData);
            if (dataString != null && dataString != "") {
                JSONObject dataJson = JSONObject.parseObject(dataString);
                String name = (String) dataJson.get("name");
                String gsz = (String) dataJson.get("gsz");
                String gszzl = (String) dataJson.get("gszzl");
                return name + "  净值估算：" + gsz + "  今日涨幅：" + gszzl + "%";
            }
        }
        return null;
    }

}
