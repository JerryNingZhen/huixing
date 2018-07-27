package com.hx.huixing.common.net;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.base.BaseApplication;

/**
 * @项目名： Xzb @包名： com.huika.xzb.utils @author： hupingyan @date：
 * 2015-10-30上午9:36:15 @创建描述： SharedPreferences存储工具
 * @修改者： $Author$ @修改时间： $Date$ @svn版本： $Rev$ @修改内容： TODO
 */
public class SPUtils {
    /**
     * 存储字符串的数据到sp中
     *
     * @param key
     * @param value
     */
    private final static String CONFIG = "config";

    public static void putInteger(String key, int value) {
        // name xml文件名 mode 文件的类型
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    public static void putString(String key, String value) {
        // name xml文件名 mode 文件的类型
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static void putFloat(String key, float value) {
        // name xml文件名 mode 文件的类型
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        sp.edit().putFloat(key, value).commit();
    }

    public static void putBoolean(String key, boolean value) {
        // name xml文件名 mode 文件的类型
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * @param key
     * @param defValue
     * @return 取对应key的值
     */
    public static Integer getInteger(String key, int defValue) {
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public static String getString(String key, String defValue) {
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static float getFloat(String key, float defValue) {
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        return sp.getFloat(key, defValue);
    }

    public static Boolean getBoolean(String key, boolean defValue) {
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }
}
