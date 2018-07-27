package com.android.base.mvp.presenter;

import com.android.base.BaseApplication;
import com.android.base.bean.ArticleAddBean;
import com.android.base.bean.BaseBean;
import com.android.base.bean.ResponseBean;
import com.android.base.configs.ConfigServer;
import com.android.base.executor.BaseTask;
import com.android.base.executor.RequestExecutor;
import com.android.base.mvp.baseclass.MvpBasePresenter;
import com.android.base.mvp.model.HttpOkBiz;
import com.android.base.mvp.view.ArticleDraftView;
import com.android.base.utils.LogUtil;

import java.util.HashMap;

/**
 * 发帖草稿箱 Presenter模块
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ArticleDraftPresenter extends MvpBasePresenter<ArticleDraftView> {

    //    private ArticleModel model;

    public ArticleDraftPresenter(ArticleDraftView view) {
        super(view);
        //        model = new ArticleModel();
    }

    /**
     * 获取草稿数据
     */
    public void selectDraftList(int currentPage,int pageSize) {
        view.showProgress(false);


        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_SELECTDRAFTLIST);
        //        currentPage // 分页信息，从1开始
        //        pageSize:    // 分页信息
        //        creator：     //  登录人id
        params.put("currentPage", currentPage + "");
        params.put("pageSize", pageSize+"");
        params.put("creator", BaseApplication.getInstance().getUserInfoBean().getId());


        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                ResponseBean responseBean = HttpOkBiz.getInstance().sendGet(params);
                BaseBean.setResponseObjectList(responseBean, ArticleAddBean.class, "");
                return responseBean;
            }

            @Override
            public void onSuccess(ResponseBean result) {
                if (view == null) {
                    LogUtil.e(getClass() + " 页面已经销毁，不在进行任何操作");
                    return;
                }
                view.dismissProgress();
                view.setViewData(result.getObject());
                view.refreshFinish();
            }

            @Override
            public void onFail(ResponseBean result) {
                if (view == null) {
                    LogUtil.e(getClass() + " 页面已经销毁，不在进行任何操作");
                    return;
                }
                view.dismissProgress();
                view.showToast(result.getInfo());
                view.refreshFinish();
            }
        });

    }
}