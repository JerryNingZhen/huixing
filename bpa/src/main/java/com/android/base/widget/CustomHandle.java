package com.android.base.widget;

import android.app.Activity;
import android.os.Handler;

import java.lang.ref.WeakReference;

/**
 * Handler弱引用
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016/12/11
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class CustomHandle extends Handler {

    private WeakReference<Activity> weakReference;

    public CustomHandle(WeakReference<Activity> weakReference) {
        this.weakReference = weakReference;
    }

    public void handleMessage(android.os.Message msg) {

        super.handleMessage(msg);
        if (weakReference.get() != null) {
            // update android ui
        }
        //            switch (msg.what) {
        //                case 100:
        //                    BaseActivity baseUI = (BaseActivity)weakReference.get();
        //                    if(baseUI!=null&&!baseUI.isonDestroyed){
        //                        baseUI.UpdateUi();
        //                    }
        //                    break;
        //
        //                default:
        //                    break;
        //            }
    }
}