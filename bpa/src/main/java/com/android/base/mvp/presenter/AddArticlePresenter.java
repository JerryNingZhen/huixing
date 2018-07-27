package com.android.base.mvp.presenter;

import android.text.TextUtils;

import com.android.base.BaseApplication;
import com.android.base.bean.ArticleAddBean;
import com.android.base.bean.ResponseBean;
import com.android.base.configs.ConfigServer;
import com.android.base.executor.BaseTask;
import com.android.base.executor.RequestExecutor;
import com.android.base.mvp.baseclass.MvpBasePresenter;
import com.android.base.mvp.model.HttpOkBiz;
import com.android.base.mvp.view.AddArticleView;
import com.android.base.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.HashMap;

/**
 * 发帖 Presenter模块
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class AddArticlePresenter extends MvpBasePresenter<AddArticleView> {

    public AddArticlePresenter(AddArticleView view) {
        super(view);
    }

    /**
     * 保存草稿箱
     *
     * @param bean
     *         待发表的文章
     */
    public void addDraft(final ArticleAddBean bean) {
        if (TextUtils.isEmpty(bean.getTitlePage()) &&
                TextUtils.isEmpty(bean.getTextContent()) &&
                TextUtils.isEmpty(bean.getTextTitle())
                ) {
            view.goBack();
            return;
        }
        addArticleUploadImg(bean);
    }

    public void addArticleUploadImg(final ArticleAddBean bean) {

        if (TextUtils.isEmpty(bean.getTitlePage())
                || bean.getTitlePage().startsWith("http")
                ) {
            addDraftServer(bean);
            return;
        }

        if (view == null) {
            LogUtil.e(getClass() + " 页面已经销毁，不在进行任何操作");
            return;
        }

        view.showProgress();
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, "common/upload");
        final HashMap<String, File> files = new HashMap<>();
        files.put("file", new File(bean.getTitlePage()));

        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                return HttpOkBiz.getInstance().upLoadFile(params, files);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                if (view == null) {
                    LogUtil.e(getClass() + " 页面已经销毁，不在进行任何操作");
                    return;
                }
                String url = (String) result.getObject();
                try {
                    JSONArray jsonArray = new JSONArray(url);
                    if (jsonArray.length() > 0) {
                        url = jsonArray.getString(0);
                        bean.setTitlePage(url);
                    }
                    //                bean.setTitlePage("https://goss.vcg.com/20b9d020-7e72-11e8-bef6-79929cace6d6.jpg");
                    addDraftServer(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(ResponseBean result) {
                if (view == null) {
                    LogUtil.e(getClass() + " 页面已经销毁，不在进行任何操作");
                    return;
                }
                view.dismissProgress();
                //                view.setViewData(result.getInfo());
                view.showToast(result.getInfo());
            }
        });

    }


    public void addDraftServer(final ArticleAddBean bean) {
        if (TextUtils.isEmpty(bean.getTitlePage()) &&
                TextUtils.isEmpty(bean.getTextContent()) &&
                TextUtils.isEmpty(bean.getTextTitle())
                ) {
            view.goBack();
            return;
        }
        view.showProgress(false);

        final HashMap<String, String> params = new HashMap<>();
        //        textTitle:      // 文章标题
        //        textConent: // 文章内容
        //        titlePage:     // 文章封面图
        //        creator：     //  登录人id

        params.put("textTitle", bean.getTextTitle());
        params.put("textContent", bean.getTextContent());
        params.put("titlePage", bean.getTitlePage());
        params.put("creator", BaseApplication.getInstance().getUserInfoBean().getId());

        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                if (!TextUtils.isEmpty(bean.getDraftId())) {
                    //编辑草稿
                    params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_EDITDRAFT);
                    params.put("draftId", bean.getDraftId());
                } else {
                    // 新增草稿
                    params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_ADDDRAFT);
                }
                return HttpOkBiz.getInstance().sendPost(params);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                if (view == null) {
                    LogUtil.e(getClass() + " 页面已经销毁，不在进行任何操作");
                    return;
                }
                view.dismissProgress();
                view.showToast(result.getInfo());
                view.goBack();
            }

            @Override
            public void onFail(ResponseBean result) {
                if (view == null) {
                    LogUtil.e(getClass() + " 页面已经销毁，不在进行任何操作");
                    return;
                }
                view.dismissProgress();
                view.showToast(result.getInfo());
            }
        });
    }
}