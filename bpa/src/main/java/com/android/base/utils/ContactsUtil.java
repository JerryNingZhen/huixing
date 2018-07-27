package com.android.base.utils;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.TelephonyManager;

import com.android.base.BaseApplication;
import com.android.base.bean.ContactInfoBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 获取联系人
 * <p>
 * <br> Author: 叶青
 * <br> Version: 4.0.0
 * <br> Date: 2017/3/23
 */
public class ContactsUtil {

    /** 通讯录信息列表 */
    private static List<ContactInfoBean.ContactListBean> contactList = new ArrayList<>();
    /** 通话记录列表 */
    private static List<ContactInfoBean.CallListBean> callList = new ArrayList<>();
    /** 通讯录信息hasmap 用来过滤重复联系人 */
    private static HashMap<String, String> hashMap = new HashMap<>();

    public static ContactInfoBean start() {
        hashMap = new HashMap<>();
        contactList = new ArrayList<>();
        callList = new ArrayList<>();

        // 读取手机里的手机联系人
        getPhoneContact();

        TelephonyManager mTelephonyManager = (TelephonyManager) BaseApplication.getInstance().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        int simState = mTelephonyManager.getSimState();
        String mString = "";
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                mString = "无卡";
                break;

            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                mString = "需要NetworkPIN解锁";
                break;

            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                mString = "需要PIN解锁";
                break;

            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                mString = "需要PUN解锁";
                break;

            case TelephonyManager.SIM_STATE_READY:
                mString = "良好";
                break;

            case TelephonyManager.SIM_STATE_UNKNOWN:
                mString = "未知状态";
                break;
        }
        LogUtil.w(mString);
        if (simState == TelephonyManager.SIM_STATE_READY) {
            // 读取Sim卡中的手机联系人
            getSimContact("content://icc/adn");//一般用这一条，如果这条不行的话可以试试下面的一条。
            getSimContact("content://sim/adn");//此种读法在我们手机里不能读取，所以，还是用上个uri比较好。
        }

        getCallLog();

        ContactInfoBean contactInfoBeen = new ContactInfoBean();
        contactInfoBeen.setContactList(contactList);
        contactInfoBeen.setCallList(callList);
        return contactInfoBeen;
    }

    /**
     * 读取手机联系人信息
     */
    private static void getPhoneContact() {
        // 从本机中取号
        Cursor cursor = null;
        try {
            if (null == BaseApplication.getInstance().getApplicationContext()) {
                return;
            }

            ContentResolver contentResolver = BaseApplication.getInstance().getApplicationContext().getContentResolver();
            if (null == contentResolver) {
                return;
            }

            // 取得电话本中开始一项的光标
            Uri uri = ContactsContract.Contacts.CONTENT_URI;
            // String[] projection = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, "sort_key"};
            cursor = contentResolver.query(uri, null, null, null, null);

            if (cursor == null) {
                return;
            }

            while (cursor.moveToNext()) {
                // 取得联系人名字
                String name = cursor.getString(cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME));
                name = filterEmoji(name);
                // 取得联系人ID
                int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                // 再类ContactsContract.CommonDataKinds.Phone中根据查询相应id联系人的所有电话；
                Cursor phoneCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                        new String[]{Integer.toString(id)},
                        null);

                if (phoneCursor != null) {
                    // 取得电话号码(可能存在多个号码)
                    while (phoneCursor.moveToNext()) {
                        String number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        number = number.replaceAll("-", "");
                        number = number.replaceAll(" ", "");
                        ContactInfoBean.ContactListBean phoneAddressList = new ContactInfoBean.ContactListBean();
                        phoneAddressList.setName(name);
                        phoneAddressList.setPhone(number);
                        if (!isContain(number, name)) {
                            hashMap.put(number, name);
                            contactList.add(phoneAddressList);
                        }
                    }
                    phoneCursor.close();
                }
            }
            cursor.close();
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
            e.printStackTrace();
        }
    }

    /**
     * 读取SIM卡联系人信息
     */
    private static void getSimContact(String adn) {
        // 读取SIM卡手机号,有两种可能:content://icc/adn与content://sim/adn
        Cursor cursor = null;
        try {
            Uri uri = Uri.parse(adn);
            if (null == BaseApplication.getInstance().getApplicationContext()) {
                return;
            }

            ContentResolver contentResolver = BaseApplication.getInstance().getApplicationContext().getContentResolver();
            if (null == contentResolver) {
                return;
            }

            cursor = contentResolver.query(uri, null, null, null, null);

            if (cursor == null) {
                return;
            }

            while (cursor.moveToNext()) {
                // 取得联系人名字
                String name = cursor.getString(cursor.getColumnIndex("name"));
                name = filterEmoji(name);
                // 取得电话号码
                String number = cursor.getString(cursor.getColumnIndex("number"));
                number = number.replaceAll("-", "");
                number = number.replaceAll(" ", "");
                ContactInfoBean.ContactListBean phoneAddressList = new ContactInfoBean.ContactListBean();
                phoneAddressList.setName(name);
                phoneAddressList.setPhone(number);
                if (!isContain(number, name)) {
                    hashMap.put(number, name);
                    contactList.add(phoneAddressList);
                }
            }
            cursor.close();
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
            e.printStackTrace();
        }
    }

    /**
     * 获取通话记录
     */
    private static List<ContactInfoBean.CallListBean> getCallLog() {
        try {
            if (null == BaseApplication.getInstance().getApplicationContext()) {
                return null;
            }

            if (!PermissionUtils.getInstance().checkSelfPermission(BaseApplication.getInstance().getApplicationContext(), Manifest.permission.READ_CALL_LOG)) {
                return null;
            }

            ContentResolver contentResolver = BaseApplication.getInstance().getApplicationContext().getContentResolver();
            if (null == contentResolver) {
                return null;
            }

            // String[] projection = {CallLog.Calls.NUMBER, CallLog.Calls.TYPE, CallLog.Calls.DATE};
            Cursor cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, null,//projection
                    null, null, CallLog.Calls.DEFAULT_SORT_ORDER);

            if (cursor == null) {
                return null;
            }

            while (cursor.moveToNext()) {
                ContactInfoBean.CallListBean call = new ContactInfoBean.CallListBean();
                String phone = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                call.setCallDate(DateUtil.millis2Time(date,DateUtil.YMD_HMS));
                call.setPhone(phone);
                call.setCallType(type + "");//1:来电;2:拨出;3:未接;4:已接
                callList.add(call);
            }
            cursor.close();
            return callList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // ********************************************判断源字符串是否包含Emoji表情

    /**
     * 过滤源字符串的emoji表情
     *
     * @param source
     *         源字符串
     *
     * @return 过滤后的字符串
     */
    private static String filterEmoji(String source) {

        if (!containsEmoji(source)) {
            // 如果不包含，直接返回
            return source;
        }

        StringBuilder buf = new StringBuilder();
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) {// 不是emoji表情char
                //提交之前的数据，使表情编程数字数组 [-16, -97, -104, -95]
                String string = String.valueOf(codePoint);
                buf.append(string);
            }
            //            else {//是emoji表情char
            //                try {
            //                    StringBuilder builder = new StringBuilder(2);
            //                    byte[] str = builder.append(String.valueOf(codePoint))
            //                            .append(String.valueOf(source.charAt(i + 1)))
            //                            .toString().getBytes("UTF-8");
            //                    String strin = Arrays.toString(str);
            //                    String newString = strin.substring(1, strin.length() - 1);
            //                    string = "Γ" + newString + "Γ";
            //                    LogUtil.i("filters running newStr = " + string);
            //                } catch (Exception e) {
            //                    e.printStackTrace();
            //                }
            //                i++;
            //            }
            //            buf.append(string);
        }

        if (buf.length() == len) {// 这里的意义在于尽可能少的toString，因为会重新生成字符串
            return source;
        } else {
            //            LogUtil.i("filter running buf.toString() = " + buf.toString());
            return buf.toString();
        }
    }

    /**
     * 判断源字符串是否包含Emoji表情
     *
     * @param str
     *         源字符串
     *
     * @return true 包含
     */
    private static boolean containsEmoji(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (isEmojiCharacter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否包含Emoji Char
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }

    /**
     * 是否已经存在该联系人
     */
    private static boolean isContain(String phone, String name) {
        if (hashMap.containsKey(phone)) {
            if (hashMap.get(phone).equals(name)) {
                return true;
            }
        }
        return false;
    }
}