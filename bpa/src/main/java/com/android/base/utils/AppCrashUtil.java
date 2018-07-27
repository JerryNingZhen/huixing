package com.android.base.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.base.BaseApplication;
import com.hx.huixing.R;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 应用程序异常类：用于捕获异常、并做出相应处理
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class AppCrashUtil implements UncaughtExceptionHandler {

    /** 默认处理方式 */
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    /** 程序出现异常后的跳转界面 ,为null时 直接退出app */
    private static String mActivityName;
    /** 异常类对象 */
    private static AppCrashUtil instance;
    /** Context */
    private static Context context;

    /**
     * 无参数构造方法
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-25,上午11:34:11
     * <br> UpdateTime: 2016-12-25,上午11:34:11
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private AppCrashUtil() {
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    /**
     * 单例:获取APP异常崩溃处理对象
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-25,上午11:34:26
     * <br> UpdateTime: 2016-12-25,上午11:34:26
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param activityName
     *         程序出现异常后的跳转界面的名字 ;activityName为null时 直接退出app。
     *
     * @return 程序异常类
     */
    public static AppCrashUtil getAppExceptionHandler(String activityName) {
        mActivityName = activityName;
        if (instance == null) {
            instance = new AppCrashUtil();
            context = BaseApplication.getInstance().getApplicationContext();
        }
        return instance;
    }

    /**
     * 异常处理
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-25,上午11:27:24
     * <br> UpdateTime: 2016-12-25,上午11:27:24
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param thread
     *         捕获的异常的线程
     * @param ex
     *         抛出的异常
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(500); // 1秒后重启，可有可无，仅凭个人喜好
                // mActivityName = "com.amos.bpademo.baseUI.WelcomeActivity";
                if (!TextUtils.isEmpty(mActivityName)) {
                    // mActivityName = context.getPackageName() + ".baseUI." + mActivityName;
                    Intent intent = new Intent(context, Class.forName(mActivityName));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 退出程序
            AppManagerUtil.getAppManager().exitApp();
        }
    }

    /**
     * 自定义异常处理
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-25,上午11:36:36
     * <br> UpdateTime: 2016-12-25,上午11:36:36
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param ex
     *         抛出的异常信息
     *
     * @return true:处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                if (!TextUtils.isEmpty(mActivityName)) {
                    // mActivityName = context.getPackageName() + ".baseUI." + mActivityName;
                    // 程序出现未知错误，即将重启
                    // ToastUtil.showToast(context, mActivityName + "\n" + context.getString(R.string.tips_app_will_restart));
                    Toast.makeText(context, context.getString(R.string.tips_app_will_restart), Toast.LENGTH_SHORT).show();
                } else {
                    // 程序出现未知错误，即将重启
                    // ToastUtil.showToast(context, context.getString(R.string.tips_app_will_exit));
                    Toast.makeText(context, context.getString(R.string.tips_app_will_exit), Toast.LENGTH_SHORT).show();
                }
                Looper.loop();
            }
        }.start();
        // 保存错误日志
        LogUtil.saveErrorLog(ex);
        // 打印错误日志
        ex.printStackTrace();

        return true;
    }
}