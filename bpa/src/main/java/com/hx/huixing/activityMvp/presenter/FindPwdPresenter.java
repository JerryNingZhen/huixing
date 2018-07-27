package com.hx.huixing.activityMvp.presenter;

import com.android.base.utils.SystemUtil;
import com.hx.huixing.activityMvp.BasePresenterImpl;
import com.hx.huixing.activityMvp.contract.FindPwdContract;
import com.hx.huixing.activityMvp.model.FindPwdModel;
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
 * <br> Date: 2018/7/20
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class FindPwdPresenter extends BasePresenterImpl<FindPwdContract.FindPwdView>
        implements FindPwdContract.FindPwdPresenter {

    FindPwdContract.FindPwdModel model;

    public FindPwdPresenter(FindPwdContract.FindPwdView view) {
        super(view);
        model = new FindPwdModel();
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
                        view.onFail();
                        view.showToast(str);
                    }
                });
    }

    @Override
    public void getVerCode(String phoneNum, String token) {
        HashMap<String, String> map = new HashMap<>();
        map.put("appid", SystemUtil.getIMEI());
        map.put("phoneNo",phoneNum);
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
}
