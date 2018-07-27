//package com.android.base.activity.base;
//
//import android.support.v4.app.FragmentTransaction;
//
//import com.hx.huixing.R;
//import com.android.base.activity.BaseActivity;
//import com.android.base.bean.ResponseBean;
//import com.android.base.fragment.TestFragment;
//import com.android.base.utils.PreferencesUtil;
//
///**
// * 图片glide加载流程
// * <p>
// * <br> Author: 叶青
// * <br> Version: 1.0.0
// * <br> Date: 2016年12月11日
// * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
// */
//public class Item1Activity extends BaseActivity {
//
//
//    @Override
//    protected int getContentViewId() {
//        return R.layout.activity_item1;
//    }
//
//    @Override
//    protected void findViews() {
//    }
//
//    @Override
//    protected void initGetData() {
//
//    }
//
//    @Override
//    protected void init() {
//        PreferencesUtil.put("123456789", new ResponseBean("10000", "操作成功", "t e s t"));
//        switchView(123);
//    }
//
//    /**
//     * 选择界面
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016-11-24,下午5:42:30
//     * <br> UpdateTime: 2016-11-24,下午5:42:30
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     *
//     * @param position
//     */
//    private void switchView(int position) {
//        try {
//            //            // 获取Fragment的操作对象
//            //            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            //
//            //            TestFragment fragment = new TestFragment();
//            //            Bundle bundle = new Bundle();
//            //            bundle.putString(ConstantKey.INTENT_KEY_TYPE, "type");
//            //            fragment.setArguments(bundle);
//            //            transaction.replace(R.id.container, fragment);
//            //
//            //            getSupportFragmentManager().popBackStack(position + "", 0);
//            //            transaction.addToBackStack(position + "");// 将上一个Fragment存回栈，生命周期为stop，不销毁
//            //            transaction.commitAllowingStateLoss();// 提交更改
//
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            //            for (int i = 0; i < listFragments.size(); i++) {
//            //                if (i != position) {
//            //                    transaction.hide(listFragments.get(i));
//            //                }
//            //            }
//            TestFragment fragment = new TestFragment();
//            transaction.add(R.id.container, fragment);
////            transaction.show(fragment);
//            transaction.commit();// 提交更改
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected void widgetListener() {
//    }
//}