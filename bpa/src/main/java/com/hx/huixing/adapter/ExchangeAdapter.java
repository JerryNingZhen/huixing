package com.hx.huixing.adapter;

import android.content.Context;
import android.view.View;

import com.android.base.adapter.SimpleBaseAdapter;
import com.hx.huixing.R;

import java.util.ArrayList;

/**
 * <br> Description 交易记录的适配器
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.adapter
 * <br> Date: 2018/8/9
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ExchangeAdapter extends SimpleBaseAdapter {


    public ExchangeAdapter(Context context, ArrayList datas) {
        super(context, datas);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_exchange_record;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        return null;
    }
}
