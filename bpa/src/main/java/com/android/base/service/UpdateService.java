package com.android.base.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;

import com.android.base.bean.ResponseBean;
import com.android.base.configs.BroadcastFilters;
import com.android.base.configs.ConfigFile;
import com.android.base.configs.ConstantKey;
import com.android.base.executor.BaseTask;
import com.android.base.executor.RequestExecutor;
import com.android.base.interfaces.OnDownLoadCallBack;
import com.android.base.mvp.model.HttpBiz;
import com.android.base.utils.ApkUtil;
import com.android.base.utils.EmptyCheckUtil;
import com.android.base.utils.LogUtil;
import com.android.base.utils.NotificationUtil;
import com.android.base.utils.StringUtil;
import com.hx.huixing.BuildConfig;
import com.hx.huixing.R;

import java.io.File;

/**
 * 下载线程
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class UpdateService extends Service {

    /** 当通知ID */
    private static final int NOTIFICATION_DOWNLOAD_ID = 1992020811;
    /** apk包的路径 */
    private String downLoadUrl;
    /** 当前下载百分比 */
    private int currentPercent = 0;
    /** 第一次启动service */
    private boolean isFirst = true;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver();
        showNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isFirst) {
            isFirst = false;
            try {
                if (intent.getExtras() != null) {
                    downLoadUrl = intent.getExtras().getString(ConstantKey.INTENT_KEY_APK_PATH);
                    downLoadUrl = "http://fzd-10017606.cos.myqcloud.com/pkg/app-fzdV3_0_1.apk";
                    if (!EmptyCheckUtil.isEmpty(downLoadUrl)) {
                        downloadFile(downLoadUrl);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 下载文件.下载文件存储至指定路径.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-22,下午5:53:11
     * <br> UpdateTime: 2016-12-22,下午5:53:11
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         下载路径.
     */
    public void downloadFile(final String url) {

        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                return HttpBiz.getInstance().downLoadFile(url, new OnDownLoadCallBack() {
                    /**
                     * 任务正在执行的时候调用
                     */
                    @Override
                    public void ResultProgress(int press, long fileSize, long finishSize) {
                        currentPercent = press;
                        updateNotification();
                    }
                });
            }

            @Override
            public void onSuccess(ResponseBean result) {
                finishDown();
            }

            @Override
            public void onFail(ResponseBean result) {
                loadError();
            }
        });
    }

    /** 通知栏服务 Notification管理 */
    private NotificationManager mNotificationManager;
    /** 通知栏对象 */
    private NotificationCompat.Builder mBuilder;

    /**
     * 显示通知栏
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-25,上午10:19:38
     * <br> UpdateTime: 2016-11-25,上午10:19:38
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void showNotification() {
        // 在顶部常驻:Notification.FLAG_ONGOING_EVENT  点击去除 ：Notification.FLAG_AUTO_CANCEL
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, new Intent(), 0);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setWhen(System.currentTimeMillis())    // 通知产生的时间，会在通知信息里显示
                .setContentIntent(pIntent)
                // .setNumber(number)                   // 显示数量
                .setPriority(Notification.PRIORITY_HIGH)// 设置该通知优先级
                .setAutoCancel(false)                   // 设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(true)                       // ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                // .setDefaults(Notification.DEFAULT_VIBRATE)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性
                // 可以组合： Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.drawable.icon_launcher)
                .setContentTitle(getString(R.string.service_update_hint_download_start))//
                .setContentText(String.format(getString(R.string.service_update_hint_download_progress), ((int) currentPercent)) + "%")
                .setProgress(100, (int) currentPercent, false) // 这个方法是显示进度条  false确定进度的进度条
                .setTicker(getString(R.string.service_update_hint_download_start));                         // 设置文本显示在状态栏通知第一次 带上升动画效果的

        //Notification notify = mBuilder.build();
        //FLAG_AUTO_CANCEL   该通知能被状态栏的清除按钮给清除掉
        //FLAG_NO_CLEAR      该通知不能被状态栏的清除按钮给清除掉
        //FLAG_ONGOING_EVENT 通知放置在正在运行
        //FLAG_INSISTENT     是否一直进行，比如音乐一直播放，知道用户响应
        //notify.flags = Notification.FLAG_NO_CLEAR;
    }

    /**
     * 更新通知栏进度条
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-25,上午10:19:33
     * <br> UpdateTime: 2016-11-25,上午10:19:33
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void updateNotification() {
        mBuilder.setContentTitle(getString(R.string.service_update_hint_download_start))
                .setContentText(String.format(getString(R.string.service_update_hint_download_progress), (int) currentPercent) + "%")
                .setAutoCancel(false)                           // 设置这个标志当用户单击面板就可以让通知将自动取消
                .setProgress(100, currentPercent, false); // 这个方法是显示进度条

        mNotificationManager.notify(NOTIFICATION_DOWNLOAD_ID, mBuilder.build());
    }

    /**
     * 完成下载
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-25,上午10:20:00
     * <br> UpdateTime: 2016-11-25,上午10:20:00
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void finishDown() {
        currentPercent = 100;

        mBuilder.setContentTitle(getString(R.string.service_update_hint_download_finish))
                .setContentText(getString(R.string.service_update_hint_download_install))
                .setAutoCancel(true)                // 设置这个标志当用户单击面板就可以让通知将自动取消
                .setProgress(0, 0, false);

        String apkLocal = ConfigFile.PATH_DOWNLOAD + StringUtil.getFileName(downLoadUrl);
        File file = new File(apkLocal);
        if (file.exists()) {
            Intent intent = new Intent();
            // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            //判读版本是否在7.0以上
            if (Build.VERSION.SDK_INT >= 24) {
                //provider authorities
                Uri apkUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", file);
                //Granting Temporary Permissions to a URI
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                Uri apkUri = Uri.fromFile(file);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            }
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
            mBuilder.setContentIntent(contentIntent);// 设置点击后安装apk
        }
        mNotificationManager.notify(NOTIFICATION_DOWNLOAD_ID, mBuilder.build());

        ApkUtil.installApk(file, null);
        stopService(new Intent().setClass(this, UpdateService.class));
    }

    /**
     * 下载失败
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-25,上午10:20:10
     * <br> UpdateTime: 2016-11-25,上午10:20:10
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void loadError() {
        mBuilder.setContentTitle(getString(R.string.service_update_hint_download_error))
                .setContentText(getString(R.string.service_update_hint_download_error_check))
                .setAutoCancel(true)        // 设置这个标志当用户单击面板就可以让通知将自动取消
                .setProgress(0, 0, false);  // 这个方法是显示进度条
        mNotificationManager.notify(NOTIFICATION_DOWNLOAD_ID, mBuilder.build());

        stopService(new Intent().setClass(this, UpdateService.class));
    }

    /** 广播接收器 */
    private BroadcastReceiver receiver;

    /**
     * 注册广播Activity
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月22日,下午1:41:25
     * <br> UpdateTime: 2016年5月22日,下午1:41:25
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    protected void registerReceiver() {
        /// 广播过滤器 */
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastFilters.ACTION_APP_EXIT);// 程序退出
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {// 广播监听回调
                if (intent.getAction().equals(BroadcastFilters.ACTION_APP_EXIT)) {// 程序退出
                    LogUtil.i("onReceive______程序退出");
                    if (mNotificationManager != null) {
                        mNotificationManager.cancel(NOTIFICATION_DOWNLOAD_ID);
                    }
                    NotificationUtil.cancelAll();
                    stopService(new Intent().setClass(UpdateService.this, UpdateService.class));
                }
            }
        };
        registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroy() {
        LogUtil.i("onDestroy...");
        if (null != receiver) {
            unregisterReceiver(receiver);
        }
        super.onDestroy();
    }
}