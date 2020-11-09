package com.codeshuai.thinwater.util;

import jdk.internal.dynalink.beans.StaticClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：codeshuai
 * @date ：Created in 2020/11/8 17:40
 */
public class RegexUtil {

    /**
     * 验证邮箱地址是否正确
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }


    /**
     * 基金代码是否正确
     *
     * @param fundCode
     * @return
     */
    public static boolean checkFundCode(String fundCode) {
        boolean flag = false;
        try {
            String check = "\\d{6}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(fundCode);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    /**
     * 获取字符串最外围的大括号！
     *
     * @param index
     * @return
     */
    public static String queryOutBraces(String index) {
        boolean flag = false;
        try {
            String check = "(?=\\{).*(?>\\})";
            Pattern p = Pattern.compile(check);
            Matcher m = p.matcher(index);
            String jsonString = null;
            while (m.find()) {
                jsonString = m.group();
            }
            return jsonString;
        } catch (Exception e) {
            flag = false;
        }
        return null;
    }
}
