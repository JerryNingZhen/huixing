package com.hx.huixing.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.base.BaseApplication;
import com.android.base.configs.ConfigFile;
import com.android.base.configs.ConfigServer;
import com.android.base.configs.ConstantKey;
import com.android.base.utils.AppManagerUtil;
import com.android.base.utils.FileUtil;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.PreferencesUtil;
import com.android.base.utils.SystemUtil;
import com.android.base.utils.ToastUtil;
import com.android.base.utils.dialog.CustomDialog;
import com.android.base.utils.dialog.DialogUtil;
import com.android.base.utils.picasso.PicassoUtil;
import com.android.base.widget.AutoBgTextView;
import com.android.base.widget.TitleView;
import com.google.gson.Gson;
import com.hx.huixing.R;
import com.hx.huixing.activityMvp.BasePresenter;
import com.hx.huixing.bean.MyInfoBean;
import com.hx.huixing.bean.UserBean;
import com.hx.huixing.common.net.JsonCallBack;
import com.hx.huixing.common.net.RetrofitUtils;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

/**
 * <br> Description 设置界面
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.activity
 * <br> Date: 2018/7/19
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    /** 标题 */
    private TitleView title_view;
    /** 个人资料 */
    private AutoBgTextView tv_personal_info;
    /** 账号设置 */
    private AutoBgTextView tv_account_set;
    /** 关于彗星 */
    private AutoBgTextView tv_about_huixing;
    /** 当前版本 */
    private AutoBgTextView tv_current_version;
    /** 清除缓存 */
    private AutoBgTextView tv_clean_cache;
    /** 退出 */
    private AutoBgTextView tv_exit;
    /** 显示版本号 */
    private TextView tv_version;
    private TextView tv_size; //缓存大小
    private RelativeLayout rl_version;
    private RelativeLayout rl_clean; //清除缓存

    private MyInfoBean bean = null;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void findViews() {
        title_view = findViewById(R.id.title_view);

        tv_personal_info = findViewById(R.id.tv_personal_info);
        tv_account_set = findViewById(R.id.tv_account_set);
        tv_about_huixing = findViewById(R.id.tv_about_huixing);
        tv_current_version = findViewById(R.id.tv_current_version);
        tv_clean_cache = findViewById(R.id.tv_clean_cache);

        tv_size = findViewById(R.id.tv_size);
        tv_exit = findViewById(R.id.tv_exit);
        tv_version = findViewById(R.id.tv_version);
        rl_version = findViewById(R.id.rl_version);
        rl_clean = findViewById(R.id.rl_clean);
    }

    @Override
    protected void initGetData() {
        getInfo();
    }

    @Override
    protected void init() {
        title_view.setLeftBtnImg();
        title_view.setTitle(R.string.setting);
        getFileSize();
    }

    @Override
    protected void widgetListener() {
        tv_personal_info.setOnClickListener(this);
        tv_account_set.setOnClickListener(this);
        tv_about_huixing.setOnClickListener(this);
        tv_clean_cache.setOnClickListener(this);
        tv_exit.setOnClickListener(this);
        rl_version.setOnClickListener(this);
        rl_clean.setOnClickListener(this);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    /** 点击 */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_personal_info: //个人资料
                Bundle bundle = new Bundle();
                bundle.putSerializable("info", bean);
                IntentUtil.gotoActivity(this, PersonalInfoActivity.class, bundle);
                break;

            case R.id.tv_account_set: //修改密码
                IntentUtil.gotoActivity(this, ModifyPwdActivity.class);
                break;

            case R.id.tv_about_huixing: //关于彗星
                IntentUtil.gotoActivity(this, AboutHXActivity.class);
                break;

            case R.id.rl_version: //当前版本
                tv_version.setText("V" + SystemUtil.getAppVersionCode());
                break;

            case R.id.rl_clean: //清除缓存
                FileUtil.deleteFolderFile(ConfigFile.PATH_DOWNLOAD,true);
                getFileSize();
                ToastUtil.showToast(this, getString(R.string.clear_cache_success));
                break;

            case R.id.tv_exit: //退出
                DialogUtil.showMessageDg(this, "提示", "确定要退出当前账号？", getString(R.string.cancel), getString(R.string.sure), null, new CustomDialog.OnDialogClickListener() {
                    @Override
                    public void onClick(CustomDialog dialog, int id, Object object) {
                        dialog.dismiss();
                        logOut();
                    }
                });
                break;

        }
    }

    /** 退出 */
    private void logOut(){
        UserBean bean = BaseApplication.getInstance().getUserInfoBean();
        bean.setId("");
        PreferencesUtil.remove("token");
        PreferencesUtil.put(ConstantKey.SP_KEY_USER_INFO, bean);
        AppManagerUtil.getAppManager().finishAllActivity();
        IntentUtil.gotoActivityToTopAndFinish(this, LoginActivity.class);
    }

    /**
     * 获取缓存大小
     */
    private void getFileSize(){
        File file = new File(ConfigFile.PATH_LOG);
        if (file!=null){
            long size = FileUtil.getFolderSize(file);
            double dSize = Double.parseDouble(size+"");
            tv_size.setText( FileUtil.getFormatSize(dSize));
        }else {
            tv_size.setText( "0M");
        }

    }

    /** 获取个人信息 */
    private void getInfo(){
        Map<String, String> map = new TreeMap<>();
        map.put("userId", BaseApplication.getInstance().getUserInfoBean().getId());
        map.put("loginUser", BaseApplication.getInstance().getUserInfoBean().getId());
        RetrofitUtils.getInstance().normalGet(ConfigServer.SERVER_API_URL + ConfigServer.MOTHED_QUARYUSERS, map, new JsonCallBack() {
            @Override
            public void next(String response) {
                 bean= new Gson().fromJson(response, MyInfoBean.class);
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
