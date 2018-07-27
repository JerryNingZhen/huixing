package com.android.base.executor;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.animation.LinearInterpolator;

/**
 * 仿IOS 加载圆圈
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2018/6/18
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class IosLoadingDrawable extends Drawable implements Animatable, ValueAnimator.AnimatorUpdateListener {

    private int mWidth = 0;
    private int mHeight = 0;
    private int mProgressDegree = 0;
    private ValueAnimator mValueAnimator;
    private Path mPath = new Path();

    public IosLoadingDrawable() {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xffaaaaaa);

        mValueAnimator = ValueAnimator.ofInt(30, 3600);
        mValueAnimator.setDuration(10000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int value = (int) animation.getAnimatedValue();
        mProgressDegree = 30 * (value / 30);
        final Drawable drawable = IosLoadingDrawable.this;
        drawable.invalidateSelf();
    }

    //<editor-fold desc="Drawable">
    @Override
    public void draw(@NonNull Canvas canvas) {
        final Drawable drawable = IosLoadingDrawable.this;
        final Rect bounds = drawable.getBounds();
        final int width = bounds.width();
        final int height = bounds.height();
        final int r = Math.max(1, width / 20);

        if (mWidth != width || mHeight != height) {
            mPath.reset();
            mPath.addCircle(width - r, height / 2, r, Path.Direction.CW);
            mPath.addRect(width - 5 * r, height / 2 - r, width - r, height / 2 + r, Path.Direction.CW);
            mPath.addCircle(width - 5 * r, height / 2, r, Path.Direction.CW);
            mWidth = width;
            mHeight = height;
        }

        canvas.save();
        canvas.rotate(mProgressDegree, (width) / 2, (height) / 2);
        for (int i = 0; i < 12; i++) {// 12片
            mPaint.setAlpha((i + 5) * 0x11);// 设置画笔 透明度
            canvas.rotate(30, (width) / 2, (height) / 2);
            canvas.drawPath(mPath, mPaint);
        }
        canvas.restore();
    }

    //</editor-fold>

    @Override
    public void start() {
        if (!mValueAnimator.isRunning()) {
            mValueAnimator.addUpdateListener(this);
            mValueAnimator.start();
        }
    }

    @Override
    public void stop() {
        if (mValueAnimator.isRunning()) {
            mValueAnimator.removeAllListeners();
            mValueAnimator.removeAllUpdateListeners();
            mValueAnimator.cancel();
        }
    }

    @Override
    public boolean isRunning() {
        return mValueAnimator.isRunning();
    }


    private Paint mPaint = new Paint();

    public void setColor(int color) {
        mPaint.setColor(color);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
