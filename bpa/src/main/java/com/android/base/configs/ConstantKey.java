package com.android.base.configs;

import com.android.base.BaseApplication;

/**
 * SP文件的Key、value:*****:Intent数据传递的Key
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ConstantKey {

    private static final String CONSTANT_KEY_BASE = BaseApplication.getInstance().getApplicationContext().getPackageName() + ".";

    /* ***********************SP数据模块*********************** */
    /** sp保存在data/data目录下的文件名 */
    public static final String SP_FILE_NAME = CONSTANT_KEY_BASE + ".spData";

    /** sp文件名---用户 */
    public static final String SP_KEY_USER_INFO = "sp_key_file_user_info";
    /** sp文件名---缓存用户手机号码 */
    public static String SP_KEY_LOGIN_PHONE = "sp_key_login_phone";
    /** sp文件名---语言 */
    public static final String SP_KEY_LANGUAGE = "sp_key_file_language";
    /** sp文件名---版本号 */
    public static final String SP_KEY_VERSION = "sp_key_file_version";
    //    /** sp文件名---网络请求缓存 */
    //    public static final String SP_KEY_FILE_REQUEST = CONSTANT_KEY_BASE + "sp_key_file_request";
    /** sp文件名---移动网络下载 */
    public static final String SP_KEY_DOWN_4G = "sp_key_down_4g";
    /** sp文件名---移动网络下载 */
    public static String SP_KEY_ARTICLE_DRAFT = "sp_key_article_draft_list";

    public static void initPrivateKey(String phone) {
        SP_KEY_ARTICLE_DRAFT = "sp_key_article_draft_list" + phone;
    }

	/* *********************Intent数据模块********************* */
    /** 版本更新……apk下载路径 */
    public static final String INTENT_KEY_APK_PATH = CONSTANT_KEY_BASE + "apk.path";
    /** 版本更新……apk3000b */
    public static final String INTENT_KEY_APK_SIZE = CONSTANT_KEY_BASE + "apk.size";
    /** 位置 下标 */
    public static final String INTENT_KEY_POSITION = CONSTANT_KEY_BASE + "position";
    /** 数组b */
    public static final String INTENT_KEY_DATAS = CONSTANT_KEY_BASE + "dataList";
    /** 账号 */
    public static final String INTENT_KEY_ACCOUNT = CONSTANT_KEY_BASE + "account";
    /** 验证码 */
    public static final String INTENT_KEY_CODE = CONSTANT_KEY_BASE + "code";
    /** 类别 */
    public static final String INTENT_KEY_TYPE = CONSTANT_KEY_BASE + "type";
    /** 标题 */
    public static final String INTENT_KEY_TITLE = CONSTANT_KEY_BASE + "title";
    /** 字符串 */
    public static final String INTENT_KEY_STRING = CONSTANT_KEY_BASE + "string";
    /** 布尔值 */
    public static final String INTENT_KEY_BOOLEAN = CONSTANT_KEY_BASE + "boolean";
    /** id */
    public static final String INTENT_KEY_ID = CONSTANT_KEY_BASE + "id";
    /** 数据 */
    public static final String INTENT_KEY_DATA = CONSTANT_KEY_BASE + "data";

}