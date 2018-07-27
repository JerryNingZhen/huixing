package com.android.base.utils;

import android.text.TextUtils;

import java.util.Random;

/**
 * 随机数工具类
 * 
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class RandomUtil {

	private static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String NUMBERS = "0123456789";
	private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";

	/**
	 * 获取一个固定长度的随机字符串 （大写字母,小写字母和数字）
	 * 
	 * <br> Version: 1.0.0
	 * <br> CreateTime: 2016-1-23,下午8:45:58
	 * <br> UpdateTime: 2016-1-23,下午8:45:58
	 * <br> CreateAuthor: 叶青
	 * <br> UpdateAuthor: 叶青
	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param length
	 *            随机字符串的长度
	 * @return 固定长度的随机字符串
	 */
	public static String getRandomNumbersAndLetters(int length) {
		return getRandom(NUMBERS_AND_LETTERS, length);
	}

	/**
	 * 获取一个固定长度的纯数字的随机字符串 （数字）
	 * 
	 * <br> Version: 1.0.0
	 * <br> CreateTime: 2016-1-23,下午8:45:58
	 * <br> UpdateTime: 2016-1-23,下午8:45:58
	 * <br> CreateAuthor: 叶青
	 * <br> UpdateAuthor: 叶青
	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param length
	 *            随机字符串的长度
	 * @return 固定长度的随机字符串
	 */
	public static String getRandomNumbers(int length) {
		return getRandom(NUMBERS, length);
	}

	/**
	 * 获取一个固定长度的纯字母的随机字符串（大写字母,小写字母）
	 * 
	 * <br> Version: 1.0.0
	 * <br> CreateTime: 2016-1-23,下午8:45:58
	 * <br> UpdateTime: 2016-1-23,下午8:45:58
	 * <br> CreateAuthor: 叶青
	 * <br> UpdateAuthor: 叶青
	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param length  长度
	 * @return 固定长度的随机字符串
	 * @see RandomUtil#getRandom(String source, int length)
	 */
	public static String getRandomLetters(int length) {
		return getRandom(LETTERS, length);
	}

	/**
	 * 获取一个固定长度的纯大写字母的随机的字符串
	 * 
	 * <br> Version: 1.0.0
	 * <br> CreateTime: 2016-1-23,下午8:45:58
	 * <br> UpdateTime: 2016-1-23,下午8:45:58
	 * <br> CreateAuthor: 叶青
	 * <br> UpdateAuthor: 叶青
	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param length
	 *            随机字符串的长度
	 * @return 固定长度的随机字符串
	 */
	public static String getRandomCapitalLetters(int length) {
		return getRandom(CAPITAL_LETTERS, length);
	}

	/**
	 * 获取一个固定长度的纯小写字母的随机的字符串
	 * 
	 * <br> Version: 1.0.0
	 * <br> CreateTime: 2016-1-23,下午8:45:58
	 * <br> UpdateTime: 2016-1-23,下午8:45:58
	 * <br> CreateAuthor: 叶青
	 * <br> UpdateAuthor: 叶青
	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param length
	 *            随机字符串的长度
	 * @return 固定长度的随机字符串
	 */
	public static String getRandomLowerCaseLetters(int length) {
		return getRandom(LOWER_CASE_LETTERS, length);
	}

	/**
	 * 从source中获取一个固定长度的随机字符串
	 * 
	 * <br> Version: 1.0.0
	 * <br> CreateTime: 2016-1-23,下午8:45:58
	 * <br> UpdateTime: 2016-1-23,下午8:45:58
	 * <br> CreateAuthor: 叶青
	 * <br> UpdateAuthor: 叶青
	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param source
	 *            字符源
	 * @param length
	 *            随机字符串长度
	 * @return source==null,return null
	 */
	public static String getRandom(String source, int length) {
		return TextUtils.isEmpty(source) ? null : getRandom(source.toCharArray(), length);
	}

	/**
	 * 从source中获取一个固定长度的随机字符串
	 * 
	 * <br> Version: 1.0.0
	 * <br> CreateTime: 2016-1-23,下午8:45:58
	 * <br> UpdateTime: 2016-1-23,下午8:45:58
	 * <br> CreateAuthor: 叶青
	 * <br> UpdateAuthor: 叶青
	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param sourceChar
	 *            char[]
	 * @param length
	 *            随机字符串的长度
	 * @return sourceChar==null,return null ;length< 0, return null
	 */
	public static String getRandom(char[] sourceChar, int length) {
		if (sourceChar == null || sourceChar.length == 0 || length < 0) {
			return null;
		}

		StringBuilder str = new StringBuilder(length);
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			str.append(sourceChar[random.nextInt(sourceChar.length)]);
		}
		return str.toString();
	}

	/**
	 * 获取0和max之间的随机整数
	 * 
	 * <br> Version: 1.0.0
	 * <br> CreateTime: 2016-1-23,下午8:45:58
	 * <br> UpdateTime: 2016-1-23,下午8:45:58
	 * <br> CreateAuthor: 叶青
	 * <br> UpdateAuthor: 叶青
	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param max
	 *            最大值
	 * @return if max <= 0, return 0, else return random int between 0 and max
	 */
	public static int getRandom(int max) {
		return getRandom(0, max);
	}

	/**
	 * 获取最小值和最大值之间的随机整数
	 * 
	 * <br> Version: 1.0.0
	 * <br> CreateTime: 2016-1-23,下午8:45:58
	 * <br> UpdateTime: 2016-1-23,下午8:45:58
	 * <br> CreateAuthor: 叶青
	 * <br> UpdateAuthor: 叶青
	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @return if min > max, return 0，if min == max, return min
	 */
	public static int getRandom(int min, int max) {
		if (min > max) {
			return 0;
		}
		if (min == max) {
			return min;
		}
		return min + new Random().nextInt(max - min);
	}

	/**
	 * 随机排列指定的数组
	 * 
	 * <br> Version: 1.0.0
	 * <br> CreateTime: 2016-1-23,下午8:45:58
	 * <br> UpdateTime: 2016-1-23,下午8:45:58
	 * <br> CreateAuthor: 叶青
	 * <br> UpdateAuthor: 叶青
	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param objArray
	 *            Object[]
	 * @return
	 */
	public static boolean shuffle(Object[] objArray) {
		return objArray != null && shuffle(objArray, getRandom(objArray.length));
	}

	/**
	 * 随机排列指定的数组
	 * 
	 * <br> Version: 1.0.0
	 * <br> CreateTime: 2016-1-23,下午8:45:58
	 * <br> UpdateTime: 2016-1-23,下午8:45:58
	 * <br> CreateAuthor: 叶青
	 * <br> UpdateAuthor: 叶青
	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param objArray
	 *            Object[]
	 * @param shuffleCount
	 *            随机次数
	 * @return
	 */
	public static boolean shuffle(Object[] objArray, int shuffleCount) {
		int length;
		if (objArray == null || shuffleCount < 0 || (length = objArray.length) < shuffleCount) {
			return false;
		}

		for (int i = 1; i <= shuffleCount; i++) {
			int random = getRandom(length - i);
			Object temp = objArray[length - i];
			objArray[length - i] = objArray[random];
			objArray[random] = temp;
		}
		return true;
	}

	/**
	 * 随机排列指定int数组
	 * 
	 * <br> Version: 1.0.0
	 * <br> CreateTime: 2016-1-23,下午8:45:58
	 * <br> UpdateTime: 2016-1-23,下午8:45:58
	 * <br> CreateAuthor: 叶青
	 * <br> UpdateAuthor: 叶青
	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param intArray
	 *            int[]
	 * @return 随机排列后的数组
	 */
	public static int[] shuffle(int[] intArray) {
		if (intArray == null) {
			return null;
		}

		return shuffle(intArray, getRandom(intArray.length));
	}

	/**
	 * 随机排列指定int数组
	 * 
	 * <br> Version: 1.0.0
	 * <br> CreateTime: 2016-1-23,下午8:45:58
	 * <br> UpdateTime: 2016-1-23,下午8:45:58
	 * <br> CreateAuthor: 叶青
	 * <br> UpdateAuthor: 叶青
	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param intArray
	 *            int[]
	 * @param shuffleCount
	 *            随机次数
	 * @return 随机排列后的数组
	 */
	public static int[] shuffle(int[] intArray, int shuffleCount) {
		int length;
		if (intArray == null || shuffleCount < 0 || (length = intArray.length) < shuffleCount) {
			return null;
		}

		int[] out = new int[shuffleCount];
		for (int i = 1; i <= shuffleCount; i++) {
			int random = getRandom(length - i);
			out[i - 1] = intArray[random];
			int temp = intArray[length - i];
			intArray[length - i] = intArray[random];
			intArray[random] = temp;
		}
		return out;
	}
}