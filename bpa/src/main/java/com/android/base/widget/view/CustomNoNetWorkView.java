package com.android.base.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hx.huixing.R;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.ScreenUtil;

/**
 * 没有网络 提示页面
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class CustomNoNetWorkView extends BaseCustomView {

    private TextView no_network_txt;
    private ImageView no_network_img;
    private Button btn_setting;

    public CustomNoNetWorkView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomNoNetWorkView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomNoNetWorkView(Context context) {
        super(context);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.view_no_network;
    }

    @Override
    protected void findViews() {
        no_network_txt = findViewByIds(R.id.no_network_txt);
        no_network_img = findViewByIds(R.id.no_network_img);
        btn_setting = findViewByIds(R.id.btn_setting);
        viewParent.post(new Runnable() {
            @Override
            public void run() {
                LinearLayout custom_view_no_network = findViewByIds(R.id.custom_view_no_network);
                custom_view_no_network.setPadding(0, ScreenUtil.getScreenHeightPx() / 6, 0, ScreenUtil.getScreenHeightPx() / 6);
                // ((LinearLayout.LayoutParams) no_network_img.getLayoutParams()).setMargins(0, ScreenUtil.getScreenHeightPx() / 6, 0, 0);
            }
        });
    }

    @Override
    protected void init() {

    }

    @Override
    protected void widgetListener() {
        btn_setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToSettings(context);
            }
        });
    }

    /**
     * 跳转到系统设置界面
     */
    public static void jumpToSettings(Context context) {
        if (null != context) {
            IntentUtil.gotoWifiActivity(context);
        }
    }

    public void setImg(int imgResId) {
        if (null != no_network_img) {
            no_network_img.setImageResource(imgResId);
        }
    }

    public void setTxt(int stringResId) {
        if (null != no_network_txt) {
            no_network_txt.setText(stringResId);
        }
    }
}