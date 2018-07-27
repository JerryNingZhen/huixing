package com.hx.huixing.common.net;


import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.android.base.BaseApplication;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SysUtils {
    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvaliable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 比较时间
     *
     * @param before  之前保存的时间
     * @param current 当前服务器返回的最大时间
     * @return 返回0没有新消息，返回1有消息
     */
    public static int getIntTime(String before, String current) {
        try {
            if (!TextUtils.isEmpty(current) && !TextUtils.isEmpty(current)) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar c1 = Calendar.getInstance();
                Calendar c2 = Calendar.getInstance();
                c1.setTime(df.parse(current));
                c2.setTime(df.parse(before));
                return c1.compareTo(c2);
            }
        } catch (java.text.ParseException e) {
            LogUtils.e(e);
        }
        return 0;
    }


    /**
     * 判断wifi是否打开
     *
     * @param context
     * @return
     */
    public static boolean isWiFiActive(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] infos = connectivity.getAllNetworkInfo();
            if (infos != null) {
                for (NetworkInfo ni : infos) {
                    if (ni.getTypeName().equals("WIFI") && ni.isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断GPS是否可用
     *
     * @param context
     * @return
     */
    public static boolean isGPSOn(Context context) {
        LocationManager lm = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        }
        return false;
    }

    public static int dip2px(float dpValue) {
        final float scale = BaseApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static boolean isExsitMianActivity() {
        String[] activePackages;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activePackages = getActivePackages();
        } else {
            activePackages = getActivePackagesCompat();
        }
        if (activePackages != null) {
            String packageName = BaseApplication.getInstance().getPackageName();
            for (String activePackage : activePackages) {
                if (activePackage.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return 获取本地版本号
     */
    public static int getVersionCode() {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = BaseApplication.getInstance().getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            return packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e(e);
        }
        return 0;
    }

    public static String getVersionName() {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = BaseApplication.getInstance().getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e(e);
        }
        return "0";
    }

    //手机型号
    public static String getSysVersionCode() {
        return Build.MODEL;
    }

    public static String getPhoneBrand() {
        return Build.BRAND;
    }

    public static String getSystemNumb() {
        return Build.VERSION.RELEASE; // 系统版本
    }

    public static String getChannelCode() {// 渠道号获取
        String code = getMetaData("CHANNEL");
        if (code != null) {
            return code;
        }
        return "hxyd";
    }

    /**
     * 删除指定文件缓存
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (dir == null) {
            return false;
        }
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    private static String getMetaData(String key) {
        try {
            ApplicationInfo ai = BaseApplication.getInstance().getPackageManager().getApplicationInfo(
                    BaseApplication.getInstance().getPackageName(), PackageManager.GET_META_DATA);
            Object value = ai.metaData.get(key);
            if (value != null) {
                return value.toString();
            }

        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */

    public static String getAppMetaData() {
        final String UMENG_CHANNEL = "UMENG_CHANNEL";
        String resultData = null;
        try {
            PackageManager packageManager = BaseApplication.getInstance().getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(BaseApplication.getInstance().getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(UMENG_CHANNEL);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e(e);
        }
        return resultData;
    }

    private static String[] getActivePackagesCompat() {
        ActivityManager mActivityManager = (ActivityManager) BaseApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningTaskInfo> taskInfo = mActivityManager.getRunningTasks(1);
        final ComponentName componentName = taskInfo.get(0).topActivity;
        final String[] activePackages = new String[1];
        activePackages[0] = componentName.getPackageName();
        return activePackages;
    }

    private static String[] getActivePackages() {
        ActivityManager mActivityManager = (ActivityManager) BaseApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        final Set<String> activePackages = new HashSet<>();
        final List<ActivityManager.RunningAppProcessInfo> processInfos = mActivityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                activePackages.addAll(Arrays.asList(processInfo.pkgList));
            }
        }
        return activePackages.toArray(new String[activePackages.size()]);
    }


}
