# 微信公众号与基金的开发

<hr>

## 使用说明


>截至为：2020-11-10为止！
关注公众号：细水弯月

会提示消息：

>请输入:
邮箱、
新增基金、
删除基金、
查看信息、
停止服务、
参考示例：
邮箱：xxxxxx@163.com、
新增基金：460005、
删除基金：460005;
邮箱第一次则表示新增、第二次表示替换;

当第一次使用时需要输入"邮箱：xxxxxx@163.com",来绑定邮箱，若输入其他文字，则会提示需要先输入邮箱；
输入邮箱后可以新增基金，输入“新增基金：460005”后，即代表了已关注此基金；
“删除基金：460005”即代表取关该基金；
“停止服务”：代表了删除用户的所有信息；

当用户设置了邮箱，且关注了一些基金，则系统会在每周一至周五的凌晨23点左右推送该基金的相关信息，该时间可以修改！

信息内容如：
> 1: 华泰柏瑞价值增长混合A  净值估算：5.0578  今日涨幅：1.10%
2: 广发中证全指金融地产联接A  净值估算：1.1786  今日涨幅：1.92%
3: 广发安泽短债债券C  净值估算：1.0571  今日涨幅：-0.02%
4: 天弘创业板ETF联接基金C  净值估算：1.0910  今日涨幅：2.81%
>该数据来源为天天基金，以实际为准！(公众号：细水弯月)


## 安装

下载源码后，自启服务！
方法一：
将源码里面的配置：数据库、邮箱、包括修改发送邮件的时间等信息填写完成后，打成架包，发在服务器上，在微信公众号的开发模式下，基本配置里面的服务器配置需要根据你服务器的地址（路径为：/weixin/msg）、代码中的令牌（在常量类中TOKEN）完善！小写加密方式设置为明文即可！
**服务器地址URL一定是80端口**
**服务器地址URL一定是80端口**
**服务器地址URL一定是80端口**
这是微信公众平台开发的一个最麻烦的地方，项目起的端口是在5002端口，因此你可能需要将5002端口转发到80端口！

方法二：
如果你只是自己在本地跑服务，但是微信公众平台里面的服务器配置需要80端口，你可以内网映射到本地。
1、先将项目配置完成，启动起来！
2、使用内网映射，这里推荐netapp，免费的，购买一个免费的隧道，在netapp配置隧道的属性，将本地端口改成5002，根据authtoken在本地启动起来，netapp会提供一个网址，该网址便可访问你本地的5002端口了！
