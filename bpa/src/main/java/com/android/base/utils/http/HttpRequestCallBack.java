package com.android.base.utils.http;

import com.android.base.bean.BaseBean;
import com.android.base.bean.ResponseBean;

/**
 * 网络请求结果回调
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2017/4/26
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public abstract class HttpRequestCallBack {

    /** 网络请求 需要解析成的class类对象 */
    private Class<? extends BaseBean> cls;
    /** 是否为JSONArray true是数组 */
    private boolean isArray = false;
    /** JSON 解析的Key */
    private String listKeyName = "";

    /**
     * 无参数 构造方法
     * <p>
     * <br> Version: 4.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/6/20 11:40
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/6/20 11:40
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public HttpRequestCallBack() {

    }

    /**
     * 无参数 构造方法
     * <p>
     * <br> Version: 4.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/6/20 11:40
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/6/20 11:40
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param cls
     *         需要解析成的class类对象
     */
    public HttpRequestCallBack(Class<? extends BaseBean> cls) {
        this.cls = cls;
    }

    /**
     * 无参数 构造方法
     * <p>
     * <br> Version: 4.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/6/20 11:40
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/6/20 11:40
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param cls
     *         需要解析成的class类对象
     * @param isArray
     *         是否为JSONArray true是数组
     * @param listKeyName
     *         JSON 解析的Key
     */
    public HttpRequestCallBack(Class<? extends BaseBean> cls, boolean isArray, String listKeyName) {
        this.cls = cls;
        this.isArray = isArray;
        this.listKeyName = listKeyName;
    }

    public Class<? extends BaseBean> getCls() {
        return cls;
    }

    public boolean isArray() {
        return isArray;
    }

    public String getListKeyName() {
        return listKeyName;
    }

    /**
     * 请求成功回调
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月19日,下午12:52:26
     * <br> UpdateTime: 2016年4月19日,下午12:52:26
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param result
     *         请求返回数据
     */
    public abstract void onSuccess(ResponseBean result);

    /**
     * 请求失败回调
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月19日,下午12:52:26
     * <br> UpdateTime: 2016年4月19日,下午12:52:26
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param result
     *         请求返回数据
     */
    public abstract void onFail(ResponseBean result);
}
