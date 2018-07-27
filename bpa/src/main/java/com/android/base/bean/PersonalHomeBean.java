package com.android.base.bean;

import com.android.base.utils.JsonUtil;

import org.json.JSONObject;

/**
 * 个人主页
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class PersonalHomeBean extends BaseBean {

    private String draftId = "";              // 草稿id
    private String addTime = ""; //            "2018-04-04 13:00:04",    // 注册时间
    private String articleCount = ""; //        47,                                      // 帖子数
    private String follow = ""; //                   false,      // 登录人是否关注该用户 true为已关注，false为未关注，null为无登录人
    private String follower = ""; //                8,                                       //  粉丝数
    private String following = ""; //               5,                                      // 关注人数
    private String followingProject = ""; //   1,                                      //  关注的项目数
    private String followingTopic = ""; //      0,                                     //  关注的专题数
    private String id = ""; // "                        231f8827-69b8-4d7e-8979-3d01f895fddb",     // 用户id
    private String isLock = ""; //                   0,                                     // 账号是否被禁用，1为被禁用，0为正常
    private String level = ""; // 3,
    private String personIntro = ""; //          "这个人很懒什么都没留下...",    // 个人简介
    private String realName = ""; //             "一九",                             // 昵称
    private String tel = ""; //                         "18959267805",            // 手机号
    private String userName = ""; //           "18959267805",             // 登录账号
    private String userPic = ""; //                "",                                     // 用户头像地址

    //"addTime":            "2018-04-04 13:00:04",    // 注册时间
    //"articleCount":        47,                                      // 帖子数
    //"follow":                   false,      // 登录人是否关注该用户 true为已关注，false为未关注，null为无登录人
    //"follower":                8,                                       //  粉丝数
    //"following":               5,                                      // 关注人数
    //"followingProject":   1,                                      //  关注的项目数
    //"followingTopic":      0,                                     //  关注的专题数
    //"id": "                        231f8827-69b8-4d7e-8979-3d01f895fddb",     // 用户id
    //"isLock":                   0,                                     // 账号是否被禁用，1为被禁用，0为正常
    //"level": 3,
    //"personIntro":          "这个人很懒什么都没留下...",    // 个人简介
    //"realName":             "一九",                             // 昵称
    //"tel":                         "18959267805",            // 手机号
    //"userName":           "18959267805",             // 登录账号
    //"userPic":                "",                                     // 用户头像地址

    @Override
    protected void init(JSONObject jSon) {
        addTime = (JsonUtil.optString(jSon, "addTime", ""));
        articleCount = (JsonUtil.optString(jSon, "articleCount", ""));
        follow = (JsonUtil.optString(jSon, "follow", ""));
        follower = (JsonUtil.optString(jSon, "follower", ""));
        following = (JsonUtil.optString(jSon, "following", ""));
        followingProject = (JsonUtil.optString(jSon, "followingProject", ""));
        followingTopic = (JsonUtil.optString(jSon, "followingTopic", ""));
        id = (JsonUtil.optString(jSon, "id", ""));
        isLock = (JsonUtil.optString(jSon, "isLock", ""));
        level = (JsonUtil.optString(jSon, "level", ""));
        personIntro = (JsonUtil.optString(jSon, "personIntro", ""));
        realName = (JsonUtil.optString(jSon, "realName", ""));
        tel = (JsonUtil.optString(jSon, "tel", ""));
        userName = (JsonUtil.optString(jSon, "userName", ""));
        userPic = (JsonUtil.optString(jSon, "userPic", ""));

        if(follow.equals("true")){
            follow = "1";
        }else{
            follow = "0";
        }
    }

    public String getDraftId() {
        return draftId;
    }

    public void setDraftId(String draftId) {
        this.draftId = draftId;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(String articleCount) {
        this.articleCount = articleCount;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getFollowingProject() {
        return followingProject;
    }

    public void setFollowingProject(String followingProject) {
        this.followingProject = followingProject;
    }

    public String getFollowingTopic() {
        return followingTopic;
    }

    public void setFollowingTopic(String followingTopic) {
        this.followingTopic = followingTopic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPersonIntro() {
        return personIntro;
    }

    public void setPersonIntro(String personIntro) {
        this.personIntro = personIntro;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }
}