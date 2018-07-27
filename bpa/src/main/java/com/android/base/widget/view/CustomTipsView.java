package com.android.base.widget.view;

import android.content.Context;
import android.util.AttributeSet;

import com.hx.huixing.R;
import com.android.base.utils.NetWorkUtil;


/**
 * 提示页面(进度条、网络异常、无网络、无数据)
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class CustomTipsView extends BaseCustomView {

    /** 等待加载进度条 */
    public CustomProgressBar customProgressBar;
    /** 没有数据 提示页面 */
    public CustomNoDataView customNoDataView;
    /** 网络异常 提示页面 */
    public CustomQueryFailView customQueryFailView;
    /** 没有网络 提示页面 */
    public CustomNoNetWorkView customNoNetWorkView;

    public CustomTipsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomTipsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTipsView(Context context) {
        super(context);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.view_tips_layout;
    }

    @Override
    protected void findViews() {
        customProgressBar = findViewByIds(R.id.progressBar_layout);
        customNoDataView = findViewByIds(R.id.no_data_layout);
        customQueryFailView = findViewByIds(R.id.query_fail_layout);
        customNoNetWorkView = findViewByIds(R.id.no_network_layout);
    }

    @Override
    protected void init() {
        if (!NetWorkUtil.isNetworkAvailable()) {
            setNoNetWorkViewVisibility(true);
        } else {
            setProgressBarVisibility(true);
        }
    }

    @Override
    protected void widgetListener() {

    }

    // *************************** 设置view点击事件 ******************************

    /**
     * 设置没有网络点击重新加载事件
     *
     * @param onClickListener
     *         没有网络点击重新加载事件
     */
    public void setNoNetWorkViewOnclick(OnClickListener onClickListener) {
        if (null != customNoNetWorkView)
            customNoNetWorkView.setOnClickListener(onClickListener);
    }

    /**
     * 设置网络异常点击重新加载事件
     *
     * @param onClickListener
     *         没有数据点击重新加载事件
     */
    public void setQueryFailViewOnclick(OnClickListener onClickListener) {
        if (null != customQueryFailView)
            customQueryFailView.setOnClickListener(onClickListener);
    }

    /**
     * 没有数据点击重新加载事件
     *
     * @param onClickListener
     *         没有数据点击重新加载事件
     */
    public void setNoDataViewOnclick(OnClickListener onClickListener) {
        if (null != customNoDataView)
            customNoDataView.setOnClickListener(onClickListener);
    }

    // *************************** getView ******************************

    public CustomProgressBar getCustomProgressBar() {
        return customProgressBar;
    }

    public CustomNoDataView getCustomNoDataView() {
        return customNoDataView;
    }

    public CustomQueryFailView getCustomQueryFailView() {
        return customQueryFailView;
    }

    public CustomNoNetWorkView getCustomNoNetWorkView() {
        return customNoNetWorkView;
    }

    // *************************** 设置view可见性 ******************************

    /**
     * 设置view可见性
     *
     * @param isVisibility
     *         true 显示
     */
    public void setProgressBarVisibility(boolean isVisibility) {
        if (isVisibility) {
            customProgressBar.setVisibility(VISIBLE);
            customNoDataView.setVisibility(GONE);
            customQueryFailView.setVisibility(GONE);
            customNoNetWorkView.setVisibility(GONE);
        }
    }

    /**
     * 设置view可见性
     *
     * @param isVisibility
     *         true 显示
     */
    public void setNoDataViewVisibility(boolean isVisibility) {
        if (isVisibility) {
            customProgressBar.setVisibility(GONE);
            customNoDataView.setVisibility(VISIBLE);
            customQueryFailView.setVisibility(GONE);
            customNoNetWorkView.setVisibility(GONE);
        }
    }

    /**
     * 设置view可见性
     *
     * @param isVisibility
     *         true 显示
     */
    public void setQueryFailViewVisibility(boolean isVisibility) {
        if (isVisibility) {
            customProgressBar.setVisibility(GONE);
            customNoDataView.setVisibility(GONE);
            customQueryFailView.setVisibility(VISIBLE);
            customNoNetWorkView.setVisibility(GONE);
        }
    }

    /**
     * 设置view可见性
     *
     * @param isVisibility
     *         true 显示
     */
    public void setNoNetWorkViewVisibility(boolean isVisibility) {
        if (isVisibility) {
            customProgressBar.setVisibility(GONE);
            customNoDataView.setVisibility(GONE);
            customQueryFailView.setVisibility(GONE);
            customNoNetWorkView.setVisibility(VISIBLE);
        }
    }
}