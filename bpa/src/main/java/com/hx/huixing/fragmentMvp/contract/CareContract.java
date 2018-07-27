package com.hx.huixing.fragmentMvp.contract;


import com.hx.huixing.activityMvp.BasePresenter;
import com.hx.huixing.activityMvp.BaseView;
import com.hx.huixing.bean.CareArticleBean;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.fragmentMvp.contract
 * <br> Date: 2018/7/18
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public interface CareContract {

    interface CareModel {
        Observable<List<CareArticleBean>> getArticleList(Map<String, String> map);
    }

    interface CareView extends BaseView{
        void getArticle(List<CareArticleBean> datas);
    }

    interface CarePresenter extends BasePresenter{
        /** like为空就是关注，1就是推荐 */
        /**
         * currentPage 从1开始
         * @param currentPage
         */
        void getArticleList(String currentPage, String pageSize, String like, String loginUserId, String type);
    }
}
