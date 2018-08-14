package com.hx.huixing.activity;

import android.support.annotation.NonNull;
import android.util.Log;

import com.amos.smartrefresh.layout.SmartRefreshLayout;
import com.amos.smartrefresh.layout.api.RefreshLayout;
import com.amos.smartrefresh.layout.listener.OnLoadMoreListener;
import com.amos.smartrefresh.layout.listener.OnRefreshListener;
import com.android.base.BaseApplication;
import com.android.base.configs.ConfigServer;
import com.android.base.utils.gson.GsonUtil;
import com.android.base.widget.CustomKeyBoardListView;
import com.android.base.widget.TitleView;
import com.hx.huixing.R;
import com.hx.huixing.activityMvp.BasePresenter;
import com.hx.huixing.adapter.ExchangeAdapter;
import com.hx.huixing.bean.SignBean;
import com.hx.huixing.common.net.JsonCallBack;
import com.hx.huixing.common.net.RetrofitUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

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
    private String pageSize = "10";

    ArrayList<SignBean.DatasBean> datasBeans = new ArrayList<>();

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
        queryChainCoinRecord(currentPage);
    }

    @Override
    protected void widgetListener() {
        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                queryChainCoinRecord(currentPage);
            }
        });

        refresh_view.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage = currentPage + 1;
                queryChainCoinRecord(currentPage);
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

    /** 获取交易纪录 */
    private void queryChainCoinRecord(int currentPage){
        Map<String, String> map = new TreeMap<>();
        map.put("currentPage", currentPage+"");
        map.put("pageSize", pageSize);
        map.put("creator", id);
        RetrofitUtils.getInstance().normalGet(ConfigServer.SERVER_API_URL + ConfigServer.METHOD_QUERYCHAINCOINRECORD, map, new JsonCallBack() {
            @Override
            public void next(String response) {
                ArrayList<SignBean.DatasBean> signList = new ArrayList<>();

                refreshFinish();
                dismissProgress();
                SignBean bean = GsonUtil.getInstance().gson.fromJson(response, SignBean.class);
                signList = bean.getDatas();

            }

            @Override
            public void error(Throwable e) {
                refreshFinish();
                dismissProgress();
            }

            @Override
            public void startLoading() {

            }

            @Override
            public void closeLoading() {

            }
        });
    }
}
