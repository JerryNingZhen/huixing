package com.hx.huixing.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.BaseApplication;
import com.android.base.activity.PersonalHomeActivity;
import com.android.base.adapter.SimpleBaseAdapter;
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
import com.hx.huixing.bean.FocusFanBean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * <br> Description 关注适配器
 * <br> Author: 谭俊
 * <br> PackageName com.tan.mvpdemo.activityMvp
 * <br> Date: 2018/8/12
 */
public class FocusFansAdapter extends SimpleBaseAdapter<FocusFanBean.DatasBean> {

    public FocusFansAdapter(Context context, ArrayList<FocusFanBean.DatasBean> datas) {
        super(context, datas);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_personal_focus;
    }

    @Override
    public View getItemView(final int position, View convertView, ViewHolder holder) {
        final FocusFanBean.DatasBean bean = dataList.get(position); //bean

        TextView txt_name = holder.getView(R.id.txt_name);//姓名
        ImageView round_image = holder.getView(R.id.round_image);//头像
        TextView txt_detail = holder.getView(R.id.txt_detail);//简介
        Button btn_add_care = holder.getView(R.id.btn_add_care); //是否已关注

        RequestOptions options = GlideUtil.getRequestOptions();
        options.transforms(new CropTransformation(CropTransformation.CropType.CIRCLE));//// 多重变换
        GlideUtil.loadImage(context, bean.getUserPic(), round_image, options.error(R.drawable.default_portrait_grey));

        txt_name.setText(bean.getRealName());
        txt_detail.setText( (bean.getPersonStringro()));
        if (bean.getFollowStatus().equals("1")) {
            btn_add_care.setText("已关注");
        } else {
            btn_add_care.setText("+关注");
        }

        /** 点击关注 */
        btn_add_care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attent(position);
            }
        });

        /** 进入他的主页 */
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstantKey.INTENT_KEY_ID, bean.getUserId());
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
        params.put("attentionId", dataList.get(position).getUserId());
        params.put("type", "1");
        params.put("password", BaseApplication.getInstance().getUserInfoBean().getUserPwd());
        params.put("creator", BaseApplication.getInstance().getUserInfoBean().getId());

        RequestExecutor.addTask(new BaseTask(context) {
            @Override
            public ResponseBean sendRequest() {
                if (dataList.get(position).getFollowStatus().equals("1")) {
                    params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_DELATTENT);
                } else {
                    params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_ATTENT);
                }
                return HttpOkBiz.getInstance().sendGet(params);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                //                hasFollowed = "";//  登录人对文章作者的关注状态 0为未关注，1为已关注
                if (dataList.get(position).getFollowStatus().equals("1")) {
                    dataList.get(position).setFollowStatus("0");
                } else {
                    dataList.get(position).setFollowStatus("1");
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
