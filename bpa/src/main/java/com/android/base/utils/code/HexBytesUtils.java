package com.android.base.utils.code;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 数据类型转换函数集
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class HexBytesUtils {

    // *************************************Java 中 byte[] 和 String 之间的转换源码：

    /**
     * 把16进制字符串String转换成字节数组byte[]
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-22,下午5:48:18
     * <br> UpdateTime: 2016-12-22,下午5:48:18
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param str
     *         十六进制串
     */
    public static byte[] hexString2Bytes(String str) { // 字符串转二进制
        int len = str.length();
        String stemp;
        if (len % 2 != 0) {
            len += 1;
        }
        byte bt[] = new byte[len / 2];
        for (int n = 0; n < len / 2; n++) {
            stemp = str.substring(n * 2, n * 2 + 2);
            bt[n] = (byte) (Integer.parseInt(stemp, 16));
        }
        return bt;
    }

    /**
     * 把byte[]转换成16进制String
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-22,下午5:48:18
     * <br> UpdateTime: 2016-12-22,下午5:48:18
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param bytes
     *         字节数组
     */
    public static String bytes2HexString(byte[] bytes) {
        if (bytes != null) {
            StringBuilder sb = new StringBuilder(bytes.length);
            String sTemp;
            for (byte aBArray : bytes) {
                sTemp = Integer.toHexString(0xFF & aBArray);
                if (sTemp.length() < 2)
                    sb.append(0);
                sb.append(sTemp.toUpperCase());
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    // *************************************Java 中 byte 和 String 之间的转换源码：

    /**
     * byte转换成16进制字符串
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-22,下午5:48:18
     * <br> UpdateTime: 2016-12-22,下午5:48:18
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param b
     *         字节
     */
    public static String byte2HexString(byte b) {
        StringBuilder sb = new StringBuilder();
        String sTemp;
        sTemp = Integer.toHexString(0xFF & b);
        if (sTemp.length() < 2)
            sb.append(0);
        sb.append(sTemp.toUpperCase());
        return sb.toString();
    }

    /**
     * 16进制字符串转换为byte
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-3-21,下午2:16:38
     * <br> UpdateTime: 2016-3-21,下午2:16:38
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param str
     *         16进制字符串
     */
    public static byte hexStr2Byte(String str) {
        return (byte) Integer.parseInt(str.substring(0, 2), 16);
    }

    // *************************************Java 中 byte[] 和 Object 之间的转换源码：

    /**
     * 把byte[]转换为Object
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-22,下午5:48:18
     * <br> UpdateTime: 2016-12-22,下午5:48:18
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param bytes
     *         bytes数组
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object bytes2Object(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = new ObjectInputStream(in);
        Object o = oi.readObject();
        oi.close();
        return o;
    }

    /**
     * 把可序列化对象转换成字节数组
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-22,下午5:48:18
     * <br> UpdateTime: 2016-12-22,下午5:48:18
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param s
     *         可序列化对象Serializable
     */
    public static byte[] object2Bytes(Serializable s) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream ot = new ObjectOutputStream(out);
        ot.writeObject(s);
        ot.flush();
        ot.close();
        return out.toByteArray();
    }

    /**
     * 把可序列化对象Serializable转换成String
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-22,下午5:48:18
     * <br> UpdateTime: 2016-12-22,下午5:48:18
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param s
     *         可序列化对象Serializable
     *
     * @throws IOException
     */
    public static String objectToHexString(Serializable s) throws IOException {
        return bytes2HexString(object2Bytes(s));
    }

    /**
     * 16进制转Object
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-22,下午5:48:18
     * <br> UpdateTime: 2016-12-22,下午5:48:18
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param hex
     *         16进制字符串
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object hexStringToObject(String hex) throws IOException, ClassNotFoundException {
        return bytes2Object(hexString2Bytes(hex));
    }

    // *************************************Java 中 byte[] 和 int 之间的转换源码：

    /**
     * 字节数组转转换成整形int
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午4:27:05
     * <br> UpdateTime: 2016-11-24,下午4:27:05
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param b
     *         bytes
     */
    public static int bytes2int(byte[] b) {
        int temp = 0;
        int a1, a2;
        a1 = (int) (b[0]);
        a2 = (int) (b[1]);
        if (a1 < 0)
            a1 += 256;
        if (a2 < 0)
            a2 += 256;
        temp = a1 << 8;
        temp |= a2 | a1;
        return temp;
    }

    /**
     * 整形int转转换成字节数组
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午4:27:05
     * <br> UpdateTime: 2016-11-24,下午4:27:05
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param i
     *         整形数字
     */
    public static byte[] int2bytes(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    // *********************************Java 中 byte 和 int 之间的转换源码：

    /**
     * byte 与 int 的相互转换
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-10-27,下午4:29:29
     * <br> UpdateTime: 2016-10-27,下午4:29:29
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param i
     *         整形数字
     */
    public static byte int2Byte(int i) {
        return (byte) i;
    }

    /**
     * Java 中 byte 和 int 之间的转换源码：
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-10-27,下午4:29:43
     * <br> UpdateTime: 2016-10-27,下午4:29:43
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param b
     *         字节
     */
    public static int byte2Int(byte b) {
        // Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }

    /**
     * Byte转Bit
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-2-24,上午11:49:56
     * <br> UpdateTime: 2016-2-24,上午11:49:56
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param b
     *         byte
     */
    public static String byte2Bit(byte b) {
        return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1) + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1) + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1) + (byte) ((b >> 1) & 0x1)
                + (byte) ((b) & 0x1);
    }

    /**
     * Bit转Byte
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-2-24,上午11:49:48
     * <br> UpdateTime: 2016-2-24,上午11:49:48
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param byteStr
     *         字符串
     */
    public static byte bit2Byte(String byteStr) {
        int re, len;
        if (null == byteStr) {
            return 0;
        }
        len = byteStr.length();
        if (len != 4 && len != 8) {
            return 0;
        }
        if (len == 8) {// 8 bit处理
            if (byteStr.charAt(0) == '0') {// 正数
                re = Integer.parseInt(byteStr, 2);
            } else {// 负数
                re = Integer.parseInt(byteStr, 2) - 256;
            }
        } else {// 4 bit处理
            re = Integer.parseInt(byteStr, 2);
        }
        return (byte) re;
    }
}