package com.android.base.utils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * fragment 的添加和替换
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class FragmentUtil {

    /**
     * 得到fragment对象
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-5,上午11:20:39
     * <br> UpdateTime: 2016-1-5,上午11:20:39
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param fragmentManager
     *         FragmentManager对象
     * @param cla
     */
    public static Fragment getFragment(FragmentManager fragmentManager, Class<?> cla) {
        Fragment fragment = fragmentManager.findFragmentByTag(cla.getName());

        try {
            if (fragment == null)
                fragment = (Fragment) cla.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragment;
    }

    /**
     * 添加fragment
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-5,上午11:21:39
     * <br> UpdateTime: 2016-1-5,上午11:21:39
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param containerViewId
     *         Identifier of the container whose fragment(s) are to be replaced.
     * @param fragmentManager
     *         FragmentManager对象
     * @param cla
     * @param isBackstack
     *         是否添加到返回栈中 true-添加
     */
    public static void gotoAddFragment(int containerViewId, FragmentManager fragmentManager, Class<?> cla, boolean isBackstack) {
        gotoFragment(containerViewId, fragmentManager, getFragment(fragmentManager, cla), isBackstack, cla.getName());
    }

    /**
     * 替换当前fragment
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-5,上午11:21:33
     * <br> UpdateTime: 2016-1-5,上午11:21:33
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param containerViewId
     *         Identifier of the container whose fragment(s) are to be replaced.
     * @param fragmentManager
     *         FragmentManager对象
     * @param cla
     * @param isBackstack
     *         是否添加到返回栈中 true-添加
     */
    public static void gotoReplaceFragment(int containerViewId, FragmentManager fragmentManager, Class<?> cla, boolean isBackstack) {
        gotoReplaceFragment(containerViewId, fragmentManager, getFragment(fragmentManager, cla), isBackstack, cla.getName());
    }

    /**
     * 添加fragment 并带参数
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-5,上午11:21:28
     * <br> UpdateTime: 2016-1-5,上午11:21:28
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param containerViewId
     *         Identifier of the container whose fragment(s) are to be replaced.
     * @param fragmentManager
     *         FragmentManager对象
     * @param cla
     * @param isBackstack
     *         是否添加到返回栈中 true-添加
     * @param bundle
     */
    public static void gotoAddFragmentAndParam(int containerViewId, FragmentManager fragmentManager, Class<?> cla, boolean isBackstack, Bundle bundle) {
        Fragment fragment = getFragment(fragmentManager, cla);
        fragment.setArguments(bundle);
        gotoFragment(containerViewId, fragmentManager, fragment, isBackstack, cla.getName());
    }

    /**
     * 替换当前fragment
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-5,上午11:21:22
     * <br> UpdateTime: 2016-1-5,上午11:21:22
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param containerViewId
     *         Identifier of the container whose fragment(s) are to be replaced.
     * @param fragmentManager
     *         FragmentManager对象
     * @param cla
     * @param isBackstack
     *         是否添加到返回栈中 true-添加
     * @param bundle
     */
    public static void gotoReplaceFragmentAndParam(int containerViewId, FragmentManager fragmentManager, Class<?> cla, boolean isBackstack, Bundle bundle) {
        Fragment fragment = getFragment(fragmentManager, cla);
        fragment.setArguments(bundle);
        gotoReplaceFragment(containerViewId, fragmentManager, fragment, isBackstack, cla.getName());
    }

    /**
     * 添加fragment
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-5,上午11:21:16
     * <br> UpdateTime: 2016-1-5,上午11:21:16
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param containerViewId
     *         Identifier of the container whose fragment(s) are to be replaced.
     * @param fragmentManager
     *         FragmentManager对象
     * @param fragment
     *         Fragment对象
     * @param isBackStack
     *         是否添加到返回栈中 true-添加
     * @param tag
     *         Optional tag name for the fragment, to later retrieve the fragment with FragmentManager.findFragmentByTag(String).
     */
    public static void gotoFragment(int containerViewId, FragmentManager fragmentManager, Fragment fragment, boolean isBackStack, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(containerViewId, fragment, tag);
        if (isBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commitAllowingStateLoss();

    }

    /**
     * 替换当前fragment
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-5,上午11:21:09
     * <br> UpdateTime: 2016-1-5,上午11:21:09
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param containerViewId
     *         Identifier of the container whose fragment(s) are to be replaced.
     * @param fragmentManager
     *         FragmentManager对象
     * @param fragment
     *         Fragment对象
     * @param isBackStack
     *         是否添加到返回栈中 true-添加
     * @param tag
     *         Optional tag name for the fragment, to later retrieve the fragment with FragmentManager.findFragmentByTag(String).
     */
    public static void gotoReplaceFragment(int containerViewId, FragmentManager fragmentManager, Fragment fragment, boolean isBackStack, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerViewId, fragment, tag);
        if (isBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commitAllowingStateLoss();
    }

}