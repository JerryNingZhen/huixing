package com.hx.huixing.bean;

/**
 * <br> Description 点赞或评论个数bean
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.bean
 * <br> Date: 2018/7/23
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class CountBean {


    /**
     * code : 0
     * count : null
     * datas : 3
     * msg : 查询成功
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

    public String getCount() {
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
