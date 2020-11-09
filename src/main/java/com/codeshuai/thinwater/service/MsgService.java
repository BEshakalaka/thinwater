package com.codeshuai.thinwater.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.codeshuai.thinwater.dao.ReceiveMsgDao;
import com.codeshuai.thinwater.dao.UserEmailDao;
import com.codeshuai.thinwater.dao.UserFundCodeDao;
import com.codeshuai.thinwater.pojo.Constant;
import com.codeshuai.thinwater.pojo.ReceiveMsg;
import com.codeshuai.thinwater.pojo.UserEmail;
import com.codeshuai.thinwater.pojo.UserFundCode;
import com.codeshuai.thinwater.util.DateUtil;
import com.codeshuai.thinwater.util.RegexUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 当用户在微信的对话框输入信息后，传到后端 走此Service！
 * @author ：codeshuai
 * @date ：Created in 2020/11/8 6:28
 */
@Service
public class MsgService {

    @Resource
    ReceiveMsgDao receiveMsgDao;
    @Resource
    UserEmailDao userEmailDao;
    @Resource
    UserFundCodeDao userFundCodeDao;

    public String dealData(ReceiveMsg receiveMsg) {
//        将收到的信息存入数据库中，留作备份
        receiveMsgDao.insert(receiveMsg);
//        获取content的文字
        String content = receiveMsg.getContent();
//        调用方法校验content
        if (content.trim().startsWith(Constant.EMAIL)) {
            return email(receiveMsg);
        }
        if (content.trim().startsWith(Constant.ADDFUNDCODE)) {
            return addFundCode(receiveMsg);
        }
        if (content.trim().startsWith(Constant.DELFUNDCODE)) {
            return delFundCode(receiveMsg);
        }
        if (content.trim().startsWith(Constant.STOPSERVICE)) {
            return stopService(receiveMsg);
        }
        if (content.trim().startsWith(Constant.USERINFORM)) {
            return userInform(receiveMsg);
        }
        return Constant.ERROR;
    }

    /**
     * 当粉丝输入邮箱时！
     */
    public String email(ReceiveMsg receiveMsg) {
        UserEmail userEmail = selectUserIsExistByUserName(receiveMsg.getFromUserName());
//        获取有用的信息！
        String email = null;
        try {
            email = queryMsg(receiveMsg.getContent());
        } catch (Exception e) {
            return e.getMessage();
        }
//        校验email的格式！
        if (!RegexUtil.checkEmail(email)) {
            return Constant.EMAILFORMATERROR;
        }

        //        查到此人将其逻辑删除！
        if (userEmail != null) {
            QueryWrapper<UserEmail> userEmailQuery = new QueryWrapper<>();
//        先查询数据库是否有此人
            userEmailQuery.eq("from_user_name", receiveMsg.getFromUserName());
            int delete = userEmailDao.delete(userEmailQuery);
        }

        UserEmail insertUserEmail = new UserEmail();
//           设置用户的一个OpenID
        insertUserEmail.setFromUserName(receiveMsg.getFromUserName());
        insertUserEmail.setEmail(email);
        insertUserEmail.setCreateTime(DateUtil.NowDate());

        int insert = userEmailDao.insert(insertUserEmail);
        return insert == 1 ? Constant.EMAILINSERTSUCCESS : Constant.EMAILINSERTERROR;
    }


    /**
     * 当粉丝输入新增基金时！
     */
    public String addFundCode(ReceiveMsg receiveMsg) {
//        先查询是否存在此人！
        UserEmail userEmail = selectUserIsExistByUserName(receiveMsg.getFromUserName());
        if (userEmail == null) {
            return Constant.NOUSER;
        }

        String fundCodeId = null;
        try {
            fundCodeId = queryMsg(receiveMsg.getContent());
        } catch (Exception e) {
            return e.getMessage();
        }
        if (!RegexUtil.checkFundCode(fundCodeId)) {
            return Constant.FUNDCODEFORMATERROR;
        }
        UserFundCode userFundCode = selectFundCodeIsExistByUserIdAndFundCode(userEmail.getId(), fundCodeId);
        if (userFundCode != null) {
            return Constant.FUNDCODEEXIST;
        }
        UserFundCode insertUserFundCode = new UserFundCode();
        insertUserFundCode.setUserId(userEmail.getId());
        insertUserFundCode.setFundCode(fundCodeId);
        insertUserFundCode.setCreateTime(DateUtil.NowDate());
        int insert = userFundCodeDao.insert(insertUserFundCode);
        return insert == 1 ? Constant.FUNDCODEINSERTSUCCESS : Constant.FUNDCODEINSERTERROR;
    }

    /**
     * 当粉丝输入删除基金时！
     */
    public String delFundCode(ReceiveMsg receiveMsg) {
//        先查询是否存在此人！
        UserEmail userEmail = selectUserIsExistByUserName(receiveMsg.getFromUserName());
        if (userEmail == null) {
            return Constant.NOUSER;
        }
        String fundCodeId = null;
        try {
            fundCodeId = queryMsg(receiveMsg.getContent());
        } catch (Exception e) {
            return e.getMessage();
        }
        if (!RegexUtil.checkFundCode(fundCodeId)) {
            return Constant.FUNDCODEFORMATERROR;
        }
        UserFundCode userFundCode = selectFundCodeIsExistByUserIdAndFundCode(userEmail.getId(), fundCodeId);
        if (userFundCode == null) {
            return Constant.DELFUNDCODENOEXIST;
        }
        return delFundCodeByUserIdAndFundCode(userEmail.getId(), fundCodeId) == 1 ? Constant.DELFUNDCODESUCCESS : Constant.DELFUNDCODEERROR;
    }

    /**
     * 当用户输入停止服务的时候
     *
     * @param receiveMsg
     * @return
     */
    public String stopService(ReceiveMsg receiveMsg) {
//        先查询是否存在此人！
        UserEmail userEmail = selectUserIsExistByUserName(receiveMsg.getFromUserName());
        if (userEmail == null) {
            return Constant.NOUSER;
        }

        return delUserEmailByUserName(receiveMsg.getFromUserName()) == 1 ? Constant.STOPSERVICESUCCESS : Constant.STOPSERVICEERROR;
    }

    /**
     * 当用户查看信息的时候
     *
     * @param receiveMsg
     * @return
     */
    private String userInform(ReceiveMsg receiveMsg) {
        //        先查询是否存在此人！
        UserEmail userEmail = selectUserIsExistByUserName(receiveMsg.getFromUserName());
        if (userEmail == null) {
            return Constant.NOUSER;
        }
        QueryWrapper<UserFundCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userEmail.getId());
        List<UserFundCode> userFundCodes = userFundCodeDao.selectList(queryWrapper);
        String resuktString = "邮箱是： " + userEmail.getEmail() + "\n";
        for (UserFundCode userFundCode : userFundCodes) {
            resuktString = resuktString + "基金： " + userFundCode.getFundCode() + "\n";
        }
        return resuktString;
    }


    public UserEmail selectUserIsExistByUserName(String fromUserName) {
        QueryWrapper<UserEmail> userEmailQuery = new QueryWrapper<>();
//        先查询数据库是否有此人
        userEmailQuery.eq("from_user_name", fromUserName);
        return userEmailDao.selectOne(userEmailQuery);
    }

    public int delUserEmailByUserName(String fromUserName) {
        QueryWrapper<UserEmail> userEmailQuery = new QueryWrapper<>();
//        先查询数据库是否有此人
        userEmailQuery.eq("from_user_name", fromUserName);
        return userEmailDao.delete(userEmailQuery);
    }

    //    查询数据库是否有此人的此基金账号！
    public UserFundCode selectFundCodeIsExistByUserIdAndFundCode(Integer userId, String fundCode) {
        QueryWrapper<UserFundCode> userFundCodeQueryWrapper = new QueryWrapper<>();
//        先查询数据库是否有此人
        userFundCodeQueryWrapper.eq("user_id", userId);
        userFundCodeQueryWrapper.eq("fund_code", fundCode);
        return userFundCodeDao.selectOne(userFundCodeQueryWrapper);
    }

    public int delFundCodeByUserIdAndFundCode(Integer userId, String fundCode) {
        QueryWrapper<UserFundCode> userFundCodeQueryWrapper = new QueryWrapper<>();
//        先查询数据库是否有此人
        userFundCodeQueryWrapper.eq("user_id", userId);
        userFundCodeQueryWrapper.eq("fund_code", fundCode);
        return userFundCodeDao.delete(userFundCodeQueryWrapper);
    }

    //    获取有用的信息
    public String queryMsg(String msg) throws Exception {
// 获取 email 的值
        String newContent = msg.replace(':', '：');
        String[] split = newContent.split("：");
//        若分割出来并不是2，则说明输入有错
        if (split.length != Constant.CONTENTLENGTH) {
            throw new Exception(Constant.FORMATERROR);
        }
        return split[split.length - 1];
    }

}
