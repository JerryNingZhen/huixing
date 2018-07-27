package com.android.base.bean;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 高德定位信息实体
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class LocationBean extends BaseBean {

    private static final long serialVersionUID = -5583548632401746229L;
    /**
     * 纬度
     */
    private double latitude = 22.558396;
    /**
     * 经度
     */
    private double longitude = 113.867319;
    /**
     * 省份(广东省)
     */
    private String provinceName = "广东省";
    /**
     * 广东省  深圳市	宝安区 44	 4403	440306
     */
    private String provinceCode = "";
    /**
     * 城市（深圳市）
     */
    private String cityName = "深圳市";
    /**
     * 广东省  深圳市	宝安区 44	 4403	440306
     */
    private String cityCode;
    /**
     * 城区（宝安区）
     */
    private String districtName = "龙岗区";
    /**
     * 广东省  深圳市	宝安区 44	 4403	440306
     */
    private String districtCode = "";
    /**
     * 详细地址
     */
    private String address;
    //    /**
    //     * 行政区代码
    //     * 广东省	 深圳市	宝安区
    //     * 44	 4403	440306
    //     */
    //    private String adCode;
    /**
     * 定位成功更新时间
     */
    private long updateTime = 0L;

    @Override
    protected void init(JSONObject jSon) throws JSONException {

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    //    public String getdistrictName() {
    //        return districtName;
    //    }
    //
    //    public void setdistrictName(String district) {
    //        this.districtName = district;
    //    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
        if (!TextUtils.isEmpty(districtCode) && districtCode.length() == 6) {
            this.provinceCode = districtCode.substring(0, 2);
            this.cityCode = districtCode.substring(0, 4);
        }
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}