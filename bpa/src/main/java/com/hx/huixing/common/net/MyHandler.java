package com.hx.huixing.common.net;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandler extends DefaultHandler {

    private String preTag;

    private User mUser;

    public User getUser() {
        return mUser;
    }

    /**
     * 开始解析一个标签时触发
     */
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if ("users".equals(localName) && mUser == null) {
            mUser = new User();
        }
        preTag = localName;
    }
    /*
	 <release_env>01</release_env>
     <log_swtich>open</log_swtich>
     <updateday> 2016_01_09 18:36</updateday>
	 */

    /**
     * 解析标签中内容
     */
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        // 将标签中内容保存到临时字符串中
        if (mUser != null) {
            String data = new String(ch, start, length);
            if ("log_swtich".equals(preTag)) {
                mUser.setLog_swtich(data);
            } else if ("updateday".equals(preTag)) {
                mUser.setUpdateday(data);
            } else if ("release_env".equals(preTag)) {
                mUser.setRelease_env(data);
            }
        }
    }

    /**
     * 结束解析一个标签时触发
     */
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        preTag = null;
    }

}
