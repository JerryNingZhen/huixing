package com.android.base.bean;

import com.android.base.utils.JsonUtil;

import org.json.JSONObject;

/**
 * 发帖属性类
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class PersonalFocusBean extends BaseBean {

    private String creator = "";
    private String hasFollowed = "";//  登录人对文章作者的关注状态
    private String realName = "";
    private String type = "";
    private String userPic = "";
    private String personIntro = "";

    @Override
    protected void init(JSONObject jSon) {
        creator = (JsonUtil.optString(jSon, "creator", ""));
        hasFollowed = (JsonUtil.optString(jSon, "hasFollowed", ""));
        realName = (JsonUtil.optString(jSon, "realName", ""));
        type = (JsonUtil.optString(jSon, "type", ""));
        userPic = (JsonUtil.optString(jSon, "userPic", ""));
        personIntro = (JsonUtil.optString(jSon, "personIntro", ""));
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getHasFollowed() {
        return hasFollowed;
    }

    public void setHasFollowed(String hasFollowed) {
        this.hasFollowed = hasFollowed;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getPersonIntro() {
        return personIntro;
    }

    public void setPersonIntro(String personIntro) {
        this.personIntro = personIntro;
    }
}