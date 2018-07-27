package com.android.base.activity.mvp.test.activity;

import com.android.base.mvp.baseclass.MvpBaseActivity;
import com.android.base.utils.LogUtil;

/**
 * 需要P层的 Activity 样例
 * <p>
 * <br> Author: 叶青
 * <br> Version: 4.0.0
 * <br> Date: 2018/6/14
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class MvpTestActivity extends MvpBaseActivity<MvpTestActivityView, MvpTestActivityPresenter> {

    public void test() {
        getMvpView().setTest();
        LogUtil.i("555555555555555555");
    }

    @Override
    protected void onResume() {
        super.onResume();
        String str = getMvpView().getTest();
        LogUtil.i(str);
    }
}
