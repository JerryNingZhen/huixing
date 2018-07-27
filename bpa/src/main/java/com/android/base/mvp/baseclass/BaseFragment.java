package com.android.base.mvp.baseclass;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.base.configs.BroadcastFilters;
import com.android.base.utils.LogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 基类Fragment
 * <p>
 * 所有的Fragment必须继承自此类
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2018/6/14
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
//public abstract class BaseFragment<V extends BaseView> extends Fragment implements BaseUI<V> {
//    /** MvpBaseView */
//    protected V mvpView;
public abstract class BaseFragment extends Fragment implements BaseUI {

    /** MvpBaseView */
    protected BaseView mvpView;
    /** 父视图 */
    protected View viewParent;
    /** 广播过滤器 */
    protected IntentFilter filter;
    /** 广播接收器 */
    protected BroadcastReceiver receiver;
    /** ButterKnife 解绑 */
    protected Unbinder unbinder;

    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(Bundle savedInstanceState) {
        // Activity中的onCreate方法执行时调用
        LogUtil.w("onCreate");
        super.onCreate(savedInstanceState);

        initVP();
        onCreateStart();
        registerReceiver();
    }

    /**
     * 初始化 MVP 模式中的，P层和V层
     * <p>
     * 在 {@link android.app.Fragment#onCreate(Bundle)} 内调用
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/6/17 1:27
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/6/17 1:27
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public abstract void initVP();

    @Override
    public void onCreateStart() {
        if (mvpView != null) {
            switchLanguage();
            viewParent = initContentView(mvpView.getContentViewId());
            unbinder = ButterKnife.bind(this, viewParent);// 兼容ButterKnife开发者
            mvpView.findViews();
            mvpView.init(getArguments());
            mvpView.widgetListener();
        } else {
            LogUtil.e("baseView == null,调用onCreateStart(),请先初始化baseView!!!");
        }
    }

    @Override
    public View initContentView(int layoutId) {
        return View.inflate(getActivity(), layoutId, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Activity中的onCreate方法执行时调用
        LogUtil.w("onCreateView");
        if (viewParent.getParent() != null) {
            ((ViewGroup) viewParent.getParent()).removeView(viewParent);
        }
        return viewParent;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends View> T findViewByIds(int id) {
        return (T) viewParent.findViewById(id);
    }

    @Override
    public void initIntentFilter() {
        //   添加广播过滤器，在此添加广播过滤器之后，所有的子类都将收到该广播
        filter = new IntentFilter();
        filter.addAction(BroadcastFilters.ACTION_CHANGE_LANGUAGE);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION); // 网络变化广播
    }

    @Override
    public void registerReceiver() {
        initIntentFilter();
        receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && !TextUtils.isEmpty(intent.getAction())) {
                    BaseFragment.this.onReceive(intent);
                }
            }
        };
        getActivity().registerReceiver(receiver, filter);
    }

    @Override
    public void onReceive(Intent intent) {
        if (intent != null && !TextUtils.isEmpty(intent.getAction())) {
            //   父类集中处理共同的请求
            if (intent.getAction().equals(BroadcastFilters.ACTION_CHANGE_LANGUAGE)) {// 修改语言
                onCreateStart();
            }
        }
    }


    /**
     * 切换语言
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年9月24日,下午2:52:16
     * <br> UpdateTime: 2016年9月24日,下午2:52:16
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    protected void switchLanguage() {
        ((BaseActivity) getActivity()).switchLanguage();
    }

    //TODO ********************************************等待对话框
    @Override
    public void showProgress() {
        ((BaseActivity) getActivity()).showProgress();
    }

    @Override
    public void showProgress(boolean cancelable) {
        ((BaseActivity) getActivity()).showProgress(cancelable);
    }

    @Override
    public void showProgress(String processMsg, boolean cancelable) {
        ((BaseActivity) getActivity()).showProgress(processMsg, cancelable);
    }

    @Override
    public void dismissProgress() {
        ((BaseActivity) getActivity()).dismissProgress();
    }

    @Override
    public void showToast(String content) {
        ((BaseActivity) getActivity()).showToast(content);
    }

    //    @Override
    //    public V getMvpView() {
    public BaseView getMvpView() {
        return mvpView;
    }

    @Override
    public void onAttach(Context context) {
        // fragment已经关联到activity
        LogUtil.w("onAttach");
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // Activity中的onCreate方法执行完后调用
        LogUtil.w("onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        // Activity中的onStart方法执行时调用
        LogUtil.w("onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        // Activity中的onResume方法执行时调用
        LogUtil.w("onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        // Activity中的onPause方法执行时调用
        LogUtil.w("onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        // Activity中的onStop方法执行时调用
        LogUtil.w("onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        // Activity中的onDestroy方法执行时调用
        LogUtil.w("onDestroyView");
        unbinder.unbind();
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {
        // Activity中的onDestroy方法执行时调用
        LogUtil.w("onDestroy");
        getActivity().unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        // Activity中的onDestroy方法执行时调用
        LogUtil.w("onDetach");
        super.onDetach();
    }
}