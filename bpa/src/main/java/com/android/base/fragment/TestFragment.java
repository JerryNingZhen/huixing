//package com.android.base.fragment;
//
//import android.os.Bundle;
//import android.widget.RadioGroup;
//import android.widget.RadioGroup.OnCheckedChangeListener;
//
//import com.hx.huixing.R;
//import com.android.base.adapter.TestListAdapter;
//import com.android.base.bean.ResponseBean;
//import com.android.base.mvp.model.TestModel;
//import com.android.base.configs.ConstantKey;
//import com.android.base.executor.BaseTask;
//import com.android.base.executor.RequestExecutor;
//import com.android.base.utils.PreferencesUtil;
//import com.android.base.utils.ToastUtil;
//import com.android.base.widget.TitleView;
//import com.android.base.widget.pullview.PullToRefreshBase;
//import com.android.base.widget.pullview.PullToRefreshListView;
//
//import java.util.ArrayList;
//
///**
// * hello world
// * <p>
// * <br> Author: 叶青
// * <br> Version: 1.0.0
// * <br> Date: 2016年12月11日
// * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
// */
//public class TestFragment extends BaseFragment {
//
//    public RadioGroup radioGroup;
//    public TitleView titleview;
//    public PullToRefreshListView lv_base;
//
//    public TestFragment() {
//    }
//
//    @Override
//    protected int getContentViewId() {
//        return R.layout.fragment_test;
//    }
//
//    @Override
//    protected void findViews() {
//        radioGroup = findViewByIds(R.id.radiogroup);
//        titleview = findViewByIds(R.id.title_view);
//        lv_base = findViewByIds(R.id.lv_base);
//    }
//
//    @Override
//    protected void widgetListener() {
//        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                // TODO Auto-generated method stub
//                if (R.id.radio_btn_left == checkedId) {
//                    // LoadingDialogUtil loadingDialogUtil = new LoadingDialogUtil(getActivity());
//                    // loadingDialogUtil.showDialog(getString(R.string.process_handle_wait), true);
//                    try {
//                        ResponseBean bean = (ResponseBean) PreferencesUtil.get("123456789",null);
//                        ToastUtil.showToast(getActivity(), bean.getInfo() + "." + bean.getStatus() + "." + bean.getObject());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else if (R.id.radio_btn_right == checkedId) {
//                }
//            }
//        });
//        lv_base.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener() {
//            @Override
//            public void onPullToDownRefresh() {
//                lv_base.setClickCompleteAndAll();
//                checkUpdate();
//            }
//
//            @Override
//            public void onPullToUpRefresh() {
//                checkUpdate();
//            }
//        });
//
//        titleview.setLeftBtnImg();
//    }
//
//    @Override
//    protected void init() {
//        initListView();
//        //        titleview.setTitle(getArguments().getString(ConstantKey.INTENT_KEY_TYPE, ""));
//    }
//
//    @Override
//    public void initGetData() {
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            String type = getArguments().getString(ConstantKey.INTENT_KEY_TYPE, "");
//            titleview.setTitle(type + "~~~~");
//        }
//    }
//
//    private void initListView() {
//
//        String string = "http://godive.cn/seaguest_server/diveres/imgs/20161221/12673-20161221200427966-0.png"
//                + ",http://godive.cn/seaguest_server/diveres/headimgs/20170119/13094-201701191718091070.png"
//                + ",http://godive.cn/seaguest_server/diveres/headimgs/20161228/12763-20161228103510014.jpg"
//                + ",http://godive.cn/seaguest_server/diveres/headimgs/20170117/13057-201701171321582230.png"
//                + ",http://godive.cn/seaguest_server/diveres/headimgs/20161221/12673-20161221171337284.jpg"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20160831/Slide_20160831004212135_91.JPG"
//                + ",http://godive.cn/seaguest_server/diveres/headimgs/20170302/13502-201703022348567790.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20160808/Slide_20160808100320912_88.jpg"
//                + ",http://godive.cn/seaguest_server/diveres/headimgs/20160812/9677-20160812123635983.jpg"
//                + ",http://godive.cn/seaguest_server/diveres/headimgs/20160720/9133-20160720221038065.jpg"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170301/201703011954204393-9133.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170302/13502-20170302160249599-1.png"
//                + ",http://godive.cn/seaguest_server/diveres/headimgs/20170214/13459-201702141245503550.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170302/13502-20170302160249625-3.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170301/201703012028075550-9677.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170301/201703011623264793-13459.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170301/201703011623264452-13459.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170301/201703012028077378-9677.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170228/201702281756329710-12529.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170301/201703011623264131-13459.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170228/201702281706302456-12256.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170228/201702281706302637-12256.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170228/201702281706302075-12256.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170228/201702281706299300-12256.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170228/201702281706301083-12256.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170228/201702281706301584-12256.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170228/201702281706299821-12256.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170228/201702281706300742-12256.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170228/201702281758484730-12529.png"
//                + ",http://godive.cn/seaguest_server/diveres/headimgs/20161211/12529-20161211220020206.jpg"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170228/201702281706302456-12256.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170228/13459-20170228143153528-1.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170228/13459-20170228143153488-0.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170228/13459-20170228143153746-7.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170228/13459-20170228143153772-8.png"
//                + ",http://godive.cn/seaguest_server/diveres/headimgs/20170223/13605-201702230110016880.png"
//                + ",http://godive.cn/seaguest_server/diveres/headimgs/20160706/5882-201607062358462270.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170227/13605-20170227193156818-1.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170227/13605-20170227193156798-0.png"
//                + ",http://godive.cn/seaguest_server/diveres/headimgs/20161210/11159-201612101513134840.png"
//                + ",http://godive.cn/seaguest_server/diveres/headimgs/20160713/8394-201607131933503730.png"
//                + ",http://godive.cn/seaguest_server/diveres/headimgs/20170224/13626-201702242255509540.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170224/13626-20170224231350658-0.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170225/201702250306485675-13629.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170225/201702250306486186-13629.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170225/201702250306486477-13629.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170225/201702250306486668-13629.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170225/13639-20170225193158495-4.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170225/13639-20170225193158526-5.png"
//                + ",http://godive.cn/seaguest_server/diveres/headimgs/20170226/13647-201702261047360710.png"
//                + ",http://godive.cn/seaguest_server/diveres/headimgs/20170225/13639-201702251932151860.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170223/13605-20170223011109906-2.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170223/13605-20170223011109881-1.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170223/13605-20170223011109858-0.png"
//                + ",http://godive.cn/seaguest_server/diveres/imgs/20170225/13639-20170225193158439-3.png";
//
//
//        String[] strs = string.split(",");
//        ArrayList<String> arrayList = new ArrayList<String>();
//        for (int i = 0; i < strs.length; i++) {
//            arrayList.add(strs[i]);
//        }
//
//        TestListAdapter adapter = new TestListAdapter(getActivity(), arrayList);
//        lv_base.getRefreshableView().setAdapter(adapter);
//    }
//
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
//        RequestExecutor.addTask(new BaseTask() {
//
//            @Override
//            public ResponseBean sendRequest() {
//                return TestModel.testPost1();
//            }
//
//            @Override
//            public void onSuccess(ResponseBean result) {
//                lv_base.onRefreshComplete();
//            }
//
//            @Override
//            public void onFail(ResponseBean result) {
//                lv_base.onRefreshComplete();
//            }
//        });
//    }
//}