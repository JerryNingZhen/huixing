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
public class ArticleAddBean extends BaseBean {

    /** 文章封面图 */
    private String titlePage = "";
    /** 标题 */
    private String textTitle = "";
    /** 内容 */
    private String textContent = "";
    /** 草稿最后更新时间 */
    private String updateTime = "";
    /** 草稿创建时间 */
    private String createTime = "";

    private String draftId = "";              // 草稿id
    //            //  文章标题
    //      //  文章内容
    //            //
    //createTime       //
    //      //

    @Override
    protected void init(JSONObject jSon) {
        titlePage = (JsonUtil.optString(jSon, "titlePage", ""));
        textTitle = (JsonUtil.optString(jSon, "textTitle", ""));
        textContent = (JsonUtil.optString(jSon, "textContent", ""));
        updateTime = (JsonUtil.optString(jSon, "updateTime", ""));
        createTime = (JsonUtil.optString(jSon, "createTime", ""));
        draftId = (JsonUtil.optString(jSon, "draftId", ""));
    }

    public String getTitlePage() {
        return titlePage;
    }

    public void setTitlePage(String titlePage) {
        this.titlePage = titlePage;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDraftId() {
        return draftId;
    }

    public void setDraftId(String draftId) {
        this.draftId = draftId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"titlePage\":\"" + titlePage + "\"" +
                ", \"textTitle\":\"" + textTitle + "\"" +
                ", \"textContent\":\"" + textContent + "\"" +
                ", \"updateTime\":\"" + updateTime + "\"" +
                ", \"createTime\":\"" + createTime + "\"" +
                ", \"draftId\":\"" + draftId + "\"" +
                '}';
    }
}