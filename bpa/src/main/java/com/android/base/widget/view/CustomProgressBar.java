package com.android.base.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amos.custom.progress.CustomProgressView;
import com.hx.huixing.R;
import com.android.base.utils.ScreenUtil;


/**
 * 等待加载进度条
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class CustomProgressBar extends BaseCustomView {

    /** 等待信息 */
    private TextView txt_Progress;
    /** 省略号点点 */
    private TextView txt_point;
    private ProgressBar progressBar;

    private CustomProgressView progressBarView;

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomProgressBar(Context context) {
        super(context);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.view_progress_bar;
    }

    @Override
    protected void findViews() {
        txt_Progress = findViewByIds(R.id.custom_dialog_txt_progress);
        txt_point = findViewByIds(R.id.txt_point);
        progressBar = findViewByIds(R.id.progressbar);
        progressBarView = findViewByIds(R.id.progress_view);

        viewParent.post(new Runnable() {
            @Override
            public void run() {
                LinearLayout custom_view_progressbar = findViewByIds(R.id.custom_view_progressbar);
                custom_view_progressbar.setPadding(0, ScreenUtil.getScreenHeightPx() / 5, 0, ScreenUtil.getScreenHeightPx() / 5);
                // ((LinearLayout.LayoutParams) progressBarView.getLayoutParams()).setMargins(0, ScreenUtil.getScreenHeightPx() / 5, 0, 0);
            }
        });
    }

    @Override
    protected void init() {

    }

    @Override
    protected void widgetListener() {

    }

    //
    //    public void setProgress(int progress) {
    //        if (null != progressBar) {
    //            progressBar.setProgress(progress);
    //        }
    //    }

    public void setTxt(int stringResId) {
        if (null != txt_Progress) {
            txt_Progress.setText(stringResId);
        }
    }
}