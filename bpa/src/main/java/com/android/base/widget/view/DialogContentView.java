package com.android.base.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.android.base.interfaces.OnDialogViewCallBack;
import com.android.base.utils.dialog.CustomDialog;
import com.hx.huixing.R;

import java.util.HashMap;


/**
 * 提示页面(进度条、网络异常、无网络、无数据)
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class DialogContentView extends BaseCustomView {

    public TextView txt_share;
    public TextView txt_edit;
    public TextView txt_del;
    public TextView txt_cancel;
    public CustomDialog customDialog;
    public OnDialogViewCallBack callBack;

    public DialogContentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DialogContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DialogContentView(Context context, CustomDialog customDialog, OnDialogViewCallBack callBack) {
        super(context);
        this.customDialog = customDialog;
        this.callBack = callBack;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.view_dialog_content_custom;
    }

    @Override
    protected void findViews() {
        txt_share = findViewByIds(R.id.txt_share);
        txt_edit = findViewByIds(R.id.txt_edit);
        txt_del = findViewByIds(R.id.txt_del);
        txt_cancel = findViewByIds(R.id.txt_cancel);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void widgetListener() {

        View.OnClickListener onClickListener = new View.OnClickListener() {
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.txt_share://
                        HashMap<String, String> map = new HashMap<>();
                        map.put("position", "share");
                        callBack.onResult(map);
                        break;
                    case R.id.txt_edit://
                        map = new HashMap<>();
                        map.put("position", "edit");
                        callBack.onResult(map);
                        break;
                    case R.id.txt_del://
                        map = new HashMap<>();
                        map.put("position", "del");
                        callBack.onResult(map);
                        break;
                    case R.id.txt_cancel://
                        customDialog.dismiss();
                        break;

                    default:// 默认
                        break;
                }
            }
        };

        txt_share.setOnClickListener(onClickListener);
        txt_edit.setOnClickListener(onClickListener);
        txt_del.setOnClickListener(onClickListener);
        txt_cancel.setOnClickListener(onClickListener);
    }
}