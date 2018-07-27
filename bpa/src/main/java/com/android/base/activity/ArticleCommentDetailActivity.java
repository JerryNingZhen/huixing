package com.android.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amos.smartrefresh.layout.SmartRefreshLayout;
import com.amos.smartrefresh.layout.api.RefreshLayout;
import com.amos.smartrefresh.layout.listener.OnLoadMoreListener;
import com.amos.smartrefresh.layout.listener.OnRefreshListener;
import com.android.base.BaseApplication;
import com.android.base.adapter.ArticleDetailCommentAdapter;
import com.android.base.bean.ArticleCommentBean;
import com.android.base.bean.BaseBean;
import com.android.base.bean.ResponseBean;
import com.android.base.configs.ConfigServer;
import com.android.base.configs.ConstantKey;
import com.android.base.executor.BaseTask;
import com.android.base.executor.RequestExecutor;
import com.android.base.mvp.baseclass.BaseActivity;
import com.android.base.mvp.baseclass.BaseView;
import com.android.base.mvp.model.HttpOkBiz;
import com.android.base.utils.DateUtil;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.LogUtil;
import com.android.base.utils.dialog.share.ShareBean;
import com.android.base.utils.dialog.share.ShareDialogUtil;
import com.android.base.utils.glide.CropTransformation;
import com.android.base.utils.glide.GlideUtil;
import com.android.base.widget.CustomKeyBoardListView;
import com.android.base.widget.TitleView;
import com.bumptech.glide.request.RequestOptions;
import com.hx.huixing.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 文章评论详情
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ArticleCommentDetailActivity extends BaseActivity implements BaseView {

    private TitleView title_view;

    private CustomKeyBoardListView lv_content;

    private EditText edit_comment;
    private TextView txt_zan;
    //    private TextView txt_comment;
    private ImageView img_share;
    private View view_right;
    private View btn_send;

    private TextView txt_date;
    private Button btn_add_care;
    private TextView txt_name;
    private TextView txt_comment_content;
    private ImageView round_image;
    private TextView txt_dz;

    private SmartRefreshLayout refresh_view;
    private String id = "";

    private int currentPage = 1;
    private ArrayList<ArticleCommentBean> commentBeans = new ArrayList<>();
    private ArticleDetailCommentAdapter adapter;
    private ArticleCommentBean bean;

    @Override
    public void initVP() {
        mvpView = this;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_article_detai_commentl;
    }

    @Override
    public void findViews() {
        title_view = findViewByIds(R.id.title_view);
        edit_comment = findViewByIds(R.id.edit_comment);
        txt_zan = findViewByIds(R.id.txt_zan);
        //        txt_comment = findViewByIds(R.id.txt_comment);
        img_share = findViewByIds(R.id.img_share);
        view_right = findViewByIds(R.id.view_right_bottom);
        btn_send = findViewByIds(R.id.btn_send);

        lv_content = findViewByIds(R.id.lv_content);
        refresh_view = findViewByIds(R.id.refresh_view);
        refresh_view.setEnableAutoLoadMore(false);

        initHeaderView();
    }

    private void initHeaderView() {
        //        activity_article_detail_header
        View view = View.inflate(this, R.layout.activity_article_detail_comment_header, null);

        txt_date = view.findViewById(R.id.txt_date);
        btn_add_care = view.findViewById(R.id.btn_add_care);
        txt_name = view.findViewById(R.id.txt_name);
        txt_comment_content = view.findViewById(R.id.txt_comment_content);
        round_image = view.findViewById(R.id.round_image);
        txt_dz = view.findViewById(R.id.txt_dz);// 点赞

        lv_content.addHeaderView(view);

    }


    @Override
    public void init(Bundle bundle) {
        if (bundle != null) {
            bean = (ArticleCommentBean) bundle.getSerializable(ConstantKey.INTENT_KEY_DATA);
            id = bean.getReviewId();
        }

        RequestOptions options = GlideUtil.getRequestOptions();
        options.transforms(new CropTransformation(CropTransformation.CropType.CIRCLE));//// 多重变换
        GlideUtil.loadImage(this, bean.getUserPic(), round_image, options.error(R.drawable.default_portrait_grey));
        txt_name.setText(bean.getRealName());
        txt_comment_content.setText(bean.getTextTitle());
        //        txt_comment.setText(bean.getReview());
        txt_dz.setText(bean.getLikes());
        txt_zan.setText(bean.getLikes());
        txt_date.setText(DateUtil.timeAgo(bean.getCreateTime()));
        if (bean.getLikeStatus().equals("1")) {
            txt_zan.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.thumb_up), null, null, null);
            txt_dz.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.thumb_up), null, null, null);
        } else {
            txt_zan.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.thumb_up_grey), null, null, null);
            txt_dz.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.thumb_up_grey), null, null, null);
        }

        if (bean.getHasFollowed().equals("1")) {
            btn_add_care.setText("已关注");
        } else {
            btn_add_care.setText("+关注");
        }

        title_view.setTitle("评论详情");

        showProgress(false);

        queryArticlesList(10);

        adapter = new ArticleDetailCommentAdapter(this, commentBeans);
        lv_content.setAdapter(adapter);
    }

    @Override
    public void widgetListener() {
        title_view.setLeftBtnImg();

        View.OnClickListener onClickListener = new View.OnClickListener() {
            public void onClick(View view) {
                switch (view.getId()) {

                    case R.id.txt_zan://
                    case R.id.txt_dz://
                        addLike();
                        break;
                    case R.id.txt_profit://
                        break;
                    case R.id.btn_add_care:// 关注
                        attent();
                        break;
                    case R.id.btn_send:// 关注
                        addComment();
                        break;
                    case R.id.img_share://
                        share();
                        break;
                    case R.id.round_image://
                        Bundle bundle = new Bundle();
                        bundle.putString(ConstantKey.INTENT_KEY_ID, bean.getCreator());
                        IntentUtil.gotoActivityToTop(ArticleCommentDetailActivity.this, PersonalHomeActivity.class, bundle);
                        break;

                    default:// 默认
                        break;
                }
            }
        };

        txt_zan.setOnClickListener(onClickListener);
        txt_dz.setOnClickListener(onClickListener);
        btn_add_care.setOnClickListener(onClickListener);
        btn_send.setOnClickListener(onClickListener);
        img_share.setOnClickListener(onClickListener);
        round_image.setOnClickListener(onClickListener);

        lv_content.setOnSizeChangedListener(new CustomKeyBoardListView.onSizeChangedListener() {

            @Override
            public void onChanged(boolean showKeyboard) {
                // TODO Auto-generated method stub
                if (showKeyboard) {
                    LogUtil.i("show keyboard true");
                    view_right.setVisibility(View.GONE);
                    btn_send.setVisibility(View.VISIBLE);
                } else {
                    LogUtil.i("show keyboard false");
                    view_right.setVisibility(View.VISIBLE);
                    btn_send.setVisibility(View.GONE);
                }
            }
        });
        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                queryArticlesList(10);
            }
        });

        refresh_view.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage = currentPage + 1;
                queryArticlesList(10);

            }
        });
    }

    private void share() {
        ShareBean shareBean = new ShareBean();

        shareBean.setPhotoUrl("http://www.mob.com/assets/images/logo-51fcf38a.png");
        shareBean.setTextContent("setTextContent");
        shareBean.setTitle("setTitle");
        shareBean.setContentUrl("www.baidu.com");
        shareBean.setContentId("");
        shareBean.setContentType("");
        String[] nameItems = getResources().getStringArray(R.array.share_types);
        Integer[] resItems = new Integer[]{R.drawable.share_wechat, //
                R.drawable.share_wechatmoments, //
                R.drawable.share_sina
        };
        ShareDialogUtil popupWindowUtil = new ShareDialogUtil(this, shareBean, nameItems, resItems);
        popupWindowUtil.show(shareBean, Gravity.BOTTOM);
    }

    public void refreshFinish() {
        refresh_view.finishRefresh();
        refresh_view.finishLoadMore();
    }

    @Override
    public void setViewData(Object object) {

    }

    //    // ************************************************************************返回键事件处理
    //    @Override
    //    public boolean dispatchKeyEvent(KeyEvent event) {
    //        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
    //            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
    //                // 要执行的事件
    //                //                getMvpView().saveDraft();
    //            }
    //            return true;
    //        }
    //        return super.dispatchKeyEvent(event);
    //    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 查询某一条评论下的所有评论
     */
    private void queryArticlesList(int pageSize) {

        final HashMap<String, String> params = new HashMap<>();
        //parentId:" ",                                                            //要查询的评论id
        //currentPage:1，
        //pageSize:10，
        //type:3，                                                                 //文章为3
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_QUERYARTICLESLIST);
        params.put("parentId", id);
        params.put("currentPage", currentPage + "");
        params.put("pageSize", pageSize + "");
        params.put("type", "3");
        params.put("loginUser", BaseApplication.getInstance().getUserInfoBean().getId());

        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                return HttpOkBiz.getInstance().sendGet(params);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                BaseBean.setResponseObjectList(result, ArticleCommentBean.class, "");
                ArrayList<ArticleCommentBean> beans = (ArrayList<ArticleCommentBean>) result.getObject();
                //                ArrayList<ArticleCommentBean> beans = GsonUtil.getInstance().gson.fromJson((String) result.getObject(), new TypeToken<List<ArticleCommentBean>>() {
                //                }.getType());
                if (currentPage == 1) {
                    commentBeans.clear();
                }
                commentBeans.addAll(beans);
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
     * 点赞
     */
    private void addLike() {
        showProgress(false);
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_ADDLIKE);
        //        reviewId:       // 点赞的文章或者评论的id
        //        userId:          //  点赞人的id
        //        likes：          //   点赞传1，取消赞传0
        params.put("reviewId", id);
        params.put("likes", (bean.getLikeStatus().equals("0") ? "1" : "0"));
        params.put("userId", BaseApplication.getInstance().getUserInfoBean().getId());

        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                return HttpOkBiz.getInstance().sendGet(params);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                if (bean.getLikeStatus().equals("0")) {
                    bean.setLikeStatus("1");
                    bean.setLikes(String.valueOf(Integer.parseInt(bean.getLikes()) + 1));
                } else {
                    bean.setLikeStatus("0");
                    bean.setLikes(String.valueOf(Integer.parseInt(bean.getLikes()) - 1));
                }

                if (bean.getLikeStatus().equals("1")) {
                    txt_zan.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.thumb_up), null, null, null);
                    txt_dz.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.thumb_up), null, null, null);
                } else {
                    txt_zan.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.thumb_up_grey), null, null, null);
                    txt_dz.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.thumb_up_grey), null, null, null);
                }

                txt_zan.setText(bean.getLikes());
                txt_dz.setText(bean.getLikes());
                dismissProgress();
            }

            @Override
            public void onFail(ResponseBean result) {
                dismissProgress();
                showToast(result.getInfo());
            }
        });
    }

    /**
     * 发表文章下评论
     */
    private void addComment() {

        String str = edit_comment.getText().toString();
        if (TextUtils.isEmpty(str)) {
            showToast("评论内容不能为空！");
            return;
        }
        showProgress(false);
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_ADDREVIEW_BLOCK);
        //  type: 3 ,                       // 文章为3
        //    textTitle:                     //  评论内容
        //    creator:                      //   登录人id
        //        parentId:"6ea93bc0-60d4-47ea-9bb"             //文章id
        //quote:quote              //    回复别人的评论时要传，传你回复的那条的评论的内容
        //quotedReviewId       //     回复别人的评论时要穿，传你回复的那条的评论的reviewId
        params.put("parentId", id);
        params.put("type", "3");
        params.put("textTitle", str);
        params.put("creator", BaseApplication.getInstance().getUserInfoBean().getId());
        params.put("quote", bean.getTextTitle());
        params.put("quotedReviewId", bean.getReviewId());

        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                return HttpOkBiz.getInstance().sendPost(params);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                bean.setReview(String.valueOf(Integer.parseInt(bean.getReview()) + 1));

                edit_comment.setText("");
                dismissProgress();
                showToast(result.getInfo());

                queryArticlesList(commentBeans.size() + 1);
            }

            @Override
            public void onFail(ResponseBean result) {
                dismissProgress();
                showToast(result.getInfo());
            }
        });
    }

    /**
     * 关注
     */
    private void attent() {
        showProgress(false);
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_ATTENT);
        //        attentionId:          // 要关注的对象id
        //        creator：             //  登录人id
        //        password：        //   登录人密码
        //        type:   1               //   传1
        params.put("attentionId", bean.getCreator());
        params.put("type", "1");
        params.put("password", BaseApplication.getInstance().getUserInfoBean().getUserPwd());
        params.put("creator", BaseApplication.getInstance().getUserInfoBean().getId());

        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                if (bean.getHasFollowed().equals("1")) {
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
                if (bean.getHasFollowed().equals("1")) {
                    bean.setHasFollowed("0");
                    btn_add_care.setText("+关注");
                } else {
                    bean.setHasFollowed("1");
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
}