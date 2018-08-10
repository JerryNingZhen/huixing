package com.android.base.activity;

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
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.BaseApplication;
import com.android.base.bean.ArticleAddBean;
import com.android.base.bean.ArticleDetailBean;
import com.android.base.bean.ResponseBean;
import com.android.base.configs.ConfigFile;
import com.android.base.configs.ConfigServer;
import com.android.base.configs.ConstantKey;
import com.android.base.configs.RequestCode;
import com.android.base.executor.BaseTask;
import com.android.base.executor.RequestExecutor;
import com.android.base.interfaces.OnDialogViewCallBack;
import com.android.base.mvp.baseclass.BaseActivity;
import com.android.base.mvp.baseclass.BaseView;
import com.android.base.mvp.model.HttpOkBiz;
import com.android.base.utils.BitmapUtil;
import com.android.base.utils.FileUtil;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.KeyboardUtil;
import com.android.base.utils.LogUtil;
import com.android.base.utils.PermissionUtils;
import com.android.base.utils.ScreenUtil;
import com.android.base.utils.ToastUtil;
import com.android.base.utils.dialog.CustomDialog;
import com.android.base.utils.dialog.DialogUtil;
import com.android.base.utils.gson.GsonUtil;
import com.android.base.utils.http.HttpOkUtil;
import com.android.base.utils.imageutils.GetPathUtil;
import com.android.base.utils.imageutils.ImageCompressUtil;
import com.android.base.widget.TitleView;
import com.android.base.widget.richeditor.RichEditor;
import com.android.base.widget.view.DialogContentEditView;
import com.hx.huixing.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 编辑界面
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class EditArticleActivity extends BaseActivity implements BaseView {
    /** TitleView */
    private TitleView titleview;
    private ImageView img_content;
    private TextView edit_title;
    //    private TextView edit_content;
    private RichEditor editor;
    private View view_bottom;
    private ArticleAddBean bean;

    @Override
    public void initVP() {
        mvpView = this;
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
        //        edit_content = findViewByIds(R.id.edit_content);
        editor = findViewByIds(R.id.editor);
        view_bottom = findViewByIds(R.id.view_bottom);

        initRichEditor();
    }

    @Override
    public void init(Bundle bundle) {
        titleview.setTitle("编辑文章");
        if (bundle != null) {
            String id = bundle.getString(ConstantKey.INTENT_KEY_ID, "");
            //            bean = (ArticleAddBean) bundle.getSerializable(ConstantKey.INTENT_KEY_DATA);
            quaryArticleDeatail(id);
        }
    }

    @Override
    public void widgetListener() {
        titleview.setLeftBtnTxt();

        titleview.setRightBtnTxt("预览", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardUtil.hideKeyBord(titleview);
                // TODO 更新

                bean.setTitlePage(imgTitle);

                bean.setTextTitle(edit_title.getText().toString());

                bean.setTextContent(textContent);

                if (TextUtils.isEmpty(bean.getTextTitle())) {
                    showToast("文章标题不能为空");
                    return;
                }
                if (TextUtils.isEmpty(bean.getTextContent())) {
                    showToast("文章内容不能为空");
                    return;
                }

                addArticleUploadImg(bean);
            }
        });

        img_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isHtml = false;
                checkPhoto();
            }
        });
    }

    @Override
    public void setViewData(Object object) {

    }


    private boolean isCamera = false;
    private boolean isStorage = false;
    /**
     * 身份证反面照片路径
     */
    public String imgTitle = "";
    public String imgTitleTemp = "";

    private void checkPhoto() {
        DialogUtil.showIosDialog(EditArticleActivity.this, null, EditArticleActivity.this.getResources().getStringArray(R.array.image_operation), Gravity.BOTTOM, null, new CustomDialog.OnDialogClickListener() {

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
                                        //                                        Intent intent = new Intent();
                                        //                                        //intent.setAction("android.intent.action.GET_CONTENT");
                                        //                                        intent.setAction(Intent.ACTION_PICK);//Pick an item fromthe data
                                        //                                        intent.setType("image/*");
                                        //                                        EditArticleActivity.this.startActivityForResult(intent, RequestCode.REQUEST_CODE_CHOSE_PHOTO);
                                        IntentUtil.chosePhoto(EditArticleActivity.this);
                                    } else {
                                        DialogUtil.showMessageDg(EditArticleActivity.this, "温馨提示", "亲，您还没有授权存储权限", "", "知道了", null, new CustomDialog.OnDialogClickListener() {
                                            @Override
                                            public void onClick(CustomDialog dialog, int id, Object object) {
                                                dialog.dismiss();
                                            }
                                        }, Gravity.CENTER);
                                    }
                                }
                            }
                        }).requestPermission(EditArticleActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
                                            //                                            Intent intent = new Intent();
                                            //                                            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                                            //                                            intent.addCategory(Intent.CATEGORY_DEFAULT);              // 根据文件地址创建文件
                                            //
                                            imgTitleTemp = ConfigFile.PATH_IMAGES + "/fzd_" + System.currentTimeMillis() + ".jpg";
                                            //                                            Uri uri = Uri.fromFile(new File(imgTitleTemp));
                                            //                                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                            //                                            EditArticleActivity.this.startActivityForResult(intent, RequestCode.REQUEST_CODE_TAKE_PHOTO);
                                            IntentUtil.takePhoto(EditArticleActivity.this, imgTitleTemp);

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
                                                DialogUtil.showMessageDg(EditArticleActivity.this, "温馨提示", tips, "", "知道了", null, new CustomDialog.OnDialogClickListener() {
                                                    @Override
                                                    public void onClick(CustomDialog dialog, int id, Object object) {
                                                        dialog.dismiss();
                                                    }
                                                }, Gravity.CENTER);
                                            }
                                        }
                                }
                            }
                        }).requestPermission(EditArticleActivity.this, REQUEST_PERMISSIONS);
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                            path = GetPathUtil.getPath(EditArticleActivity.this, uri);
                        } else {
                            //4.4以下系统使用这个方法处理图片
                            path = null;
                            //通过Uri和selection来获取真实的图片路径
                            Cursor cursor = EditArticleActivity.this.getContentResolver().query(uri, null, null, null, null);
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
                        //                            ToastUtil.showToast(EditArticleActivity.this, "图片像素太低，请重新上传");
                        //                            return;
                        //                        }
                        // 解析本地图片，获得图片尺寸
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        // 不生成Bitmap 只是获取bitmap的宽高，相对省内存
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(path, options);
                        if (options.outWidth == -1 || options.outHeight == -1) {
                            ToastUtil.showToast(EditArticleActivity.this, "图片已损坏，请重新选择");
                            LogUtil.i("图片已损坏，请重新选择");
                            return;
                        }
                        LogUtil.i(uri + ">>>>" + path + ">>>");

                        String filePath = ImageCompressUtil.compressImg(path);
                        setViewBottom(filePath);
                    }
                }
            } else if (requestCode == RequestCode.REQUEST_CODE_TAKE_PHOTO) {

                String filePath = ImageCompressUtil.compressImg(imgTitleTemp);
                setViewBottom(filePath);
            } else if (requestCode == RequestCode.REQUEST_CODE_ADD_ARTICLE) {
                EditArticleActivity.this.setResult(Activity.RESULT_OK);
                EditArticleActivity.this.finishActivity();
            }
        }
    }

    private void setViewBottom(String path) {
        if (isHtml) {
            imagePaths.add(path);
            editor.insertImage("file://" + path,
                    "picvision\" style=\"width:100%;");
        } else {
            imgTitle = path;
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
                        //                    PicassoUtil.loadImage(EditArticleActivity.this, imgTitle, img_content);
                    } else {
                        ToastUtil.showToast(EditArticleActivity.this, "图片已损坏，请重新选择");
                        imgTitle = "";
                    }
                }
            });
        }
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
                    //                    PicassoUtil.loadImage(EditArticleActivity.this, imgTitle, img_content);
                }
                dismissProgress();
            }

            @Override
            public void onFail(ResponseBean result) {
                dismissProgress();
            }
        });
    }

    private void initRichEditor() {
        //        editor.setEditorHeight(ScreenUtil.getScreenHeightPx() / 2);
        editor.setEditorFontSize(ScreenUtil.sp2px(20));
        editor.setEditorFontColor(0xff999999);
        editor.setEditorBackgroundColor(0xfff5f5f5);
        editor.setBackgroundColor(0xfff5f5f5);
        //editor.setBackgroundResource(R.drawable.bg);
        editor.setPadding(ScreenUtil.dip2px(25), ScreenUtil.dip2px(25), ScreenUtil.dip2px(25), ScreenUtil.dip2px(25));
        //editor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        editor.setPlaceholder("正文");
        //editor.setInputEnabled(false);

        editor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                textContent = text;
                LogUtil.i(text);
            }
        });

        editor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                LogUtil.i(hasFocus + "=====>" + v.getClass().getName());
                if (hasFocus) {
                    view_bottom.setVisibility(View.VISIBLE);
                    KeyboardUtil.showKeyBord(editor);
                } else {
                    view_bottom.setVisibility(View.GONE);
                }
            }
        });
        findViewByIds(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.undo();
            }
        });

        findViewByIds(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.redo();
            }
        });

        findViewByIds(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setBold();
            }
        });

        findViewByIds(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setItalic();
            }
        });

        //        findViewByIds(R.id.action_subscript).setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                editor.setSubscript();
        //            }
        //        });
        //
        //        findViewByIds(R.id.action_superscript).setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                editor.setSuperscript();
        //            }
        //        });

        findViewByIds(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setStrikeThrough();
            }
        });

        findViewByIds(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setUnderline();
            }
        });

        findViewByIds(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setHeading(1);
            }
        });

        findViewByIds(R.id.action_heading2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setHeading(2);
            }
        });

        findViewByIds(R.id.action_heading3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setHeading(3);
            }
        });

        findViewByIds(R.id.action_heading4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setHeading(4);
            }
        });

        findViewByIds(R.id.action_heading5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setHeading(5);
            }
        });

        findViewByIds(R.id.action_heading6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setHeading(6);
            }
        });

        //        // 设置字体颜色
        //        findViewByIds(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
        //            private boolean isChanged;
        //
        //            @Override
        //            public void onClick(View v) {
        //                editor.setTextColor(isChanged ? Color.RED : Color.BLACK);
        //                isChanged = !isChanged;
        //            }
        //        });
        //
        //        // 设置字体背景颜色
        //        findViewByIds(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
        //            private boolean isChanged;
        //
        //            @Override
        //            public void onClick(View v) {
        //                editor.setTextBackgroundColor(isChanged ? Color.WHITE : Color.CYAN);
        //                isChanged = !isChanged;
        //            }
        //        });
        //
        //        // 设置margin
        //        findViewByIds(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                editor.setIndent();
        //            }
        //        });
        //
        //        // 设置margin
        //        findViewByIds(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                editor.setOutdent();
        //            }
        //        });

        findViewByIds(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setAlignLeft();
            }
        });

        findViewByIds(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setAlignCenter();
            }
        });

        findViewByIds(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setAlignRight();
            }
        });

        findViewByIds(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setBlockquote();
            }
        });

        findViewByIds(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setBullets();
            }
        });

        findViewByIds(R.id.action_insert_numbers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setNumbers();
            }
        });

        findViewByIds(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                KeyboardUtil.hideKeyBord(editor);
                isHtml = true;
                checkPhoto();
                //                editor.insertImage("http://www.uiimg.com/upload/image/20171123/5a167ec5cde6b.jpg",
                //                        "picvision\" style=\"width:100% ");
            }
        });

        findViewByIds(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.hideKeyBord(editor);
                // TODO 弹框
                CustomDialog dialog = new CustomDialog(EditArticleActivity.this);
                DialogContentEditView contentView = new DialogContentEditView(EditArticleActivity.this, dialog, new OnDialogViewCallBack() {
                    @Override
                    public void onResult(Map<String, String> map) {
                        String title = "";
                        String address = "";
                        if (map.containsKey("title")) {
                            title = map.get("title");
                        }
                        if (map.containsKey("address")) {
                            address = map.get("address");
                        }
                        editor.insertLink(address, title);
                    }
                });
                dialog.createDialog(contentView, false);
                dialog.show();
            }
        });

        ////        checkbox
        //        findViewByIds(R.id.action_insert_checkbox).setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                editor.insertTodo();
        //            }
        //        });

        findViewByIds(R.id.action_line).

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.insertLine();
                    }
                });
        final HorizontalScrollView horizontalscrollview = findViewByIds(R.id.horizontalscrollview);
        horizontalscrollview.setVisibility(View.GONE);

        findViewByIds(R.id.action_font).

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (horizontalscrollview.getVisibility() == View.VISIBLE) {
                            horizontalscrollview.setVisibility(View.GONE);
                        } else {
                            horizontalscrollview.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private boolean isHtml = false;
    private String textContent = "";
    public ArrayList<String> imagePaths = new ArrayList<>();


    /**
     * 文章详情
     */
    private void quaryArticleDeatail(String id) {
        showProgress();
        //        "reviewId":      // 查询的文章的id
        //        "loginUser":    //  当前登录人id
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_QUARYARTICLEDEATAIL);
        params.put("loginUser", BaseApplication.getInstance().getUserInfoBean().getId());
        params.put("reviewId", id);

        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                return HttpOkBiz.getInstance().sendGet(params);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                dismissProgress();
                ArticleDetailBean detailBean = GsonUtil.getInstance().json2Bean((String) result.getObject(), ArticleDetailBean.class);

                bean = new ArticleAddBean();
                bean.setTitlePage(detailBean.getTitlePage());
                bean.setTextContent(detailBean.getTextContent());
                bean.setTextTitle(detailBean.getTextTitle());
                bean.setCreateTime(detailBean.getCreateTime());
                bean.setReviewId(detailBean.getReviewId());


                if (bean != null) {
                    if (!TextUtils.isEmpty(bean.getTitlePage())) {
                        imgTitle = bean.getTitlePage();
                        downLoadImage();
                    }
                    edit_title.setText(bean.getTextTitle());
                    editor.setHtml(bean.getTextContent());
                    textContent = bean.getTextContent();
                    //                edit_content.setText(Html.fromHtml(bean.getTextContent()));
                }
            }

            @Override
            public void onFail(ResponseBean result) {
                dismissProgress();
                showToast(result.getInfo());
            }
        });
    }

    public void addArticleUploadImg(final ArticleAddBean bean) {

        if (TextUtils.isEmpty(bean.getTitlePage())
                || bean.getTitlePage().startsWith("http")
                ) {
            uploadContentImage(bean);
            return;
        }

        showProgress();
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, "common/upload");
        final HashMap<String, File> files = new HashMap<>();
        files.put("file", new File(bean.getTitlePage()));

        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                return HttpOkBiz.getInstance().upLoadFile(params, files);
            }

            @Override
            public void onSuccess(ResponseBean result) {

                String url = (String) result.getObject();
                try {
                    JSONArray jsonArray = new JSONArray(url);
                    if (jsonArray.length() > 0) {
                        url = jsonArray.getString(0);
                        bean.setTitlePage(url);
                    }
                    //                bean.setTitlePage("https://goss.vcg.com/20b9d020-7e72-11e8-bef6-79929cace6d6.jpg");
                    uploadContentImage(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(ResponseBean result) {
                dismissProgress();
                showToast(result.getInfo());
            }
        });

    }


    private Map<String, String> map = new HashMap<>();

    private void uploadContentImage(final ArticleAddBean bean) {

        map = new HashMap<>();
        if (imagePaths.size() > 0) {
            String content = bean.getTextContent();
            for (int i = 0; i < imagePaths.size(); i++) {
                if (content.contains(imagePaths.get(i))) {
                    map.put(imagePaths.get(i), "");
                }
            }
        }

        if (map.size() <= 0) {
            addArticle(bean);
            return;
        }

        showProgress();

        Set<Map.Entry<String, String>> set = map.entrySet();
        for (Map.Entry<String, String> entry : set) {
            upLoad(bean, entry.getKey());
        }

    }

    private void upLoad(final ArticleAddBean bean, final String path) {
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, "common/upload");
        final HashMap<String, File> files = new HashMap<>();
        files.put("file", new File(path));

        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                return HttpOkBiz.getInstance().upLoadFile(params, files);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                String url = (String) result.getObject();
                try {
                    JSONArray jsonArray = new JSONArray(url);
                    if (jsonArray.length() > 0) {
                        url = jsonArray.getString(0);
                        //                        bean.setTitlePage(url);
                        map.put(path, url);
                    }

                    boolean isFinish = true;
                    Set<Map.Entry<String, String>> set = map.entrySet();
                    for (Map.Entry<String, String> entry : set) {
                        if (TextUtils.isEmpty(entry.getValue())) {
                            isFinish = false;
                            break;
                        }
                    }

                    if (isFinish) {
                        String content = bean.getTextContent();
                        for (Map.Entry<String, String> entry : set) {
                            content = content.replace("file://" + entry.getKey(), entry.getValue());
                        }
                        textContent = content;
                        bean.setTextContent(content);
                        LogUtil.i(content);
                        imagePaths.clear();
                        addArticle(bean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(ResponseBean result) {
                dismissProgress();
                showToast(result.getInfo());
            }
        });
    }

    public void addArticle(final ArticleAddBean bean) {
        showProgress();

        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_ADDREVIEW);
        params.put("textTitle", bean.getTextTitle());
        params.put("textContent", bean.getTextContent());
        params.put("type", "4");
        params.put("creator", BaseApplication.getInstance().getUserInfoBean().getId());
        params.put("password", BaseApplication.getInstance().getUserInfoBean().getUserPwd());
        params.put("titlePage", bean.getTitlePage());


        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                if (!TextUtils.isEmpty(bean.getReviewId())) {
                    params.put("reviewId", bean.getReviewId());
                    params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_UPDATAREVIEW);
                }
                return HttpOkBiz.getInstance().sendPost(params);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                dismissProgress();
                // 编辑文章
                //                Bundle bundle = new Bundle();
                //                bundle.putSerializable(ConstantKey.INTENT_KEY_ID, bean.getReviewId());
                //                IntentUtil.gotoActivityToTopAndFinish(EditArticleActivity.this, ArticleDetailActivity.class, bundle);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstantKey.INTENT_KEY_DATA, bean);
                IntentUtil.gotoActivityForResult(EditArticleActivity.this, ArticlePreviewActivity.class, bundle, RequestCode.REQUEST_CODE_ADD_ARTICLE);
                //showToast(result.getInfo());
            }

            @Override
            public void onFail(ResponseBean result) {
                dismissProgress();
                showToast(result.getInfo());
            }
        });
    }

}