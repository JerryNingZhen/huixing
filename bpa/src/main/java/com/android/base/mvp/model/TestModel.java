package com.android.base.mvp.model;

import com.android.base.bean.ResponseBean;
import com.android.base.bean.UpdateBean;
import com.android.base.configs.ConfigFile;
import com.android.base.configs.ConfigServer;
import com.android.base.interfaces.OnDownLoadCallBack;
import com.android.base.utils.DateUtil;
import com.android.base.utils.code.MD5Coder;
import com.android.base.utils.gson.GsonUtil;
import com.android.base.utils.http.HttpRequestCallBack;

import java.io.File;
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
public class TestModel {

    /**
     * 测试get请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public static ResponseBean testGet() {
        String string = "http://api.map.baidu.com/geocoder/v2/";
        // String string = "http://api.map.baidu.com/geocoder/v2/?address=%s&output=json&ak=CidLuM0yn9FVWbw5GqLqpNg5rFlZDloO";
        Map<String, String> params = new HashMap<>();
        params.put("address", "湖北省宜昌市西陵一路亚洲广场B座2507号");
        params.put("output", "json");
        params.put("ak", "CidLuM0yn9FVWbw5GqLqpNg5rFlZDloO");

        //        ResponseBean result = HttpOkBiz.getInstance().sendGet(params);
        ResponseBean result = HttpOkBiz.getInstance().sendGet(string, params);
        //        if (HttpBiz.checkSuccess(result)) {
        //            // BaseBean.setResponseObjectList(result, AdvertisementBean.class);
        //            // BaseBean.setResponseObjectList(result, AdvertisementBean.class, "list");
        //            BaseBean.setResponseObject(result, UpdateBean.class);
        //            // 数据缓存
        //            PreferencesUtil.putObject(params.get(ConfigServer.SERVER_METHOD_KEY), result);
        //        }
        return result;
    }

    /**
     * 测试post请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public static ResponseBean testPost() {
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, "version/check");
        String postTime = DateUtil.getDate("yyyyMMddHHmmss");
        String sign = MD5Coder.md5Encrypt(postTime + "wFaj81aXawkj8XNOF3GFCUn2q903zN8F").toLowerCase();
        params.put("posttime", postTime);
        params.put("sign", sign);
        params.put("type", "1");
        params.put("verCode", "1");
        //params.put("channel", "57fc786667e58e66ad0017df");

        ResponseBean result = HttpOkBiz.getInstance().sendPost(params);
        //        ResponseBean result = HttpOkBiz.getInstance().sendPost(ConfigServer.SERVER_API_URL+params.get(ConfigServer.SERVER_METHOD_KEY), params);
        if (HttpBiz.checkSuccess(result)) {
            result.setObject(GsonUtil.getInstance().json2Bean((String) result.getObject(), UpdateBean.class));
            //BaseBean.setResponseObject(result, UpdateBean.class);
        }
        return result;
    }

    /**
     * 测试post请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public static ResponseBean testUpLoadFile() {
        //        final String string = "http://61.130.9.88:8081/router/rest/";
        //        String method = "fileService.upload";
        //        final HashMap<String, String> params = new HashMap<>();
        //        params.put("key", "1.0");
        //        params.put("method", method);
        //        params.put("app_key", "10");
        //        params.put("v", "1.0");
        //
        final Map<String, File> Files = new HashMap<>();
        Files.put("image", new File(ConfigFile.PATH_IMAGES + "456.jpg"));

        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, "auth/uploadPicture");
        String postTime = DateUtil.getDate("yyyyMMddHHmmss");
        String sign = MD5Coder.md5Encrypt(postTime + "wFaj81aXawkj8XNOF3GFCUn2q903zN8F").toLowerCase();
        params.put("posttime", postTime);
        params.put("sign", sign);
        params.put("token", "需要替換");
        ResponseBean result = HttpOkBiz.getInstance().upLoadFile(params, Files);
        //        ResponseBean result = HttpOkBiz.getInstance().upLoadFile(ConfigServer.SERVER_API_URL, params, Files);
        //        if (HttpBiz.checkSuccess(result)) {
        //            BaseBean.setResponseObject(result, UpdateBean.class);
        //        }
        return result;
    }

    /**
     * 测试post请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public static ResponseBean testDownLoadFile(String url) {
        return HttpOkBiz.getInstance().downLoadFile(url, null);
    }

    /**
     * 测试post请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public static ResponseBean testPost1() {
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, "version/check");
        String postTime = DateUtil.getDate("yyyyMMddHHmmss");
        //40位的SHA签名
        String sign = MD5Coder.md5Encrypt(postTime + "wFaj81aXawkj8XNOF3GFCUn2q903zN8F").toLowerCase();
        params.put("posttime", postTime);
        params.put("sign", sign);
        params.put("type", "1");
        params.put("verCode", "1");

        ResponseBean result = HttpBiz.getInstance().sendPost(params);
        if (HttpBiz.checkSuccess(result)) {
            result.setObject(GsonUtil.getInstance().json2Bean((String) result.getObject(), UpdateBean.class));
            //BaseBean.setResponseObject(result, UpdateBean.class);
        }
        return result;
    }

    /* TODO *************************************************** 异步 **************************************************/

    /**
     * 测试get请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public static void testGet(final HttpRequestCallBack callBack) {
        String string = "http://api.map.baidu.com/geocoder/v2/";
        // String string = "http://api.map.baidu.com/geocoder/v2/?address=%s&output=json&ak=CidLuM0yn9FVWbw5GqLqpNg5rFlZDloO";
        Map<String, String> map = new HashMap<>();
        map.put("address", "湖北省宜昌市西陵一路亚洲广场B座2507号");
        map.put("output", "json");
        map.put("ak", "CidLuM0yn9FVWbw5GqLqpNg5rFlZDloO");
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
     * 测试post请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public static void testPost(final HttpRequestCallBack callBack) {
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, "version/check");
        String postTime = DateUtil.getDate("yyyyMMddHHmmss");
        String sign = MD5Coder.md5Encrypt(postTime + "wFaj81aXawkj8XNOF3GFCUn2q903zN8F").toLowerCase();
        params.put("posttime", postTime);
        params.put("sign", sign);
        params.put("type", "1");
        params.put("verCode", "1");

        HttpOkBiz.getInstance().sendPost(params, new HttpRequestCallBack() {
            //        HttpOkBiz.getInstance().sendPost(ConfigServer.SERVER_API_URL +params.get(ConfigServer.SERVER_METHOD_KEY), params, new HttpRequestCallBack() {

            @Override
            public void onSuccess(ResponseBean result) {
                if (HttpBiz.checkSuccess(result)) {
                    // 数据缓存
                    // PreferencesUtil.put((ConfigServer.SERVER_METHOD_KEY), result.getObject());
                    result.setObject(GsonUtil.getInstance().json2Bean((String) result.getObject(), UpdateBean.class));
                    // BaseBean.setResponseObjectList(result, AdvertisementBean.class);
                    // BaseBean.setResponseObjectList(result, AdvertisementBean.class, "list");
                    // BaseBean.setResponseObject(result, UpdateBean.class);
                }
                callBack.onSuccess(result);
            }

            @Override
            public void onFail(ResponseBean result) {
                callBack.onFail(result);
            }
        });
    }

    /**
     * 测试downLoadFile
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public static void testDownLoadFile(String url, OnDownLoadCallBack downLoadCallable, HttpRequestCallBack callBack) {
        HttpOkBiz.getInstance().downLoadFile(url, downLoadCallable, callBack);
    }

    /**
     * 测试uploadFile
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public static void testUpLoadFile(Map<String, File> files, final HttpRequestCallBack callBack) {
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, "version/check");
        String postTime = DateUtil.getDate("yyyyMMddHHmmss");
        String sign = MD5Coder.md5Encrypt(postTime + "wFaj81aXawkj8XNOF3GFCUn2q903zN8F").toLowerCase();
        params.put("posttime", postTime);
        params.put("sign", sign);
        params.put("type", "1");
        params.put("verCode", "1");
        HttpOkBiz.getInstance().upLoadFile(params, files, new HttpRequestCallBack() {
            //        HttpOkBiz.getInstance().upLoadFile(ConfigServer.SERVER_API_URL +params.get(ConfigServer.SERVER_METHOD_KEY),  params, files, new HttpRequestCallBack() {
            @Override
            public void onSuccess(ResponseBean result) {
                callBack.onSuccess(result);
            }

            @Override
            public void onFail(ResponseBean result) {
                callBack.onFail(result);
            }
        });
    }

}