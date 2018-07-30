package com.hx.huixing.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hx.huixing.R;

/**
 * created by tanjun
 * 带进度条的webview
 */
public class ProgressBarWebView extends WebView {

    private ProgressBar mProgressBar;

    public ProgressBarWebView(Context context) {
        super(context);
    }

    public ProgressBarWebView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mProgressBar = new ProgressBar(context,null,
                android.R.attr.progressBarStyleHorizontal);
        LinearLayout.LayoutParams layoutParams = new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 8);
        mProgressBar.setLayoutParams(layoutParams);

         /** 颜色值 */
        int colorRes =
                context.getResources().getColor(R.color.content_blue);
        mProgressBar.setProgress(colorRes);
        addView(mProgressBar);
        setWebChromeClient(new WebChromeClient());
    }

    public class WebChromeClient extends android.webkit.WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100){
                mProgressBar.setVisibility(GONE);
            }else {
                if (mProgressBar.getVisibility() == GONE){
                    mProgressBar.setVisibility(VISIBLE);
                }
                mProgressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams params = (LayoutParams) mProgressBar.getLayoutParams();
        params.x = l;
        params.y = t;
        mProgressBar.setLayoutParams(params);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
