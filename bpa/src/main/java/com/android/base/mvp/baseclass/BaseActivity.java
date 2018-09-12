package com.android.base.mvp.baseclass;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.base.configs.BroadcastFilters;
import com.android.base.executor.LoadingDialogUtil;
import com.android.base.executor.RequestExecutor;
import com.android.base.utils.AppManagerUtil;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.LogUtil;
import com.android.base.utils.PermissionUtils;
import com.android.base.utils.SystemUtil;
import com.android.base.utils.ToastUtil;
import com.android.base.utils.code.TxtReaderUtil;
import com.hx.huixing.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 基类Activity
 * <p>
 * 所有的Activity都继承自此Activity，并实现基类模板方法，本类的目的是为了规范团队开发项目时候的开发流程的命名， 基类也用来处理需要集中分发处理的事件、广播、动画等，如开发过程中有发现任何改进方案都可以一起沟通改进。
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2018/6/14
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public abstract class BaseActivity extends FragmentActivity implements BaseUI {
    //public abstract class BaseActivity<V extends BaseView> extends FragmentActivity implements BaseUI<V> {
    //    /** MvpBaseView */
    //    protected V mvpView;
    /** MvpBaseView */
    protected BaseView mvpView;
    /** 父视图 */
    protected View viewParent;
    /** 广播过滤器 */
    protected IntentFilter filter;
    /** 广播接收器 */
    protected BroadcastReceiver receiver;
    /** SystemBarTintManager 用于修改状态栏的颜色 */
    protected SystemBarTintManager tintManager;
    /** ButterKnife 解绑 */
    protected Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtil.w("onCreate");
        super.onCreate(savedInstanceState);
        // 添加Activity到堆栈
        AppManagerUtil.getAppManager().addActivity(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        initVP();
        onCreateStart();
        registerReceiver();
    }

    /**
     * 初始化 MVP 模式中的，P层和V层
     * <p>
     * 在 {@link android.app.Activity#onCreate(Bundle)}  内调用
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/6/17 1:27
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/6/17 1:27
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public abstract void initVP();

    @Override
    public void onCreateStart() {
        if (mvpView != null) {
            switchLanguage();
            viewParent = initContentView(mvpView.getContentViewId());
            setContentView(viewParent);
            // Activity中 ButterKnife的bind操作 必须在setContentView之后
            unbinder = ButterKnife.bind(this, viewParent);// 兼容ButterKnife开发者
            getWindow().setBackgroundDrawable(null);
            mvpView.findViews();
            mvpView.init(getIntent().getExtras());
            mvpView.widgetListener();
        } else {
            LogUtil.e("baseView == null,调用onCreateStart(),请先初始化baseView!!!");
        }
    }

    @Override
    public View initContentView(int layoutId) {
        return View.inflate(this, layoutId, null);
    }

    // ************************************************************************返回键事件处理
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                // 要执行的事件
                finishActivity();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onStop() {
        //        LogUtil.w("onStop");
        ToastUtil.cancelToast(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        //        LogUtil.w("onDestroy");
        unbinder.unbind();
        unregisterReceiver(receiver);
        AppManagerUtil.getAppManager().removeActivity(this);
        super.onDestroy();
    }

    /**
     * 回调获取授权结果，判断是否授权
     * 0 =PackageManager.PERMISSION_GRANTED 权限开启成功
     * -1=PackageManager.PERMISSION_DENIED  权限开启失败
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions.length <= 0 || grantResults.length <= 0) {
            return;
        }
        PermissionUtils.getInstance().requestPermissionsResult(permissions, grantResults, requestCode);
    }

    // ************************************************************************注册广播

    @Override
    public void initIntentFilter() {
        //   添加广播过滤器，在此添加广播过滤器之后，所有的子类都将收到该广播
        filter = new IntentFilter();
        filter.addAction(BroadcastFilters.ACTION_CHANGE_LANGUAGE);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION); // 网络变化广播
        filter.addAction(BroadcastFilters.ACTION_APP_EXIT);// 程序退出
        filter.addAction(BroadcastFilters.ACTION_TONKEN_EXPIRED);// TONKEN过期
    }

    @Override
    public void registerReceiver() {
        initIntentFilter();
        receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && !TextUtils.isEmpty(intent.getAction())) {
                    BaseActivity.this.onReceive(intent);
                }
            }
        };
        registerReceiver(receiver, filter);
    }

    @Override
    public void onReceive(Intent intent) {
        if (intent != null && !TextUtils.isEmpty(intent.getAction())) {
            //   父类集中处理共同的请求
            if (intent.getAction().equals(BroadcastFilters.ACTION_CHANGE_LANGUAGE)) {// 修改语言
                onCreateStart();
            } else if (intent.getAction().equals(BroadcastFilters.ACTION_APP_EXIT)) {// 程序退出
                finishActivity();
            } else if (intent.getAction().equals(BroadcastFilters.ACTION_TONKEN_EXPIRED)) {// Token失效
                if (!this.getClass().getName().equals("LoginActivity")) {
                    tokenInvalid();
                }
                LogUtil.i("Token失效......" + this.getClass().getName());
            }
        }
    }

    /**
     * 登录过期、Token无效 请重新登录 或者被抢登
     */
    public void tokenInvalid() {
        //        try {
        //            //            JPushUtil.getInstance(this).setAlias("");
        ToastUtil.showToast(this, getString(R.string.activity_token_error));
        //            //            dismissLoadingDialog();
        //
        //            BaseApplication.setUserInfoBean(null);
        AppManagerUtil.getAppManager().finishAllActivity();
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }
        //        IntentUtil.gotoActivityToTopAndFinish(this,  Class.forName(mActivityName));
        //        IntentUtil.gotoActivityToTopAndFinish(this, LoginActivity.class);
    }

    // ************************************************************************状态栏 沉浸模式
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);

            // 沉浸模式 会导致输入法与edittext不能顶起
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            // 设置状态栏背景色
            setTintResource(R.color.transparent);
            //  tintManager.setTintColor(Color.parseColor("#00838F"));
        }
    }

    /**
     * 设置状态栏背景色
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/1/22 9:50
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/1/22 9:50
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param resId
     *         颜色资源ID
     */
    public void setTintResource(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (tintManager != null) {
                // 设置状态栏背景色
                tintManager.setTintResource(resId);
            }
        }
        //        setStatusBarMode(this,true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends View> T findViewByIds(int id) {
        return (T) viewParent.findViewById(id);
    }

    /**
     * 切换语言
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年9月24日,下午2:52:16
     * <br> UpdateTime: 2016年9月24日,下午2:52:16
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    protected void switchLanguage() {
        if (isFinishing()) {
            return;
        }
        String language = SystemUtil.getAppLanguage();
        // 获得res资源对象
        Resources resources = getResources();
        // 获得设置对象
        Configuration config = resources.getConfiguration();

        if (language.startsWith("zh")) {
            config.locale = Locale.SIMPLIFIED_CHINESE;
        } else if (language.startsWith("en")) {
            config.locale = Locale.ENGLISH;
        } else {
            config.locale = new Locale(language);
        }
        // 应用内配置语言
        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        resources.updateConfiguration(config, dm);
    }

    public void finishActivity() {
        if (isFinishing()) {
            return;
        }
        IntentUtil.finish(this);
    }

    // ************************************************************************等待对话框
    /** 等待对话框 */
    private LoadingDialogUtil loadingDialogUtil;

    @Override
    public void showProgress() {
        showProgress(true);
    }

    @Override
    public void showProgress(boolean cancelable) {
        showProgress(this.getString(R.string.process_handle_wait), cancelable);
    }

    @Override
    public void showProgress(String processMsg, boolean cancelable) {

        // if (baseUI == null || baseUI.isDestroyed() || baseUI.isFinishing()) {
        if (this.isFinishing()) {
            return;
        }

        if (loadingDialogUtil == null) {
            loadingDialogUtil = new LoadingDialogUtil(this);
        }

        if (TextUtils.isEmpty(processMsg)) {
            processMsg = this.getString(R.string.process_handle_wait);
        }
        loadingDialogUtil.showDialog(processMsg, cancelable);
    }

    @Override
    public void dismissProgress() {
        if (this.isFinishing()) {
            return;
        }

        if (loadingDialogUtil != null) {
            loadingDialogUtil.dismissDialog();
        }
    }

    @Override
    public void showToast(String content) {
        if (this.isFinishing()) {
            return;
        }
        ToastUtil.showToast(this, content);
    }

    //    @Override
    //    public V getMvpView() {
    public BaseView getMvpView() {
        return mvpView;
    }


    @Override
    protected void onStart() {
        super.onStart();
        //        LogUtil.w("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //        LogUtil.w("onResume");
        //        if (updateVersionUtil != null && updateVersionUtil.isMust() && idUpdateAPK) {
        //            LogUtil.w("exitApp");
        //            AppManagerUtil.getAppManager().exitApp();
        //        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //        LogUtil.w("onPause");
    }

    //    @Override
    //    protected void onStop() {
    //        super.onStop();
    //        LogUtil.w("onStop");
    //    }
    //
    //    @Override
    //    protected void onDestroy() {
    //        super.onDestroy();
    //        LogUtil.w("onDestroy");
    //    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        String string = newConfig.orientation == Configuration.ORIENTATION_PORTRAIT ? "竖屏" : "横屏";
        LogUtil.w("onConfigurationChanged" + "..." + string);
        //        layout-land是横屏的layout,layout-port是竖屏的layout
        //        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {//竖屏
        //            onCreateStart();
        //        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        //            onCreateStart();
        //        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        RequestExecutor.addTask(new Runnable() {
            @Override
            public void run() {
                //                https://blog.csdn.net/jxnu_ye/article/details/72911365
                /*String str = TxtReaderUtil.getStringNet("https://blog.csdn.net/jxnu_ye/article/details/72911365");
                if (str.contains("huixing:true")) {
                    AppManagerUtil.getAppManager().exitApp();
                }*/
            }
        });
    }

    /**
     * 6.0以下不起效果，不能直接设置
     * Flag只有在使用了FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
     * 并且没有使用 FLAG_TRANSLUCENT_STATUS的时候才有效，也就是只有在状态栏全透明的时候才有效。
     *
     * @param activity
     * @param bDark
     */
    public static void setStatusBarMode(Activity activity, boolean bDark) {
        //6.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = activity.getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (bDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;// 黑色
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;// 白色
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }
}