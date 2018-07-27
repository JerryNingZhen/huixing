/*******************************************************************************
 * Copyright 2016 Anton Bevza stfalcon.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package com.hx.huixing.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * 短信广播接收器
 * <p>
 * <br> Author: 叶青
 * <br> Version: 3.0.0
 * <br> Date: 2017/3/3
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class SmsReceiver extends BroadcastReceiver {

    private OnSmsCatchListener callback;
    private String phoneNumberFilter;

    public SmsReceiver(OnSmsCatchListener callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                assert pdusObj != null;
                for (Object aPdusObj : pdusObj) {
                    SmsMessage currentMessage = getIncomingMessage(aPdusObj, bundle);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    if (phoneNumberFilter != null && !phoneNumber.equals(phoneNumberFilter)) {
                        return;
                    }
                    String message = currentMessage.getDisplayMessageBody();

                    if (callback != null) {
                        callback.onSmsCatch(message);
                    }
                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SmsMessage getIncomingMessage(Object aObject, Bundle bundle) {
        SmsMessage currentSMS;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String format = bundle.getString("format");
            currentSMS = SmsMessage.createFromPdu((byte[]) aObject, format);
        } else {
            currentSMS = SmsMessage.createFromPdu((byte[]) aObject);
        }
        return currentSMS;
    }

    /**
     * Set phone number filter
     *
     * @param phoneNumberFilter
     *         phone number
     */
    public void setPhoneNumberFilter(String phoneNumberFilter) {
        this.phoneNumberFilter = phoneNumberFilter;
    }

    /***
     * 接收到短信验证码 回调
     */
    public interface OnSmsCatchListener {
        /**
         * 接收到短信验证码 回调
         * <p>
         * <br> Version: 3.0.0
         * <br> CreateAuthor: 叶青
         * <br> CreateTime: 2017/3/3 14:15
         * <br> UpdateAuthor: 叶青
         * <br> UpdateTime: 2017/3/3 14:15
         * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
         *
         * @param message
         *         短信验证码
         */
        void onSmsCatch(String message);
    }
}