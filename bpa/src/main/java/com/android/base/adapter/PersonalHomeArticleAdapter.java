package com.android.base.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.BaseApplication;
import com.android.base.activity.ArticleDetailActivity;
import com.android.base.activity.EditArticleActivity;
import com.android.base.bean.ArticleDetailBean;
import com.android.base.bean.ResponseBean;
import com.android.base.configs.ConfigServer;
import com.android.base.configs.ConstantKey;
import com.android.base.executor.BaseTask;
import com.android.base.executor.RequestExecutor;
import com.android.base.interfaces.OnDialogViewCallBack;
import com.android.base.mvp.model.HttpOkBiz;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.ScreenUtil;
import com.android.base.utils.ToastUtil;
import com.android.base.utils.dialog.CustomDialog;
import com.android.base.utils.dialog.DialogUtil;
import com.android.base.utils.dialog.share.ShareBean;
import com.android.base.utils.dialog.share.ShareDialogUtil;
import com.android.base.utils.glide.GlideUtil;
import com.android.base.widget.view.DialogContentView;
import com.hx.huixing.R;
import com.hx.huixing.widget.RoundCornerImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <br> Description 首页适配器
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.adapter
 * <br> Date: 2018/7/20
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class PersonalHomeArticleAdapter extends SimpleBaseAdapter<ArticleDetailBean> {


    public PersonalHomeArticleAdapter(Context context, ArrayList<ArticleDetailBean> data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_personal_home_article;
    }

    @Override
    public View getItemView(final int position, View convertView, ViewHolder holder) {
        TextView tv_content_title = holder.getView(R.id.tv_content_title);//文章标题
        TextView tv_content = holder.getView(R.id.tv_content);//摘要
        RoundCornerImage iv_content = holder.getView(R.id.iv_content); //文章插图

        TextView txt_zan = holder.getView(R.id.txt_zan);
        TextView txt_comment = holder.getView(R.id.txt_comment);//评论个数
        TextView txt_coin_count = holder.getView(R.id.txt_coin_count); //币个数
        TextView tv_date = holder.getView(R.id.tv_date);
        ImageView img_arrow = holder.getView(R.id.img_arrow);

        final ArticleDetailBean bean = dataList.get(position);
        iv_content.getLayoutParams().height = ScreenUtil.getScreenWidthPx() / 2;

        tv_date.setText(bean.getCreateTime());
        if (!TextUtils.isEmpty(bean.getTitlePage())) {
            GlideUtil.loadImage(context, bean.getTitlePage(), iv_content, GlideUtil.getRequestOptions());
        } else {
            GlideUtil.loadImage(context, "", iv_content, GlideUtil.getRequestOptions().error(R.drawable.img_default_grey));
        }
        tv_content_title.setText(bean.getTextTitle());
        tv_content.setText(Html.fromHtml(bean.getTextContent()));

        txt_zan.setText(bean.getLikes());
        if (bean.getLikeStatus().equals("1")) {
            txt_zan.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.thumb_up), null, null, null);
        } else {
            txt_zan.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.thumb_up_grey), null, null, null);
        }

        txt_comment.setText(bean.getReview());
        txt_coin_count.setText(bean.getArticleProfit());

        //

        if (BaseApplication.getInstance().getUserInfoBean().getId().equals(bean.getCreator())) {
            img_arrow.setVisibility(View.VISIBLE);
        } else {
            img_arrow.setVisibility(View.GONE);
        }

        img_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(position);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstantKey.INTENT_KEY_ID, bean.getReviewId());
                IntentUtil.gotoActivity(context, ArticleDetailActivity.class, bundle);
            }
        });

        return convertView;
    }


    private void show(final int position) {
        final CustomDialog dialog = new CustomDialog(context);
        DialogContentView contentView = new DialogContentView(context, dialog, new OnDialogViewCallBack() {
            @Override
            public void onResult(Map<String, String> map) {
                dialog.dismiss();
                String str = map.get("position");
                switch (str) {
                    case "share"://
                        share(position);
                        break;
                    case "edit"://
                        //                        ArticleAddBean bean = new ArticleAddBean();
                        //                        bean.setTitlePage(dataList.get(position).getTitlePage());
                        //                        bean.setTextContent(dataList.get(position).getTextContent());
                        //                        bean.setTextTitle(dataList.get(position).getTextTitle());
                        //                        bean.setCreateTime(dataList.get(position).getCreateTime());
                        //                        bean.setReviewId(dataList.get(position).getReviewId());
                        Bundle bundle = new Bundle();
                        //                        bundle.putSerializable(ConstantKey.INTENT_KEY_DATA, bean);
                        bundle.putSerializable(ConstantKey.INTENT_KEY_ID, dataList.get(position).getReviewId());
                        IntentUtil.gotoActivity(context, EditArticleActivity.class, bundle);
                        break;
                    case "del"://
                        DialogUtil.showMessageDg(context, "确定删除文章？", "", "取消", "删除", null, new CustomDialog.OnDialogClickListener() {
                            @Override
                            public void onClick(CustomDialog dialog, int id, Object object) {
                                dialog.dismiss();
                                delReview(position);
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
    }

    private void share(int positon) {
        ShareBean shareBean = new ShareBean();

        if (!TextUtils.isEmpty(dataList.get(positon).getTitlePage())) {
            shareBean.setPhotoUrl(dataList.get(positon).getTitlePage());
        } else {
            shareBean.setPhotoUrl("http://huixing.io/img/favicon.png");
        }
        shareBean.setTextContent(dataList.get(positon).getTextTitle());
        shareBean.setTitle(dataList.get(positon).getTextTitle());
        shareBean.setContentUrl(ConfigServer.SHARE_URL + dataList.get(positon).getReviewId());
        String[] nameItems = context.getResources().getStringArray(R.array.share_types);
        Integer[] resItems = new Integer[]{R.drawable.share_wechat, //
                R.drawable.share_wechatmoments, //
                R.drawable.share_sina
        };
        popupWindowUtil = new ShareDialogUtil(context, shareBean, nameItems, resItems);
        popupWindowUtil.show(shareBean, Gravity.BOTTOM);
    }

    ShareDialogUtil popupWindowUtil;

    public void dismissProgress() {
        if (popupWindowUtil != null) {
            popupWindowUtil.dismissProgress();
        }
    }

    /**
     * 删除文章
     */
    private void delReview(final int position) {
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_DELREVIEW);
        //        reviewId            // 文章id
        //        userId               //  用户id
        //        passWord         //  用户密码
        params.put("reviewId", dataList.get(position).getReviewId());
        params.put("passWord", BaseApplication.getInstance().getUserInfoBean().getUserPwd());
        params.put("userId", BaseApplication.getInstance().getUserInfoBean().getId());

        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                return HttpOkBiz.getInstance().sendGet(params);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                dataList.remove(position);
                notifyDataSetChanged();
                ToastUtil.showToast(context, result.getInfo());
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context, result.getInfo());
            }
        });
    }
}

