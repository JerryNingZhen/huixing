package com.hx.huixing.bean;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.tan.mvpdemo.activityMvp
 * <br> Date: 2018/7/22
 */
public class CoinBean {

    /**
     * code : 0
     * count : null
     * datas : {"coinChinaName":"彗星","coinId":1,"coinName":"hui","totalCount":null}
     * msg : 查询成功
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
         * coinChinaName : 彗星
         * coinId : 1
         * coinName : hui
         * totalCount : null
         */

        private String coinChinaName;
        private String coinId;
        private String coinName;
        private String totalCount;

        public String getCoinChinaName() {
            return coinChinaName;
        }

        public void setCoinChinaName(String coinChinaName) {
            this.coinChinaName = coinChinaName;
        }

        public String getCoinId() {
            return coinId;
        }

        public void setCoinId(String coinId) {
            this.coinId = coinId;
        }

        public String getCoinName() {
            return coinName;
        }

        public void setCoinName(String coinName) {
            this.coinName = coinName;
        }

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }
    }
}
