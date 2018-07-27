//package com.android.base.fragment;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.res.Configuration;
//import android.content.res.Resources;
//import android.net.ConnectivityManager;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.text.TextUtils;
//import android.util.DisplayMetrics;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.android.base.activity.BaseActivity;
//import com.android.base.configs.BroadcastFilters;
//import com.android.base.utils.SystemUtil;
//
//import java.util.Locale;
//
///**
// * 基类Fragment
// * <p>
// * 所有的Fragment必须继承自此类
// * <br> Author: 叶青
// * <br> Version: 1.0.0
// * <br> Date: 2016年3月29日
// * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
// */
//public abstract class BaseFragment extends Fragment {
//
//    /** 父视图 */
//    protected View viewParent;
//    /** 广播接收器 */
//    protected BroadcastReceiver receiver;
//    /** 广播过滤器 */
//    protected IntentFilter filter;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        switchLanguage();
//        viewParent = View.inflate(getActivity(), getContentViewId(), null);
//        findViews();
//        try {
//            initGetData();
//            widgetListener();
//            init();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        registerReceiver();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if (viewParent.getParent() != null) {
//            ((ViewGroup) viewParent.getParent()).removeView(viewParent);
//        }
//        return viewParent;
//    }
//
//    /**
//     * 获取显示view的xml文件ID
//     * <p>
//     * 在Activity的 {@link #onCreate(Bundle)}里边被调用
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016年4月21日,下午2:31:19
//     * <br> UpdateTime: 2016年4月21日,下午2:31:19
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     *
//     * @return xml文件ID
//     */
//    protected abstract int getContentViewId();
//
//    /**
//     * 控件查找
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016年5月22日,上午10:03:58
//     * <br> UpdateTime: 2016年5月22日,上午10:03:58
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     */
//    protected abstract void findViews();
//
//    /**
//     * 获取初始数据
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016年6月7日,上午11:13:55
//     * <br> UpdateTime: 2016年6月7日,上午11:13:55
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     */
//    public abstract void initGetData();
//
//    /**
//     * 初始化
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016年5月22日,上午10:05:06
//     * <br> UpdateTime: 2016年5月22日,上午10:05:06
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     */
//    protected abstract void init();
//
//    /**
//     * 组件监听
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016年5月22日,上午10:05:39
//     * <br> UpdateTime: 2016年5月22日,上午10:05:39
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     */
//    protected abstract void widgetListener();
//
//    /**
//     * 重置视图
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime:: 2016年9月14日,下午4:10:05
//     * <br> UpdateTime: 2016年9月14日,下午4:10:05
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     */
//    private void resetView() {
//        switchLanguage();
//        // getViews();
//        viewParent = View.inflate(getActivity(), getContentViewId(), null);
//        findViews();
//        try {
//            initGetData();
//            widgetListener();
//            init();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        registerReceiver();
//    }
//
//    /**
//     * 切换语言
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016年9月24日,下午2:52:16
//     * <br> UpdateTime: 2016年9月24日,下午2:52:16
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     */
//    private void switchLanguage() {
//        String language = SystemUtil.getAppLanguage();
//        // 获得res资源对象
//        Resources resources = getResources();
//        // 获得设置对象
//        Configuration config = resources.getConfiguration();
//
//        if (language.startsWith("zh")) {
//            config.locale = Locale.SIMPLIFIED_CHINESE;
//        } else if (language.startsWith("en")) {
//            config.locale = Locale.ENGLISH;
//        } else {
//            config.locale = new Locale(language);
//        }
//        // 应用内配置语言
//        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
//        resources.updateConfiguration(config, dm);
//    }
//
//    /**
//     * 是否已经创建
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016年6月6日,上午11:16:54
//     * <br> UpdateTime: 2016年6月6日,上午11:16:54
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     *
//     * @return true已经创建，false未创建
//     */
//    public boolean isCreated() {
//        return viewParent != null;
//    }
//
//    /**
//     * 设置广播过滤器，在此添加广播过滤器之后，所有的子类都将收到该广播
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016年5月22日,下午1:43:15
//     * <br> UpdateTime: 2016年5月22日,下午1:43:15
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     */
//    protected void setFilterActions() {
//        //   添加广播过滤器，在此添加广播过滤器之后，所有的子类都将收到该广播
//        filter = new IntentFilter();
//        filter.addAction(BroadcastFilters.ACTION_CHANGE_LANGUAGE);
//        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION); // 网络变化广播
//        filter.addAction(BroadcastFilters.ACTION_APP_EXIT);// 程序退出
//        filter.addAction(BroadcastFilters.ACTION_LOCATION_COMPLETE);// 定位完成
//    }
//
//    /**
//     * 注册广播
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016年5月22日,下午1:41:25
//     * <br> UpdateTime: 2016年5月22日,下午1:41:25
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     */
//    protected void registerReceiver() {
//        setFilterActions();
//        receiver = new BroadcastReceiver() {
//
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                if (intent != null && !TextUtils.isEmpty(intent.getAction())) {
//                    BaseFragment.this.onReceive(intent);
//                }
//            }
//        };
//        getActivity().registerReceiver(receiver, filter);
//
//    }
//
//    /**
//     * 广播监听回调
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016年5月22日,下午1:40:30
//     * <br> UpdateTime: 2016年5月22日,下午1:40:30
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     *
//     * @param intent
//     *         广播附带内容
//     */
//    protected void onReceive(Intent intent) {
//        //  接受到广播之后做的处理操作
//        if (intent.getAction().equals(BroadcastFilters.ACTION_CHANGE_LANGUAGE)) {// 修改语言
//            resetView();
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        getActivity().unregisterReceiver(receiver);
//        super.onDestroy();
//    }
//
//    /**
//     * 泛型:查找控件
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016年5月22日,下午1:40:30
//     * <br> UpdateTime: 2016年5月22日,下午1:40:30
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     *
//     * @param id
//     *         控件ID
//     *
//     * @return 控件view
//     */
//    @SuppressWarnings("unchecked")
//    protected <T extends View> T findViewByIds(int id) {
//        if (viewParent == null) {
//            viewParent = View.inflate(getActivity(), getContentViewId(), null);
//        }
//        return (T) viewParent.findViewById(id);
//    }
//
//    //TODO ********************************************等待对话框
//    public void showProgress() {
//        ((BaseActivity) getActivity()).showProgress();
//    }
//
//    public void showProgress(boolean cancelable) {
//        ((BaseActivity) getActivity()).showProgress(cancelable);
//    }
//
//    /**
//     * 显示等待对话框
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016年4月23日,上午10:41:59
//     * <br> UpdateTime: 2016年4月23日,上午10:41:59
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     *
//     * @param processMsg
//     *         如果内容不为空，则会显示提示框，否则不显示
//     * @param cancelable
//     *         提示框是否可以取消，默认可以取消
//     */
//    public void showProgress(String processMsg, boolean cancelable) {
//        ((BaseActivity) getActivity()).showProgress(processMsg, cancelable);
//    }
//
//    /**
//     * 关闭等待对话框
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016-11-23,下午3:10:40
//     * <br> UpdateTime: 2016-11-23,下午3:10:40
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     */
//    public void dismissProgress() {
//        ((BaseActivity) getActivity()).dismissProgress();
//    }
//}