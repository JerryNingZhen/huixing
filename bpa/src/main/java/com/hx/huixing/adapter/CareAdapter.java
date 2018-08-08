package com.hx.huixing.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.base.BaseApplication;
import com.android.base.activity.ArticleDetailActivity;
import com.android.base.activity.PersonalHomeActivity;
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
import com.android.base.utils.glide.GlideRoundTransform;
import com.android.base.utils.glide.GlideUtil;
import com.android.base.utils.picasso.PicassoUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hx.huixing.R;
import com.hx.huixing.bean.CareArticleBean;
import com.hx.huixing.widget.RoundImage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * <br> Description 首页适配器
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.adapter
 * <br> Date: 2018/7/20
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class CareAdapter extends BaseAdapter {

    private ArrayList<CareArticleBean.DatasBean> datas;
    private Context mContext;
    private LayoutInflater mInflater;

    public CareAdapter(Context context, ArrayList<CareArticleBean.DatasBean> datas) {
        this.datas = datas;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = mInflater.inflate(R.layout.item_care_adapter, viewGroup, false);

        RoundImage round_image;//用户图片
        TextView tv_name; //用户名
        TextView tv_date; //日期 四天前
        ImageView iv_content; //文章插图
        TextView tv_content_title;//文章标题
        TextView tv_content_brife;//摘要 50个字

        LinearLayout ll_thumb;//点赞 用于点击
        TextView thumb_count;

        LinearLayout ll_comment; //评论 用于点击
        TextView tv_comment_count;//评论个数

        TextView tv_coin_count; //币个数

        Button btn_add_care;//关注按钮

        round_image = view.findViewById(R.id.round_image);
        tv_name = view.findViewById(R.id.tv_name);
        tv_date = view.findViewById(R.id.tv_date);
        tv_content_title = view.findViewById(R.id.tv_content_title);
        tv_content_brife = view.findViewById(R.id.tv_content_brife);
        iv_content = view.findViewById(R.id.iv_content);

        btn_add_care = view.findViewById(R.id.btn_add_care); //添加关注

        tv_comment_count = view.findViewById(R.id.tv_comment_count);

        ll_thumb = view.findViewById(R.id.ll_thumb);
        thumb_count = view.findViewById(R.id.thumb_count);
        ll_comment = view.findViewById(R.id.ll_comment);
        tv_coin_count = view.findViewById(R.id.tv_coin_count);


        final CareArticleBean.DatasBean bean = datas.get(i);
        PicassoUtil.loadImage(mContext, bean.getUserPic(), round_image);

        tv_name.setText(bean.getRealName());
        /** 天数 */
        String days = "";
        try {
            days = String.valueOf(DateUtil.getTimeInterval(DateUtil.GetNowDate(), bean.getCreateTime(), ""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        int day = (int)Double.parseDouble(days);

        if (bean.getHasFollowed().equals("1")) {
            //btn_add_care.setText("已关注");
            btn_add_care.setVisibility(View.GONE); //已关注就隐藏
        } else {
            btn_add_care.setText("关注");
        }

        if (day == 0) {
            tv_date.setText("今天");
        } else {
            tv_date.setText(day + "天前");
        }
        if (!TextUtils.isEmpty(bean.getTitlePage())) {
            GlideUtil.loadImage(mContext, bean.getTitlePage(), iv_content, GlideUtil.getRequestOptions().error(R.drawable.img_default_grey));
        } else {
            GlideUtil.loadImage(mContext, "", iv_content, GlideUtil.getRequestOptions().error(R.drawable.img_default_grey));
        }

        tv_content_title.setText(bean.getTextTitle());
        //tv_content_brife.setText(Html.fromHtml(bean.getTextContent()));
        tv_content_brife.setText(bean.getTextContent());

        //        ll_thumb.setOnClickListener(new View.OnClickListener() { //点赞
        //            @Override
        //            public void onClick(View v) {
        //
        //            }
        //        });
        thumb_count.setText(bean.getLikes());

        //        ll_comment.setOnClickListener(new View.OnClickListener() {//跳转评论页
        //            @Override
        //            public void onClick(View v) {
        //
        //            }
        //        });

        tv_comment_count.setText(bean.getReview());
        tv_coin_count.setText(bean.getArticleProfit());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstantKey.INTENT_KEY_ID, bean.getReviewId());
                IntentUtil.gotoActivity(mContext, ArticleDetailActivity.class, bundle);
            }
        });
        round_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(ConstantKey.INTENT_KEY_ID, bean.getCreator());
                IntentUtil.gotoActivityToTop(mContext, PersonalHomeActivity.class, bundle);
            }
        });
        btn_add_care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attent(i);
            }
        });

        return view;
    }


    /**
     * 关注
     */
    private void attent(final int position) {
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_ATTENT);
        //        attentionId:          // 要关注的对象id
        //        creator：             //  登录人id
        //        password：        //   登录人密码
        //        type:   1               //   传1
        params.put("attentionId", datas.get(position).getCreator());
        params.put("type", "1");
        params.put("password", BaseApplication.getInstance().getUserInfoBean().getUserPwd());
        params.put("creator", BaseApplication.getInstance().getUserInfoBean().getId());

        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                if (datas.get(position).getHasFollowed().equals("1")) {
                    params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_DELATTENT);
                } else {
                    params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_ATTENT);
                }
                return HttpOkBiz.getInstance().sendGet(params);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                ToastUtil.showToast(mContext, result.getInfo());
                //                hasFollowed = "";//  登录人对文章作者的关注状态 0为未关注，1为已关注
                if (datas.get(position).getHasFollowed().equals("1")) {
                    datas.get(position).setHasFollowed("0");
                } else {
                    datas.get(position).setHasFollowed("1");
                }
                notifyDataSetChanged();
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(mContext, result.getInfo());
            }
        });
    }
}
