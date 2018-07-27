package com.android.base.utils.http;

import android.net.Uri;

import com.android.base.BaseApplication;
import com.android.base.mvp.model.BaseBiz;
import com.android.base.configs.ConfigFile;
import com.android.base.configs.ConfigServer;
import com.android.base.executor.Cancel;
import com.android.base.interfaces.OnDownLoadCallBack;
import com.android.base.utils.LogUtil;
import com.android.base.utils.StringUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 网络操作类.
 * <p>
 * 用于网络的POST 、 GET 、 download、upload等操作
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年3月30日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class HttpUtil {

    private static HttpUtil httpUtil;

    public static HttpUtil getInstance() {
        if (httpUtil == null) {
            httpUtil = new HttpUtil();
        }
        return httpUtil;
    }

    /** 加解密统一使用的编码方式 */
    private final static String ENCODING = "UTF-8";

    /**
     * 像指定地址发送post请求提交数据.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月31日,上午2:06:53
     * <br> UpdateTime: 2016年12月31日,上午2:06:53
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param path
     *         数据提交路径.
     * @param params
     *         发送请求参数,key为属性名,value为属性值.
     *
     * @return 服务器的响应信息, 当发生错误时返回响应码.
     *
     * @throws IOException
     *         网络连接错误时抛出IOException.
     * @throws TimeoutException
     *         网络连接超时时抛出TimeoutException.
     */
    public String sendPost(String path, Map<String, String> params) throws IOException, TimeoutException, Cancel.CancelException {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false); // 设置是否启用缓存,post请求不能使用缓存.
            conn.setDoOutput(true); // 设置输出,post请求必须设置.
            conn.setDoInput(true); // 设置输入,post请求必须设置.
            // 设置通用的请求属性 // 请求头数据
            //            conn.setRequestProperty("Content-Type", "text/html;charset=utf-8");//有时候你需要给 connection 指定 Content-type
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("charset", ENCODING);
            Map<String, String> headersParams = BaseBiz.getHeaders();
            for (Map.Entry<String, String> entry : headersParams.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
            conn.setConnectTimeout(ConfigServer.SERVER_CONNECT_TIMEOUT);
            conn.setReadTimeout(ConfigServer.SERVER_CONNECT_TIMEOUT);
            conn.setRequestMethod("POST");
            conn.connect(); // 打开网络链接.
            Cancel.checkInterrupted();

            // 输出流 */
            DataOutputStream output = new DataOutputStream(conn.getOutputStream());
            output.writeBytes(getParams(params)); // 将请求参数写入网络链接.
            output.flush();
            output.close();

            Cancel.checkInterrupted();
            return readResponse(conn);
        } catch (SocketTimeoutException e) {
            throw new TimeoutException(e.getMessage());
        }
    }

    /**
     * 像指定地址发送get请求.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月31日,上午3:04:16
     * <br> UpdateTime: 2016年12月31日,上午3:04:16
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param path
     *         数据提交路径.
     * @param params
     *         参数
     *
     * @return 服务器的响应信息, 当发生错误时返回响应码.
     *
     * @throws IOException
     *         网络连接错误时抛出IOException.
     * @throws TimeoutException
     *         网络连接超时时抛出TimeoutException.
     */
    public String sendGet(String path, Map<String, String> params) throws IOException, TimeoutException, Cancel.CancelException {
        try {
            Uri.Builder builderUri = Uri.parse(path).buildUpon();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builderUri.appendQueryParameter(entry.getKey(), entry.getValue());
            }
            Uri uri = builderUri.build();
            URL url = new URL(uri.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false); // 设置是否启用缓存,post请求不能使用缓存.
            // 设置通用的请求属性 // 请求头数据
            //            conn.setRequestProperty("Content-Type", "text/html;charset=utf-8");//有时候你需要给 connection 指定 Content-type
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("charset", ENCODING);
            Map<String, String> headersParams = BaseBiz.getHeaders();
            for (Map.Entry<String, String> entry : headersParams.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
            conn.setConnectTimeout(ConfigServer.SERVER_CONNECT_TIMEOUT);
            conn.setReadTimeout(ConfigServer.SERVER_CONNECT_TIMEOUT);
            conn.setRequestMethod("GET");

            conn.connect(); // 打开网络链接.
            Cancel.checkInterrupted();
            return readResponse(conn);
        } catch (SocketTimeoutException e) {
            throw new TimeoutException(e.getMessage());
        }
    }

    /**
     * 读取服务器响应信息.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月31日,上午3:04:49
     * <br> UpdateTime: 2016年12月31日,上午3:04:49
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return 服务器的响应信息, 当发生错误时返回响应码.
     *
     * @throws IOException
     *         读取信息发生错误时抛出IOException.
     */
    private String readResponse(HttpURLConnection conn) throws IOException, Cancel.CancelException {
        // 返回结果
        String result;
        int responseCode = conn.getResponseCode();
        LogUtil.i(conn.getURL() + "\n" + "conn.getResponseCode()---->>>>" + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // 若响应码以2开头则读取响应头总的返回信息
            //返回打开连接读取的输入流
            InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder stringBuffer = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                Cancel.checkInterrupted();
                stringBuffer.append(line);
            }
            //result = EncodingUtils.getString(stringBuffer.toString().getBytes(), ENCODING);
            result = new String(stringBuffer.toString().getBytes(), ENCODING);
            inputStreamReader.close();
            bufferedReader.close();
        } else { // 若响应码不以2开头则返回错误信息.
            result = "error";
        }

        conn.disconnect();
        return result;
    }

    /**
     * 将发送请求的参数构造为指定格式.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月31日,上午3:05:28
     * <br> UpdateTime: 2016年12月31日,上午3:05:28
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param params
     *         发送请求的参数,key为属性名,value为属性值.
     *
     * @return 指定格式的请求参数.
     */
    private String getParams(Map<String, String> params) {
        if (params.size() <= 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        // 取出所有参数进行构造
        for (Map.Entry<String, String> entry : params.entrySet()) {
            try {
                String param = entry.getKey() + "=" + entry.getValue() + "&";
                // String param = entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), ENCODING) + "&";
                stringBuilder.append(param);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 返回构造结果
        return stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
    }


    // **************************************文件上传下载

    /**
     * 文件上传
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月31日,上午3:11:17
     * <br> UpdateTime: 2016年12月31日,上午3:11:17
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param path
     *         服务器地址
     * @param params
     *         附带参数集合
     * @param files
     *         文件集合，支持多文件上传
     */
    public String uploadFile(String path, Map<String, String> params, Map<String, File> files) throws IOException, Cancel.CancelException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(path);
        MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        if (params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                StringBody stringBody = new StringBody(entry.getValue(), Charset.forName(HTTP.UTF_8));
                multipartEntity.addPart(entry.getKey(), stringBody);
            }
        }
        if (files.size() > 0) {
            for (Map.Entry<String, File> entry : files.entrySet()) {
                FileBody fileBody = new FileBody(entry.getValue());
                multipartEntity.addPart(entry.getKey(), fileBody);
            }
        }
        // 请求头数据
        Map<String, String> headersParams = BaseBiz.getHeaders();
        for (Map.Entry<String, String> entry : headersParams.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }
        httpPost.setEntity(multipartEntity);

        String result;
        HttpResponse response = httpClient.execute(httpPost);
        Cancel.checkInterrupted();
        int statueCode = response.getStatusLine().getStatusCode();
        LogUtil.i(path + "\n" + "conn.getResponseCode()---->>>>" + statueCode);
        if (statueCode == HttpURLConnection.HTTP_OK) {
            result = EntityUtils.toString(response.getEntity(), ENCODING);
            //result = new String(response.getEntity(), ENCODING);
            LogUtil.json(path, result);
        } else {
            result = "error";
        }

        try {
            multipartEntity.consumeContent();
            httpPost.abort();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cancel.checkInterrupted();
        return result;

    }

    /**
     * 下载文件 并返回当前下载的进度
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:: 2016-11-3,下午2:08:57
     * <br> UpdateTime: 2016-11-3,下午2:08:57
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: : (此处输入修改内容, 若无修改可不写.)
     *
     * @param url
     *         下载路径
     * @param downLoadCallable
     *         监听线程回调接口
     *
     * @return false：下载文件失败；true:下载文件成功
     */
    public boolean downloadFile(String url, OnDownLoadCallBack downLoadCallable) throws IOException, Cancel.CancelException {

        File file = new File(ConfigFile.PATH_DOWNLOAD);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return false;
            }
        }

        String localPath = ConfigFile.PATH_DOWNLOAD + StringUtil.getFileName(url);
        File localFile = new File(localPath);

        // 文件已经存在，无需下载
        if (localFile.exists()) {
            return true;
        }

        File[] files = new File(ConfigFile.PATH_DOWNLOAD).listFiles();
        String fileName = StringUtil.getFileName(url);
        if (files != null) {
            for (File fileItem : new File(ConfigFile.PATH_DOWNLOAD).listFiles()) {
                if (fileItem.getName().contains(fileName)) {
                    fileItem.delete();
                }
            }
        }

        URL urlNet = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlNet.openConnection();
        conn.setReadTimeout(ConfigServer.SERVER_CONNECT_TIMEOUT);//设置客户端连接超时间隔，如果超过指定时间  服务端还没有响应 不等了
        //            conn.setRequestMethod("GET");
        //            conn.setDoInput(true);
        //            conn.connect();
        // 下载文件的总大小
        long fileSize = conn.getContentLength();
        int code = conn.getResponseCode();
        LogUtil.i(url + "\n" + "conn.getResponseCode()---->>>>" + code);
        if (code == HttpURLConnection.HTTP_OK) {
            Cancel.checkInterrupted();
            InputStream input = conn.getInputStream();

            // 临时缓存文件
            // /storage/emulated/0/Diver/3E53AE683F8E8C84221DB763B30FE907/video/558d07830881c.mp4.temp
            String tempPath = localPath + ".temp";
            File tempFile = new File(tempPath);
            // 创建文件
            if (!tempFile.createNewFile()) {
                return false;
            }
            OutputStream output = new FileOutputStream(tempFile);
            byte buffer[] = new byte[2048];
            int read;

            // 已经下载下载文件的总大小
            long finishSize = 0;

            while ((read = input.read(buffer)) != -1) { // 读取信息循环写入文件
                Cancel.checkInterrupted();
                output.write(buffer, 0, read);
                finishSize += read;
                // 当前下载进度
                int progress = (int) (finishSize * 100f / fileSize);
                LogUtil.i("下传进度..." + localFile.getName() + "..." + progress + "%");
                if (downLoadCallable != null) {
                    synchronized (BaseApplication.getInstance().getApplicationContext()) {
                        downLoadCallable.ResultProgress(progress, fileSize, finishSize);
                    }
                }
            }
            output.flush();
            output.close();
            input.close();
            Cancel.checkInterrupted();
            // 文件下载完成，更名为正式缓存文件
            return tempFile.renameTo(localFile);
        }
        Cancel.checkInterrupted();
        return false;
    }
}