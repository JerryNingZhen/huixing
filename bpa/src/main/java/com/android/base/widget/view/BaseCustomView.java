package com.android.base.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.base.mvp.baseclass.BaseActivity;

/**
 * 基础自定义view
 * <p>
 * 用于规范自定义view的编写流程
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年9月2日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public abstract class BaseCustomView extends RelativeLayout {

    /** 上下文 */
    protected Context context;
    /** 父视图 */
    protected View viewParent;

    public BaseCustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setContentView(context);
    }

    public BaseCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setContentView(context);
    }

    public BaseCustomView(Context context) {
        super(context);
        setContentView(context);
    }

    /**
     * 初始化
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年9月2日,上午11:31:29
     * <br> UpdateTime: 2016年9月2日,上午11:31:29
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     */
    private void setContentView(Context context) {
        this.context = context;
        viewParent = LayoutInflater.from(this.context).inflate(getContentViewId(), null);
        this.addView(viewParent);
        findViews();
        widgetListener();
        init();
    }

    /**
     * 获取显示view的layout id
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年9月2日,上午11:42:37
     * <br> UpdateTime: 2016年9月2日,上午11:42:37
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    protected abstract int getContentViewId();

    /**
     * 控件查找
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年9月2日,上午11:43:51
     * <br> UpdateTime: 2016年9月2日,上午11:43:51
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    protected abstract void findViews();

    /**
     * 初始化
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年9月2日,上午11:45:08
     * <br> UpdateTime: 2016年9月2日,上午11:45:08
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    protected abstract void init();

    /**
     * 组件监听
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年9月2日,上午11:44:45
     * <br> UpdateTime: 2016年9月2日,上午11:44:45
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    protected abstract void widgetListener();


    /**
     * 泛型:查找控件
     * <p>
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
    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewByIds(int id) {
        if (viewParent == null) {
            viewParent = View.inflate(context, getContentViewId(), null);
        }
        return (T) viewParent.findViewById(id);
    }

    public void showProgress() {
        ((BaseActivity) context).showProgress();
    }

    public void showProgress(boolean cancelable) {
        ((BaseActivity) context).showProgress(cancelable);
    }

    public void showProgress(String processMsg, boolean cancelable) {
        ((BaseActivity) context).showProgress(processMsg, cancelable);
    }

    public void dismissProgress() {
        ((BaseActivity) context).dismissProgress();
    }

    public void showToast(String content) {
        ((BaseActivity) context).showToast(content);
    }
}