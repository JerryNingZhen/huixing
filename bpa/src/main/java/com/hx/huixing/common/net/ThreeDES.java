package com.hx.huixing.common.net;

import java.io.ByteArrayOutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class ThreeDES {
    private static final String Algorithm = "Blowfish"; // 定义 加密算法,可用
    // DES,DESede,Blowfish
    private static final String hexString = "0123456789ABCDEF";

    /**
     * @param keybyte 加密密钥，长度为24字节
     * @param src     字节数组(根据给定的字节数组构造一个密钥。 )
     * @return
     */
    public static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            // 根据给定的字节数组和算法构造一个密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e) {
            LogUtils.e(e);
        } catch (javax.crypto.NoSuchPaddingException e) {
            LogUtils.e(e);
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return null;
    }

    /**
     * @param keybyte 密钥
     * @param src     需要解密的数据
     * @return
     */
    public static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            // 解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e) {
            LogUtils.e(e);
        } catch (javax.crypto.NoSuchPaddingException e) {
            LogUtils.e(e);
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return null;
    }

    /**
     * 字符串转为16进制
     *
     * @param str
     * @return
     */
    public static String encode(String str) {
        // 根据默认编码获取字节数组
        byte[] bytes = str.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        String strs = "";

        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }

    /**
     * @param bytes
     * @return 将16进制数字解码成字符串, 适用于所有字符（包括中文）
     */
    public static String decode(String bytes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(
                bytes.length() / 2);
        // 将每2位16进制整数组装成一个字节
        for (int i = 0; i < bytes.length(); i += 2)
            baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
                    .indexOf(bytes.charAt(i + 1))));
        return new String(baos.toByteArray());
    }

    // 转换成十六进制字符串
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + ":";
        }
        return hs.toUpperCase();
    }


    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 加密
     *
     * @param src
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptThreeDESECB(String src, String key) throws Exception {
        DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey securekey = keyFactory.generateSecret(dks);

        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, securekey);
        byte[] b = cipher.doFinal(src.getBytes("utf-8"));
        return bytesToHexString(b);
    }

    /**
     * 解密
     *
     * @param src
     * @param key
     * @return
     * @throws Exception
     */
    // 3DESECB解密,key必须是长度大于等于 3*8 = 24 位
    public static String decryptThreeDESECB(String src, String key) throws Exception {
        DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey securekey = keyFactory.generateSecret(dks);

        // --Chipher对象解密
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, securekey);
        byte[] retByte = cipher.doFinal(src.getBytes("UTF-8"));
        return new String(retByte);
    }

    /**
     * 用户名解密
     *
     * @param ssoToken 字符串
     * @return String 返回解密字符串
     */
    public static String decrypt(String ssoToken) throws Exception {
        String name = new String();
        java.util.StringTokenizer st = new java.util.StringTokenizer(ssoToken, "%");
        while (st.hasMoreElements()) {
            int asc = Integer.parseInt((String) st.nextElement()) - 27;
            name = name + (char) asc;
        }
        return name;
    }

    /**
     * 用户名加密
     *
     * @param ssoToken 字符串
     * @return String 返回加密字符串
     */
    public static String encrypt(String ssoToken) throws Exception {
        byte[] _ssoToken = ssoToken.getBytes("ISO-8859-1");
        String name = new String();
        for (int i = 0; i < _ssoToken.length; i++) {
            int asc = _ssoToken[i];
            _ssoToken[i] = (byte) (asc + 27);
            name = name + (asc + 27) + "%";
        }
        return name;
    }

}
