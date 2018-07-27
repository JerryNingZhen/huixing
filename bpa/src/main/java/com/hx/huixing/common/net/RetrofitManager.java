package com.hx.huixing.common.net;


import android.text.TextUtils;

import com.google.gson.Gson;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Description:
 * @Author: Simon
 * @Created: 2017/3/8 14:14
 */

public class RetrofitManager {
    private String url;
    private long outTime;

    public String getUrl() {
        return url;
    }

    public RetrofitManager setUrl(String url) {
        this.url = url;
        return this;
    }

    private static RetrofitManager mRetrofitManager;
    private Retrofit mRetrofit;

    private RetrofitManager() {
        initRetrofit("");
    }

    public RetrofitManager(String state) {
        initRetrofit(state);
    }

    public static RetrofitManager getInstance() {
        if (mRetrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (mRetrofitManager == null) {
                    mRetrofitManager = new RetrofitManager();
                }
            }
        }
        return mRetrofitManager;
    }

    private void initRetrofit(String state) {
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //okhttp使用https忽略证书验证
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());
            builder.sslSocketFactory(sslContext.getSocketFactory());
            builder.addInterceptor(new CustomInterceptor());
            if (TextUtils.isEmpty(state)) {
                outTime = 30;
            } else {
                outTime = 5;
            }
            builder.connectTimeout(outTime, TimeUnit.SECONDS);
            builder.readTimeout(outTime, TimeUnit.SECONDS);
            builder.writeTimeout(outTime, TimeUnit.SECONDS);
            builder.retryOnConnectionFailure(true);

            OkHttpClient client = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(DataService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();

        } catch (NoSuchAlgorithmException e) {
            LogUtils.e(e);
        } catch (KeyManagementException e) {
            LogUtils.e(e);
        }
    }

    /**
     *  okhttp使用https忽略证书验证
     */
    X509TrustManager xtm = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };

    public <T> T createReq(Class<T> reqServer) {
        return mRetrofit.create(reqServer);
    }
}
