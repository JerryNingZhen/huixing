package com.android.base.bean;

import com.android.base.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 文章评论列表
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ArticleCommentBean extends BaseBean {


    /**
     * articleProfit :
     * createTime : 2018-06-15 23:16:33
     * creator : ae8f68c5-00e0-48c9-9ed9-c0134ff68795
     * fedbackList : []
     * hasFollowed :
     * id :
     * likeStatus :
     * likes : 0
     * number : 1
     * parentId : 421104c3-edbf-4a40-9755-6104e33fe1b4
     * password :
     * projectBigName :
     * projectId :
     * projectLogo :
     * quote :
     * quotedReviewId :
     * realName : gaochuang
     * review : 0
     * reviewId : 9d1df5de-ef96-43d8-ac5a-c4ba13f085f6
     * score :
     * textContent :
     * textTitle : 已保存，赞赞赞！
     * titlePage :
     * topic :
     * topicId :
     * topicPic :
     * topiclist :
     * type : 3
     * unusefull : 0
     * updateTime :
     * updater :
     * usefull : 0
     * userPic :
     */

    private String articleProfit="";
    private String createTime="";
    private String creator="";
        private String hasFollowed="";//  登录人对文章作者的关注状态
    //    private String id="";
    private String likeStatus="0";
    private String likes="";
    //    private String number="";
    private String parentId=""; //文章id
    //    private String password="";
    //    private String projectBigName="";
    //    private String projectId="";
    //    private String projectLogo="";
    //    private String quote="";
    //    private String quotedReviewId="";
    private String realName="";
    private String review="";
    private String reviewId="";
    //    private String score="";
    //    private String textContent="";
    private String textTitle="";
    //    private String titlePage="";
    //    private String topic="";
    //    private String topicId="";
    //    private String topicPic="";
    //    private String topiclist="";
    private String type="";
    //    private String unusefull="";
    //    private String updateTime="";
    //    private String updater="";
    //    private String usefull="";
    private String userPic=""; //       作者头像
    private ArrayList<ArticleCommentBean> fedbackList = new ArrayList<>();

    //    createTime:"2018-07-19 10:33:49 "                        //评论时间
    //    creator:"42e7ce4d-c7ad-476b-8850-1a60b "      //评论者id
    //    fedbackList:[{},{}]                                             //楼中楼数据，字段与其他一样
    //    parentId:"6ea93bc0-60d4-47ea-9bb"             //文章id
    //    realName："柱子 "                                               //评论者用户名
    //    reviewId：""91c851cc-b364-4fc2-bb3b""       //文章评论id
    //    textTitle:"哈哈哈哈哈哈哈"                                  //评论内容
    //    type:3                                                                  //评论类型为3

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    //    public String getQuote() {
    //        return quote;
    //    }
    //
    //    public void setQuote(String quote) {
    //        this.quote = quote;
    //    }

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

    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
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

    public ArrayList<ArticleCommentBean> getFedbackList() {
        return fedbackList;
    }

    public void setFedbackList(ArrayList<ArticleCommentBean> fedbackList) {
        this.fedbackList = fedbackList;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
//        private String articleProfit="";
        articleProfit = JsonUtil.optString(jSon,"articleProfit","");
        //        private String ="";
        createTime = JsonUtil.optString(jSon,"createTime","");
        //        private String creator="";
        creator = JsonUtil.optString(jSon,"creator","");
        //        private String likeStatus="";
        likeStatus = JsonUtil.optString(jSon,"likeStatus","0");
//        private String likes="";
        likes = JsonUtil.optString(jSon,"likes","");
        //        private String parentId=""; //文章id
        parentId = JsonUtil.optString(jSon,"parentId","");
        //        private String realName="";
        realName = JsonUtil.optString(jSon,"realName","");
        //        private String review="";
        review = JsonUtil.optString(jSon,"review","");
        //        private String reviewId="";
        reviewId = JsonUtil.optString(jSon,"reviewId","");
        //        private String textTitle="";
        textTitle = JsonUtil.optString(jSon,"textTitle","");
        //        private String type="";
        type = JsonUtil.optString(jSon,"type","");
        //        private String userPic=""; //       作者头像
        userPic = JsonUtil.optString(jSon,"typuserPice","");
        //        private List<?> fedbackList;
        userPic = JsonUtil.optString(jSon,"typuserPice","");
        hasFollowed = JsonUtil.optString(jSon,"hasFollowed","");

        fedbackList = (ArrayList<ArticleCommentBean>) BaseBean.toModelList(JsonUtil.optString(jSon,"fedbackList",""),ArticleCommentBean.class);
    }

    public String getHasFollowed() {
        return hasFollowed;
    }

    public void setHasFollowed(String hasFollowed) {
        this.hasFollowed = hasFollowed;
    }
}