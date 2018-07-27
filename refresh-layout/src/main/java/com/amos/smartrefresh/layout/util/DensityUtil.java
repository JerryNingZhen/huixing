package com.amos.smartrefresh.layout.util;

import android.content.res.Resources;

/**
 * 像素密度计算工具
 */
@SuppressWarnings("unused")
public class DensityUtil {

    public float density;

    public DensityUtil() {
        density = Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue
     *         虚拟像素
     *
     * @return 像素
     */
    public static int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue
     *         像素
     *
     * @return 虚拟像素
     */
    public static float px2dp(int pxValue) {
        return (pxValue / Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue
     *         虚拟像素
     *
     * @return 像素
     */
    public int dip2px(float dpValue) {
        return (int) (0.5f + dpValue * density);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue
     *         像素
     *
     * @return 虚拟像素
     */
    public float px2dip(int pxValue) {
        return (pxValue / density);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午4:39:13
     * <br> UpdateTime: 2016-11-24,下午4:39:13
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param spValue
     *         sp值
     */
    public static int sp2px(int spValue) {
        if (spValue <= 0) {
            return 0;
        }
        //        Context context = BaseApplication.getInstance().getApplicationContext();
        //        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}  