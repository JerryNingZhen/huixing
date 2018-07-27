package com.hx.huixing.bean;

import com.android.base.bean.BaseBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.bean
 * <br> Date: 2018/7/17
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class UserBean extends BaseBean implements Serializable{
    private String  addTime="";
    private String  deptId=""; 
    private String  follow="";
    private String  follower=""; 
    private String  following=""; 
    private String  followingProject="";
    private String  followingTopic="";
    private String  id="";
    private String  isLock="";
    private String  level="";
    private String  personIntro=""; 
    private String  phoneCode="";
    private String  realName="";
    private String  roleId=""; 
    private String  tel ="";
    private String  userName ="";
    private String  userPic=""; 
    private String  userPwd="";
    private String  userSalt="";
    private String  userType="";


    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
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

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserSalt() {
        return userSalt;
    }

    public void setUserSalt(String userSalt) {
        this.userSalt = userSalt;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {

    }
}
