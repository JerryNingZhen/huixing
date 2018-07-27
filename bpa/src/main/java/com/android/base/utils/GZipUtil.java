package com.android.base.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * GZipUtil 字符串压缩 解压工具类 
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2017/11/16
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class GZipUtil {

    /***
     * 压缩GZip
     */
    public static byte[] compress(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.finish();
            gzip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * 解压GZip
     *
     * @param data
     * @return
     */
    public static byte[] decompress(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            GZIPInputStream gzip = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
            gzip.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    public static void main(String[] args) {
        String s = "this is a test";

        byte[] b5 = compress(s.getBytes());
        System.out.println("gZip:" + bytesToHexString(b5));
        byte[] b6 = decompress(b5);
        System.out.println("unGZip:" + new String(b6));
        byte[] b7 = decompress(hexString2byte("1f8b0800000000000013333432363135030061d3720906000000"));
        System.out.println("unGZip:" + new String(b7));
    }
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
    public static byte[] hexString2byte(String str) { // 字符串转二进制
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
    public static String bytesToHexString(byte[] bytes) {
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

} 