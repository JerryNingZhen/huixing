package com.android.base.configs;

/**
 * 服务端配置类
 * <p>
 * 此处定义服务器的链接地址配置和接口请求的方法，
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年4月4日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ConfigServer {

    // *****************************网请求消息状态码 ******************************//
    /** 请求接口数据成功状态码 */
    public static final String RESPONSE_STATUS_SUCCESS = "0";
    /** 请求接口数据异常状态码 */
    public static final String RESPONSE_STATUS_FAIL = "99999";
    /** 请求接口TOKEN失效状态码 */
    public static final String RESPONSE_STATUS_TOKEN_ERROR = "101";

    // ***************************接口请求配置 ****************************//
    /** 服务器连接方法key */
    public static final String SERVER_METHOD_KEY = "method";
    /** 语言key */
    public static final String SERVER_LANGUAGE_KEY = "language";
    /** 服务器超时时间 */
    public static final int SERVER_CONNECT_TIMEOUT = 30000;

    /** 请求数据条数 */
    public static final String PAGE_COUNT = "10";

    /** 返回数据json最外层key */
    public static final String RESULT_JSON_STATUS = "code";
    public static final String RESULT_JSON_INFO = "msg";
    public static final String RESULT_JSON_DATA = "datas";

    // ***************************服务器地址 ****************************/
    /** 服务器地址 */
    //
    public final static String SERVER_API_URL = "http://testapi.blockcomet.com/";
    //public final static String SERVER_API_URL = "http://backend.blockcomet.com/";
    public final static String SHARE_URL = "http://huixing.io/comment.html?reviewId=";
    /** 发表文章 */
    public final static String MOTHED_ADDREVIEW = "blockchain/addReview";
    /** 草稿箱-查询草稿（列表） */
    public final static String MOTHED_SELECTDRAFTLIST = "draft/selectDraftList";
    /** 草稿箱-新增草稿 */
    public final static String MOTHED_ADDDRAFT = "draft/addDraft";
    /** 草稿箱-编辑草稿 */
    public final static String MOTHED_EDITDRAFT = "draft/editDraft";
    /** 草稿箱-删除草稿 */
    public final static String MOTHED_DELECTDRAFT = "draft/deleteDraft";
    /** 文章详情 */
    public final static String MOTHED_QUARYARTICLEDEATAIL = "topic/quaryArticleDeatailOnApp";
    /** 文章下面的评论列表 */
    public final static String MOTHED_QUERYARTICLES = "blockchain/queryArticles";
    /** 查询某一条评论下的所有评论 */
    public final static String MOTHED_QUERYARTICLESLIST = "blockchain/queryArticlesList";
    /** 发表文章下评论 */
    public final static String MOTHED_ADDREVIEW_BLOCK = "blockchain/addReview";
    /** 点赞 */
    public final static String MOTHED_ADDLIKE = "blockchain/addLike";
    /** 关注 */
    public final static String MOTHED_ATTENT = "attention/attent";
    /** 取消关注 */
    public final static String MOTHED_DELATTENT = "attention/delAttent";

    /** 我的主页 & 他的主页 */
    public final static String MOTHED_QUARYUSERS = "news/quaryusers";
    /** 他的主页---文章列表 */
    public final static String MOTHED_QUARYREVIEWBYUSER = "blockchain/quaryReviewByUser";


    /** 删除文章 */
    public final static String MOTHED_DELREVIEW = "blockchain/delReview";
    /** 编辑文章 */
    public final static String MOTHED_UPDATAREVIEW = "blockchain/updataReviewOnApp";


    /** 修改密码 */
    public final static String METHOD_CHANGEPASSWORD = "news/changePassword";
    /** token */
    public final static String method_getAppToken = "blockchain/getAppToken";
    /** 查余额 */
    public final static String METHOD_QUERYCHAINCOINBANLACEDETAIL = "chainCoinWallet/queryChainCoinBanlaceDetail";
    /** 未读消息数量 type 2为点赞的未读消息，3为评论的未读消息 */
    public final static String METHOD_COUNTNOTREADMESSAGE = "news/countNotReadMessage";
    /** 验证码获取接口 */
    public final static String METHOD_GETAPPCODE = "blockchain/getAppCode";

    /** 查看消息列表 type   2为点赞的，3为评论的 */
    public final static String METHOD_GETMESSAGE = "news/getMessage";
    /** 查询用户信息 */
    public final static String METHOD_QUARYUSERS = "news/quaryusers";
    /** 体力 经验 */
    public final static String METHOD_POPERTYONAPP = "news/popertyOnApp";
    /** 清除已读消息 */
    public final static String METHOD_READALLMESSAGE = "news/readAllMessage";
    /** 今日是否已签到 */
    public final static String METHOD_DETERMINHASSIGNIN = "chainCoinWallet/determinHasSignInOnApp";
    /** 签到 */
    public final static String METHOD_SIGNIN = "chainCoinWallet/signInOnApp";
    /** 修改个人头像 */
    public final static String METHOD_CHANGELOGO = "news/changeLogo";
    /** 修改个人资料 */
    public final static String METHOD_CHANGEREALNAME = "news/changeRealname";
    /** 找回密码 */
    public final static String METHOD_RECOVERPASSWORD ="news/recoverPassword";
    /** 邀请好友 */
    //http://m.huixing.io/#/inviteRegister/inviteRegister?invitingCode=12345
    public final static String H5_INVITE = "http://m.huixing.io/#/inviteRegister/inviteRegister?";
    /** 注册 */
    public final static String METHOD_REGISTERUSERONAPP = "news/registerUserOnApp";
    /** 上传头像 */
    public final static String METHOD_UPLOAD ="common/upload";
    /** 上传头像 */
    public final static String METHOD_INQUIRYAPPVERSION ="common/inquiryAppVersion";
    /** 关注人列表 & 粉丝列表 1为查看关注的人，4为查看粉丝*/
    public final static String METHOD_QUARYATTENTIONDATA = "attention/quaryAttentionData";
    /** 关注的人的文章 */
    public final static String METHOD_SELECTFOLLOWINGARTICLE = "attention/selectFollowingArticle";
    /** 交易记录 */
    public final static String METHOD_QUERYCHAINCOINRECORD = "chainCoinWallet/queryChainCoinRecord";

}