package com.android.base.utils.gson;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Gson解析
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2018/1/6
 * <br> Copyright: Copyright © 2018 xTeam Technology. All rights reserved.
 */
public class GsonUtil {

    private static GsonUtil gsonUtil;

    public static GsonUtil getInstance() {
        if (gsonUtil == null) {
            gsonUtil = new GsonUtil();
        }
        return gsonUtil;
    }

    private GsonUtil() {
        buildGson();
    }

    public Gson gson;

    /**
     * 增加后台返回""和"null"的处理
     * 1.int=>0
     * 2.double=>0.00
     * 3.long=>0L
     * 4.String=>""
     */
    private void buildGson() {
        if (gson == null) {
            //            gson = new Gson();
            gson = new GsonBuilder()
                    .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                    .registerTypeAdapter(long.class, new LongDefault0Adapter())
                    .registerTypeAdapter(String.class, new StringDefault0Adapter())
                    //.serializeNulls()//调用serializeNulls方法，改变gson对象的默认行为 null 被原样输出
                    .create();
        }
    }

    /**
     * 转成bean
     *
     * @param jsonStr
     *         json格式字符串
     * @param cls
     *         Bean
     */
    public <T> T json2Bean(String jsonStr, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(jsonStr, cls);
        }
        return t;
    }

    /**
     * 把json 字符串转化成list
     *
     * @param json
     *         json格式字符串
     * @param cls
     *         Bean
     */
    private <T> ArrayList<T> stringToList(String json, Class<T> cls, String key) {
        ArrayList<T> list = new ArrayList<T>();
        try {
            if (!TextUtils.isEmpty(key)) {
                JSONObject jsonObject = new JSONObject(json);
                json = jsonObject.optString(key, "");
            }
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (final JsonElement elem : array) {
                list.add(gson.fromJson(elem, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 转成list
     *
     * @param jsonStr
     *         json格式字符串
     * @param cls
     *         ArrayList<T>
     */
    public <T> ArrayList<T> jsonToList(String jsonStr, Class<T> cls, String jsonKey) {
        ArrayList<T> list = new ArrayList<>();
        try {
            if (!TextUtils.isEmpty(jsonKey)) {
                JSONObject jsonObject = new JSONObject(jsonStr);
                jsonStr = jsonObject.optString(jsonKey, "");
            }
            if (gson != null) {
                list = gson.fromJson(jsonStr, new TypeToken<List<T>>() {
                }.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 转成list中有map的
     *
     * @param jsonStr
     *         json格式字符串
     */
    public <T> ArrayList<Map<String, T>> jsonToListMaps(String jsonStr) {
        ArrayList<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(jsonStr,
                    new TypeToken<ArrayList<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param jsonStr
     *         json格式字符串
     */
    public <T> Map<String, T> jsonToMap(String jsonStr) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(jsonStr, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
}
