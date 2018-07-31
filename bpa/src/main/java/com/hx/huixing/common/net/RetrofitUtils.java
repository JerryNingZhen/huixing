package com.hx.huixing.common.net;


import com.hx.huixing.uitl.LogUtil;

import java.io.IOException;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * @Description:
 * @Author: Simon
 * @Created: 2017/3/27 15:59
 */

public class RetrofitUtils {

    private static RetrofitUtils mRetrofitUtils;
    private int statePos = 0;//0:弹出加载框；1:不弹出

    private RetrofitUtils() {
    }

    public static RetrofitUtils getInstance() {
        if (mRetrofitUtils == null) {
            synchronized (RetrofitUtils.class) {
                if (mRetrofitUtils == null) {
                    mRetrofitUtils = new RetrofitUtils();
                }
            }
        }
        return mRetrofitUtils;
    }

    public RetrofitUtils setStatePos(int statePos) {
        this.statePos = statePos;
        return this;
    }

    public void normalGet(String url, Map<String, String> map, final JsonCallBack callBack) {
        RetrofitManager.getInstance()
                .createReq(AppApi.class)
                .getRequest(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.error(e);
                        LogUtils.e("(请求框架)：" + e);
                    }

                    @Override
                    public void onNext(Response<ResponseBody> response) {
                        try {
                            String res = response.body().string();
                            LogUtil.json(res);
                            callBack.next(res);
                        } catch (IOException e) {
                            LogUtils.e("(请求框架)：" + e);
                            callBack.error(e);
                        }
                    }
                });
    }

    public void normalPost(final String url, Map<String, String> map, final JsonCallBack callBack) {
        if (statePos == 0) {
            callBack.startLoading();
        }
        RetrofitManager.getInstance()
                .createReq(AppApi.class)
                .postRequest(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {

                    @Override
                    public void onCompleted() {
                        callBack.closeLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.closeLoading();
                        callBack.error(e);
                        LogUtils.e("(请求框架onError)：" + e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<ResponseBody> response) {
                        callBack.closeLoading();
                        try {
                            String res = response.body().string();
                            LogUtils.i("Http===：" + DataService.BASE_URL + url);
                            LogUtils.i("Json===：" + res);
                            LogUtils.i("======================華麗的分割線=========================");
                            callBack.next(res);
                        } catch (IOException e) {
                            LogUtils.e("(请求框架onNext)：" + e);
                        }
                    }
                });
    }

    public void normalPostArticle(final String url, RequestBody jsonStr, final JsonCallBack callBack) {
        if (statePos == 0) {
            callBack.startLoading();
        }
        RetrofitManager.getInstance()
                .createReq(AppApi.class)
                .postRequestArticle(url, jsonStr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {

                    @Override
                    public void onCompleted() {
                        callBack.closeLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.closeLoading();
                        callBack.error(e);
                        LogUtils.e("(请求框架onError)：" + e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<ResponseBody> response) {
                        callBack.closeLoading();
                        try {
                            String res = response.body().string();
                            LogUtils.i("Http===：" + DataService.BASE_URL + url);
                            LogUtils.i("Json===：" + res);
                            LogUtils.i("======================華麗的分割線=========================");
                            callBack.next(res);
                        } catch (IOException e) {
                            LogUtils.e("(请求框架onNext)：" + e);
                        }
                    }
                });
    }


    public void normalPost(String url, Map<String, String> map, String state, final JsonCallBack callBack) {
        callBack.startLoading();
        new RetrofitManager(state)
                .createReq(AppApi.class)
                .postRequest(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.error(e);
                        LogUtils.e(e);
                    }

                    @Override
                    public void onNext(Response<ResponseBody> response) {
                        try {
                            String res = response.body().string();
                            callBack.next(res);
                        } catch (IOException e) {
                            LogUtils.e("请求失败=======" + e);
                            callBack.error(e);
                            callBack.closeLoading();
                        }
                    }
                });
    }
}
