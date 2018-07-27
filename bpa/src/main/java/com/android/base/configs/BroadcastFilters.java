package com.android.base.configs;

import com.android.base.BaseApplication;

/**
 * 注册广播接收ACTION
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class BroadcastFilters {

    // ***************************注册广播接收动作 ***************************//
    private static final String ACTION_BASE = BaseApplication.getInstance().getApplicationContext().getPackageName();
    /** 语言切换监听 */
    public static final String ACTION_CHANGE_LANGUAGE = ACTION_BASE + "language.change";
    /** 开始定位 */
    public static final String ACTION_LOCATION_START = ACTION_BASE + "location.start";
    //    /** 请求我的位置（每5s刷新一次） */
    //    public static final String ACTION_REQUEST_MINE_LOCATION = ACTION_BASE + "request.mine.location";
    /** 结束定位 */
    public static final String ACTION_LOCATION_FINISH = ACTION_BASE + "location.finish";
    /** 定位完成 （包含失败或者成功）*/
    public static final String ACTION_LOCATION_COMPLETE = ACTION_BASE + "location.complete";
    /** 支付完成 */
    public static final String ACTION_WECHAT_PAY_COMPLETE = ACTION_BASE + "pay.complete";
    /** 程序退出 */
    public static final String ACTION_APP_EXIT = ACTION_BASE + "app.exit";
    /** Token失效 */
    public static final String ACTION_TONKEN_EXPIRED = ACTION_BASE + "tonken.expired";
}