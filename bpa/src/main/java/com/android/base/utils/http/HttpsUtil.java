package com.android.base.utils.http;

import android.annotation.SuppressLint;

import com.android.base.executor.Cancel;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Https网络操作类.
 * <p>
 * 用于网络的POST 、 GET 、 download、upload等操作
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年3月30日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class HttpsUtil {

    /** 返回结果 */
    private String result;
    /** 输入流 */
    private BufferedInputStream input;
    /** https链接 */
    private HttpsURLConnection conn;
    /** 输出流 */
    private DataOutputStream output;

    private static class TrustAnyTrustManager implements X509TrustManager {

        @SuppressLint("TrustAllX509TrustManager")
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @SuppressLint("TrustAllX509TrustManager")
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @SuppressLint("BadHostnameVerifier")
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * get方式请求服务器(https协议)
     *
     * @param url
     *         请求地址
     * @param timeout
     *         超时时间(毫秒).
     */
    public String sendGet(String url, int timeout) throws IOException, TimeoutException, Cancel.CancelException {
        try {
            Cancel.checkInterrupted();
            URL console = new URL(url);
            // 通过指定协议（一般是TLS）获取SSL上下文（SSLContext）实例。
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());

            conn = (HttpsURLConnection) console.openConnection();
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            //            conn.setDoOutput(true); // 设置输出,post请求必须设置.
            //            conn.setDoInput(true); // 设置输入,post请求必须设置.
            //            conn.setUseCaches(false); // 设置是否启用缓存,post请求不能使用缓存.
            //            conn.setRequestProperty("Content-Type", "text/html;charset=utf-8"); // 设置Content-Type属性.
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.setRequestMethod("GET");
            conn.connect(); // 打开网络链接.

            //            Cancel.checkInterrupted();
            //            output = new DataOutputStream(conn.getOutputStream());
            //            if (attribute != null)
            //                output.write(getParams(attribute).getBytes("utf-8"));
            //
            //            output.flush();// flush输出流的缓冲
            Cancel.checkInterrupted();
            return readResponse();
        } catch (SocketTimeoutException e) {
            throw new TimeoutException(e.getMessage());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new IOException(e.getMessage());
        }
    }


    /**
     * post方式请求服务器(https协议)
     *
     * @param url
     *         请求地址
     * @param timeout
     *         超时时间(毫秒).
     * @param attribute
     *         发送请求参数,key为属性名,value为属性值.
     */
    public String sendPost(String url, int timeout, HashMap<String, String> attribute) throws IOException, TimeoutException, Cancel.CancelException {
        try {
            Cancel.checkInterrupted();
            URL console = new URL(url);
            // 通过指定协议（一般是TLS）获取SSL上下文（SSLContext）实例。
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());

            conn = (HttpsURLConnection) console.openConnection();
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.setDoOutput(true); // 设置输出,post请求必须设置.
            conn.setDoInput(true); // 设置输入,post请求必须设置.
            conn.setUseCaches(false); // 设置是否启用缓存,post请求不能使用缓存.
            //            conn.setRequestProperty("Content-Type", "text/html;charset=utf-8"); // 设置Content-Type属性.
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.setRequestMethod("POST");
            conn.connect(); // 打开网络链接.

            Cancel.checkInterrupted();
            output = new DataOutputStream(conn.getOutputStream());
            if (attribute != null) {
                output.write(getParams(attribute).getBytes("utf-8"));
            }

            output.flush();// flush输出流的缓冲
            Cancel.checkInterrupted();
            return readResponse();
        } catch (SocketTimeoutException e) {
            throw new TimeoutException(e.getMessage());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * 读取服务器响应信息.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月31日, 上午3:04:49
     * <br> UpdateTime: 2016年12月31日, 上午3:04:49
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容, 若无修改可不写.)
     *
     * @return 服务器的响应信息, 当发生错误时返回响应码.
     *
     * @throws IOException
     *         读取信息发生错误时抛出IOException.
     */
    private String readResponse() throws IOException, Cancel.CancelException {
        int code = conn.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            // 若响应码为200 则读取响应头总的返回信息
            input = new BufferedInputStream(conn.getInputStream());
            ByteArrayBuffer arrayBuffer = new ByteArrayBuffer(1024);
            int length = -1;
            Thread currentThread = Thread.currentThread();

            while ((length = input.read()) != -1) {
                Cancel.checkInterrupted(currentThread);
                arrayBuffer.append(length);
            }
            result = EncodingUtils.getString(arrayBuffer.toByteArray(), "UTF-8");
        } else {
            // 若响应码不为200 则返回错误信息.
            return "error=====>conn.getResponseCode():" + code;
        }
        closeConnection();
        return result;
    }

    /**
     * 将发送请求的参数构造为指定格式.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月31日, 上午3:05:28
     * <br> UpdateTime: 2016年12月31日, 上午3:05:28
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容, 若无修改可不写.)
     *
     * @param attribute
     *         发送请求的参数,key为属性名,value为属性值.
     *
     * @return 指定格式的请求参数.
     */
    private String getParams(HashMap<String, String> attribute) {
        Set<String> keys = attribute.keySet(); // 获取所有参数名
        Iterator<String> iterator = keys.iterator(); // 将所有参数名进行跌代
        StringBuilder params = new StringBuilder();
        // 取出所有参数进行构造
        while (iterator.hasNext()) {
            try {
                String key = iterator.next();
                String param = key + "=" + URLEncoder.encode(attribute.get(key), "utf-8") + "&";
                params.append(param);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        // 返回构造结果
        if (params.toString().length() > 0) {
            return params.toString().substring(0, params.toString().length() - 1);
        } else {
            return "";
        }
    }

    /**
     * 关闭链接与所有从链接中获得的流.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月31日, 上午3:06:27
     * <br> UpdateTime: 2016年12月31日, 上午3:06:27
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容, 若无修改可不写.)
     *
     * @throws IOException
     *         关闭发生错误时抛出IOException.
     */
    private void closeConnection() throws IOException {
        if (input != null) {
            input.close();
        }
        if (output != null) {
            output.close();
        }
        if (conn != null) {
            conn.disconnect();
        }
    }
}