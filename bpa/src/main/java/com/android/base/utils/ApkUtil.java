package com.android.base.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.android.base.BaseApplication;
import com.hx.huixing.BuildConfig;
import com.android.base.configs.RequestCode;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;


/**
 * apk工具类:用于(root)安装应用、(root)卸载应用、判断是否系统应用等
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ApkUtil {

    /**
     * 检测应用是否已经安装
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-25,上午11:02:13
     * <br> UpdateTime: 2016-11-25,上午11:02:13
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param packageName
     *         路径包名
     *
     * @return true 已经安装 ， false 未安装
     */
    public static boolean isInstalledNew(String packageName) {
        Context context = BaseApplication.getInstance().getApplicationContext();
        boolean installed = false;
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        List<ApplicationInfo> installedApplications = context.getPackageManager().getInstalledApplications(0);
        for (ApplicationInfo in : installedApplications) {
            if (packageName.equals(in.packageName)) {
                installed = true;
                break;
            } else {
                installed = false;
            }
        }
        return installed;
    }

    /**
     * 检测应用是否已经安装
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-25,上午11:02:13
     * <br> UpdateTime: 2016-11-25,上午11:02:13
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param packageName
     *         路径包名
     *
     * @return true 已经安装 ， false 未安装
     */
    public static boolean isInstalled(String packageName) {
        Context context = BaseApplication.getInstance().getApplicationContext();
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    /**
     * 安装apk
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-25,上午11:02:07
     * <br> UpdateTime: 2016-11-25,上午11:02:07
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param file
     *         apk文件
     */
    public static void installApk(File file,Activity activity) {
        Context context = BaseApplication.getInstance().getApplicationContext();
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            //provider authorities
            Uri apkUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //Granting Temporary Permissions to a URI
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            Uri apkUri = Uri.fromFile(file);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }

        if(activity==null){
            context.startActivity(intent);
        }else{
            activity.startActivityForResult(intent, RequestCode.REQUEST_CODE_INSTALL_APK);
        }
    }

    /**
     * 获取该app是否在进程里面并且是否在前台进程
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-25,上午11:02:00
     * <br> UpdateTime: 2016-11-25,上午11:02:00
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return true 在前台进程 false不在前台进程
     */
    public static boolean isAppOnForeground() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (RunningAppProcessInfo appProcess : appProcesses) {
            // 判断该工程的包名是否在前台 是就返回true
            if (appProcess.processName.equals(packageName) && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 安装apk--根据是否有root权限选择是否静默安装
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-4-7,上午9:48:46
     * <br> UpdateTime: 2016-4-7,上午9:48:46
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param apkPath
     *         apk文件路径
     */
    public static boolean install(String apkPath,Activity activity) {
        // 先判断手机是否有root权限
        if (checkRootPermission()) {
            // 有root权限，利用静默安装实现
            return clientInstall(apkPath);
        } else {
            // 没有root权限，利用意图进行安装
            File file = new File(apkPath);
            if (!file.exists()) {
                return false;
            }
            installApk(file,activity);
            return true;
        }
    }

    /**
     * 卸载apk--根据是否有root权限选择是否静默卸载
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-4-7,上午9:56:06
     * <br> UpdateTime: 2016-4-7,上午9:56:06
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param packageName
     *         需要卸载的app包名
     */
    public static boolean uninstall(String packageName) {
        if (checkRootPermission()) {
            // 有root权限，利用静默卸载实现
            return clientUninstall(packageName);
        } else {
            Uri packageURI = Uri.parse("package:" + packageName);
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            uninstallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Context context = BaseApplication.getInstance().getApplicationContext();
            context.startActivity(uninstallIntent);
            return true;
        }
    }

    /**
     * 判断手机是否有root权限
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-4-7,上午9:58:40
     * <br> UpdateTime: 2016-4-7,上午9:58:40
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return true：root
     */
    private static boolean checkRootPermission() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            PrintWriter PrintWriter = new PrintWriter(process.getOutputStream());
            PrintWriter.flush();
            PrintWriter.close();
            int value = process.waitFor();
            return checkResult(value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return false;
    }

    /**
     * 静默安装
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-4-7,上午9:57:07
     * <br> UpdateTime: 2016-4-7,上午9:57:07
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param apkPath
     *         apk文件路径
     */
    private static boolean clientInstall(String apkPath) {
        PrintWriter PrintWriter = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            PrintWriter = new PrintWriter(process.getOutputStream());
            PrintWriter.println("chmod 777 " + apkPath);
            PrintWriter.println("export LD_LIBRARY_PATH=/vendor/lib:/system/lib");
            PrintWriter.println("pm install -r " + apkPath);
            // PrintWriter.println("exit");
            PrintWriter.flush();
            PrintWriter.close();
            int value = process.waitFor();
            return checkResult(value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return false;
    }

    /**
     * 静默卸载
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-4-7,上午9:56:54
     * <br> UpdateTime: 2016-4-7,上午9:56:54
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param packageName
     *         应用包名
     */
    private static boolean clientUninstall(String packageName) {
        PrintWriter PrintWriter = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            PrintWriter = new PrintWriter(process.getOutputStream());
            PrintWriter.println("LD_LIBRARY_PATH=/vendor/lib:/system/lib ");
            PrintWriter.println("pm uninstall " + packageName);
            PrintWriter.flush();
            PrintWriter.close();
            int value = process.waitFor();
            return checkResult(value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return false;
    }

    /**
     * 启动app com.exmaple.client/.MainActivity com.exmaple.client/com.exmaple.client.MainActivity
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-4-7,上午9:59:15
     * <br> UpdateTime: 2016-4-7,上午9:59:15
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param packageName
     *         应用包名
     * @param activityName
     *         类名
     */
    public static boolean startApp(String packageName, String activityName) {
        // boolean isSuccess = false;
        String cmd = "am start -n " + packageName + "/" + activityName + " \n";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            int value = process.waitFor();
            return checkResult(value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return false;
    }

    /**
     * 校验返回结果
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-4-7,上午10:01:37
     * <br> UpdateTime: 2016-4-7,上午10:01:37
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param value
     */
    private static boolean checkResult(int value) {
        if (value == 0) {// 代表成功
            return true;
        } else if (value == 1) { // 失败
            return false;
        } else { // 未知情况
            return false;
        }
    }

}