package com.hx.huixing.uitl;

/**
 * <br> Description 不好归类的方法放在这里
 * <br> Author: 谭俊
 * <br> PackageName com.tan.mvpdemo.activityMvp
 * <br> Date: 2018/7/21
 */
public class ComUtils {

    public static int transToInt(String str){
        int result = 0;
        if (str.contains(".")){
            int index = str.indexOf(".");
            result = Integer.parseInt(str.substring(0, index));
        }
        return result;
    }

}
