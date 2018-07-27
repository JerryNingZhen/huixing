package com.android.base.executor;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
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
public class DownProgressDialogUtil {

    /** 等待提示框对象 */
    private Dialog progressDialog;
    /** 下载进度 */
    private ProgressBar view_progress;
    /** 标题 */
    private TextView txt_title;
    /** 更新信息 */
    private TextView txt_content;
    /** 等待信息 */
    private TextView txt_Progress;


    public DownProgressDialogUtil(Context context) {
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
        progressDialog = new Dialog(context, R.style.dialog_style);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        View view = View.inflate(context, R.layout.view_loading_dialog_progress, null);

        Window window = progressDialog.getWindow(); // 得到对话框
        if (window != null) {
            window.setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画
        }
        view_progress = (ProgressBar) view.findViewById(R.id.view_progress);
        txt_Progress = (TextView) view.findViewById(R.id.txt_progress);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_content = (TextView) view.findViewById(R.id.txt_content);

        progressDialog.setContentView(view);
    }


    public void setProgress(int progress) {
        view_progress.setProgress(progress);
        txt_Progress.setText(progress + "%");
    }

    /**
     * 显示等待对话框
     *
     * @param isCanceledOnTouchOutside
     *         收点击dialog 之外 dialog消失
     */
    public void showDialog(String title, String info, Boolean isCanceledOnTouchOutside) {
        if (!TextUtils.isEmpty(title)) {
            txt_title.setText(title);
        }

        if (!TextUtils.isEmpty(info)) {
            txt_content.setText((info));
        } else {
            txt_content.setVisibility(View.GONE);
        }

        txt_Progress.setText("0%");

        progressDialog.setCancelable(isCanceledOnTouchOutside);
        progressDialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);

        dismissDialog();
        progressDialog.show();
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
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
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

}