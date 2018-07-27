package com.hx.huixing.common.net;



import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @Description:
 * @Author: Simon
 * @Created: 2017/3/8 14:25
 */

public class RspCheckInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        try {
            ResponseBody rspBody = response.body();
            if (response.isSuccessful()) {
                JSONObject jsonObject = JSONObject.parseObject(rspBody.string(), JSONObject.class);
                JSONObject data = jsonObject.getJSONObject("data");
                System.out.println("data=============" + data);
            }
        } catch (JSONException e) {
            LogUtils.e("RspCheckInterceptor：" + e);
        }
        return response;
    }

}
