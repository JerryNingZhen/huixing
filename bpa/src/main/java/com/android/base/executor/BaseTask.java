package com.android.base.executor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.android.base.BaseApplication;
import com.hx.huixing.R;
import com.android.base.bean.ResponseBean;
import com.android.base.mvp.model.BaseBiz;
import com.android.base.configs.BroadcastFilters;
import com.android.base.configs.ConfigServer;
import com.android.base.utils.HandlerUtil;

/**
 * 基础事务
 * <p>
 * 基础事务可以实现单独的网络请求，带缓存的请求和带数据库缓存的请求可以继承此事务后作扩展。
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年4月23日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public abstract class BaseTask implements Runnable {

    /** 请求成功 */
    private static final int REQUEST_SUCCESS = 100;
    /** 请求失败 */
    private static final int REQUEST_FAIL = 110;

    /** 等待提示信息 */
    private String processMsg = "";
    /** 窗口是否可取消 */
    private boolean cancelable = true;
    /** 当前线程对象 */
    private Thread currentThread;
    /** 加载提示框 */
    LoadingDialogUtil loadingDialogUtil;
    /** true代表用户手动关闭对话框，则需要关闭线程 */
    boolean isBackKeyDismiss = true;

    /**
     * 无参构造函数
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月23日,上午10:38:11
     * <br> UpdateTime: 2016年4月23日,上午10:38:11
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public BaseTask() {
    }

    /**
     * 有参构造函数
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月23日,上午10:41:59
     * <br> UpdateTime: 2016年4月23日,上午10:41:59
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文
     */
    public BaseTask(Context context) {
        //		如果内容不为空，则会显示提示框，否则不显示
        this.processMsg = context.getString(R.string.process_handle_wait);
        //		提示框是否可以取消，默认可以取消
        this.cancelable = true;

        if (!TextUtils.isEmpty(this.processMsg)) {
            loadingDialogUtil = new LoadingDialogUtil(context);
            loadingDialogUtil.showDialog(processMsg, this.cancelable);

        }
        loadingDialogUtil.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                if (isBackKeyDismiss) {
                    interrupt();
                }
            }
        });
    }

    /**
     * 有参构造函数
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月23日,上午10:41:59
     * <br> UpdateTime: 2016年4月23日,上午10:41:59
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文
     * @param processMsg
     *         如果内容不为空，则会显示提示框，否则不显示
     * @param cancelable
     *         提示框是否可以取消，默认可以取消
     */
    public BaseTask(Context context, String processMsg, boolean cancelable) {

        this.processMsg = processMsg;
        this.cancelable = cancelable;

        if (!TextUtils.isEmpty(this.processMsg)) {
            loadingDialogUtil = new LoadingDialogUtil(context);
            loadingDialogUtil.showDialog(processMsg, this.cancelable);
            loadingDialogUtil.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (isBackKeyDismiss) {
                        interrupt();
                    }
                }
            });
        }
    }

    /**
     * 事务执行线程
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月23日,上午10:43:27
     * <br> UpdateTime: 2016年4月23日,上午10:43:27
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    @Override
    public void run() {
        currentThread = Thread.currentThread();
        // 读取网络数据
        ResponseBean result = sendRequest();
        if (BaseBiz.checkSuccess(result)) {
            HandlerUtil.sendMessage(mHandler, REQUEST_SUCCESS, result);
        } else {
            HandlerUtil.sendMessage(mHandler, REQUEST_FAIL, result);
        }
    }

    /**
     * 发送请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月19日,下午1:48:56
     * <br> UpdateTime: 2016年4月19日,下午1:48:56
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return 请求返回数据
     */
    public abstract ResponseBean sendRequest();

    /**
     * 请求成功回调
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月19日,下午12:52:26
     * <br> UpdateTime: 2016年4月19日,下午12:52:26
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param result
     *         请求返回数据
     */
    public abstract void onSuccess(ResponseBean result);

    /**
     * 请求失败回调
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月19日,下午12:52:26
     * <br> UpdateTime: 2016年4月19日,下午12:52:26
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param result
     *         请求返回数据
     */
    public abstract void onFail(ResponseBean result);

    /**
     * 终止线程
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月19日,下午2:13:42
     * <br> UpdateTime: 2016年4月19日,下午2:13:42
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void interrupt() {
        if (null != currentThread) {
            currentThread.interrupt();
        }
    }

    /**
     * 异步处理Handler对象
     */
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_SUCCESS: // 网络请求数据成功
                    if (loadingDialogUtil != null) {
                        isBackKeyDismiss = false;
                        loadingDialogUtil.dismissDialog();
                    }

                    onSuccess((ResponseBean) msg.obj);
                    break;
                case REQUEST_FAIL: // 网络请求数据失败
                    if (loadingDialogUtil != null) {
                        isBackKeyDismiss = false;
                        loadingDialogUtil.dismissDialog();
                    }
                    ResponseBean bean = (ResponseBean) msg.obj;
                    if (bean.getStatus().equals(ConfigServer.RESPONSE_STATUS_TOKEN_ERROR)) {
                        Intent intent = new Intent();
                        intent.setAction(BroadcastFilters.ACTION_TONKEN_EXPIRED);
                        BaseApplication.getInstance().sendBroadcast(intent);
                    } else {
                        onFail(bean);
                    }
                    break;
            }
        }

    };

}