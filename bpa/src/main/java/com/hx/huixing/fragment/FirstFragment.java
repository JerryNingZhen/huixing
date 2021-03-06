package com.hx.huixing.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.BaseApplication;
import com.android.base.configs.ConfigServer;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.ScreenUtil;
import com.google.gson.Gson;
import com.hx.huixing.R;
import com.hx.huixing.activity.MainActivity;
import com.hx.huixing.activity.MessageActivity;
import com.hx.huixing.adapter.BasePagerAdapter;
import com.hx.huixing.bean.CommentBean;
import com.hx.huixing.bean.CountBean;
import com.hx.huixing.bean.ThumbBean;
import com.hx.huixing.common.net.JsonCallBack;
import com.hx.huixing.common.net.RetrofitUtils;
import com.hx.huixing.widget.TabPageIndicator;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

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

    private ImageView iv_msg;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_first;
    }

    @Override
    protected void findViews() {
        mActivity = ((MainActivity) getActivity());
        indicator = findViewByIds(R.id.indicator);
        mViewPager = findViewByIds(R.id.viewpager);
        iv_msg = findViewByIds(R.id.iv_msg);

    }

    @Override
    public void initGetData() {
        titles.add(getString(R.string.care));
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
        iv_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtil.gotoActivity(mActivity, MessageActivity.class);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        countNotReadMessage();
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

    /**
     *  查询消息条数
     *  要查看的消息的类型，传整数数组，
     * 2为点赞的未读消息，3为评论的未读消息
     * 7为点赞评论的，1为回复别人的评论
     */
    private void countNotReadMessage(){
        Map<String, String> map = new TreeMap<>();
        map.put("userId", "42e7ce4d-c7ad-476b-8850-1a60bba0e64a");
        map.put("userPwd", "32f913adb0951b14373e444c3c4cfcc9");
        //map.put("userId", BaseApplication.getInstance().getUserInfoBean().getId());
        //map.put("password", BaseApplication.getInstance().getUserInfoBean().getUserPwd());
        map.put("type", "2,3,7,1");

        RetrofitUtils.getInstance().normalGet(ConfigServer.SERVER_API_URL + ConfigServer.METHOD_COUNTNOTREADMESSAGE, map, new JsonCallBack() {
            @Override
            public void next(String response) {
                CountBean bean = new Gson().fromJson(response, CountBean.class);
                if (bean != null){
                    int msgCount = Integer.parseInt(bean.getDatas());
                    if (msgCount == 0){
                        /** 消息为0 */
                        iv_msg.setImageResource(R.drawable.icon_msg);
                    }else if (msgCount > 0){
                        /** 消息不为0 */
                        iv_msg.setImageResource(R.drawable.icon_msg_spot);
                    }
                }else {
                    iv_msg.setImageResource(R.drawable.icon_msg);
                }


            }

            @Override
            public void error(Throwable e) {
                Log.e("tanjun",e.toString());

            }

            @Override
            public void startLoading() {

            }

            @Override
            public void closeLoading() {

            }
        });

    }



}
