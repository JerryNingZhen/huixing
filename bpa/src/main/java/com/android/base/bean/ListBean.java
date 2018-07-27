//package com.android.base.bean;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
///**
// * 用于解析Json数据列表的bean
// *
// * <br> Author: 叶青
// * <br> Version: 1.0.0
// * <br> Date: 2016年12月31日
// * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
// */
//public class ListBean extends BaseBean {
//
//	private static final long serialVersionUID = -2244381413323684472L;
//
//	/** 需要进行json解析的数据类型 */
//	private Class<? extends BaseBean> clazz;
//
//	/** 记录总数 */
//	private int count;
//	/** 当前面 */
//	private int page;
//	/** 页大小 */
//	private int rows;
//
//	/** 列表数据 */
//	private ArrayList<? extends BaseBean> modelList;
//
//	public ListBean(String msgStr, Class<? extends BaseBean> clazz) throws JSONException {
//		this.clazz = clazz;
//		init(new JSONObject(msgStr));
//	}
//
//	@Override
//	protected void init(JSONObject jSon) throws JSONException {
//		count = jSon.optInt("count", 0);
//		page = jSon.optInt("page", 0);
//		rows = jSon.optInt("rows", 0);
//		modelList = toModelList(jSon.optString("datalist"), clazz);
//	}
//
//	public int getCount() {
//		return count;
//	}
//
//	public void setCount(int count) {
//		this.count = count;
//	}
//
//	public int getPage() {
//		return page;
//	}
//
//	public void setPage(int page) {
//		this.page = page;
//	}
//
//	public int getRows() {
//		return rows;
//	}
//
//	public void setRows(int rows) {
//		this.rows = rows;
//	}
//
//	public ArrayList<? extends BaseBean> getModelList() {
//		return modelList;
//	}
//
//	public void setModelList(ArrayList<? extends BaseBean> modelList) {
//		this.modelList = modelList;
//	}
//
//
//	@Override
//	public String toString() {
//		return super.toString();
//	}
//}