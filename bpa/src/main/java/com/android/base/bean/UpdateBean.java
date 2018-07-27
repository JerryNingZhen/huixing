package com.android.base.bean;

import com.android.base.utils.JsonUtil;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 版本更新属性类
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class UpdateBean extends BaseBean {

    /** 新版版本号 */
    @SerializedName("verCode")
    private String versionCode = "";
    /** 新版版本号 */
    @SerializedName("verName")
    private String versionName = "";
    /** 下载地址 */
    @SerializedName("url")
    private String url = "";
    /** 版本说明 */
    @SerializedName("info")
    private String versionInfo = "";
    /** 强制更新 (0为不强制，1为强制)' */
    @SerializedName("force")
    private String force = "";

    //			"verName": "V1.8.1",//版本名称
    //			"verCode": "10801",//版本号 verCode=0 可忽略
    //			"force": "0",是否强制更新 0=否
    //			"url": "http://www.baidu.com/ ",//app下载地址
    //			"info": "AAAAA"//版本更新描述

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        versionCode = (JsonUtil.optString(jSon, "verCode", ""));
        url = (JsonUtil.optString(jSon, "url", ""));
        versionInfo = (JsonUtil.optString(jSon, "info", ""));
        force = (JsonUtil.optString(jSon, "force", ""));
        versionName = (JsonUtil.optString(jSon, "verName", ""));
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getForce() {
        return force;
    }

    public void setForce(String force) {
        this.force = force;
    }
}