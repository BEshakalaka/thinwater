package com.codeshuai.thinwater.util;

import com.codeshuai.thinwater.pojo.MessageText;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * xml解析工具类
 *
 * @author ：codeshuai
 * @date ：Created in 2020/11/8 2:26
 */
public class MessageUtil {
    /**
     * 将xml变成Map
     *
     * @param request
     * @return
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        SAXReader reader = new SAXReader();

        InputStream in = null;
        try {
            in = request.getInputStream();
            Document doc = reader.read(in);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element element : list) {
                map.put(element.getName(), element.getText());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 将发送消息封装成对应的xml格式
     */
    public String messageToxml(MessageText message) {
        XStream xstream = new XStream();
        xstream.alias("xml", message.getClass());
        return xstream.toXML(message);
    }

    /**
     * 封装发送消息对象,封装时，需要将调换发送者和接收者的关系
     *
     * @param FromUserName
     * @param ToUserName
     */
    public String initMessage(String FromUserName, String ToUserName, String Content) {
        MessageText text = new MessageText();
        text.setToUserName(FromUserName);
        text.setFromUserName(ToUserName);
        text.setContent(Content);
        text.setCreateTime(System.currentTimeMillis());
        text.setMsgType("text");
        return this.messageToxml(text);
    }
}
