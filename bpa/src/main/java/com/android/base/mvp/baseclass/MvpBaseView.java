package com.android.base.mvp.baseclass;

import android.view.View;

/**
 * MvpBaseView
 * <p>
 * MVP中所有的View层都必须继承自MvpBaseView
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2018/6/14
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public abstract class MvpBaseView<A extends BaseUI> implements BaseView {

    /** baseUI  只有两种 《Activity 或者 Fragment》 */
    protected A baseUI;

    //    public MvpBaseView() {
    //    }

    public MvpBaseView(A baseUI) {
        this.baseUI = baseUI;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findViewByIds(int id) {
        return (T) baseUI.findViewByIds(id);
    }

    // ************************************************************************等待对话框
    public void showProgress() {
        baseUI.showProgress();
    }

    public void showProgress(boolean cancelable) {
        baseUI.showProgress(cancelable);
    }

    public void showProgress(String processMsg, boolean cancelable) {
        baseUI.showProgress(processMsg, cancelable);
    }

    public void dismissProgress() {
        baseUI.dismissProgress();
    }

    public void showToast(String content) {
        baseUI.showToast(content);
    }
}