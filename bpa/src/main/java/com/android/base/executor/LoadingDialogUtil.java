package com.android.base.executor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hx.huixing.R;

/**
 * 等待提示框封装类
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class LoadingDialogUtil {

    /** 等待提示框对象 */
    private Dialog progressDialog;
    /** 等待信息 */
    private TextView txt_Progress;
    /** 省略号点点 */
    private TextView txt_point;

    private Window window = null;
    private Context context;

    public LoadingDialogUtil(Context context) {
        init(context);
    }

    /**
     * 初始话对话框
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-23,下午3:10:26
     * <br> UpdateTime: 2016-11-23,下午3:10:26
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文Context
     */
    protected void init(Context context) {
        this.context = context;
        progressDialog = new Dialog(context, R.style.dialog_style);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        View view = View.inflate(context, R.layout.view_loading_dialog, null);

        window = progressDialog.getWindow(); // 得到对话框
        window.setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画
        if (null != window) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.y = 100;
            window.setAttributes(params);
        }

        txt_Progress = (TextView) view.findViewById(R.id.custom_dialog_txt_progress);
        txt_point = (TextView) view.findViewById(R.id.txt_point);
        //        ImageView img = (ImageView) view.findViewById(R.id.img);
        //        IosLoadingDrawable drawable = new IosLoadingDrawable();
        //        img.setImageDrawable(drawable);
        //        drawable.start();
        progressDialog.setContentView(view);
    }

    /**
     * 显示等待对话框
     *
     * @param text
     *         进度条上的文本
     * @param isCanceledOnTouchOutside
     *         收点击dialog 之外 dialog消失
     */
    public void showDialog(String text, Boolean isCanceledOnTouchOutside) {
        try {
            if (((Activity) context).isFinishing()) {
                return;
            }
            if (!TextUtils.isEmpty(text)) {
                //txt_Progress.setText(text);
            }
            progressDialog.setCancelable(isCanceledOnTouchOutside);
            progressDialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);

            // dismissDialog();
            if(!progressDialog.isShowing()){
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.sendEmptyMessage(CHANGE_TITLE_WHAT);
    }

    /**
     * 关闭等待对话框
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-23,下午3:10:40
     * <br> UpdateTime: 2016-11-23,下午3:10:40
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public void dismissDialog() {
        try {
            if (((Activity) context).isFinishing()) {
                return;
            }
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 设置对话框消失监听
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-23,下午3:10:48
     * <br> UpdateTime: 2016-11-23,下午3:10:48
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param listener
     *         对话框消失监听
     */
    public void setOnDismissListener(OnDismissListener listener) {
        if (null != progressDialog) {
            progressDialog.setOnDismissListener(listener);
        }
    }

    /** handle message what */
    private static final int CHANGE_TITLE_WHAT = 1;
    /** 省略号动画延时时长 */
    private static final int CHANGE_TITLE_DELAY_MILLIS = 500;
    /** 省略号点点的最大个数 */
    private static final int MAX_SUFFIX_NUMBER = 6;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(Looper.getMainLooper()) {
        private int num = 0;

        public void handleMessage(android.os.Message msg) {
            if (msg.what == CHANGE_TITLE_WHAT) {
                StringBuilder builder = new StringBuilder();
                if (num >= MAX_SUFFIX_NUMBER) {
                    num = 0;
                }
                num++;
                for (int i = 0; i < num; i++) {
                    builder.append("·");
                }
                txt_point.setText(builder.toString());
                if (progressDialog.isShowing()) {
                    handler.sendEmptyMessageDelayed(CHANGE_TITLE_WHAT, CHANGE_TITLE_DELAY_MILLIS);
                } else {
                    num = 0;
                }
            }
        }
    };
}