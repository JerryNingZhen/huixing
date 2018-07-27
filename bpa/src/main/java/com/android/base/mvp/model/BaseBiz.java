package com.android.base.mvp.model;

import android.content.Context;
import android.text.TextUtils;

import com.android.base.BaseApplication;
import com.hx.huixing.R;
import com.android.base.bean.ResponseBean;
import com.android.base.configs.ConfigServer;
import com.android.base.executor.Cancel;
import com.android.base.interfaces.OnDownLoadCallBack;
import com.android.base.utils.NetWorkUtil;
import com.android.base.utils.SystemUtil;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 系统业务逻辑
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public abstract class BaseBiz {

    public static HashMap<String, String> getBaseParams() {
        return getBaseParams(null);

    }

    /**
     * 添加post请求的固定参数
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月31日,上午5:00:05
     * <br> UpdateTime: 2016年12月31日,上午5:00:05
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param method
     *         METHOD
     *
     * @return 含固定参数的HashMap
     */
    public static HashMap<String, String> getBaseParams(String method) {
        HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_LANGUAGE_KEY, SystemUtil.getAppLanguage());
        if (!TextUtils.isEmpty(method)) {
            params.put(ConfigServer.SERVER_METHOD_KEY, method);
        }
        return params;

    }

    /**
     * 为map添加数据，如果value值为空则不传递次参数
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-8,下午2:10:30
     * <br> UpdateTime: 2016-11-8,下午2:10:30
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param params
     *         键值对对象
     * @param key
     *         键
     * @param value
     *         值
     */
    public static void mapPutValue(HashMap<String, String> params, String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            params.put(key, value);
        } else {
            params.put(key, "");
        }
    }

    /**
     * 校验网络请求是否成功
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-25,上午10:34:23
     * <br> UpdateTime: 2016-11-25,上午10:34:23
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param responseBean
     *         网络请求返回的初始数据
     *
     * @return true操作成功 false操作不成功
     */
    public static boolean checkSuccess(ResponseBean responseBean) {
        return responseBean != null && (responseBean.getStatus().equals(ConfigServer.RESPONSE_STATUS_SUCCESS));
    }

    /**
     * 发送get请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月20日,下午4:57:16
     * <br> UpdateTime: 2016年5月20日,下午4:57:16
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param params
     *         参数
     *
     * @return 请求后返回的接口数据
     */
    public ResponseBean sendPost(HashMap<String, String> params) {
        if (params.containsKey(ConfigServer.SERVER_METHOD_KEY)) {
            String method = params.get(ConfigServer.SERVER_METHOD_KEY);
            params.remove(ConfigServer.SERVER_METHOD_KEY);
            return sendPost(ConfigServer.SERVER_API_URL + method, params);
        }
        return sendPost(ConfigServer.SERVER_API_URL, params);
    }

    /**
     * 发送post请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月31日,上午5:01:35
     * <br> UpdateTime: 2016年12月31日,上午5:01:35
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         接口地址
     * @param params
     *         接口参数
     *
     * @return 请求后返回的解析数据
     */
    public abstract ResponseBean sendPost(String url, Map<String, String> params);

    /**
     * 发送get请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月20日,下午4:57:16
     * <br> UpdateTime: 2016年5月20日,下午4:57:16
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param params
     *         参数
     *
     * @return 请求后返回的接口数据
     */
    public ResponseBean sendGet(Map<String, String> params) {
        if (params.containsKey(ConfigServer.SERVER_METHOD_KEY)) {
            String method = params.get(ConfigServer.SERVER_METHOD_KEY);
            params.remove(ConfigServer.SERVER_METHOD_KEY);
            return sendGet(ConfigServer.SERVER_API_URL + method, params);
        }
        return sendGet(ConfigServer.SERVER_API_URL, params);
    }

    /**
     * 发送get请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月20日,下午4:57:16
     * <br> UpdateTime: 2016年5月20日,下午4:57:16
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         服务器地址
     * @param params
     *         参数
     *
     * @return 请求后返回的接口数据
     */
    public abstract ResponseBean sendGet(String url, Map<String, String> params);


    /**
     * 发送get请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月20日,下午4:57:16
     * <br> UpdateTime: 2016年5月20日,下午4:57:16
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param params
     *         参数
     *
     * @return 请求后返回的接口数据
     */
    public ResponseBean upLoadFile(Map<String, String> params, Map<String, File> files) {
        if (params.containsKey(ConfigServer.SERVER_METHOD_KEY)) {
            String method = params.get(ConfigServer.SERVER_METHOD_KEY);
            params.remove(ConfigServer.SERVER_METHOD_KEY);
            return upLoadFile(ConfigServer.SERVER_API_URL + method, params, files);
        }
        return upLoadFile(ConfigServer.SERVER_API_URL, params, files);
    }

    /**
     * 文件上传
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月20日,下午4:57:16
     * <br> UpdateTime: 2016年5月20日,下午4:57:16
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         服务器地址
     * @param params
     *         附带参数集合
     * @param files
     *         文件集合，支持多文件上传
     *
     * @return 请求后返回的接口数据
     */
    public abstract ResponseBean upLoadFile(String url, Map<String, String> params, Map<String, File> files);

    /**
     * 文件下载
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月20日,下午4:57:16
     * <br> UpdateTime: 2016年5月20日,下午4:57:16
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         下载路径.
     * @param downLoadCallable
     *         监听线程回调接口
     *
     * @return 请求后返回的接口数据
     */
    public abstract ResponseBean downLoadFile(String url, OnDownLoadCallBack downLoadCallable);

    /**
     * 网络处理返回数据
     * <p>
     * <br> Version: 4.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/4/26 15:07
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/4/26 15:07
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param e
     *         Throwable
     */
    public static ResponseBean getErrorMsg(Throwable e) {
        e.printStackTrace();
        Context context = BaseApplication.getInstance().getApplicationContext();
        if (!NetWorkUtil.isNetworkAvailable()) {
            return new ResponseBean(context.getString(R.string.exception_net_work_io_code), context.getString(R.string.exception_net_work_io_message), "");
        } else if (e instanceof SocketTimeoutException || e instanceof TimeoutException || e instanceof ConnectException) {
            return new ResponseBean(context.getString(R.string.exception_net_work_time_out_code), context.getString(R.string.exception_net_work_time_out_message), "");
        } else if (e instanceof Cancel.CancelException) {
            return new ResponseBean(context.getString(R.string.exception_cancel_code), context.getString(R.string.exception_cancel_message), "");
        } else if (e instanceof IOException) {
            return new ResponseBean(context.getString(R.string.exception_local_other_code), context.getString(R.string.exception_local_other_message), "");
        } else {
            return new ResponseBean(context.getString(R.string.exception_local_other_code), context.getString(R.string.exception_local_other_message), "");
        }
    }

    /**
     * 网络处理返回数据
     * <p>
     * <br> Version: 4.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/4/26 15:07
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/4/26 15:07
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param result
     *         网络请求结果数据处理
     */
    public static ResponseBean getResponseBean(String result) throws Exception {
        Context context = BaseApplication.getInstance().getApplicationContext();
        ResponseBean responseBean = new ResponseBean();
        JSONObject jsonObject = new JSONObject(result);
        responseBean.setStatus(jsonObject.optString(ConfigServer.RESULT_JSON_STATUS, context.getString(R.string.exception_local_other_code)));
        responseBean.setInfo(jsonObject.optString(ConfigServer.RESULT_JSON_INFO, context.getString(R.string.exception_local_other_message)));
        responseBean.setObject(jsonObject.optString(ConfigServer.RESULT_JSON_DATA, ""));
        return responseBean;
    }

    /**
     * 生成请求头Headers
     *
     * @return Headers
     */
    public static Map<String, String> getHeaders() {
        Map<String, String> headersParams = new HashMap<>();
        headersParams.put("Content-Type", "application/json; charset=utf-8");
        headersParams.put("platform", "Android");
        //        headersParams.put("model", android.os.Build.MODEL);//"型号: "
        //        headersParams.put("brand", android.os.Build.BRAND);//"android系统定制商: "
        //        headersParams.put("release", android.os.Build.VERSION.RELEASE);//"android系统版本:6.0 "
        //        headersParams.put("sdk_int", android.os.Build.VERSION.SDK_INT+"");//"android系统版本:23 "
        //        headersParams.put("appVersion", SystemUtil.getAppVersionCode() + "");
        //headersParams.put("channel", SystemUtil.getChannelName());
        //        String message = "本机参数========>PU架构: " + android.os.Build.CPU_ABI + "\n" +
        //                "型号: " + android.os.Build.MODEL + "\n" +
        //                "硬件厂商: " + android.os.Build.MANUFACTURER + "\n" +
        //                "系统版本: " + android.os.Build.VERSION.SDK_INT + "\n" +
        //                "android系统定制商: " + android.os.Build.BRAND + "\n" +
        //                "手机厂商: " + android.os.Build.PRODUCT + "\n";
        //        LogUtil.i(message);
        return headersParams;
    }
}