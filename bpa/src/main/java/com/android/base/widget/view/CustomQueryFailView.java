package com.android.base.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hx.huixing.R;
import com.android.base.utils.ScreenUtil;


/**
 * 网络异常 提示页面
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class CustomQueryFailView extends BaseCustomView {

    private TextView query_fail_txt;

    private ImageView query_fail_img;

    public CustomQueryFailView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomQueryFailView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomQueryFailView(Context context) {
        super(context);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.view_query_fail;
    }

    @Override
    protected void findViews() {
        query_fail_txt = findViewByIds(R.id.query_fail_txt);
        query_fail_img = findViewByIds(R.id.query_fail_img);
        viewParent.post(new Runnable() {
            @Override
            public void run() {
                LinearLayout custom_view_query_fail = findViewByIds(R.id.custom_view_query_fail);
                custom_view_query_fail.setPadding(0, ScreenUtil.getScreenHeightPx() / 6, 0, ScreenUtil.getScreenHeightPx() / 6);
                //((LinearLayout.LayoutParams) query_fail_img.getLayoutParams()).setMargins(0, ScreenUtil.getScreenHeightPx() / 6, 0, 0);
            }
        });
        setTxt(R.string.string_query_fail);
        setImg(R.drawable.icon_query_fail);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void widgetListener() {

    }

    public void setImg(int imgResId) {
        if (null != query_fail_img) {
            if (imgResId > 0) {
                query_fail_img.setImageResource(imgResId);
            } else {
                query_fail_img.setImageResource(R.drawable.icon_nocontent);
            }

        }
    }

    public void setTxt(int stringResId) {
        if (null != query_fail_txt) {
            if (stringResId > 0) {
                query_fail_txt.setText(stringResId);
            } else {
                query_fail_txt.setText(R.string.string_no_data);
            }
        }
    }
}