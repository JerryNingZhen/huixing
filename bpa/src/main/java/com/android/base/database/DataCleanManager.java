package com.android.base.database;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 本应用数据清除管理器
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class DataCleanManager {

    private static final String DATA_DATA = File.separator + "data" + File.separator + "data" + File.separator;

    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-30,下午8:28:41
     * <br> UpdateTime: 2016-12-30,下午8:28:41
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-30,下午8:29:02
     * <br> UpdateTime: 2016-12-30,下午8:29:02
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File(DATA_DATA + context.getPackageName() + "/databases"));
    }

    /**
     * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-30,下午8:29:10
     * <br> UpdateTime: 2016-12-30,下午8:29:10
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File(DATA_DATA + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 按名字清除本应用数据库
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-30,下午8:29:25
     * <br> UpdateTime: 2016-12-30,下午8:29:25
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文
     * @param dbName
     *         数据库名字
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-30,下午8:29:34
     * <br> UpdateTime: 2016-12-30,下午8:29:34
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-30,下午8:29:42
     * <br> UpdateTime: 2016-12-30,下午8:29:42
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-30,下午8:29:49
     * <br> UpdateTime: 2016-12-30,下午8:29:49
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param filePath
     *         文件夹路径
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * 清除本应用所有的数据
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-30,下午8:30:00
     * <br> UpdateTime: 2016-12-30,下午8:30:00
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文
     * @param filepaths
     *         app自定义文件夹路径
     */
    public static void cleanApplicationData(Context context, String[] filepaths) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        for (String filePath : filepaths) {
            cleanCustomCache(filePath);
        }
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-30,下午8:30:09
     * <br> UpdateTime: 2016-12-30,下午8:30:09
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param directory
     *         待删除的文件夹
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }
}