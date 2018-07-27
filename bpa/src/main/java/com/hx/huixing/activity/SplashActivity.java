package com.hx.huixing.activity;

import android.os.Handler;
import android.text.TextUtils;

import com.android.base.configs.ConstantKey;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.PreferencesUtil;
import com.hx.huixing.R;
import com.hx.huixing.activityMvp.BasePresenter;
import com.hx.huixing.bean.UserBean;

/**
 * <br> Description 启动页
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.activity
 * <br> Date: 2018/7/19
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class SplashActivity extends BaseActivity {

    UserBean userBean = null;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initGetData() {
        userBean = (UserBean) PreferencesUtil.get(ConstantKey.SP_KEY_USER_INFO, null);
    }

    @Override
    protected void init() {
        handler.postDelayed(runnable, 1500);
    }

    @Override
    protected void widgetListener() {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (userBean != null && (!TextUtils.isEmpty(userBean.getId()))) {
                IntentUtil.gotoActivityAndFinish(SplashActivity.this, MainActivity.class);
            } else {
                IntentUtil.gotoActivityAndFinish(SplashActivity.this, LoginActivity.class);
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        handler = null;
        super.onDestroy();
    }
}
