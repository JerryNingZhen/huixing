package com.android.base.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.android.base.BaseApplication;

/**
 * 获取当前网络连接状态
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class NetWorkUtil {

    //    //中国移动CMNET网络(中国移动GPRS接入方式之一, 主要为PC、笔记本电脑、PDA设立)
    //    public static final int TYPE_MOBILE_CMNET = 1;
    //    //中国移动CMWAP网络(中国移动GPRS接入方式之一,主要为手机WAP上网而设立)
    //    public static final int TYPE_MOBILE_CMWAP = 2;
    //    //中国联通UNIWAP网络(中国联通划分GPRS接入方式之一, 主要为手机WAP上网而设立)
    //    public static final int TYPE_MOBILE_UNIWAP = 3;
    //    //中国联通3GWAP网络
    //    public static final int TYPE_MOBILE_3GWAP = 4;
    //    //中国联通3HNET网络
    //    public static final int TYPE_MOBLIE_3GNET = 5;
    //    //中国联通UNINET网络(中国联通划分GPRS接入方式之一, 主要为PC、笔记本电脑、PDA设立)
    //    public static final int TYPE_MOBILE_UNINET = 6;
    //    //中国电信CTWAP网络
    //    public static final int TYPE_MOBILE_CTWAP = 7;
    //    //中国电信CTNET网络
    //    public static final int TYPE_MOBILE_CTNET = 8;
    //    //WIFI网络
    //    public static final int TYPE_WIFI = 10;
    //     * 网络类型 - 无连接
    private static final int NETWORK_TYPE_NO_CONNECTION = -1231545315;
    private static final String NETWORK_TYPE_WIFI = "wifi";
    private static final String NETWORK_TYPE_3G = "eg";
    private static final String NETWORK_TYPE_2G = "2g";
    private static final String NETWORK_TYPE_WAP = "wap";
    private static final String NETWORK_TYPE_UNKNOWN = "unknown";
    private static final String NETWORK_TYPE_DISCONNECT = "disconnect";

    /**
     * 得到网络类型
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-23,下午5:27:48
     * <br> UpdateTime: 2016-1-23,下午5:27:48
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public static int getNetworkType() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
        return networkInfo == null ? -1 : networkInfo.getType();
    }

    /**
     * 获取当前网络类型名字
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-23,下午5:26:25
     * <br> UpdateTime: 2016-1-23,下午5:26:25
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    @SuppressWarnings("deprecation")
    public static String getNetworkTypeName() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        String type = NETWORK_TYPE_DISCONNECT;
        if (manager == null || (networkInfo = manager.getActiveNetworkInfo()) == null) {
            return type;
        }

        if (networkInfo.isConnected()) {
            String typeName = networkInfo.getTypeName();
            if ("WIFI".equalsIgnoreCase(typeName)) {
                type = NETWORK_TYPE_WIFI;
            } else if ("MOBILE".equalsIgnoreCase(typeName)) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                type = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork() ? NETWORK_TYPE_3G : NETWORK_TYPE_2G) : NETWORK_TYPE_WAP;
            } else {
                type = NETWORK_TYPE_UNKNOWN;
            }
        }
        return type;
    }

    /**
     * 是否为移动网络
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-23,下午5:28:01
     * <br> UpdateTime: 2016-1-23,下午5:28:01
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private static boolean isFastMobileNetwork() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            return false;
        }

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false;
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false;
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true;
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false;
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true;
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true;
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true;
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }

    /**
     * 获取当前网络链接状态.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月31日,上午3:02:55
     * <br> UpdateTime: 2016年12月31日,上午3:02:55
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return true网络可用，false网络不可用
     */
    public static boolean isNetworkAvailable() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); // 获取网络服务
        if (connectivity == null) {
            // 判断网络服务是否为空
            return false;
        } else {
            // 判断当前是否有任意网络服务开启
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-5,下午3:53:47
     * <br> UpdateTime: 2016-1-5,下午3:53:47
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public static boolean isWifi() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    // TODO

    /**
     * 获取当前网络的状态
     *
     * @param context
     *         上下文
     *
     * @return 当前网络的状态。具体类型可参照NetworkInfo.State.CONNECTED、NetworkInfo.State.CONNECTED.DISCONNECTED等字段。当前没有网络连接时返回null
     */
    public static NetworkInfo.State getCurrentNetworkState(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkInfo != null ? networkInfo.getState() : null;
    }

    /**
     * 获取当前网络的类型
     *
     * @param context
     *         上下文
     *
     * @return 当前网络的类型。具体类型可参照ConnectivityManager中的TYPE_BLUETOOTH、TYPE_MOBILE、TYPE_WIFI等字段。当前没有网络连接时返回NetworkUtils.NETWORK_TYPE_NO_CONNECTION
     */
    public static int getCurrentNetworkType(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkInfo != null ? networkInfo.getType() : NETWORK_TYPE_NO_CONNECTION;
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     *         上下文
     *
     * @return boolean 网络连接状态
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            //获取连接对象
            if (mNetworkInfo != null) {
                //判断是TYPE_MOBILE网络
                if (ConnectivityManager.TYPE_MOBILE == mNetworkInfo.getType()) {
                    LogUtil.i("网络连接类型为：TYPE_MOBILE");
                    //判断移动网络连接状态
                    NetworkInfo.State STATE_MOBILE = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
                    if (STATE_MOBILE == NetworkInfo.State.CONNECTED) {
                        LogUtil.i("网络连接类型为：TYPE_MOBILE, 网络连接状态CONNECTED成功！");
                        return mNetworkInfo.isAvailable();
                    }
                }

                //判断是TYPE_WIFI网络
                if (ConnectivityManager.TYPE_WIFI == mNetworkInfo.getType()) {
                    LogUtil.i("网络连接类型为：TYPE_WIFI");
                    //判断WIFI网络状态
                    NetworkInfo.State STATE_WIFI = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
                    if (STATE_WIFI == NetworkInfo.State.CONNECTED) {
                        LogUtil.i("网络连接类型为：TYPE_WIFI, 网络连接状态CONNECTED成功！");
                        return mNetworkInfo.isAvailable();
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断网络是否连接
     *
     * @param activity
     *         Activity
     *
     * @return boolean 网络连接状态
     */
    public static boolean isNetworkConnected(Activity activity) {
        Context context = BaseApplication.getInstance().getApplicationContext();
        ConnectivityManager mConnectivityManager = (ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConnectivityManager == null) {
            return false;
        }
        NetworkInfo[] arrayOfNetworkInfo = mConnectivityManager.getAllNetworkInfo();
        boolean falg = false;
        if (arrayOfNetworkInfo != null) {
            for (NetworkInfo anArrayOfNetworkInfo : arrayOfNetworkInfo) {
                if (anArrayOfNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    falg = true;
                    break;
                }
            }
        }
        return falg;
    }

    /**
     * 判断当前网络的类型是否是Wifi
     *
     * @param context
     *         上下文
     *
     * @return 当前网络的类型是否是Wifi。false：当前没有网络连接或者网络类型不是wifi
     */
    public static boolean isWifiByType(Context context) {
        return getCurrentNetworkType(context) == ConnectivityManager.TYPE_WIFI;
    }
}