package com.android.base.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.android.base.BaseApplication;
import com.android.base.bean.LocationBean;
import com.android.base.configs.BroadcastFilters;
import com.android.base.configs.ConstantKey;
import com.android.base.utils.DateUtil;
import com.android.base.utils.LogUtil;
import com.android.base.utils.ToastUtil;

/**
 * 高德地图定位服务
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class AMapLocationService extends Service {

    private AMapLocationClient locationClient = null;
    /** 第一次启动service */
    private boolean isFirst = true;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        //        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        //        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //        if (!gps) {
        //            // 跳转设置GPS开关
        //            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        //            startActivityForResult(intent, 0);
        //        }
        locationClient = new AMapLocationClient(this);
        // 设置定位回调监听
        locationClient.setLocationListener(mLocationListener);
        // 注册广播
        registerReceive();

        // 初始化定位参数
        // 声明mLocationOption对象 */
        AMapLocationClientOption locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置是否返回地址信息（默认返回地址信息）
        locationOption.setNeedAddress(true);
        // 设置是否只定位一次(true),默认为false TODO
        locationOption.setOnceLocation(true);
        // 设置是否强制刷新WIFI，默认为强制刷新
        locationOption.setWifiActiveScan(true);
        // 设置是否允许模拟位置,默认为false，不允许模拟位置
        locationOption.setMockEnable(false);
        // 设置定位间隔,单位毫秒,默认为1h
        locationOption.setInterval(3600 * 1000);
        // 给定位客户端对象设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isFirst) {
            isFirst = false;
        } else {
            startLocation();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 声明定位回调监听器
     */
    private AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            LogUtil.i(DateUtil.getDate() + "-----------------" + (System.currentTimeMillis() - BaseApplication.getInstance().locationBean.getUpdateTime()));
            // 发送定位失败的广播
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putBoolean(ConstantKey.INTENT_KEY_BOOLEAN, false);
            intent.setAction(BroadcastFilters.ACTION_LOCATION_COMPLETE);
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    // 定位成功回调信息，设置相关消息
                    // aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    // aMapLocation.getAccuracy();//获取精度信息
                    // aMapLocation.getCountry();//国家信息
                    // aMapLocation.getLocationDistrict();//城区信息
                    // aMapLocation.getStreet();//街道信息
                    // aMapLocation.getStreetNum();//街道门牌号信息
                    // aMapLocation.getAdCode();//地区编码
                    // aMapLocation.getAOIName();//获取当前定位点的AOI信息

                    // latitude=22.559916longitude=113.866794province=广东省#city=深圳市#district=宝安区#cityCode=0755#adCode=440306
                    // #address=广东省深圳市宝安区兴业路靠近豪业华庭
                    // #country=中国#road=兴业路#poiName=豪业华庭#street=兴业路#streetNum=2012-5号#aoiName=豪业华庭
                    // #errorCode=0#errorInfo=success#locationDetail=-5#locationType=4
                    // 保存定位信息
                    if (null == BaseApplication.getInstance().locationBean) {
                        BaseApplication.getInstance().locationBean = new LocationBean();
                    }

                    if ((System.currentTimeMillis() - BaseApplication.getInstance().locationBean.getUpdateTime()) > 10 * 1000) {
                        // TODO  防止同一时间多次 调用定位回调
                    }

                    BaseApplication.getInstance().locationBean.setLatitude(aMapLocation.getLatitude());
                    BaseApplication.getInstance().locationBean.setLongitude(aMapLocation.getLongitude());
                    BaseApplication.getInstance().locationBean.setProvinceName(aMapLocation.getProvince());
                    BaseApplication.getInstance().locationBean.setCityName(aMapLocation.getCity());
                    BaseApplication.getInstance().locationBean.setDistrictName(aMapLocation.getDistrict());
                    BaseApplication.getInstance().locationBean.setAddress(aMapLocation.getAddress());
                    BaseApplication.getInstance().locationBean.setDistrictCode(aMapLocation.getAdCode());
                    BaseApplication.getInstance().locationBean.setUpdateTime(aMapLocation.getTime());

                    LogUtil.i(
                            aMapLocation.getAdCode() + "====\n" +//440303 地区编码
                                    aMapLocation.getAddress() + "====\n" +//广东省深圳市罗湖区建设路靠近南洋大厦A座
                                    aMapLocation.getCountry() + "====\n" +//中国
                                    aMapLocation.getProvince() + "====\n" +//广东省
                                    aMapLocation.getCity() + "====\n" +//深圳市
                                    aMapLocation.getDistrict() + "====\n" +//罗湖区
                                    aMapLocation.getStreet() + "====\n" +//建设路
                                    // aMapLocation.getRoad() + "====\n" +//建设路
                                    aMapLocation.getStreetNum() + "====\n" +//2006-6号
                                    aMapLocation.getAoiName() + "====\n" +//南方证券大厦
                                    aMapLocation.getPoiName() + "====\n" +//南洋大厦A座
                                    aMapLocation.getCityCode() + "====\n" +//区号 0755
                                    aMapLocation.getErrorCode() + "====\n" +//0
                                    aMapLocation.getErrorInfo() + "====\n" +//success
                                    aMapLocation.getLatitude() + "====\n" +// 22.539973
                                    aMapLocation.getLongitude() + "====\n" +// 114.116494
                                    //aMapLocation.getLocationDetail() + "====\n" +
                                    //aMapLocation.getProvider() + "====\n" +
                                    //aMapLocation.getAccuracy() + "====\n" +
                                    //aMapLocation.getAltitude() + "====\n" +
                                    //aMapLocation.getBearing() + "====\n" +
                                    //aMapLocation.getLocationType() + "====\n" +
                                    //aMapLocation.getSatellites() + "====\n" +
                                    //aMapLocation.getSpeed() + "====\n" +
                                    aMapLocation.getTime() + "====\n"
                    );
                    bundle.putBoolean(ConstantKey.INTENT_KEY_BOOLEAN, true);
                    ToastUtil.showToast(AMapLocationService.this, "success");
                } else {
                    ToastUtil.showToast(AMapLocationService.this, "fail");
                    LogUtil.i(aMapLocation.getErrorCode() + "====" + aMapLocation.getErrorInfo());
                }
            }
            // 发送定位失败的广播
            intent.putExtras(bundle);
            sendBroadcast(intent);
        }
    };

    /**
     * 开始定位
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016/11/2 21:28
     * <br> UpdateTime: 2016/11/2 21:28
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void startLocation() {
        if (null != locationClient) {
            locationClient.startLocation();// 开始定位
        }
    }

    /**
     * 停止定位
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-4-14,下午2:39:02
     * <br> UpdateTime: 2016-4-14,下午2:39:02
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容, 若无修改可不写.)
     */
    private void stopLocation() {
        if (null != locationClient) {
            locationClient.stopLocation();// 停止定位
        }
        stopService(new Intent().setClass(AMapLocationService.this, AMapLocationService.class));
    }

    /** 广播接收器 */
    private BroadcastReceiver receiver;

    /**
     * 注册广播
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016/11/2 21:32
     * <br> UpdateTime: 2016/11/2 21:32
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void registerReceive() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastFilters.ACTION_LOCATION_COMPLETE);// 定位完成
        filter.addAction(BroadcastFilters.ACTION_LOCATION_START);// 开始定位
        filter.addAction(BroadcastFilters.ACTION_LOCATION_FINISH);// 结束定位
        filter.addAction(BroadcastFilters.ACTION_APP_EXIT);//程序退出

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // 开始定位
                if (intent.getAction().equals(BroadcastFilters.ACTION_LOCATION_START)) {
                    startLocation();
                }

                // 结束定位
                else if (intent.getAction().equals(BroadcastFilters.ACTION_LOCATION_FINISH)) {
                    stopLocation();// 停止定位
                }

                // 程序退出
                else if (intent.getAction().equals(BroadcastFilters.ACTION_APP_EXIT)) {
                    LogUtil.i("停止定位...............");
                    stopLocation();// 停止定位
                }

                //// 定位完成
                //else if (intent.getAction().equals(BroadcastFilters.ACTION_LOCATION_COMPLETE)) {
                //
                //    Bundle bundle = intent.getExtras();
                //    boolean isSuccess = bundle.getBoolean(ConstantKey.INTENT_KEY_BOOLEAN);
                //    LogUtil.i("isSuccess......" + isSuccess);
                //    //                    long l = locationOption.getInterval();
                //    //                    // 设置定位间隔,单位毫秒,默认为1h
                //    //                    int interval = 3600 * 1000;
                //    //                    if (!isSuccess) {
                //    //                        interval = 1000;//10s
                //    //                    }
                //    //
                //    //                    if (l != 1000) {
                //    //                        // 设置定位间隔,单位毫秒,默认为1h
                //    //                        locationOption.setInterval(interval);
                //    //                        // 给定位客户端对象设置定位参数
                //    //                        locationClient.setLocationOption(locationOption);
                //    //                        // 启动定位
                //    //                        locationClient.startLocation();
                //    //                    }
                //}
            }
        };
        registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroy() {
        LogUtil.i("onDestroy...");
        if (null != receiver) {
            unregisterReceiver(receiver);
        }
        if (null != locationClient) {
            locationClient.onDestroy();// 销毁定位客户端
        }
        super.onDestroy();
    }
}