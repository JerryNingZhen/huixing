package com.android.base.activity;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.base.mvp.baseclass.MvpBaseActivity;
import com.android.base.mvp.presenter.AddArticlePresenter;
import com.android.base.mvp.view.AddArticleView;

/**
 * 发帖界面
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class AddArticleActivity extends MvpBaseActivity<AddArticleView, AddArticlePresenter> {
    // ************************************************************************返回键事件处理
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                // 要执行的事件
                getMvpView().saveDraft();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getMvpView().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}