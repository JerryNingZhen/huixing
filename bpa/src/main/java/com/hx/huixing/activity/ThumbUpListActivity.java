package com.hx.huixing.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.amos.smartrefresh.layout.SmartRefreshLayout;
import com.amos.smartrefresh.layout.api.RefreshLayout;
import com.amos.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.android.base.BaseApplication;
import com.android.base.configs.ConfigServer;
import com.android.base.utils.KeyboardUtil;
import com.android.base.widget.TitleView;
import com.google.gson.Gson;
import com.hx.huixing.R;
import com.hx.huixing.activityMvp.BasePresenter;
import com.hx.huixing.adapter.CommentAdapter;
import com.hx.huixing.adapter.GetThumbAdapter;
import com.hx.huixing.bean.CommentBean;
import com.hx.huixing.bean.ThumbBean;
import com.hx.huixing.common.net.JsonCallBack;
import com.hx.huixing.common.net.RetrofitUtils;
import com.hx.huixing.uitl.ComUtils;
import com.hx.huixing.widget.EmptyView;

import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * <br> Description 点赞列表
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.activity
 * <br> Date: 2018/7/23
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ThumbUpListActivity extends BaseActivity {
    /** 标题 */
    private TitleView titleView;
    /** 为空的时候加载 */
    private EmptyView emptyView;
    /** 刷新 */
    private SmartRefreshLayout refresh_view;
    /** 列表 */
    private ListView list_view;


    /** 回复 */
    public RelativeLayout rlReply;
    public EditText etReplyContent;
    public Button btnSend;

    /** 当前页 */
    private int currentPage = 1;
    private String pageSize = "10";

    private String userId;
    private String password;

    /** 2为点赞的，3为评论的 */
    private String type = "";
    private String title;

    /** 点赞列表 */
    private ArrayList<ThumbBean.DatasBean> thumbs = new ArrayList<>();
    /** 评论列表 */
    private ArrayList<CommentBean.DatasBean> comments = new ArrayList<>();

    /** 点赞 */
    private GetThumbAdapter thumbAdapter;
    /** 评论 */
    private CommentAdapter commentAdapter;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_get_thumb_up;
    }

    @Override
    protected void findViews() {
        titleView = findViewById(R.id.title_view);
        list_view = findViewById(R.id.list_view);
        emptyView = findViewById(R.id.empty_view);
        refresh_view = findViewById(R.id.refresh_view);

        rlReply = findViewById(R.id.rl_reply_content);
        etReplyContent = findViewById(R.id.et_reply_content);
        btnSend = findViewById(R.id.btn_send);
    }

    @Override
    protected void initGetData() {
        //userId = BaseApplication.getInstance().getUserInfoBean().getId();
        //password = BaseApplication.getInstance().getUserInfoBean().getUserPwd();
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");
        title = bundle.getString("title");
        userId = "42e7ce4d-c7ad-476b-8850-1a60bba0e64a";
        password = "32f913adb0951b14373e444c3c4cfcc9";

        if (type.equals("2")){
            thumbAdapter = new GetThumbAdapter(thumbs,this);
            list_view.setAdapter(thumbAdapter);
        }else if (type.equals("3")){
            commentAdapter = new CommentAdapter(comments, this, ThumbUpListActivity.this);
            list_view.setAdapter(commentAdapter);
        }

    }

    @Override
    protected void init() {
        titleView.setLeftBtnImg();
        titleView.setTitle(title);
        getMessage(type);

        emptyView.setInitLoadintLayoutIsDisplay(false);
        emptyView.bindView(list_view);

    }

    @Override
    protected void widgetListener() {
        /** 直接刷新  上拉加载 */
        refresh_view.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage += 1;
                getMessage(type);

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currentPage = 1;
                getMessage(type);

            }
        });

    }

    private void finishRefreshLoad(){
        refresh_view.finishLoadMore();
        refresh_view.finishRefresh();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    private void getMessage(final String type){
        Map<String, String> map = new TreeMap<>();
        map.put("userId", userId);
        map.put("userPwd", password);
        map.put("currentPage", currentPage+"");
        map.put("pageSize", pageSize);
        map.put("type", type);
        RetrofitUtils.getInstance().normalGet(ConfigServer.SERVER_API_URL + ConfigServer.METHOD_GETMESSAGE,
                map, new JsonCallBack() {
                    @Override
                    public void next(String response) {
                        Log.e("tanjun", response);
                        finishRefreshLoad();
                        if (type.equals("2")){ //点赞
                            ArrayList<ThumbBean.DatasBean> list_thumbs = new ArrayList<>();
                            ThumbBean bean = new Gson().fromJson(response, ThumbBean.class);
                            list_thumbs = bean.getDatas();

                            if (list_thumbs.size() > 0){
                                if (currentPage == 1){
                                    thumbs.clear();
                                }
                                thumbs.addAll(list_thumbs);
                            }else if (list_thumbs.size() == 0){
                                if (currentPage == 1){
                                    emptyView.empty();
                                }
                            }
                            thumbAdapter.notifyDataSetChanged();
                            readAllMessage(type);
                        }else if (type.equals("3")){ //评论
                            ArrayList<CommentBean.DatasBean> list_comments = new ArrayList<>();
                            CommentBean bean = new Gson().fromJson(response, CommentBean.class);
                            list_comments = bean.getDatas();

                            if (list_comments.size() > 0){
                                if (currentPage == 1){
                                    comments.clear();
                                }
                                comments.addAll(list_comments);
                            }else if (list_comments.size() == 0){
                                if (currentPage == 1){
                                    emptyView.empty();
                                }
                            }
                            commentAdapter.notifyDataSetChanged();
                            readAllMessage(type);
                        }

                    }

                    @Override
                    public void error(Throwable e) {
                        emptyView.failure();
                        finishRefreshLoad();
                    }

                    @Override
                    public void startLoading() {

                    }

                    @Override
                    public void closeLoading() {

                    }
                });
    }

    /** 清除已读 */
    private void readAllMessage(String type){
        Map<String, String> map = new TreeMap<>();
        map.put("userId", userId);
        map.put("userPwd", password);
        map.put("type", type);
        RetrofitUtils.getInstance().normalGet(ConfigServer.SERVER_API_URL + ConfigServer.METHOD_READALLMESSAGE, map, new JsonCallBack() {
            @Override
            public void next(String response) {
                Log.e("tanjun", response);
            }

            @Override
            public void error(Throwable e) {

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
