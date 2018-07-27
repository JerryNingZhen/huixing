package com.android.base.interfaces;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.android.base.BaseApplication;
import com.bumptech.glide.Glide;

/**
 * AbsListView滚动监听
 * 
 * * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 */
public class OnScrollPauseListener implements OnScrollListener {

	/** 触碰屏幕滚动时---true暂停 */
	private boolean pauseOnScroll;
	/** 惯性滑动时是否暂停加载---true暂停 */
	private boolean pauseOnFling;
	/** 外部自定义的OnScrollListener */
	private OnScrollListener customListener;

	public OnScrollPauseListener(boolean pauseOnScroll, boolean pauseOnFling) {
		this(pauseOnScroll, pauseOnFling, null);
	}

	protected OnScrollPauseListener(boolean pauseOnScroll, boolean pauseOnFling, AbsListView.OnScrollListener customListener) {
		this.pauseOnScroll = pauseOnScroll;
		this.pauseOnFling = pauseOnFling;
		this.customListener = customListener;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

		// 回调顺序如下
		// 第1次：scrollState = SCROLL_STATE_TOUCH_SCROLL(1) 当屏幕滚动且用户使用的触碰或手指还在屏幕上时为1
		// 第2次：scrollState = SCROLL_STATE_FLING(2) 由于用户的操作，屏幕产生惯性滑动时为2
		// 第3次：scrollState = SCROLL_STATE_IDLE(0) 当屏幕停止滚动时为0；

		switch (scrollState) {
		case OnScrollListener.SCROLL_STATE_IDLE:// 当屏幕停止滚动时为0
			resume();
			break;

		case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 当屏幕滚动且用户使用的触碰或手指还在屏幕上时为1
			if (pauseOnScroll) {
				pause();
			}
			break;

		case OnScrollListener.SCROLL_STATE_FLING:// 由于用户的操作，屏幕产生惯性滑动时为2
			if (pauseOnFling) {
				pause();
			}
			break;
		}

		if (customListener != null) {
			customListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (customListener != null) {
			customListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}

	/**
	 * 恢复加载
	 * 
	 * <br> Version: 1.0.0
	 * <br> CreateTime: 2016-1-27,上午10:55:16
	 * <br> UpdateTime: 2016-1-27,上午10:55:16
	 * <br> CreateAuthor: 叶青
	 * <br> UpdateAuthor: 叶青
	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public void resume() {
		Context context = BaseApplication.getInstance().getApplicationContext();
		Glide.with(context).resumeRequests();
	}

	/**
	 * 暂停加载
	 * 
	 * <br> Version: 1.0.0
	 * <br> CreateTime: 2016-1-27,上午10:55:30
	 * <br> UpdateTime: 2016-1-27,上午10:55:30
	 * <br> CreateAuthor: 叶青
	 * <br> UpdateAuthor: 叶青
	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public void pause() {
		Context context = BaseApplication.getInstance().getApplicationContext();
		Glide.with(context).pauseRequests();
	}
}