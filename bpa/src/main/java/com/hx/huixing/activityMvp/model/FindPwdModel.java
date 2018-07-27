package com.hx.huixing.activityMvp.model;

import com.hx.huixing.activityMvp.contract.FindPwdContract;
import com.hx.huixing.bean.TokenBean;
import com.hx.huixing.common.http.RequestServer;
import com.hx.huixing.common.http.ResponseFunc;

import java.util.Map;

import rx.Observable;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.activityMvp.model
 * <br> Date: 2018/7/20
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class FindPwdModel implements FindPwdContract.FindPwdModel {
    /** 拿token */
    @Override
    public Observable<TokenBean> getToken(Map<String, String> map) {
        return RequestServer.createRetrofit().getToken(map)
                .map(new ResponseFunc<TokenBean>());
    }

    /** 获取验证码 */
    @Override
    public Observable<Object> getVerCode(Map<String, String> map) {
        return RequestServer.createRetrofit().getVerCode(map)
                .map(new ResponseFunc<Object>());
    }
}
