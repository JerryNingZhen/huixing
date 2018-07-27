package com.android.base.utils.dialog.share;

import com.android.base.bean.BaseBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 分享 所需字段
 * 
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ShareBean extends BaseBean {

	/** 标题 */
	private String title;
	/** 内容 */
	private String textContent;
	/** 图片本地路径 全路径 */
	private String photoPath;
	/** 内容链接 全路径 */
	private String contentUrl;
	/** 网络图片链接 全路径 */
	private String photoUrl;

	/** 分享内容id---内部分享专用 */
	private String contentId;
	/** 分享内容type-- 内部分享专用 */
	private String contentType;

	@Override
	protected void init(JSONObject jSon) throws JSONException {

		title = (jSon.optString("title", ""));
		textContent = (jSon.optString("text_content", ""));
		photoPath = (jSon.optString("photo_path", ""));
		contentUrl = (jSon.optString("content_url", ""));
		contentId = (jSon.optString("content_id", ""));
		contentType = (jSon.optString("content_type", ""));
		photoUrl = (jSon.optString("photo_url", ""));
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
}