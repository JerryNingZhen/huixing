package com.hx.huixing.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.base.utils.StringUtil;
import com.android.base.utils.ToastUtil;
import com.android.base.utils.picasso.PicassoUtil;
import com.hx.huixing.R;
import com.hx.huixing.bean.CommentBean;
import com.hx.huixing.widget.RoundImage;

import java.util.ArrayList;

/**
 * <br> Description 我的消息 评论适配器
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.adapter
 * <br> Date: 2018/7/23
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class CommentAdapter extends BaseAdapter {

    private ArrayList<CommentBean.DatasBean> listDatas;
    private Context mContext;

    public CommentAdapter(ArrayList<CommentBean.DatasBean> listDatas, Context mContext) {
        this.listDatas = listDatas;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return listDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return listDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_comment, null);
        final RelativeLayout view_bottom = convertView.findViewById(R.id.view_bottom);
        RoundImage round_image = convertView.findViewById(R.id.round_image);
        TextView tv_name = convertView.findViewById(R.id.tv_name);
        TextView tv_date = convertView.findViewById(R.id.tv_date);
        TextView tv_title_comment = convertView.findViewById(R.id.tv_title_comment);
        Button btn_add_reply = convertView.findViewById(R.id.btn_add_reply);
        TextView tv_comment = convertView.findViewById(R.id.tv_comment);
        final EditText edit_comment = convertView.findViewById(R.id.edit_comment); //内容
        Button btn_send = convertView.findViewById(R.id.btn_send); //发表按钮

        /** 数据 */
        CommentBean.DatasBean bean = listDatas.get(position);

        String title = bean.getTitle();
        title = "《" + title + "》";


        /** 设置数据 */
        PicassoUtil.loadImage(mContext, bean.getCreatorPic(), round_image);
        tv_name.setText(bean.getRealName());
        tv_title_comment.setText(Html.fromHtml(mContext.getString(R.string.comment_article)+ StringUtil.makeColorText(title, "#0765a8")));
        tv_date.setText(bean.getCreateTime());
        tv_comment.setText(bean.getContent()); //回复

        btn_add_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_bottom.setVisibility(View.VISIBLE);
            }
        });

        /** 表达评论 */
        /*btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edit_comment.getText().toString().trim())){
                    ToastUtil.showToast(mContext, "评论内容不能为空");
                    return;
                }

            }
        });*/

        return convertView;
    }
}
