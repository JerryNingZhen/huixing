package com.hx.huixing.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.base.BaseApplication;
import com.android.base.configs.ConfigServer;
import com.android.base.utils.ToastUtil;
import com.android.base.widget.AutoBgButton;
import com.android.base.widget.TitleView;
import com.google.gson.Gson;
import com.hx.huixing.R;
import com.hx.huixing.activityMvp.BasePresenter;
import com.hx.huixing.bean.EditInfoBean;
import com.hx.huixing.common.net.JsonCallBack;
import com.hx.huixing.common.net.RetrofitUtils;
import com.hx.huixing.widget.ContainsEmojiEditText;

import java.util.Map;
import java.util.TreeMap;

/**
 * <br> Description 个人资料修改
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.activity
 * <br> Date: 2018/7/25
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class EditPersonInfoActivity extends BaseActivity {

    private TitleView title_view;
    private ContainsEmojiEditText et_name;
    private ContainsEmojiEditText et_advice;

    private TextView txt_count;
    private AutoBgButton btn_register;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_edit_info;
    }

    @Override
    protected void findViews() {
        title_view = findViewById(R.id.title_view);
        et_name = findViewById(R.id.et_name);
        et_advice = findViewById(R.id.et_advice);
        txt_count = findViewById(R.id.txt_count);
        btn_register = findViewById(R.id.btn_register);
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void init() {
        title_view.setLeftBtnImg();
        title_view.setTitle("个人资料");
    }

    @Override
    protected void widgetListener() {

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString().trim();
                String content = et_advice.getText().toString().trim();

                if (TextUtils.isEmpty(name)){
                    ToastUtil.showToast(EditPersonInfoActivity.this, "昵称不能为空");
                    return;
                }
                if (name.length() > 10){
                    ToastUtil.showToast(EditPersonInfoActivity.this, "昵称不能超过10个字");
                    return;
                }
                editInfo(name, content);
            }
        });

        et_advice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (!TextUtils.isEmpty(str)) {
                    txt_count.setText(String.valueOf((30 - str.length())) + "/30");
                } else {
                    txt_count.setText("30");
                }
            }
        });

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    private void editInfo(String name, String personIntro){
        Map<String, String> map = new TreeMap<>();
        map.put("realName", name);
        map.put("passWord", BaseApplication.getInstance().getUserInfoBean().getUserPwd());
        map.put("userId", BaseApplication.getInstance().getUserInfoBean().getId());
        map.put("personIntro", personIntro);
        RetrofitUtils.getInstance().normalGet(ConfigServer.SERVER_API_URL + ConfigServer.METHOD_CHANGEREALNAME, map, new JsonCallBack() {
            @Override
            public void next(String response) {
                Log.e("tanjun", response);
                EditInfoBean bean = new Gson().fromJson(response, EditInfoBean.class);
                if (bean.getCode().equals("0")){
                    ToastUtil.showToast(BaseApplication.getInstance().getApplicationContext(), bean.getMsg());
                    finishActivity();
                }
            }

            @Override
            public void error(Throwable e) {

            }

            @Override
            public void startLoading() {

            }

            @Override
            public void closeLoading() {

            }
        });
    }
}
