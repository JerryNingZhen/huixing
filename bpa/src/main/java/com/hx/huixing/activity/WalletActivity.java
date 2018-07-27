package com.hx.huixing.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.base.BaseApplication;
import com.android.base.configs.ConfigServer;
import com.android.base.utils.PreferencesUtil;
import com.android.base.widget.TitleView;
import com.google.gson.Gson;
import com.hx.huixing.R;
import com.hx.huixing.activityMvp.BasePresenter;
import com.hx.huixing.bean.CoinBean;
import com.hx.huixing.common.net.JsonCallBack;
import com.hx.huixing.common.net.RetrofitUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * <br> Description 我的钱包
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.activity
 * <br> Date: 2018/7/19
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class WalletActivity extends BaseActivity implements View.OnClickListener {

    /** 标题 */
    private TitleView title_view;
    /** 钱 */
    private TextView tv_value;
    /** 币天 数 */
    private TextView tv_bitian_value;
    private TextView tv_type;
    /** 提币按钮 */
    private Button btn_get_coin;
    /** 什么是币天 */
    private TextView tv_question;
    /** 交易记录 */
    private TextView tv_exchange_record;
    /** 收款码 */
    private TextView tv_money_code;

    private String coinChName;
    private String coinName;
    private String totalCount;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void findViews() {
        title_view = findViewById(R.id.title_view);
        tv_type = findViewById(R.id.tv_type);
        tv_value = findViewById(R.id.tv_value);
        btn_get_coin = findViewById(R.id.btn_get_coin);
        tv_question = findViewById(R.id.tv_question);
        tv_exchange_record = findViewById(R.id.tv_exchange_record);
        tv_money_code = findViewById(R.id.tv_money_code);
    }

    @Override
    protected void initGetData() {
        queryChainCoinBanlaceDetail();

    }

    @Override
    protected void init() {
        title_view.setLeftBtnImg();
        title_view.setTitle(R.string.my_wallet);
    }

    @Override
    protected void widgetListener() {
        btn_get_coin.setOnClickListener(this); //提币
        tv_question.setOnClickListener(this); //什么是币天
        tv_exchange_record.setOnClickListener(this);//交易记录
        tv_money_code.setOnClickListener(this);//收款码

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

     /** 点击 */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get_coin:

                break;

            case R.id.tv_question:

                break;

            case R.id.tv_exchange_record:

                break;

            case R.id.tv_money_code:

                break;
        }
    }

    private void queryChainCoinBanlaceDetail(){
        Map<String, String> map = new TreeMap<>();
        map.put("creator", BaseApplication.getInstance().getUserInfoBean().getId());
        map.put("coin_id", "1"); //1是HUI
        RetrofitUtils.getInstance().normalGet(ConfigServer.SERVER_API_URL + ConfigServer.METHOD_QUERYCHAINCOINBANLACEDETAIL,
                map, new JsonCallBack() {
                    @Override
                    public void next(String response) {
                        Log.e("tanjun", response);
                        CoinBean bean  = new Gson().fromJson(response, CoinBean.class);
                        /** 名称 */
                        tv_type.setText(bean.getDatas().getCoinName());
                        /** 钱总数 */
                        String value = bean.getDatas().getTotalCount();

                        if (TextUtils.isEmpty(value)){
                            tv_value.setText("0");
                        }else {
                            tv_value.setText(value);
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
