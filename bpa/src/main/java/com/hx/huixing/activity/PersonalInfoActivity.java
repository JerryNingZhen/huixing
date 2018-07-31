package com.hx.huixing.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.base.BaseApplication;
import com.android.base.configs.ConfigFile;
import com.android.base.configs.ConfigServer;
import com.android.base.utils.FileUtil;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.ToastUtil;
import com.android.base.utils.dialog.CustomDialog;
import com.android.base.utils.dialog.DialogUtil;
import com.android.base.utils.imageutils.GetPathUtil;
import com.android.base.utils.picasso.PicassoUtil;
import com.android.base.widget.TitleView;
import com.google.gson.Gson;
import com.hx.huixing.R;
import com.hx.huixing.activityMvp.BasePresenter;
import com.hx.huixing.bean.CountBean;
import com.hx.huixing.bean.MyInfoBean;
import com.hx.huixing.common.net.JsonCallBack;
import com.hx.huixing.common.net.RetrofitUtils;
import com.hx.huixing.config.RequestCode;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * <br> Description 个人资料
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.activity
 * <br> Date: 2018/7/19
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */

@RuntimePermissions
public class PersonalInfoActivity extends BaseActivity implements View.OnClickListener {

    /** 标题 */
    private TitleView title_view;
    /** 头像 */
    private ImageView iv_user;
    /** 昵称（用来点击） */
    private RelativeLayout rl_nickname;
    /** 昵称（用来展示） */
    private TextView tv_nickname;
    /** 个人简介 */
    private TextView tv_self_intro;
    /** 描述内容 */
    private TextView tv_des;

    private MyInfoBean bean;
    private String name;
    private String selfDes;
    private String headUrl;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void findViews() {
        title_view = findViewById(R.id.title_view);
        iv_user = findViewById(R.id.iv_user);
        rl_nickname = findViewById(R.id.rl_nickname);
        tv_nickname = findViewById(R.id.tv_nickname);
        tv_self_intro = findViewById(R.id.tv_self_intro);
        tv_des = findViewById(R.id.tv_des);
    }

    @Override
    protected void initGetData() {
        Bundle bundle = getIntent().getExtras();
        bean = (MyInfoBean) bundle.getSerializable("info");
        name = bean.getDatas().getRealName();//姓名
        selfDes = bean.getDatas().getPersonIntro(); //个人简介
        headUrl = bean.getDatas().getUserPic();
    }

    @Override
    protected void init() {
        title_view.setLeftBtnImg();
        title_view.setTitle(R.string.personal_info);
        title_view.setRightBtnTxt("编辑", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("selfDes", selfDes);
                IntentUtil.gotoActivity(PersonalInfoActivity.this, EditPersonInfoActivity.class, bundle);
            }
        });

        tv_nickname.setText(name);
        tv_des.setText(selfDes);
        PicassoUtil.loadImage(this, headUrl, iv_user);
    }

    @Override
    protected void widgetListener() {
        iv_user.setOnClickListener(this);
        /*rl_nickname.setOnClickListener(this);
        tv_self_intro.setOnClickListener(this);*/
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_user: //头像
                showChoseDialog();

                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //getInfo();
    }

    /**
     * 选择相册 相机对话框
     */
    public void showChoseDialog() {
        DialogUtil.showIosDialog(this, null, getResources().getStringArray(R.array.image_operation), Gravity.BOTTOM, null, new CustomDialog.OnDialogClickListener() {

            @Override
            public void onClick(CustomDialog dialog, int id, Object object) {
                dialog.dismiss();
                switch (id) {
                    case 1:// 相册
                        checkPhoto();
                        break;
                    case 0:// 相机
                        tackPic();
                        break;
                }
            }
        });
    }

    /** 拍照 */
    private void tackPic() {
        PersonalInfoActivityPermissionsDispatcher.showCameraWithPermissionCheck(this);
    }

    private void checkPhoto(){
        PersonalInfoActivityPermissionsDispatcher.showPermissionWithPermissionCheck(this);
    }

    /**
     * 拍照 保存图片的本地缓存路径
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == RequestCode.REQUEST_CODE_PIC){
                if (data != null) {
                    Uri uri = data.getData();
                    String picPath;//相册路径
                    if (uri.toString().startsWith("file:///storage/emulated")){
                        picPath = uri.toString().replace("file:///storage/emulated", "/storage/emulated");
                    }else {
                        //判断手机系统版本号
                        if (Build.VERSION.SDK_INT >= 19) {
                            //4.4及以上系统使用这个方法处理图片
                            picPath = GetPathUtil.getPath(this, uri);
                        } else {
                            //4.4以下系统使用这个方法处理图片
                            picPath = null;
                            //通过Uri和selection来获取真实的图片路径
                            Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null) {
                                if (cursor.moveToFirst()) {
                                    picPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                                }
                                cursor.close();
                            }
                        }
                    }
                    if (!TextUtils.isEmpty(picPath)){
                        // 解析本地图片，获得图片尺寸
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        // 不生成Bitmap 只是获取bitmap的宽高，相对省内存
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(picPath, options);
                        if (options.outWidth == -1 || options.outHeight == -1){
                            ToastUtil.showToast(PersonalInfoActivity.this, "图片已损坏，请重新选择");
                            return;
                        }
                        /** 上传图片 */
                        changeLogo(picPath);
                    }
                }
            }
            else if (requestCode == RequestCode.REQUEST_CODE_PHOTO){ //相机
                changeLogo(tempPath);
            }else if (requestCode == RequestCode.REQUEST_CODE_PREVIEW){ //预览
                //mPresenter.queryAllImg(imeiId);
                //getInfo();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PersonalInfoActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    /** 拍照权限 */
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showPermission() {
        FileUtil.createAllFile();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);//Pick an item from the data
        intent.setType("image/*");
        startActivityForResult(intent, RequestCode.REQUEST_CODE_PIC);
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showDenied() {
        ToastUtil.showToast(this, "存储权限被拒绝");
    }

    /**
     * 拍照 保存图片的本地缓存路径
     */
    private String tempPath = "";
    /** 相机权限 可以的存储权限写一起 后面整理 */
    @NeedsPermission(Manifest.permission.CAMERA)
    void showCamera() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        tempPath = ConfigFile.PATH_IMAGES + "/dk_" + System.currentTimeMillis() + ".jpg";

        tempPath = ConfigFile.PATH_IMAGES + "/dk_" + System.currentTimeMillis() + ".jpg";
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(this, "com.hxyd.dyt.fileprovider", new File(tempPath));
            //								uri = FileProvider.getUriForFile(RecordedOrderActvityThree.this, BuildConfig.APPLICATION_ID + ".fileProvider", new File(tempPath));
        } else {
            uri = Uri.fromFile(new File(tempPath));
        }
        //							Uri uri = Uri.fromFile(new File(tempPath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_PHOTO);

    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void showDialog(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("权限申请")
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.cancel();
                    }
                }).setCancelable(false)
                .show();
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void showCameraDenied() {
        ToastUtil.showLongToast(this,"相机权限被拒绝");
    }

    /** 上传头像 */
    private void changeLogo(final String photoUrl){
        showProgress("正在上传中...",false);
        Map<String, String> map = new TreeMap<>();
        map.put("userId", BaseApplication.getInstance().getUserInfoBean().getId());
        map.put("passWord", BaseApplication.getInstance().getUserInfoBean().getUserPwd());
        map.put("userPic", photoUrl);
        RetrofitUtils.getInstance().normalGet(ConfigServer.SERVER_API_URL + ConfigServer.METHOD_CHANGELOGO, map, new JsonCallBack() {
            @Override
            public void next(String response) {
                Log.e("tanjun", response);
                dismissProgress();
                CountBean bean = new Gson().fromJson(response, CountBean.class);
                int code = Integer.parseInt(bean.getCode());
                if (code == 0){
                    ToastUtil.showToast(PersonalInfoActivity.this, bean.getMsg());
                    PicassoUtil.loadImage(PersonalInfoActivity.this, photoUrl, iv_user);
                }
            }

            @Override
            public void error(Throwable e) {

            }

            @Override
            public void startLoading() {

            }

            @Override
            public void closeLoading() {

            }
        });
    }

   /* private void getInfo(){
        Map<String, String> map = new TreeMap<>();
        map.put("userId", BaseApplication.getInstance().getUserInfoBean().getId());
        map.put("loginUser", BaseApplication.getInstance().getUserInfoBean().getId());
        RetrofitUtils.getInstance().normalGet(ConfigServer.SERVER_API_URL + ConfigServer.MOTHED_QUARYUSERS, map, new JsonCallBack() {
            @Override
            public void next(String response) {
                MyInfoBean bean = new Gson().fromJson(response, MyInfoBean.class);
                *//** 名称 *//*
                //tv_nickname.setText(bean.getDatas().getRealName());
                *//** 头像 *//*
                PicassoUtil.loadImage(PersonalInfoActivity.this, bean.getDatas().getUserPic(),iv_user);
                //tv_des.setText(bean.getDatas().getPersonIntro());
            }

            @Override
            public void error(Throwable e) {

            }

            @Override
            public void startLoading() {

            }

            @Override
            public void closeLoading() {

            }
        });
    }*/

}
