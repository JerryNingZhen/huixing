//package com.android.base.utils;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.os.Build;
//import android.view.View;
//import android.view.ViewTreeObserver;
//import android.view.Window;
//import android.view.WindowManager;
//
//import java.lang.ref.WeakReference;
//
///**
// * 键盘显示隐藏监听。并返回输入法所占用的高度
// *
// * <br> Author: 叶青
// * <br> Version: 1.0.0
// * <br> Date: 2016年12月11日
// * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
// */
//@SuppressLint("NewApi")
//public class KeyboardWatcher {
//
//	private WeakReference<Activity> activityRef;
//	private WeakReference<View> rootViewRef;
//	private WeakReference<OnKeyboardToggleListener> onKeyboardToggleListenerRef;
//	private ViewTreeObserver.OnGlobalLayoutListener viewTreeObserverListener;
//
//	public KeyboardWatcher(Activity baseUI) {
//		activityRef = new WeakReference<>(baseUI);
//		initialize();
//	}
//
//	public void setListener(OnKeyboardToggleListener onKeyboardToggleListener) {
//		onKeyboardToggleListenerRef = new WeakReference<>(onKeyboardToggleListener);
//	}
//
//	/**
//	 * 关闭键盘
//	 *
//	 * <br> Version: 1.0.0
//	 * <br> CreateTime: 2016-5-6,上午11:48:11
//	 * <br> UpdateTime: 2016-5-6,上午11:48:11
//	 * <br> CreateAuthor: 叶青
//	 * <br> UpdateAuthor: 叶青
//	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//	 *
//	 */
//	public void destroy() {
//		if (rootViewRef.get() != null)
//			if (Build.VERSION.SDK_INT >= 16) {
//				rootViewRef.get().getViewTreeObserver().removeOnGlobalLayoutListener(viewTreeObserverListener);
//			} else {
//				rootViewRef.get().getViewTreeObserver().removeGlobalOnLayoutListener(viewTreeObserverListener);
//			}
//	}
//
//	/**
//	 * 初始化数据
//	 *
//	 * <br> Version: 1.0.0
//	 * <br> CreateTime: 2016-5-6,上午11:50:08
//	 * <br> UpdateTime: 2016-5-6,上午11:50:08
//	 * <br> CreateAuthor: 叶青
//	 * <br> UpdateAuthor: 叶青
//	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//	 *
//	 */
//	private void initialize() {
//		if (hasAdjustResizeInputMode()) {
//			viewTreeObserverListener = new GlobalLayoutListener();
//			rootViewRef = new WeakReference<>(activityRef.get().findViewById(Window.ID_ANDROID_CONTENT));
//			rootViewRef.get().getViewTreeObserver().addOnGlobalLayoutListener(viewTreeObserverListener);
//		} else {
//			throw new IllegalArgumentException(String.format("Activity %s should have windowSoftInputMode=\"adjustResize\""
//					+ "to make KeyboardWatcher working. You can set it in AndroidManifest.xml", activityRef.get().getClass().getSimpleName()));
//		}
//	}
//
//	/**
//	 * 调整输入模式
//	 *
//	 * <br> Version: 1.0.0
//	 * <br> CreateTime: 2016-5-6,上午11:49:41
//	 * <br> UpdateTime: 2016-5-6,上午11:49:41
//	 * <br> CreateAuthor: 叶青
//	 * <br> UpdateAuthor: 叶青
//	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//	 */
//	private boolean hasAdjustResizeInputMode() {
//		return (activityRef.get().getWindow().getAttributes().softInputMode & WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE) != 0;
//	}
//
//	/**
//	 * 获得一个视图的高度
//	 *
//	 * 当一个视图树的布局发生改变时，可以被ViewTreeObserver监听到， 这是一个注册监听视图树的观察者(observer)，在视图树的全局事件改变时得到通知。
//	 *
//	 * ViewTreeObserver不能直接实例化，而是通过getViewTreeObserver()获得。
//	 *
//	 * <br> Author: 叶青
//	 * <br> Version: 1.0.0
//	 * <br> Date: 2016年12月11日
//	 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
//	 */
//	private class GlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
//		int initialValue;
//		boolean hasSentInitialAction;
//		boolean isKeyboardShown;
//
//		@Override
//		public void onGlobalLayout() {
//			if (initialValue == 0) {
//				initialValue = rootViewRef.get().getHeight();
//			} else {
//				if (initialValue > rootViewRef.get().getHeight()) {
//					if (onKeyboardToggleListenerRef.get() != null) {
//						if (!hasSentInitialAction || !isKeyboardShown) {
//							isKeyboardShown = true;
//							onKeyboardToggleListenerRef.get().onKeyboardShown(initialValue - rootViewRef.get().getHeight());
//						}
//					}
//				} else {
//					if (!hasSentInitialAction || isKeyboardShown) {
//						isKeyboardShown = false;
//						rootViewRef.get().post(new Runnable() {
//							@Override
//							public void run() {
//								if (onKeyboardToggleListenerRef.get() != null) {
//									onKeyboardToggleListenerRef.get().onKeyboardClosed();
//								}
//							}
//						});
//					}
//				}
//				hasSentInitialAction = true;
//			}
//		}
//	}
//
//	/**
//	 * 键盘显示隐藏监听
//	 *
//	 * <br> Author: 叶青
//	 * <br> Version: 1.0.0
//	 * <br> Date: 2016年12月11日
//	 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
//	 *
//	 */
//	public interface OnKeyboardToggleListener {
//		/**
//		 * 键盘显示
//		 *
//		 * <br> Version: 1.0.0
//		 * <br> CreateTime: 2016-5-6,上午11:46:16
//		 * <br> UpdateTime: 2016-5-6,上午11:46:16
//		 * <br> CreateAuthor: 叶青
//		 * <br> UpdateAuthor: 叶青
//		 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//		 *
//		 * @param keyboardSize
//		 *            键盘高度
//		 */
//		void onKeyboardShown(int keyboardSize);
//
//		/**
//		 * 键盘关闭
//		 *
//		 * <br> Version: 1.0.0
//		 * <br> CreateTime: 2016-5-6,上午11:46:16
//		 * <br> UpdateTime: 2016-5-6,上午11:46:16
//		 * <br> CreateAuthor: 叶青
//		 * <br> UpdateAuthor: 叶青
//		 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//		 *
//		 */
//		void onKeyboardClosed();
//	}
//}