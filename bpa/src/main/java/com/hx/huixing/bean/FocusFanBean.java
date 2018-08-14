package com.hx.huixing.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <br> Description 关注 和 粉丝bean
 * <br> Author: 谭俊
 * <br> PackageName com.tan.mvpdemo.activityMvp
 * <br> Date: 2018/8/12
 */
public class FocusFanBean implements Serializable {

    /**
     * code : 0
     * count : null
     * datas : [{"followStatus":1,"follower":10,"following":4,"personStringro":"","realName":"神威","userId":"f8585ac0-1e34-4992-9c9f-9b5fa4b85edc","userPic":"http://testapi.blockcomet.com/blockchain/getpic?picture=/data/NewsPaperFront/img/20180517191356885_man3.png"},{"followStatus":1,"follower":4,"following":0,"personStringro":"","realName":"BlockD","userId":"6dd5e6be-8735-4248-9116-14729c396774","userPic":"http://testapi.blockcomet.com/blockchain/getpic?picture=/data/NewsPaperFront/img/20180519142914130_1512382277.png"},{"followStatus":1,"follower":6,"following":4,"personStringro":" 还不错","realName":"熙","userId":"db2bc250-1b48-4add-b0c4-bc849bf79723","userPic":"http://testapi.blockcomet.com/blockchain/getpic?picture=/data/NewsPaperFront/img/20180517120058803_头像1.jpeg"}]
     * msg : 查询成功
     */

    private String code;
    private String count;
    private String msg;
    private ArrayList<DatasBean> datas;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean implements Serializable{
        /**
         * followStatus : 1
         * follower : 10
         * following : 4
         * personStringro : 
         * realName : 神威
         * userId : f8585ac0-1e34-4992-9c9f-9b5fa4b85edc
         * userPic : http://testapi.blockcomet.com/blockchain/getpic?picture=/data/NewsPaperFront/img/20180517191356885_man3.png
         */

        private String followStatus;
        private String follower;
        private String following;
        private String personStringro;
        private String realName;
        private String userId; //用户id
        private String userPic;

        public String getFollowStatus() {
            return followStatus;
        }

        public void setFollowStatus(String followStatus) {
            this.followStatus = followStatus;
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

        public String getPersonStringro() {
            return personStringro;
        }

        public void setPersonStringro(String personStringro) {
            this.personStringro = personStringro;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserPic() {
            return userPic;
        }

        public void setUserPic(String userPic) {
            this.userPic = userPic;
        }
    }
}
