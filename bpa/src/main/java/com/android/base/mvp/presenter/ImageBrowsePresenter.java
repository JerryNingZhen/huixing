package com.android.base.mvp.presenter;

import com.android.base.mvp.baseclass.MvpBasePresenter;
import com.android.base.mvp.view.ImageBrowseView;

/**
 * 图片浏览器
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ImageBrowsePresenter extends MvpBasePresenter<ImageBrowseView> {

    public ImageBrowsePresenter(ImageBrowseView view) {
        super(view);
    }

    //    /**
    //     * 初始化viewpage
    //     * <p>
    //     * <br> Version: 1.0.0
    //     * <br> CreateTime: 2016-1-20,上午11:24:49
    //     * <br> UpdateTime: 2016-1-20,上午11:24:49
    //     * <br> CreateAuthor: 叶青
    //     * <br> UpdateAuthor: 叶青
    //     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
    //     */
    //    public void initViewPage(Context context, LinearLayout viewDot, ArrayList<View> views, ArrayList<String> imgPath) {
    //        viewDot.removeAllViews();
    //        for (int i = 0; i < imgPath.size(); i++) {
    //            PhotoView photoView = new PhotoView(context);
    //            photoView.setBackgroundColor(context.getResources().getColor(R.color.black));
    //            //            photoView.setTag(i);
    //            //            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
    //            //                @Override
    //            //                public void onPhotoTap(View view, float v, float v1) {
    //            //                    finishActivity();
    //            //                }
    //            //            });
    //            Picasso.with(context).load(imgPath.get(i))//
    //                    // .transform(new GlideRoundTransform(context, 15))// 圆角大小
    //                    .placeholder(R.drawable.img_default_grey)// 占位图 默认图片 一般可以设置成一个加载中的进度GIF图
    //                    .error(R.drawable.img_load_error_grey)// 占位图 默认图片 一般可以设置成一个加载中的进度GIF图
    //                    //                    .crossFade()// 渐变切换
    //                    //                    .dontAnimate()// 不使用Glide的默认动画
    //                    //                    .diskCacheStrategy(DiskCacheStrategy.ALL)// 避免同一张图片加载两次
    //                    // .centerCrop()// 比例类型
    //                    .into(photoView);
    //
    //            views.add(photoView);
    //
    //
    //            View view = new View(context);
    //            view.setBackgroundResource(R.drawable.selector_point_white_blue);
    //            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(20, 20);
    //            lp.leftMargin = ScreenUtil.dip2px(4);
    //            view.setLayoutParams(lp);
    //            view.setEnabled(false);
    //            viewDot.addView(view);
    //        }
    //    }
    //
    //    private void setViewPager(int position) {
    //        // 一张不显示 和 超过限定数量用n/m形式显示
    //        if (views.size() == 1 || views.size() > 9) {
    //            // if (list_Images.size() == 1) {
    //            viewDot.setVisibility(View.GONE);
    //            viewDisplayNum.setVisibility(View.GONE);
    //            // 超过限定数量9 用n/m形式显示
    //            if (views.size() > displayNum) {
    //                viewDisplayNum.setVisibility(View.VISIBLE);
    //                viewDisplayNum.setText((position + 1) + "/" + views.size());
    //            }
    //        } else {// 圆点形式
    //            viewDisplayNum.setVisibility(View.GONE);
    //            viewDot.setVisibility(View.VISIBLE);
    //            if (position < viewDot.getChildCount()) {
    //                viewDot.getChildAt(position).setEnabled(true);
    //            }
    //        }
    //
    //        ViewPagerAdapter adapter = new ViewPagerAdapter(views);
    //        viewPager.setAdapter(adapter);
    //
    //        viewPager.setCurrentItem(position);
    //        // 设置viewpager保留多少个显示界面
    //        viewPager.setOffscreenPageLimit(2);
    //        viewPager.setEnabled(true);
    //    }
}