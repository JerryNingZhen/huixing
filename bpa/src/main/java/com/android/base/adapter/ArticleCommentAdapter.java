package com.android.base.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.base.BaseApplication;
import com.android.base.activity.ArticleCommentDetailActivity;
import com.android.base.activity.PersonalHomeActivity;
import com.android.base.bean.ArticleCommentBean;
import com.android.base.bean.ResponseBean;
import com.android.base.configs.ConfigServer;
import com.android.base.configs.ConstantKey;
import com.android.base.executor.BaseTask;
import com.android.base.executor.RequestExecutor;
import com.android.base.mvp.model.HttpOkBiz;
import com.android.base.utils.DateUtil;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.ToastUtil;
import com.android.base.utils.glide.CropTransformation;
import com.android.base.utils.glide.GlideUtil;
import com.bumptech.glide.request.RequestOptions;
import com.hx.huixing.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 文章评论列表 适配器
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ArticleCommentAdapter extends SimpleBaseAdapter<ArticleCommentBean> {


    public ArticleCommentAdapter(Context context, ArrayList<ArticleCommentBean> data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_article_comment;
    }

    @Override
    public View getItemView(final int position, View convertView, ViewHolder holder) {
        TextView txt_item = holder.getView(R.id.txt_item);
        TextView txt_date = holder.getView(R.id.txt_date);

        TextView txt_comment = holder.getView(R.id.txt_comment);
        TextView txt_name = holder.getView(R.id.txt_name);
        ImageView round_image = holder.getView(R.id.round_image);

        TextView txt_hf = holder.getView(R.id.txt_hf);// 回复
        TextView txt_dz = holder.getView(R.id.txt_dz);// 点赞
        ListView list_view = holder.getView(R.id.list_view);// 点赞
        TextView txt_more = holder.getView(R.id.txt_more);// 点赞

        final ArticleCommentBean bean = dataList.get(position);

        //        GlideUtil.loadImage(context, bean.getUserPic(), round_image, GlideUtil.getRequestOptions());
        RequestOptions options = GlideUtil.getRequestOptions();
        options.transforms(new CropTransformation(CropTransformation.CropType.CIRCLE));//// 多重变换
        GlideUtil.loadImage(context, bean.getUserPic(), round_image, options.error(R.drawable.default_portrait_grey));
        txt_item.setText((position + 1) + "楼");
        txt_name.setText(bean.getRealName());
        txt_comment.setText(bean.getTextTitle());
        txt_date.setText(DateUtil.timeAgo(bean.getCreateTime()));

        txt_dz.setText(bean.getLikes());
        if (bean.getLikeStatus().equals("1")) {
            txt_dz.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.thumb_up), null, null, null);
        } else {
            txt_dz.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.thumb_up_grey), null, null, null);
        }

        ArrayList<ArticleCommentBean> data = dataList.get(position).getFedbackList();
        if (data.size() > 3) {
            data.subList(0, 2);
            txt_more.setVisibility(View.VISIBLE);
        }else{
            txt_more.setVisibility(View.GONE);
        }

        ArticleCommentItemAdapter adapter = new ArticleCommentItemAdapter(context, data);
        list_view.setAdapter(adapter);

        OnClickListener onClickListener = (new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstantKey.INTENT_KEY_DATA, bean);
                IntentUtil.gotoActivity(context, ArticleCommentDetailActivity.class, bundle);
            }
        });

        convertView.setOnClickListener(onClickListener);
        txt_hf.setOnClickListener(onClickListener);
        txt_more.setOnClickListener(onClickListener);
        txt_dz.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addLike(position);
            }
        });

        round_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(ConstantKey.INTENT_KEY_ID, bean.getCreator());
                IntentUtil.gotoActivityToTop(context, PersonalHomeActivity.class, bundle);
            }
        });
        return convertView;
    }


    /**
     * 点赞
     */
    private void addLike(final int position) {
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_ADDLIKE);
        //        reviewId:       // 点赞的文章或者评论的id
        //        userId:          //  点赞人的id
        //        likes：          //   点赞传1，取消赞传0
        params.put("reviewId", dataList.get(position).getReviewId());
        params.put("likes", (dataList.get(position).getLikeStatus().equals("0") ? "1" : "0"));
        params.put("userId", BaseApplication.getInstance().getUserInfoBean().getId());
        //        final ArticleCommentBean bean = dataList.get(position);
        RequestExecutor.addTask(new BaseTask(context) {
            @Override
            public ResponseBean sendRequest() {
                return HttpOkBiz.getInstance().sendGet(params);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                if (dataList.get(position).getLikeStatus().equals("0")) {
                    dataList.get(position).setLikeStatus("1");
                    dataList.get(position).setLikes(String.valueOf(Integer.parseInt(dataList.get(position).getLikes()) + 1));
                } else {
                    dataList.get(position).setLikeStatus("0");
                    dataList.get(position).setLikes(String.valueOf(Integer.parseInt(dataList.get(position).getLikes()) - 1));
                }

                notifyDataSetChanged();
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context, result.getInfo());
            }
        });
    }
}