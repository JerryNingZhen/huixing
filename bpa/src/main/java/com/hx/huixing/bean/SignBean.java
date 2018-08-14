package com.hx.huixing.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * <br> Description 签到
 * <br> Author: 谭俊
 * <br> PackageName com.tan.mvpdemo.activityMvp
 * <br> Date: 2018/8/15
 */
public class SignBean {

    /**
     * code : 0
     * count : 11
     * datas : [{"actionDetail":"签到奖励","actionId":null,"awardType":"签到","coinChinaName":"彗星","coinCurrency":0.5,"coinId":null,"coinName":"hui","coStringypeId":1,"createTime":"2018-08-10 15:01:03.0","realName":"八菱科技","userName":"15338705596"},{"actionDetail":"签到奖励","actionId":null,"awardType":"签到","coinChinaName":"彗星","coinCurrency":0.5,"coinId":null,"coinName":"hui","coStringypeId":1,"createTime":"2018-08-08 15:38:27.0","realName":"八菱科技","userName":"15338705596"},{"actionDetail":"你的点评《拉进来了》很棒，被点赞啦，请收下奖励再接再厉哦~","actionId":"a41b3f65-0af6-40e8-86bd-dccc8ef8612d","awardType":"回复文章/长评","coinChinaName":"彗星","coinCurrency":0.13,"coinId":null,"coinName":"hui","coStringypeId":7,"createTime":"2018-07-31 15:07:36.0","realName":"八菱科技","userName":"15338705596"},{"actionDetail":"点赞《ctctgcgg》","actionId":"f5ad7d3f-1e7b-4177-a471-b59bd3375193","awardType":"文章收益","coinChinaName":"彗星","coinCurrency":0.1,"coinId":null,"coinName":"hui","coStringypeId":3,"createTime":"2018-07-31 14:58:42.0","realName":"八菱科技","userName":"15338705596"},{"actionDetail":"点赞《翻译成v有吃药此言差矣吃呀吃呀》","actionId":"1e929fef-5fdf-4154-97d2-49ccda908516","awardType":"文章收益","coinChinaName":"彗星","coinCurrency":0.1,"coinId":null,"coinName":"hui","coStringypeId":3,"createTime":"2018-07-31 14:57:40.0","realName":"八菱科技","userName":"15338705596"},{"actionDetail":"点赞《非常吃吃吃》","actionId":"31715fd9-d666-47d8-9d22-f0e04e92ab87","awardType":"文章收益","coinChinaName":"彗星","coinCurrency":0.11,"coinId":null,"coinName":"hui","coStringypeId":3,"createTime":"2018-07-31 14:52:33.0","realName":"八菱科技","userName":"15338705596"},{"actionDetail":"《哦哦哦》很棒，被点赞啦，请收下奖励再接再厉哦~","actionId":"0efa5b34-677a-4d1a-9e92-cf6404463c4c","awardType":"文章收益","coinChinaName":"彗星","coinCurrency":0.15,"coinId":null,"coinName":"hui","coStringypeId":3,"createTime":"2018-07-31 13:50:01.0","realName":"八菱科技","userName":"15338705596"},{"actionDetail":"《PK骷髅精灵》很棒，被点赞啦，请收下奖励再接再厉哦~","actionId":"5cdaaed4-c861-404b-bc4e-90d4ac0ea7cf","awardType":"文章收益","coinChinaName":"彗星","coinCurrency":0.15,"coinId":null,"coinName":"hui","coStringypeId":3,"createTime":"2018-07-31 13:15:19.0","realName":"八菱科技","userName":"15338705596"},{"actionDetail":"签到奖励","actionId":null,"awardType":"签到","coinChinaName":"彗星","coinCurrency":0.5,"coinId":null,"coinName":"hui","coStringypeId":1,"createTime":"2018-07-31 11:29:50.0","realName":"八菱科技","userName":"15338705596"},{"actionDetail":"签到奖励","actionId":null,"awardType":"签到","coinChinaName":"彗星","coinCurrency":0.5,"coinId":null,"coinName":"hui","coStringypeId":1,"createTime":"2018-07-30 17:58:55.0","realName":"八菱科技","userName":"15338705596"}]
     * msg : 查询成功
     */

    private String code;
    private String count;
    private String msg;
    private ArrayList<DatasBean> datas;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * actionDetail : 签到奖励
         * actionId : null
         * awardType : 签到
         * coinChinaName : 彗星
         * coinCurrency : 0.5
         * coinId : null
         * coinName : hui
         * coStringypeId : 1
         * createTime : 2018-08-10 15:01:03.0
         * realName : 八菱科技
         * userName : 15338705596
         */

        private String actionDetail;
        private String actionId;
        private String awardType;
        private String coinChinaName;
        private String coinCurrency;
        private String coinId;
        private String coinName;
        private String coStringypeId;
        private String createTime;
        private String realName;
        private String userName;

        public String getActionDetail() {
            return actionDetail;
        }

        public void setActionDetail(String actionDetail) {
            this.actionDetail = actionDetail;
        }

        public String getActionId() {
            return actionId;
        }

        public void setActionId(String actionId) {
            this.actionId = actionId;
        }

        public String getAwardType() {
            return awardType;
        }

        public void setAwardType(String awardType) {
            this.awardType = awardType;
        }

        public String getCoinChinaName() {
            return coinChinaName;
        }

        public void setCoinChinaName(String coinChinaName) {
            this.coinChinaName = coinChinaName;
        }

        public String getCoinCurrency() {
            return coinCurrency;
        }

        public void setCoinCurrency(String coinCurrency) {
            this.coinCurrency = coinCurrency;
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

        public String getCoStringypeId() {
            return coStringypeId;
        }

        public void setCoStringypeId(String coStringypeId) {
            this.coStringypeId = coStringypeId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
