package com.hx.huixing.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.android.base.BaseApplication;
import com.android.base.activity.ArticleDetailActivity;
import com.android.base.activity.InviteFriendActivity;
import com.android.base.configs.ConfigFile;
import com.android.base.configs.ConfigServer;
import com.android.base.utils.BitmapUtil;
import com.android.base.utils.FileUtil;
import com.android.base.utils.dialog.share.ShareBean;
import com.android.base.utils.dialog.share.ShareDialogUtil;
import com.android.base.widget.TitleView;
import com.hx.huixing.R;
import com.hx.huixing.activityMvp.BasePresenter;
import com.hx.huixing.widget.ProgressBarWebView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
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

    private File file;
    StringBuilder builder;

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

        builder= new StringBuilder();
        builder.append(ConfigServer.H5_INVITE)
                .append("invitingCode=")
                .append(BaseApplication.getInstance().getUserInfoBean().getInvitingCode());
        mWebView.loadUrl(builder.toString());

        titleView.setLeftBtnImg();
        titleView.setTitle("邀请好友");
        titleView.setRightBtnTxt("分享", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
        setConfigCallback((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE));
        initWebView();
    }

    @Override
    protected void widgetListener() {


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

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                /** 截图 */
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = BitmapUtil.takeScreenShot4View1(InviteActivity.this, mWebView);
                        if (bitmap != null) {
                            try {
                                file = new File(ConfigFile.PATH_CAMERA + "/screenShot.png");
                                if (file.exists()) {
                                    file.delete();
                                }
                                file.createNewFile();
                                FileOutputStream os = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });

    }

    /**
     * 用来防止退出app后 webview还占用内存
     *
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
        } catch (Exception e) {
        }
    }

    ShareDialogUtil popupWindowUtil;

    private void share() {
        ShareBean shareBean = new ShareBean();
        shareBean.setPhotoPath(ConfigFile.PATH_CAMERA + "/screenShot.png");

        shareBean.setTextContent("邀请好友 赚彗星币");
        shareBean.setTitle("邀请好友");
        shareBean.setContentUrl(builder.toString());

        String[] nameItems = getResources().getStringArray(R.array.share_types);
        Integer[] resItems = new Integer[]{R.drawable.share_wechat, //
                R.drawable.share_wechatmoments, //
                R.drawable.share_sina
        };
        popupWindowUtil = new ShareDialogUtil(this, shareBean, nameItems, resItems);
        popupWindowUtil.show(shareBean, Gravity.BOTTOM);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        if(popupWindowUtil!=null){
            popupWindowUtil.dismissProgress();
        }
    }

}
