package com.android.base.utils.dialog.share;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.adapter.SimpleBaseAdapter;
import com.hx.huixing.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 分享助手类，dialog的适配器
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ShareDialogAdapter extends SimpleBaseAdapter<String> {

    /** 分享平台图片 */
    private List<Integer> resList = new ArrayList<>();

    public ShareDialogAdapter(Context context, ArrayList<String> datas, List<Integer> resList) {
        super(context,   datas);
        this.resList = resList;
    }

    @Override
    public int getItemResource() {
        return R.layout.share_dialog_grid_item;
    }

    @Override
    public View getItemView(int position, View convertView, SimpleBaseAdapter<String>.ViewHolder holder) {
        // 图片 */
        ImageView share_type = (ImageView) convertView.findViewById(R.id.share_type);
        // 名称 */
        TextView share_name = (TextView) convertView.findViewById(R.id.share_name);

        share_type.setImageResource(resList.get(position));
        share_name.setVisibility(View.VISIBLE);
        share_name.setText(dataList.get(position));

        return convertView;
    }

}