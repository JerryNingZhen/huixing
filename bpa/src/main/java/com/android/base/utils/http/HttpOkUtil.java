package com.android.base.utils.http;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.android.base.BaseApplication;
import com.android.base.bean.BaseBean;
import com.android.base.bean.ResponseBean;
import com.android.base.configs.BroadcastFilters;
import com.android.base.configs.ConfigFile;
import com.android.base.configs.ConfigServer;
import com.android.base.configs.ConstantKey;
import com.android.base.interfaces.OnDownLoadCallBack;
import com.android.base.mvp.model.BaseBiz;
import com.android.base.utils.JsonUtil;
import com.android.base.utils.LogUtil;
import com.android.base.utils.StringUtil;
import com.hx.huixing.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

import static com.android.base.mvp.model.BaseBiz.getErrorMsg;

/**
 * okHttp3 实现网络工具类  get、post请求 文件上传 文件下载 图片加载
 * okHttp只会对get请求进行缓存,post请求是不会进行缓存
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2017/4/13
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class HttpOkUtil {

    //    RequestBody：普通的请求参数
    //    FormBody.Builder：以表单的方式传递键值对的请求参数
    //    MultipartBody.Builder：以表单的方式上传文件的请求参数
    //Call
    //enqueue(Callback callback)：异步请求
    //execute()：同步请求

    private OkHttpClient okhttpclient;
    private static HttpOkUtil httpOkUtil;
    //    private Handler okHttpHandler;//全局处理子线程和M主线程通信

    public static HttpOkUtil getInstance() {
        if (httpOkUtil == null) {
            httpOkUtil = new HttpOkUtil();
        }
        return httpOkUtil;
    }

    private OkHttpClient getClient() {
        if (okhttpclient == null) {
            okhttpclient = new OkHttpClient.Builder()
                    .readTimeout(ConfigServer.SERVER_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)   //设置读取超时时间
                    .writeTimeout(ConfigServer.SERVER_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)  //设置写的超时时间
                    .connectTimeout(ConfigServer.SERVER_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)//设置连接超时时间
                    .retryOnConnectionFailure(true)                                            //设置不进行连接失败重试
                    .build();

            //            okHttpHandler = new Handler(BaseApplication.getInstance().getMainLooper());
        }
        return okhttpclient;
    }

    /**
     * 像指定地址发送get请求提交数据.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/4/17 13:57
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/4/17 13:57
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         数据提交路径.
     * @param params
     *         发送请求参数,key为属性名,value为属性值.
     */
    public String sendGet(String url, Map<String, String> params) throws IOException {
        // 添加请求参数
        Uri.Builder builderUri = Uri.parse(url).buildUpon();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builderUri.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        Uri uri = builderUri.build();
        url = uri.toString();

        // 创建request
        //Request request = new Request.Builder().tag(url).url(url).build();
        Request request = addHeaders().tag(url).url(url).build();
        Call call = getClient().newCall(request);           //请求加入队列
        Response response = call.execute();                 //同步的
        LogUtil.i(response.toString());
        if (response.isSuccessful()) {
            //获得返回体
            ResponseBody responseBody = response.body();
            return responseBody.string();
        }

        return "";
    }

    /**
     * 像指定地址发送post请求提交数据.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/4/17 13:57
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/4/17 13:57
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         数据提交路径.
     * @param params
     *         发送请求参数,key为属性名,value为属性值.
     */
    public String sendPost(final String url, Map<String, String> params) throws IOException {
        // 如果是3.0之前版本的，构建表单数据 FormEncodingBuilder; 3.0之后版本FormBody.Builder
        //        FormBody.Builder formBody = new FormBody.Builder();
        //        for (Map.Entry<String, String> entry : params.entrySet()) {
        //            formBody.add(entry.getKey(), entry.getValue());
        //        }
        //        FormBody body = formBody.build();//post请求参数为表单
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), JsonUtil.map2JsonObjectStr((HashMap<String, String>) params)); // JSON为MediaType.parse(“application/json; charset=utf-8”)//post请求，json为参数
        //        StringBuilder tempParams = new StringBuilder();
        //        for (Map.Entry<String, String> entry : params.entrySet()) {
        //            // tempParams.append("&").append(String.format("%s=%s", entry.getKey(), URLEncoder.encode(entry.getValue(), "utf-8")));
        //            tempParams.append("&").append(String.format("%s=%s", entry.getKey(),entry.getValue()));
        //        }
        //        String json = tempParams.toString().replaceFirst("&", "");
        //        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);//post请求，json为参数

        //Request request = new Request.Builder().headers(setHeaders(new HashMap<String, String>())).tag("").url(url).post(body).build();
        Request request = addHeaders().tag(url).url(url).post(body).build();
        Call call = getClient().newCall(request);                    //请求加入队列
        Response response = call.execute();                          //同步的 需要新开线程
        LogUtil.i(response.toString());
        if (response.isSuccessful()) {
            //获得返回体
            ResponseBody responseBody = response.body();
            return responseBody.string();
        }
        return "";
    }

    /**
     * 文件上传
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/4/17 13:58
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/4/17 13:58
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         服务器地址
     * @param params
     *         附带参数集合
     * @param files
     *         文件集合，支持多文件上传
     */
    public String upLoadFile(final String url, Map<String, String> params, Map<String, File> files) throws IOException {
        // form的分割线,自己定义
        String boundary = "xx--------------------------------------------------------------xx";
        MultipartBody.Builder builderBody = new MultipartBody.Builder(boundary);
        // 如果数据包含文件：setType(MultipartBody.FORM);
        builderBody.setType(MultipartBody.FORM);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builderBody.addFormDataPart(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, File> entry : files.entrySet()) {
            final File file = entry.getValue();
            //            RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(file.getPath())), file);
            //            builderBody.addFormDataPart(entry.getKey(), file.getName(), fileBody);
            builderBody.addFormDataPart(entry.getKey(), file.getName(), new RequestBody() {
                @Override
                public MediaType contentType() {
                    return MediaType.parse(guessMimeType(file.getPath()));
                }

                @Override
                public long contentLength() {
                    return file.length();
                }

                @Override
                public void writeTo(BufferedSink sink) {
                    try {
                        Source source = Okio.source(file);
                        Buffer buf = new Buffer();

                        Long contentLength = contentLength();
                        Long remaining = contentLength();
                        for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {// 2048字节 回调一次
                            sink.write(buf, readCount);
                            // 剩余进度
                            remaining = remaining - readCount;
                            // 当前上传进度
                            String progress = (contentLength - remaining) * 100 / contentLength + "%";
                            LogUtil.i(file.getName() + "上传进度..." + contentLength + "..." + remaining + "..." + progress);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        MultipartBody body = builderBody.build();

        // Request request = new Request.Builder().headers(setHeaders(new HashMap<String, String>())).tag("").url(url).post(body).build();
        //        Map<String, String> headersParams = new HashMap<>();
        //        headersParams.put("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary3a0HaJTnvQepi0vf");
        //        Headers.Builder headersBuilder = new Headers.Builder();
        //        for (Map.Entry<String, String> entry : headersParams.entrySet()) {
        //            headersBuilder.add(entry.getKey(), entry.getValue());
        //        }
        //        Request request = new Request.Builder().headers(headersBuilder.build()).tag("").url(url).post(body).build();
        Request request = addHeaders().tag("").url(url).post(body).build();
        Call call = getClient().newCall(request);                    //请求加入队列
        Response response = call.execute();                          //同步的 需要新开线程
        LogUtil.i(response.toString());
        if (response.isSuccessful()) {
            //获得返回体
            ResponseBody responseBody = response.body();
            return responseBody.string();
        }
        return "";
    }

    /**
     * 下载文件存储至指定路径.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/4/17 13:59
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/4/17 13:59
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         下载路径.
     * @param downLoadCallable
     *         监听线程回调接口
     */
    public boolean downLoadFile(final String url, OnDownLoadCallBack downLoadCallable) throws IOException {
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

        //Request request = new Request.Builder().headers(setHeaders(new HashMap<String, String>())).tag(url).url(url).build();
        Request request = addHeaders().tag(url).url(url).build();
        Call call = getClient().newCall(request);                    //请求加入队列
        Response response = call.execute();                          //同步的 需要新开线程
        LogUtil.i(response.toString());
        if (response.isSuccessful()) {
            // 获得返回体
            ResponseBody body = response.body();
            // 下载文件的总大小
            long fileSize = body.contentLength();

            InputStream is;
            FileOutputStream fos;
            byte[] buf = new byte[2048];
            int read;
            // 已经下载下载文件的总大小
            long finishSize = 0;

            is = body.byteStream();

            String tempPath = localPath + ".temp";
            File tempFile = new File(tempPath);
            // 创建文件
            if (!tempFile.createNewFile()) {
                return false;
            }
            fos = new FileOutputStream(tempFile);

            while ((read = is.read(buf)) != -1) {
                fos.write(buf, 0, read);
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

            fos.flush();
            is.close();
            fos.close();
            LogUtil.i("下载文件成功");
            // 文件下载完成，更名为正式缓存文件
            return tempFile.renameTo(localFile);

        }
        return false;
    }

    /**
     * 下载图片 不推荐使用
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/4/17 13:59
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/4/17 13:59
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         下载路径.
     * @param errorResId
     *         下载出错显示的图片资源文件ID
     */
    public Bitmap loadImage(final String url, final int errorResId) throws IOException {
        // Request request = new Request.Builder().headers(setHeaders(new HashMap<String, String>())).tag(url).url(url).build();
        Request request = addHeaders().tag(url).url(url).build();
        Call call = getClient().newCall(request);                     //请求加入队列
        Response response = call.execute();                           //同步的 需要新开线程
        LogUtil.i(response.toString());
        if (response.isSuccessful()) {
            // 获得返回体
            ResponseBody body = response.body();
            //response的body是图片的byte字节
            byte[] bytes = body.bytes();
            //把byte字节组装成图片
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            LogUtil.i("下载图片成功");
            return bmp;
        }
        return BitmapFactory.decodeResource(BaseApplication.getInstance().getResources(), errorResId);
    }

    /**
     * 根据文件路径判断MediaType
     */
    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * 统一为请求添加头信息
     */
    private Request.Builder addHeaders() {
        Map<String, String> headersParams = BaseBiz.getHeaders();
        Headers.Builder headersBuilder = new Headers.Builder();
        for (Map.Entry<String, String> entry : headersParams.entrySet()) {
            headersBuilder.add(entry.getKey(), entry.getValue());
        }
        //        headersBuilder.add("Connection","close");
        return new Request.Builder().headers(headersBuilder.build());
        //        Request.Builder requestBuilder = new Request.Builder();
        //        for (Map.Entry<String, String> entry : headersParams.entrySet()) {
        //            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        //        }
        //        return requestBuilder;
    }

    /* TODO *************************************************** 异步 **************************************************/

    /**
     * 像指定地址发送get请求提交数据.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/4/17 13:57
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/4/17 13:57
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         数据提交路径.
     * @param params
     *         发送请求参数,key为属性名,value为属性值.
     */
    public void sendGet(String url, Map<String, String> params, final HttpRequestCallBack callBack) {
        // 添加请求参数
        Uri.Builder builderUri = Uri.parse(url).buildUpon();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builderUri.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        Uri uri = builderUri.build();
        final String urlStr = uri.toString();

        // 创建request
        Request request = addHeaders().tag(urlStr).url(urlStr).build();
        //        Request.Builder builder = new Request.Builder();
        //        builder.tag("");                          // 设置tag 可用于取消线程
        //        builder.url(uri.toString());
        //        // builder.method("GET", null);           //可以省略，默认是GET请求
        //        Request request = builder.build();
        Call call = getClient().newCall(request);           //请求加入队列
        call.enqueue(                                       //异步的
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // LogUtil.i(urlStr + ".." + e.getMessage());
                        failCallBack(e, callBack);
                        // call.cancel();//取消线程
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        LogUtil.i(response.toString());
                        // 请求成功
                        successCallBack(response.body(), callBack);
                        // call.cancel();//取消线程
                    }
                });
    }

    /**
     * 像指定地址发送post请求提交数据.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/4/17 13:57
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/4/17 13:57
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         数据提交路径.
     * @param params
     *         发送请求参数,key为属性名,value为属性值.
     */
    public void sendPost(final String url, Map<String, String> params, final HttpRequestCallBack callBack) {
        // 如果是3.0之前版本的，构建表单数据 FormEncodingBuilder; 3.0之后版本FormBody.Builder
        //        FormBody.Builder formBody = new FormBody.Builder();
        //        for (Map.Entry<String, String> entry : params.entrySet()) {
        //            formBody.add(entry.getKey(), entry.getValue());
        //        }
        //        FormBody body = formBody.build();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), JsonUtil.map2JsonObjectStr((HashMap<String, String>) params)); // JSON为MediaType.parse(“application/json; charset=utf-8”)//post请求，json为参数
        //        RequestBody body = RequestBody.create(JSON, json); // JSON为MediaType.parse(“application/json; charset=utf-8”)//post请求，json为参数
        Request request = addHeaders().tag(url).url(url).post(body).build();
        //        Request.Builder builder = new Request.Builder();
        //        builder.tag(url);                                    // 设置tag 可用于取消线程
        //        builder.url(url);                                   // 这里的URL路径为全路径，传递时注意
        //        builder.post(body);
        //        Request request = builder.build();
        Call call = getClient().newCall(request);           //请求加入队列
        call.enqueue(                                       //异步的
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // LogUtil.i(url + ".." + e.getMessage());
                        failCallBack(e, callBack);
                        // call.cancel();//取消线程
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        LogUtil.i(response.toString());
                        // 请求成功
                        successCallBack(response.body(), callBack);
                        // call.cancel();//取消线程
                    }
                });
    }

    /**
     * 文件上传
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/4/17 13:58
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/4/17 13:58
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         服务器地址
     * @param params
     *         附带参数集合
     * @param files
     *         文件集合，支持多文件上传
     */
    public void upLoadFile(final String url, Map<String, String> params, Map<String, File> files, final HttpRequestCallBack callBack) {
        //        /* form的分割线,自己定义 */
        //        String boundary = "xx--------------------------------------------------------------xx";
        //        MultipartBody.Builder builderBody = new MultipartBody.Builder(boundary);
        MultipartBody.Builder builderBody = new MultipartBody.Builder();
        builderBody.setType(MultipartBody.FORM);            //如果数据包含文件：setType(MultipartBody.FORM);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builderBody.addFormDataPart(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, File> entry : files.entrySet()) {
            final File file = entry.getValue();
            //            RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(file.getPath())), file);
            //            builderBody.addFormDataPart(entry.getKey(), file.getName(), fileBody);
            builderBody.addFormDataPart(entry.getKey(), file.getName(), new RequestBody() {
                @Override
                public MediaType contentType() {
                    return MediaType.parse(guessMimeType(file.getPath()));
                }

                @Override
                public long contentLength() {
                    return file.length();
                }

                @Override
                public void writeTo(BufferedSink sink) {
                    try {
                        Source source = Okio.source(file);
                        //sink.writeAll(source);
                        Buffer buf = new Buffer();

                        Long contentLength = contentLength();
                        Long remaining = contentLength();
                        for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                            sink.write(buf, readCount);
                            // 剩余进度
                            remaining = remaining - readCount;
                            // 当前上传进度
                            String progress = (contentLength - remaining) * 100 / contentLength + "%";
                            LogUtil.i("上传进度..." + contentLength + "..." + remaining + "..." + progress);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        MultipartBody body = builderBody.build();

        Request request = addHeaders().tag(url).url(url).post(body).build();
        //        Request.Builder builder = new Request.Builder();
        //        builder.tag(url);                                    // 设置tag 可用于取消线程
        //        builder.url(url);                                   //这里的URL路径为全路径，传递时注意
        //        builder.post(body);
        //        Request request = builder.build();
        Call call = getClient().newCall(request);           //请求加入队列
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failCallBack(e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) {
                LogUtil.i(response.toString());
                // 请求成功
                successCallBack(response.body(), callBack);
                // call.cancel();//取消线程
            }
        });
    }

    /**
     * 下载文件存储至指定路径.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/4/17 13:59
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/4/17 13:59
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         下载路径.
     * @param downLoadCallable
     *         监听线程回调接口
     * @param callBack
     *         HttpRequestCallBack
     */
    public void downLoadFile(final String url, final OnDownLoadCallBack downLoadCallable, final HttpRequestCallBack callBack) {
        final Context context = BaseApplication.getInstance().getApplicationContext();
        final ResponseBean responseBean = new ResponseBean();

        File file = new File(ConfigFile.PATH_DOWNLOAD);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                responseBean.setStatus(ConfigServer.RESPONSE_STATUS_FAIL);
                responseBean.setInfo(context.getString(R.string.service_update_hint_download_error));
                //                callBack.onSuccess(responseBean);
                sendMsg(callBack, responseBean, REQUEST_SUCCESS);
                return;
            }
        }

        final String localPath = ConfigFile.PATH_DOWNLOAD + StringUtil.getFileName(url);
        final File localFile = new File(localPath);


        // 文件已经存在，无需下载
        if (localFile.exists()) {
            responseBean.setStatus(ConfigServer.RESPONSE_STATUS_SUCCESS);
            responseBean.setInfo(context.getString(R.string.service_update_hint_download_finish));
            //            callBack.onSuccess(responseBean);
            sendMsg(callBack, responseBean, REQUEST_SUCCESS);
            return;
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

        Request request = addHeaders().tag(url).url(url).build();
        //        Request.Builder builder = new Request.Builder();
        //        builder.tag(url);                                   // 设置tag 可用于取消线程
        //        builder.url(url);                                   //这里的URL路径为全路径，传递时注意
        //        Request request = builder.build();
        Call call = getClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failCallBack(e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.i(response.toString());
                ResponseBody body = response.body();
                // 下载文件的总大小
                long fileSize = body.contentLength();

                InputStream is;
                FileOutputStream fos;
                byte[] buf = new byte[2048];
                int read;
                // 已经下载下载文件的总大小
                long finishSize = 0;

                is = body.byteStream();
                String tempPath = localPath + ".temp";
                File tempFile = new File(tempPath);
                // 创建文件
                if (!tempFile.createNewFile()) {
                    responseBean.setStatus(ConfigServer.RESPONSE_STATUS_FAIL);
                    responseBean.setInfo(context.getString(R.string.service_update_hint_download_error));
                    //                    callBack.onFail(responseBean);
                    sendMsg(callBack, responseBean, REQUEST_FAIL);
                    return;
                }

                fos = new FileOutputStream(tempFile);

                while ((read = is.read(buf)) != -1) {
                    fos.write(buf, 0, read);
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

                fos.flush();
                is.close();
                fos.close();
                LogUtil.i("下载文件成功");
                tempFile.renameTo(localFile);
                // call.cancel();//取消线程

                responseBean.setStatus(ConfigServer.RESPONSE_STATUS_SUCCESS);
                responseBean.setInfo(context.getString(R.string.service_update_hint_download_finish));
                //                callBack.onSuccess(responseBean);
                sendMsg(callBack, responseBean, REQUEST_SUCCESS);
            }
        });
    }
    //
    //    /**
    //     * 下载图片
    //     * <p>
    //     * <br> Version: 1.0.0
    //     * <br> CreateAuthor: 叶青
    //     * <br> CreateTime: 2017/4/17 13:59
    //     * <br> UpdateAuthor: 叶青
    //     * <br> UpdateTime: 2017/4/17 13:59
    //     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
    //     *
    //     * @param image
    //     *         ImageView.
    //     * @param url
    //     *         下载路径.
    //     * @param errorResId
    //     *         下载出错显示的图片资源文件ID
    //     */
    //    public void loadImage(final ImageView image, final String url, final int errorResId) {
    //        Request request = addHeaders().tag(url).url(url).build();
    //        //        Request.Builder builder = new Request.Builder();
    //        //        builder.tag(url);                                    // 设置tag 可用于取消线程
    //        //        builder.url(url);                                   //这里的URL路径为全路径，传递时注意
    //        //        Request request = builder.build();
    //        Call call = getClient().newCall(request);           //请求加入队列
    //        // Response execute = call.execute();               //同步的
    //        call.enqueue(                                       //异步的
    //                new Callback() {
    //                    @Override
    //                    public void onFailure(Call call, IOException e) {
    //                        e.printStackTrace();
    //                        LogUtil.i(url + ".." + e.getMessage());
    //                        // call.cancel();//取消线程
    //                        onFail(e, null);
    //                    }
    //
    //                    @Override
    //                    public void onResponse(Call call, Response response) throws IOException {
    //     LogUtil.i(response.toString());
    //                        // 请求成功
    //                        ResponseBody body = response.body();
    //                        //                        String content = body.string();
    //                        //                        LogUtil.json(url, content);
    //                        //我写的这个例子是请求一个图片
    //                        //response的body是图片的byte字节
    //                        byte[] bytes = body.bytes();
    //                        //把byte字节组装成图片
    //                        final Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    //                        LogUtil.i("下载图片成功");
    //                        //回调是运行在非ui主线程，
    //                        //数据请求成功后，在主线程中更新
    //                        TestOkHttpActivity.getActivity().runOnUiThread(new Runnable() {
    //                            @Override
    //                            public void run() {
    //                                // 网络图片请求成功，更新到主线程的ImageView
    //                                if (bmp != null) {
    //                                    image.setImageBitmap(bmp);
    //                                } else {
    //                                    image.setImageResource(errorResId);
    //                                }
    //                            }
    //                        });
    //
    //                        body.close();
    //                        // call.cancel();//取消线程
    //                    }
    //                });
    //    }
    //

    /**
     * 取消线程 : 异步方式可以用该方法取消 同步方式 没用
     */
    public void cancel(String tag) {
        for (Call call : getClient().dispatcher().runningCalls()) {
            if (call.request().tag().equals(tag)) {
                call.cancel();
            }
        }

        for (Call call : getClient().dispatcher().queuedCalls()) {
            if (call.request().tag().equals(tag)) {
                call.cancel();
            }
        }
    }

    /*   *************************************************** 异步数据处理 **************************************************/

    /**
     * 统一处理成功信息
     *
     * @param body
     *         ResponseBody
     * @param callBack
     *         HttpRequestCallBack
     */
    private void successCallBack(final ResponseBody body, final HttpRequestCallBack callBack) {
        //        okHttpHandler.post(new Runnable() {
        //            @Override
        //            public void run() {
        ResponseBean responseBean;
        try {
            String result = body.string();
            LogUtil.json("okHttp异步数据获取 getSuccessMsg", result);
            responseBean = BaseBiz.getResponseBean(result);
        } catch (Exception e) {
            responseBean = BaseBiz.getErrorMsg(e);
            //            callBack.onFail(responseBean);
            sendMsg(callBack, responseBean, REQUEST_FAIL);
            return;
        }

        body.close();

        if (callBack != null) {
            if (BaseBiz.checkSuccess(responseBean)) {
                if (callBack.isArray()) {
                    BaseBean.setResponseObjectList(responseBean, callBack.getCls(), callBack.getListKeyName());
                    // responseBean.setObject(GsonUtil.getInstance().jsonToList(String.valueOf(responseBean.getObject()),callBack.getCls(),callBack.getListKeyName()));
                } else {
                    BaseBean.setResponseObject(responseBean, callBack.getCls());
                    // responseBean.setObject(GsonUtil.getInstance().json2Bean(String.valueOf(responseBean.getObject()), callBack.getCls()));
                }

                //                callBack.onSuccess(responseBean);
                sendMsg(callBack, responseBean, REQUEST_SUCCESS);
            } else {
                if (responseBean.getStatus().equals(ConfigServer.RESPONSE_STATUS_TOKEN_ERROR)) {// TOKEN失效
                    Context context = BaseApplication.getInstance().getApplicationContext();
                    Intent intent = new Intent();
                    intent.setAction(BroadcastFilters.ACTION_TONKEN_EXPIRED);
                    context.sendBroadcast(intent);
                } else {
                    //                    callBack.onFail(responseBean);
                    sendMsg(callBack, responseBean, REQUEST_FAIL);
                }
            }
        }
        //            }
        //        });
    }

    /**
     * 统一处理失败信息
     *
     * @param e
     *         Throwable
     * @param callBack
     *         HttpRequestCallBack
     */
    private void failCallBack(final Throwable e, final HttpRequestCallBack callBack) {
        LogUtil.e(e);
        //        okHttpHandler.post(new Runnable() {
        //            @Override
        //            public void run() {
        ResponseBean responseBean = getErrorMsg(e);
        if (callBack != null) {
            //            callBack.onFail(responseBean);
            sendMsg(callBack, responseBean, REQUEST_FAIL);
        }
        //            }
        //        });
    }

    private void sendMsg(HttpRequestCallBack callBack, ResponseBean responseBean, int successOrFail) {
        Message message = mHandler.obtainMessage();
        message.what = successOrFail;
        message.obj = callBack;
        Bundle bundle = new Bundle();
        bundle.putSerializable(ConstantKey.INTENT_KEY_DATA, responseBean);
        message.setData(bundle);
        message.sendToTarget();
        // mHandler.sendMessage(message);
    }

    /** 请求成功 */
    private static final int REQUEST_SUCCESS = 100;
    /** 请求失败 */
    private static final int REQUEST_FAIL = 110;

    /**
     * 异步处理Handler对象
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(BaseApplication.getInstance().getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            LogUtil.e("handleMessage    " + msg.getWhen());
            HttpRequestCallBack callBack = (HttpRequestCallBack) msg.obj;
            Bundle bundle = msg.getData();
            ResponseBean responseBean = (ResponseBean) bundle.getSerializable(ConstantKey.INTENT_KEY_DATA);
            switch (msg.what) {
                case REQUEST_SUCCESS: // 网络请求数据成功
                    callBack.onSuccess(responseBean);
                    break;
                case REQUEST_FAIL: // 网络请求数据失败
                    callBack.onFail(responseBean);
                    break;
            }
        }

    };
}