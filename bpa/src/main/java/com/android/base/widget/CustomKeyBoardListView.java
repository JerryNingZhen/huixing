package com.android.base.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ListView;

import com.android.base.utils.LogUtil;

/**
 * 自定义 ScrollView  实现输入框 显示隐藏 监听
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2017/10/19
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class CustomKeyBoardListView extends ListView {

    private onSizeChangedListener mChangedListener;

    public CustomKeyBoardListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomKeyBoardListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomKeyBoardListView(Context context) {
        super(context);
    }

    //    @Override
    //    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    //        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //         LogUtil.i("onMeasure-----------");
    //    }
    //
    //    @Override
    //    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    //        super.onLayout(changed, l, t, r, b);
    //         LogUtil.i("onLayout-------------------");
    //    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtil.i("onSizeChanged-------------------");
        LogUtil.i("w----" + w + "\n" + "h-----" + h + "\n" + "oldW-----" + oldw + "\noldh----" + oldh);
        if (null != mChangedListener && 0 != oldw && 0 != oldh) {
            // TODO 如果输入框支持换行，则不能直接用h < oldh。因为换行也会改变scrollview的高度。
            boolean mShowKeyboard = h < oldh;
            if (mShowKeyboard) {
                mHandler.sendMessage(mHandler.obtainMessage(KEYBOARD_SHOW));
            } else {
                mHandler.sendMessage(mHandler.obtainMessage(KEYBOARD_HIDE));
            }
        }
    }

    private static final int KEYBOARD_SHOW = 0X10;
    private static final int KEYBOARD_HIDE = 0X20;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case KEYBOARD_HIDE:
                    LogUtil.i("KEYBOARD_HIDE");
                    mChangedListener.onChanged(false);
                    break;

                case KEYBOARD_SHOW:
                    LogUtil.i("KEYBOARD_SHOW");
                    mChangedListener.onChanged(true);
                    break;

                default:
                    break;
            }
        }
    };
    public void setOnSizeChangedListener(onSizeChangedListener listener) {
        mChangedListener = listener;
    }

    public interface onSizeChangedListener {

        void onChanged(boolean showKeyboard);
    }

}