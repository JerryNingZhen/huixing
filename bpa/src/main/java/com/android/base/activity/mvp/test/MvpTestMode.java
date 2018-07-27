package com.android.base.activity.mvp.test;

import com.android.base.mvp.baseclass.MvpBaseModel;
import com.android.base.bean.ResponseBean;
import com.android.base.mvp.model.HttpOkBiz;
import com.android.base.utils.http.HttpRequestCallBack;

import java.util.HashMap;

/**
 * <p>
 * <br> Author: 叶青
 * <br> Version: 4.0.0
 * <br> Date: 2018/6/14
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class MvpTestMode extends MvpBaseModel {

    /**
     * 1.	删除交易所(app/auth/coin/delUserExchange)
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/6/4 12:19
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/6/4 12:19
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public void loadData(HashMap<String, String> map, final HttpRequestCallBack callBack) {
        String string = "http://api.map.baidu.com/geocoder/v2/";
        //        // String string = "http://api.map.baidu.com/geocoder/v2/?address=%s&output=json&ak=CidLuM0yn9FVWbw5GqLqpNg5rFlZDloO";
        //        Map<String, String> map = new HashMap<>();
        //        map.put("address", "湖北省宜昌市西陵一路亚洲广场B座2507号");
        //        map.put("output", "json");
        //        map.put("ak", "CidLuM0yn9FVWbw5GqLqpNg5rFlZDloO");
        HttpOkBiz.getInstance().sendGet(string, map, new HttpRequestCallBack() {
            //        HttpOkBiz.getInstance().sendGet( map, new HttpRequestCallBack() {
            @Override
            public void onSuccess(ResponseBean resultObj) {
                callBack.onSuccess(resultObj);
            }

            @Override
            public void onFail(ResponseBean result) {
                callBack.onFail(result);
            }
        });
    }

    /**
     * 1.	删除交易所(app/auth/coin/delUserExchange)
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/6/4 12:19
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/6/4 12:19
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public void loadDataPost(HashMap<String, String> map, final HttpRequestCallBack callBack) {
        HttpOkBiz.getInstance().sendPost(map, callBack);
    }
}
