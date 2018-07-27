package com.android.base.mvp.baseclass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Activity Fragment 共用的基类方法
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2018/6/14
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public interface BaseUI {
    //public interface BaseUI<V extends BaseView> {

    /**
     * MVP模式开发下，继承MvpBaseActivity 不需要重写该方法
     * 在 {@link android.app.Activity#onCreate(Bundle)}  内调用
     * 在 {@link android.app.Fragment#onCreate(Bundle)} 内调用
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/6/15 14:21
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/6/15 14:21
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    void onCreateStart();

    /**
     * 初始化ContentView
     * 在 {@link  #onCreateStart()}  内调用
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/6/15 14:21
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/6/15 14:21
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return Activity或者Fragment的ContentView
     */
    View initContentView(int layoutId);

    /**
     * 泛型:查找控件
     * <p>
     * 在 {@link  #onCreateStart()}  内调用
     * 在 {@link  #initContentView(int)}  后调用
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月22日,下午1:40:30
     * <br> UpdateTime: 2016年5月22日,下午1:40:30
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param id
     *         控件ID
     *
     * @return 控件view
     */
    <T extends View> T findViewByIds(int id);

    /**
     * 设置广播过滤器
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月22日,下午1:43:15
     * <br> UpdateTime: 2016年5月22日,下午1:43:15
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    void initIntentFilter();

    /**
     * 注册广播
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月22日,下午1:41:25
     * <br> UpdateTime: 2016年5月22日,下午1:41:25
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    void registerReceiver();

    /**
     * 广播监听回调
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月22日,下午1:40:30
     * <br> UpdateTime: 2016年5月22日,下午1:40:30
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param intent
     *         广播附带内容
     */
    void onReceive(Intent intent);

    //TODO ********************************************等待对话框

    /**
     * 显示等待对话框
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月23日,上午10:41:59
     * <br> UpdateTime: 2016年4月23日,上午10:41:59
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    void showProgress();

    /**
     * 显示等待对话框
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月23日,上午10:41:59
     * <br> UpdateTime: 2016年4月23日,上午10:41:59
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param cancelable
     *         提示框是否可以取消，默认可以取消
     */
    void showProgress(boolean cancelable);

    /**
     * 显示等待对话框
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月23日,上午10:41:59
     * <br> UpdateTime: 2016年4月23日,上午10:41:59
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param processMsg
     *         如果内容不为空，则会显示提示框，否则不显示
     * @param cancelable
     *         提示框是否可以取消，默认可以取消
     */
    void showProgress(String processMsg, boolean cancelable);

    /**
     * 关闭等待对话框
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-23,下午3:10:40
     * <br> UpdateTime: 2016-11-23,下午3:10:40
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    void dismissProgress();

    /**
     * 显示提示信息
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/6/13 14:34
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/6/13 14:34
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param content
     *         提示内容
     */
    void showToast(String content);


    //    V getMvpView();
}

