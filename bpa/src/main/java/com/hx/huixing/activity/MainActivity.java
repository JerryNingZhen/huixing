package com.hx.huixing.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.android.base.activity.AddArticleActivity;
import com.android.base.utils.AppManagerUtil;
import com.android.base.utils.IntentUtil;
import com.android.base.utils.ToastUtil;
import com.android.base.widget.TitleView;
import com.hx.huixing.R;
import com.hx.huixing.activityMvp.BasePresenter;
import com.hx.huixing.fragment.FirstFragment;
import com.hx.huixing.fragment.SecondFragment;
import com.hx.huixing.fragment.ThirdFragment;
import com.hx.huixing.uitl.LogUtil;

import java.util.ArrayList;


/**
 * <br> Description 主页
 * <br> Author: 谭俊
 * <br> PackageName com.tan.mvpdemo.activityMvp
 * <br> Date: 2018/7/15
 */
public class MainActivity extends BaseActivity {

    /** 记录退出按下时间 */
    private long exitTime = 0;
    /** 标题栏 */
    private TitleView titleView;

    /** 首页 */
    private FirstFragment firstFragment = null;
    /** 文章 */
    private SecondFragment secondFragment = null;
    /** 我的 */
    private ThirdFragment thirdFragment = null;

    /** fragment集合 */
    private ArrayList<Fragment> listFragments = new ArrayList<>();

    /** 功能单选按钮组 */
    private RadioGroup radiogroup;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews() {
        titleView = (TitleView) findViewById(R.id.title_view);
        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
    }

    @Override
    protected void initGetData() {
    }

    @Override
    protected void init() {
        initFragment();
    }

    @Override
    protected void widgetListener() {
        // 功能菜单选择事件更改 主页切换
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.main_radio_btn_index) {
                    switchView(0);
                    setTintResource(R.color.title_bg_color);
                } else if (checkedId == R.id.main_radio_btn_order) {
                    //switchView(1);
                    IntentUtil.gotoActivity(MainActivity.this, AddArticleActivity.class);
                    if (!firstFragment.isHidden()) {
                        radiogroup.check(R.id.main_radio_btn_index);
                    }

                    if (!thirdFragment.isHidden()) {
                        radiogroup.check(R.id.main_radio_btn_mine);
                    }

                } else if (checkedId == R.id.main_radio_btn_mine) {
                    setTintResource(R.color.content_blue);
                    switchView(2);
                }
            }
        });
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    /**
     * 初始化子模块
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年3月29日,下午1:50:57
     * <br> UpdateTime:  2016年3月29日,下午1:50:57
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    private void initFragment() {

        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();

        listFragments.add(firstFragment);
        listFragments.add(secondFragment);
        listFragments.add(thirdFragment);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.view_parent, firstFragment);
        transaction.add(R.id.view_parent, secondFragment);
        transaction.add(R.id.view_parent, thirdFragment);
        transaction.commit();

        switchView(0);
    }

    /**
     * 选择界面
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年3月29日,下午5:18:33
     * <br> UpdateTime:  2016年3月29日,下午5:18:33
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     *
     * @param position
     *         fragment的索引值
     */
    public void switchView(int position) {
        try {

            if (listFragments.get(position).isVisible()) {
                return;
            }

            // 获取Fragment的操作对象
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            for (int i = 0; i < listFragments.size(); i++) {
                LogUtil.i(listFragments.get(i).isVisible() + "...");
                transaction.hide(listFragments.get(i));
            }

            transaction.show(listFragments.get(position));
            // 提交更改
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ***********************************返回键事件处理*********************************
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                // 要执行的事件
                // DialogUtil.showExitsDg(MainActivity.this);对话框退出
                // 判断2次点击事件时间
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    ToastUtil.showToast(MainActivity.this, getString(R.string.tips_exit_time));
                    exitTime = System.currentTimeMillis();
                } else {
                    // 退出程序
                    //JPushUtil.getInstance(this).setAlias(null);
                    AppManagerUtil.getAppManager().exitApp();
                    // finish();
                }

            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(radiogroup.getCheckedRadioButtonId()==R.id.main_radio_btn_index){
            if(firstFragment!=null){
                firstFragment.onActivityRestart();
            }
        }
    }
}
