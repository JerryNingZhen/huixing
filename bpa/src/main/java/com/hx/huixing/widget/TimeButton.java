package com.hx.huixing.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

/*
PS: 由于发现timer每次cancle()之后不能重新schedule方法,所以计时完毕只恐timer.
每次开始计时的时候重新设置timer, 没想到好办法初次下策
注意把该类的onCreate()onDestroy()和activity的onCreate()onDestroy()同步处理*/


public class TimeButton extends Button implements OnClickListener {
    private final int DEFAULT_LENGTH = 90;// 默认倒计时长度，单位秒
    private String textafter = "秒后重新获取";
    private String textbefore = "点击获取验证码";
    private boolean canClick = true; //再计时的时候控制点击事件
    private Timer timer;
    private TimerTask timerTask;
    private int length = DEFAULT_LENGTH;
    private OnEventListener listener;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            TimeButton.this.setText(msg.what + textafter);
            if (msg.what <= 0) {
                canClick = true;
                TimeButton.this.setText(textbefore);
                clearTimer();
            }
        }
    };

    public TimeButton(Context context) {
        this(context, null);
    }

    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    int countDownLength;

    private void initTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {

            @Override
            public void run() {
                countDownLength--;
                handler.sendEmptyMessage(countDownLength);
            }
        };
    }

    public void clearTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void setListener(OnEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (canClick && listener.onValidate()) {
            canClick = false;
            listener.onClick(v);
        }
        // timer.scheduleAtFixedRate(task, delay, period);
    }

    public void setCanClick(){
        canClick = true;
    }

    public void startSchedule(){

        clearTimer();
        countDownLength = length;
        initTimer();
        this.setText(length + textafter);
        timer.schedule(timerTask, 0, 1000);
    }

    /**
     * 和activity的onDestroy()方法同步
     */
    public void onDestroy() {
        clearTimer();
        Log.e("carier", "onDestroy");
    }

    /**
     * 设置计时时候显示的文本
     */
    public void setTextAfter(String text) {
        this.textafter = text;
    }

    /**
     * 设置点击之前的文本
     */
    public void setTextBefore(String text) {
        this.textbefore = text;
    }

    /**
     * 设置到计时长度
     * @param length 时间 默认毫秒
     * @return
     */
    public void setLength(int length) {
        this.length = length;
    }

    public interface OnEventListener {
        public boolean onValidate();
        public void onClick(View v);
    }

}