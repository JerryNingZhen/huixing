package com.android.base.mvp.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hx.huixing.R;
import com.android.base.activity.ImageBrowseActivity;
import com.android.base.adapter.ViewPagerAdapter;
import com.android.base.configs.ConstantKey;
import com.android.base.mvp.baseclass.MvpBaseView;
import com.android.base.utils.ScreenUtil;
import com.android.base.widget.CustomViewPager4Lock;
import com.android.base.widget.TitleView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 图片浏览器
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ImageBrowseView extends MvpBaseView<ImageBrowseActivity> {

    /** ViewPager 自定义控制左右滑动事件 */
    private CustomViewPager4Lock viewPager;
    /** TitleView */
    private TitleView titleview;
    /** 所有的图片view */
    private ArrayList<View> views = new ArrayList<>();
    /** 更多 */
    private ImageView imgMore;
    /** 自动切换圆点试图 */
    private LinearLayout viewDot;
    /** 自动切换圆点试图大于10张图片后，换成n/m的形式显示 */
    private TextView viewDisplayNum;
    /** （切换圆点试图 ，n/m的形式）显示数目的界限，默认是11张 */
    private int displayNum = 9;
    /** 当前位置 */
    private int position = 0;
    /** 图片全路径（或者本地路径） */
    private ArrayList<String> imgPath = new ArrayList<>();

    public ImageBrowseView(ImageBrowseActivity baseUI) {
        super(baseUI);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_image_browse;
    }

    @Override
    public void findViews() {
        titleview = findViewByIds(R.id.title_view);
        viewPager = findViewByIds(R.id.view_pager);
        imgMore = findViewByIds(R.id.img_more);
        viewDot = findViewByIds(R.id.view_dot);
        viewDisplayNum = findViewByIds(R.id.view_displaynum);
        imgMore.setVisibility(View.GONE);
    }

    @Override
    public void init(Bundle bundle) {
        if (bundle != null) {
            imgPath = bundle.getStringArrayList(ConstantKey.INTENT_KEY_DATAS);
            position = bundle.getInt(ConstantKey.INTENT_KEY_POSITION);
        }
        initViewPage();
        titleview.setTitle(baseUI.getString(R.string.activity_image_browse));
    }


    /**
     * 初始化viewpage
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-20,上午11:24:49
     * <br> UpdateTime: 2016-1-20,上午11:24:49
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void initViewPage() {
        //        baseUI.getMvpPresenter().initViewPage(baseUI, viewDot, views, imgPath);
        viewDot.removeAllViews();
        for (int i = 0; i < imgPath.size(); i++) {
            PhotoView photoView = new PhotoView(baseUI);
            photoView.setBackgroundColor(baseUI.getResources().getColor(R.color.black));
            //            photoView.setTag(i);
            //            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            //                @Override
            //                public void onPhotoTap(View view, float v, float v1) {
            //                    finishActivity();
            //                }
            //            });
            Picasso.with(baseUI).load(imgPath.get(i))//
                    // .transform(new GlideRoundTransform(context, 15))// 圆角大小
                    .placeholder(R.drawable.img_default_grey)// 占位图 默认图片 一般可以设置成一个加载中的进度GIF图
                    .error(R.drawable.img_load_error_grey)// 占位图 默认图片 一般可以设置成一个加载中的进度GIF图
                    //                    .crossFade()// 渐变切换
                    //                    .dontAnimate()// 不使用Glide的默认动画
                    //                    .diskCacheStrategy(DiskCacheStrategy.ALL)// 避免同一张图片加载两次
                    // .centerCrop()// 比例类型
                    .into(photoView);

            views.add(photoView);


            View view = new View(baseUI);
            view.setBackgroundResource(R.drawable.selector_point_white_blue);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(20, 20);
            lp.leftMargin = ScreenUtil.dip2px(4);
            view.setLayoutParams(lp);
            view.setEnabled(false);
            viewDot.addView(view);
        }
        setViewPager(position);
    }

    private void setViewPager(int position) {
        // 一张不显示 和 超过限定数量用n/m形式显示
        if (views.size() == 1 || views.size() > 9) {
            // if (list_Images.size() == 1) {
            viewDot.setVisibility(View.GONE);
            viewDisplayNum.setVisibility(View.GONE);
            // 超过限定数量9 用n/m形式显示
            if (views.size() > displayNum) {
                viewDisplayNum.setVisibility(View.VISIBLE);
                viewDisplayNum.setText((position + 1) + "/" + views.size());
            }
        } else {// 圆点形式
            viewDisplayNum.setVisibility(View.GONE);
            viewDot.setVisibility(View.VISIBLE);
            if (position < viewDot.getChildCount()) {
                viewDot.getChildAt(position).setEnabled(true);
            }
        }

        ViewPagerAdapter adapter = new ViewPagerAdapter(views);
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(position);
        // 设置viewpager保留多少个显示界面
        viewPager.setOffscreenPageLimit(2);
        viewPager.setEnabled(true);
    }


    @Override
    public void setViewData(Object object) {

    }

    @Override
    public void widgetListener() {
        titleview.setLeftBtnImg();

        titleview.setRightBtnTxt("删除", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (views.size() == 1) {
                    baseUI.finishActivity();
                    return;
                }

                int currentItem = viewPager.getCurrentItem();
                views.remove(currentItem);
                viewDot.removeViewAt(currentItem);
                if (currentItem == 0) {
                    setViewPager(currentItem);
                } else {
                    setViewPager(currentItem - 1);
                }
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < views.size(); i++) {
                    PhotoViewAttacher mAttacher = new PhotoViewAttacher((PhotoView) views.get(i));
                    mAttacher.setScale(1);
                }

                if (views.size() > displayNum) {
                    // 大于10张，就用n/m形式
                    viewDisplayNum.setText((position + 1) + "/" + views.size());
                } else {
                    if (position < viewDot.getChildCount()) {
                        for (int i = 0; i < viewDot.getChildCount(); i++) {
                            if (i == position) {
                                viewDot.getChildAt(i).setEnabled(true);
                            } else {
                                viewDot.getChildAt(i).setEnabled(false);
                            }
                        }
                    }
                }

                ((PhotoView) views.get(position)).setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                    @Override
                    public void onViewTap(View view, float v, float v1) {
                        baseUI.finishActivity();
                    }
                });
            }

            /**
             * @param arg0
             *            :当前页面，及你点击滑动的页面
             * @param arg1
             *            arg1:当前页面偏移的百分比
             * @param arg2
             *            arg2:当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            /**
             * @param arg0
             *            有三种状态（0，1，2）。arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。
             */
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

        // 更多按钮
        imgMore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }
}