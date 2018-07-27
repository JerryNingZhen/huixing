package com.hx.huixing.activityMvp.contract;

import com.hx.huixing.activityMvp.BasePresenter;
import com.hx.huixing.activityMvp.BaseView;
import com.hx.huixing.bean.TokenBean;

import java.util.Map;

import rx.Observable;

/**
 * <br> Description 注册
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.activityMvp.contract
 * <br> Date: 2018/7/17
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public interface RegisterContract {
    interface RegisterModel {
        Observable<TokenBean> getToken(Map<String, String> map);
        Observable<Object> getVerCode(Map<String, String> map);
        Observable<Object> registerUser(Map<String,String> map);
    }

    interface RegisterView extends BaseView{
        /** 获取token */
        void getToken(String token);

        void onFail();
    }

    interface RegisterPresenter extends BasePresenter{
        void getToken();
        void getVerCode(String phoneNum, String token);
        void registerUser(String realName, String userName, String userPwd,String tel, String phoneCode);
    }
}
