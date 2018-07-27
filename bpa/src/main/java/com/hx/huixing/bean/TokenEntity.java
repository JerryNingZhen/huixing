package com.hx.huixing.bean;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.tan.mvpdemo.activityMvp
 * <br> Date: 2018/7/22
 */
public class TokenEntity {

    /**
     * code : 0
     * count : null
     * datas : {"token":"f35eb848-d57a-4aa6-b46f-68d31463f134"}
     * msg : 获取成功
     */

    private String code;
    private String count;
    private DatasBean datas;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DatasBean {
        /**
         * token : f35eb848-d57a-4aa6-b46f-68d31463f134
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
