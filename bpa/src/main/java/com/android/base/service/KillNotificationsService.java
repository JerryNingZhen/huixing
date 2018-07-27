package com.android.base.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.android.base.utils.LogUtil;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/**
 * 灰色保活
 * <p>
 * 思路一：API <  18，启动前台Service时直接传入new Notification()；
 * 思路二：API >= 18，同时启动两个id相同的前台Service，然后再将后启动的Service做stop处理；
 * <p>
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class KillNotificationsService extends Service {

    private final static int GRAY_SERVICE_ID = 1111;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GRAY_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            Intent innerIntent = new Intent(this, GrayInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /** 给 API >= 18 的平台上用的灰色保活手段 */
    public class GrayInnerService extends Service {
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GRAY_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            //                Notification.Builder builder = new Notification.Builder(this);
            //                builder.setSmallIcon(R.drawable.icon_close_round);
            //                startForeground(GRAY_SERVICE_ID, builder.build());
            //                new Handler().postDelayed(new Runnable() {
            //                    @Override
            //                    public void run() {
            //                        stopForeground(true);
            //                        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            //                        if (manager != null) {
            //                            manager.cancel(GRAY_SERVICE_ID);
            //                        }
            //                        stopSelf();
            //                    }
            //                }, 100);
            //            }
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }


    @Override
    public void onCreate() {
        LogUtil.w("服务：onCreate");
        super.onCreate();

    }

    //    @Override
    //    public int onStartCommand(Intent intent, int flags, int startId) {
    //        LogUtil.w("服务：onStartCommand..." + intent);
    //        // START_STICKY: 和原来的onStart类似，在被系统kill掉后会restart，但是不同的是onStartCommand会被调用并传入一个null值作为intent，这个时候service就可以对这种case做出处理。
    //        // START_NOT_STICKY： 被kill掉后就不会自动restart了。
    //        // START_REDELIVER_INTENT： 如果service被kill掉了，系统会把之前的intent再次发送给service，直到service处里完成。
    //        return Service.START_STICKY;
    //        // return super.onStartCommand(intent, flags, startId);
    //    }

    @Override
    public void onStart(Intent intent, int startId) {
        LogUtil.w("服务：onStart");
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        LogUtil.w("服务：onDestroy");
        LogUtil.i("onDestroy...");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.w("服务：onBind");
        // return mBinder;
        return null;
    }

    @Override
    public void onRebind(Intent it) {
        LogUtil.w("服务：onRebind");
        super.onRebind(it);
    }

    @Override
    public boolean onUnbind(Intent it) {
        LogUtil.w("服务：onUnbind");
        return super.onUnbind(it);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        LogUtil.w("服务：onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        LogUtil.w("服务：onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogUtil.w("服务：onTrimMemory " + level);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // NotificationUtil.cancelNotification();
        LogUtil.w("服务：onTaskRemoved");
        super.onTaskRemoved(rootIntent);
    }

    @Override
    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        LogUtil.w("服务：dump");
        super.dump(fd, writer, args);
    }
}