//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.android.base.utils.code;

import android.text.TextUtils;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import javax.crypto.Cipher;

/**
 * RSA加密
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class RSACoder {

    /** 算法 */
    private static final String ALGORITHM = "RSA";
    /** 加解密统一使用的编码方式 */
    private final static String ENCODING = "UTF-8";
    /** 公钥 */
    private static Key mPublicKey;
    /** 私钥 */
    private static Key mPrivateKey;

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
        if (mPublicKey == null) {
            initKey();
        }
        try {
            // 实例化加解密类
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 加密
            cipher.init(Cipher.ENCRYPT_MODE, mPublicKey);
            //将明文转化为根据公钥加密的密文，为byte数组格式
            byte[] enBytes = cipher.doFinal(str.getBytes(ENCODING));
            //将byte数组转化为base64的编码
            // return Base64.encodeToString(enBytes, Base64.NO_WRAP);
            return Base64.encode(enBytes);
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
        if (mPrivateKey == null) {
            initKey();
        }
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, mPrivateKey);
            //先将转为base64编码的加密后的数据转化为byte数组
            //byte[] enBytes = Base64.decode(str, Base64.NO_WRAP);
            byte[] enBytes = Base64.decode(str);
            //解密称为byte数组，应该为字符串数组最后转化为字符串
            byte[] deBytes = cipher.doFinal(enBytes);
            return new String(deBytes, ENCODING);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    private static void initKey() {
        try {
            //设置使用何种加密算法
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
            // 密钥位数 这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低 256加密后字符串长度==44 186==32
            keyPairGen.initialize(186);
            // 密钥对
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 公钥
            mPublicKey = keyPair.getPublic();
            // 私钥
            mPrivateKey = keyPair.getPrivate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //
    //    /**
    //     * 函数说明：getPublicKey 取得公钥
    //     * <p>
    //     * <br> Version: 1.0.0
    //     * <br> CreateTime: 2016-1-22,下午4:01:36
    //     * <br> UpdateTime: 2016-1-22,下午4:01:36
    //     * <br> CreateAuthor: 叶青
    //     * <br> UpdateAuthor: 叶青
    //     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
    //     *
    //     * @param key
    //     *         公钥字符串
    //     *
    //     * @return PublicKey 返回公钥
    //     *
    //     * @throws Exception
    //     */
    //    public static PublicKey getPublicKey(String key) throws Exception {
    //        byte[] keyBytes = Base64.decode(key, Base64.NO_WRAP);
    //
    //        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
    //        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
    //        return keyFactory.generatePublic(keySpec);
    //    }
    //
    //    /**
    //     * 函数说明：getPrivateKey 取得私钥
    //     * <p>
    //     * <br> Version: 1.0.0
    //     * <br> CreateTime: 2016-1-22,下午4:01:36
    //     * <br> UpdateTime: 2016-1-22,下午4:01:36
    //     * <br> CreateAuthor: 叶青
    //     * <br> UpdateAuthor: 叶青
    //     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
    //     *
    //     * @param key
    //     *         私钥字符串
    //     *
    //     * @return PrivateKey 返回私钥
    //     *
    //     * @throws Exception
    //     */
    //    public static PrivateKey getPrivateKey(String key) throws Exception {
    //        byte[] keyBytes = Base64.decode(key, Base64.NO_WRAP);
    //
    //        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
    //        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
    //        return keyFactory.generatePrivate(keySpec);
    //    }
}