package com.android.base.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 获取通讯录和通话记录
 * <p>
 * <br> Author: 叶青
 * <br> Version: 4.0.0
 * <br> Date: 2017/4/12
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class ContactInfoBean extends BaseBean {

    @Override
    protected void init(JSONObject jSon) throws JSONException {

    }

    private List<ContactListBean> contactList;

    private List<CallListBean> callList;

    public List<ContactListBean> getContactList() {
        return contactList;
    }

    public void setContactList(List<ContactListBean> contactList) {
        this.contactList = contactList;
    }

    public List<CallListBean> getCallList() {
        return callList;
    }

    public void setCallList(List<CallListBean> callList) {
        this.callList = callList;
    }

    /** 通讯录 */
    public static class ContactListBean {
        private String name;
        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    /** 通话记录 */
    public static class CallListBean {
        private String phone;
        private String callDate;
        private String callType;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCallDate() {
            return callDate;
        }

        public void setCallDate(String callDate) {
            this.callDate = callDate;
        }

        public String getCallType() {
            return callType;
        }

        public void setCallType(String callType) {
            this.callType = callType;
        }
    }
}
