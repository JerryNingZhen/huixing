package com.android.base.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
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
import com.android.base.adapter.ArticleCommentAdapter;
import com.android.base.bean.ArticleAddBean;
import com.android.base.bean.ArticleCommentBean;
import com.android.base.bean.ArticleDetailBean;
import com.android.base.bean.BaseBean;
import com.android.base.bean.ResponseBean;
import com.android.base.configs.ConfigServer;
import com.android.base.configs.ConstantKey;
import com.android.base.executor.BaseTask;
import com.android.base.executor.RequestExecutor;
import com.android.base.interfaces.OnDialogViewCallBack;
import com.android.base.mvp.baseclass.BaseActivity;
import com.android.base.mvp.baseclass.BaseView;
import com.android.base.mvp.model.HttpOkBiz;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.LogUtil;
import com.android.base.utils.ScreenUtil;
import com.android.base.utils.StringUtil;
import com.android.base.utils.dialog.CustomDialog;
import com.android.base.utils.dialog.DialogUtil;
import com.android.base.utils.dialog.share.ShareBean;
import com.android.base.utils.dialog.share.ShareDialogUtil;
import com.android.base.utils.glide.CropTransformation;
import com.android.base.utils.glide.GlideUtil;
import com.android.base.utils.gson.GsonUtil;
import com.android.base.utils.http.HttpOkUtil;
import com.android.base.widget.CustomKeyBoardListView;
import com.android.base.widget.TitleView;
import com.android.base.widget.view.DialogContentView;
import com.bumptech.glide.request.RequestOptions;
import com.hx.huixing.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 文章详情
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ArticleDetailActivity extends BaseActivity implements BaseView {

    private TitleView title_view;
    private ImageView img_content;
    private TextView txt_title;
    private TextView txt_date;
    private TextView txt_content;
    private TextView txt_copyright;

    private TextView txt_like;
    private TextView txt_profit;

    private ImageView round_image;
    private TextView txt_name;
    private TextView txt_detail;
    private Button btn_add_care;

    private TextView txt_comment_size;
    private CustomKeyBoardListView lv_content;

    private EditText edit_comment;
    private TextView txt_zan;
    private TextView txt_comment;
    private ImageView img_share;
    private View view_right;
    private View btn_send;

    private SmartRefreshLayout refresh_view;

    private int currentPage = 1;
    private ArrayList<ArticleCommentBean> commentBeans = new ArrayList<>();
    private ArticleCommentAdapter adapter;
    private ArticleDetailBean bean;

    public ArticleDetailBean getBean() {
        return bean;
    }

    @Override
    public void initVP() {
        mvpView = this;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_article_detail;
    }

    @Override
    public void findViews() {
        title_view = findViewByIds(R.id.title_view);
        edit_comment = findViewByIds(R.id.edit_comment);
        txt_zan = findViewByIds(R.id.txt_zan);
        txt_comment = findViewByIds(R.id.txt_comment);
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
        View view = View.inflate(this, R.layout.activity_article_detail_header, null);
        img_content = view.findViewById(R.id.img_content);
        txt_title = view.findViewById(R.id.txt_title);
        txt_content = view.findViewById(R.id.txt_content);
        txt_copyright = view.findViewById(R.id.txt_copyright);
        txt_date = view.findViewById(R.id.txt_date);
        txt_like = view.findViewById(R.id.txt_like);
        txt_profit = view.findViewById(R.id.txt_profit);
        round_image = view.findViewById(R.id.round_image);
        txt_name = view.findViewById(R.id.txt_name);
        txt_detail = view.findViewById(R.id.txt_detail);
        btn_add_care = view.findViewById(R.id.btn_add_care);
        txt_comment_size = view.findViewById(R.id.txt_comment_size);
        lv_content.addHeaderView(view);

    }

    String id = "";

    @Override
    public void init(Bundle bundle) {
        if (bundle != null) {
            id = bundle.getString(ConstantKey.INTENT_KEY_ID, "");
        }
        title_view.setTitle("文章详情");

        showProgress(false);

        quaryArticleDeatail();
        queryArticles(10);

        adapter = new ArticleCommentAdapter(this, commentBeans);
        lv_content.setAdapter(adapter);
    }

    @Override
    public void widgetListener() {
        title_view.setLeftBtnImg();

        View.OnClickListener onClickListener = new View.OnClickListener() {
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.txt_comment://
                        lv_content.setSelection(1);
                        break;

                    case R.id.txt_like://
                    case R.id.txt_zan://
                        addLike();
                        break;
                    case R.id.txt_profit://
                        break;
                    case R.id.img_share://
                        if (bean.getCreator().equals(BaseApplication.getInstance().getUserInfoBean().getId())) {
                            final CustomDialog dialog = new CustomDialog(ArticleDetailActivity.this);
                            DialogContentView contentView = new DialogContentView(ArticleDetailActivity.this, dialog, new OnDialogViewCallBack() {
                                @Override
                                public void onResult(Map<String, String> map) {
                                    dialog.dismiss();
                                    String str = map.get("position");
                                    switch (str) {
                                        case "share"://
                                            share();
                                            break;
                                        case "edit"://
                                            ArticleAddBean articleAddBean = new ArticleAddBean();
                                            articleAddBean.setTitlePage(bean.getTitlePage());
                                            articleAddBean.setTextContent(bean.getTextContent());
                                            articleAddBean.setTextTitle(bean.getTextTitle());
                                            articleAddBean.setCreateTime(bean.getCreateTime());
                                            articleAddBean.setReviewId(bean.getReviewId());
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable(ConstantKey.INTENT_KEY_DATA, articleAddBean);
                                            IntentUtil.gotoActivity(ArticleDetailActivity.this, AddArticleActivity.class, bundle);
                                            break;
                                        case "del"://
                                            DialogUtil.showMessageDg(ArticleDetailActivity.this, "确定删除文章？", "", "取消", "删除", null, new CustomDialog.OnDialogClickListener() {
                                                @Override
                                                public void onClick(CustomDialog dialog, int id, Object object) {
                                                    dialog.dismiss();
                                                    delReview();
                                                }
                                            });
                                            break;
                                        default:// 默认
                                            break;
                                    }
                                }
                            });
                            dialog.createDialog(contentView, false);
                            dialog.show();
                            dialog.setDialogWidth(Gravity.BOTTOM, 0);
                        } else {
                            share();
                        }

                        break;
                    case R.id.btn_add_care:// 关注
                        attent();
                        break;
                    case R.id.btn_send:// 关注
                        addComment();
                        break;
                    case R.id.round_image:// 个人主页
                        Bundle bundle = new Bundle();
                        bundle.putString(ConstantKey.INTENT_KEY_ID, bean.getCreator());
                        IntentUtil.gotoActivityToTop(ArticleDetailActivity.this, PersonalHomeActivity.class, bundle);
                        break;

                    default:// 默认
                        break;
                }
            }
        };

        txt_comment.setOnClickListener(onClickListener);
        txt_like.setOnClickListener(onClickListener);
        txt_zan.setOnClickListener(onClickListener);
        txt_profit.setOnClickListener(onClickListener);
        btn_add_care.setOnClickListener(onClickListener);
        btn_send.setOnClickListener(onClickListener);
        round_image.setOnClickListener(onClickListener);
        img_share.setOnClickListener(onClickListener);

        lv_content.setOnSizeChangedListener(new CustomKeyBoardListView.onSizeChangedListener() {

            @Override
            public void onChanged(boolean showKeyboard) {
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
                queryArticles(10);
            }
        });

        refresh_view.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage = currentPage + 1;
                queryArticles(10);

            }
        });
    }

    private void share() {
        if (bean == null) {
            return;
        }
        ShareBean shareBean = new ShareBean();

        if (!TextUtils.isEmpty(BaseApplication.getInstance().getUserInfoBean().getUserPic())) {
            shareBean.setPhotoUrl(BaseApplication.getInstance().getUserInfoBean().getUserPic());
        } else {
            shareBean.setPhotoUrl("http://huixing.io/img/favicon.png");
        }

        shareBean.setTextContent(bean.getTextTitle());
        shareBean.setTitle(bean.getTextTitle());
        shareBean.setContentUrl(ConfigServer.SHARE_URL + id);

        String[] nameItems = getResources().getStringArray(R.array.share_types);
        Integer[] resItems = new Integer[]{R.drawable.share_wechat, //
                R.drawable.share_wechatmoments, //
                R.drawable.share_sina
        };
        ShareDialogUtil popupWindowUtil = new ShareDialogUtil(ArticleDetailActivity.this, shareBean, nameItems, resItems);
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
     * 文章详情
     */
    private void quaryArticleDeatail() {
        //        "reviewId":      // 查询的文章的id
        //        "loginUser":    //  当前登录人id
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_QUARYARTICLEDEATAIL);
        params.put("loginUser", BaseApplication.getInstance().getUserInfoBean().getId());
        params.put("reviewId", id);

        //        new ArticleModel().quaryArticleDeatail(params, new HttpRequestCallBack(ArticleDetailBean.class) {
        //            @Override
        //            public void onSuccess(final ResponseBean result) {
        //                //                bean = GsonUtil.getInstance().json2Bean((String) result.getObject(), ArticleDetailBean.class);
        //                bean = (ArticleDetailBean) result.getObject();
        //                txt_title.setText(bean.getTextTitle());
        //                dismissProgress();
        //                showToast(result.getInfo());
        //
        //            }
        //
        //            @Override
        //            public void onFail(ResponseBean result) {
        //                dismissProgress();
        //                showToast(result.getInfo());
        //
        //            }
        //        });
        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                return HttpOkBiz.getInstance().sendGet(params);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                bean = GsonUtil.getInstance().json2Bean((String) result.getObject(), ArticleDetailBean.class);
                if (!TextUtils.isEmpty(bean.getTitlePage())) {
                    downLoadImage();
                } else {
                    dismissProgress();
                }
                txt_title.setText(bean.getTextTitle());
                txt_content.setText(Html.fromHtml(bean.getTextContent()));

                String copyright = String.format(getString(R.string.activity_article_detail), StringUtil.makeColorText(bean.getRealName(), "#5c5c5c"));
                txt_copyright.setText(Html.fromHtml(copyright));

                //               likeStatus": 1,      null为无登录人，0为未点赞，1为已点赞
                if (!TextUtils.isEmpty(bean.getLikeStatus()) && bean.getLikeStatus().equals("1")) {
                    txt_like.setText("已赞");
                    txt_zan.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.thumb_up), null, null, null);
                } else {
                    txt_like.setText("赞一下");
                    txt_zan.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.thumb_up_grey), null, null, null);
                }

                txt_zan.setText(bean.getLikes());
                txt_date.setText("发布于 " + bean.getCreateTime());
                //                txt_like.setText(bean.getLikes());
                txt_profit.setText(bean.getArticleProfit());
                RequestOptions options = GlideUtil.getRequestOptions();
                options.transforms(new CropTransformation(CropTransformation.CropType.CIRCLE));//// 多重变换
                GlideUtil.loadImage(ArticleDetailActivity.this, bean.getUserPic(), round_image, options.error(R.drawable.default_portrait_grey));

                txt_name.setText(bean.getRealName());
                txt_detail.setText(Html.fromHtml("个人介绍 " + StringUtil.makeColorText(bean.getPersonIntro(), "#5c5c5c")));

                //                hasFollowed": 1,                 null为无登录人，0为未关注，1为已关注
                if (!TextUtils.isEmpty(bean.getHasFollowed()) && bean.getHasFollowed().equals("1")) {
                    btn_add_care.setText("已关注");
                } else {
                    btn_add_care.setText("关注");
                }

                txt_comment_size.setText("评论（" + bean.getReview() + "）");
                txt_comment.setText(bean.getReview());

                //                hasFollowed = "";//  登录人对文章作者的关注状态 0为未关注，1为已关注
                if (bean.getHasFollowed().equals("1")) {
                    btn_add_care.setText("已关注");
                } else {
                    btn_add_care.setText("+关注");
                }

                if (bean.getCreator().equals(BaseApplication.getInstance().getUserInfoBean().getId())) {
                    img_share.setImageResource(R.drawable.icon_more_new);
                }
            }

            @Override
            public void onFail(ResponseBean result) {
                dismissProgress();
                showToast(result.getInfo());
            }
        });
    }

    private void downLoadImage() {

        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                Bitmap bitmap = null;
                try {
                    bitmap = HttpOkUtil.getInstance().loadImage(bean.getTitlePage(), R.drawable.img_default_grey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new ResponseBean(ConfigServer.RESPONSE_STATUS_SUCCESS, "", bitmap);
            }

            @Override
            public void onSuccess(ResponseBean result) {

                Bitmap bitmap = (Bitmap) result.getObject();
                if (bitmap != null) {
                    float height2Width = (float) bitmap.getHeight() / bitmap.getWidth();
                    img_content.getLayoutParams().height = (int) (ScreenUtil.getScreenWidthPx() * height2Width);

                    //设置图片充满ImageView控件
                    img_content.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    //等比例缩放
                    img_content.setAdjustViewBounds(true);
                    img_content.setImageBitmap(bitmap);
                    //                    bitmap.recycle();
                    //                    PicassoUtil.loadImage(baseUI, imgTitle, img_content);
                }
                dismissProgress();
            }

            @Override
            public void onFail(ResponseBean result) {
                dismissProgress();
            }
        });
    }

    /**
     * 文章下面的评论列表
     */
    private void queryArticles(int pageSize) {
        //parentId:" ",                                                            //文章id
        //currentPage:1，
        //pageSize:5，
        //type:3，                                                                 //文章为3

        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_QUERYARTICLES);
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
                //                ArrayList<ArticleCommentBean> beans = GsonUtil.getInstance().jsonToList((String) result.getObject(),ArticleCommentBean.class,"");
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
                    txt_like.setText("已赞");
                    txt_zan.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.thumb_up), null, null, null);
                } else {
                    txt_like.setText("赞一下");
                    txt_zan.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.thumb_up_grey), null, null, null);
                }

                txt_zan.setText(bean.getLikes());
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

        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                return HttpOkBiz.getInstance().sendPost(params);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                bean.setReview(String.valueOf(Integer.parseInt(bean.getReview()) + 1));

                txt_comment.setText(bean.getReview());
                txt_comment_size.setText("评论（" + bean.getReview() + "）");
                edit_comment.setText("");
                dismissProgress();
                showToast(result.getInfo());
                queryArticles(commentBeans.size() + 1);
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

    /**
     * 删除文章
     */
    private void delReview() {
        showProgress(false);
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_DELREVIEW);
        //        reviewId            // 文章id
        //        userId               //  用户id
        //        passWord         //  用户密码
        params.put("reviewId", id);
        params.put("passWord", BaseApplication.getInstance().getUserInfoBean().getUserPwd());
        params.put("userId", BaseApplication.getInstance().getUserInfoBean().getId());

        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                return HttpOkBiz.getInstance().sendGet(params);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                dismissProgress();
                showToast(result.getInfo());
                finishActivity();
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
        queryArticles(commentBeans.size());
    }
}