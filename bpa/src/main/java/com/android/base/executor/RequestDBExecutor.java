package com.android.base.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 数据库操作队列
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class RequestDBExecutor {

    /** 数据库操作线程池队列，同时只允许一个线程操作数据库 */
    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    /**
     * 往线程池添加线程
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-10-25,下午4:51:24
     * <br> UpdateTime: 2016-10-25,下午4:51:24
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param task
     *         线程
     */
    public static void addTask(Runnable task) {
        executorService.submit(task);
    }

    /**
     * 往线程池添加网络请求事务
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月23日,上午11:56:50
     * <br> UpdateTime: 2016年4月23日,上午11:56:50
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param task
     *         纯请求事务操作
     */
    public static void addTask(BaseTask task) {
        executorService.submit(task);
    }

    /**
     * 关闭线程池
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-10-25,下午4:58:51
     * <br> UpdateTime: 2016-10-25,下午4:58:51
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public static void shutdown() {
        executorService.shutdown();
    }

}