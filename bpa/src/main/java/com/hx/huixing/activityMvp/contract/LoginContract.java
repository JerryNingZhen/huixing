package com.hx.huixing.activityMvp.contract;


import com.hx.huixing.activityMvp.BasePresenter;
import com.hx.huixing.activityMvp.BaseView;
import com.hx.huixing.bean.UserBean;

import java.util.Map;

import rx.Observable;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.activityMvp.contract
 * <br> Date: 2018/7/17
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public interface LoginContract {
    interface LoginModel {
        Observable<UserBean> login(Map<String, String> map);
    }

    interface LoginView extends BaseView{
        void getUserInfo(UserBean userBean);
    }

    interface LoginPresenter extends BasePresenter{
        void login(String userName, String userPwd);
    }
}
