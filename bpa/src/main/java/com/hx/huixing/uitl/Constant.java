package com.hx.huixing.uitl;


import com.android.base.BaseApplication;
import com.android.base.configs.ConfigServer;
import com.android.base.utils.PreferencesUtil;

/**
 * Created by tanjun on 2018/5/2.
 * 常量类
 */

public class Constant {

    /** 是否显示日志 */
    public static final boolean IS_SHOW_LOG = true;

    public static final String LOG_TAG = "tanjun";

    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";

    /**
     * 百度地址
     * 用来获取网络时间
     */
    public static final String baidu = "http://www.baidu.com";

    /**
     * 普通图片压缩裁剪后的后缀
     */
    public static final String IMAGE_LOGO_COMPRESS = "_compress+new.";

    private static final String CONSTANT_KEY_BASE = BaseApplication.getInstance().getApplicationContext().getPackageName() + ".";

    /** sp文件名---用户 */
    public static final String SP_KEY_FILE_USER_INFO = "sp_key_file_user_info";

    /** sp保存在data/data目录下的文件名 */
    public static final String SP_FILE_NAME = CONSTANT_KEY_BASE + ".spData";

    /** posttimeKey请求发送时间 */
    public static final String SERVER_POST_TIME_KEY = "posttime";

    /** signKey :40位的SHA签名 */
    public static final String SERVER_SIGN_KEY = "sign";

    public static final int CONNECT_TIME_OUT = 15000;//超时时间

    //public static final String BASE_URL = "http://192.168.5.68:50400"; //测试内网
    //public static final String BASE_URL = "http://backend.blockcomet.com/blockchain/"; //彗星
    public static final String BASE_URL = ConfigServer.SERVER_API_URL; //彗星外网测试
    //public static final String BASE_URL = "http://192.168.0.215:8080/"; //柱子开发环境
    //public static final String BASE_URL = "http://192.168.5.16:9004"; //测试
    //public static final String BASE_URL = "http://192.168.5.70:9004"; //集成开发环境
    //public static final String BASE_URL = "http://dytapi-test.p2phx.com:19004"; //外网测试
    //public static final String BASE_URL ="http://test_dyt-platapi.zimilbs.com:50400"; //zmx外网测试
    //public static final String BASE_URL = "http://172.16.20.156:50400/"; //俊焕给的地址
    public static final String METHOD_UPLOADIMG = "/app/auth/task/uploadImg"; //图片上传
    //public static final String BASE_URL = "http://api.daiyetong.com"; //测试

    //public static final String BASE_URL = "http://api.daiyetong.com";//生产

    public static final String SIGN_KEY = "aFaj81aXawkj8XNOF3GFCUn2q903LN8U";

    public static final String DES_KEY = "abc45678901234567890ABCD";

    public static final String PLATFORM = "ANDROID";

    public static final String CHANNEL = "hx";

    public static final String USER_ID = "userID";

    private static final String TOKEN = "DYT_TOKEN";

    public static final String SIGN_MAP_KEY = "sign";

    public static final String POSTTIME_MAP_KEY = "posttime";

    public static final String TOKEN_MAP_KEY = "token";

    private static final String USER_ID_KEY = "userID";

    private static final String ALIAS = "Alias";

    private static final String BASEDATA_VERSION = "BaseDataVersion";

    private static final String PRODUCTINFOLIST_VERSION = "ProductInfoListVersion";

    private static final String AREALIST_VERSION = "AreaListVersion";

    private static final String JPUSH_MESSAGE = "jpush_message";

    public static final String PRODUCT_TYPE = "product_type";

    public static final int CACHE_SIZE = 10;

    public static final String APK_NAME = "dyt";

    public static final String APK_SUFFIX = ".apk";

    public static final String CACHE_CATALOG_NAME = "dyt";


    public static String getToken() {
        return (String) PreferencesUtil.get( TOKEN, "");
    }

    public static void setToken(String token) {
        PreferencesUtil.put( TOKEN, token);
    }

    public static String getAlias() {
        return (String) PreferencesUtil.get( ALIAS, "");
    }

    public static void setAlias(String alias) {
        PreferencesUtil.put( ALIAS, alias);
    }

    public static String getUserID() {
        return (String) PreferencesUtil.get( USER_ID_KEY, "");
    }

    public static void setUserID(String userID) {
        PreferencesUtil.put(USER_ID_KEY, userID);
    }

    public static String getBaseDataVersion() {
        return (String) PreferencesUtil.get(BASEDATA_VERSION, "");
    }

    public static void setBaseDataVersion(String version) {
        PreferencesUtil.put(BASEDATA_VERSION, version);
    }

    public static String getProductInfoListVersion() {
        return (String) PreferencesUtil.get(PRODUCTINFOLIST_VERSION, "");
    }

    public static void setProductInfoListVersion(String version) {
        PreferencesUtil.put(PRODUCTINFOLIST_VERSION, version);
    }

    public static String getAreaListVersion() {
        return (String) PreferencesUtil.get(AREALIST_VERSION, "");
    }

    public static void setAreaListVersion(String version) {
        PreferencesUtil.put(AREALIST_VERSION, version);
    }

    public static void setMessage(boolean is) {
        PreferencesUtil.put(JPUSH_MESSAGE, is);
    }

    public static boolean getMessage() {
        return (boolean) PreferencesUtil.get(JPUSH_MESSAGE, false);
    }

}
