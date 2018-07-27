package com.android.base.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hx.huixing.R;
import com.android.base.utils.ScreenUtil;


/**
 * 没有数据 提示页面
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class CustomNoDataView extends BaseCustomView {

    private TextView no_content_txt;

    private ImageView no_content_img;

    public CustomNoDataView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomNoDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomNoDataView(Context context) {
        super(context);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.view_no_data;
    }

    @Override
    protected void findViews() {
        no_content_txt = findViewByIds(R.id.no_content_txt);
        no_content_img = findViewByIds(R.id.no_content_img);
        viewParent.post(new Runnable() {
            @Override
            public void run() {
                LinearLayout custom_view_no_data = findViewByIds(R.id.custom_view_no_data);
                custom_view_no_data.setPadding(0, ScreenUtil.getScreenHeightPx() / 6, 0, ScreenUtil.getScreenHeightPx() / 6);
                // ((LinearLayout.LayoutParams) no_content_img.getLayoutParams()).setMargins(0, ScreenUtil.getScreenHeightPx() / 6, 0, 0);
            }
        });
    }

    @Override
    protected void init() {

    }

    @Override
    protected void widgetListener() {

    }

    public void setImg(int imgResId) {
        if (null != no_content_img) {
            if (imgResId > 0) {
                no_content_img.setImageResource(imgResId);
            } else {
                no_content_img.setImageResource(R.drawable.icon_nocontent);
            }

        }
    }

    public void setTxt(int stringResId) {
        if (null != no_content_txt) {
            if (stringResId > 0) {
                no_content_txt.setText(stringResId);
            } else {
                no_content_txt.setText(R.string.string_no_data);
            }
        }
    }
}