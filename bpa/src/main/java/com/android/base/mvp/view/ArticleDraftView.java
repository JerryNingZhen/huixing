package com.android.base.mvp.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ListView;

import com.amos.smartrefresh.layout.listener.OnLoadMoreListener;
import com.android.base.activity.ArticleDraftActivity;
import com.android.base.adapter.ArticleDraftAdapter;
import com.android.base.bean.ArticleAddBean;
import com.android.base.configs.RequestCode;
import com.android.base.mvp.baseclass.MvpBaseView;
import com.android.base.widget.TitleView;
import com.hx.huixing.R;
import com.amos.smartrefresh.layout.SmartRefreshLayout;
import com.amos.smartrefresh.layout.api.RefreshLayout;
import com.amos.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Collection;


/**
 * 发帖草稿箱 View模块
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ArticleDraftView extends MvpBaseView<ArticleDraftActivity> {

    /** TitleView */
    private TitleView titleview;
    private ListView lv_content;
    private ArticleDraftAdapter adapter;
    public ArrayList<ArticleAddBean> beans = new ArrayList<>();
    private SmartRefreshLayout refresh_view;

    public ArticleDraftView(ArticleDraftActivity baseUI) {
        super(baseUI);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_article_draft;
    }

    @Override
    public void findViews() {
        titleview = findViewByIds(R.id.title_view);
        refresh_view = findViewByIds(R.id.refresh_view);
        lv_content = findViewByIds(R.id.lv_content);

        refresh_view.setEnableAutoLoadMore(false);
    }

    @Override
    public void init(Bundle bundle) {
        if (bundle != null) {
            //            imgPath = bundle.getStringArrayList(ConstantKey.INTENT_KEY_DATAS);
        }
        titleview.setTitle("我的草稿");

        adapter = new ArticleDraftAdapter(baseUI, beans);
        lv_content.setAdapter(adapter);

        baseUI.getMvpPresenter().selectDraftList(1, 10);
    }

    @Override
    public void setViewData(Object object) {
        if (curPage == 1) {
            this.beans.clear();
        }
        this.beans.addAll((Collection<? extends ArticleAddBean>) object);
        adapter.notifyDataSetChanged();
    }

    public void refreshFinish() {
        refresh_view.finishRefresh();
        refresh_view.finishLoadMore();
    }

    /** 当前页 从1开始 */
    private int curPage = 1;

    @Override
    public void widgetListener() {
        titleview.setLeftBtnImg();

        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                curPage = 1;
                baseUI.getMvpPresenter().selectDraftList(curPage, 10);
            }
        });

        refresh_view.setOnLoadMoreListener(new   OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                curPage = curPage + 1;
                baseUI.getMvpPresenter().selectDraftList(curPage, 10);

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RequestCode.REQUEST_CODE_ADD_ARTICLE_DRAFT) {
                baseUI.setResult(Activity.RESULT_OK);
                baseUI.finishActivity();
            }
        }
    }
}