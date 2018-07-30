package com.hx.huixing.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.base.BaseApplication;
import com.android.base.configs.ConfigServer;
import com.android.base.widget.TitleView;
import com.hx.huixing.R;
import com.hx.huixing.activityMvp.BasePresenter;
import com.hx.huixing.widget.ProgressBarWebView;

import java.lang.reflect.Field;

/**
 * <br> Description 邀请好友
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.activity
 * <br> Date: 2018/7/30
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class InviteActivity extends BaseActivity {

    private TitleView titleView;
    private ProgressBarWebView mWebView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_invite_friend;
    }

    @Override
    protected void findViews() {
        titleView = findViewById(R.id.title_view);
        mWebView = findViewById(R.id.webview);
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void init() {
        setConfigCallback((WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE));
        initWebView();
    }

    @Override
    protected void widgetListener() {
        StringBuilder builder = new StringBuilder();
        builder.append(ConfigServer.H5_INVITE).append("invitingCode=").append(BaseApplication.getInstance().getUserInfoBean().getInvitingCode());
        mWebView.loadUrl(builder.toString());
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setConfigCallback(null);
    }

    /**
     * 初始化webview
     */
    private void initWebView() {
        mWebView.canGoBack();
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        mWebView.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //不加这个 会在浏览器中打开
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });


        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("isAppBack")) {
                    finish();
                }
                return true;
            }
        });

    }

    /**
     * 用来防止退出app后 webview还占用内存
     * @param windowManager
     */
    public void setConfigCallback(WindowManager windowManager) {
        try {
            Field field = WebView.class.getDeclaredField("mWebViewCore");
            field = field.getType().getDeclaredField("mBrowserFrame");
            field = field.getType().getDeclaredField("sConfigCallback");
            field.setAccessible(true);
            Object configCallback = field.get(null);

            if (null == configCallback) {
                return;
            }

            field = field.getType().getDeclaredField("mWindowManager");
            field.setAccessible(true);
            field.set(configCallback, windowManager);
        } catch(Exception e) {
        }
    }
}
