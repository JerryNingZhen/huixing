package com.hx.huixing.activityMvp.presenter;

import com.android.base.utils.PreferencesUtil;
import com.android.base.utils.SystemUtil;
import com.hx.huixing.activityMvp.BasePresenterImpl;
import com.hx.huixing.activityMvp.contract.RegisterContract;
import com.hx.huixing.activityMvp.model.RegisterModel;
import com.hx.huixing.bean.TokenBean;
import com.hx.huixing.common.http.FilterSubscriber;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.activityMvp.presenter
 * <br> Date: 2018/7/17
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class RegisterPresenter extends BasePresenterImpl<RegisterContract.RegisterView>
        implements RegisterContract.RegisterPresenter {

    RegisterContract.RegisterModel model;

    public RegisterPresenter(RegisterContract.RegisterView view) {
        super(view);
        model = new RegisterModel();
    }

    @Override
    public void getToken() {
        Map<String, String> map = new HashMap();
        map.put("appid", SystemUtil.getIMEI());
        model.getToken(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<TokenBean>() {
                    @Override
                    public void onNext(TokenBean tokenBean) {
                        view.getToken(tokenBean.getToken());
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

    @Override
    public void getVerCode(String phoneNo, String token) {
        HashMap<String, String> map = new HashMap<>();
        map.put("appid", SystemUtil.getIMEI());
        map.put("phoneNo",phoneNo);
        map.put("token",token);
        model.getVerCode(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<Object>(){

                    @Override
                    public void onNext(Object data) {
                        view.showToast("验证码已发送");
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

    @Override
    public void registerUser(String realName,String userName, String userPwd, String tel, String phoneCode) {
        HashMap<String, String> map = new HashMap<>();
        map.put("realName", realName);
        map.put("userName", userName);
        map.put("userPwd", userPwd);
        map.put("tel", tel);
        map.put("phoneCode", phoneCode);
        model.registerUser(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        view.onSuccess();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        view.onFail();
                        view.showToast(str);
                    }
                });
    }

    @Override
    public void start() {

    }

    @Override
    public void detach() {

    }
}
