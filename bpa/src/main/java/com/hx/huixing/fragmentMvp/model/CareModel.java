package com.hx.huixing.fragmentMvp.model;


import com.hx.huixing.bean.CareArticleBean;
import com.hx.huixing.common.http.RequestServer;
import com.hx.huixing.common.http.ResponseFuncList;
import com.hx.huixing.fragmentMvp.contract.CareContract;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.fragmentMvp.model
 * <br> Date: 2018/7/18
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class CareModel implements CareContract.CareModel {
    @Override
    public Observable<List<CareArticleBean>> getArticleList(Map<String, String> map) {
        return RequestServer.createRetrofit().getArticleList(map)
                .map(new ResponseFuncList<CareArticleBean>());
    }
}
