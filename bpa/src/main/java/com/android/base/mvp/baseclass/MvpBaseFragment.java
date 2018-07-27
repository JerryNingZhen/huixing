package com.android.base.mvp.baseclass;

import com.android.base.utils.SystemUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;

/**
 * Mvp模式基类Fragment
 * <p>
 * 所有的Fragment必须继承自此类
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2018/6/14
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public abstract class MvpBaseFragment<V extends MvpBaseView, P extends MvpBasePresenter<V>> extends BaseFragment {

    /** MvpBasePresenter */
    private P mvpPresenter;

    @Override
    public void initVP() {
        try {
            Type[] types = SystemUtil.getGenericSuperclass(getClass());
            if (types == null) {
                return;
            }

            Class c = (Class) types[0];
            /*以下调用带参的、私有构造函数*/
            Constructor c0 = c.getDeclaredConstructor(getClass());
            c0.setAccessible(true);
            mvpView = (V) c0.newInstance(this);

            c = (Class) types[1];
            /*以下调用带参的、私有构造函数*/
            c0 = c.getDeclaredConstructor(mvpView.getClass());
            c0.setAccessible(true);
            mvpPresenter = (P) c0.newInstance(mvpView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        if (mvpPresenter != null) {
            mvpPresenter.onDestroy();
            mvpPresenter = null;
            System.gc();
        }
        super.onDestroy();
    }

    @Override
    @SuppressWarnings("unchecked")
    public V getMvpView() {
        return (V) mvpView;
    }

    public P getMvpPresenter() {
        return mvpPresenter;
    }
}