package com.hx.huixing.activity;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.android.base.BaseApplication;
import com.android.base.configs.ConstantKey;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.PreferencesUtil;
import com.android.base.utils.ToastUtil;
import com.android.base.widget.AutoBgButton;
import com.android.base.widget.TitleView;
import com.hx.huixing.R;
import com.hx.huixing.activityMvp.contract.LoginContract;
import com.hx.huixing.activityMvp.presenter.LoginPresenter;
import com.hx.huixing.bean.UserBean;

/**
 * <br> Description 登录
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.activity
 * <br> Date: 2018/7/16
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class LoginActivity extends BaseActivity<LoginContract.LoginPresenter>
        implements LoginContract.LoginView {

    private TitleView title_view;
    private EditText et_login_username;
    private EditText et_pwd;
    private AutoBgButton btn_login;

    private UserBean userBean;

    private Button btn_forget;

    private String userName;

    private ToggleButton btn_toggle;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void findViews() {
        title_view = findViewById(R.id.title_view);
        btn_toggle = findViewById(R.id.btn_toggle);
        btn_forget = findViewById(R.id.btn_forget);
        et_login_username = findViewById(R.id.et_login_username);
        et_pwd = findViewById(R.id.et_pwd);
        btn_login = findViewById(R.id.btn_login);
    }

    @Override
    protected void initGetData() {
        title_view.setTitle(R.string.login);
        title_view.setRightBtnTxtColor(R.color.content_blue);
        title_view.setRightBtnTxt(getString(R.string.go_register), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.gotoActivity(LoginActivity.this, RegisterActivity.class);
            }
        });

    }

    @Override
    protected void init() {
        String phone = (String) PreferencesUtil.get(ConstantKey.SP_KEY_LOGIN_PHONE, "");
        et_login_username.setText(phone);
    }

    @Override
    protected void widgetListener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = et_login_username.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();
                if (TextUtils.isEmpty(userName)) {
                    showToast(getString(R.string.empty_number));
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    showToast(getString(R.string.empty_pwd));
                    return;
                }

                mPresenter.login(userName, pwd);
            }
        });

        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.gotoActivity(LoginActivity.this, FindPasswordActivity.class);
            }
        });

        btn_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    /** 显示 */
                    et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    /** 隐藏 */
                    et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }


    @Override
    public LoginContract.LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }


    @Override
    public void getUserInfo(UserBean userBean) {
        this.userBean = userBean;
        /** 保存用户信息 */
        //BaseApplication.getInstance().saveUserInfoBean();
        BaseApplication.getInstance().setUserInfoBean(userBean);
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public void onSuccess() {
        PreferencesUtil.put(ConstantKey.SP_KEY_LOGIN_PHONE, et_login_username.getText().toString());
        IntentUtil.gotoActivity(this, MainActivity.class);
        finishActivity();
    }
}
