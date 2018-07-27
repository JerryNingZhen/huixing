package com.android.base.utils.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.hx.huixing.R;
import com.android.base.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/**
 * Glide图片加载
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class GlideUtil {


    /**
     * 加载图片
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016/5/9 17:36
     * <br> UpdateTime: 2016/5/9 17:36
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文
     * @param imgPath
     *         路径
     * @param imageView
     *         控件ImageView
     * @param options
     *         RequestOptions
     */
    public static void loadImage(Context context, String imgPath, ImageView imageView, RequestOptions options) {
        Glide.with(context)
                //.asBitmap()//强制指定加载静态图片
                //.asGif()//强制指定加载动态图片
                //.asFile()//强制指定文件格式的加载
                //.asDrawable()//强制指定Drawable格式的加载
                .load(imgPath)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                        LogUtil.i("图片加载失败");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                        //LogUtil.i("图片加载成功");
                        return false;
                    }
                })
                .into(imageView);

    }

    //    /**
    //     * 加载图片
    //     * <p>
    //     * <br> Version: 1.0.0
    //     * <br> CreateTime:
    //     * <br> UpdateTime
    //     * <br> CreateAuthor: 叶青
    //     * <br> UpdateAuthor: 叶青
    //     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
    //     *
    //     * @param context
    //     *         上下文
    //     * @param imgPathx
    //     *         路径
    //     * @param imageView
    //     *         控件ImageView
    //     */
    //    public static void loadImage(Context context, String imgPath, ImageView imageView) {
    //        loadImage(context, imgPath, imageView, R.drawable.img_default_grey_base);
    //    }
    //
    //    /**
    //     * 加载图片
    //     * <p>
    //     * <br> Version: 1.0.0
    //     * <br> CreateTime: 2016/5/9 17:36
    //     * <br> UpdateTime: 2016/5/9 17:36
    //     * <br> CreateAuthor: 叶青
    //     * <br> UpdateAuthor: 叶青
    //     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
    //     *
    //     * @param context
    //     *         上下文
    //     * @param imgPath
    //     *         路径
    //     * @param imageView
    //     *         控件ImageView
    //     * @param resDefaultId
    //     *         占位图 默认图片 一般可以设置成一个加载中的进度GIF图
    //     */
    //    public static void loadImage(Context context, String imgPath, ImageView imageView, int resDefaultId) {
    //
    //        if (!Util.isOnMainThread()) {
    //            return;
    //        }
    //
    //        RequestOptions options = getRequestOptions().centerCrop()
    //                .placeholder(resDefaultId);//
    //
    //        loadImage(context, imgPath, imageView, options);
    //    }
    //
    //    /**
    //     * 设置圆角图片
    //     * <p>
    //     * <br> Version: 1.0.0
    //     * <br> CreateTime: 2016/5/9 17:36
    //     * <br> UpdateTime: 2016/5/9 17:36
    //     * <br> CreateAuthor:  叶青
    //     * <br> UpdateAuthor:  叶青
    //     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
    //     *
    //     * @param context
    //     *         上下文
    //     * @param imgPath
    //     *         图片路径
    //     * @param imageView
    //     *         ImageView
    //     */
    //    public static void loadRoundImage(Context context, String imgPath, ImageView imageView) {
    //        loadRoundImage(context, imgPath, imageView, R.drawable.img_default_grey_base);
    //    }
    //
    //    /**
    //     * 设置圆角图片
    //     * <p>
    //     * <br> Version: 1.0.0
    //     * <br> CreateTime: 2016/5/9 17:36
    //     * <br> UpdateTime: 2016/5/9 17:36
    //     * <br> CreateAuthor:  叶青
    //     * <br> UpdateAuthor:  叶青
    //     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
    //     *
    //     * @param context
    //     *         上下文
    //     * @param imgPath
    //     *         图片路径
    //     * @param imageView
    //     *         ImageView
    //     * @param resDefaultId
    //     *         占位图 默认图片 一般可以设置成一个加载中的进度GIF图
    //     */
    //    public static void loadRoundImage(Context context, String imgPath, ImageView imageView, int resDefaultId) {
    //
    //        if (!Util.isOnMainThread()) {
    //            return;
    //        }
    //
    //        RequestOptions options = getRequestOptions()
    //                .transforms(new CropTransformation(CropTransformation.CropType.SQUARE), new RoundedCornersTransformation(ScreenUtil.dip2px(20), 0))//// 多重变换
    //                .placeholder(resDefaultId);//
    //
    //        loadImage(context, imgPath, imageView, options);
    //    }
    //
    //    /**
    //     * 设置圆角图片
    //     * <p>
    //     * <br> Version: 1.0.0
    //     * <br> CreateTime: 2016/5/9 17:36
    //     * <br> UpdateTime: 2016/5/9 17:36
    //     * <br> CreateAuthor:  叶青
    //     * <br> UpdateAuthor:  叶青
    //     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
    //     *
    //     * @param context
    //     *         上下文
    //     * @param imgPath
    //     *         图片路径
    //     * @param imageView
    //     *         ImageView
    //     */
    //    public static void loadCircleImage(Context context, String imgPath, ImageView imageView) {
    //        loadCircleImage(context, imgPath, imageView, R.drawable.default_portrait_grey);
    //    }
    //
    //    /**
    //     * 设置圆角图片(加载头像)
    //     * <p>
    //     * <br> Version: 1.0.0
    //     * <br> CreateTime: 2016/5/9 17:36
    //     * <br> UpdateTime: 2016/5/9 17:36
    //     * <br> CreateAuthor: 叶青
    //     * <br> UpdateAuthor: 叶青
    //     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
    //     *
    //     * @param context
    //     *         上下文
    //     * @param imgPath
    //     *         图片路径
    //     * @param imageView
    //     *         ImageView
    //     * @param resDefaultId
    //     *         占位图 默认图片 一般可以设置成一个加载中的进度GIF图
    //     */
    //    public static void loadCircleImage(Context context, String imgPath, ImageView imageView, int resDefaultId) {
    //
    //        //ImageView的scaleType设置不当，导致使用Glide时出现OOM，不能使用fitXY
    //        if (!Util.isOnMainThread()) {
    //            return;
    //        }
    //
    //        RequestOptions options = getRequestOptions()
    //                .transform(new CropTransformation(CropTransformation.CropType.CIRCLE))
    //                .placeholder(resDefaultId);//
    //
    //        loadImage(context, imgPath, imageView, options);
    //    }

    public static RequestOptions getRequestOptions() {
        return new RequestOptions()
                .centerCrop()//
                //                .fitCenter() //ImageView的scaleType设置不当，导致使用Glide时出现OOM，不能使用fitXY
                //                .circleCrop()//图片变换(圆角等)
                .placeholder(R.drawable.img_default_grey_base)//
                .error(R.drawable.img_load_error_base)//
                .fallback(R.drawable.img_load_error_base)
                .dontAnimate()// 不使用Glide的默认动画
                // .override(200, 100)//指定图片大小 .override(Target.SIZE_ORIGINAL); 加载一张图片的原始尺寸
                //                .skipMemoryCache(true)//禁止内存缓存
                // DiskCacheStrategy.NONE： 表示不缓存任何内容。
                // DiskCacheStrategy.DATA： 表示只缓存原始图片。
                // DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
                // DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
                // DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                //                .transforms(new CropTransformation(CropTransformation.CropType.SQUARE), new RoundedCornersTransformation(ScreenUtil.dip2px(20), 0))//// 多重变换
                //                .transform(new CropTransformation(CropTransformation.CropType.SQUARE))//// 多重变换
                .priority(Priority.HIGH);
    }

}