package com.android.base.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.android.base.BaseApplication;
import com.android.base.configs.ConstantKey;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;


/**
 * 系统信息工具类：用户获取当前app版本号、版本名称；手机Mac地址、IMEI码等
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class SystemUtil {

    /**
     * 获取当前系统版本号
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-10-21,下午3:45:06
     * <br> UpdateTime: 2016-10-21,下午3:45:06
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return 当前系统版本号
     */
    public static int getAppVersionCode() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        int versionCode = 1;
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            versionCode = 1;
        }
        return versionCode;
    }

    /**
     * 获取当前系统版本名称
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-10-21,下午3:45:17
     * <br> UpdateTime: 2016-10-21,下午3:45:17
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return 当前系统版本名称
     */
    public static String getAppVersionName() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        String versionName = "";
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            versionName = "1.0.0";
        }
        return versionName;
    }

    /** ApplicationMetaData_key值 */
    private static final String APPLICATION_METADATA = "UMENG_CHANNEL";

    /**
     * appliction MetaData读取
     * <p>
     * <br/> Version: 1.0
     * <br/> CreateTime:  2015-5-6,上午11:27:45
     * <br/> UpdateTime:  2015-5-6,上午11:27:45
     * <br/> CreateAuthor:  yeqing
     * <br/> UpdateAuthor:  yeqing
     * <br/> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public static String getChannelName() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        String channelName = "";
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);

            channelName = info.metaData.getString(APPLICATION_METADATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //LogUtil.i(channelName);

        return channelName;
    }

    /**
     * 获取栈顶app的包名
     * 5.0版本之后google废弃了getRunningTasks（）方法，意味着我们在5.0以后不能通过该方法获取正在运行的应用程序
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/3/4 22:39
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/3/4 22:39
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return 栈顶app的包名
     */
    public static String getTopAppPackageName() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        ActivityManager.RunningAppProcessInfo currentInfo = null;
        Field field = null;
        int START_TASK_TO_FRONT = 2;
        String currentApp = "CurrentNULL";

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            try {
                field = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");
            } catch (Exception e) {
                e.printStackTrace();
            }

            List<ActivityManager.RunningAppProcessInfo> appList = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo app : appList) {
                if (app.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Integer state = null;
                    try {
                        state = field.getInt(app);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (state != null && state == START_TASK_TO_FRONT) {
                        currentInfo = app;
                        break;
                    }
                }
            }
            if (currentInfo != null) {
                currentApp = currentInfo.processName;
            }
        } else {
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            currentApp = tasks.get(0).processName;
        }
        Log.e("TAG", "Current App in foreground is: " + currentApp);
        return currentApp;
    }


    /**
     * 获取当前系统可用运行内存
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午4:08:08
     * <br> UpdateTime: 2016-11-24,下午4:08:08
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return 系统总的运行内存 xxxMB
     */
    public static long getAvailMemory() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        // 当前系统的可用内存
        // mi.availMem;
        // 将获取的内存大小规格化
        // return Formatter.formatFileSize(context, mi.availMem);
        // 将获取的内存大小规格化Mb
        return mi.availMem / (1024 * 1024);
    }

    /**
     * 获取当前系统总的运行内存
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午4:07:57
     * <br> UpdateTime: 2016-11-24,下午4:07:57
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return 系统总的运行内存 xxxMB
     */
    public static long getTotalMemory() {
        // 系统内存信息文件
        String filePath = "/proc/meminfo";
        String strFileContent;
        String[] arrayOfString;

        long memory = 0;
        try {
            FileReader localFileReader = new FileReader(filePath);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            // 读取meminfo第一行，系统总内存大小
            strFileContent = localBufferedReader.readLine();

            arrayOfString = strFileContent.split("\\s+");

            // 获得系统总内存，单位是KB，除以1024转换为Mb
            memory = Integer.valueOf(arrayOfString[1]) / 1024;
            localBufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        // return Formatter.formatFileSize(context, memory);//
        // Byte转换为KB或者MB，内存大小规格化
        // MB，内存大小规格化
        return memory;
    }

    /**
     * 获取手机系统语言
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-26,下午5:33:18
     * <br> UpdateTime: 2016-12-26,下午5:33:18
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public static String getSystemLanguage() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        String language = context.getResources().getConfiguration().locale.getLanguage();

        if (language.startsWith("zh")) {
            language = "zh";
        } else if (language.startsWith("en")) {
            language = "en";
        }

        return language;
    }

    /**
     * 获取app语言设置
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-18,上午10:20:15
     * <br> UpdateTime: 2016-12-18,上午10:20:15
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return app语言设置
     */
    public static String getAppLanguage() {
        return (String) PreferencesUtil.get(ConstantKey.SP_KEY_LANGUAGE, SystemUtil.getSystemLanguage());
    }

    // TODO **********************************************手机系统相关参数


    /**
     * 获取当前手机IMEI号
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-5,上午9:53:25
     * <br> UpdateTime: 2016-1-5,上午9:53:25
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return 返回手机IMEI号
     */
    public static String getIMEI() {
        //        // 需要android.permission.READ_PHONE_STATE权限
        //        // IMEI号（国际移动设备身份码）、IMSI号（国际移动设备识别码）这两个是有电话功能的移动设备才具有，也就是说某些没有电话功能的平板是获取不到IMEI和IMSI号的。
        //        // 且在某些设备上getDeviceId()会返回垃圾数据。
        //        Context context = BaseApplication.getInstance().getApplicationContext();
        //        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        //        //        String imsi = mTelephonyMgr.getSubscriberId(); //获取IMSI号
        //        String imei = mTelephonyMgr.getDeviceId(); //获取IMEI号
        //        return imei;
        Context context = BaseApplication.getInstance().getApplicationContext();
        String androidId = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        //ANDROID_ID 是设备首次启动时由系统随机生成的一串64位的十六进制数字。
        //.设备刷机wipe数据或恢复出厂设置时ANDROID_ID值会被重置。
        //.现在网上已有修改设备ANDROID_ID值的APP应用。
        //.某些厂商定制的系统可能会导致不同的设备产生相同的ANDROID_ID。
        //.某些厂商定制的系统可能导致设备返回ANDROID_ID值为空。
        //.CDMA设备，ANDROID_ID和DeviceId返回的值相同
        //Build.SERIAL 获取产品序列号SerialNumber: 5LM0216129002374
        String imei = androidId + Build.SERIAL;
        return imei.toUpperCase();
    }

    /**
     * 获取当前设备UUID唯一识别码
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-22,下午12:44:32
     * <br> UpdateTime: 2016-1-22,下午12:44:32
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public static String getUUID() {
        return getIMEI();
    }

    /**
     * 获取网络运营商：中国电信,中国移动,中国联通
     */
    public static String getCarrier() {
        Context ctx = BaseApplication.getInstance().getApplicationContext();
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getNetworkOperatorName().toLowerCase(Locale.getDefault());
        } else {
            return "";
        }
    }

    /**
     * 获取得屏幕密度
     */
    public static String getDensity() {
        Context ctx = BaseApplication.getInstance().getApplicationContext();
        String densityStr = null;
        final int density = ctx.getResources().getDisplayMetrics().densityDpi;
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                densityStr = "LDPI";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                densityStr = "MDPI";
                break;
            case DisplayMetrics.DENSITY_TV:
                densityStr = "TVDPI";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                densityStr = "HDPI";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                densityStr = "XHDPI";
                break;
            case DisplayMetrics.DENSITY_400:
                densityStr = "XMHDPI";
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                densityStr = "XXHDPI";
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                densityStr = "XXXHDPI";
                break;
        }
        return densityStr;
    }

    /**
     * 反射获得泛型参数
     *
     * @return 泛型参数集合
     */
    public static Type[] getGenericSuperclass(Class klass) {

        //getSuperclass()获得该类的父类
        //        System.out.println(klass.getSuperclass());
        //Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
        Type type = klass.getGenericSuperclass();

        if (type == null || !(type instanceof ParameterizedType)) {
            return null;
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        //getActualTypeArguments 获取参数化类型的数组，泛型可能有多个
        Type[] types = parameterizedType.getActualTypeArguments();

        if (types == null || types.length == 0) {
            return null;
        }
        return types;
    }
}