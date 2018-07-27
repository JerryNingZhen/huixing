package com.android.base.database;


import com.android.base.bean.UserInfoBean;
import com.android.base.utils.LogUtil;
import com.tencent.wcdb.Cursor;

import static com.android.base.database.DBFields.FIELDS_NAME;
import static com.android.base.database.DBFields.FIELDS_PASSWORD;
import static com.android.base.database.DBFields.FIELDS_PORTRAIT;

/**
 * 数据库操作实例----只做数据库操作语句实例，--所有数据库的操作在DataBaseUtil中添加
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class DBOperationUtil {

    /**
     * 插入一条登录用户记录
     */
    public static void insertTest(String password, UserInfoBean bean) {
        DBHelper helper = DBHelperManage.getDBHelper(password);
        // helper.setWriteAheadLoggingEnabled(true);
        String[] titles = new String[]{DBFields.FIELDS_ACCOUNT, FIELDS_PASSWORD, FIELDS_NAME, FIELDS_PORTRAIT};
        String[] values = new String[]{bean.getUserAccount(), bean.getPassword(), bean.getNickname(), bean.getPortrait()};
        // 从本地数据库查找是否存在该用户记录
        Cursor cursor = helper.select(DBFields.TB_USER_INFO, new String[]{"*"}, DBFields.FIELDS_ACCOUNT + " =? ", new String[]{bean.getUserAccount()}, null, null, null, null);

        // 如果本地数据库存在该用户，则更新该记录，否则插入一条新的记录
        if (cursor.getCount() <= 0) {
            helper.insert(true, DBFields.TB_USER_INFO, titles, values);
        } else {
            helper.update(true, DBFields.TB_USER_INFO, titles, values, DBFields.FIELDS_ACCOUNT + " =? ", new String[]{bean.getUserAccount()});
        }

    }

    /**
     * 插入一条登录用户记录
     */
    public static void updateTest(String password, UserInfoBean bean) {
        DBHelper helper = DBHelperManage.getDBHelper(password);
        // helper.setWriteAheadLoggingEnabled(true);
        String[] titles = new String[]{DBFields.FIELDS_ACCOUNT, FIELDS_PASSWORD, FIELDS_NAME, FIELDS_PORTRAIT};
        String[] values = new String[]{bean.getUserAccount(), bean.getPassword(), bean.getNickname(), bean.getPortrait()};
        // 从本地数据库查找是否存在该用户记录
        Cursor cursor = helper.select(DBFields.TB_USER_INFO, new String[]{"*"}, DBFields.FIELDS_ACCOUNT + " =? ", new String[]{bean.getUserAccount()}, null, null, null, null);

        // 如果本地数据库存在该用户，则更新该记录，否则插入一条新的记录
        if (cursor.getCount() <= 0) {
            helper.insert(true, DBFields.TB_USER_INFO, titles, values);
        } else {
            helper.update(true, DBFields.TB_USER_INFO, titles, values, DBFields.FIELDS_ACCOUNT + " =? ", new String[]{bean.getUserAccount()});
        }

    }

    /**
     * 获取指定登录用户信息
     */
    public static void selectTest(String password) {
        DBHelper helper = DBHelperManage.getDBHelper(password);
        // helper.setWriteAheadLoggingEnabled(true);
        //  查询所有数据
        Cursor cursor = helper.select(DBFields.TB_USER_INFO, // table 表名.String
                new String[]{"*"}, // 查询字段.String[]{"*"}所有
                null, // 条件字段.String
                null, // 条件值.String[]
                null, // 分组名称.String
                null, // 分组条件.与groupBy配合使用.String
                "id asc", // 排序字段.String "id asc"
                null// 分页.String
        );
        //      TODO 多条件查询
        //		Cursor cursor = helper.select(table, // table 表名.String
        //				new String[] { "*" }, // 查询字段.String[]{"*"}所有
        //				DBFields.STATE + " = ? or " + DBFields.STATE + " = ? and " + DBFields.MYSEAL + " = ? and " + DBFields.FIELDS_ID + " = ? ", // 条件字段.String
        //				new String[] { TApplication.STATE_BLACKLIST_BOTH, TApplication.STATE_BLACKLIST_MINE, TApplication.STATE_BLACKLIST_OTHER }, // 条件值.String[]
        //				null, // 分组名称.String
        //				null, // 分组条件.与groupBy配合使用.String
        //				null, // 排序字段.String "id asc"
        //				null// 分页.String
        //				);
        //      TODO 模糊查询
        //		Cursor cursor = helper.select(table, // table 表名.String
        //				new String[] { "*" }, // 查询字段.String[]{"*"}所有
        //				DBFields.STATE + " = ? or " + DBFields.STATE + " = ? or " + DBFields.STATE + " = ? or " + DBFields.STATE + " = ? ", // 条件字段.String
        //				new String[] { TApplication.STATE_BLACKLIST_BOTH, TApplication.STATE_BLACKLIST_MINE, TApplication.STATE_BLACKLIST_OTHER, TApplication.STATE_FRIEND, }, // 条件值.String[]
        //				null, // 分组名称.String
        //				null, // 分组条件.与groupBy配合使用.String
        //				null, // 排序字段.String "id asc"
        //				null// 分页.String
        //				);

        LogUtil.i(cursor.getCount() + "......");
        while (cursor.moveToNext()) {
            String account = cursor.getString(cursor.getColumnIndex(DBFields.FIELDS_ACCOUNT));
            String password1 = cursor.getString(cursor.getColumnIndex(FIELDS_PASSWORD));
            String name = cursor.getString(cursor.getColumnIndex(FIELDS_NAME));
            String portrait = cursor.getString(cursor.getColumnIndex(FIELDS_PORTRAIT));
            int rowid = cursor.getInt(cursor.getColumnIndex(DBFields.FIELDS_ID));
            LogUtil.i(account + "....." + password1 + "....." + name + "....." + portrait + "....." + rowid);
        }
        helper.close();
    }

    /**
     * 使用之前一定要删除DownloadManager中的下载任务 删除本地缓存视频的数据库记录
     */
    public static void deleteTest(String password, UserInfoBean bean) {
        DBHelper helper = DBHelperManage.getDBHelper(password);
        // helper.setWriteAheadLoggingEnabled(true);
        helper.delete(true, DBFields.TB_USER_INFO, DBFields.FIELDS_ACCOUNT + " = ?", new String[]{bean.getUserAccount()});
    }
}