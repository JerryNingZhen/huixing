package com.hx.huixing.activity;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.base.BaseApplication;
import com.android.base.configs.ConfigServer;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.SystemUtil;
import com.android.base.utils.ToastUtil;
import com.android.base.widget.AutoBgButton;
import com.android.base.widget.TitleView;
import com.google.gson.Gson;
import com.hx.huixing.R;
import com.hx.huixing.activityMvp.BasePresenter;
import com.hx.huixing.bean.TokenEntity;
import com.hx.huixing.common.net.JsonCallBack;
import com.hx.huixing.common.net.RetrofitUtils;
import com.hx.huixing.widget.TimeButton;

import java.util.Map;
import java.util.TreeMap;

/**
 * <br> Description 修改密码
 * <br> Author: 谭俊
 * <br> PackageName com.tan.mvpdemo.activityMvp
 * <br> Date: 2018/7/22
 */
public class ModifyPwdActivity extends BaseActivity implements View.OnClickListener {

    /** 标题 */
    private TitleView title_view;
    /** 当前手机号（要拼接弄成一个串） */
    private TextView tv_cur_number;
    /** 验证码 */
    private EditText et_verify_code;
    /** 验证码按钮 */
    private TimeButton btn_getcode;
    /** 新密码 */
    private EditText et_password;
    /** 修改按钮 */
    private Button btn_modify;

    String tel;
    String token;
    /**  */
    String verifyCode;
    String newPwd;

    //倒计时长，单位秒
    private int countdownSecond = 60;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_modify;
    }

    @Override
    protected void findViews() {
        title_view = findViewById(R.id.title_view);
        tv_cur_number = findViewById(R.id.tv_cur_number);
        et_verify_code = findViewById(R.id.et_verify_code);
        btn_getcode = findViewById(R.id.btn_getcode);
        et_password = findViewById(R.id.et_password);
        btn_modify = findViewById(R.id.btn_modify);
    }

    @Override
    protected void initGetData() {
        tel = BaseApplication.getInstance().getUserInfoBean().getUserName();
    }

    @Override
    protected void init() {
        title_view.setLeftBtnImg();
        title_view.setTitle(R.string.modify_pwd);
        tv_cur_number.setText(getString(R.string.bind_number) + tel);

        btn_getcode.setLength(countdownSecond);
        btn_getcode.setTextBefore("获取验证码");
        btn_getcode.setTextAfter("s");

    }

    @Override
    protected void widgetListener() {
        btn_getcode.setOnClickListener(this);
        btn_modify.setOnClickListener(this);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_getcode:
                getToken();
                break;
            case R.id.btn_modify:
                if (checkEmpty()){
                    modifyPwd();
                }
                break;
        }
    }

    /** token */
    private void getToken(){
        Map<String, String> map = new TreeMap<>();
        map.put("appid", SystemUtil.getIMEI());
        RetrofitUtils.getInstance().normalGet(ConfigServer.SERVER_API_URL + ConfigServer.method_getAppToken,
                map, new JsonCallBack() {
                    @Override
                    public void next(String response) {
                        btn_getcode.startSchedule();
                        TokenEntity entity = new Gson().fromJson(response, TokenEntity.class);
                        token =entity.getDatas().getToken();
                        getVerCode(token);
                    }

                    @Override
                    public void error(Throwable e) {
                        btn_getcode.clearTimer();
                        btn_getcode.setCanClick();
                        btn_getcode.setText("获取验证码");
                    }

                    @Override
                    public void startLoading() {

                    }

                    @Override
                    public void closeLoading() {

                    }
                });

    }

    /** 验证码 */
    private void getVerCode(String token){
        Map<String, String> map = new TreeMap<>();
        map.put("appid", SystemUtil.getIMEI());
        map.put("phoneNo",tel);
        map.put("token", token);
        RetrofitUtils.getInstance().normalGet(ConfigServer.SERVER_API_URL + ConfigServer.METHOD_GETAPPCODE,
                map, new JsonCallBack() {
                    @Override
                    public void next(String response) {
                        ToastUtil.showToast(ModifyPwdActivity.this, "验证码已发送！");
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

    /** 修改密码 */
    private void modifyPwd(){
        Map<String, String> map = new TreeMap<>();
        map.put("userId", BaseApplication.getInstance().getUserInfoBean().getId());
        map.put("tel",tel);
        map.put("passWord", BaseApplication.getInstance().getUserInfoBean().getUserPwd());
        map.put("realName", BaseApplication.getInstance().getUserInfoBean().getRealName());
        map.put("code", verifyCode);
        map.put("newPassword", newPwd);

        RetrofitUtils.getInstance().normalGet(ConfigServer.SERVER_API_URL + ConfigServer.METHOD_CHANGEPASSWORD, map
                , new JsonCallBack() {
                    @Override
                    public void next(String response) {
                        ToastUtil.showToast(ModifyPwdActivity.this, "修改成功，请重新登录！");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                IntentUtil.gotoActivityAndFinish(ModifyPwdActivity.this, LoginActivity.class);
                            }
                        }, 1500);

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

    private boolean checkEmpty(){
        verifyCode = et_verify_code.getText().toString().trim();
        newPwd = et_password.getText().toString().trim();

        if (TextUtils.isEmpty(verifyCode)){
            ToastUtil.showToast(this, getString(R.string.vercode_number));
            return false;
        }

        if (TextUtils.isEmpty(newPwd)){
            ToastUtil.showToast(this, getString(R.string.empty_new_pwd));
            return false;
        }
        return true;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        btn_getcode.onDestroy();
    }
}
