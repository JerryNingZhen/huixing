package com.android.base.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.android.base.bean.ArticleCommentBean;
import com.android.base.utils.DateUtil;
import com.android.base.utils.StringUtil;
import com.hx.huixing.R;

import java.util.ArrayList;

/**
 * 文章评论列表 适配器
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ArticleCommentItemAdapter extends SimpleBaseAdapter<ArticleCommentBean> {


    public ArticleCommentItemAdapter(Context context, ArrayList<ArticleCommentBean> data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_article_comment_item;
    }

    @Override
    public View getItemView(final int position, View convertView, ViewHolder holder) {
        TextView txt_date = holder.getView(R.id.txt_date);

        TextView txt_comment = holder.getView(R.id.txt_comment);

        final ArticleCommentBean bean = dataList.get(position);

        txt_comment.setText(Html.fromHtml(bean.getRealName() + "：" + StringUtil.makeColorText(bean.getTextTitle(), "#5c5c5c")));
        txt_date.setText(DateUtil.timeAgo(bean.getCreateTime()));

        //        OnClickListener onClickListener = (new OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                Bundle bundle = new Bundle();
        //                bundle.putSerializable(ConstantKey.INTENT_KEY_DATA, bean.getReviewId());
        //                IntentUtil.gotoActivity(context, ArticleCommentDetailActivity.class, bundle);
        //            }
        //        });
        //        convertView.setOnClickListener(onClickListener);
        return convertView;
    }

}