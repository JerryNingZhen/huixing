package com.hx.huixing.common.http;


import com.hx.huixing.bean.CareArticleBean;
import com.hx.huixing.bean.TokenBean;
import com.hx.huixing.bean.UserBean;

import java.util.Map;

import retrofit2.http.HEAD;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by tanjun on 2018/5/31.
 * 请求接口
 */

public interface RequestBiz {

      /** 获取token http://backend.blockcomet.com/blockchain/getAppToken */
      @POST("blockchain/getAppToken")
      Observable<Result<TokenBean>> getToken(@QueryMap Map<String, String> map);

      /** 获取appCode */
      @POST("blockchain/getAppCode")
      Observable<Result<Object>> getVerCode(@QueryMap Map<String,String> map);

      /** 注册 */
      @POST("news/registerUserOnApp")
      @Headers("Content-Type: application/json;charset=UTF-8")
      Observable<Result<Object>> registerUser(@QueryMap Map<String, String> map);

      /** 登录 */
      @POST("news/login")
      Observable<Result<UserBean>> login(@QueryMap Map<String, String> map);

      /** 关注/推荐   like为空就是关注，1就是推荐*/
      @POST("quaryReviewByUser")
      Observable<ResultList<CareArticleBean>> getArticleList(@QueryMap Map<String, String> map);



      //    /** 登录 */
//    @POST("/app/enc/login")
//    Observable<Result<UserInfoLoginBean>> login(@QueryMap Map<String, String> map);
//
//    /** 版本更新 */
//    @POST("/app/version/check")
//    Observable<Result<UpdateInfoBean>> checkVersion(@QueryMap Map<String, String> map);
//
//    /** 获取组织信息 */
//    @POST("/app/auth/getOrganization")
//    Observable<Result<PickerCardBean>> getOrgInfo(@QueryMap Map<String, String> map);
//
//    /** 提交信息 */
//    @POST("/app/auth/createCustInfo")
//    Observable<Result<CustIdBean>> createCustomerInfo(@QueryMap Map<String, String> map);
//
//    /** 根据Imei 查询信息 */
//    @POST("/app/auth/task/getImeiInfo")
//    Observable<Result<ZMXGPSDeviceInfoBean>> getDeviceInfo(@QueryMap Map<String, String> map);
//
//    /** GPS安装 设备信息 */
//    @POST("/app/auth/detect")
//    Observable<Result<Object>> detect(@QueryMap Map<String, String> map);
//
//    /***********************装车位置***************************/
//    /** 绑定设备 */
//    @POST("/app/auth/task/newBinding")
//    Observable<Result<Object>> newBinding(@QueryMap Map<String, String> map);
//
//    /** 查询图片信息 */
//    @POST("/app/auth/task/queryAllImgByParams")
//    Observable<Result<ImgBean>> queryAllImg(@QueryMap Map<String, String> map);
//
//    /** 图片上传 */
//    @Multipart
//    @POST()
//    Observable<Result<FiledBean>> upLoadImg(@Url String url, @QueryMap Map<String, String> map, @PartMap Map<String, RequestBody> fileMap);
//
//    /** 图片删除 */
//    @POST("/app/auth/task/deleteImg")
//    Observable<Result<Object>> deleteImg(@QueryMap Map<String, String> map);
//
//    /** 获取gps定位信息 */
//    @POST("/app/auth/task/getLocationInfos")
//    Observable<Result<GPSBean>> getGpsLocationBean(@QueryMap Map<String, String> map);
//
//    /** 拆除 */
//    @POST("/app/auth/task/teardown")
//    Observable<Result<Object>> tearDown(@QueryMap Map<String, String> map);
//
//    /** 安装完成 */
//    @POST("/app/auth/task/completeTask")
//    Observable<Result<Object>> installComplete(@QueryMap Map<String, String> map);
//
//    /** 锁油断电 */
//    @POST("/app/auth/instructions")
//    Observable<Result<Object>> lockInstructiom(@QueryMap Map<String, String> map);
//
//    @POST("app/auth/getStoreList")
//    Observable<Result<GpsMonitor.HomeBean>> getStoreList(@QueryMap Map<String, String> map);
//
//    @POST("/app/auth/mine/logout")
//    Observable<Result<Object>> logOut(@QueryMap Map<String, String> map);
//
//    /** GPS监控获取门店下设备列表        @POST("/app/gps/getEquipmentList") */
//    @POST("/app/gps/getEquipmentList")
//    Observable<Result<GpsMonitor.StoryList>> getEquipmentList(@QueryMap Map<String, String> map);
//    /** 超级管管理员GPS监控获取门店下设备列表 */
//    @POST("app/auth/getEquipmentListForSuperManager")
//    Observable<Result<GpsMonitor.StoryList>> getEquipmentListForSuperManager(@QueryMap Map<String, String> map);

}
