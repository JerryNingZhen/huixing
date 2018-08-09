package com.android.base.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.android.base.interfaces.OnDialogViewCallBack;
import com.android.base.utils.dialog.CustomDialog;
import com.hx.huixing.R;

import java.util.HashMap;
import java.util.Map;


/**
 * 提示页面(进度条、网络异常、无网络、无数据)
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class DialogContentEditView extends BaseCustomView {

    public EditText et_title;
    public EditText et_address;
    public View btn_left;
    public View btn_right;
    public CustomDialog customDialog;
    public OnDialogViewCallBack callBack;

    public DialogContentEditView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DialogContentEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DialogContentEditView(Context context, CustomDialog customDialog, OnDialogViewCallBack callBack) {
        super(context);
        this.customDialog = customDialog;
        this.callBack = callBack;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.view_dialog_edit;
    }

    @Override
    protected void findViews() {
        //        public EditText et_title;
        //        public EditText et_address;
        //        public  View btn_left;
        //        public  View btn_right;
        et_title = findViewByIds(R.id.et_title);
        et_address = findViewByIds(R.id.et_address);
        btn_left = findViewByIds(R.id.btn_left);
        btn_right = findViewByIds(R.id.btn_right);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void widgetListener() {

        OnClickListener onClickListener = new OnClickListener() {
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_left://
                        customDialog.dismiss();
                        break;
                    case R.id.btn_right://
                        Map<String, String> map = new HashMap<>();
                        map.put("title", et_title.getText().toString());
                        map.put("address", et_address.getText().toString());
                        callBack.onResult(map);
                        customDialog.dismiss();
                        break;

                    default:// 默认
                        break;
                }
            }
        };

        btn_left.setOnClickListener(onClickListener);
        btn_right.setOnClickListener(onClickListener);
    }
}