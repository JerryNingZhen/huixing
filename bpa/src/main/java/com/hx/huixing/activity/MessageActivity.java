package com.hx.huixing.activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.base.BaseApplication;
import com.android.base.configs.ConfigServer;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.ToastUtil;
import com.android.base.widget.TitleView;
import com.google.gson.Gson;
import com.hx.huixing.R;
import com.hx.huixing.activityMvp.BasePresenter;
import com.hx.huixing.activityMvp.contract.MainContract;
import com.hx.huixing.bean.CountBean;
import com.hx.huixing.common.net.JsonCallBack;
import com.hx.huixing.common.net.RetrofitUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * <br> Description 首页进来的消息 点赞和评论
 * <br> Author: 谭俊
 * <br> PackageName com.tan.mvpdemo.activityMvp
 * <br> Date: 2018/7/22
 */
public class MessageActivity extends BaseActivity implements View.OnClickListener {

    private TitleView title_view;

    private TextView tv_comment_count;//评论
    private TextView tv_thumb_up; //点赞
/*    private TextView tv_new_focus;//新关注
    private TextView tv_reward_count;//奖励
    private TextView tv_notice_count;//社区公告*/

    /** 显示个数 */
    private TextView tv_thumb_num; //点赞
    private TextView tv_comment_num; //评论
/*    private TextView tv_care_num;//新关注个数
    private TextView tv_reward_num;//奖励个数
    private TextView tv_notice_num;//社区公告个数*/


    @Override
    protected int getContentViewId() {
        return R.layout.activity_message;
    }

    @Override
    protected void findViews() {
        title_view = findViewById(R.id.title_view);

        tv_thumb_up = findViewById(R.id.tv_thumb_up);
        tv_comment_count = findViewById(R.id.tv_comment_count);
/*        tv_new_focus = findViewById(R.id.tv_new_focus);
        tv_reward_count = findViewById(R.id.tv_reward_count);
        tv_notice_count = findViewById(R.id.tv_notice_count);*/

        tv_thumb_num = findViewById(R.id.tv_thumb_num);
        tv_comment_num = findViewById(R.id.tv_comment_num);
/*        tv_care_num = findViewById(R.id.tv_care_num);
        tv_reward_num = findViewById(R.id.tv_reward_num);
        tv_notice_num = findViewById(R.id.tv_notice_num);*/

    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        countNotReadMessage(tv_thumb_num, tv_thumb_up, "2");
        countNotReadMessage(tv_comment_num, tv_comment_count, "3");
    }

    @Override
    protected void init() {
        title_view.setLeftBtnImg();
        title_view.setTitle(R.string.message);
    }

    @Override
    protected void widgetListener() {
        tv_thumb_up.setOnClickListener(this);
        tv_comment_count.setOnClickListener(this);
/*        tv_new_focus.setOnClickListener(this);
        tv_reward_count.setOnClickListener(this);
        tv_notice_count.setOnClickListener(this);*/
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_thumb_up://点赞
                Bundle bundle1 = new Bundle();
                bundle1.putString("type", "2");
                bundle1.putString("title", "获赞");
                IntentUtil.gotoActivity(this, ThumbUpListActivity.class, bundle1);
                break;

            case R.id.tv_comment_count://评论
                Bundle bundle2 = new Bundle();
                bundle2.putString("type", "3");
                bundle2.putString("title", "评论");
                IntentUtil.gotoActivity(this, ThumbUpListActivity.class, bundle2);
                break;
        }
    }


    /**
     *  要查看的消息的类型，传整数数组，
     * 2为点赞的未读消息，3为评论的未读消息
     */
    /**
     *
     * @param count 显示数量
     * @param text 要绑定的控件
     * @param type 类型
     */
    private void countNotReadMessage(final TextView count, final TextView text, String type){
        Map<String, String> map = new TreeMap<>();
        //map.put("userId", "42e7ce4d-c7ad-476b-8850-1a60bba0e64a");
        //map.put("userPwd", "32f913adb0951b14373e444c3c4cfcc9");
        map.put("userId", BaseApplication.getInstance().getUserInfoBean().getId());
        map.put("password", BaseApplication.getInstance().getUserInfoBean().getUserPwd());
        map.put("type", type);

        RetrofitUtils.getInstance().normalGet(ConfigServer.SERVER_API_URL + ConfigServer.METHOD_COUNTNOTREADMESSAGE, map, new JsonCallBack() {
            @Override
            public void next(String response) {
                CountBean bean = new Gson().fromJson(response, CountBean.class);
                if (TextUtils.isEmpty(bean.getDatas()) || Integer.parseInt(bean.getDatas()) == 0){
                    count.setVisibility(View.GONE);
                }else if (Integer.parseInt(bean.getDatas())>=99){
                    count.setVisibility(View.VISIBLE);
                    count.setText("99");//未读消息
                }else {
                    count.setVisibility(View.VISIBLE);
                    count.setText(bean.getDatas());//未读消息
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


}
