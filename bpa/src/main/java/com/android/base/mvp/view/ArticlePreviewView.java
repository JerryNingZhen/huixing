package com.android.base.mvp.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.activity.ArticleDetailActivity;
import com.android.base.activity.ArticlePreviewActivity;
import com.android.base.bean.ArticleAddBean;
import com.android.base.bean.ResponseBean;
import com.android.base.configs.ConfigServer;
import com.android.base.configs.ConstantKey;
import com.android.base.executor.BaseTask;
import com.android.base.executor.RequestExecutor;
import com.android.base.mvp.baseclass.MvpBaseView;
import com.android.base.utils.BitmapUtil;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.ScreenUtil;
import com.android.base.utils.http.HttpOkUtil;
import com.android.base.utils.imageutils.ImageCompressUtil;
import com.android.base.widget.TitleView;
import com.hx.huixing.R;

import java.net.URL;

/**
 * 发帖预览 View模块
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ArticlePreviewView extends MvpBaseView<ArticlePreviewActivity> {

    /** TitleView */
    private TitleView titleview;
    private ImageView img_content;
    private TextView txt_title;
    private TextView txt_date;
    private TextView txt_content;
    private ArticleAddBean bean;

    public ArticlePreviewView(ArticlePreviewActivity baseUI) {
        super(baseUI);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_article_preview;
    }

    @Override
    public void findViews() {
        titleview = findViewByIds(R.id.title_view);
        img_content = findViewByIds(R.id.img_content);
        txt_title = findViewByIds(R.id.txt_title);
        txt_date = findViewByIds(R.id.txt_date);
        txt_content = findViewByIds(R.id.txt_content);
    }

    @Override
    public void init(Bundle bundle) {
        if (bundle != null) {
            bean = (ArticleAddBean) bundle.getSerializable(ConstantKey.INTENT_KEY_DATA);
        }
        titleview.setTitle("预览");

        if (bean != null) {
            if (!TextUtils.isEmpty(bean.getTitlePage())) {
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
                        //                    PicassoUtil.loadImage(baseUI, idCardPath2, img_content);
                    }
                } else {
                    downLoadImage();
                }
            } else {
                img_content.setVisibility(View.GONE);
            }
            txt_title.setText(bean.getTextTitle());
            txt_date.setText(bean.getCreateTime());
            //            txt_content.setText(Html.fromHtml(bean.getTextContent()));
            //            showProgress();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Html.ImageGetter imgGetter = new Html.ImageGetter() {
                        public Drawable getDrawable(String source) {
                            Drawable drawable;
                            try {
                                URL url = new URL(source);
                                drawable = Drawable.createFromStream(url.openStream(), "img");
                            } catch (Exception e) {
                                return null;
                            }
                            if (drawable != null) {
                                //        exec("javascript:RE.insertImage('" + "http://backend.blockcomet.com/blockchain/getpic?picture=/data/NewsPaperFront/img/2018-08-09_9c28b6ec-f644-4399-8819-ea1d35bd0b8d.png" + "', '" + "\" style=\"width:100%;height:1px;margin-top:30px;margin-bottom:30px;" + "');");
                                //        exec("javascript:RE.insertImage('" + "http://backend.blockcomet.com/blockchain/getpic?picture=/data/NewsPaperFront/img/2018-08-09_1bc007fe-e276-479b-982a-09404049f7e8.png" + "', '" + "\" style=\"width:100%;height:1px;margin-top:30px;margin-bottom:30px;" + "');");

                                if("http://backend.blockcomet.com/blockchain/getpic?picture=/data/NewsPaperFront/img/2018-08-09_9c28b6ec-f644-4399-8819-ea1d35bd0b8d.png".equals(source)
                                      ||"http://backend.blockcomet.com/blockchain/getpic?picture=/data/NewsPaperFront/img/2018-08-09_1bc007fe-e276-479b-982a-09404049f7e8.png".equals(source)  ){
                                    drawable.setBounds(0, 0, ScreenUtil.getScreenWidthPx(), 1);
                                }else{
                                    drawable.setBounds(0, 0, ScreenUtil.getScreenWidthPx(), (int) (ScreenUtil.getScreenWidthPx() * 1f / drawable.getIntrinsicWidth() * drawable.getIntrinsicHeight()));
                                }
                            }
                            return drawable;
                        }
                    };

                    final Spanned text = Html.fromHtml(bean.getTextContent(), imgGetter, null);
                    txt_content.post(new Runnable() {
                        @Override
                        public void run() {
                            txt_content.setText(text);
                            //                            dismissProgress();
                        }
                    });
                }
            }).start();

        }
    }


    String id = "";

    @Override
    public void setViewData(Object object) {

        if (object instanceof String) {
            id = (String) object;
            if (!TextUtils.isEmpty(id)) {
                // 发布成功
                baseUI.getMvpPresenter().delectDraft(bean);
            }
        }

    }

    @Override
    public void widgetListener() {
        titleview.setLeftBtnImg();

        titleview.setRightBtnTxt("发布", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bean!=null&& !TextUtils.isEmpty(bean.getReviewId())){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ConstantKey.INTENT_KEY_ID, bean.getReviewId());
                    baseUI.setResult(Activity.RESULT_OK);
                    showToast("修改成功");
                    IntentUtil.gotoActivityToTopAndFinish(baseUI, ArticleDetailActivity.class, bundle);
                    return;
                }
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

                baseUI.getMvpPresenter().addArticleUploadImg(bean);
            }
        });

        img_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    /**
     * 返回事件
     */
    public void goBack() {
        baseUI.setResult(Activity.RESULT_OK);
        //      TODO
        Bundle bundle = new Bundle();
        bundle.putSerializable(ConstantKey.INTENT_KEY_ID, id);
        IntentUtil.gotoActivityToTopAndFinish(baseUI, ArticleDetailActivity.class, bundle);
        //        baseUI.finishActivity();
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