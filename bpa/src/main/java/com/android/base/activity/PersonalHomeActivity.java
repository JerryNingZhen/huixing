package com.android.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amos.smartrefresh.layout.SmartRefreshLayout;
import com.amos.smartrefresh.layout.api.RefreshLayout;
import com.amos.smartrefresh.layout.listener.OnLoadMoreListener;
import com.amos.smartrefresh.layout.listener.OnRefreshListener;
import com.android.base.BaseApplication;
import com.android.base.adapter.PersonalHomeArticleAdapter;
import com.android.base.bean.ArticleDetailBean;
import com.android.base.bean.BaseBean;
import com.android.base.bean.PersonalHomeBean;
import com.android.base.bean.ResponseBean;
import com.android.base.configs.ConfigServer;
import com.android.base.configs.ConstantKey;
import com.android.base.executor.BaseTask;
import com.android.base.executor.RequestExecutor;
import com.android.base.mvp.baseclass.BaseActivity;
import com.android.base.mvp.baseclass.BaseView;
import com.android.base.mvp.model.HttpOkBiz;
import com.android.base.utils.LogUtil;
import com.android.base.utils.glide.CropTransformation;
import com.android.base.utils.glide.GlideUtil;
import com.android.base.widget.CustomKeyBoardListView;
import com.android.base.widget.TitleView;
import com.bumptech.glide.request.RequestOptions;
import com.hx.huixing.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 个人主页
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class PersonalHomeActivity extends BaseActivity implements BaseView {

    private TitleView title_view;

    private CustomKeyBoardListView lv_content;

    private Button btn_add_care;
    private TextView txt_name;
    private TextView txt_detail;
    private ImageView round_image;
    private TextView txt_article;
    private TextView txt_gz;
    private TextView txt_fs;
    private TextView txt_level;

    private SmartRefreshLayout refresh_view;
    private String id = "";

    private int currentPage = 1;
    private ArrayList<ArticleDetailBean> dataBeans = new ArrayList<>();
    private PersonalHomeArticleAdapter adapter;
    private PersonalHomeBean bean;

    @Override
    public void initVP() {
        mvpView = this;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_personal_home;
    }

    @Override
    public void findViews() {
        title_view = findViewByIds(R.id.title_view);

        lv_content = findViewByIds(R.id.lv_content);
        refresh_view = findViewByIds(R.id.refresh_view);
        refresh_view.setEnableAutoLoadMore(false);

        initHeaderView();
    }

    private void initHeaderView() {
        View view = View.inflate(this, R.layout.activity_personal_home_header, null);

        btn_add_care = view.findViewById(R.id.btn_add_care);
        txt_name = view.findViewById(R.id.txt_name);
        txt_detail = view.findViewById(R.id.txt_detail);
        round_image = view.findViewById(R.id.round_image);
        txt_article = view.findViewById(R.id.txt_article);
        txt_gz = view.findViewById(R.id.txt_gz);
        txt_fs = view.findViewById(R.id.txt_fs);
        txt_level = view.findViewById(R.id.txt_level);

        lv_content.addHeaderView(view);
    }


    @Override
    public void init(Bundle bundle) {
        if (bundle != null) {// 要查询的用户id
            id = bundle.getString(ConstantKey.INTENT_KEY_ID, "");
        }

        adapter = new PersonalHomeArticleAdapter(this, dataBeans);
        lv_content.setAdapter(adapter);

        title_view.setTitle("个人主页");
        showProgress(false);
        quaryusers();
        quaryReviewByUser(10);
    }

    @Override
    public void widgetListener() {
        title_view.setLeftBtnImg();

        //        title_view.setRightBtnImg(R.drawable.icon_share, new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                ShareBean shareBean = new ShareBean();
        //
        //                shareBean.setPhotoUrl("http://www.mob.com/assets/images/logo-51fcf38a.png");
        //                shareBean.setTextContent("setTextContent");
        //                shareBean.setTitle("setTitle");
        //                shareBean.setContentUrl("www.baidu.com");
        //                shareBean.setContentId("");
        //                shareBean.setContentType("");
        //                String[] nameItems = getResources().getStringArray(R.array.share_types);
        //                Integer[] resItems = new Integer[]{R.drawable.share_wechat, //
        //                        R.drawable.share_wechatmoments, //
        //                        R.drawable.share_sina
        //                        //                                , //
        //                        //                                R.drawable.share_sina, //
        //                        //                                R.drawable.share_sina, //
        //                        //                                R.drawable.share_sina, //
        //                        //                                R.drawable.share_sina, //
        //                        //                                R.drawable.share_sina, //
        //                        //                                R.drawable.share_sina, //
        //                        //                                R.drawable.share_sina, //
        //                        //                                R.drawable.share_sina, //
        //                        //                                R.drawable.share_sina, //
        //                        //                                R.drawable.share_qq //
        //                };
        //                ShareDialogUtil popupWindowUtil = new ShareDialogUtil(PersonalHomeActivity.this, shareBean, nameItems, resItems);
        //                popupWindowUtil.show(shareBean, Gravity.BOTTOM);
        //            }
        //        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.txt_zan://
                        break;
                    case R.id.btn_add_care:// 关注
                        attent();
                        break;

                    default:// 默认
                        break;
                }
            }
        };

        btn_add_care.setOnClickListener(onClickListener);

        lv_content.setOnSizeChangedListener(new CustomKeyBoardListView.onSizeChangedListener() {

            @Override
            public void onChanged(boolean showKeyboard) {
                // TODO Auto-generated method stub
                if (showKeyboard) {
                    LogUtil.i("show keyboard true");
                } else {
                    LogUtil.i("show keyboard false");
                }
            }
        });
        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                quaryusers();
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
        if (object instanceof PersonalHomeBean) {
            txt_name.setText(bean.getRealName());
            txt_detail.setText("个人简介：" + bean.getPersonIntro());
            if (bean.getFollow().equals("1")) {
                btn_add_care.setText("已关注");
            } else {
                btn_add_care.setText("关注");
            }

            if (BaseApplication.getInstance().getUserInfoBean().getId().equals(id)) {
                btn_add_care.setVisibility(View.GONE);
            }
            txt_article.setText(bean.getArticleCount());
            txt_gz.setText(bean.getFollowing());
            txt_fs.setText(bean.getFollow());
            txt_level.setText("lv." + bean.getLevel());

            RequestOptions options = GlideUtil.getRequestOptions();
            options.transforms(new CropTransformation(CropTransformation.CropType.CIRCLE));//// 多重变换
            GlideUtil.loadImage(this, bean.getUserPic(), round_image, options.error(R.drawable.default_portrait_grey));
            // GlideUtil.loadImage(this, "https://goss.vcg.com/20b9d020-7e72-11e8-bef6-79929cace6d6.jpg", round_image, options);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 我的主页 & 他的主页
     */
    private void quaryusers() {

        final HashMap<String, String> params = new HashMap<>();
        //userId            // 要查询的用户id
        //loginUser   // 当前登录人id，用于判断是否关注TA
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_QUARYUSERS);
        params.put("userId", id);
        params.put("loginUser", BaseApplication.getInstance().getUserInfoBean().getId());

        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                return HttpOkBiz.getInstance().sendGet(params);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                BaseBean.setResponseObject(result, PersonalHomeBean.class);
                bean = (PersonalHomeBean) result.getObject();
                setViewData(bean);
                //                dismissProgress();
                //                refreshFinish();
            }

            @Override
            public void onFail(ResponseBean result) {
                //                dismissProgress();
                showToast(result.getInfo());
                //                refreshFinish();
            }
        });
    }

    /**
     * 他的主页---文章列表
     */
    private void quaryReviewByUser(int pageSize) {

        final HashMap<String, String> params = new HashMap<>();
        //            currentPage: 1         // 分页信息，从1开始
        //            pageSize: 12            // 分页信息
        //            creator: 42e7ce4d  // 要查询的人的id
        //            type: 4                     // 类型，查询文章类型给4
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_QUARYREVIEWBYUSER);
        params.put("creator", id);
        params.put("currentPage", currentPage + "");
        params.put("pageSize", pageSize + "");
        params.put("type", "4");
        //        params.put("loginUser", BaseApplication.getInstance().getUserInfoBean().getId());

        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                return HttpOkBiz.getInstance().sendGet(params);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                BaseBean.setResponseObjectList(result, ArticleDetailBean.class, "");
                ArrayList<ArticleDetailBean> beans = (ArrayList<ArticleDetailBean>) result.getObject();
                //                //                ArrayList<ArticleCommentBean> beans = GsonUtil.getInstance().gson.fromJson((String) result.getObject(), new TypeToken<List<ArticleCommentBean>>() {
                //                //                }.getType());
                if (currentPage == 1) {
                    dataBeans.clear();
                }
                dataBeans.addAll(beans);
                adapter.notifyDataSetChanged();
                dismissProgress();
                refreshFinish();
            }

            @Override
            public void onFail(ResponseBean result) {
                dismissProgress();
                showToast(result.getInfo());
                refreshFinish();
            }
        });
    }

    /**
     * 关注
     */
    private void attent() {
        if (bean == null) {
            return;
        }
        showProgress(false);
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_ATTENT);
        //        attentionId:          // 要关注的对象id
        //        creator：             //  登录人id
        //        password：        //   登录人密码
        //        type:   1               //   传1
        params.put("attentionId", id);
        params.put("type", "1");
        params.put("password", BaseApplication.getInstance().getUserInfoBean().getUserPwd());
        params.put("creator", BaseApplication.getInstance().getUserInfoBean().getId());

        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                if (bean.getFollow().equals("1")) {
                    params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_DELATTENT);
                } else {
                    params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_ATTENT);
                }
                return HttpOkBiz.getInstance().sendGet(params);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                dismissProgress();
                showToast(result.getInfo());
                //                hasFollowed = "";//  登录人对文章作者的关注状态 0为未关注，1为已关注
                if (bean.getFollow().equals("1")) {
                    bean.setFollow("0");
                    btn_add_care.setText("关注");
                } else {
                    bean.setFollow("1");
                    btn_add_care.setText("已关注");
                }
            }

            @Override
            public void onFail(ResponseBean result) {
                dismissProgress();
                showToast(result.getInfo());
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        quaryReviewByUser(dataBeans.size());
    }
}