package com.android.base.mvp.model;

import com.android.base.utils.http.HttpRequestCallBack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 文章业务处理
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ArticleModel {

    /**
     * 测试uploadFile
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public void articleImage(Map<String, String> params, HashMap<String, File> files, final HttpRequestCallBack callBack) {
        HttpOkBiz.getInstance().upLoadFile(params, files, callBack);
    }

    /**
     * 测试uploadFile
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public void articleAdd(HashMap<String, String> params, final HttpRequestCallBack callBack) {
        HttpOkBiz.getInstance().sendPost(params, callBack);
    }

    /**
     * 草稿箱-查询草稿（列表）
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public void selectDraftList(HashMap<String, String> params, final HttpRequestCallBack callBack) {
        HttpOkBiz.getInstance().sendGet(params, callBack);
    }
    /**
     * 草稿箱-新增草稿
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public void addDraft(HashMap<String, String> params, final HttpRequestCallBack callBack) {
        HttpOkBiz.getInstance().sendPost(params, callBack);
    }

    /**
     * 草稿箱-删除草稿
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public void delectDraft(HashMap<String, String> params, final HttpRequestCallBack callBack) {
        HttpOkBiz.getInstance().sendPost(params, callBack);
    }
    /**
     * 文章详情
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public void quaryArticleDeatail(HashMap<String, String> params, final HttpRequestCallBack callBack) {
        HttpOkBiz.getInstance().sendGet(params, callBack);
    }

}