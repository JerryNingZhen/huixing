package com.android.base.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;

import com.android.base.BaseApplication;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 通知栏工具类
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2017/3/31
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class NotificationUtil {

    /**
     * 生成Notification对象
     *
     * @return 返回Notification对象
     */
    public static Notification getNotification(int notifyId, Class cls, boolean autoCancel, boolean ongoing, int defaults, int smallIcon, int titleText, int contentText, int tickerText, int progress) {
        Resources res = BaseApplication.getInstance().getApplicationContext().getResources();
        return getNotification(notifyId, cls, autoCancel, ongoing, defaults, smallIcon, res.getString(titleText),
                res.getString(contentText), res.getString(tickerText), progress);
    }

    /**
     * 生成Notification对象
     *
     * @param notifyId
     *         通知标识id
     * @param cls
     *         点击后打开的类
     * @param autoCancel
     *         设置这个标志当用户单击面板就可以让通知将自动取消
     * @param ongoing
     *         true，将无法通过左右滑动的方式清除。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
     * @param defaults
     *         // 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性
     *         // 可以组合：Notification.DEFAULT_VIBRATE Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音
     *         // 小于-1则为defaults属性
     * @param smallIcon
     *         显示的icon的id
     * @param titleText
     *         打开通知抽屉后的标题
     * @param contentText
     *         打开通知抽屉后的内容
     * @param tickerText
     *         显示的文字
     * @param progress
     *         进度条 进度 (-1 则不显示进度条 ， 大于等于0 则显示进度条)
     *
     * @return 返回Notification对象
     */
    public static Notification getNotification(int notifyId, Class cls, boolean autoCancel, boolean ongoing, int defaults, int smallIcon, String titleText, String contentText, String tickerText, int progress) {

        Context context = BaseApplication.getInstance().getApplicationContext();
        Intent intent = new Intent();
        if (cls != null) {
            intent = new Intent(context, cls);
        }

        // 控制点击通知后显示内容的类
        PendingIntent pIntent = PendingIntent.getActivity(
                context,// Context
                0,      // requestCode  现在是没有使用的，所以任意值都可以
                intent, // Intent
                0       // PendingIntent的flag，在update这个通知的时候可以加特别的flag
        );

        // NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        mBuilder.setWhen(System.currentTimeMillis())    // 通知产生的时间，会在通知信息里显示
                .setContentIntent(pIntent)
                .setPriority(Notification.PRIORITY_HIGH)// 设置该通知优先级
                .setAutoCancel(autoCancel)              // 设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(ongoing)                    // 设为true,将无法通过左右滑动的方式清除。他们通常是用来表示一个后台任务,
                //                                      // 用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                //.setDefaults(defaults)                // 通知添加声音、闪灯和振动效果
                .setSmallIcon(smallIcon)                // 设置通知小图标
                //.setLargeIcon(Bitmap)                 // 设置通知大图标large icon
                //.setNumber(number)                    // 显示数量 设置通知数量的显示类似于QQ那种，用于同志的合并*/
                //.setVibrate(new long[]{300,300,300,300})// 震动
                //.setLights(0xffff0000,1000,1000)        // 呼吸灯
                //.setSound(Uri.parse("file:///sdcard/xx.mp3"))// 自定义铃声 多媒体库内的铃声Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "5")
                .setContentTitle(titleText)             // 设置title
                .setContentText(contentText)            // 设置详细文本
                .setTicker(tickerText)                  // 设置文本显示在状态栏通知第一次 带上升动画效果的
                .setProgress(100, progress, false);     // 这个方法是显示进度条  false确定进度的进度条

        if (defaults >= -1) {
            // 向通知添加声音、闪灯和振动效果，最一致的方式是使用当前的用户默认设置，使用defaults属性
            // Notification.DEFAULT_ALL、DEFAULT_VIBRATE、DEFAULT_SOUND、DEFAULT_LIGHTS
            mBuilder.setDefaults(defaults);
        }

        if (progress >= 0) {
            mBuilder.setProgress(100, progress, false); // 这个方法是显示进度条  false确定进度的进度条
        } else {
            mBuilder.setProgress(0, 0, false);          // 这个方法是显示进度条  false确定进度的进度条
        }
        // notification.contentIntent = pIntent;
        notify(notifyId, mBuilder.build());
        return mBuilder.build();
    }


    /**
     * 发布通知
     */
    public static void notify(int notifyId, Notification n) {
        NotificationManager notificationManager = (NotificationManager) BaseApplication.getInstance().getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notifyId, n);
    }

    /**
     * 取消消息
     */
    public static void cancel(int notifyId) {
        NotificationManager notificationManager = (NotificationManager) BaseApplication.getInstance().getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(notifyId);
    }

    /**
     * 取消消息
     */
    public static void cancelAll() {
        NotificationManager notificationManager = (NotificationManager) BaseApplication.getInstance().getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}