package com.hx.huixing.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.android.base.BaseApplication;
import com.android.base.configs.ConfigServer;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.ToastUtil;
import com.android.base.widget.AutoBgButton;
import com.android.base.widget.TitleView;
import com.google.gson.Gson;
import com.hx.huixing.R;
import com.hx.huixing.activityMvp.contract.FindPwdContract;
import com.hx.huixing.activityMvp.presenter.FindPwdPresenter;
import com.hx.huixing.bean.CountBean;
import com.hx.huixing.common.net.JsonCallBack;
import com.hx.huixing.common.net.RetrofitUtils;
import com.hx.huixing.widget.TimeButton;

import java.util.Map;
import java.util.TreeMap;

/**
 * <br> Description 找回密码
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.activity
 * <br> Date: 2018/7/18
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class FindPasswordActivity extends BaseActivity<FindPwdContract.FindPwdPresenter>
        implements FindPwdContract.FindPwdView, View.OnClickListener {

    /** 标题 */
    private TitleView title_view;
    /** 手机号输入框  */
    private EditText et_number;
    /** 密码输入框 */
    private EditText et_password;
    /** 验证码输入 */
    private EditText et_verify_code;
    /** 验证码按钮 */
    private TimeButton btn_code;
    /**  */
    private AutoBgButton btn_find;

    //倒计时长，单位秒
    private int countdownSecond = 60;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_find_password;
    }

    @Override
    protected void findViews() {
        title_view = findViewById(R.id.title_view);
        et_number = findViewById(R.id.et_number);
        et_password = findViewById(R.id.et_password);
        et_verify_code = findViewById(R.id.et_verify_code);
        btn_code = findViewById(R.id.btn_getcode);
        btn_find = findViewById(R.id.btn_find);
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void init() {
        title_view.setLeftBtnImg();
        title_view.setTitle("找回密码");

        btn_code.setLength(countdownSecond);
        btn_code.setTextBefore("获取验证码");
        btn_code.setTextAfter("s");
    }

    @Override
    protected void widgetListener() {
        btn_code.setOnClickListener(this);
        btn_find.setOnClickListener(this);
    }

    @Override
    public FindPwdContract.FindPwdPresenter initPresenter() {
        return new FindPwdPresenter(this);
    }

    @Override
    public void getToken(String token) {
        btn_code.startSchedule();
        String phone = et_number.getText().toString().trim();
        mPresenter.getVerCode(phone, token);
    }

    @Override
    public void onFail() {
        btn_code.clearTimer();
        btn_code.setCanClick();
        btn_code.setText("获取验证码");
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showToast(BaseApplication.getInstance().getApplicationContext(), msg);
    }

    @Override
    public void onSuccess() {
        showToast("操作成功");
        IntentUtil.gotoActivity(this, LoginActivity.class);
        finishActivity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_getcode:
                String phoneNo = et_number.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNo)){
                    ToastUtil.showLongToast(this, getString(R.string.empty_number));
                    return;
                }
                if (phoneNo.length() == 11){
                    et_verify_code.requestFocus();
                }

                mPresenter.getToken();
                break;

            case R.id.btn_find:
                if (checkEmpty()){
                    finPwd();
                }

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        btn_code.onDestroy();
    }


    private boolean checkEmpty(){
        String num = et_number.getText().toString().trim();
        String newPwd = et_password.getText().toString().trim();
        String verCode = et_verify_code.getText().toString().trim();
        if (TextUtils.isEmpty(num)){
            ToastUtil.showToast(this,getString(R.string.empty_number));
            return false;
        }

        if (TextUtils.isEmpty(newPwd)){
            ToastUtil.showToast(this, getString(R.string.empty_pwd));
            return false;
        }

        if (TextUtils.isEmpty(verCode)){
            ToastUtil.showToast(this, "验证码不能为空！");
            return false;
        }
        return true;
    }

    /**
     * 修改密码
     */
    private void finPwd(){
        Map<String,String> map = new TreeMap<>();
        map.put("userName", et_number.getText().toString().trim());
        map.put("code", et_verify_code.getText().toString().trim());
        map.put("newPassword", et_password.getText().toString().trim());
        map.put("type","2");
        RetrofitUtils.getInstance().normalGet(ConfigServer.SERVER_API_URL + ConfigServer.METHOD_RECOVERPASSWORD, map, new JsonCallBack() {
            @Override
            public void next(String response) {
                CountBean bean = new Gson().fromJson(response, CountBean.class);
                if (bean.getCode().equals("0")){
                    ToastUtil.showToast(BaseApplication.getInstance().getApplicationContext(), "修改成功");
                    IntentUtil.gotoActivityAndFinish(FindPasswordActivity.this, LoginActivity.class);
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
