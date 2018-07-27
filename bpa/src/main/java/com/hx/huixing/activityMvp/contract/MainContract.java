package com.hx.huixing.activityMvp.contract;

import com.hx.huixing.activityMvp.BasePresenter;
import com.hx.huixing.activityMvp.BaseView;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.tan.mvpdemo.activityMvp
 * <br> Date: 2018/7/15
 */
public interface MainContract {
    interface Model {
    }

    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter {
        /** 版本更新 */
        void checkVersion();
    }
}
