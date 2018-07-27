package com.android.base;

import android.app.Application;

import com.android.base.bean.LocationBean;
import com.android.base.configs.ConstantKey;
import com.android.base.utils.AppCrashUtil;
import com.android.base.utils.FileUtil;
import com.android.base.utils.NotificationUtil;
import com.android.base.utils.PreferencesUtil;
import com.hx.huixing.bean.UserBean;
import com.mob.MobSDK;


/**
 * 全局公用Application类
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月24日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class BaseApplication extends Application {

    /** 全局TApplication  获取全局上下文，可用于文本、图片、sp数据的资源加载，不可用于视图级别的创建和展示 */
    protected static BaseApplication application;
    /** 定位信息bean */
    public LocationBean locationBean;

    /**
     * 获取全局TApplication
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/3/7 17:51
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/3/7 17:51
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return BaseApplication
     */
    public static BaseApplication getInstance() {
        if (application == null) {
            synchronized (BaseApplication.class) {
                if (application == null) {
                    application = new BaseApplication();
                }
            }
        }
        return application;
    }

    /**
     * 整个应用程序的初始入口函数
     * <p>
     * 本方法内一般用来初始化程序的全局数据，或者做应用的长数据保存取回操作
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月24日, 下午2:12:17
     * <br> UpdateTime: 2016年12月24日, 下午2:12:17
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容, 若无修改可不写.)
     */
    @Override
    public void onCreate() {
        application = this;
        NotificationUtil.cancelAll();
        //applicationId要和包路径相同，才能正常重启APP WelcomeActivity
        initData("");
        String phone = (String) PreferencesUtil.get(ConstantKey.SP_KEY_LOGIN_PHONE, "");
        ConstantKey.initPrivateKey(phone);
        //        MobSDK.init(this);
        //        MobSDK.init(this, "你的AppKey", "你的AppSecret");
        MobSDK.init(this, "18b434ca85a74", "fdcedac1cd54c744a21f833b15707b37");
        super.onCreate();
    }

    /**
     * 初始化数据
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-29,下午10:47:29
     * <br> UpdateTime: 2016-12-29,下午10:47:29
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容, 若无修改可不写.)
     *
     * @param activityName
     *         程序出现异常后的跳转界面的名字 ;activityName为null时 直接退出app。
     */
    private void initData(String activityName) {
        locationBean = new LocationBean();

        // 错误日志捕捉工具WelcomeActivity
        Thread.setDefaultUncaughtExceptionHandler(AppCrashUtil.getAppExceptionHandler(activityName));
        // 本地文件构建
        FileUtil.createAllFile();
        //// 实例化GalleryFinal
        //initGalleryFinal(this);
        //// 实例化极光推送
        //JPushUtil.getInstance(this).setAlias("123456789amos21");
        //// 实例化LeakCanary
        //LeakCanary.install(this);

    }

    /** 用户信息bean */
    private UserBean userInfoBean;


    public UserBean getUserInfoBean() {
        if (userInfoBean == null) {
            userInfoBean = (UserBean) PreferencesUtil.get(ConstantKey.SP_KEY_USER_INFO, null);
        }
        return userInfoBean;
    }

    public void setUserInfoBean(UserBean userInfoBean) {
        this.userInfoBean = userInfoBean;
        saveUserInfoBean();
    }

    //    public String getToken() {
    //        if (getUserInfoBean() != null) {
    //            return userInfoBean.getUserPwd();
    //        }
    //        return "";
    //    }
    public  void saveUserInfoBean() {
        PreferencesUtil.put(ConstantKey.SP_KEY_USER_INFO, getInstance().userInfoBean);
    }

}