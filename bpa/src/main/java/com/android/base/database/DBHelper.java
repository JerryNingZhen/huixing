package com.android.base.database;

import android.content.ContentValues;
import android.content.Context;

import com.tencent.wcdb.Cursor;
import com.tencent.wcdb.DatabaseErrorHandler;
import com.tencent.wcdb.database.SQLiteCipherSpec;
import com.tencent.wcdb.database.SQLiteDatabase;
import com.tencent.wcdb.database.SQLiteDatabase.CursorFactory;
import com.tencent.wcdb.database.SQLiteOpenHelper;

/**
 * 数据库操作类.
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月31日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class DBHelper extends SQLiteOpenHelper {

    /** 数据库操作接口对象 */
    private OnOperationDataBase operation;
    /** 数据库操作链接 */
    private SQLiteDatabase db;
    /** 游标对象 */
    private Cursor cursor;

    /* ***************************** 加密构造方法 start********************************/
    public DBHelper(Context context, String name, String password, SQLiteCipherSpec cipher, CursorFactory cursorFactory, int version, DatabaseErrorHandler errorHandler, OnOperationDataBase operation) {
        super(context, name, password.getBytes(), cipher, cursorFactory, version, errorHandler);
        this.operation = operation;
    }

    //    public DBHelper(android.content.Context context, String name, String password, CursorFactory factory, int version, DatabaseErrorHandler errorHandler, OnOperationDataBase operation) {
    //        this(context, name, password, null, factory, version, errorHandler, operation);
    //    }
    /* ***************************** 加密构造方法 end  ********************************/

    /* ***************************** 不加密构造方法 start********************************/

    //    public DBHelper(Context context, String name, CursorFactory cursorFactory, int version) {
    //        super(context, name, cursorFactory, version);
    //    }

    public DBHelper(Context context, String name, CursorFactory cursorFactory, int version, OnOperationDataBase operation) {
        super(context, name, cursorFactory, version);
        this.operation = operation;
    }

    /* ***************************** 不加密构造方法 end  ********************************/

    /**
     * 重写父类的onCreate方法,调用OperationDataBase接口中的createTable方法.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-3-1,下午4:53:55
     * <br> UpdateTime: 2016-3-1,下午4:53:55
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容, 若无修改可不写.)
     *
     * @param db
     *         数据库操作对象.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        if (operation != null) {
            operation.onCreateTable(db, this);
        }
    }

    /**
     * 重写父类的onUpgrade方法,调用OperationDataBase接口中的updateDataBase方法.
     * <p>
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-3-1,下午4:55:28
     * <br> UpdateTime: 2016-3-1,下午4:55:28
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容, 若无修改可不写.)
     *
     * @param db
     *         数据库操作对象.
     * @param oldVersion
     *         旧版本号.
     * @param newVersion
     *         新版本号.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (operation != null) {
            operation.onUpDateDB(db, oldVersion, newVersion);
        }
    }

    /**
     * 数据库降级
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年1月11日,下午2:03:38
     * <br> UpdateTime: 2016年1月11日,下午2:03:38
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param db
     *         数据库操作对象
     * @param oldVersion
     *         旧版本
     * @param newVersion
     *         新版本
     */

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (operation != null) {
            operation.onUpDateDB(db, oldVersion, newVersion);
        }
    }

    /**
     * 打开数据库连接.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-5-31,下午5:34:44
     * <br> UpdateTime: 2016-5-31,下午5:34:44
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容, 若无修改可不写.)
     */
    public void open() {
        super.onOpen(getWrite());
    }

    /**
     * 获取数据库写入权限操作对象.
     *
     * @return 数据库写入权限操作对象.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-3-1,下午4:57:01
     * <br> UpdateTime: 2016-3-1,下午4:57:01
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容, 若无修改可不写.)
     */
    public SQLiteDatabase getWrite() {
        if (null == db || db.isReadOnly()) {
            db = this.getWritableDatabase();
        }
        return db;
    }

    /**
     * 获取数据库读取权限操作对象.
     *
     * @return 数据库读取权限操作对象.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-3-1,下午4:57:43
     * <br> UpdateTime: 2016-3-1,下午4:57:43
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容, 若无修改可不写.)
     */
    public SQLiteDatabase getRead() {
        if (null == db || !db.isReadOnly()) {
            db = this.getReadableDatabase();
        }
        return db;
    }

    /**
     * 向数据库的指定表中插入数据.
     *
     * @param needClose
     *         是否需要关闭数据库连接.true为关闭,否则不关闭.
     * @param table
     *         表名.
     * @param titles
     *         字段名.
     * @param values
     *         数据值.
     *
     * @return 若传入的字段名与插入值的长度不等则返回false, 否则执行成功则返回true.
     * <p>
     * <br> Version: 1.1
     * <br> CreateTime: 2016-3-1,下午4:59:01
     * <br> UpdateTime: 2016-5-8,下午2:19:41
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: 根据传入的needClose判断是否关闭当前的数据库连接.
     */
    public boolean insert(boolean needClose, String table, String[] titles, String[] values) {
        if (titles.length != values.length) // 判断传入的字段名数量与插入数据的数量是否相等
            return false;
        else {
            db = getWrite();
            if (db.isOpen()) {
                // 将插入值与对应字段放入ContentValues实例中
                ContentValues contentValues = new ContentValues();
                for (int i = 0; i < titles.length; i++) {
                    contentValues.put(titles[i], values[i]);
                }
                getWrite().insert(table, null, contentValues); // 执行插入操作
                if (needClose) {
                    close();
                }
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 删除数据库的指定表中的指定数据.
     * <p>
     * <br> Version: 1.1
     * <br> CreateTime: 2016-3-1,下午5:05:50
     * <br> UpdateTime: 2016-5-8,下午2:25:13
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: 根据传入的needClose判断是否关闭当前的数据库连接.
     *
     * @param needClose
     *         是否需要关闭数据库连接.true为关闭,否则不关闭.
     * @param table
     *         表名.
     * @param conditions
     *         条件字段.
     * @param whereValues
     *         条件值.
     */
    public void delete(boolean needClose, String table, String conditions, String[] whereValues) {
        db = getWrite();
        if (db.isOpen()) {
            getWrite().delete(table, conditions, whereValues); // 执行删除操作
        }
        if (needClose) {
            close();
        }
    }

    /**
     * 修改数据库的指定表中的指定数据.
     *
     * @param needClose
     *         是否需要关闭数据库连接.true为关闭,否则不关闭.
     * @param table
     *         表名.
     * @param titles
     *         字段名.
     * @param values
     *         数据值.
     * @param conditions
     *         条件字段.
     * @param whereValues
     *         条件值.
     *
     * @return 若传入的字段名与插入值的长度不等则返回false, 否则执行成功则返回true.
     * <p>
     * <br> Version: 1.1
     * <br> CreateTime: 2016-3-1,下午5:10:58
     * <br> UpdateTime: 2016-5-8,下午2:30:32
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: 根据传入的needClose判断是否关闭当前的数据库连接.
     */
    public boolean update(boolean needClose, String table, String[] titles, String[] values, String conditions, String[] whereValues) {
        if (titles.length != values.length)
            return false;
        else {
            db = getWrite();
            if (db.isOpen()) {
                // 将插入值与对应字段放入ContentValues实例中
                ContentValues contentValues = new ContentValues();
                for (int i = 0; i < titles.length; i++) {
                    contentValues.put(titles[i], values[i]);
                }
                getWrite().update(table, contentValues, conditions, whereValues); // 执行修改操作
                if (needClose) {
                    close();
                }
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 查询数据库的指定表中的指定数据.
     *
     * @param table
     *         表名.
     * @param columns
     *         查询字段.
     * @param selection
     *         条件字段.
     * @param selectionArgs
     *         条件值.
     * @param groupBy
     *         分组名称.
     * @param having
     *         分组条件.与groupBy配合使用.
     * @param orderBy
     *         排序字段.
     * @param limit
     *         分页.
     *
     * @return 查询结果游标
     * <p>
     * <br> Version: 1.1
     * <br> CreateTime: 2016-3-1,下午5:23:28
     * <br> UpdateTime: 2016-5-8,下午2:43:32
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: 根据传入的needClose判断是否关闭当前的数据库连接.将游标中的数据遍历返回.
     */
    public Cursor select(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        cursor = getRead().query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit); // 执行查询操作.
        return cursor;
    }

    //
    //    /**
    //     * 向数据库的指定表中插入数据,数据内容将被进行加密.
    //     * <p>
    //     * <br> Version: 1.1
    //     * <br> CreateTime: 2016-3-1,下午4:59:01
    //     * <br> UpdateTime: 2016-5-8,下午2:19:41
    //     * <br> CreateAuthor: 叶青
    //     * <br> UpdateAuthor: 叶青
    //     * <br> UpdateInfo: 根据传入的needClose判断是否关闭当前的数据库连接.
    //     *
    //     * @param needClose
    //     *         是否需要关闭数据库连接.true为关闭,否则不关闭.
    //     * @param table
    //     *         表名.
    //     * @param titles
    //     *         字段名.
    //     * @param values
    //     *         数据值.
    //     *
    //     * @return 若传入的字段名与插入值的长度不等则返回false, 否则执行成功则返回true.
    //     */
    //    public boolean insertByEncrypt(boolean needClose, String table, String[] titles, String[] values) {
    //        if (titles.length != values.length) // 判断传入的字段名数量与插入数据的数量是否相等
    //            return false;
    //        else {
    //            if (null == db || db.isReadOnly()) {
    //                db = this.getWritableDatabase();
    //            }
    //            if (db.isOpen()) {
    //                // 将插入值与对应字段放入ContentValues实例中
    //                ContentValues contentValues = new ContentValues();
    //                for (int i = 0; i < titles.length; i++) {
    //                    contentValues.put(titles[i], DES3Coder.encrypt(values[i]));
    //                }
    //                getWrite().insert(table, null, contentValues); // 执行插入操作
    //                if (needClose) {
    //                    close();
    //                }
    //                return true;
    //            } else {
    //                return false;
    //            }
    //        }
    //    }
    //
    //
    //    /**
    //     * 删除数据库的指定表中的指定数据(条件值被加密).
    //     * <p>
    //     * <br> Version: 1.1
    //     * <br> CreateTime: 2016-3-1,下午5:05:50
    //     * <br> UpdateTime: 2016-5-8,下午2:25:13
    //     * <br> CreateAuthor: 叶青
    //     * <br> UpdateAuthor: 叶青
    //     * <br> UpdateInfo: 根据传入的needClose判断是否关闭当前的数据库连接.
    //     *
    //     * @param needClose
    //     *         是否需要关闭数据库连接.true为关闭,否则不关闭.
    //     * @param table
    //     *         表名.
    //     * @param conditions
    //     *         条件字段.
    //     * @param whereValues
    //     *         条件值.
    //     */
    //    public void deleteByEncrypt(boolean needClose, String table, String conditions, String[] whereValues) {
    //        String[] values = new String[whereValues.length];
    //        for (int i = 0; i < values.length; i++) {
    //            values[i] = DES3Coder.encrypt(whereValues[i]);
    //        }
    //        if (null == db || db.isReadOnly()) {
    //            db = this.getWritableDatabase();
    //        }
    //        if (db.isOpen()) {
    //            getWrite().delete(table, conditions, values); // 执行删除操作
    //        }
    //        if (needClose) {
    //            close();
    //        }
    //    }
    //
    //
    //    /**
    //     * 修改数据库的指定表中的指定数据.
    //     *
    //     * @param needClose
    //     *         是否需要关闭数据库连接.true为关闭,否则不关闭.
    //     * @param table
    //     *         表名.
    //     * @param titles
    //     *         字段名.
    //     * @param values
    //     *         数据值.
    //     * @param conditions
    //     *         条件字段.
    //     * @param whereValues
    //     *         条件值.
    //     * @param whereValuesNeedEncrypt
    //     *         条件值是否需要加密.
    //     *
    //     * @return 若传入的字段名与插入值的长度不等则返回false, 否则执行成功则返回true.
    //     * <p>
    //     * <br> Version: 1.1
    //     * <br> CreateTime: 2016-3-1,下午5:10:58
    //     * <br> UpdateTime: 2016-5-8,下午2:30:32
    //     * <br> CreateAuthor: 叶青
    //     * <br> UpdateAuthor: 叶青
    //     * <br> UpdateInfo: 根据传入的needClose判断是否关闭当前的数据库连接.
    //     */
    //    public boolean updateByEncrypt(boolean needClose, String table, String[] titles, String[] values, String conditions, String[] whereValues, boolean whereValuesNeedEncrypt) {
    //        if (titles.length != values.length)
    //            return false;
    //        else {
    //            if (null == db || db.isReadOnly()) {
    //                db = this.getWritableDatabase();
    //            }
    //            if (db.isOpen()) {
    //                // 将插入值与对应字段放入ContentValues实例中
    //                ContentValues contentValues = new ContentValues();
    //                for (int i = 0; i < titles.length; i++) {
    //                    contentValues.put(titles[i], DES3Coder.encrypt(values[i]));
    //                }
    //                String[] wValues;
    //                if (whereValuesNeedEncrypt) {
    //                    wValues = new String[whereValues.length];
    //                    for (int i = 0; i < values.length; i++) {
    //                        values[i] = DES3Coder.encrypt(whereValues[i]);
    //                    }
    //                } else {
    //                    wValues = whereValues;
    //                }
    //                getWrite().update(table, contentValues, conditions, wValues); // 执行修改操作
    //                if (needClose) {
    //                    close();
    //                }
    //                return true;
    //            } else {
    //                return false;
    //            }
    //        }
    //    }
    //
    //    /**
    //     * 查询数据库的指定表中的指定数据.
    //     *
    //     * @param needClose
    //     *         是否需要关闭数据库连接.true为关闭,否则不关闭.
    //     * @param table
    //     *         表名.
    //     * @param columns
    //     *         查询字段.
    //     * @param selection
    //     *         条件字段.
    //     * @param selectionArgs
    //     *         条件值.
    //     * @param groupBy
    //     *         分组名称.
    //     * @param having
    //     *         分组条件.与groupBy配合使用.
    //     * @param orderBy
    //     *         排序字段.
    //     * @param limit
    //     *         分页.
    //     *
    //     * @return 查询的数据.
    //     * <p>
    //     * <br> Version: 1.1
    //     * <br> CreateTime: 2016-3-1,下午5:23:28
    //     * <br> UpdateTime: 2016-5-8,下午2:43:32
    //     * <br> CreateAuthor: 叶青
    //     * <br> UpdateAuthor: 叶青
    //     * <br> UpdateInfo: 根据传入的needClose判断是否关闭当前的数据库连接.将游标中的数据遍历返回.
    //     */
    //    public ArrayList<HashMap<String, String>> select(boolean needClose, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy,
    //                                                     String limit) {
    //        cursor = getRead().query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit); // 执行查询操作.
    //        int count = cursor.getColumnCount();
    //        ArrayList<HashMap<String, String>> list = new ArrayList<>();
    //        while (cursor.moveToNext()) {
    //            HashMap<String, String> map = new HashMap<>();
    //            for (int i = 0; i < count; i++) {
    //                map.put(cursor.getColumnName(i), cursor.getString(i));
    //            }
    //            list.add(map);
    //        }
    //        cursor.close();
    //        if (needClose) {
    //            close();
    //        }
    //        return list;
    //    }
    //
    //    /**
    //     * 查询数据库的指定表中的指定数据.
    //     *
    //     * @param needClose
    //     *         是否需要关闭数据库连接.true为关闭,否则不关闭.
    //     * @param table
    //     *         表名.
    //     * @param columns
    //     *         查询字段.
    //     * @param selection
    //     *         条件字段.
    //     * @param selectionArgs
    //     *         条件值.
    //     * @param groupBy
    //     *         分组名称.
    //     * @param having
    //     *         分组条件.与groupBy配合使用.
    //     * @param orderBy
    //     *         排序字段.
    //     * @param limit
    //     *         分页.
    //     * @param whereValuesNeedEncrypt
    //     *         条件值是否需要加密.
    //     *
    //     * @return 查询的数据.
    //     * <p>
    //     * <br> Version: 1.1
    //     * <br> CreateTime: 2016-3-1,下午5:23:28
    //     * <br> UpdateTime: 2016-5-8,下午2:43:32
    //     * <br> CreateAuthor: 叶青
    //     * <br> UpdateAuthor: 叶青
    //     * <br> UpdateInfo: 根据传入的needClose判断是否关闭当前的数据库连接.将游标中的数据遍历返回.
    //     */
    //    public ArrayList<HashMap<String, String>> selectByEncrypt(boolean needClose, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
    //                                                              String orderBy, String limit, boolean whereValuesNeedEncrypt) {
    //        String[] args;
    //        if (whereValuesNeedEncrypt) {
    //            args = new String[selectionArgs.length];
    //            for (int i = 0; i < args.length; i++) {
    //                args[i] = DES3Coder.encrypt(selectionArgs[i]);
    //            }
    //        } else {
    //            args = selectionArgs;
    //        }
    //        cursor = getRead().query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit); // 执行查询操作.
    //        int count = cursor.getColumnCount();
    //        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    //        while (cursor.moveToNext()) {
    //            HashMap<String, String> map = new HashMap<String, String>();
    //            for (int i = 0; i < count; i++) {
    //                map.put(cursor.getColumnName(i), DES3Coder.decrypt(cursor.getString(i)));
    //            }
    //            list.add(map);
    //        }
    //        cursor.close();
    //        if (needClose) {
    //            close();
    //        }
    //        return list;
    //    }
    //
    //    /**
    //     * 事务处理,调用OperationTransaction接口中的executeTransaction的方法根据返回判断事务是否执行成功. 若事务执行成功则进行数据提交,否则进行滚回操作.
    //     * <p>
    //     * <br> Version: 1.1
    //     * <br> CreateTime: 2016-3-1,下午5:27:36
    //     * <br> UpdateTime: 2016-5-8,下午2:49:43
    //     * <br> CreateAuthor: 叶青
    //     * <br> UpdateAuthor: 叶青
    //     * <br> UpdateInfo: 根据传入的needClose判断是否关闭当前的数据库连接.
    //     *
    //     * @param needClose
    //     *         是否需要关闭数据库连接.true为关闭,否则不关闭.
    //     * @param ot
    //     *         操作数据库事务对象.
    //     *
    //     * @return 若事务执行成功则返回true, 否则滚回返回false.
    //     */
    //    public boolean transaction(boolean needClose, OnTransaction ot) {
    //        if (null == db || db.isReadOnly()) {
    //            db = this.getWritableDatabase();
    //        }
    //        if (db.isOpen()) {
    //            db.beginTransaction(); // 开始事务.
    //            boolean isSuccess = ot.executeTransaction(db);
    //            if (isSuccess) {
    //                db.setTransactionSuccessful(); // 设置事务处理成功,不设置会自动回滚不提交.
    //            }
    //            db.endTransaction(); // 事务结束.
    //            if (needClose) {
    //                close();
    //            }
    //            return isSuccess;
    //        } else {
    //            return false;
    //        }
    //    }

    /**
     * 删除数据库的指定表中的所有数据.
     * <p>
     * <br> Version: 1.1
     * <br> CreateTime: 2016-3-1,下午5:30:47
     * <br> UpdateTime: 2016-5-8,下午5:52:37
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: 根据传入的needClose判断是否关闭当前的数据库连接.
     *
     * @param needClose
     *         是否需要关闭数据库连接.true为关闭,否则不关闭.
     * @param table
     *         表名
     */
    public void clear(boolean needClose, String table) {
        getWrite().execSQL("delete from " + table);
        if (needClose) {
            close();
        }
    }

    /**
     * 关闭打开的所有数据库对象.
     * <p>
     * <br> Version: 1.1
     * <br> CreateTime: 2016-3-1,下午5:33:05
     * <br> UpdateTime: 2016-5-31,下午5:20:45
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: 添加数据库操作对象的关闭.
     */
    public void close() {
        if (null != cursor && !cursor.isClosed()) {
            cursor.close();
        }

        if (null != db && db.isOpen()) {
            db.close();
            db = null;
        }
        super.close();
    }

    /**
     * 操作数据库接口
     * <p>
     * <br> Author: 叶青
     * <br> Version: 1.0.0
     * <br> Date: 2016年12月11日
     * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
     */
    public interface OnOperationDataBase {

        /**
         * 创建表接口.
         * <p>
         * <br> Version: 1.0.0
         * <br> CreateTime: 2016-3-1,下午4:01:19
         * <br> UpdateTime: 2016-3-1,下午4:01:19
         * <br> CreateAuthor: 叶青
         * <br> UpdateAuthor: 叶青
         * <br> UpdateInfo: (此处输入修改内容, 若无修改可不写.)
         *
         * @param db
         *         数据库操作对象.
         */
        public void onCreateTable(SQLiteDatabase db, SQLiteOpenHelper dbHelper);

        /**
         * 更新数据库接口.
         * <p>
         * <br> Version: 1.0.0
         * <br> CreateTime: 2016-3-1,下午4:02:10
         * <br> UpdateTime: 2016-3-1,下午4:02:10
         * <br> CreateAuthor: 叶青
         * <br> UpdateAuthor: 叶青
         * <br> UpdateInfo: (此处输入修改内容, 若无修改可不写.)
         *
         * @param db
         *         数据库操作对象.
         * @param oldVersion
         *         老版本号.
         * @param newVersion
         *         新版本号.
         */
        public void onUpDateDB(SQLiteDatabase db, int oldVersion, int newVersion);

    }

    //    /**
    //     * 事务操作接口.
    //     * <p>
    //     * <br> Author: 叶青
    //     * <br> Version: 1.0.0
    //     * <br> Date: 2016年12月11日
    //     * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
    //     */
    //    public interface OnTransaction {
    //
    //        /**
    //         * 执信事务.
    //         * <p>
    //         * <br> Version: 1.0.0
    //         * <br> CreateTime: 2016-3-1,下午4:46:47
    //         * <br> UpdateTime: 2016-3-1,下午4:46:47
    //         * <br> CreateAuthor: 叶青
    //         * <br> UpdateAuthor: 叶青
    //         * <br> UpdateInfo: (此处输入修改内容, 若无修改可不写.)
    //         *
    //         * @param db
    //         *         数据库操作对象.
    //         *
    //         * @return 若事务执行完成并成功返回true, 若失败则返回false.
    //         */
    //        public boolean executeTransaction(SQLiteDatabase db);
    //
    //    }
}
