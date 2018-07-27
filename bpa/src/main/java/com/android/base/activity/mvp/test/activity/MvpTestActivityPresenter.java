package com.android.base.activity.mvp.test.activity;

import com.android.base.mvp.baseclass.MvpBasePresenter;
import com.android.base.activity.mvp.test.MvpTestMode;
import com.android.base.bean.ResponseBean;
import com.android.base.bean.UpdateBean;
import com.android.base.configs.ConfigServer;
import com.android.base.executor.BaseTask;
import com.android.base.executor.RequestExecutor;
import com.android.base.mvp.model.HttpOkBiz;
import com.android.base.utils.DateUtil;
import com.android.base.utils.LogUtil;
import com.android.base.utils.code.MD5Coder;
import com.android.base.utils.http.HttpRequestCallBack;

import java.util.HashMap;

/**
 * <p>
 * <br> Author: 叶青
 * <br> Version: 4.0.0
 * <br> Date: 2018/6/14
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class MvpTestActivityPresenter extends MvpBasePresenter<MvpTestActivityView> {

    private MvpTestMode model;

    public MvpTestActivityPresenter(MvpTestActivityView view) {
        super(view);
        model = new MvpTestMode();
    }

    //    @Override
    public void loadData() {
        if (view == null) {
            LogUtil.e(getClass() + " 页面已经销毁，不在进行任何操作");
            return;
        }

        String str = view.getTest();
        view.showProgress();
        HashMap<String, String> map = new HashMap<>();
        map.put("address", "湖北省宜昌市西陵一路亚洲广场B座2507号");
        map.put("output", "json");
        map.put("ak", "CidLuM0yn9FVWbw5GqLqpNg5rFlZDloO");
        map.put("str", str);
        model.loadData(map, new HttpRequestCallBack() {
            @Override
            public void onSuccess(ResponseBean result) {
                if (view == null) {
                    LogUtil.e(getClass() + " 页面已经销毁，不在进行任何操作");
                    return;
                }
                view.dismissProgress();
                view.setViewData(result.getObject());
                view.showToast(result.getInfo());
            }

            @Override
            public void onFail(ResponseBean result) {
                if (view == null) {
                    LogUtil.e(getClass() + " 页面已经销毁，不在进行任何操作");
                    return;
                }
                view.dismissProgress();
                view.setViewData(result.getInfo());
                view.showToast(result.getInfo());
            }
        });

    }
    public void loadDataPost() {
        if (view == null) {
            LogUtil.e(getClass() + " 页面已经销毁，不在进行任何操作");
            return;
        }

        String str = view.getTest();
        view.showProgress();
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, "version/check");
        String postTime = DateUtil.getDate("yyyyMMddHHmmss");
        String sign = MD5Coder.md5Encrypt(postTime + "wFaj81aXawkj8XNOF3GFCUn2q903zN8F").toLowerCase();
        params.put("posttime", postTime);
        params.put("sign", sign);
        params.put("type", "1");
        params.put("verCode", "1");
        model.loadDataPost(params, new HttpRequestCallBack(UpdateBean.class) {
            @Override
            public void onSuccess(ResponseBean result) {
                if (view == null) {
                    LogUtil.e(getClass() + " 页面已经销毁，不在进行任何操作");
                    return;
                }
                view.dismissProgress();
                view.setViewData(result.getObject());
                view.showToast(result.getInfo());
            }

            @Override
            public void onFail(ResponseBean result) {
                if (view == null) {
                    LogUtil.e(getClass() + " 页面已经销毁，不在进行任何操作");
                    return;
                }
                view.dismissProgress();
                view.setViewData(result.getInfo());
                view.showToast(result.getInfo());
            }
        });

    }

    public void loadData1() {
        LogUtil.e(getClass() + " loadData1");
        final String str = view.getTest();
        view.showProgress();

        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                String string = "http://api.map.baidu.com/geocoder/v2/";
                HashMap<String, String> map = new HashMap<>();
                map.put("address", "湖北省宜昌市西陵一路亚洲广场B座2507号");
                map.put("output", "json");
                map.put("ak", "CidLuM0yn9FVWbw5GqLqpNg5rFlZDloO");
                map.put("str", str);
                ResponseBean bean = HttpOkBiz.getInstance().sendGet(string, map);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return bean;
            }

            @Override
            public void onSuccess(ResponseBean result) {
                if (view == null) {
                    LogUtil.e(getClass() + " 页面已经销毁，不在进行任何操作");
                    return;
                }
                view.dismissProgress();
                view.setViewData(result.getObject());
                view.showToast(result.getInfo());
            }

            @Override
            public void onFail(ResponseBean result) {
                if (view == null) {
                    LogUtil.e(getClass() + " 页面已经销毁，不在进行任何操作");
                    return;
                }
                view.dismissProgress();
                view.setViewData(result.getInfo());
                view.showToast(result.getInfo());
            }
        });
    }
}
