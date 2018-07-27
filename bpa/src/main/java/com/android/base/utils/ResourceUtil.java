package com.android.base.utils;

import android.content.Context;

import com.android.base.BaseApplication;

import java.lang.reflect.Field;

/**
 * 通过字段名称动态获取res资源
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ResourceUtil {

    /**
     * 获取 layout 的 FIELDS_ID
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-24,下午2:10:11
     * <br> UpdateTime: 2016-12-24,下午2:10:11
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param paramString
     *         resId
     *
     * @return resId
     */
    public static int getLayoutId(String paramString) {
        Context context = BaseApplication.getInstance().getApplicationContext();
        return context.getResources().getIdentifier(paramString, "layout", context.getPackageName());
    }

    /**
     * 获取 string 的 FIELDS_ID
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-24,下午2:10:11
     * <br> UpdateTime: 2016-12-24,下午2:10:11
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param paramString
     *
     * @return resId
     */
    public static int getStringId(String paramString) {
        Context context = BaseApplication.getInstance().getApplicationContext();
        return context.getResources().getIdentifier(paramString, "string", context.getPackageName());
    }

    /**
     * 获取 drawable 的 FIELDS_ID
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-24,下午2:10:11
     * <br> UpdateTime: 2016-12-24,下午2:10:11
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param paramString
     *
     * @return resId
     */
    public static int getDrawableId(String paramString) {
        Context context = BaseApplication.getInstance().getApplicationContext();
        return context.getResources().getIdentifier(paramString, "drawable", context.getPackageName());
    }

    /**
     * 获取 style 的 FIELDS_ID
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-24,下午2:10:11
     * <br> UpdateTime: 2016-12-24,下午2:10:11
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param paramString
     *
     * @return resId
     */
    public static int getStyleId(String paramString) {
        Context context = BaseApplication.getInstance().getApplicationContext();
        return context.getResources().getIdentifier(paramString, "style", context.getPackageName());
    }

    /**
     * 获取 id 的 FIELDS_ID
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-24,下午2:10:11
     * <br> UpdateTime: 2016-12-24,下午2:10:11
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param paramString
     *
     * @return resId
     */
    public static int getId(String paramString) {
        Context context = BaseApplication.getInstance().getApplicationContext();
        return context.getResources().getIdentifier(paramString, "id", context.getPackageName());
    }

    /**
     * 获取 color 的 FIELDS_ID
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-24,下午2:10:03
     * <br> UpdateTime: 2016-12-24,下午2:10:03
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param paramString
     *
     * @return resId
     */
    public static int getColorId(String paramString) {
        Context context = BaseApplication.getInstance().getApplicationContext();
        return context.getResources().getIdentifier(paramString, "color", context.getPackageName());
    }

    /**
     * 获取 anim 的 FIELDS_ID
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-24,下午2:09:51
     * <br> UpdateTime: 2016-12-24,下午2:09:51
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param paramString
     *
     * @return resId
     */
    public static int getAnimId(String paramString) {
        Context context = BaseApplication.getInstance().getApplicationContext();
        return context.getResources().getIdentifier(paramString, "anim", context.getPackageName());
    }

    /**
     * 获取 array 的 FIELDS_ID
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-24,下午2:09:51
     * <br> UpdateTime: 2016-12-24,下午2:09:51
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param paramString
     *
     * @return resId
     */
    public static int getArrayId(String paramString) {
        Context context = BaseApplication.getInstance().getApplicationContext();
        return context.getResources().getIdentifier(paramString, "array", context.getPackageName());
    }

    /**
     * 对于 context.getResources().getIdentifier 无法获取的数据 , 或者数组 资源反射值
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-24,下午2:09:29
     * <br> UpdateTime: 2016-12-24,下午2:09:29
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param name
     * @param type
     *
     * @return resId
     */
    private static Object getResourceId(String name, String type) {
        Context context = BaseApplication.getInstance().getApplicationContext();
        String className = context.getPackageName() + ".R";
        try {

            Class<?> cls = Class.forName(className);
            for (Class<?> childClass : cls.getClasses()) {

                String simple = childClass.getSimpleName();
                if (simple.equals(type)) {

                    for (Field field : childClass.getFields()) {

                        String fieldName = field.getName();
                        if (fieldName.equals(name)) {
                            System.out.println(fieldName);
                            return field.get(null);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * context.getResources().getIdentifier 无法获取到 styleable 的数据
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-24,下午2:08:41
     * <br> UpdateTime: 2016-12-24,下午2:08:41
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param name
     *
     * @return resId
     */
    public static int getStyleableId(String name) {
        return ((Integer) getResourceId(name, "styleable")).intValue();
    }

    /**
     * 获取 styleable 的 FIELDS_ID 号数组
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-24,下午2:08:32
     * <br> UpdateTime: 2016-12-24,下午2:08:32
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param name
     *
     * @return resId
     */
    public static int[] getStyleableArray(String name) {
        return (int[]) getResourceId(name, "styleable");
    }
}