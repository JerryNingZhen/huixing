package com.android.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;

import com.hx.huixing.BuildConfig;
import com.hx.huixing.R;
import com.android.base.configs.RequestCode;

import java.io.File;


/**
 * Intent辅助类.
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class IntentUtil {

    //standard-默认模式              调用startActivity()方法就会产生一个新的实例。
    //singleTop-栈顶复用模式         在这个模式下的Activity实例，如果不位于栈顶，会产生一个新的实例。
    //singleTask-栈内复用模式        调用startActivity()方法会在一个新的task中产生Activity实例，以后每次调用都会使用这个，不会去产生新的实例了。
    //singleInstance-全局唯一模式    跟singleTask基本上是一样；只有一个区别：在这个模式下的Activity实例所处的task中，只能有这个activity实例。
    //
    //FLAG_ACTIVITY_NEW_TASK      默认的跳转类型,它会重新创建一个新的Activity
    //FLAG_ACTIVITY_SINGLE_TOP    这个FLAG就相当于加载模式中的singletop，比如说原来栈中情况是A,B,C,D在D中启动D，栈中的情况还是A,B,C,D
    //FLAG_ACTIVITY_CLEAR_TOP     这个FLAG就相当于加载模式中的SingleTask，这种FLAG启动的Activity会把要启动的Activity之上的Activity全部弹出栈空间。类如：原来栈中的情况是A,B,C,D这个时候从D中跳转到B，这个时候栈中的情况就是A,B了
    //                            如果是设置launchMode为standard（默认）时，目标Activity（gotoClass）会销毁重建;设置了launchMode为singleTop或者设置标志位为FLAG_ACTIVITY_SINGLE_TOP时，就不会销毁重建目标Activity
    //FLAG_ACTIVITY_NO_HISTORY   意思就是说用这个FLAG启动的Activity，一旦退出，它不会存在于栈中，比方说！原来是A,B,C这个时候再C中以这个FLAG启动D的，D再启动E，这个时候栈中情况为A,B,C,E。
    //FLAG_ACTIVITY_BROUGHT_TO_FRONT 这个Flag的意思，比如我现在有一个A，然后在A中启动B，并设置FLAG_ACTIVITY_BROUGHT_TO_FRONT这个启动标记，那么B就是以FLAG_ACTIVITY_BROUGHT_TO_FRONT启动的。然后在B中启动C，此时栈就是A,B,C。如果这个时候在C中启动B，那么栈的情况会是A,C,B
    //FLAG_ACTIVITY_REORDER_TO_FRONT 这个Flag的意思，如果在栈中有A,B,C三个Activity，并且是正常启动的，此时在C中启动B的话，还是会变成A,C,B的。 如果使用了标志 FLAG_ACTIVITY_CLEAR_TOP，那这个FLAG_ACTIVITY_REORDER_TO_FRONT标志会被忽略。
    //                               这个Flag的意思：如果这个activity已经启动了，就不产生新的activity，而只是把这个activity实例加到栈顶来就可以了。 这个标志一般不需要我们去设置，在launchMode为singleTask时，默认设置该标志。
    //FLAG_ACTIVITY_CLEAR_TASK        会将与目标Activity关联的task清空，目标Activity会成为task的根Activity，这个标志只能与FLAG_ACTIVITY_NEW_TASK结合使用。

    /**
     * 跳转至指定activity.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午3:48:25
     * <br> UpdateTime: 2016-11-24,下午3:48:25
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文环境.
     * @param gotoClass
     *         待启动的界面.
     */
    public static void gotoActivity(Context context, Class<?> gotoClass) {
        gotoActivity(context, gotoClass, null);
    }

    /**
     * 携带传递数据跳转至指定activity.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午3:47:51
     * <br> UpdateTime: 2016-11-24,下午3:47:51
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文环境.
     * @param gotoClass
     *         待启动的界面.
     * @param bundle
     *         携带数据.
     */
    public static void gotoActivity(Context context, Class<?> gotoClass, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(context, gotoClass);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }

    /**
     * 跳转至指定activity.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午3:47:41
     * <br> UpdateTime: 2016-11-24,下午3:47:41
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文.
     * @param gotoClass
     *         待启动的界面.
     * @param requestCode
     *         请求码.
     */
    public static void gotoActivityForResult(Context context, Class<?> gotoClass, int requestCode) {
        gotoActivityForResult(context, gotoClass, null, requestCode);
    }

    /**
     * 携带传递数据跳转至指定activity.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午3:47:30
     * <br> UpdateTime: 2016-11-24,下午3:47:30
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文.
     * @param gotoClass
     *         待启动的界面.
     * @param bundle
     *         携带数据.
     * @param requestCode
     *         请求码.
     */
    public static void gotoActivityForResult(Context context, Class<?> gotoClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(context, gotoClass);
        ((Activity) context).startActivityForResult(intent, requestCode);
        ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }


    /**
     * 跳转至指定activity,并关闭当前activity.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午3:47:21
     * <br> UpdateTime: 2016-11-24,下午3:47:21
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文.
     * @param gotoClass
     *         待启动的界面.
     */
    public static void gotoActivityAndFinish(Context context, Class<?> gotoClass) {
        gotoActivityAndFinish(context, gotoClass, null);
    }

    /**
     * 携带传递数据跳转至指定activity,并关闭当前activity.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午3:47:12
     * <br> UpdateTime: 2016-11-24,下午3:47:12
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文.
     * @param gotoClass
     *         待启动的界面.
     * @param bundle
     *         携带数据.
     */
    public static void gotoActivityAndFinish(Context context, Class<?> gotoClass, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(context, gotoClass);
        context.startActivity(intent);
        ((Activity) context).finish();
        ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }

    /**
     * 单例模式跳转至指定activity
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午3:48:18
     * <br> UpdateTime: 2016-11-24,下午3:48:18
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文.
     * @param gotoClass
     *         待启动的界面.
     */
    public static void gotoActivityToTopAndFinish(Context context, Class<?> gotoClass) {
        gotoActivityToTopAndFinish(context, gotoClass, null);
    }

    /**
     * 单例模式跳转并携带数据
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午3:48:00
     * <br> UpdateTime: 2016-11-24,下午3:48:00
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文
     * @param gotoClass
     *         待启动的界面.
     * @param bundle
     *         Bundle
     */
    public static void gotoActivityToTopAndFinish(Context context, Class<?> gotoClass, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(context, gotoClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        ((Activity) context).finish();
        ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }

    /**
     * 携带传递数据跳转至指定activity,并关闭当前activity.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午3:47:01
     * <br> UpdateTime: 2016-11-24,下午3:47:01
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文环境.
     * @param gotoClass
     *         待启动的界面.
     */
    public static void gotoActivityToTop(Context context, Class<?> gotoClass) {
        gotoActivityToTop(context, gotoClass, null);
    }

    /**
     * 携带传递数据跳转至指定activity,并关闭当前activity.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午3:46:29
     * <br> UpdateTime: 2016-11-24,下午3:46:29
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文环境.
     * @param gotoClass
     *         待启动的界面.
     * @param bundle
     *         携带数据.
     */
    public static void gotoActivityToTop(Context context, Class<?> gotoClass, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(context, gotoClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//值得注意的是如果是设置launchMode为standard（默认）时，目标Activity（gotoClass）会销毁重建
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }

    /**
     * 携带传递数据跳转至指定activity,
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午3:46:51
     * <br> UpdateTime: 2016-11-24,下午3:46:51
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文环境.
     * @param gotoClass
     *         待启动的界面.
     * @param requestCode
     *         下一个页面关闭时的返回码
     */
    public static void gotoActivityToTopForResult(Context context, Class<?> gotoClass, int requestCode) {
        gotoActivityToTopForResult(context, gotoClass, null, requestCode);
    }

    /**
     * 携带传递数据跳转至指定activity,
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午3:46:38
     * <br> UpdateTime: 2016-11-24,下午3:46:38
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文环境.
     * @param gotoClass
     *         待启动的界面.
     * @param bundle
     *         Bundle
     * @param requestCode
     *         下一个页面关闭时的返回码
     */
    public static void gotoActivityToTopForResult(Context context, Class<?> gotoClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(context, gotoClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        ((Activity) context).startActivityForResult(intent, requestCode);
        ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }

    /**
     * 描述：关闭某个activity
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午3:46:07
     * <br> UpdateTime: 2016-11-24,下午3:46:07
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         需要关闭的界面
     */
    public static void finish(Context context) {
        ((Activity) context).finish();
        ((Activity) context).overridePendingTransition(R.anim.exit_enter, R.anim.exit_exit);
    }

    /* **************************************系统相关跳转******************************************/

    /**
     * 跳转到发送短信界面
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午3:46:18
     * <br> UpdateTime: 2016-11-24,下午3:46:18
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文
     * @param phoneNum
     *         手机号码
     * @param content
     *         短信内容
     */
    public static void gotoSendMessageActivity(Context context, String phoneNum, String content) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + phoneNum));
        intent.putExtra("sms_body", content);
        context.startActivity(intent);
    }

    /**
     * 跳转拨号界面
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午3:46:18
     * <br> UpdateTime: 2016-11-24,下午3:46:18
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文
     * @param phoneNum
     *         手机号码
     */
    public static void gotoInputPhoneNumActivity(Context context, String phoneNum) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNum));
        context.startActivity(intent);
        //        ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }

    /**
     * 直接拨打电话
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-24,下午3:46:18
     * <br> UpdateTime: 2016-11-24,下午3:46:18
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文
     * @param phoneNum
     *         手机号码
     */
    public static void gotoCallPhoneActivity(Context context, String phoneNum) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNum));
            context.startActivity(intent);
            //            ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
        } catch (Exception e) {
            gotoInputPhoneNumActivity(context, phoneNum);
        }
    }

    /**
     * 打开系统设置界面
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-5,下午3:53:58
     * <br> UpdateTime: 2016-1-5,下午3:53:58
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         Context
     */
    public static void gotoSystemSettingActivity(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_SETTINGS);
        if (!(context instanceof Activity)) {
            // 从非页面启动的，需要添加FLAG_ACTIVITY_NEW_TASK
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
        //        ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }

    /**
     * 打开网络设置界面
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-5,下午3:53:58
     * <br> UpdateTime: 2016-1-5,下午3:53:58
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         Context
     */
    public static void gotoWifiActivity(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_WIFI_SETTINGS);
        if (!(context instanceof Activity)) {
            // 从非页面启动的，需要添加FLAG_ACTIVITY_NEW_TASK
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
        //        ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }

    /***
     * 调用系统相机拍照
     *
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/11/27 20:56
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/11/27 20:56
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context    Context
     */
    public static void takePhoto(Context context, String path) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT); // 根据文件地址创建文件
        // String path = ConfigFile.PATH_IMAGES + "/fzd_" + System.currentTimeMillis() + ".jpg";
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", new File(path));
        } else {
            uri = Uri.fromFile(new File(path));
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        ((Activity) context).startActivityForResult(intent, RequestCode.REQUEST_CODE_TAKE_PHOTO);
    }

    /***
     * 系统相册选取图片
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/11/27 20:56
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/11/27 20:56
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     * @param context    Context
     */
    public static void chosePhoto(Context context) {
        // 使用该方式选择图 当前activity 不能设置 singleTop、singleTask、singleInstance
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);//  Pick an item fromthe data
        intent.setType("image/*");
        ((Activity) context).startActivityForResult(intent, RequestCode.REQUEST_CODE_CHOSE_PHOTO);
    }
}