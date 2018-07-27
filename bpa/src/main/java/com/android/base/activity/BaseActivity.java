//package com.android.base.activity;
//
//import android.annotation.TargetApi;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.res.Configuration;
//import android.content.res.Resources;
//import android.net.ConnectivityManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.app.FragmentActivity;
//import android.text.TextUtils;
//import android.util.DisplayMetrics;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//
//import com.hx.huixing.R;
//import com.android.base.configs.BroadcastFilters;
//import com.android.base.executor.LoadingDialogUtil;
//import com.android.base.utils.AppManagerUtil;
//import com.android.base.utils.IntentUtil;
//import com.android.base.utils.KeyboardUtil;
//import com.android.base.utils.LogUtil;
//import com.android.base.utils.PermissionUtils;
//import com.android.base.utils.SystemUtil;
//import com.android.base.utils.ToastUtil;
//import com.readystatesoftware.systembartint.SystemBarTintManager;
//
//import java.util.Locale;
//
///**
// * 基类Activity
// * <p>
// * 所有的Activity都继承自此Activity，并实现基类模板方法，本类的目的是为了规范团队开发项目时候的开发流程的命名， 基类也用来处理需要集中分发处理的事件、广播、动画等，如开发过程中有发现任何改进方案都可以一起沟通改进。
// * <br> Author: 叶青
// * <br> Version: 1.0.0
// * <br> Date: 2016年3月29日
// * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
// */
//public abstract class BaseActivity extends FragmentActivity {
//
//    /** 父视图 */
//    protected View viewParent;
//    /** 广播接收器 */
//    protected BroadcastReceiver receiver;
//    /** 广播过滤器 */
//    protected IntentFilter filter;
//    /** SystemBarTintManager 用于修改状态栏的颜色 */
//    protected SystemBarTintManager tintManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // 添加Activity到堆栈
//        AppManagerUtil.getAppManager().addActivity(this);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//        }
//        switchLanguage();
//        viewParent = View.inflate(this, getContentViewId(), null);
//        setContentView(viewParent);
//        getWindow().setBackgroundDrawable(null);
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
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    private void setTranslucentStatus(boolean on) {
//        Window win = getWindow();
//        WindowManager.LayoutParams winParams = win.getAttributes();
//        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//        if (on) {
//            winParams.flags |= bits;
//        } else {
//            winParams.flags &= ~bits;
//        }
//        win.setAttributes(winParams);
//
//        // 沉浸模式 会导致输入法与edittext不能顶起
//        tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setNavigationBarTintEnabled(true);
//        // 设置状态栏背景色
//        setTintResource(R.color.transparent);
//        //  tintManager.setTintColor(Color.parseColor("#00838F"));
//    }
//
//    /**
//     * 设置状态栏背景色
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateAuthor: 叶青
//     * <br> CreateTime: 2017/1/22 9:50
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateTime: 2017/1/22 9:50
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     *
//     * @param resId
//     *         颜色资源ID
//     */
//    public void setTintResource(int resId) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if (tintManager != null) {
//                // 设置状态栏背景色
//                tintManager.setTintResource(resId);
//            }
//        }
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
//     * 在 {@link #getContentViewId()} 之后被调用
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016年4月21日,下午1:58:20
//     * <br> UpdateTime: 2016年4月21日,下午1:58:20
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     */
//    protected abstract void findViews();
//
//    /**
//     * 获取上一个界面传送过来的数据
//     * <p>
//     * 在{@link BaseActivity#init()}之前被调用
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016年4月21日,下午2:42:56
//     * <br> UpdateTime: 2016年4月21日,下午2:42:56
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     */
//    protected abstract void initGetData();
//
//    /**
//     * 初始化应用程序，设置一些初始化数据都获取数据等操作
//     * <p>
//     * 在{@link #widgetListener()}之后被调用
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016年4月21日,下午1:55:15
//     * <br> UpdateTime: 2016年4月21日,下午1:55:15
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     */
//    protected abstract void init();
//
//    /**
//     * 组件监听模块
//     * <p>
//     * 在{@link #findViews()}后被调用
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016年4月21日,下午1:56:06
//     * <br> UpdateTime: 2016年4月21日,下午1:56:06
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     */
//    protected abstract void widgetListener();
//
//    /**
//     * 设置广播过滤器
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
//        filter.addAction(BroadcastFilters.ACTION_TONKEN_EXPIRED);// TONKEN过期
//    }
//
//    /**
//     * 注册广播Activity
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
//                    BaseActivity.this.onReceive(intent);
//                }
//            }
//        };
//        registerReceiver(receiver, filter);
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
//        //   父类集中处理共同的请求
//        if (intent.getAction().equals(BroadcastFilters.ACTION_CHANGE_LANGUAGE)) {// 修改语言
//            resetView();
//        } else if (intent.getAction().equals(BroadcastFilters.ACTION_TONKEN_EXPIRED)) {// Token失效
//            if (!this.getClass().getName().equals("LoginActivity")) {
//                tokenInvalid();
//            }
//            LogUtil.i("Token失效......" + this.getClass().getName());
//        }
//    }
//
//    /**
//     * 登录过期、Token无效 请重新登录 或者被抢登
//     */
//    public void tokenInvalid() {
//        //        try {
//        //            //            JPushUtil.getInstance(this).setAlias("");
//        ToastUtil.showToast(this, getString(R.string.activity_token_error));
//        //            //            dismissLoadingDialog();
//        //
//        //            BaseApplication.setUserInfoBean(null);
//        AppManagerUtil.getAppManager().finishAllActivity();
//        //        } catch (Exception e) {
//        //            e.printStackTrace();
//        //        }
//        //        IntentUtil.gotoActivityToTopAndFinish(this,  Class.forName(mActivityName));
//        //        IntentUtil.gotoActivityToTopAndFinish(this, LoginActivity.class);
//    }
//
//    /**
//     * 重置视图
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime:: 2016年9月14日,下午4:10:05
//     * <br> UpdateTime: 2016年9月14日,下午4:10:05
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容, 若无修改可不写.)
//     */
//    private void resetView() {
//        switchLanguage();
//        viewParent = View.inflate(this, getContentViewId(), null);
//        setContentView(viewParent);
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
//    public void switchLanguage() {
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
//            viewParent = View.inflate(this, getContentViewId(), null);
//        }
//        return (T) viewParent.findViewById(id);
//    }
//
//
//    // ***********************************返回键事件处理*********************************
//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
//                // 要执行的事件
//                finishActivity();
//            }
//            return true;
//        }
//        return super.dispatchKeyEvent(event);
//    }
//
//    /**
//     * 结束当前
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016年5月22日,下午1:40:30
//     * <br> UpdateTime: 2016年5月22日,下午1:40:30
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     */
//    protected void finishActivity() {
//        IntentUtil.finish(this);
//    }
//
//    @Override
//    protected void onStop() {
//        ToastUtil.cancelToast(this);
//        super.onStop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        unregisterReceiver(receiver);
//        KeyboardUtil.fixFocusedViewLeak(viewParent);
//        AppManagerUtil.getAppManager().removeActivity(this);
//        super.onDestroy();
//    }
//
//    /**
//     * 回调获取授权结果，判断是否授权
//     * 0 =PackageManager.PERMISSION_GRANTED 权限开启成功
//     * -1=PackageManager.PERMISSION_DENIED  权限开启失败
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (permissions.length <= 0 || grantResults.length <= 0) {
//            return;
//        }
//        PermissionUtils.getInstance().requestPermissionsResult(permissions, grantResults, requestCode);
//    }
//
//    //TODO **********************************************************************************等待对话框
//    /** 等待对话框 */
//    private LoadingDialogUtil loadingDialogUtil;
//
//    public void showProgress() {
//        showProgress(true);
//    }
//
//    public void showProgress(boolean cancelable) {
//        showProgress(getString(R.string.process_handle_wait), cancelable);
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
//
//        if (isFinishing()) {
//            return;
//        }
//
//        if (loadingDialogUtil == null) {
//            loadingDialogUtil = new LoadingDialogUtil(this);
//        }
//
//        if (!TextUtils.isEmpty(processMsg)) {
//            loadingDialogUtil.showDialog(processMsg, cancelable);
//        }
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
//        if (isFinishing()) {
//            return;
//        }
//
//        if (loadingDialogUtil != null) {
//            loadingDialogUtil.dismissDialog();
//        }
//    }
//
//    /**
//     * 显示提示信息
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateAuthor: 叶青
//     * <br> CreateTime: 2018/6/13 14:34
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateTime: 2018/6/13 14:34
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     *
//     * @param content
//     *         提示内容
//     */
//    public void showToast(String content) {
//        if (isFinishing()) {
//            return;
//        }
//        ToastUtil.showToast(this, content);
//    }
//
//}