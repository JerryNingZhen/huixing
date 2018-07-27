package com.android.base.database;

import android.content.Context;
import android.text.TextUtils;

import com.android.base.BaseApplication;
import com.android.base.utils.LogUtil;
import com.tencent.wcdb.DatabaseUtils;
import com.tencent.wcdb.database.SQLiteDatabase;
import com.tencent.wcdb.database.SQLiteOpenHelper;
import com.tencent.wcdb.repair.RepairKit;

import java.io.File;


/**
 * 数据库管理工具
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年5月18日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class DBHelperManage {


    /** 加密数据库版本 */
    private static final int DATA_BASE_VERSION_ENCRYPTED = 2;
    /** 加密数据库名称 */
    private static final String DATABASE_NAME_ENCRYPTED = "encrypted.db";
    /** 未加密数据库版本 */
    private static final int DATA_BASE_VERSION_NORMAL = 2;
    /** 未加密数据库名称 */
    private static final String DATABASE_NAME_NORMAL = "normal.db";
    private static Context mContext;

    /**
     * 获取数据库操作对象
     */
    public static DBHelper getDBHelper(String password) {
        mContext = BaseApplication.getInstance().getApplicationContext();
        if (TextUtils.isEmpty(password)) {
            return getNormalDBHelper(mContext);
        } else {
            return getEncryptedDBHelper(mContext, password);
        }
    }

    /**
     * 获取加密数据库操作对象
     */
    private static DBHelper getEncryptedDBHelper(Context context, final String password) {

        return new DBHelper(context, DATABASE_NAME_ENCRYPTED, password, null, null, DATA_BASE_VERSION_ENCRYPTED, null, new DBHelper.OnOperationDataBase() {

            @Override
            public void onUpDateDB(SQLiteDatabase db, int oldVersion, int newVersion) {
                try {
                    if (newVersion > oldVersion) {// 升级
                        switch (db.getVersion()) {
                            case 1:// 数据库版本1-2
                                String sql = "ALTER TABLE " + DBFields.TB_USER_INFO // 用户表新增字段
                                        + " ADD " // 创推送消息表
                                        //  + DBFields.FIELDS_ACCOUNT + " TEXT, " // 账号
                                        + DBFields.FIELDS_NAME + " TEXT" // 名称
                                        + ");";
                                db.execSQL(sql);
                            case 2:// 数据库版本2-3
                                db.execSQL("CREATE TABLE message1 (content TEXT, sender TEXT);");
                        }
                    }
                } catch (RuntimeException e) {
                    // 数据库更新失败
                    e.printStackTrace();
                }
                RepairKit.MasterInfo.save(db, db.getPath() + "-mbak", password.getBytes());
            }

            @Override
            public void onCreateTable(SQLiteDatabase db, SQLiteOpenHelper dbHelper) {
                // String sql = "create table if not exists " + 表名 + "(" // 创推送消息表
                // + DBFields.FIELDS_ID + " integer PRIMARY KEY autoincrement, " //
                // 自增长id.
                // + DBFields.MESSAGE_ID + " varchar, " // 更推送消息id
                // + DBFields.IS_READ + " varchar, " // 消息是否已读（1未读，2已读）
                // + DBFields.SEND_TIME + " varchar" // 发送时间
                // + ");";
                // db.execSQL(sql);

                final File oldDbFile = mContext.getDatabasePath(DATABASE_NAME_NORMAL);
                if (oldDbFile.exists()) {
                    LogUtil.i("Migrating plain-text database to encrypted one.");

                    // SQLiteOpenHelper begins a transaction before calling onCreate().
                    // We have to end the transaction before we can attach a new database.
                    db.endTransaction();

                    // Attach old database to the newly created, encrypted database.
                    String sql = String.format("ATTACH DATABASE %s AS old;",
                            DatabaseUtils.sqlEscapeString(oldDbFile.getPath()));
                    db.execSQL(sql);

                    // Export old database.
                    db.beginTransaction();
                    DatabaseUtils.stringForQuery(db, "SELECT sqlcipher_export('main', 'old');", null);
                    db.setTransactionSuccessful();
                    db.endTransaction();

                    // Get old database version for later upgrading.
                    int oldVersion = (int) DatabaseUtils.longForQuery(db, "PRAGMA old.user_version;", null);

                    // Detach old database and enter a new transaction.
                    db.execSQL("DETACH DATABASE old;");

                    // Old database can be deleted now.
                    oldDbFile.delete();

                    // Before further actions, restore the transaction.
                    db.beginTransaction();

                    // Check if we need to upgrade the schema.
                    if (oldVersion > DATA_BASE_VERSION_ENCRYPTED) {
                        dbHelper.onDowngrade(db, oldVersion, DATA_BASE_VERSION_ENCRYPTED);
                    } else if (oldVersion < DATA_BASE_VERSION_ENCRYPTED) {
                        dbHelper.onUpgrade(db, oldVersion, DATA_BASE_VERSION_ENCRYPTED);
                    }
                } else {
                    LogUtil.i("Creating new encrypted database.");
                    // Do the real initialization if the old database is absent.
                    String sql = "create table if not exists " + DBFields.TB_USER_INFO + "(" // 创推送消息表
                            + DBFields.FIELDS_ID + " integer PRIMARY KEY autoincrement, " // 自增长id.
                            + DBFields.FIELDS_ACCOUNT + " TEXT, " // 账号
                            + DBFields.FIELDS_NAME + " TEXT, " // 名称
                            + DBFields.FIELDS_PORTRAIT + " TEXT, " // 头像
                            + DBFields.FIELDS_PASSWORD + " TEXT" // 密码
                            + ");";
                    db.execSQL(sql);
                }
                // OPTIONAL: backup master info for corruption recovery.
                RepairKit.MasterInfo.save(db, db.getPath() + "-mbak", password.getBytes());
            }
        });
    }

    /**
     * 获取旧版本（未加密）数据库操作对象
     */
    private static DBHelper getNormalDBHelper(Context context) {

        return new DBHelper(context, DATABASE_NAME_NORMAL, null, DATA_BASE_VERSION_NORMAL, new DBHelper.OnOperationDataBase() {

            @Override
            public void onUpDateDB(SQLiteDatabase db, int oldVersion, int newVersion) {
                try {
                    if (newVersion > oldVersion) {// 升级
                        switch (db.getVersion()) {
                            case 1:// 数据库版本1-2
                                String sql = "ALTER TABLE " + DBFields.TB_USER_INFO // 用户表新增字段
                                        + " ADD " // 创推送消息表
                                        //  + DBFields.FIELDS_ACCOUNT + " TEXT, " // 账号
                                        + DBFields.FIELDS_NAME + " TEXT" // 名称
                                        + ");";
                                db.execSQL(sql);
                            case 2:// 数据库版本2-3
                                db.execSQL("CREATE TABLE message1 (content TEXT, sender TEXT);");
                        }
                    }

                } catch (RuntimeException e) {
                    // 数据库更新失败
                    e.printStackTrace();
                }
            }

            @Override
            public void onCreateTable(SQLiteDatabase db, SQLiteOpenHelper dbHelper) {
                String sql = "create table if not exists " + DBFields.TB_USER_INFO + "(" // 创推送消息表
                        + DBFields.FIELDS_ID + " integer PRIMARY KEY autoincrement, " // 自增长id.
                        + DBFields.FIELDS_ACCOUNT + " TEXT, " // 账号
                        + DBFields.FIELDS_NAME + " TEXT, " // 名称
                        + DBFields.FIELDS_PORTRAIT + " TEXT, " // 头像
                        + DBFields.FIELDS_PASSWORD + " TEXT" // 密码
                        + ");";
                db.execSQL(sql);
            }
        });
    }
}
