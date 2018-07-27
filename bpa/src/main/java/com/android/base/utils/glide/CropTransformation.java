package com.android.base.utils.glide;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.RSRuntimeException;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import static android.R.attr.radius;

/**
 * 图片裁剪
 * BLUR,//高斯模糊
 * GRAY,//黑白处理
 * COLOR,//颜色蒙层
 * RECTANGLE,//裁剪成长方形
 * SQUARE,//裁剪成正方形
 * MASK,//根据图片资源形状处理bitmap
 * CIRCLE//裁剪成圆形
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2017/12/29
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class CropTransformation extends BitmapTransformation {

    public enum CropType {
        BLUR,//高斯模糊
        GRAY,//黑白处理
        COLOR,//颜色蒙层
        RECTANGLE,//裁剪成长方形
        SQUARE,//裁剪成正方形
        MASK,//根据图片资源形状处理bitmap
        CIRCLE//裁剪成圆形
    }

    private int width;//裁剪的宽度
    private int height;//裁剪的高度

    private CropType cropType;// 裁剪类型

    private float aspectRatio = 0;// 裁剪成长方形 宽高比
    private int color;// 蒙层颜色 RGB Color.argb(100, 255, 255, 255)
    private int blur;//高斯模糊 模糊程度

    private static Paint paint1 = new Paint();
    private int maskId;// 根据图片资源形状处理bitmap 图片id

    static {
        paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    public CropTransformation(CropType cropType) {
        this.cropType = cropType;
    }

    /**
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/12/29 22:10
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/12/29 22:10
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param cropType
     *         CropType裁剪类型
     * @param aspectRatio
     *         裁剪成长方形 宽高比
     * @param color
     *         蒙层颜色 RGB Color.argb(100, 255, 255, 255)
     * @param blur
     *         //高斯模糊 模糊程度
     */
    public CropTransformation(CropType cropType, float aspectRatio, int color, int blur, int maskId) {
        this.aspectRatio = aspectRatio;
        this.cropType = cropType;
        this.color = color;
        this.blur = blur;
        this.maskId = maskId;
    }

    @Override
    protected Bitmap transform(Context context, BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        switch (cropType) {
            case SQUARE://
                int size = Math.max(outWidth, outHeight);
                return TransformationUtils.centerCrop(pool, toTransform, size, size);

            case CIRCLE://
                return TransformationUtils.circleCrop(pool, toTransform, outWidth, outHeight);

            case RECTANGLE://
                if (aspectRatio > 0) {
                    if (aspectRatio == 1) {
                        size = Math.max(outWidth, outHeight);
                        return TransformationUtils.centerCrop(pool, toTransform, size, size);
                    }

                    float sourceRatio = (float) toTransform.getWidth() / (float) toTransform.getHeight();// 获取原始狂高比例
                    if (sourceRatio > aspectRatio) {
                        height = toTransform.getHeight();
                    } else {
                        width = toTransform.getWidth();
                    }

                    if (width != 0) {
                        height = (int) ((float) width / aspectRatio);
                    } else {
                        if (height != 0) {
                            width = (int) ((float) height * aspectRatio);
                        }
                    }
                } else {
                    width = toTransform.getWidth();
                    height = toTransform.getHeight();
                }

                float left = (toTransform.getWidth() - width) / 2;
                float top = (toTransform.getHeight() - height) / 2;
                Rect sourceRect = new Rect((int) left, (int) top, (int) (left + width), (int) (top + height));
                Rect targetRect = new Rect(0, 0, width, height);

                Bitmap.Config config = toTransform.getConfig() != null ? toTransform.getConfig() : Bitmap.Config.ARGB_8888;
                Bitmap bitmap = pool.get(width, height, config);
                bitmap.setHasAlpha(true);

                Canvas canvas = new Canvas(bitmap);
                canvas.drawBitmap(toTransform, sourceRect, targetRect, null);

                return bitmap;

            case BLUR://
                if (blur <= 0) {
                    blur = 25;
                }
                width = toTransform.getWidth();
                height = toTransform.getHeight();
                //                if (sampling <= 0) {
                //                    sampling = DEFAULT_DOWN_SAMPLING;
                //                }
                //                int scaledWidth = width / sampling;
                //                int scaledHeight = height / sampling;
                //
                //                Bitmap bitmap = pool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
                bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888);
                canvas = new Canvas(bitmap);
                //                canvas.scale(1 / (float) sampling, 1 / (float) sampling);
                Paint paint = new Paint();
                paint.setFlags(Paint.FILTER_BITMAP_FLAG);
                canvas.drawBitmap(toTransform, 0, 0, paint);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    try {
                        bitmap = RSBlur.blur(context, bitmap, blur);
                    } catch (RSRuntimeException e) {
                        bitmap = FastBlur.blur(bitmap, blur, true);
                    }
                } else {
                    bitmap = FastBlur.blur(bitmap, radius, true);
                }

                return bitmap;

            case GRAY://
                width = toTransform.getWidth();
                height = toTransform.getHeight();

                config = toTransform.getConfig() != null ? toTransform.getConfig() : Bitmap.Config.ARGB_8888;
                bitmap = pool.get(width, height, config);
                //        bitmap = BitmapUtils.gray(toTransform, bitmap);
                canvas = new Canvas(bitmap);
                ColorMatrix saturation = new ColorMatrix();
                saturation.setSaturation(0f);
                paint = new Paint();
                paint.setColorFilter(new ColorMatrixColorFilter(saturation));
                canvas.drawBitmap(toTransform, 0, 0, paint);

                return bitmap;

            case COLOR://
                width = toTransform.getWidth();
                height = toTransform.getHeight();

                config = toTransform.getConfig() != null ? toTransform.getConfig() : Bitmap.Config.ARGB_8888;
                bitmap = pool.get(width, height, config);
                //    bitmap = BitmapUtils.colorFilter(toTransform,bitmap,color);
                canvas = new Canvas(bitmap);
                paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
                canvas.drawBitmap(toTransform, 0, 0, paint);

                return bitmap;

            case MASK:
                width = toTransform.getWidth();
                height = toTransform.getHeight();

                bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888);
                bitmap.setHasAlpha(true);
                Drawable mask = context.getApplicationContext().getResources().getDrawable(maskId);
                //    Drawable mask = Utils.getMaskDrawable(context.getApplicationContext(), maskId);

                canvas = new Canvas(bitmap);
                mask.setBounds(0, 0, width, height);
                mask.draw(canvas);
                paint = new Paint();
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(toTransform, 0, 0, paint);

                return bitmap;
            default:// 默认
                break;
        }
        return null;
        //        if (CropType.SQUARE.equals(cropType)) {
        //            int size = Math.max(outWidth, outHeight);
        //            return TransformationUtils.centerCrop(pool, toTransform, size, size);
        //
        //        } else if (CropType.CIRCLE.equals(cropType)) {
        //            return TransformationUtils.circleCrop(pool, toTransform, outWidth, outHeight);
        //
        //        } else {
        //            if (aspectRatio > 0) {
        //                if (aspectRatio == 1) {
        //                    int size = Math.max(outWidth, outHeight);
        //                    return TransformationUtils.centerCrop(pool, toTransform, size, size);
        //                }
        //
        //                float sourceRatio = (float) toTransform.getWidth() / (float) toTransform.getHeight();// 获取原始狂高比例
        //                if (sourceRatio > aspectRatio) {
        //                    height = toTransform.getHeight();
        //                } else {
        //                    width = toTransform.getWidth();
        //                }
        //
        //                if (width != 0) {
        //                    height = (int) ((float) width / aspectRatio);
        //                } else {
        //                    if (height != 0) {
        //                        width = (int) ((float) height * aspectRatio);
        //                    }
        //                }
        //            } else {
        //                width = toTransform.getWidth();
        //                height = toTransform.getHeight();
        //            }
        //
        //            float left = (toTransform.getWidth() - width) / 2;
        //            float top = (toTransform.getHeight() - height) / 2;
        //            Rect sourceRect = new Rect((int) left, (int) top, (int) (left + width), (int) (top + height));
        //            Rect targetRect = new Rect(0, 0, width, height);
        //
        //            Bitmap.Config config = toTransform.getConfig() != null ? toTransform.getConfig() : Bitmap.Config.ARGB_8888;
        //            Bitmap bitmap = pool.get(width, height, config);
        //            bitmap.setHasAlpha(true);
        //
        //            Canvas canvas = new Canvas(bitmap);
        //            canvas.drawBitmap(toTransform, sourceRect, targetRect, null);
        //
        //            return bitmap;
        //        }
    }

    @Override
    public String key() {
        return "CropTransformation(aspectRatio=" + aspectRatio +
                ", width=" + width +
                ", height=" + height +
                ", blur=" + blur +
                ", maskId=" + maskId +
                ", cropType=" + cropType + ")";
    }

}

//package jp.wasabeef.glide.transformations;
//
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.RectF;
//import android.support.annotation.NonNull;
//
//import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
//import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
//
///**
// * 图片裁剪
// * <p>
// * <br> Author: 叶青
// * <br> Version: 6.0.0
// * <br> Date: 2017/12/29
// * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
// */
//public class CropTransformation extends BitmapTransformation {
//
//    public enum CropType {
//        TOP,
//        CENTER,
//        BOTTOM,
//        RECTANGLE,//裁剪成长方形
//        SQUARE,//裁剪成正方形
//        CIRCLE//裁剪成圆形
//    }
//
//    private int width;//裁剪的宽度
//    private int height;//裁剪的高度
//
//    private float aspectRatio;// 宽高比
//
//    private CropType cropType = CropType.CENTER;
//
//    public CropTransformation(CropType cropType) {
//        this.cropType = cropType;
//    }
//
//    public CropTransformation(int width, int height) {
//        this(width, height, CropType.CENTER);
//    }
//
//    public CropTransformation(int width, int height, CropType cropType) {
//        this.width = width;
//        this.height = height;
//        this.cropType = cropType;
//    }
//
//    @Override
//    protected Bitmap transform(Context context, BitmapPool pool,
//                               Bitmap toTransform, int outWidth, int outHeight) {
//
//        if (CropType.SQUARE.equals(cropType)) {
//            int size = Math.max(outWidth, outHeight);
//            return TransformationUtils.centerCrop(pool, toTransform, size, size);
//        } else if (CropType.CIRCLE.equals(cropType)) {
//            return TransformationUtils.circleCrop(pool, toTransform, outWidth, outHeight);
//        } else if (CropType.RECTANGLE.equals(cropType)) {
//            width = width == 0 ? toTransform.getWidth() : width;
//            height = height == 0 ? toTransform.getHeight() : height;
//
//            Bitmap.Config config = toTransform.getConfig() != null ? toTransform.getConfig() : Bitmap.Config.ARGB_8888;
//            Bitmap bitmap = pool.get(width, height, config);
//
//            bitmap.setHasAlpha(true);
//
//            float scaleX = (float) width / toTransform.getWidth();
//            float scaleY = (float) height / toTransform.getHeight();
//            float scale = Math.max(scaleX, scaleY);
//
//            float scaledWidth = scale * toTransform.getWidth();
//            float scaledHeight = scale * toTransform.getHeight();
//            float left = (width - scaledWidth) / 2;
//            float top = getTop(scaledHeight);
//            RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);
//
//            Canvas canvas = new Canvas(bitmap);
//            canvas.drawBitmap(toTransform, null, targetRect, null);
//
//            return bitmap;
//        }
//        return null;
//    }
//
//    @Override
//    public String key() {
//        return "CropTransformation(width=" + width + ", height=" + height + ", cropType=" + cropType
//                + ")";
//    }
//
//    private float getTop(float scaledHeight) {
//        switch (cropType) {
//            case TOP:
//                return 0;
//            case CENTER:
//                return (height - scaledHeight) / 2;
//            case BOTTOM:
//                return height - scaledHeight;
//            default:
//                return 0;
//        }
//    }
//}
