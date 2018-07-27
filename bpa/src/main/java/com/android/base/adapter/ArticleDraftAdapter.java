package com.android.base.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.activity.AddArticleActivity;
import com.android.base.bean.ArticleAddBean;
import com.android.base.configs.ConstantKey;
import com.android.base.configs.RequestCode;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.LogUtil;
import com.android.base.utils.ScreenUtil;
import com.android.base.utils.glide.GlideUtil;
import com.hx.huixing.R;

import java.util.ArrayList;

/**
 * 发帖草稿箱 适配器
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ArticleDraftAdapter extends SimpleBaseAdapter<ArticleAddBean> {

    private int viewHeight;

    public ArticleDraftAdapter(Context context, ArrayList<ArticleAddBean> data) {
        super(context, data);
        viewHeight = (ScreenUtil.getScreenWidthPx() - ScreenUtil.dip2px(20)) / 3;
    }

    @Override
    public int getItemResource() {
        return R.layout.item_article_draft;
    }

    @Override
    public View getItemView(final int position, View convertView, ViewHolder holder) {
        TextView txt_title = holder.getView(R.id.txt_title);
        TextView txt_content = holder.getView(R.id.txt_content);
        TextView txt_date = holder.getView(R.id.txt_date);
        ImageView img_content = holder.getView(R.id.img_content);

        img_content.getLayoutParams().height = viewHeight;

        final ArticleAddBean bean = dataList.get(position);

        GlideUtil.loadImage(context,bean.getTitlePage(),img_content,GlideUtil.getRequestOptions());
        // PicassoUtil.loadImage(context, bean.getTitlePage(), img_content);
        txt_title.setText(bean.getTextTitle());
        txt_content.setText(bean.getTextContent());
        txt_date.setText(bean.getCreateTime());

        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstantKey.INTENT_KEY_DATA, bean);
                LogUtil.i(bean.toString());
                IntentUtil.gotoActivityForResult(context, AddArticleActivity.class, bundle, RequestCode.REQUEST_CODE_ADD_ARTICLE_DRAFT);
            }
        });
        return convertView;
    }

}