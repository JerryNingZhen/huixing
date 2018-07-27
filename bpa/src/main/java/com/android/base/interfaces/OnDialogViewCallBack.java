package com.android.base.interfaces;

import java.util.Map;

/**
 * 弹出框视图回调接口
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2017/9/2
 */
public interface OnDialogViewCallBack {

	/**
	 * 结束查询
	 */
	public void onResult(Map<String, String> map);
	
}
