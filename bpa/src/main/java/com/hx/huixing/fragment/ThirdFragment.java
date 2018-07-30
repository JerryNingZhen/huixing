package com.hx.huixing.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amos.smartrefresh.layout.SmartRefreshLayout;
import com.amos.smartrefresh.layout.api.RefreshLayout;
import com.amos.smartrefresh.layout.listener.OnRefreshListener;
import com.android.base.BaseApplication;
import com.android.base.activity.InviteFriendActivity;
import com.android.base.activity.PersonalArticleActivity;
import com.android.base.activity.PersonalFansActivity;
import com.android.base.activity.PersonalFocusActivity;
import com.android.base.configs.ConfigServer;
import com.android.base.configs.ConstantKey;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.ToastUtil;
import com.google.gson.Gson;
import com.hx.huixing.R;
import com.hx.huixing.activity.MainActivity;
import com.hx.huixing.activity.SettingActivity;
import com.hx.huixing.activity.WalletActivity;
import com.hx.huixing.bean.CheckSignInBean;
import com.hx.huixing.bean.MainPageBean;
import com.hx.huixing.bean.ValueBean;
import com.hx.huixing.common.net.JsonCallBack;
import com.hx.huixing.common.net.RetrofitUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * <br> Description 我的界面
 * <br> Author: 谭俊
 * <br> PackageName com.tan.mvpdemo.activityMvp
 * <br> Date: 2018/7/15
 */
public class ThirdFragment extends BaseFragment implements View.OnClickListener {

    /**
     * 刷新控件
     */
    private SmartRefreshLayout refresh_view;
    /**
     * 用户头像
     */
    private ImageView iv_user_head;
    /**
     * 用户名
     */
    private TextView tv_third_name;
    /**
     * 力量值
     */
    private TextView tv_power;
    /**
     * 经验值
     */
    private TextView tv_experience;
    /**
     * 钱包
     */
    private TextView tv_wallet;
    /**
     * 邀请
     */
    private TextView tv_invite;
    /**
     * 签到
     */
    private RelativeLayout rl_sign;
    /**
     * 设置
     */
    private TextView tv_setting;
    /** 用来显示签到状态 */
    private TextView tv_sign;

    //帖子数量
    private TextView tv_publish;
    //关注别人
    private TextView tv_focus_count;
    //粉丝
    private TextView tv_follow_count;
    //等级
    private TextView tv_level;

    private RelativeLayout rl_publish;
    private RelativeLayout rl_focus;
    private RelativeLayout rl_fans;

    private MainActivity mActivity;

    private String userId;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_third;
    }

    @Override
    protected void findViews() {
        mActivity = (MainActivity) getActivity();
        refresh_view = findViewByIds(R.id.refresh_view);
        iv_user_head = findViewByIds(R.id.iv_user_head);
        tv_third_name = findViewByIds(R.id.tv_third_name);
        tv_power = findViewByIds(R.id.tv_power);
        tv_experience = findViewByIds(R.id.tv_experience);
        tv_wallet = findViewByIds(R.id.tv_wallet);
        tv_invite = findViewByIds(R.id.tv_invite);
        rl_sign = findViewByIds(R.id.rl_sign);
        tv_setting = findViewByIds(R.id.tv_setting);

        rl_publish = findViewByIds(R.id.rl_publish);
        rl_focus = findViewByIds(R.id.rl_focus);
        rl_fans = findViewByIds(R.id.rl_fans);

        tv_publish = findViewByIds(R.id.tv_publish);
        tv_focus_count = findViewByIds(R.id.tv_focus_count);
        tv_follow_count = findViewByIds(R.id.tv_follow_count);
        tv_level = findViewByIds(R.id.tv_level);

        tv_sign = findViewByIds(R.id.tv_sign);
    }

    @Override
    public void initGetData() {
        userId = BaseApplication.getInstance().getUserInfoBean().getId();
        /** 用户信息 */
        getUserInfo(userId, userId);
        /** 体力经验 */
        getValue();
    }

    @Override
    protected void init() {
        checkSignIn();
    }

    @Override
    protected void widgetListener() {
        tv_wallet.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
        rl_sign.setOnClickListener(this);
//        tv_invite.setOnClickListener(this);
        rl_publish.setOnClickListener(this);
//        rl_focus.setOnClickListener(this);
//        rl_fans.setOnClickListener(this);

        /** 下拉刷新 */
        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                /** 用户信息 */
                getUserInfo(userId, userId);
                /** 体力经验 */
                getValue();
                checkSignIn();
                refresh_view.finishRefresh(1500);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_setting: //设置
                IntentUtil.gotoActivity(mActivity, SettingActivity.class);
                break;

            case R.id.rl_sign://签到
                signIn();
                break;

            case R.id.tv_wallet://钱包
                IntentUtil.gotoActivity(mActivity, WalletActivity.class);
                break;

            case R.id.tv_invite://邀请好友
                IntentUtil.gotoActivity(mActivity, InviteFriendActivity.class);
                break;

            case R.id.rl_publish://帖子

                Bundle bundle = new Bundle();
                bundle.putString(ConstantKey.INTENT_KEY_ID, BaseApplication.getInstance().getUserInfoBean().getId());
                IntentUtil.gotoActivityToTop(getActivity(), PersonalArticleActivity.class, bundle);
                break;

            case R.id.rl_focus://关注
                bundle = new Bundle();
                bundle.putString(ConstantKey.INTENT_KEY_ID, BaseApplication.getInstance().getUserInfoBean().getId());
                IntentUtil.gotoActivityToTop(getActivity(), PersonalFocusActivity.class, bundle);
                break;

            case R.id.rl_fans://粉丝
                bundle = new Bundle();
                bundle.putString(ConstantKey.INTENT_KEY_ID, BaseApplication.getInstance().getUserInfoBean().getId());
                IntentUtil.gotoActivityToTop(getActivity(), PersonalFansActivity.class, bundle);
                break;
        }
    }

    public void signToast(String msg) {
        Toast toast = new Toast(mActivity);
        View view = View.inflate(mActivity, R.layout.self_toast, null);
        TextView tv = view.findViewById(R.id.tv_sign_success);
        tv.setText(msg);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 70);
        toast.show();
    }

    /**
     * @param loginUser
     *         登录用户的id
     * @param userId
     *         要查询的用户id
     */
    private void getUserInfo(final String loginUser, String userId) {
        Map<String, String> map = new TreeMap<>();
        map.put("loginUser", loginUser);
        map.put("userId", userId);
        RetrofitUtils.getInstance().normalGet(ConfigServer.SERVER_API_URL + ConfigServer.METHOD_QUARYUSERS, map, new JsonCallBack() {
            @Override
            public void next(String response) {
                Log.e("tanjun", response);
                MainPageBean bean = new Gson().fromJson(response, MainPageBean.class);
                MainPageBean.DatasBean datasBean = bean.getDatas();
                //用户名
                tv_third_name.setText(datasBean.getRealName());
                //帖子
                tv_publish.setText(datasBean.getArticleCount());
                tv_focus_count.setText(datasBean.getFollowing());
                tv_follow_count.setText(datasBean.getFollower());

            }

            @Override
            public void error(Throwable e) {

            }

            @Override
            public void startLoading() {

            }

            @Override
            public void closeLoading() {

            }
        });
    }

    /** 体力 经验 等级 */
    private void getValue() {
        Map<String, String> map = new TreeMap<>();
        map.put("userId", BaseApplication.getInstance().getUserInfoBean().getId());
        map.put("userPwd", BaseApplication.getInstance().getUserInfoBean().getUserPwd());
        RetrofitUtils.getInstance().normalGet(ConfigServer.SERVER_API_URL + ConfigServer.METHOD_POPERTYONAPP, map, new JsonCallBack() {
            @Override
            public void next(String response) {
                ValueBean bean = new Gson().fromJson(response, ValueBean.class);
                ValueBean.DatasBean datasBean = bean.getDatas();
                String level = datasBean.getRating();
                tv_level.setText("LV." + level);
                /** 体力100 + ( 5 * 等级) */
                tv_power.setText("体力值：" + datasBean.getGrade() + "/" + String.valueOf(100 + Integer.parseInt(level) * 5));
                tv_experience.setText("经验值：" + datasBean.getExperience() + "/" + String.valueOf(100 + Integer.parseInt(level) * 5));
            }

            @Override
            public void error(Throwable e) {

            }

            @Override
            public void startLoading() {

            }

            @Override
            public void closeLoading() {

            }
        });
    }

    /**
     * 是否已签到
     */
    private void checkSignIn() {
        Map<String, String> map = new TreeMap<>();
        map.put("userId", BaseApplication.getInstance().getUserInfoBean().getId());
        map.put("userPwd", BaseApplication.getInstance().getUserInfoBean().getUserPwd());
        RetrofitUtils.getInstance().normalGet(ConfigServer.SERVER_API_URL + ConfigServer.METHOD_DETERMINHASSIGNIN, map, new JsonCallBack() {
            @Override
            public void next(String response) {
                CheckSignInBean bean = new Gson().fromJson(response, CheckSignInBean.class);
                // 0为未签到， -1 为已签到
                int code = Integer.parseInt(bean.getCode());
                if (code == 0) {
                    tv_sign.setText("签到");
                } else if (code == 1) {
                    tv_sign.setText("已签到");
                    //ToastUtil.showToast(mActivity, "已签到，");
                    rl_sign.setEnabled(false);

                }
            }

            @Override
            public void error(Throwable e) {

            }

            @Override
            public void startLoading() {

            }

            @Override
            public void closeLoading() {

            }
        });
    }

    /** 签到 */
    private void signIn() {
        Map<String, String> map = new TreeMap<>();
        map.put("userId", BaseApplication.getInstance().getUserInfoBean().getId());
        map.put("userPwd", BaseApplication.getInstance().getUserInfoBean().getUserPwd());
        RetrofitUtils.getInstance().normalGet(ConfigServer.SERVER_API_URL + ConfigServer.METHOD_SIGNIN, map, new JsonCallBack() {
            @Override
            public void next(String response) {
                Log.e("tanjun", response);
                signToast("签到成功，已向您钱包发射0.5HUI");
            }

            @Override
            public void error(Throwable e) {

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
