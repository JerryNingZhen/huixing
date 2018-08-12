package com.hx.huixing.adapter;

import android.content.Context;
import android.view.View;

import com.android.base.adapter.SimpleBaseAdapter;
import com.hx.huixing.bean.FocusFanBean;

import java.util.ArrayList;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.tan.mvpdemo.activityMvp
 * <br> Date: 2018/8/12
 */
public class FocusAdapter extends SimpleBaseAdapter<FocusFanBean.DatasBean> {

    public FocusAdapter(Context context, ArrayList<FocusFanBean.DatasBean> datas) {
        super(context, datas);
    }

    @Override
    public int getItemResource() {
        return 0;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        return null;
    }
}
