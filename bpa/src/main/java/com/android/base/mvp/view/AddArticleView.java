package com.android.base.mvp.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.activity.AddArticleActivity;
import com.android.base.activity.ArticleDraftActivity;
import com.android.base.activity.ArticlePreviewActivity;
import com.android.base.bean.ArticleAddBean;
import com.android.base.bean.ResponseBean;
import com.android.base.configs.ConfigFile;
import com.android.base.configs.ConfigServer;
import com.android.base.configs.ConstantKey;
import com.android.base.configs.RequestCode;
import com.android.base.executor.BaseTask;
import com.android.base.executor.RequestExecutor;
import com.android.base.mvp.baseclass.MvpBaseView;
import com.android.base.utils.BitmapUtil;
import com.android.base.utils.DateUtil;
import com.android.base.utils.FileUtil;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.KeyboardUtil;
import com.android.base.utils.LogUtil;
import com.android.base.utils.PermissionUtils;
import com.android.base.utils.ScreenUtil;
import com.android.base.utils.ToastUtil;
import com.android.base.utils.dialog.CustomDialog;
import com.android.base.utils.dialog.DialogUtil;
import com.android.base.utils.http.HttpOkUtil;
import com.android.base.utils.imageutils.GetPathUtil;
import com.android.base.utils.imageutils.ImageCompressUtil;
import com.android.base.widget.TitleView;
import com.hx.huixing.R;

import java.io.File;

/**
 * 发帖 View模块
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class AddArticleView extends MvpBaseView<AddArticleActivity> {

    /** TitleView */
    private TitleView titleview;
    private ImageView img_content;
    private TextView edit_title;
    private TextView edit_content;
    private ArticleAddBean bean;

    public AddArticleView(AddArticleActivity baseUI) {
        super(baseUI);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_add_article;
    }

    @Override
    public void findViews() {
        titleview = findViewByIds(R.id.title_view);
        img_content = findViewByIds(R.id.img_content);
        edit_title = findViewByIds(R.id.edit_title);
        edit_content = findViewByIds(R.id.edit_content);
    }

    @Override
    public void init(Bundle bundle) {
        //titleview.setTitle(baseUI.getString(R.string.activity_image_browse));

        if (bundle != null) {
            bean = (ArticleAddBean) bundle.getSerializable(ConstantKey.INTENT_KEY_DATA);
            if (bean != null) {
                if (!TextUtils.isEmpty(bean.getTitlePage())) {
                    imgTitle = bean.getTitlePage();
                    if (bean.getTitlePage().contains("storage/emulated")) {
                        float height2Width = BitmapUtil.height2Width(bean.getTitlePage());
                        img_content.getLayoutParams().height = (int) (ScreenUtil.getScreenWidthPx() * height2Width);

                        Bitmap bitmap = ImageCompressUtil.getBitmap(bean.getTitlePage());
                        if (bitmap != null) {
                            //设置图片充满ImageView控件
                            img_content.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            //等比例缩放
                            img_content.setAdjustViewBounds(true);
                            img_content.setImageBitmap(bitmap);
                            //                    bitmap.recycle();
                            //                    PicassoUtil.loadImage(baseUI, imgTitle, img_content);
                        }
                    } else {
                        downLoadImage();
                    }
                }
                edit_title.setText(bean.getTextTitle());
                edit_content.setText(Html.fromHtml(bean.getTextContent()));
            }
        }
    }


    @Override
    public void setViewData(Object object) {

    }

    @Override
    public void widgetListener() {
        titleview.setLeftBtnTxt("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.hideKeyBord(titleview);
                if (!TextUtils.isEmpty(bean.getReviewId())) {// 编辑文章
                    baseUI.finishActivity();
                } else {
                    saveDraft();
                }
            }
        });

        titleview.setRightBtnTxt("预览", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardUtil.hideKeyBord(titleview);
                ArticleAddBean bean = checkData();
                //                if (TextUtils.isEmpty(bean.getTitlePage())) {
                //                    showToast("文章封面照片不能为空");
                //                    return;
                //                }
                if (TextUtils.isEmpty(bean.getTextTitle())) {
                    showToast("文章标题不能为空");
                    return;
                }
                if (TextUtils.isEmpty(bean.getTextContent())) {
                    showToast("文章内容不能为空");
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstantKey.INTENT_KEY_DATA, bean);
                IntentUtil.gotoActivityForResult(baseUI, ArticlePreviewActivity.class, bundle, RequestCode.REQUEST_CODE_ADD_ARTICLE);
            }
        });

        if (TextUtils.isEmpty(bean.getReviewId())) {// 编辑文章
            titleview.setRightBtnImgNew(R.drawable.icon_draft_right, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IntentUtil.gotoActivityToTopForResult(baseUI, ArticleDraftActivity.class, RequestCode.REQUEST_CODE_ADD_ARTICLE);
                }
            });
        }
        img_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPhoto();
            }
        });
    }

    /**
     * 保存草稿箱
     */
    public void saveDraft() {
        baseUI.getMvpPresenter().addDraft(checkData());
    }

    /**
     * 返回事件
     */
    public void goBack() {
        baseUI.finishActivity();
    }


    private ArticleAddBean checkData() {
        if (bean == null) {
            bean = new ArticleAddBean();
        }
        //        bean.setImageTitle("https://goss.vcg.com/3a750b70-faa0-11e7-a964-b7ed0e8248ba.jpg");
        bean.setTitlePage(imgTitle);
        bean.setTextTitle(edit_title.getText().toString());
        bean.setTextContent(edit_content.getText().toString());
        if (TextUtils.isEmpty(bean.getDraftId())) {
            if (TextUtils.isEmpty(bean.getCreateTime())) {
                bean.setCreateTime(DateUtil.getDate());
            }
        } else {
            bean.setUpdateTime(DateUtil.getDate());
        }
        return bean;
    }

    private boolean isCamera = false;
    private boolean isStorage = false;

    private void checkPhoto() {
        DialogUtil.showIosDialog(baseUI, null, baseUI.getResources().getStringArray(R.array.image_operation), Gravity.BOTTOM, null, new CustomDialog.OnDialogClickListener() {

            @Override
            public void onClick(CustomDialog dialog, int id, Object object) {
                dialog.dismiss();
                switch (id) {
                    case 1:// 相册
                        PermissionUtils.getInstance(new PermissionUtils.PermissionGrant() {
                            @Override
                            public void onPermissionGranted(Object permissionNameOrCode, boolean isSuccess) {
                                LogUtil.i(permissionNameOrCode + "........." + isSuccess);
                                if (String.valueOf(permissionNameOrCode).equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                    if (isSuccess) {
                                        FileUtil.createAllFile();
                                        Intent intent = new Intent();
                                        //intent.setAction("android.intent.action.GET_CONTENT");
                                        intent.setAction(Intent.ACTION_PICK);//Pick an item fromthe data
                                        intent.setType("image/*");
                                        baseUI.startActivityForResult(intent, RequestCode.REQUEST_CODE_CHOSE_PHOTO);
                                    } else {
                                        DialogUtil.showMessageDg(baseUI, "温馨提示", "亲，您还没有授权存储权限", "", "知道了", null, new CustomDialog.OnDialogClickListener() {
                                            @Override
                                            public void onClick(CustomDialog dialog, int id, Object object) {
                                                dialog.dismiss();
                                            }
                                        }, Gravity.CENTER);
                                    }
                                }
                            }
                        }).requestPermission(baseUI, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        break;
                    case 0:// 相机
                        // 权限数组
                        String[] REQUEST_PERMISSIONS = {
                                Manifest.permission.CAMERA,                         // android.permission-group.CAMERA
                                Manifest.permission.WRITE_EXTERNAL_STORAGE          // android.permission-group.STORAGE
                        };
                        PermissionUtils.getInstance(new PermissionUtils.PermissionGrant() {
                            @Override
                            public void onPermissionGranted(Object permissionNameOrCode, boolean isSuccess) {
                                LogUtil.i(permissionNameOrCode + "........." + isSuccess);
                                switch (String.valueOf(permissionNameOrCode)) {
                                    case Manifest.permission.CAMERA:
                                        isCamera = isSuccess;
                                        break;
                                    case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                                        isStorage = isSuccess;
                                        if (isSuccess) {
                                            FileUtil.createAllFile();
                                        }
                                        break;
                                    case PermissionUtils.REQUEST_MULTIPLE_PERMISSION:
                                        if (isSuccess) {
                                            Intent intent = new Intent();
                                            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                                            intent.addCategory(Intent.CATEGORY_DEFAULT);              // 根据文件地址创建文件

                                            imgTitle = ConfigFile.PATH_IMAGES + "/fzd_" + System.currentTimeMillis() + ".jpg";
                                            Uri uri = Uri.fromFile(new File(imgTitle));
                                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                            baseUI.startActivityForResult(intent, RequestCode.REQUEST_CODE_TAKE_PHOTO);

                                        } else {
                                            String tips = "";
                                            if (!isCamera) {
                                                tips = tips + "相机";
                                            }

                                            if (!isStorage) {
                                                tips = tips + "、存储";
                                            }

                                            if (!TextUtils.isEmpty(tips)) {
                                                if (tips.startsWith("、")) {
                                                    tips = tips.replaceFirst("、", "");
                                                }
                                                tips = "亲，您还没有授权" + tips + "权限";
                                                DialogUtil.showMessageDg(baseUI, "温馨提示", tips, "", "知道了", null, new CustomDialog.OnDialogClickListener() {
                                                    @Override
                                                    public void onClick(CustomDialog dialog, int id, Object object) {
                                                        dialog.dismiss();
                                                    }
                                                }, Gravity.CENTER);
                                            }
                                        }
                                }
                            }
                        }).requestPermission(baseUI, REQUEST_PERMISSIONS);
                        break;
                }
            }
        });
    }

    /**
     * 身份证反面照片路径
     */
    private String imgTitle = "";

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RequestCode.REQUEST_CODE_CHOSE_PHOTO) {
                if (data != null) {
                    final Uri uri = data.getData();
                    String path;
                    if (uri.toString().startsWith("file:///storage/emulated")) {
                        path = uri.toString().replace("file:///storage/emulated", "/storage/emulated");
                    } else {
                        //判断手机系统版本号
                        if (Build.VERSION.SDK_INT >= 19) {
                            //4.4及以上系统使用这个方法处理图片
                            path = GetPathUtil.getPath(baseUI, uri);
                        } else {
                            //4.4以下系统使用这个方法处理图片
                            path = null;
                            //通过Uri和selection来获取真实的图片路径
                            Cursor cursor = baseUI.getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null) {
                                if (cursor.moveToFirst()) {
                                    path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                                }
                                cursor.close();
                            }
                        }
                    }

                    if (!TextUtils.isEmpty(path)) {
                        //                        if (FileUtil.getFileSize(path) / 1024 < 300) {
                        //                            ToastUtil.showToast(baseUI, "图片像素太低，请重新上传");
                        //                            return;
                        //                        }
                        // 解析本地图片，获得图片尺寸
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        // 不生成Bitmap 只是获取bitmap的宽高，相对省内存
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(path, options);
                        if (options.outWidth == -1 || options.outHeight == -1) {
                            ToastUtil.showToast(baseUI, "图片已损坏，请重新选择");
                            LogUtil.i("图片已损坏，请重新选择");
                            return;
                        }
                        LogUtil.i(uri + ">>>>" + path + ">>>");

                        imgTitle = ImageCompressUtil.compressImg(path);
                        setViewBottom();
                    }
                }
            } else if (requestCode == RequestCode.REQUEST_CODE_TAKE_PHOTO) {

                imgTitle = ImageCompressUtil.compressImg(imgTitle);
                setViewBottom();
            } else if (requestCode == RequestCode.REQUEST_CODE_ADD_ARTICLE) {
                baseUI.setResult(Activity.RESULT_OK);
                baseUI.finishActivity();
            }
        }
    }

    private void setViewBottom() {
        img_content.post(new Runnable() {
            @Override
            public void run() {
                float height2Width = BitmapUtil.height2Width(imgTitle);
                img_content.getLayoutParams().height = (int) (ScreenUtil.getScreenWidthPx() * height2Width);

                Bitmap bitmap = ImageCompressUtil.getBitmap(imgTitle);
                if (bitmap != null) {
                    //设置图片充满ImageView控件
                    img_content.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    //等比例缩放
                    img_content.setAdjustViewBounds(true);
                    img_content.setImageBitmap(bitmap);
                    //                    bitmap.recycle();
                    //                    PicassoUtil.loadImage(baseUI, imgTitle, img_content);
                } else {
                    ToastUtil.showToast(baseUI, "图片已损坏，请重新选择");
                    imgTitle = "";
                }
            }
        });
    }


    private void downLoadImage() {

        showProgress();
        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                Bitmap bitmap = null;
                try {
                    bitmap = HttpOkUtil.getInstance().loadImage(bean.getTitlePage(), R.drawable.default_portrait_grey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new ResponseBean(ConfigServer.RESPONSE_STATUS_SUCCESS, "", bitmap);
            }

            @Override
            public void onSuccess(ResponseBean result) {

                Bitmap bitmap = (Bitmap) result.getObject();
                if (bitmap != null) {
                    float height2Width = (float) bitmap.getHeight() / bitmap.getWidth();
                    img_content.getLayoutParams().height = (int) (ScreenUtil.getScreenWidthPx() * height2Width);

                    //设置图片充满ImageView控件
                    img_content.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    //等比例缩放
                    img_content.setAdjustViewBounds(true);
                    img_content.setImageBitmap(bitmap);
                    //                    bitmap.recycle();
                    //                    PicassoUtil.loadImage(baseUI, imgTitle, img_content);
                }
                dismissProgress();
            }

            @Override
            public void onFail(ResponseBean result) {
                dismissProgress();
            }
        });
    }
}