package com.android.base.bean;

import com.android.base.utils.code.DES3Coder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用户信息bean
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class UserInfoBean extends BaseBean {

    /** 账号 */
    private String userAccount;
    /** 密码 */
    private String password;
    /** 头像 */
    private String portrait;
    /** 昵称 */
    private String nickname;

    @Override
    protected void init(JSONObject jSon) throws JSONException {

        userAccount = (jSon.optString("userAccount", "-1"));
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getPassword() {
        return DES3Coder.decrypt(password);
    }

    public void setPassword(String password) {
        this.password = DES3Coder.encrypt(password);
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
