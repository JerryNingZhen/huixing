package com.hx.huixing.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.serializer.BeanContext;
import com.android.base.adapter.SimpleBaseAdapter;
import com.hx.huixing.R;
import com.hx.huixing.bean.SignBean;

import java.util.ArrayList;

/**
 * <br> Description 交易记录的适配器
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.adapter
 * <br> Date: 2018/8/9
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ExchangeAdapter extends SimpleBaseAdapter<SignBean.DatasBean> {


    public ExchangeAdapter(Context context, ArrayList datas) {
        super(context, datas);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_exchange_record;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        TextView tv_title = holder.getView(R.id.tv_title); //如 文章收益
        TextView tv_time = holder.getView(R.id.tv_time); //时间
        TextView tv_earn = holder.getView(R.id.tv_earn); //+10HUI

        SignBean.DatasBean bean = dataList.get(position);
        tv_title.setText(bean.getActionDetail());
        tv_time.setText(bean.getCreateTime());
        tv_earn.setText("+" + bean.getCoinCurrency() + bean.getCoinName());

        return convertView;
    }
}
