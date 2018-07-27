package com.android.base.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.BaseApplication;
import com.android.base.activity.PersonalHomeActivity;
import com.android.base.bean.PersonalFocusBean;
import com.android.base.bean.ResponseBean;
import com.android.base.configs.ConfigServer;
import com.android.base.configs.ConstantKey;
import com.android.base.executor.BaseTask;
import com.android.base.executor.RequestExecutor;
import com.android.base.mvp.model.HttpOkBiz;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.ToastUtil;
import com.android.base.utils.glide.CropTransformation;
import com.android.base.utils.glide.GlideUtil;
import com.bumptech.glide.request.RequestOptions;
import com.hx.huixing.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * <br> Description 首页适配器
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.adapter
 * <br> Date: 2018/7/20
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class PersonalFocusAdapter extends SimpleBaseAdapter<PersonalFocusBean> {


    public PersonalFocusAdapter(Context context, ArrayList<PersonalFocusBean> data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_personal_focus;
    }

    @Override
    public View getItemView(final int position, View convertView, ViewHolder holder) {
        TextView txt_name = holder.getView(R.id.txt_name);//
        ImageView round_image = holder.getView(R.id.round_image);//摘要
        Button btn_add_care = holder.getView(R.id.btn_add_care); //文章插图

        TextView txt_detail = holder.getView(R.id.txt_detail);
        final PersonalFocusBean bean = dataList.get(position);

        RequestOptions options = GlideUtil.getRequestOptions();
        options.transforms(new CropTransformation(CropTransformation.CropType.CIRCLE));//// 多重变换
        GlideUtil.loadImage(context, bean.getUserPic(), round_image, options.error(R.drawable.default_portrait_grey));

        txt_name.setText(bean.getRealName());
        txt_detail.setText( (bean.getPersonIntro()));

        if (bean.getHasFollowed().equals("1")) {
            btn_add_care.setText("已关注");
        } else {
            btn_add_care.setText("+关注");
        }

        btn_add_care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attent(position);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstantKey.INTENT_KEY_ID, bean.getCreator());
                IntentUtil.gotoActivity(context, PersonalHomeActivity.class, bundle);
            }
        });

        return convertView;
    }

    /**
     * 关注
     */
    private void attent(final int position) {
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_ATTENT);
        //        attentionId:          // 要关注的对象id
        //        creator：             //  登录人id
        //        password：        //   登录人密码
        //        type:   1               //   传1
        params.put("attentionId", dataList.get(position).getCreator());
        params.put("type", "1");
        params.put("password", BaseApplication.getInstance().getUserInfoBean().getUserPwd());
        params.put("creator", BaseApplication.getInstance().getUserInfoBean().getId());

        RequestExecutor.addTask(new BaseTask(context) {
            @Override
            public ResponseBean sendRequest() {
                if (dataList.get(position).getHasFollowed().equals("1")) {
                    params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_DELATTENT);
                } else {
                    params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_ATTENT);
                }
                return HttpOkBiz.getInstance().sendGet(params);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                //                hasFollowed = "";//  登录人对文章作者的关注状态 0为未关注，1为已关注
                if (dataList.get(position).getHasFollowed().equals("1")) {
                    dataList.get(position).setHasFollowed("0");
                } else {
                    dataList.get(position).setHasFollowed("1");
                }
                notifyDataSetChanged();
                ToastUtil.showToast(context,result.getInfo());
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context,result.getInfo());
            }
        });
    }
}
