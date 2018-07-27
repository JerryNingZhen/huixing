package com.android.base.utils.picasso;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.RSRuntimeException;

import com.android.base.utils.glide.FastBlur;
import com.android.base.utils.glide.RSBlur;
import com.squareup.picasso.Transformation;

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
public class CropTransformation implements Transformation {

    private int mWidth;
    private int mHeight;
    private CropType cropType;
    private int color;// 蒙层颜色 RGB Color.argb(100, 255, 255, 255)
    private int blur;//高斯模糊 模糊程度
    private int maskId;// 根据图片资源形状处理bitmap 图片id
    private float aspectRatio = 0;// 裁剪成长方形 宽高比

    public enum CropType {
        BLUR,//高斯模糊
        GRAY,//黑白处理
        COLOR,//颜色蒙层
        RECTANGLE,//裁剪成长方形
        SQUARE,//裁剪成正方形
        MASK,//根据图片资源形状处理bitmap
        CIRCLE//裁剪成圆形
    }


    public CropTransformation(CropType cropType) {
        this.cropType = cropType;
    }

    //    public CropTransformation(CropType cropType, int color, int blur) {
    //        this.color = color;
    //        this.cropType = cropType;
    //        this.blur = blur;
    //    }

    private Context context;

    public CropTransformation(Context context, CropType cropType, int color, int blur, int maskId, float aspectRatio) {
        this.color = color;
        this.cropType = cropType;
        this.blur = blur;
        this.context = context;
        this.maskId = maskId;
        this.aspectRatio = aspectRatio;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        switch (cropType) {
            case SQUARE://
                int size = Math.min(source.getWidth(), source.getHeight());

                mWidth = (source.getWidth() - size) / 2;
                mHeight = (source.getHeight() - size) / 2;

                Bitmap bitmap = Bitmap.createBitmap(source, mWidth, mHeight, size, size);
                if (bitmap != source) {
                    source.recycle();
                }

                return bitmap;
            case CIRCLE://
                size = Math.min(source.getWidth(), source.getHeight());

                mWidth = (source.getWidth() - size) / 2;
                mHeight = (source.getHeight() - size) / 2;

                bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(bitmap);
                Paint paint = new Paint();
                BitmapShader shader =
                        new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
                if (mWidth != 0 || mHeight != 0) {
                    // source isn't square, move viewport to center
                    Matrix matrix = new Matrix();
                    matrix.setTranslate(-mWidth, -mHeight);
                    shader.setLocalMatrix(matrix);
                }
                paint.setShader(shader);
                paint.setAntiAlias(true);

                float r = size / 2f;
                canvas.drawCircle(r, r, r, paint);
                source.recycle();

                return bitmap;

            case RECTANGLE://
                if (aspectRatio > 0) {
                    if (aspectRatio == 1) {
                        size = Math.min(source.getWidth(), source.getHeight());

                        mWidth = (source.getWidth() - size) / 2;
                        mHeight = (source.getHeight() - size) / 2;

                        bitmap = Bitmap.createBitmap(source, mWidth, mHeight, size, size);
                        if (bitmap != source) {
                            source.recycle();
                        }

                        return bitmap;
                    }

                    float sourceRatio = (float) source.getWidth() / (float) source.getHeight();// 获取原始狂高比例
                    if (sourceRatio > aspectRatio) {
                        mHeight = source.getHeight();
                    } else {
                        mWidth = source.getWidth();
                    }

                    if (mWidth != 0) {
                        mHeight = (int) ((float) mWidth / aspectRatio);
                    } else {
                        if (mHeight != 0) {
                            mWidth = (int) ((float) mHeight * aspectRatio);
                        }
                    }
                } else {
                    mWidth = source.getWidth();
                    mHeight = source.getHeight();
                }

                float left = (source.getWidth() - mWidth) / 2;
                float top = (source.getHeight() - mHeight) / 2;
                Rect sourceRect = new Rect((int) left, (int) top, (int) (left + mWidth), (int) (top + mHeight));
                Rect targetRect = new Rect(0, 0, mWidth, mHeight);

                bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);

                canvas = new Canvas(bitmap);
                canvas.drawBitmap(source, sourceRect, targetRect, null);
                source.recycle();
                return bitmap;

            case BLUR://
                mWidth = source.getWidth();
                mHeight = source.getHeight();
                bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);

                canvas = new Canvas(bitmap);
                //                canvas.scale(1 / (float) mSampling, 1 / (float) mSampling);
                paint = new Paint();
                paint.setFlags(Paint.FILTER_BITMAP_FLAG);
                canvas.drawBitmap(source, 0, 0, paint);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    try {
                        bitmap = RSBlur.blur(context, bitmap, blur);
                    } catch (RSRuntimeException e) {
                        bitmap = FastBlur.blur(bitmap, blur, true);
                    }
                } else {
                    bitmap = FastBlur.blur(bitmap, blur, true);
                }
                source.recycle();

                return bitmap;
            case GRAY://
                mWidth = source.getWidth();
                mHeight = source.getHeight();
                bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);

                canvas = new Canvas(bitmap);
                ColorMatrix saturation = new ColorMatrix();
                saturation.setSaturation(0f);
                paint = new Paint();
                paint.setColorFilter(new ColorMatrixColorFilter(saturation));
                canvas.drawBitmap(source, 0, 0, paint);
                source.recycle();

                return bitmap;

            case COLOR://
                mWidth = source.getWidth();
                mHeight = source.getHeight();
                bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);

                canvas = new Canvas(bitmap);
                paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
                canvas.drawBitmap(source, 0, 0, paint);
                source.recycle();

                return bitmap;

            case MASK:
                mWidth = source.getWidth();
                mHeight = source.getHeight();

                Bitmap result = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);

                Drawable drawable = context.getApplicationContext().getResources().getDrawable(maskId);
                if (drawable == null) {
                    throw new IllegalArgumentException("maskId is invalid");
                }

                canvas = new Canvas(result);
                drawable.setBounds(0, 0, mWidth, mHeight);
                drawable.draw(canvas);
                paint = new Paint();
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(source, 0, 0, paint);

                source.recycle();

                return result;
            default:// 默认
                break;
        }

        return null;
    }

    @Override
    public String key() {
        return "CropTransformation(width=" + mWidth +
                ", height=" + mHeight +
                ", cropType=" + cropType +
                ", blur=" + blur +
                ", maskId=" + maskId +
                ", aspectRatio=" + aspectRatio +
                ", color=" + color + ")";
    }
}
