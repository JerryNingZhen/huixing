package com.android.base.mvp.baseclass;

import android.os.Bundle;

/**
 * BaseView
 * <p>
 * MVP中所有的View层都必须继承自BaseView
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2018/6/14
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public interface BaseView {

    /**
     * 获取显示view的xml文件ID
     * <p>
     * 在 {@link android.app.Activity#onCreate(Bundle)} 内调用
     * 在 {@link android.app.Fragment#onCreate(Bundle)} 内调用
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月21日,下午2:31:19
     * <br> UpdateTime: 2016年4月21日,下午2:31:19
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return xml文件ID
     */
    int getContentViewId();

    /**
     * 控件查找
     * <p>
     * 在 {@link #getContentViewId()} 之后被调用
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月21日,下午1:58:20
     * <br> UpdateTime: 2016年4月21日,下午1:58:20
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    void findViews();

    /**
     * 初始化应用程序，设置一些初始化数据都获取数据等操作
     * <p>
     * 如：Activity.getIntent().getExtras()或者Fragment.getArguments()
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月21日,下午1:55:15
     * <br> UpdateTime: 2016年4月21日,下午1:55:15
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param bundle
     *         Activity.getIntent().getExtras()或者Fragment.getArguments()
     */
    void init(Bundle bundle);

    /**
     * 组件监听模块
     * <p>
     * 在{@link #init(Bundle)}后被调用
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月21日,下午1:56:06
     * <br> UpdateTime: 2016年4月21日,下午1:56:06
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    void widgetListener();

    /**
     * 用于网络请求成功后，初始化View的数据
     * <p>
     * 对外只暴露一个 setViewData 方法，如果有多个网络请求、多种返回数据类型 请根据 object instanceof XXXX，做区分
     * <p>
     * 在{@link MvpBasePresenter#loadData()} 之后被调用
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月21日,下午1:55:15
     * <br> UpdateTime: 2016年4月21日,下午1:55:15
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    void setViewData(Object object);
}
