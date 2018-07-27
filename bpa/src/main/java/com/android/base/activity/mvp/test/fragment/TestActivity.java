package com.android.base.activity.mvp.test.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.hx.huixing.R;
import com.android.base.mvp.baseclass.BaseActivity;
import com.android.base.mvp.baseclass.BaseView;
import com.android.base.configs.ConstantKey;
import com.android.base.utils.LogUtil;

/**
 * 不需要P层的 Activity 样例
 * <p>
 * <br> Author: 叶青
 * <br> Version: 4.0.0
 * <br> Date: 2018/6/14
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class TestActivity extends BaseActivity implements BaseView {

    LinearLayout view_parent;
    View titleview;
    public MvpTestFragment mvpTestFragment;


    @Override
    public void initVP() {
        mvpView = this;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    public void findViews() {
        view_parent = findViewByIds(R.id.view_parent);
        titleview = findViewByIds(R.id.title_view);
    }

    @Override
    public void init(Bundle bundle) {
        titleview.setVisibility(View.GONE);
        view_parent.removeAllViews();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mvpTestFragment = new MvpTestFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ConstantKey.INTENT_KEY_TYPE, "type");
        mvpTestFragment.setArguments(arguments);
        transaction.add(R.id.view_parent, mvpTestFragment);
        transaction.commit();
    }

    @Override
    public void widgetListener() {
    }

    @Override
    public void setViewData(Object object) {

    }

    public void test() {
        LogUtil.i("66666666666666666");
    }

}
