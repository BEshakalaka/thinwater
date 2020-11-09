package com.codeshuai.thinwater.pojo;

/**
 * 常量
 *
 * @author ：codeshuai
 * @date ：Created in 2020/11/8 12:55
 */
public class Constant {

    public final static String TOKEN = "codeshuai";

    public final static String INFORMFUNDDATAURL = "http://fundgz.1234567.com.cn/js/000000.js?rt=1463558676006";
    public final static String REPLACEFUNDCODE = "000000";

    public final static Integer CONTENTLENGTH = 2;
    public final static String NOUSER = "你未设置邮箱，请先设置邮箱!";

    public final static String TEXTERROR = "暂不支持纯文字以外的输入;\n";
    public final static String ERROR = "请输入:\n邮箱、\n新增基金、\n删除基金、\n查看信息、\n停止服务、\n" +
            "参考示例：\n邮箱：xxxxxx@163.com、\n新增基金：460005、\n删除基金：460005;\n\n\n" +
            "邮箱第一次则表示新增、第二次表示替换;";

    public final static String FORMATERROR = "请按照提示的格式正确输入! 请加上冒号";

    public final static String EMAIL = "邮箱";
    public final static String EMAILINSERTSUCCESS = "邮箱设置成功!";
    public final static String EMAILINSERTERROR = "邮箱设置有误，请重新输入!";
    public final static String EMAILFORMATERROR = "邮箱格式错误,请重新输入!";

    public final static String ADDFUNDCODE = "新增基金";
    public final static String FUNDCODEFORMATERROR = "基金的格式为6位纯数字，请重新输入!";
    public final static String FUNDCODEEXIST = "该基金代码已存在!";
    public final static String FUNDCODEINSERTSUCCESS = "该基金代码设置成功!";
    public final static String FUNDCODEINSERTERROR = "该基金代码设置有误，请重新输入!";


    public final static String DELFUNDCODE = "删除基金";
    public final static String DELFUNDCODENOEXIST = "该基金代码不存在!";
    public final static String DELFUNDCODESUCCESS = "该基金代码已删除!";
    public final static String DELFUNDCODEERROR = "该基金代码删除有误，请重新输入!";

    public final static String USERINFORM = "查看信息";


    public final static String STOPSERVICE = "停止服务";
    public final static String STOPSERVICESUCCESS = "已暂停服务!";
    public final static String STOPSERVICEERROR = "停止服务失败!";

    public final static String EMAILSUBJECT = "-基金情况";


    public final static String ENDMSG = "该数据来源为天天基金，以实际为准！(公众号：细水弯月)";

}
