package com.android.base.interfaces;

/**
 * 监听线程回调接口
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public interface OnDownLoadCallBack {

    /**
     * 任务正在执行的时候调用
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-5,下午4:01:37
     * <br> UpdateTime: 2016-1-5,下午4:01:37
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param press
     *         0-100
     * @param fileSize
     *         下载文件的总大小
     * @param finishSize
     *         已经下载下载文件的总大小
     */
    void ResultProgress(int press, long fileSize, long finishSize);
}