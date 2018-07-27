package com.android.base.mvp.model;

import com.android.base.utils.LogUtil;
import com.android.base.utils.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统业务处理
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class SystemModel {

    /**
     * 获取本机外网IP地址,所在区域信息等
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public static void getIpDetail() {
        try {
            Map<String, String> map = new HashMap<>();
            //            String address = "http://ip.taobao.com/service/getIpInfo2.php?ip=myip";// pc浏览器可以使用
            //            String string = "http://pv.sohu.com/cityjson";// var returnCitySN = {"cip": "119.137.84.201", "cid": "440300", "cname": "广东省深圳市"};
            //            map.put("ie", "utf-8");
            //            String string = "http://www.3322.org/dyndns/getip"; // 119.137.84.201
            String string = "http://ip.chinaz.com/getip.aspx";// {ip:'119.137.84.201',address:'广东省深圳市 电信'}
            // HttpBiz.sendGet(string, ConfigServer.SERVER_CONNECT_TIMEOUT, map);
            String result = HttpUtil.getInstance().sendGet(string, map);
            result = result.replace("var returnCitySN = ", "").replace(";", "");
            LogUtil.json(string, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据地址信息 获取经度 纬度
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public static void getLatAndLng() {
        // 经度：" + lat + "    纬度：" + lng
        //"status": 0,
        //"result": {
        //    "location": {
        //        "lng": 111.29010503065494,
        //        "lat": 30.70873810859424
        //    },
        //    "precise": 1,
        //    "confidence": 80,
        //    "level": "地产小区"
        try {
            //            String string = "http://api.map.baidu.com/geocoder/v2/?address=%s&output=json&ak=CidLuM0yn9FVWbw5GqLqpNg5rFlZDloO";
            //            string = String.format(string, "湖北省宜昌市西陵一路亚洲广场B座2507号");
            String string = "http://api.map.baidu.com/geocoder/v2/";
            Map<String, String> map = new HashMap<>();
            map.put("address", "湖北省宜昌市西陵一路亚洲广场B座2507号");
            map.put("output", "json");
            map.put("ak", "CidLuM0yn9FVWbw5GqLqpNg5rFlZDloO");
            String result = HttpUtil.getInstance().sendGet(string, map);
            result = result.replace("地产小区", "null");
            LogUtil.json(string, result);
            //            TestBean testBean = GsonUtil.getInstance().json2Bean(result, TestBean.class);
            //            LogUtil.json(string, testBean.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}