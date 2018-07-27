//package com.android.base.activity.base;
//
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.LinearLayout;
//
//import com.hx.huixing.R;
//import com.android.base.activity.BaseActivity;
//import com.android.base.utils.ToastUtil;
//import com.android.base.widget.TitleView;
//
///**
// * 侧拉菜单实现
// *
// * <br> Author: 叶青
// * <br> Version: 1.0.0
// * <br> Date: 2016年12月11日
// * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
// */
//public class Item2Activity extends BaseActivity {
//
//	/** 侧拉 */
//	private DrawerLayout drawerlayout;
//	/** 标题栏 */
//	public TitleView titleview;
//	/** item1 */
//	private LinearLayout item1;
//
//	@Override
//	protected int getContentViewId() {
//		return R.layout.activity_item2;
//	}
//
//	@Override
//	protected void findViews() {
//		drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//		drawerlayout.setScrimColor(getResources().getColor(R.color.black_30));
//		titleview = (TitleView) findViewById(R.id.title_view);
//		item1 = (LinearLayout) findViewById(R.id.item1);
//	}
//
//	@Override
//	protected void initGetData() {
//
//	}
//
//	@Override
//	protected void init() {
//		titleview.setTitle("侧滑菜单");
//	}
//
//	@Override
//	protected void widgetListener() {
//		titleview.setLeftBtnImg();
//		titleview.setRightBtnTxt("open", new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				drawerlayout.openDrawer(GravityCompat.START);
//			}
//		});
//
//		drawerlayout.setDrawerListener(new DrawerLayout.DrawerListener() {
//			@Override
//			public void onDrawerSlide(View drawerView, float slideOffset) {
//				// ToastUtil.showToast(Item2Activity.this, "onDrawerSlide");
//			}
//
//			@Override
//			public void onDrawerOpened(View drawerView) {
//				// ToastUtil.showToast(Item2Activity.this, "onDrawerOpened");
//			}
//
//			@Override
//			public void onDrawerClosed(View drawerView) {
//				// ToastUtil.showToast(Item2Activity.this, "onDrawerClosed");
//			}
//
//			@Override
//			public void onDrawerStateChanged(int newState) {
//				// ToastUtil.showToast(Item2Activity.this, "onDrawerStateChanged");
//			}
//		});
//
//		item1.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				drawerlayout.closeDrawers();
//				ToastUtil.showToast(Item2Activity.this, "item1");
//			}
//		});
//	}
//}