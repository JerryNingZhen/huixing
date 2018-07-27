package com.hx.huixing.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.hx.huixing.R;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.tan.mvpdemo.activityMvp
 * <br> Date: 2018/7/15
 */
public class SecondFragment extends BaseFragment {

    private TextView tv_content;

    public static SecondFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        SecondFragment fragment = new SecondFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_second;
    }

    @Override
    protected void findViews() {
        tv_content = findViewByIds(R.id.tv_content2);
    }

    @Override
    public void initGetData() {
        /*Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.get("name").toString();
            tv_content.setText(name);
        }*/
    }

    @Override
    protected void init() {

    }

    @Override
    protected void widgetListener() {

    }
}
