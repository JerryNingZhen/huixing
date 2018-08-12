package com.android.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.amos.smartrefresh.layout.SmartRefreshLayout;
import com.amos.smartrefresh.layout.api.RefreshLayout;
import com.amos.smartrefresh.layout.listener.OnLoadMoreListener;
import com.amos.smartrefresh.layout.listener.OnRefreshListener;
import com.android.base.BaseApplication;
import com.android.base.adapter.PersonalFocusAdapter;
import com.android.base.configs.ConfigServer;
import com.android.base.configs.ConstantKey;
import com.android.base.mvp.baseclass.BaseActivity;
import com.android.base.mvp.baseclass.BaseView;
import com.android.base.utils.gson.GsonUtil;
import com.android.base.widget.CustomKeyBoardListView;
import com.android.base.widget.TitleView;
import com.hx.huixing.R;
import com.hx.huixing.bean.FocusFanBean;
import com.hx.huixing.common.net.JsonCallBack;
import com.hx.huixing.common.net.RetrofitUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * 个人关注人数列表
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class PersonalFocusActivity extends BaseActivity implements BaseView {

    private TitleView title_view;

    private CustomKeyBoardListView lv_content;
    private SmartRefreshLayout refresh_view;
    private String id = "";

    private int currentPage = 1;
    //private ArrayList<PersonalFocusBean> dataBeans = new ArrayList<>();
    private ArrayList<FocusFanBean.DatasBean> dataBeans = new ArrayList<>();
    private PersonalFocusAdapter adapter;

    @Override
    public void initVP() {
        mvpView = this;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_personal_article;
    }

    @Override
    public void findViews() {
        title_view = findViewByIds(R.id.title_view);

        lv_content = findViewByIds(R.id.lv_content);
        refresh_view = findViewByIds(R.id.refresh_view);
        refresh_view.setEnableAutoLoadMore(false);
    }

    @Override
    public void init(Bundle bundle) {
        //id = BaseApplication.getInstance().getUserInfoBean().getId();
        if (bundle != null) {// 要查询的用户id
            id = bundle.getString(ConstantKey.INTENT_KEY_ID, "");
            //id = "f8585ac0-1e34-4992-9c9f-9b5fa4b85edc";
        }

        // TODO: 2018/8/12  
        //adapter = new PersonalFocusAdapter(this, dataBeans);
        lv_content.setAdapter(adapter);

        title_view.setTitle("关注");
        showProgress(false);
        quaryReviewByUser(10);
    }

    @Override
    public void widgetListener() {
        title_view.setLeftBtnImg();

        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                quaryReviewByUser(10);
            }
        });

        refresh_view.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage = currentPage + 1;
                quaryReviewByUser(10);
            }
        });
    }

    public void refreshFinish() {
        refresh_view.finishRefresh();
        refresh_view.finishLoadMore();
    }

    @Override
    public void setViewData(Object object) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 他的主页---文章列表
     */
//    private void quaryReviewByUser(int pageSize) {
//
//        final HashMap<String, String> params = new HashMap<>();
//        //            currentPage: 1         // 分页信息，从1开始
//        //            pageSize: 12            // 分页信息
//        //            creator: 42e7ce4d  // 要查询的人的id
//        //            type: 4                     // 类型，查询文章类型给4
//        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_QUARYREVIEWBYUSER);
//        params.put("creator", id);
//        params.put("currentPage", currentPage + "");
//        params.put("pageSize", pageSize + "");
//        params.put("type", "4");
//        //        params.put("loginUser", BaseApplication.getInstance().getUserInfoBean().getId());
//
//        RequestExecutor.addTask(new BaseTask() {
//            @Override
//            public ResponseBean sendRequest() {
//                return HttpOkBiz.getInstance().sendGet(params);
//            }
//
//            @Override
//            public void onSuccess(ResponseBean result) {
//                BaseBean.setResponseObjectList(result, ArticleDetailBean.class, "");
//                ArrayList<ArticleDetailBean> beans = (ArrayList<ArticleDetailBean>) result.getObject();
//                //                //                ArrayList<ArticleCommentBean> beans = GsonUtil.getInstance().gson.fromJson((String) result.getObject(), new TypeToken<List<ArticleCommentBean>>() {
//                //                //                }.getType());
//                if (currentPage == 1) {
//                    dataBeans.clear();
//                }
//                for (int i = 0; i < beans.size(); i++) {
//                    PersonalFocusBean bean = new PersonalFocusBean();
//                    bean.setCreator(beans.get(i).getCreator());
//                    bean.setHasFollowed(beans.get(i).getHasFollowed());
//                    bean.setPersonIntro(beans.get(i).getPersonIntro());
//                    bean.setType(beans.get(i).getType());
//                    bean.setRealName(beans.get(i).getRealName());
//                    bean.setUserPic(beans.get(i).getUserPic());
//                    dataBeans.add(bean);
//                }
//                //                dataBeans.addAll(beans);
//                adapter.notifyDataSetChanged();
//                dismissProgress();
//                refreshFinish();
//            }
//
//            @Override
//            public void onFail(ResponseBean result) {
//                dismissProgress();
//                showToast(result.getInfo());
//                refreshFinish();
//            }
//        });
//    }

    @Override
    protected void onRestart() {
        super.onRestart();
        quaryReviewByUser(dataBeans.size());
    }

    /** type 1是关注的人 4是粉丝 */
    private void quaryReviewByUser(int pageSize){
        Map<String, String> map = new TreeMap<>();
        map.put("currentPage", currentPage + "");
        map.put("pageSize", pageSize + "");
        map.put("creator", id); //要查看的用户
        map.put("loginUser",id);
        map.put("type", "1");

        RetrofitUtils.getInstance().normalGet(ConfigServer.SERVER_API_URL + ConfigServer.METHOD_QUARYATTENTIONDATA, map, new JsonCallBack() {
            @Override
            public void next(String response) {
                ArrayList<FocusFanBean.DatasBean> focusList = new ArrayList<>();

                FocusFanBean bean = GsonUtil.getInstance().gson.fromJson(response, FocusFanBean.class);
                focusList = bean.getDatas();
                if (focusList.size() > 0){
                    if (currentPage == 1){
                        dataBeans.clear();
                    }
                    dataBeans.addAll(focusList);
                }
                adapter.notifyDataSetChanged();
                dismissProgress();
                refreshFinish();
            }

            @Override
            public void error(Throwable e) {
                dismissProgress();
                refreshFinish();
            }

            @Override
            public void startLoading() {

            }

            @Override
            public void closeLoading() {

            }
        });
    }


}