package com.hx.huixing.common.net;

import java.util.HashMap;

public class Constants {
    //判断Tmall页面的资源加载是否是第一次，只加载一次；
    public static boolean isWebDataIsFirst = true;

    //區分Android跟IOS的其他地方不可更改，僅此一個
    public static final String ANDROID = "Android";

    //Tmall页面的webview资源预加载，初始化配置；
    public static HashMap<String, String> sMimeTypeMap = new HashMap<>();

    static {
        sMimeTypeMap.put("html", "text/html");
        sMimeTypeMap.put("css", "text/css");
        sMimeTypeMap.put("js", "text/javascript");
        sMimeTypeMap.put("gif", "image/gif");
        sMimeTypeMap.put("ico", "image/x-icon");
        sMimeTypeMap.put("png", "image/png");
        sMimeTypeMap.put("jpg", "image/jpeg");
        sMimeTypeMap.put("jpeg", "image/jpeg");
        sMimeTypeMap.put("png", "image/png");
    }


    //显示项目完成进度的图片资源
    public static final int ICON_PERCENT_0 = 0;
    public static final int ICON_PERCENT_5 = 5;
    public static final int ICON_PERCENT_10 = 10;
    public static final int ICON_PERCENT_20 = 20;
    public static final int ICON_PERCENT_30 = 30;
    public static final int ICON_PERCENT_40 = 40;
    public static final int ICON_PERCENT_50 = 50;
    public static final int ICON_PERCENT_60 = 60;
    public static final int ICON_PERCENT_70 = 70;
    public static final int ICON_PERCENT_80 = 80;
    public static final int ICON_PERCENT_90 = 90;
    public static final int ICON_PERCENT_95 = 95;
    public static final int ICON_PERCENT_100 = 100;

    /**
     * MainActivity頁面底部Table切換
     */
    public static final String HOME_TAB_TAG = "homeTab";// 首页
    public static final String INVESTMENT_TAB_TAG = "investmentTab";// 投资
    public static final String FORUM_TAB_TAG = "forumTab";// 论坛 标签
    public static final String ACCOUNT_TAB_TAG = "accountTab";// 个人账户
    public static final String TMALL_TAB_TAG = "tmallTab";// 商城

    public static final String APP_ID = "wx45b7851907bde7c9";
    public static final String APP_SECRET = "8151a33d91ed913da38f56efaad59b7a";


    /***************以下全部是SharedPreferences的Key全局唯一*************/
    /**
     * 首頁數據列表更多是否被點擊
     * 默認值為false，被點擊為true，否則為false
     */
    public static final String HOME_MORE_STATE = "homeMoreState";

    public static final String INVEST_PRODUCT = "investProduct";
    public static final String INVEST_NEW = "investNew";
    public static final String INVEST_VIP = "investVip";
    public static final String INVEST_ASSGMENT = "investAssgment";
    public static final String NEW_PRODUCT = "newProduct";
    public static final String TOKEN = "token";
    public static final String ALIAS = "alias";
    public static final String BASE_ACTIVITY = "baseActivity";
    public static final String MY_RECEIVER = "myReceiver";
    public static final String CURRENT_TIME = "currentTime";// 切换后台或者按物理返回键时间戳
    //存储第一次进入公告页面的key
    public static final String NOTICE_KEY = "noticeKey";
    //存储第一次进入信息页面的key
    public static final String MESSAGE_KEY = "messageKey";
    public static final String NOTICE_FLAG = "noticeFlag";
    public static final String MESSAGE_FLAG = "messageFlag";
    public static final String MESSAGE_MAX_SUM = "message_max_sum";
    public static final String NOTICE_MAX_SUM = "notice_max_sum";
    //存储用户名key
    public static final String GESTURE_ACCOUNT = "gestureAccount";
    public static final String FIRST_TIME = "firstTime";

    //存储用户是否设置成功手势密码Key
    public static final String GESTURE_KEY = "gesture_key";
    //存储用户登录成功时的用户名
    public static final String USER_NAME = "user_name";
    public static final String FIRST_RECHARGE_FLAG = "firstRechargeFlag";

    public static final String ACCOUNT_GUIDE = "accountGuide";

    public static final String MESSAGE_BACK = "message_back";


    public static final String SIGN_KEY = "signKey";
    public static final String ENCRY_KEY = "encryKey";

    /**
     * 存储是否是第一次打开App应用状态
     */
    public static final String IS_FIRSTIN = "isfirstin";

    public static final String SHOW_RATE_INVEST = "showRateInvest";

    public static final String SHOW_RATE_CREDITOR = "showRateCreditor";

    // 是否解析評估彈出框字段  true：彈出；false：不彈出
    public static final String IS_RISK_FLAG = "isRiskFlag";

    // 存儲後台返回的用戶是否已經評估過的狀態
    public static final String RISK_FLAG = "riskFlag";

    //存儲後台返回的評估內容
    public static final String RISK_CONTENT = "riskContent";


    //01:登录；02：分享
    public static final String WEI_XIN = "weiXin";

    //存儲後台返回的約標期限
    public static final String BESPEAK_DATA = "bespeakData";
}
