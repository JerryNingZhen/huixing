//package com.android.base.activity.base;
//
//import android.content.Intent;
//import android.text.TextUtils;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.LinearLayout;
//
//import com.hx.huixing.R;
//import com.android.base.activity.BaseActivity;
//import com.android.base.activity.mvp.test.activity.MvpTestActivity;
//import com.android.base.activity.mvp.test.fragment.TestActivity;
//import com.android.base.bean.ResponseBean;
//import com.android.base.bean.UpdateBean;
//import com.android.base.configs.ConstantKey;
//import com.android.base.executor.BaseTask;
//import com.android.base.executor.RequestExecutor;
//import com.android.base.mvp.model.TestModel;
//import com.android.base.service.UpdateService;
//import com.android.base.utils.IntentUtil;
//import com.android.base.utils.MathUtils;
//import com.android.base.utils.SystemUtil;
//import com.android.base.utils.ToastUtil;
//import com.android.base.widget.view.BannerView;
//import com.android.base.widget.TitleView;
//
///**
// * 主界面
// * <p>
// * <br> Author: 叶青
// * <br> Version: 1.0.0
// * <br> Date: 2016年12月11日
// * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
// */
//public class HomeActivity extends BaseActivity {
//
//    /** 记录退出按下时间 */
//    private long exitTime = 0;
//    /** 标题栏 */
//    public TitleView titleview;
//    public View item0;
//    public View item1;
//    public View item2;
//    public View item3;
//    public View item4;
//    public View item5;
//    public View item6;
//    public View item7;
//    public View item8;
//    public View item9;
//    public View item10;
//    public View item11;
//    public LinearLayout view_heard;
//
//    @Override
//    protected int getContentViewId() {
//        return R.layout.activity_home;
//    }
//
//    @Override
//    protected void findViews() {
//        titleview = (TitleView) findViewById(R.id.title_view);
//        item0 = findViewById(R.id.item0);
//        item1 = findViewById(R.id.item1);
//        item2 = findViewById(R.id.item2);
//        item3 = findViewById(R.id.item3);
//        item4 = findViewById(R.id.item4);
//        item5 = findViewById(R.id.item5);
//        item6 = findViewById(R.id.item6);
//        item7 = findViewById(R.id.item7);
//        item8 = findViewById(R.id.item8);
//        item9 = findViewById(R.id.item9);
//        item10 = findViewById(R.id.item10);
//        item11 = findViewById(R.id.item11);
//        view_heard = findViewByIds(R.id.view_heard);
//
//        BannerView bannerView = new BannerView(this);
//        view_heard.addView(bannerView);
//        //bannerView.initViewPageData(null);
//        //        CustomTipsView customTipsView = findViewByIds(R.id.custom_tips_layout);
//        //        customTipsView.setNoDataViewVisibility(true);
//    }
//
//    @Override
//    protected void initGetData() {
//
//    }
//
//    @Override
//    protected void init() {
//        titleview.setTitle("首页");
//        checkUpdate();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //		LogUtil.v("LogUtil.v");
//        //		LogUtil.d("LogUtil.d");
//        //		LogUtil.i("LogUtil.i");
//        //		LogUtil.w("LogUtil.w");
//        //		LogUtil.e("LogUtil.e");
//        //		LogUtil.out("LogUtil.out");
//    }
//
//    @Override
//    protected void widgetListener() {
//
//        item0.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // 扫码主界面
//                //                IntentUtil.gotoActivity(HomeActivity.this, CaptureActivity.class);
//            }
//        });
//
//        item1.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // 图片glide加载流程
//                IntentUtil.gotoActivity(HomeActivity.this, Item1Activity.class);
//            }
//        });
//        item2.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // 侧拉菜单实现
//                IntentUtil.gotoActivity(HomeActivity.this, Item2Activity.class);
//            }
//        });
//
//        item3.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                //                // 定位
//                //                Intent intent = new Intent();
//                //                intent.setAction(BroadcastFilters.ACTION_LOCATION_START);
//                //                sendBroadcast(intent);
//                                IntentUtil.gotoActivity(HomeActivity.this, MvpTestActivity.class);
//            }
//        });
//
//        item4.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                                IntentUtil.gotoActivity(HomeActivity.this, TestActivity.class);
//            }
//        });
//
//        item5.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                //                IntentUtil.gotoActivity(HomeActivity.this, Item5Activity.class);
//            }
//        });
//
//        item6.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                IntentUtil.gotoActivity(HomeActivity.this, Item6Activity.class);
//            }
//        });
//        item7.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                IntentUtil.gotoActivity(HomeActivity.this, Item7Activity.class);
//            }
//        });
//        item8.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                //                IntentUtil.gotoActivity(HomeActivity.this, PayDemoActivity.class);
//            }
//        });
//        item9.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                //                IntentUtil.gotoActivity(HomeActivity.this, PayActivity.class);
//            }
//        });
//        item10.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                IntentUtil.gotoActivity(HomeActivity.this, TestOkHttpActivity.class);
//            }
//        });
//        item11.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                IntentUtil.gotoActivity(HomeActivity.this, TestDBActivity.class);
//            }
//        });
//    }
//
//    // ***********************************返回键事件处理*********************************
//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
//                // 要执行的事件
//                // DialogUtil.showExitsDg(HomeActivity.this);对话框退出
//                // 判断2次点击事件时间
//                if ((System.currentTimeMillis() - exitTime) > 2000) {
//                    // ToastUtil.showToast(HomeActivity.this,
//                    // getString(ResourceUtil.getStringId("tips_exit_time")));
//                    ToastUtil.showToast(HomeActivity.this, getString(R.string.tips_exit_time));
//                    exitTime = System.currentTimeMillis();
//                } else {
//                    finishActivity();
//                }
//
//            }
//            return true;
//        }
//        return super.dispatchKeyEvent(event);
//    }
//
//    /**
//     * 检测更新
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016-11-25,上午10:38:53
//     * <br> UpdateTime: 2016-11-25,上午10:38:53
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     */
//    private void checkUpdate() {
//        RequestExecutor.addTask(new BaseTask(HomeActivity.this, getString(com.hx.huixing.R.string.process_handle_wait), true) {
//
//            @Override
//            public ResponseBean sendRequest() {
//                return TestModel.testPost1();
//            }
//
//            @Override
//            public void onSuccess(ResponseBean result) {
//                UpdateBean bean = (UpdateBean) result.getObject();
//
//                // 比较当前版本跟服务器版本 version——name的大小
//                if (!TextUtils.isEmpty(bean.getVersionCode())) {
//                    if ( MathUtils.str2Int(bean.getVersionCode())>SystemUtil.getAppVersionCode() ) {
//                        //                    if (SystemUtil.compareVersion(bean.getVersionCode(), SystemUtil.getAppVersionName())) {
//                        // DialogUtil.showUpdateDg(HomeActivity.this, bean);
//                        Intent intent = new Intent();
//                        intent.putExtra(ConstantKey.INTENT_KEY_APK_PATH, bean.getUrl());
//                        intent.setClass(HomeActivity.this, UpdateService.class);
//                        // startService(intent);
//                    }
//                }
//                ToastUtil.showToast(HomeActivity.this, result.getInfo());
//            }
//
//            @Override
//            public void onFail(ResponseBean result) {
//                ToastUtil.showToast(HomeActivity.this, result.getInfo());
//            }
//        });
//    }
//}