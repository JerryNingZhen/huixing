package com.hx.huixing.activityMvp.model;

import com.hx.huixing.activityMvp.contract.LoginContract;
import com.hx.huixing.bean.UserBean;
import com.hx.huixing.common.http.RequestServer;
import com.hx.huixing.common.http.ResponseFunc;

import java.util.Map;

import rx.Observable;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.activityMvp.model
 * <br> Date: 2018/7/17
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class LoginModel implements LoginContract.LoginModel {
    @Override
    public Observable<UserBean> login(Map<String, String> map) {
        return RequestServer.createRetrofit().login(map).map(new ResponseFunc<UserBean>());
    }
}
