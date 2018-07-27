//package com.android.base.activity.base;
//
//import android.view.KeyEvent;
//import android.view.inputmethod.EditorInfo;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.hx.huixing.R;
//import com.android.base.activity.BaseActivity;
//import com.android.base.utils.EmptyCheckUtil;
//import com.android.base.utils.KeyboardUtil;
//import com.android.base.utils.LogUtil;
//import com.android.base.widget.CustomKeyBoardLayout;
//import com.android.base.widget.TitleView;
//
///**
// * 键盘高度
// * <p>
// * <br> Author: 叶青
// * <br> Version: 1.0.0
// * <br> Date: 2016年12月11日
// * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
// */
//public class Item6Activity extends BaseActivity {
//
//    private TextView textView;
//    private CustomKeyBoardLayout keyboardLayout;
//
//    @Override
//    protected int getContentViewId() {
//        return R.layout.activity_item6;
//    }
//
//    @Override
//    protected void findViews() {
//        keyboardLayout = (CustomKeyBoardLayout) findViewById(R.id.view1);
//        textView = (TextView) findViewById(R.id.textView);
//        final TitleView title_view = findViewByIds(R.id.title_view);
//        final EditText edit_search = findViewByIds(R.id.editText1);
//        title_view.setTitle("键盘高度");
//        title_view.setLeftBtnImg();
//
//        edit_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
//        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
//                    KeyboardUtil.hideKeyBord(edit_search);
//                    if (!EmptyCheckUtil.isEmpty(edit_search.getText().toString())) {
//                        //						if (currentFragment == FRAGMENT_INDEX) {
//                        //							switchView(FRAGMENT_TYPE);
//                        //						} else {
//                        //							searchResultFragment.initListView();
//                        //						}
//                    }
//                    return true;
//                }
//
//                return false;
//            }
//        });
//
//        keyboardLayout.setOnSizeChangedListener(new CustomKeyBoardLayout.onSizeChangedListener() {
//
//            @Override
//            public void onChanged(boolean showKeyboard) {
//                // TODO Auto-generated method stub
//                if (showKeyboard) {
//                    LogUtil.i("show keyboard true");
//                    title_view.setTitle("show keyboard true");
//                    //                    mHandler.sendMessage(mHandler.obtainMessage(KEYBOARD_SHOW));
//                } else {
//                    title_view.setTitle("show keyboard false");
//                    LogUtil.i("show keyboard false");
//                    //                    mHandler.sendMessage(mHandler.obtainMessage(KEYBOARD_HIDE));
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void init() {
//    }
//
//    @Override
//    protected void initGetData() {
//
//    }
//
//    @Override
//    protected void widgetListener() {
//
//    }
//
//}