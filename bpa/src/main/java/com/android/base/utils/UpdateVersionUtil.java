package com.android.base.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.android.base.bean.BaseBean;
import com.android.base.bean.ResponseBean;
import com.android.base.bean.UpdateBean;
import com.android.base.configs.ConfigFile;
import com.android.base.configs.ConfigServer;
import com.android.base.configs.ConstantKey;
import com.android.base.configs.RequestCode;
import com.android.base.executor.BaseTask;
import com.android.base.executor.DownProgressDialogUtil;
import com.android.base.executor.RequestExecutor;
import com.android.base.interfaces.OnDownLoadCallBack;
import com.android.base.mvp.model.HttpBiz;
import com.android.base.mvp.model.HttpOkBiz;
import com.android.base.service.UpdateService;
import com.android.base.utils.dialog.CustomDialog;
import com.android.base.utils.dialog.DialogUtil;
import com.hx.huixing.BuildConfig;
import com.hx.huixing.R;

import java.io.File;
import java.util.HashMap;

/**
 * 检测更新 工具类
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/5
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class UpdateVersionUtil {

    private Activity activity;
    private boolean isShowToast = false;

    public UpdateVersionUtil(Activity activity, boolean isShowToast) {
        this.activity = activity;
        this.isShowToast = isShowToast;
    }

    /**
     * 检测更新
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-25,上午10:38:53
     * <br> UpdateTime: 2016-11-25,上午10:38:53
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public void checkUpdate() {
        RequestExecutor.addTask(new BaseTask(activity, activity.getString(R.string.process_handle_wait), false) {

            @Override
            public ResponseBean sendRequest() {
                HashMap<String, String> params = new HashMap<>();
                params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_INQUIRYAPPVERSION);
                //                params.put("type", "1");
                //                params.put("verCode", "1");

                ResponseBean result = HttpOkBiz.getInstance().sendGet(params);
                if (HttpBiz.checkSuccess(result)) {
                    //                    result.setObject(GsonUtil.getInstance().json2Bean((String) result.getObject(), UpdateBean.class));
                    BaseBean.setResponseObject(result, UpdateBean.class);
                }
                return result;
            }

            @Override
            public void onSuccess(ResponseBean result) {
                bean = (UpdateBean) result.getObject();

                // 比较当前版本跟服务器版本 version——name的大小
                if (!TextUtils.isEmpty(bean.getVersionCode())) {
                    // if (Integer.parseInt(bean.getVersionCode()) > 1) {
                    if (Integer.parseInt(bean.getVersionCode()) > SystemUtil.getAppVersionCode()) {
                        DialogUtil.showMessageDg(activity, "发现新版本", bean.getVersionInfo(), "取消", "确定", new CustomDialog.OnDialogClickListener() {
                            @Override
                            public void onClick(CustomDialog dialog, int id, Object object) {
                                if (isMust()) {
                                    AppManagerUtil.getAppManager().exitApp();
                                } else {
                                    dialog.dismiss();
                                }
                            }
                        }, new CustomDialog.OnDialogClickListener() {
                            @Override
                            public void onClick(CustomDialog dialog, int id, Object object) {

                                // TODO 下载
                                dialog.dismiss();
                                PermissionUtils.getInstance(new PermissionUtils.PermissionGrant() {
                                    @Override
                                    public void onPermissionGranted(Object permissionName, boolean isSuccess) {
                                        if (isSuccess) {
                                            LogUtil.i(permissionName + "........." + true);
                                        } else {
                                            LogUtil.e(permissionName + "........." + false);
                                        }
                                        switch (String.valueOf(permissionName)) {
                                            case Manifest.permission.RECORD_AUDIO:
                                                break;
                                            case Manifest.permission.READ_CONTACTS:
                                                break;
                                            case Manifest.permission.CALL_PHONE:
                                                break;
                                            case Manifest.permission.CAMERA:
                                                break;
                                            case Manifest.permission.ACCESS_FINE_LOCATION:
                                                break;
                                            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                                                if (isSuccess) {
                                                    FileUtil.createAllFile();
                                                    Intent intent = new Intent();
                                                    intent.putExtra(ConstantKey.INTENT_KEY_APK_PATH, bean.getUrl());
                                                    intent.setClass(activity, UpdateService.class);
                                                    //baseUI.startService(intent);
                                                    downLoadApk(bean.getUrl());
                                                } else {
                                                    ToastUtil.showToast(activity, "您已拒绝了文件存储权限");
                                                    if (isMust()) {
                                                        AppManagerUtil.getAppManager().exitApp();
                                                    }
                                                }
                                                break;
                                            case Manifest.permission.READ_SMS:
                                                break;
                                            case PermissionUtils.REQUEST_MULTIPLE_PERMISSION:
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                }).requestPermission(activity, PermissionUtils.REQUEST_PERMISSIONS[5]);
                            }
                        });
                    } else {
                        // com.amos.bpademo.baseUI.MainActivity new Intent(context, Class.forName("com.amos.bpademo.baseUI.baseUI.MainActivity"));
                        //  if (!(baseUI.getLocalClassName().equals("com.amos.bpademo.baseUI.MainActivity"))) {
                        if (isShowToast) {
                            ToastUtil.showToast(activity, "当前已是最新版本");
                        }
                    }
                }
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(activity, result.getInfo());
            }
        });
    }

    /** 检测更新信息 */
    private UpdateBean bean;
    /** 更新进度 */
    private DownProgressDialogUtil downProgressDialogUtil;

    private boolean isMust() {
        return false;
        //        return bean != null && (bean.getForce().equals("1"));
    }

    /**
     * apk下载
     * <p>
     * <br> Version: 6.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/5 18:13
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/5 18:13
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         下载路径
     */
    private void downLoadApk(final String url) {
        if(TextUtils.isEmpty(url.trim())){
            return;
        }
        String apkLocal = StringUtil.getLocalCachePath(url, ConfigFile.PATH_DOWNLOAD);
        File localFile = new File(apkLocal);

        // 文件已经存在，无需下载
        if (localFile.exists()) {
            installApk(url);
            return;
        }

        if (downProgressDialogUtil == null) {
            downProgressDialogUtil = new DownProgressDialogUtil(activity);
        }
        downProgressDialogUtil.showDialog("提示", "正在下载中，请稍后...", !isMust());

        RequestExecutor.addTask(new BaseTask() {

            @Override
            public ResponseBean sendRequest() {
                return HttpBiz.getInstance().downLoadFile(url, new OnDownLoadCallBack() {
                    @Override
                    public void ResultProgress(final int press, long fileSize, long finishSize) {
                        if (downProgressDialogUtil != null) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    downProgressDialogUtil.setProgress(press);
                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void onSuccess(ResponseBean result) {
                installApk(url);
                downProgressDialogUtil.dismissDialog();
                ToastUtil.showToast(activity, result.getInfo());
            }

            @Override
            public void onFail(ResponseBean result) {
                downProgressDialogUtil.dismissDialog();
                ToastUtil.showToast(activity, result.getInfo());
            }
        });
    }

    /**
     * 安装apk
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-25,上午11:02:07
     * <br> UpdateTime: 2016-11-25,上午11:02:07
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void installApk(String url) {
        String apkLocal = StringUtil.getLocalCachePath(url, ConfigFile.PATH_DOWNLOAD);
        //                String apkLocal = ConfigFile.PATH_DOWNLOAD + StringUtil.getFileName(url);
        File file = (new File(apkLocal));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            //provider authorities
            Uri apkUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //Granting Temporary Permissions to a URI
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            Uri apkUri = Uri.fromFile(file);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }

        activity.startActivityForResult(intent, RequestCode.REQUEST_CODE_INSTALL_APK);

        if (isMust()) {
            AppManagerUtil.getAppManager().exitApp();
        }
    }
}
