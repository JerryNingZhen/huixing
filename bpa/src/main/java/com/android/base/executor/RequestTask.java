package com.android.base.executor;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.base.bean.ResponseBean;
import com.android.base.mvp.model.BaseBiz;
import com.android.base.utils.HandlerUtil;
import com.android.base.utils.PreferencesUtil;

/**
 * 网络请求带(SP文件、数据库)缓存
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年4月19日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public abstract class RequestTask extends BaseTask {

    /** 请求缓存成功 */
    private static final int REQUEST_CACHE = 120;

    public RequestTask() {
        super();
    }

    public RequestTask(Context context) {
        super(context);
    }

    public RequestTask(Context context, String processMsg, boolean cancelable) {
        super(context, processMsg, cancelable);
    }

    @Override
    public void run() {
        // 读取缓存数据
        getCacheExecutor();
        super.run();
    }

    /**
     * 在数据库线程池里边执行数据库操作
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月23日,上午10:26:44
     * <br> UpdateTime: 2016年4月23日,上午10:26:44
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void getCacheExecutor() {
        RequestDBExecutor.addTask(new Runnable() {
            @Override
            public void run() {
                ResponseBean response = readCache();
                if (response != null && BaseBiz.checkSuccess(response)) {
                    HandlerUtil.sendMessage(mHandler, REQUEST_CACHE, response);
                }
            }
        });
    }

    /**
     * 请求缓存数据(SP文件、数据库)
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月19日,下午3:28:06
     * <br> UpdateTime: 2016年4月19日,下午3:28:06
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public abstract ResponseBean readCache();

    /**
     * 请求缓存返回应答处理
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月19日,下午3:27:40
     * <br> UpdateTime: 2016年4月19日,下午3:27:40
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param result
     *         缓存数据
     */
    public abstract void onReadCacheSuccess(ResponseBean result);

    /**
     * 异步处理Handler对象
     */
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_CACHE:// 本地读取缓存成功
                    if (loadingDialogUtil != null) {
                        isBackKeyDismiss = false;
                        loadingDialogUtil.dismissDialog();
                    }
                    ResponseBean responseBean = (ResponseBean) msg.obj;
                    if (responseBean != null) {
                        onReadCacheSuccess(responseBean);
                    }
                    break;
            }
        }

    };

    /**
     * 加载缓存文件
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月19日,下午4:05:37
     * <br> UpdateTime: 2016年4月19日,下午4:05:37
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param method
     *         缓存文件内容key
     *
     * @return 成功获取 返回解析后的数据 否则 null
     */
    public String loadCacheData(String method) {
        //        PreferencesUtil spUtil = PreferencesUtil.getSpUtil(ConstantKey.SP_KEY_FILE_REQUEST);
        //PreferencesUtil spUtil = PreferencesUtil.getSpUtil();
        return (String) PreferencesUtil.get(method,"");
    }
}