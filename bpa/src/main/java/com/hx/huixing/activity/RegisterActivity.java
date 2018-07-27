package com.hx.huixing.activity;

import android.text.TextUtils;
import android.view.View;

import com.android.base.utils.IntentUtil;
import com.android.base.utils.ToastUtil;
import com.android.base.widget.AutoBgButton;
import com.android.base.widget.TitleView;
import com.hx.huixing.R;
import com.hx.huixing.activityMvp.contract.RegisterContract;
import com.hx.huixing.activityMvp.presenter.RegisterPresenter;
import com.hx.huixing.widget.ContainsEmojiEditText;
import com.hx.huixing.widget.TimeButton;

/**
 * <br> Description 注册
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.activity
 * <br> Date: 2018/7/16
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class RegisterActivity extends BaseActivity<RegisterContract.RegisterPresenter>
        implements View.OnClickListener,RegisterContract.RegisterView {

    /** 标题 */
    private TitleView titleView;
    /** 手机输入框 */
    private ContainsEmojiEditText et_login_username;
    /** 密码输入框 */
    private ContainsEmojiEditText et_login_password;
    /** 验证码输入框 */
    private ContainsEmojiEditText et_verify_code;
    /** 昵称 */
    private ContainsEmojiEditText et_real_name;
    /** 获取验证码点击 */
    private TimeButton btn_getcode;
    /** 注册按钮 */
    private AutoBgButton btn_register;

    private String password = "";
    private String verCode = "";

    private String token = "";

    long firstTime; //按下验证码时间
    //倒计时长，单位秒
    private int countdownSecond = 60;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void findViews() {
        titleView = findViewById(R.id.title_view);
        et_login_username = findViewById(R.id.et_login_username);
        et_login_password = findViewById(R.id.et_login_password);
        et_verify_code = findViewById(R.id.et_verify_code);
        et_real_name = findViewById(R.id.et_real_name);
        btn_getcode = findViewById(R.id.btn_getcode);
        btn_register = findViewById(R.id.btn_register);
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void init() {
        titleView.setLeftBtnImg();
        titleView.setTitle(getString(R.string.register_new_user));

        btn_getcode.setLength(countdownSecond);
        btn_getcode.setTextBefore("获取验证码");
        btn_getcode.setTextAfter("s");
    }

    @Override
    protected void widgetListener() {
        btn_getcode.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    /** 初始化P层 */
    @Override
    public RegisterContract.RegisterPresenter initPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_getcode: //获取验证码
                String phoneNo = et_login_username.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNo)){
                    ToastUtil.showLongToast(this, getString(R.string.empty_number));
                    return;
                }
                if (phoneNo.length() == 11){
                    et_verify_code.requestFocus();
                }

                mPresenter.getToken();
                break;

            case R.id.btn_register: //注册
                String phoneNo2 = et_login_username.getText().toString().trim();
                password = et_login_password.getText().toString().trim();
                String realName = et_real_name.getText().toString().trim();
                verCode = et_verify_code.getText().toString().trim();

                if (TextUtils.isEmpty(phoneNo2)){
                    ToastUtil.showLongToast(this, getString(R.string.empty_number));
                    return;
                }

                if (password.length()<6 || password.length()>16){
                    ToastUtil.showToast(this, getString(R.string.password_length));
                    return;
                }

                if (TextUtils.isEmpty(verCode)){
                    ToastUtil.showToast(this, getString(R.string.vercode_number));
                    return;
                }

                mPresenter.registerUser(realName, phoneNo2, password, phoneNo2, verCode);

                break;
        }

    }

    @Override
    public void getToken(String token) {
        btn_getcode.startSchedule();
        String phoneNo = et_login_username.getText().toString().trim();
        mPresenter.getVerCode(phoneNo, token);
    }

    @Override
    public void onFail() {
        btn_getcode.clearTimer();
        btn_getcode.setCanClick();
        btn_getcode.setText("获取验证码");
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showLongToast(this, msg);
    }

    @Override
    public void onSuccess() {
        showToast("注册成功");
        IntentUtil.gotoActivity(this, LoginActivity.class);
        finishActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        btn_getcode.onDestroy();
    }
}
