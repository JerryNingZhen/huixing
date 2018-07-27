package com.hx.huixing.activityMvp.presenter;

import com.hx.huixing.activityMvp.BasePresenterImpl;
import com.hx.huixing.activityMvp.contract.LoginContract;
import com.hx.huixing.activityMvp.model.LoginModel;
import com.hx.huixing.bean.UserBean;
import com.hx.huixing.common.http.FilterSubscriber;
import com.hx.huixing.uitl.MD5Coder;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.activityMvp.presenter
 * <br> Date: 2018/7/17
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class LoginPresenter extends BasePresenterImpl<LoginContract.LoginView>
        implements LoginContract.LoginPresenter {

    LoginContract.LoginModel model;

    public LoginPresenter(LoginContract.LoginView view) {
        super(view);
        model = new LoginModel();
    }

    @Override
    public void login(String userName, String userPwd) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("userPwd", MD5Coder.md5(userName + userPwd));
        model.login(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<UserBean>(){

                    @Override
                    public void onNext(UserBean data) {
                        view.getUserInfo(data);
                        view.onSuccess();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        view.showToast(str);
                    }
                });

    }
}
