package com.hx.huixing.activity;

import android.widget.TextView;

import com.android.base.utils.SystemUtil;
import com.android.base.widget.TitleView;
import com.hx.huixing.R;
import com.hx.huixing.activityMvp.BasePresenter;

/**
 * <br> Description 关于彗星
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.activity
 * <br> Date: 2018/7/24
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class AboutHXActivity extends BaseActivity {

    private TitleView titleView;
    private TextView tv_version;
    private TextView tv_right;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    protected void findViews() {
        titleView = findViewById(R.id.title_view);
        tv_version = findViewById(R.id.tv_version);
        tv_right = findViewById(R.id.tv_right);
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void init() {
        titleView.setLeftBtnImg();
        titleView.setTitle(R.string.about);
        tv_version.setText("V" + SystemUtil.getAppVersionCode());
        tv_right.setText("@2018 huixing.io ALL right reserved");

    }

    @Override
    protected void widgetListener() {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
