package com.hx.huixing.common.net;



import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Description: okhttp3攔截器
 * @Author: Simon
 * @Created: 2017/5/5 17:38
 */

public class CustomInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("platform", Constants.ANDROID)//平台 ANDROID,IOS
                .addHeader("osVersion", SysUtils.getSystemNumb())//操作系统版本
                .addHeader("model", SysUtils.getSysVersionCode())//品牌
                .addHeader("appVersion", SysUtils.getVersionCode() + "")//app版本号
                .addHeader("channel", SysUtils.getChannelCode())//通道号
                .addHeader("tenantId","0000100001")
                .build();
        return chain.proceed(request);
    }
}
