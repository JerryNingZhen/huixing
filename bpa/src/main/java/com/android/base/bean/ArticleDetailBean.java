package com.android.base.bean;

import com.android.base.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.bean
 * <br> Date: 2018/7/17
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ArticleDetailBean extends BaseBean {
    /**
     * articleProfit : 0
     * createTime : 2018-07-23 12:37:17
     * creator : 9251cd61-e146-4429-846c-693a1ba506be
     * fedbackList :
     * hasFollowed :
     * id : null
     * likeStatus :
     * likes : 0
     * number :
     * parentId :
     * password :
     * projectBigName :
     * projectId :
     * projectLogo :
     * quote : null
     * quotedReviewId :
     * realName : 不可开交
     * review : 0
     * reviewId : b0e7d108-30a0-44ef-93be-4c8f068f8cab
     * score : null
     * textContent : 没事的话了吧台风
     * textTitle : ceshhhjjj
     * titlePage : https://goss.vcg.com/20b9d020-7e72-11e8-bef6-79929cace6d6.jpg
     * topic :
     * topicId :
     * topicPic :
     * topiclist : []
     * type : 4
     * unusefull : 0
     * updateTime :
     * updater :
     * usefull : 0
     * userPic :
     */

    private String articleProfit = "";
    private String createTime = "";
    private String creator = "";
    //    private String fedbackList = "";
    private String hasFollowed = "";//  登录人对文章作者的关注状态
    //    private String id = "";
    private String likeStatus = "0";
    private String likes = "";
    //    private String number = "";
    //    private String parentId = "";
    //    private String password = "";
    //    private String projectBigName = "";
    //    private String projectId = "";
    //    private String projectLogo = "";
    //    private String quote = "";
    //    private String quotedReviewId = "";
    private String realName = "";
    private String review = "";
    private String reviewId = "";
    //    private String score = "";
    private String textContent = "";
    private String textTitle = "";
    private String titlePage = "";
    //    private String topic = "";
    //    private String topicId = "";
    //    private String topicPic = "";
    private String type = "";
    //    private String unusefull = "";
    //    private String updateTime = "";
    //    private String updater = "";
    //    private String usefull = "";
    private String userPic = "";
    //    private List<?> topiclist;
    private String personIntro = "";

    //"articleProfit": 6,                                          // 文章收益
    //"createTime": "2018-05-23 13:50:24",     // 文章创建时间
    //"creator": "42e7ce4d-c7ad-",                    //  作者id
    //"hasFollowed": 1,  //  登录人对作者的关注状态 null为无登录人，0为未关注，1为已关注
    //"likeStatus": 1,     //  登录人对文章的点赞状态 null为无登录人，0为未点赞，1为已点赞
    //"likes": 4,                                                    //  文章收到的点赞数
    //"realName": "柱子",                                   //  作者昵称
    //"review": 41,                                               //  文章收到的评论数
    //"reviewId": "6ea93bc0-60d4",                //  文章id
    //"textContent": "<div>hhhh</div>",         //   文章内容
    //"textTitle": "台湾区块链议会联盟成立,     //   文章标题
    //"titlePage": "http:/.../titlePage-4.jpg",    //    文章封面图
    //"type": 4,                                                   //     文章类型
    //"userPic": "http:201805414_0.jpeg"      //       作者头像

    public String getArticleProfit() {
        return articleProfit;
    }

    public void setArticleProfit(String articleProfit) {
        this.articleProfit = articleProfit;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(String likeStatus) {
        this.likeStatus = likeStatus;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public String getTitlePage() {
        return titlePage;
    }

    public void setTitlePage(String titlePage) {
        this.titlePage = titlePage;
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

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        //        private String articleProfit = "";
        articleProfit = JsonUtil.optString(jSon, "articleProfit", "");
        //        private String createTime = "";
        createTime = JsonUtil.optString(jSon, "createTime", "");
        //        private String creator =
        creator = JsonUtil.optString(jSon, "creator", "");
        //        private String hasFollowed = "";//  登录人对文章作者的关注状态
        hasFollowed = JsonUtil.optString(jSon, "hasFollowed", "");

        //        private String likeStatus = "";
        likeStatus = JsonUtil.optString(jSon, "likeStatus", "0");

        //        private String likes = "";
        likes = JsonUtil.optString(jSon, "likes", "");

        //        private String realName = "";
        realName = JsonUtil.optString(jSon, "realName", "");

        //        private String review = "";
        review = JsonUtil.optString(jSon, "review", "");

        //        private String reviewId = "";
        reviewId = JsonUtil.optString(jSon, "reviewId", "");

        //        private String textContent = "";
        textContent = JsonUtil.optString(jSon, "textContent", "");

        //        private String textTitle = "";
        textTitle = JsonUtil.optString(jSon, "textTitle", "");

        //        private String titlePage = "";
        titlePage = JsonUtil.optString(jSon, "titlePage", "");

        //        private String type = "";
        type = JsonUtil.optString(jSon, "type", "");

        //        private String userPic = "";
        userPic = JsonUtil.optString(jSon, "userPic", "");
        personIntro = JsonUtil.optString(jSon, "personIntro", "");

    }

    public String getPersonIntro() {
        return personIntro;
    }

    public void setPersonIntro(String personIntro) {
        this.personIntro = personIntro;
    }
}
