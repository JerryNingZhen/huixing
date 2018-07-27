package com.hx.huixing.common.http;


import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.SSLException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by tanjun on 2017/5/31.
 * 过滤器
 */

public abstract class FilterSubscriber<T> extends Subscriber<T> {
    public String error;

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof TimeoutException || e instanceof SocketTimeoutException) {
            error = "网络连接超时";
        } else if (e instanceof ConnectException || e instanceof SocketException || e instanceof UnknownHostException) {
            error = "当前网络不可用，请重新检查网络设置";
        } else if (e instanceof JsonSyntaxException) {
            error = "服务器异常-Json格式出问题";
        } else if (e instanceof SSLException || e instanceof HttpException) {
            error = "服务器异常";
        } else {
            error = e.getMessage();
        }
        onError(error);
    }

    @Override
    public abstract void onNext(T data);

    @Override
    public abstract void onCompleted();

    public abstract void onError(String str);
}
