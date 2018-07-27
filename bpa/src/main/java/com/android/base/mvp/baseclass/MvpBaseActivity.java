package com.android.base.mvp.baseclass;

import com.android.base.utils.SystemUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;

/**
 * Mvp模式基类Activity
 * <p>
 * 所有的Activity都继承自此Activity，并实现基类模板方法，本类的目的是为了规范团队开发项目时候的开发流程的命名， 基类也用来处理需要集中分发处理的事件、广播、动画等，如开发过程中有发现任何改进方案都可以一起沟通改进。
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2018/6/14
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public abstract class MvpBaseActivity<V extends MvpBaseView, P extends MvpBasePresenter<V>> extends BaseActivity {

    /** MvpBasePresenter */
    private P mvpPresenter;

    @SuppressWarnings("unchecked")
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
            /*以下调用无参的、私有构造函数*/
            //            c0 = c.getDeclaredConstructor();
            //            c0.setAccessible(true);
            //            basePresenter = (T) c0.newInstance();
            //            basePresenter.setView(baseView);
            /*以下调用带参的、私有构造函数*/
            c0 = c.getDeclaredConstructor(mvpView.getClass());
            c0.setAccessible(true);
            mvpPresenter = (P) c0.newInstance(mvpView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (mvpPresenter != null) {
            mvpPresenter.onDestroy();
            mvpPresenter = null;
            System.gc();
        }
        super.onDestroy();
    }


    @SuppressWarnings("unchecked")
    public V getMvpView() {
        return (V) mvpView;
    }

    public P getMvpPresenter() {
        return mvpPresenter;
    }
}