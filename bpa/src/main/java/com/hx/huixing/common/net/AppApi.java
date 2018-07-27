package com.hx.huixing.common.net;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * retrofit请求
 */

public interface AppApi {
    @FormUrlEncoded
    @POST()
    @Headers("Content-Type: application/json;charset=UTF-8")
    Observable<Response<ResponseBody>> postRequest(@Url String url, @FieldMap Map<String, String> map);

    @GET()
    Observable<Response<ResponseBody>> getRequest(@Url String url, @QueryMap Map<String, String> map);

    /**
     * 发文章专用的post
     * @param url
     * @param body
     * @return
     */
    @POST()
    @Headers("Content-Type: application/json;charset=UTF-8")
    Observable<Response<ResponseBody>> postRequestArticle(@Url String url,@Body RequestBody body);

    /**
     * 这个项目里的post请求都要用json格式上传
     * @param url
     * @param body
     * @return
     */
    @POST()
    Observable<Response<ResponseBody>> postRequestForJson(@Url String url, @Body RequestBody body);

}
