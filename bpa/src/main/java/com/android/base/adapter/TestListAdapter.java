package com.android.base.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.hx.huixing.R;
import com.android.base.activity.ImageBrowseActivity;
import com.android.base.configs.ConstantKey;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.ScreenUtil;
import com.android.base.utils.glide.GlideUtil;
import com.android.base.utils.picasso.PicassoUtil;

import java.util.ArrayList;

/**
 * 测试 基类适配器
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class TestListAdapter extends SimpleBaseAdapter<String> {

    public TestListAdapter(Context context, ArrayList<String> data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_test_listview;
    }

    @Override
    public View getItemView(final int position, View convertView, ViewHolder holder) {
        ImageView img_round = holder.getView(R.id.img_portrait_round);
        ImageView img_circle = holder.getView(R.id.img_portrait_circle);

        img_round.setScaleType(ScaleType.CENTER_CROP);
        img_circle.setScaleType(ScaleType.CENTER_CROP);

        // 控制ImageView的宽高
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) img_round.getLayoutParams();
        layoutParams.width = ScreenUtil.getScreenWidthPx() / 2;
        layoutParams.height = ScreenUtil.getScreenWidthPx() / 2;
        layoutParams = (LayoutParams) img_circle.getLayoutParams();
        layoutParams.width = ScreenUtil.getScreenWidthPx() / 2;
        layoutParams.height = (int) (ScreenUtil.getScreenWidthPx() / 2);

        // TODO 设置图片形状
        // img.setPhoneModel(RoundImageView.TYPE_ROUND);
        // img.setBorderRadius(15);

        // // 解析本地图片，获得图片尺寸
        // String localImagePath = StringUtil.getUserLocalImagePath(dataList.get(position));
        // BitmapUtil.height2Width(localImagePath);

        // TODO 自己的加载图片
        // AsyncImageSetter.setImage(img, dataList.get(position), R.drawable.img_default, 100, false, 1, false);
        // TODO 加载默认图片.diskCacheStrategy(DiskCacheStrategy.ALL)（避免同一张图片加载两次）
        // Glide.with(context).load(dataList.get(position)).into(img);
        // TODO 加载圆形图片
        // Glide.with(context).load(dataList.get(position)).transform(new GlideCircleTransform(context)).into(img);
        // TODO 加载圆角图片
        // Glide.with(context).load(dataList.get(position)).transform(new GlideRoundTransform(context)).into(img);
        // TODO 加载圆角图片

        //        GlideUtil.loadImage(context, dataList.get(position), img_round, GlideUtil.getRequestOptions());
        PicassoUtil.loadImage(context, dataList.get(position), img_round);
        //        PicassoUtil.loadImage(context, dataList.get(position), img_round, R.drawable.img_default_grey, R.drawable.img_default_grey, new com.android.base.utils.picasso.CropTransformation(com.android.base.utils.picasso.CropTransformation.CropType.CIRCLE));


        //        RequestOptions options = GlideUtil.getRequestOptions().transform(new CropTransformation(CropTransformation.CropType.CIRCLE));
        //        RequestOptions options = GlideUtil.getRequestOptions().transform(new CropTransformation(CropTransformation.CropType.COLOR, -1, Color.argb(100, 255, 255, 255), -1, -1));
        //        GlideUtil.loadImage(context, dataList.get(Math.abs(dataList.size() - position - 1)), img_circle, options);
        GlideUtil.loadImage(context, dataList.get(Math.abs(dataList.size() - position - 1)), img_circle, GlideUtil.getRequestOptions());
        //        PicassoUtil.loadImage(context, dataList.get(Math.abs(dataList.size() - position - 1)), img_circle);

        // TODO glide缓存清理
        // Glide.get(context).clearDiskCache();
        // Glide.get(context).clearMemory();
        // ImageView的scaleType设置不当，导致使用Glide时出现OOM，不能使用fitXY

        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(ConstantKey.INTENT_KEY_DATAS, dataList);
                bundle.putInt(ConstantKey.INTENT_KEY_POSITION, position);
                IntentUtil.gotoActivity(context, ImageBrowseActivity.class, bundle);
            }
        });
        return convertView;
    }

}