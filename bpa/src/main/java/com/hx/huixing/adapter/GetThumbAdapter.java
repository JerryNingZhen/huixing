package com.hx.huixing.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.base.utils.StringUtil;
import com.hx.huixing.R;
import com.hx.huixing.bean.ThumbBean;

import java.util.ArrayList;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.adapter
 * <br> Date: 2018/7/23
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class GetThumbAdapter extends BaseAdapter{

    private ArrayList<ThumbBean.DatasBean> listDatas;
    private Context mContext;

    public GetThumbAdapter(ArrayList<ThumbBean.DatasBean> listDatas, Context mContext) {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_adapter_thumb, null);
        TextView tv_name = convertView.findViewById(R.id.tv_name); //名字
        TextView tv_article_name = convertView.findViewById(R.id.tv_article_name); //文章名
        TextView tv_time = convertView.findViewById(R.id.tv_time);// 时间

        ThumbBean.DatasBean bean = listDatas.get(position);
        tv_name.setText(bean.getRealName());
        tv_time.setText(bean.getCreateTime());
        String articleName = "《"+ bean.getTitle()+"》";
        tv_article_name.setText(Html.fromHtml(mContext.getString(R.string.like_article)+ StringUtil.makeColorText(articleName, "#0765a8")));

        return convertView;
    }

}
