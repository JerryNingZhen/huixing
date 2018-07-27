package com.hx.huixing.activityMvp.contract;

import com.hx.huixing.activityMvp.BasePresenter;
import com.hx.huixing.activityMvp.BaseView;
import com.hx.huixing.bean.TokenBean;

import java.util.Map;

import rx.Observable;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.activityMvp.contract
 * <br> Date: 2018/7/20
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public interface FindPwdContract {

    interface FindPwdModel {
        Observable<TokenBean> getToken(Map<String, String> map);
        Observable<Object> getVerCode(Map<String, String> map);

    }

    interface FindPwdView extends BaseView{
        /** 获取token */
        void getToken(String token);
        void onFail();
    }

    interface FindPwdPresenter extends BasePresenter{
        void getToken();
        void getVerCode(String phoneNum, String token);

    }
}
