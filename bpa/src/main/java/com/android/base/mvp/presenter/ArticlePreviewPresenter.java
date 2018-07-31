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
import com.android.base.mvp.view.ArticlePreviewView;
import com.android.base.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.HashMap;

/**
 * 发帖预览 Presenter模块
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ArticlePreviewPresenter extends MvpBasePresenter<ArticlePreviewView> {

    //    private ArticleModel model;

    public ArticlePreviewPresenter(ArticlePreviewView view) {
        super(view);
        //        model = new ArticleModel();
    }

    public void addArticleUploadImg(final ArticleAddBean bean) {

        if (TextUtils.isEmpty(bean.getTitlePage())
                || bean.getTitlePage().startsWith("http")
                ) {
            addArticle(bean);
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
                    addArticle(bean);
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

    public void addArticle(final ArticleAddBean bean) {
        if (view == null) {
            LogUtil.e(getClass() + " 页面已经销毁，不在进行任何操作");
            return;
        }

        view.showProgress();

        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_ADDREVIEW);
        params.put("textTitle", bean.getTextTitle());
        params.put("textContent", bean.getTextContent());
        params.put("type", "4");
        params.put("creator", BaseApplication.getInstance().getUserInfoBean().getId());
        params.put("password", BaseApplication.getInstance().getUserInfoBean().getUserPwd());
        params.put("titlePage", bean.getTitlePage());


        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                if (!TextUtils.isEmpty(bean.getReviewId())) {
                    params.put("reviewId", bean.getReviewId());
                    params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_UPDATAREVIEW);
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
                if (!TextUtils.isEmpty(bean.getReviewId())) {// 编辑文章
                    view.setViewData(bean.getReviewId());
                } else {
                    String id = (String) result.getObject();
                    view.setViewData(id);
                }
                view.showToast(result.getInfo());
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

    /**
     * 草稿箱- 删除草稿
     *
     * @param bean
     *         待发表的文章
     */
    public void delectDraft(final ArticleAddBean bean) {

        if (TextUtils.isEmpty(bean.getDraftId())) {
            view.goBack();
            return;
        }

        view.showProgress(false);

        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_DELECTDRAFT);
        // 草稿id
        // 草稿作者

        params.put("draftId", bean.getDraftId());
        params.put("creator", BaseApplication.getInstance().getUserInfoBean().getId());

        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                return HttpOkBiz.getInstance().sendPost(params);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                if (view == null) {
                    LogUtil.e(getClass() + " 页面已经销毁，不在进行任何操作");
                    return;
                }
                view.dismissProgress();
                //                view.setViewData(result.getObject());
                view.goBack();
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
}