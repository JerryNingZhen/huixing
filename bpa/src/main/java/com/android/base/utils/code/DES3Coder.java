package com.android.base.utils.code;

import android.text.TextUtils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 三重Des对称加密
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class DES3Coder {

    /**
     * 1、是3DES。
     * 2、分组方式是ECB，所以不需要初始化向量 CBC需要初始化向量。
     * 3、填充模式是PKCS5Padding。  16字节加密后数据长度32  不满16字节加密后长度16
     */
    private static final String TRANSFORMATION_TYPE_CBC = "DESede/CBC/PKCS5Padding";
    private static final String TRANSFORMATION_TYPE_ECB = "DESede/ECB/PKCS5Padding";
    /** 算法 */
    private static final String ALGORITHM = "DESede";
    /** 密钥 长度不得小于24 */
    private final static String SECRET_KEY = "123456789012345678901234";
    /** 向量 可有可无 终端后台也要约定 must be 8 bytes long 01234567 */
    private final static String IV_KEY = "01234567";
    /** 加解密统一使用的编码方式 */
    private final static String ENCODING = "UTF-8";

    /**
     * 加密
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-22,下午4:01:13
     * <br> UpdateTime: 2016-1-22,下午4:01:13
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param str
     *         需要加密的明文
     *
     * @return 加密后的密文
     */
    public static String encrypt(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            DESedeKeySpec spec = new DESedeKeySpec(SECRET_KEY.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            Key key = keyFactory.generateSecret(spec);
            Cipher cipher;
            if (TextUtils.isEmpty(IV_KEY)) {
                cipher = Cipher.getInstance(TRANSFORMATION_TYPE_ECB);
                cipher.init(Cipher.ENCRYPT_MODE, key);
            } else {
                cipher = Cipher.getInstance(TRANSFORMATION_TYPE_CBC);
                IvParameterSpec iv = new IvParameterSpec(IV_KEY.getBytes());
                cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            }
            byte[] encryptData = cipher.doFinal(str.getBytes(ENCODING));
            // 将byte数组转化为base64的编码
            //return Base64.encodeToString(encryptData, Base64.NO_WRAP);
            return Base64.encode(encryptData);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 解密
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-22,下午4:01:36
     * <br> UpdateTime: 2016-1-22,下午4:01:36
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param str
     *         需要解密的密文
     *
     * @return 解密的明文文
     */
    public static String decrypt(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            DESedeKeySpec spec = new DESedeKeySpec(SECRET_KEY.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            Key key = keyFactory.generateSecret(spec);
            Cipher cipher;
            if (TextUtils.isEmpty(IV_KEY)) {
                cipher = Cipher.getInstance(TRANSFORMATION_TYPE_ECB);
                cipher.init(Cipher.DECRYPT_MODE, key);
            } else {
                cipher = Cipher.getInstance(TRANSFORMATION_TYPE_CBC);
                IvParameterSpec iv = new IvParameterSpec(IV_KEY.getBytes());
                cipher.init(Cipher.DECRYPT_MODE, key, iv);
            }
            //先将转为base64编码的加密后的数据转化为byte数组
            // byte[] enBytes = Base64.decode(str, Base64.NO_WRAP);
            byte[] enBytes = Base64.decode(str);
            //解密称为byte数组，应该为字符串数组最后转化为字符串
            byte[] decryptData = cipher.doFinal(enBytes);
            return new String(decryptData, ENCODING);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}