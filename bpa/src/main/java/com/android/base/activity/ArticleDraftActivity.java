package com.android.base.activity;

import android.content.Intent;

import com.android.base.mvp.baseclass.MvpBaseActivity;
import com.android.base.mvp.presenter.ArticleDraftPresenter;
import com.android.base.mvp.view.ArticleDraftView;

/**
 * 发帖草稿箱界面
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ArticleDraftActivity extends MvpBaseActivity<ArticleDraftView, ArticleDraftPresenter> {

    @Override
    protected void onRestart() {
        super.onRestart();
        getMvpPresenter().selectDraftList(1, getMvpView().beans.size());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getMvpView().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}