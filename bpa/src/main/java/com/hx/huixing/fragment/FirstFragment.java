package com.hx.huixing.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.android.base.utils.IntentUtil;
import com.android.base.utils.ScreenUtil;
import com.hx.huixing.R;
import com.hx.huixing.activity.MainActivity;
import com.hx.huixing.activity.MessageActivity;
import com.hx.huixing.adapter.BasePagerAdapter;
import com.hx.huixing.widget.TabPageIndicator;

import java.util.ArrayList;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.tan.mvpdemo.activityMvp
 * <br> Date: 2018/7/15
 */
public class FirstFragment extends BaseFragment {

    private TabPageIndicator indicator;
    private ViewPager mViewPager;

    BasePagerAdapter mAdapter;

    private ArrayList<Fragment> fragment = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();

    private MainActivity mActivity;

    private Button btn_notice;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_first;
    }

    @Override
    protected void findViews() {
        mActivity = ((MainActivity) getActivity());
        indicator = findViewByIds(R.id.indicator);
        mViewPager = findViewByIds(R.id.viewpager);
        btn_notice = findViewByIds(R.id.btn_notice);

    }

    @Override
    public void initGetData() {
        titles.add(getString(R.string.dynamic));
        titles.add(getString(R.string.recomend));
        fragment.add(new CareFragment());
        fragment.add(new RecomendFragment());
    }

    @Override
    protected void init() {
        mAdapter = new BasePagerAdapter(mActivity.getSupportFragmentManager(), titles,fragment);
        mViewPager.setAdapter(mAdapter);
        indicator.setViewPager(mViewPager);
        setTabPagerIndicator();
    }

    @Override
    protected void widgetListener() {
        /** 消息 */
        btn_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtil.gotoActivity(mActivity, MessageActivity.class);
            }
        });

    }

    /** 设置上面的导航 */
    private void setTabPagerIndicator() {
        indicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_NOWEIGHT_NOEXPAND_SAME);// 设置模式，一定要先设置模式
        indicator.setDividerPadding(ScreenUtil.dip2px(10));
        indicator.setIndicatorColor(Color.parseColor("#303F9F"));// 设置底部导航线的颜色
        indicator.setTextColorSelected(Color.parseColor("#FF000000"));// 设置tab标题选中的颜色
        indicator.setTextColor(Color.parseColor("#666666"));// 设置tab标题未被选中的颜色
        indicator.setTextSize(ScreenUtil.sp2px(18));// 设置字体大小
    }

    public void onActivityRestart() {
        try {
            int position = (mViewPager.getCurrentItem());
            Fragment contentFragment = mAdapter.getItem(position);
            if (position == 0) {
                ((CareFragment) contentFragment).onActivityRestart();
            } else {
                ((RecomendFragment) contentFragment).onActivityRestart();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
