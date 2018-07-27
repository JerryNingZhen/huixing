package com.android.base.utils;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * 计算
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class MathUtils {


    // ****************2016年12月22日 17:33:09*********************
    // ********************************字符转整型 装码失败会默认返回0

    /**
     * 字符转整型 装码失败会默认返回0
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-30,下午9:11:34
     * <br> UpdateTime: 2016-12-30,下午9:11:34
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param str
     *         转换的字符串
     *
     * @return 转换数值
     */
    public static int str2Int(String str) {
        return str2Int(str, 0);
    }

    /**
     * 带默认值的字符转整型
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-30,下午9:11:34
     * <br> UpdateTime: 2016-12-30,下午9:11:34
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param str
     *         转换的字符串
     * @param defaultValue
     *         默认值
     *
     * @return 转换的数值
     */
    public static int str2Int(String str, int defaultValue) {
        if (TextUtils.isEmpty(str)) {
            return defaultValue;
        } else {
            return Integer.parseInt(str);
        }
    }

    // ********************************字符转长整型 装码失败会默认返回0

    /**
     * 字符转长整型 装码失败会默认返回0
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-30,下午9:11:34
     * <br> UpdateTime: 2016-12-30,下午9:11:34
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param str
     *         转换的字符串
     *
     * @return 转换数值
     */
    public static long str2Long(String str) {
        return str2Long(str, 0);
    }

    /**
     * 带默认值的字符转长整型
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-30,下午9:11:34
     * <br> UpdateTime: 2016-12-30,下午9:11:34
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param str
     *         转换的字符串
     * @param defaultValue
     *         默认值
     *
     * @return 转换的数值
     */
    public static long str2Long(String str, long defaultValue) {
        if (TextUtils.isEmpty(str)) {
            return defaultValue;
        } else {
            return Long.parseLong(str);
        }
    }

    // ********************************字符转浮点型 失败会默认返回0

    /**
     * 字符转浮点型 失败会默认返回0
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-30,下午9:11:34
     * <br> UpdateTime: 2016-12-30,下午9:11:34
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param str
     *         转换的字符串
     *
     * @return 转换数值
     */
    public static float str2Float(String str) {
        return str2Float(str, 0);
    }

    /**
     * 带默认值的字符转浮点型
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-30,下午9:11:34
     * <br> UpdateTime: 2016-12-30,下午9:11:34
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param str
     *         转换的字符串
     * @param defaultValue
     *         默认值
     *
     * @return 转换的数值
     */
    public static float str2Float(String str, float defaultValue) {
        if (TextUtils.isEmpty(str)) {
            return defaultValue;
        } else {
            return Float.parseFloat(str);
        }
    }

    // ********************************字符转双精度浮点型 失败会默认返回0.0

    /**
     * 字符转双精度浮点型 失败会默认返回0.0
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-30,下午9:11:34
     * <br> UpdateTime: 2016-12-30,下午9:11:34
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param str
     *         转换的字符串
     *
     * @return 转换数值
     */
    public static Double str2Double(String str) {
        return str2Double(str, 0.0);
    }

    /**
     * 带默认值的字符转双精度浮点型
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-30,下午9:11:34
     * <br> UpdateTime: 2016-12-30,下午9:11:34
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param str
     *         转换的字符串
     * @param defaultValue
     *         默认值
     *
     * @return 转换的数值
     */
    public static Double str2Double(String str, Double defaultValue) {
        if (TextUtils.isEmpty(str)) {
            return defaultValue;
        } else {
            return Double.parseDouble(str);
        }
    }

    // ********************************四舍五入.失败会默认返回0.0

    /**
     * 四舍五入.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-30,下午2:27:06
     * <br> UpdateTime: 2016-12-30,下午2:27:06
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param str
     *         原数
     * @param decimal
     *         保留几位小数
     *
     * @return 四舍五入后的值
     */
    public static String round4String(String str, int decimal) {
        if (TextUtils.isEmpty(str)) {
            str = "0";
        } else {
            if (str.contains(",")) {
                str = str.replace(",", "");
            }
        }
        BigDecimal b = new BigDecimal(str).setScale(decimal, BigDecimal.ROUND_HALF_UP);
        return b.toString();
    }

    /**
     * 格式化百分数
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-30,下午2:27:06
     * <br> UpdateTime: 2016-12-30,下午2:27:06
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param str
     *         原数
     * @param decimal
     *         保留几位小数
     *
     * @return 格式化百分数
     */
    public static String getPercent(String str, int decimal) {
        if (TextUtils.isEmpty(str)) {
            str = "0";
        }
        BigDecimal b = new BigDecimal(Double.parseDouble(str) * 100).setScale(decimal, BigDecimal.ROUND_HALF_UP);
        return b.toString() + "%";
    }
}