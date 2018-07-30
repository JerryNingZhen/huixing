package com.hx.huixing.bean;

import java.io.Serializable;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.bean
 * <br> Date: 2018/7/25
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class MyInfoBean implements Serializable{

    /**
     * code : 0
     * count : null
     * datas : {"addTime":"2018-07-18 18:45:38","articleCount":40,"deptId":0,"follow":false,"follower":0,"following":0,"followingProject":0,"followingTopic":0,"id":"9251cd61-e146-4429-846c-693a1ba506be","isLock":0,"level":3,"personIntro":"她离开了","phoneCode":null,"realName":"5句图","roleId":8,"tel":"15338705596","userName":"15338705596","userPic":null,"userPwd":null,"userSalt":"s2n71ej6bzryxr2p","userType":null}
     * msg : 查询成功
     */

    private String code;
    private String count;
    private DatasBean datas;
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

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DatasBean implements Serializable{
        /**
         * addTime : 2018-07-18 18:45:38
         * articleCount : 40
         * deptId : 0
         * follow : false
         * follower : 0
         * following : 0
         * followingProject : 0
         * followingTopic : 0
         * id : 9251cd61-e146-4429-846c-693a1ba506be
         * isLock : 0
         * level : 3
         * personIntro : 她离开了
         * phoneCode : null
         * realName : 5句图
         * roleId : 8
         * tel : 15338705596
         * userName : 15338705596
         * userPic : null
         * userPwd : null
         * userSalt : s2n71ej6bzryxr2p
         * userType : null
         */

        private String addTime;
        private String articleCount;
        private String deptId;
        private boolean follow;
        private String follower;
        private String following;
        private String followingProject;
        private String followingTopic;
        private String id;
        private String isLock;
        private String level;
        private String personIntro;
        private String phoneCode;
        private String realName;
        private String roleId;
        private String tel;
        private String userName;
        private String userPic;
        private String userPwd;
        private String userSalt;
        private String userType;

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

        public String getDeptId() {
            return deptId;
        }

        public void setDeptId(String deptId) {
            this.deptId = deptId;
        }

        public boolean isFollow() {
            return follow;
        }

        public void setFollow(boolean follow) {
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
    }
}
