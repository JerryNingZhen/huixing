package com.hx.huixing.activity;

import android.support.annotation.NonNull;

import com.amos.smartrefresh.layout.SmartRefreshLayout;
import com.amos.smartrefresh.layout.api.RefreshLayout;
import com.amos.smartrefresh.layout.listener.OnLoadMoreListener;
import com.amos.smartrefresh.layout.listener.OnRefreshListener;
import com.android.base.BaseApplication;
import com.android.base.widget.CustomKeyBoardListView;
import com.android.base.widget.TitleView;
import com.hx.huixing.R;
import com.hx.huixing.activityMvp.BasePresenter;
import com.hx.huixing.adapter.ExchangeAdapter;

/**
 * <br> Description 交易记录
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.activity
 * <br> Date: 2018/8/9
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ExchangeRecordActivity extends BaseActivity {

    private TitleView title_view;

    private CustomKeyBoardListView lv_content;
    private SmartRefreshLayout refresh_view;
    private String id = "";

    private int currentPage = 1;

    ExchangeAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_exchange_record;
    }

    @Override
    protected void findViews() {
        title_view = (TitleView) findViewByIds(R.id.title_view);

        lv_content = (CustomKeyBoardListView) findViewByIds(R.id.lv_content);
        refresh_view = (SmartRefreshLayout) findViewByIds(R.id.refresh_view);
        refresh_view.setEnableAutoLoadMore(false);
    }

    @Override
    protected void initGetData() {
        id = BaseApplication.getInstance().getUserInfoBean().getId();
    }

    @Override
    protected void init() {
        title_view.setLeftBtnImg();
        title_view.setTitle(R.string.exchange_record);
        showProgress(false);
    }

    @Override
    protected void widgetListener() {
        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
            }
        });

        refresh_view.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage = currentPage + 1;
            }
        });
    }

    public void refreshFinish() {
        refresh_view.finishRefresh();
        refresh_view.finishLoadMore();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
