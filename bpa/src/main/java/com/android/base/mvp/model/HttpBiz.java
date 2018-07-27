package com.android.base.mvp.model;

import android.content.Context;

import com.android.base.BaseApplication;
import com.hx.huixing.R;
import com.android.base.bean.ResponseBean;
import com.android.base.configs.ConfigServer;
import com.android.base.interfaces.OnDownLoadCallBack;
import com.android.base.utils.LogUtil;
import com.android.base.utils.http.HttpUtil;

import java.io.File;
import java.util.Map;

/**
 * 系统业务逻辑
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class HttpBiz extends BaseBiz {

    private static HttpBiz baseBiz;

    public static HttpBiz getInstance() {
        if (baseBiz == null) {
            baseBiz = new HttpBiz();
        }
        return baseBiz;
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
     * @return ResponseBean 网络请求完成返回的数据
     */
    @Override
    public ResponseBean sendPost(String url, Map<String, String> params) {
        try {
            String result = HttpUtil.getInstance().sendPost(url, params);
            LogUtil.json(url, result);
            return getResponseBean(result);
        } catch (Exception e) {
            return getErrorMsg(e);
        }
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
     * @return ResponseBean 网络请求完成返回的数据
     */
    @Override
    public ResponseBean sendGet(String url, Map<String, String> params) {
        try {
            String result = HttpUtil.getInstance().sendGet(url, params);
            LogUtil.json(url, result);
            return getResponseBean(result);
        } catch (Exception e) {
            return getErrorMsg(e);
        }
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
     * @return ResponseBean 网络请求完成返回的数据
     */
    @Override
    public ResponseBean upLoadFile(String url, Map<String, String> params, Map<String, File> files) {
        try {
            String result = HttpUtil.getInstance().uploadFile(url, params, files);
            LogUtil.json(url, result);
            return getResponseBean(result);
        } catch (Exception e) {
            return getErrorMsg(e);
        }
    }

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
     * @return ResponseBean 网络请求完成返回的数据
     */
    @Override
    public ResponseBean downLoadFile(String url, OnDownLoadCallBack downLoadCallable) {
        ResponseBean responseBean = new ResponseBean();
        Context context = BaseApplication.getInstance().getApplicationContext();
        try {
            boolean result = HttpUtil.getInstance().downloadFile(url, downLoadCallable);
            if (result) {
                responseBean.setStatus(ConfigServer.RESPONSE_STATUS_SUCCESS);
                responseBean.setInfo(context.getString(R.string.service_update_hint_download_finish));
            } else {
                responseBean.setStatus(ConfigServer.RESPONSE_STATUS_FAIL);
                responseBean.setInfo(context.getString(R.string.service_update_hint_download_error));
            }
        } catch (Exception e) {
            responseBean = getErrorMsg(e);
        }

        return responseBean;
    }
}