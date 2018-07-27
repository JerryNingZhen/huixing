package com.android.base.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hx.huixing.R;


/**
 * 自定义Toast
 * 
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class CustomToast extends Toast {

	/** 设置Toast内容 */
	private TextView tipTxt;

	public CustomToast(Context context, int gravity, int xOffset, int yOffset, int duration) {
		super(context);
		setGravity(gravity, xOffset, yOffset);
		setDuration(Toast.LENGTH_SHORT);
		View view = View.inflate(context, R.layout.view_custom_toast, null);
		tipTxt = (TextView) view.findViewById(R.id.tv_common_custom_toast);
		setView(view);
	}

	/**
	 * 设置Toast内容
	 * 
	 * <br> Version: 1.0.0
	 * <br> CreateTime: 2016-11-24,下午3:43:40
	 * <br> UpdateTime: 2016-11-24,下午3:43:40
	 * <br> CreateAuthor: 叶青
	 * <br> UpdateAuthor: 叶青
	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param text
	 */
	public void setShowMsg(CharSequence text) {
		tipTxt.setText(text);
	}
}