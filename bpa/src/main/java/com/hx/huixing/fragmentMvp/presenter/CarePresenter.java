package com.hx.huixing.fragmentMvp.presenter;

import com.hx.huixing.bean.CareArticleBean;
import com.hx.huixing.common.http.FilterSubscriber;
import com.hx.huixing.fragmentMvp.contract.CareContract;
import com.hx.huixing.fragmentMvp.model.CareModel;

import java.util.HashMap;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.fragmentMvp.presenter
 * <br> Date: 2018/7/18
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class CarePresenter implements CareContract.CarePresenter {

    private CareContract.CareModel model;
    private CareContract.CareView view;

    public CarePresenter(CareContract.CareView view) {
        this.view = view;
        model = new CareModel();
    }

    /**
     *  like为空就是关注，1就是推荐
     *  currentPage 从1开始
     *  类型，4为文章
     * @param currentPage
     * @param pageSize
     * @param like
     * @param loginUserId
     * @param type
     */
    @Override
    public void getArticleList(String currentPage, String pageSize, String like, String loginUserId, String type) {
        HashMap map = new HashMap();
        map.put("currentPage", currentPage);
        map.put("pageSize", pageSize);
        map.put("like", like);
        map.put("loginUser", loginUserId);
        map.put("type", like);
        model.getArticleList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<List<CareArticleBean>>(){

                    @Override
                    public void onNext(List<CareArticleBean> data) {
                        view.getArticle(data);
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
    public void start() {

    }

    @Override
    public void detach() {

    }
}
