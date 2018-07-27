//package com.android.base.activity.base;
//
//import android.view.View;
//import android.view.View.OnClickListener;
//
//import com.hx.huixing.R;
//import com.android.base.activity.BaseActivity;
//import com.android.base.bean.ResponseBean;
//import com.android.base.bean.UserInfoBean;
//import com.android.base.configs.ConfigServer;
//import com.android.base.database.DBOperationUtil;
//import com.android.base.executor.BaseTask;
//import com.android.base.executor.RequestExecutor;
//import com.android.base.utils.DateUtil;
//import com.android.base.utils.ToastUtil;
//import com.android.base.widget.TitleView;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//
///**
// * 主界面
// * <p>
// * <br> Author: 叶青
// * <br> Version: 1.0.0
// * <br> Date: 2016年12月11日
// * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
// */
//public class TestDBActivity extends BaseActivity {
//
//    /** 标题栏 */
//    public TitleView titleview;
//    public View item0;
//    public View item1;
//    public View item2;
//    public View item3;
//    public View item4;
//
//    @Override
//    protected int getContentViewId() {
//        return R.layout.activity_item11;
//    }
//
//    @Override
//    protected void findViews() {
//        activity = this;
//        titleview = (TitleView) findViewById(R.id.title_view);
//        item0 = findViewById(R.id.item0);
//        item1 = findViewById(R.id.item1);
//        item2 = findViewById(R.id.item2);
//        item3 = findViewById(R.id.item3);
//        item4 = findViewById(R.id.item4);
//    }
//
//    @Override
//    protected void initGetData() {
//
//    }
//
//    @Override
//    protected void init() {
//        titleview.setTitle("DB");
//        bean.setUserAccount(DateUtil.getDate(DateUtil.HMS));
//        bean.setPassword("123456");
//        bean.setNickname(DateUtil.getDate(DateUtil.HM));
//        bean.setPortrait(DateUtil.getDate());
//    }
//
//    UserInfoBean bean = new UserInfoBean();
//    String passphrase = "";
//    DateFormat DATE_FORMAT = SimpleDateFormat.getDateTimeInstance();
//
//    @Override
//    protected void widgetListener() {
//        titleview.setLeftBtnImg();
//        item0.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                RequestExecutor.addTask(new BaseTask() {
//                    @Override
//                    public ResponseBean sendRequest() {
//                        passphrase = "";
//                        DBOperationUtil.selectTest(passphrase);
//                        return new ResponseBean(ConfigServer.RESPONSE_STATUS_SUCCESS);
//                    }
//
//                    @Override
//                    public void onSuccess(ResponseBean result) {
//
//                    }
//
//                    @Override
//                    public void onFail(ResponseBean result) {
//
//                    }
//                });
//            }
//        });
//
//        item1.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                RequestExecutor.addTask(new BaseTask() {
//                    @Override
//                    public ResponseBean sendRequest() {
//                        passphrase = "passphrase";
//                        DBOperationUtil.selectTest(passphrase);
//                        return new ResponseBean(ConfigServer.RESPONSE_STATUS_SUCCESS);
//                    }
//
//                    @Override
//                    public void onSuccess(ResponseBean result) {
//                        ToastUtil.showToast(TestDBActivity.this, result.getInfo());
//                    }
//
//                    @Override
//                    public void onFail(ResponseBean result) {
//                        ToastUtil.showToast(TestDBActivity.this, result.getInfo());
//                    }
//                });
//            }
//        });
//
//        item2.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                RequestExecutor.addTask(new BaseTask() {
//                    @Override
//                    public ResponseBean sendRequest() {
//                        bean.setUserAccount(DateUtil.getDate(DateUtil.HM));
//                        bean.setNickname(DateUtil.getDate(DateUtil.HM));
//                        bean.setPortrait(DateUtil.getDate());
//                        DBOperationUtil.insertTest(passphrase, bean);
//                        DBOperationUtil.selectTest(passphrase);
//                        return new ResponseBean(ConfigServer.RESPONSE_STATUS_SUCCESS);
//                    }
//
//                    @Override
//                    public void onSuccess(ResponseBean result) {
//                        ToastUtil.showToast(TestDBActivity.this, result.getInfo());
//                    }
//
//                    @Override
//                    public void onFail(ResponseBean result) {
//                        ToastUtil.showToast(TestDBActivity.this, result.getInfo());
//                    }
//                });
//            }
//        });
//
//        item3.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                RequestExecutor.addTask(new BaseTask() {
//                    @Override
//                    public ResponseBean sendRequest() {
//                        bean.setNickname(DateUtil.getDate(DateUtil.HM));
//                        bean.setPortrait(DateUtil.getDate());
//                        DBOperationUtil.updateTest(passphrase, bean);
//                        DBOperationUtil.selectTest(passphrase);
//                        return new ResponseBean(ConfigServer.RESPONSE_STATUS_SUCCESS);
//                    }
//
//                    @Override
//                    public void onSuccess(ResponseBean result) {
//
//                    }
//
//                    @Override
//                    public void onFail(ResponseBean result) {
//
//                    }
//                });
//            }
//        });
//
//        item4.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                RequestExecutor.addTask(new BaseTask() {
//                    @Override
//                    public ResponseBean sendRequest() {
//                        bean.setNickname(DateUtil.getDate(DateUtil.HM));
//                        bean.setPortrait(DateUtil.getDate());
//                        DBOperationUtil.deleteTest(passphrase, bean);
//                        DBOperationUtil.selectTest(passphrase);
//                        return new ResponseBean(ConfigServer.RESPONSE_STATUS_SUCCESS);
//                    }
//
//                    @Override
//                    public void onSuccess(ResponseBean result) {
//
//                    }
//
//                    @Override
//                    public void onFail(ResponseBean result) {
//
//                    }
//                });
//            }
//        });
//
//    }
//
//    public static TestDBActivity activity;
//
//    public static TestDBActivity getActivity() {
//        return activity;
//    }
//}