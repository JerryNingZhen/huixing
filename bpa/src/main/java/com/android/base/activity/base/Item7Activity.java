//package com.android.base.activity.base;
//
//import android.view.Gravity;
//import android.view.View;
//
//import com.hx.huixing.R;
//import com.android.base.activity.BaseActivity;
//import com.android.base.utils.IntentUtil;
//import com.android.base.utils.StringUtil;
//import com.android.base.utils.ToastUtil;
//import com.android.base.utils.dialog.CustomDialog;
//import com.android.base.utils.dialog.DialogUtil;
//import com.android.base.widget.TitleView;
//
///**
// * 弹出框
// * <p>
// * <br> Author: 叶青
// * <br> Version: 1.0.0
// * <br> Date: 2016年12月11日
// * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
// */
//public class Item7Activity extends BaseActivity {
//    /** 标题栏 */
//    public TitleView titleview;
//    public View item0;
//    public View item1;
//    public View item2;
//    public View item5;
//    public View item6;
//
//    @Override
//    protected int getContentViewId() {
//        // TODO Auto-generated method stub
//        return R.layout.activity_item7;
//    }
//
//    @Override
//    protected void findViews() {
//        // TODO Auto-generated method stub
//        titleview = (TitleView) findViewById(R.id.title_view);
//        item0 = findViewById(R.id.item0);
//        item1 = findViewById(R.id.item1);
//        item2 = findViewById(R.id.item2);
//        item5 = findViewById(R.id.item5);
//        item6 = findViewById(R.id.item6);
//        titleview.setTitle("弹出框");
//    }
//
//    @Override
//    protected void init() {
//        // TODO Auto-generated method stub
//    }
//
//    @Override
//    protected void initGetData() {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    protected void widgetListener() {
//        // TODO Auto-generated method stub
//        titleview.setLeftBtnImg();
//        item0.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                //                GalleryFinalUtil.selectPhoto(//
//                //                        Item7Activity.this,// 上下文
//                //                        new ArrayList<PhotoInfo>(),// 已选择的图片列表
//                //                        false,// 是否单图选择
//                //                        9,// 最大的图片选择张数
//                //                        true,// 是否可以编辑图片
//                //                        true,// 是否可以旋转图片
//                //                        true,// 是否旋转覆盖原图片
//                //                        true,// 是否可以裁剪图片
//                //                        true,// 是否裁剪覆盖原图片
//                //                        600,// 裁剪图片的宽度
//                //                        600,// 裁剪图片的高度
//                //                        false,// 是否裁剪为正方形
//                //                        true,// 是否显示相机按钮
//                //                        true,// 是否显示预览按钮
//                //                        true,// 是否强制裁剪--单图才可以强制裁剪
//                //                        true,// 是否强制裁剪编辑--单图才可以强制裁剪
//                //                        mOnHanlderResultCallback // 回调方法
//                //                );
//                //
//                //                GalleryFinalUtil.selectPhoto(//
//                //                        Item7Activity.this,// 上下文
//                //                        new ArrayList<PhotoInfo>(),// 已选择的图片列表
//                //                        true,// 是否单图选择
//                //                        1,// 最大的图片选择张数
//                //                        true,// 是否可以编辑图片
//                //                        true,// 是否可以旋转图片
//                //                        false,// 是否旋转覆盖原图片
//                //                        true,// 是否可以裁剪图片
//                //                        false,// 是否裁剪覆盖原图片
//                //                        800,// 裁剪图片的宽度
//                //                        800,// 裁剪图片的高度
//                //                        true,// 是否裁剪为正方形
//                //                        false,// 是否显示相机按钮
//                //                        false,// 是否显示预览按钮
//                //                        false,// 是否强制裁剪--单图才可以强制裁剪
//                //                        false,//  强制裁剪后是否可以对图片编辑，默认不可编辑
//                //                        mOnHanlderResultCallback // 回调方法
//                //                );
//            }
//        });
//
//        item1.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                DialogUtil.showSingleSelectDialog(Item7Activity.this, getString(R.string.hint_select_oper), "星期日", getResources().getStringArray(R.array.weeks), new CustomDialog.OnDialogClickListener() {
//
//                    @Override
//                    public void onClick(CustomDialog dialog, int id, Object object) {
//                        // dialog.dismiss();
//                        // id点击位置，object内容
//                        ToastUtil.showToast(Item7Activity.this, StringUtil.getList(getResources().getStringArray(R.array.weeks)).get(id) + "===" + object);
//                    }
//                });
//            }
//        });
//
//        item2.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                DialogUtil.showSingleSelectDialog4Gravity(Item7Activity.this, getString(R.string.hint_select_oper), getResources().getStringArray(R.array.weeks), Gravity.BOTTOM, new CustomDialog.OnDialogClickListener() {
//
//                    @Override
//                    public void onClick(CustomDialog dialog, int id, Object object) {
//                        // dialog.dismiss();
//                        // id点击位置，object内容
//                        ToastUtil.showToast(Item7Activity.this, StringUtil.getList(getResources().getStringArray(R.array.weeks)).get(id) + "===" + object);
//                    }
//                });
//            }
//        });
//
//        item5.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                //对话框
//                String spanned = ("拨打" + StringUtil.makeColorText("18566771883", "#00AAFF") + "?");
//                DialogUtil.showMessageDg(Item7Activity.this, getString(R.string.prompt), spanned, getString(R.string.cancel), getString(R.string.sure), null, new CustomDialog.OnDialogClickListener() {
//                    @Override
//                    public void onClick(CustomDialog dialog, int id, Object object) {
//                        IntentUtil.gotoCallPhoneActivity(Item7Activity.this, "18566771883");
//                        dialog.dismiss();
//                    }
//                }, Gravity.CENTER);
//
//            }
//        });
//
//        item6.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                DialogUtil.showMessageDg(Item7Activity.this, getString(R.string.prompt), "123456789测试", null, getString(R.string.sure), null, new CustomDialog.OnDialogClickListener() {
//
//                            @Override
//                            public void onClick(CustomDialog dialog, int id, Object object) {
//                                dialog.dismiss();
//                            }
//                        }
//                        , Gravity.CENTER);
//            }
//        });
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//
//
//    //    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
//    //        @Override
//    //        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
//    //            if (resultList != null) {
//    //                ToastUtil.showToast(Item7Activity.this, resultList.size() + "");
//    //                String imageLocalPath = ImageCompressUtil.compressImg(resultList.get(0).getPhotoPath());
//    //            }
//    //            //				mPhotoList.addAll(resultList);
//    //            //				mChoosePhotoListAdapter.notifyDataSetChanged();
//    //        }
//    //
//    //        @Override
//    //        public void onHanlderFailure(int requestCode, String errorMsg) {
//    //            Toast.makeText(Item7Activity.this, errorMsg, Toast.LENGTH_SHORT).show();
//    //        }
//    //    };
//
//
//}