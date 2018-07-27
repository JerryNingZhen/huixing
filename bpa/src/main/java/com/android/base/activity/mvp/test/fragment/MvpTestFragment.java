package com.android.base.activity.mvp.test.fragment;

import com.android.base.mvp.baseclass.MvpBaseFragment;
import com.android.base.utils.LogUtil;

/**
 * 需要P层的  Fragment 样例
 * <p>
 * <br> Author: 叶青
 * <br> Version: 4.0.0
 * <br> Date: 2018/6/14
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class MvpTestFragment extends MvpBaseFragment<MvpTestFragmentView, MvpTestFragmentPresenter> {

    public void test() {
        LogUtil.i("777777777777777777");
    }
}
