package com.hx.huixing.common.net;

import com.android.base.BaseApplication;
import com.hx.huixing.uitl.Constant;

@SuppressWarnings("deprecation")
public class DataService {
    /**
     * 从配置文件中获取base接口
     */
    public static final String BASE_URL = Constant.BASE_URL;

    /**
     * 提现资费信息接口
     */
    public static final String WITHDRAW_CASH_INFO = "auth/cash/info";

    /**
     * 提现费用计算接口
     */
    public static final String WITHDRAW_CALCFEEE = "auth/cash/calcFee";

    /**
     * 提现申请提交接口
     */
    public static final String WITHDRAW_SAVE_NEW = "auth/enc/cash/save";

    /**
     * 启动页面广告图片接口
     */
    public static final String SCREEN_ONAD = "main/screenOnAd";


    /**
     * 富友验证码接口
     */
    public static final String FUYOU_CODE_IMAGE = "common/captcha?captchaId=";// FUYOU_BASE_URL

    public static final String AUTO_UPDATE_URL = "version/check";// 2.0测试阶段接口

    public static final String REALNAME_SAVE = "auth/enc/mine/realnameSave";
    public static final String REALNAME_SEARCH = "auth/mine/realnameSearch";
    public static final String BANKS_SEARCH = "auth/mine/bankAccountSearch";// 银行卡查询接口
    public static final String CARD_SETTING = "auth/enc/mine/bankAccountSave";// 银行卡修改提交接口
    public static final String FORGET_PASSWORD = "enc/forget/check";// 忘记密码1
    public static final String FORGET_PASSWORDUPDATE = "enc/forget/update";// 忘记密码update
    public static final String HOME = "main/companyData";
    public static final String HOME_FORENOTICE = "main/advancedBorrow";
    public static final String INVEST_LIST = "auth/mine/tenderList";
    public static final String INVEST_RECORD = "borrow/record";
    public static final String INVEST_ZHAIRECORD = "enc/borrowChange/record";
    public static final String LOGIN_PASSWORD = "auth/enc/mine/modifyLoginpass";
    public static final String LOGIN_YANZHENG = "enc/validate/smscode";
    public static final String MODIFY_PAYPASS = "auth/enc/mine/modifyPaypass";
    public static final String MATERIA = "borrow/attachment";
    public static final String MOBILE_AUTHENTICATION = "auth/enc/mine/realphoneSave";// 手机认证接口
    public static final String MOBILE_PHONE_SEARCH = "auth/mine/realphoneSearch";// 手机认证查询接口
    public static final String MOBILE_AUTHENTICATION_CODE = "enc/sms";// 获取短信验证码接口
    public static final String REGISTER = "enc/simplify/register";
    public static final String TRADE_PASSWORD = "auth/enc/mine/resetPaypassSave";
    public static final String MESSAGE = "auth/main/message";
    public static final String ANNOUMCEMENT = "main/announcement";
    public static final String CALANDER = "auth/returnCalendar/total";
    public static final String CALANDER_DETEIL = "auth/returnCalendar/detail";
    public static final String HOME_LIST = "borrow/list";
    public static final String HOME_LIST_DETAIL = "borrow/detail";
    public static final String TTRANSFER_DETAIL = "borrowChange/detail";
    public static final String HOME_CHANGE_LIST = "borrowChange/list";
    public static final String BORROW_CHANGE_SELLLIST = "auth/borrowChange/sellList";
    public static final String BORROW_CHANGE_SOLDLIST = "auth/borrowChange/soldList";
    public static final String BORROW_CHANGE_BUYLIST = "auth/borrowChange/buyList";
    public static final String FORUMINDEX = "auth/forum";
    public static final String BASE_DATA = "main/base";
    public static final String INVEST_BORROW_PERSONALBA = "auth/borrow/personalBalance";
    public static final String INVEST_BORROW_COUPOM = "auth/enc/coupon/list";
    public static final String TRANS_CLAIMS = "auth/enc/borrowChange/sellSave";
    public static final String SELL_TIP = "auth/enc/borrowChange/sellTip";
    public static final String ZHAIQUANZHUANRDELETE = "auth/enc/borrowChange/sellCancel";
    public static final String BORROW_TENDER_BUY = "auth/enc/borrowChange/buy";
    public static final String OMEPERSONALATA = "auth/enc/main/personalData";
    public static final String VIP_LEVEL_DATA = "auth/vip/personalData";
    public static final String ECONTRACT_SEARCH = "auth/mine/econtractSearch";
    public static final String MESSAGE_DELETE = "auth/enc/message/delete";

    /**
     * 我的页面个人数据接口 Tiger
     */
    public static final String PERSONAL_DATA = "auth/mine/personalData";
    public static final String RECHARGE_PAY = "auth/enc/recharge/pay";
    public static final String RECHARGE_INFO = "auth/recharge/info";
    public static final String BORROW_TENDER = "auth/enc/borrow/tender";
    public static final String REALMAIL_SAVE = "auth/enc/mine/realmailSave";
    public static final String REALMAIL_SEARCH = "auth/mine/realmailSearch";
    public static final String RESETPAYPASS_CHECK = "auth/enc/mine/resetPaypassCheck";
    public static final String PERSON_SEARCH = "auth/mine/personInfoSearch";// 个人数据查询页面
    public static final String PERSON_SAVE = "auth/enc/mine/personInfoSave";// 个人数据提交页面
    public static final String NEW_TASK_LIST = "auth/newTaskList";

    public static final String LOGIN_OUT = "auth/mine/logout";// 退出
    public static final String LOGIN_NEW = "enc/login";// 2016.3.15
    public static final String CHECK_USER_FLAG = "auth/mine/checkUserFlag";// 个人信息检查接口
    public static final String THIRDPARTY_LOGIN = "enc/thirdparty/auth";// 第三方登录认证接口
    public static final String REGISTER_THRID_BAND = "enc/thirdparty/binding";// 第三方登录绑定接口
    public static final String THRID_LOGIN_SELECT = "auth/enc/thirdparty/search";// 第三方登录绑定查询接口
    public static final String UNBIND_THRID_LOGIN = "auth/enc/thirdparty/unbind";// 第三方登录绑定解绑接口

    /**
     * 我的预约标 Tiger
     **/
    public static final String RESERVE_LIST = "auth/enc/reserve/list";// 预约标查看
    public static final String RESERVE_DETAIL = "auth/enc/reserve/detail";// 预约标详情
    public static final String APPLY_SUBMIT = "auth/enc/reserve/applySubmit";// 预约标申请提交
    public static final String RESERVE_APPLY = "auth/enc/reserve/apply";// 预约标申请
    public static final String RESERVE_CANCEL = "auth/enc/reserve/cancel";// 预约标申请撤销
    public static final String BORROW_DETAIL = "auth/enc/borrow/detail";// 预约标购买详情展示接口

    /**
     * 手势密码 Tiger
     */
    public static final String GESTURES_SEARCH = "enc/gestures/search";// 是否设置手势密码查询
    public static final String GESTURES_SET = "auth/enc/gestures/set";// 设置手势密码
    public static final String GESTURES_VERIFY = "enc/gestures/verify";// 手势密码验证登录
    public static final String CHECK_LOGIN = "auth/main/checkLogin";// 校验登录态接口


    /**
     * 华兴存管
     */
//    public static final String HUAXING_CREATE_ACCOUNT = "auth/depository/createAccount";// 开通E账户
//    public static final String HUAXING_BIND_CARD = "auth/depository/bindCard";// 绑卡
//    public static final String HUAXING_TRADE_CHECK = "auth/depository/tradeCheck";// 交易<充值提现>校验
//    public static final String HUAXING_TRADE = "auth/enc/depository/trade";// 交易<充值提现>
//    public static final String HUAXING_SELL_CANCEL = "auth/enc/depository/borrowChange/sellCancel";// 存管撤销转让接口
//    public static final String HUAXING_SELL_SAVE = "auth/enc/depository/borrowChange/sellSave";// 存管转让债权提交接口
//    public static final String HUAXING_PERSONAL_DATA = "auth/depository/mine/personalData";// 存管我的页面个人数据接口
//    public static final String HUAXING_BORROW_CHANGE_BUY = "auth/enc/depository/borrowChange/buy";// 存管债权购买接口
//    public static final String HUAXING_BORROW_TENDER = "auth/enc/depository/borrow/tender";// 存管投标提交接口
//    public static final String HUAXING_PERSONAL_BALANCE = "auth/depository/borrow/personalBalance";// 存管个人可用余额接口
//    public static final String HUAXING_BUY_LIST = "auth/depository/borrowChange/buyList";// 华兴承接债权列表接口
//    public static final String HUAXING_SELL_LIST = "auth/depository/borrowChange/sellList";// 华兴可转让债权列表接口
//    public static final String HUAXING_SOLD_LIST = "auth/depository/borrowChange/soldList";// 华兴转出债权列表接口
//    public static final String HUAXING_RESERVE_LIST = "auth/depository/reserve/list";// 华兴预约标查看
//    public static final String HUAXING_RESERVE_DETAIL = "auth/enc/depository/reserve/detail";// 存管预约标详情
//    public static final String HUAXING_TENDER_LIST = "auth/depository/mine/tenderList";// 存管投标记录接口
//    public static final String HUAXING_RESERVE_APPLY = "auth/depository/reserve/apply";// 存管预约标申请
//    public static final String HUAXING_RESERVE_APPLY_SUBMIT = "auth/enc/depository/reserve/applySubmit";// 存管预约标申请提交
//    public static final String HUAXING_BORROW_CHANGE_SELL_TIP = "auth/enc/depository/borrowChange/sellTip";// 存管债权转让提示信息接口

    /**
     * 红包接口
     */
    public static final String REDPACKET_LIST = "auth/enc/redpacket/list";// 用户投标时选择红包查询
    public static final String REDPACKET_ALL_LIST = "auth/enc/redpacketall/list";// 用户红包查询

    /**
     * 生产环境地址
     */
    public static final String UAT_SERVICE = "https://ogws.ghbank.com.cn/extService/ghbExtService.do";

    /**
     * SIT测试地址
     */
    public static final String SIT_SERVICE = "http://183.63.131.106:40013/extService/ghbExtService.do";
}
