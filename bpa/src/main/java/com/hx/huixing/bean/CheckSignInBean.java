package com.hx.huixing.bean;

/**
 * <br> Description 是否已签到
 * <br> Author: 谭俊
 * <br> PackageName com.tan.mvpdemo.activityMvp
 * <br> Date: 2018/7/24
 */
public class CheckSignInBean {


    /**
     * code : -1
     * count : null
     * datas : 2018-07-24
     * msg : 本日已签到
     */

    private String code;
    private String count;
    private String datas;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
