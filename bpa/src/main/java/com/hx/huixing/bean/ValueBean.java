package com.hx.huixing.bean;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.bean
 * <br> Date: 2018/7/24
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ValueBean {


    /**
     * code : 0
     * count : null
     * datas : {"experience":0,"grade":0,"rating":2}
     * msg : 查询成功
     */

    private int code;
    private String count;
    private DatasBean datas;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
         * experience : 0
         * grade : 0
         * rating : 2
         */

        private String experience;
        private String grade;
        private String rating;

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }
    }
}
