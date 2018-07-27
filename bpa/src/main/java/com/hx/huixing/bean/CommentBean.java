package com.hx.huixing.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * <br> Description 我的消息 评论 bean
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.bean
 * <br> Date: 2018/7/23
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class CommentBean {

    /**
     * code : 0
     * count : 3
     * datas : [{"actionId":"299ceb05-b75c-4ff5-b36a-1f3715eccba5",
     * "content":"22222热额挖到发送到发送到发送到发送到",
     * "createTime":"2018-07-18 21:31:18","creatorPic":null,
     * "messageId":884,"realName":"wuzhao","status":0,"
     * title":"22222热额挖到发送到发送到发送到发送到","type":3,
     * "userId":"328f077d-77d9-40a4-a8da-75db09519d02"}]
     */

    private String code;
    private String count;
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

    public ArrayList<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * actionId : 299ceb05-b75c-4ff5-b36a-1f3715eccba5
         * content : 22222热额挖到发送到发送到发送到发送到
         * createTime : 2018-07-18 21:31:18
         * creatorPic : null
         * messageId : 884
         * realName : wuzhao
         * status : 0
         * title : 22222热额挖到发送到发送到发送到发送到
         * type : 3
         * userId : 328f077d-77d9-40a4-a8da-75db09519d02
         */

        private String actionId;
        private String content;
        private String createTime;
        private String creatorPic;
        private String messageId;
        private String realName;
        private String status;
        private String title;
        private String type;
        private String userId;

        public String getActionId() {
            return actionId;
        }

        public void setActionId(String actionId) {
            this.actionId = actionId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreatorPic() {
            return creatorPic;
        }

        public void setCreatorPic(String creatorPic) {
            this.creatorPic = creatorPic;
        }

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
