package com.android.base.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则匹配工具类
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class RegexUtil {

    /** 正则表达式：验证用户名 */
    private static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";
    /** 正则表达式：验证密码 */
    private static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";
    /** 正则表达式：验证邮箱 */
    private static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    /** 正则表达式：验证IP地址 */
    private static final String REGEX_IP_ADDRESS = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /** 正则表达式：验证手机号； \d 数字字符匹配。等效于 [0-9] */
    private static final String REGEX_MOBILE = "^1[0-9]{10}$";
    /** 正则表达式：验证身份证 */
    private static final String REGEX_ID_CARD = "(^[1-9][0-9]{13}[a-zA-Z0-9]{1}$)|(^[1-9][0-9]{16}[a-zA-Z0-9]{1}$)";
    /** 正则表达式：纯数字 */
    private static final String REGEX_NUMBER = "^[0-9]+$";
    /** 正则表达式：纯字母 */
    private static final String REGEX_CHAR = "^[a-zA-Z]+$";
    /** 正则表达式：纯小写字母 */
    private static final String REGEX_CHAR_LOWER = "^[a-z]+$";
    /** 正则表达式：纯大写字母 */
    private static final String REGEX_CHAR_UPPER = "^[A-Z]+$";
    /** 正则表达式：验证汉字 */
    private static final String REGEX_CHINESE = "^[\u4e00-\u9fa5]{0,}$";

    /**
     * 校验用户名
     *
     * @param username
     *         待校验的字符串
     *
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     *         待校验的字符串
     *
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     *         待校验的字符串
     *
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     *         待校验的字符串
     *
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     *         待校验的字符串
     *
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     *         待校验的字符串
     *
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddress
     *         待校验的字符串
     *
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIpAddress(String ipAddress) {
        return Pattern.matches(REGEX_IP_ADDRESS, ipAddress);
    }


    /**
     * 判断是否纯数字
     *
     * @param source
     *         需要判断的源字符串
     *
     * @return true 纯数字，false非纯数字
     */
    public static boolean isNumer(String source) {
        return Pattern.matches(REGEX_NUMBER, source);
    }

    /**
     * 判断是纯字母串
     *
     * @param source
     *         判断的源字符串
     *
     * @return true是纯字母字符串，false非纯字母字符串
     */
    public static boolean isChar(String source) {
        return Pattern.matches(REGEX_CHAR, source);
    }

    /**
     * 判断是否纯小写字母
     *
     * @param source
     *         需要判断的源字符串
     *
     * @return true 纯数字，false非纯数字
     */
    public static boolean isCharLower(String source) {
        return Pattern.matches(REGEX_CHAR_LOWER, source);
    }

    /**
     * 判断是否纯大写字母
     *
     * @param source
     *         需要判断的源字符串
     *
     * @return true 纯数字，false非纯数字
     */
    public static boolean isCharUpper(String source) {
        return Pattern.matches(REGEX_CHAR_UPPER, source);
    }

    /**
     * 是否纯特殊符号串
     *
     * @param source
     *         判断的源字符串
     *
     * @return true纯特殊符号串，false非纯特殊符号串
     */
    public static boolean isSymbol(String source) {
        //        Pattern p = Pattern.compile("[{\\[(<~!@#$%^&*()_+=-`|\"?,./;'\\>)\\]}]*");
        Pattern p = Pattern.compile("^[`~!@#$%^&*()+=|{}':;',\\[\\].<>《》/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]+$");
        Matcher m = p.matcher(source);
        return m.matches();
    }

    /**
     * 固定电话号码验证
     *
     * @param str
     *         需要验证的String
     *
     * @return 验证通过返回true
     */
    public static boolean isTelPhone(String str) {
        //        (\d{3,4}|\d{3,4}-|\s)?\d{6,10}
        //        p1 = Pattern.compile("^0\\d{2,3}-?\\d{7,8}$");  // 验证带区号的
        Pattern p1 = Pattern.compile("^[0][1-9]{2,3}-[1-9]{1}[0-9]{5,10}$");  // 验证带区号的 区号第一位为0，后面2至3位数字，电话第一位1-9 后面为5至10位纯数字
        Pattern p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号 第一位1-9 后面5至8位纯数字
        if (str.length() > 9) {
            Matcher m = p1.matcher(str);
            return m.matches();
        } else {
            Matcher m = p2.matcher(str);
            return m.matches();
        }
    }

    /**
     * 校验是否是以零开的整数字符串
     *
     * @param num
     *         校验的字符串
     *
     * @return 验证通过返回true
     */
    public static boolean isValidNaturalNumber(String num) {
        Pattern p = Pattern.compile("^[0|.][0-9]+$");
        Matcher m = p.matcher(num);
        return m.matches();
    }
}