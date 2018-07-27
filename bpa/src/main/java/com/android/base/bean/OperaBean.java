package com.android.base.bean;

import com.android.base.utils.DateUtil;
import com.android.base.utils.SystemUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 数据统计bean
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class OperaBean extends BaseBean {

    private static final long serialVersionUID = -8829950916372045248L;
    /** 操作时间 */
    private String time;
    /** 机型LG D802 */
    private String phoneModel;
    /** 系统版本4.4.0 */
    private String romVersion;
    /** 应用版本1000 */
    private String versionCode;
    /** 系统类型android or ios */
    private String systemType;

    public OperaBean() {
        setTime(DateUtil.getDate());// 设置操作时间
        setPhoneModel(android.os.Build.MODEL);// 设置机型
        setRomVersion(android.os.Build.VERSION.RELEASE);// 设置系统版本
        setSystemType("Android");// 设置系统类型
        setVersionCode(SystemUtil.getAppVersionCode() + "");// 设置应用版本
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getRomVersion() {
        return romVersion;
    }

    public void setRomVersion(String romVersion) {
        this.romVersion = romVersion;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }
}