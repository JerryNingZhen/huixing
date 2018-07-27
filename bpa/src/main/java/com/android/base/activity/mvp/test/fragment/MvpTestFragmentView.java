package com.android.base.activity.mvp.test.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hx.huixing.R;
import com.android.base.mvp.baseclass.MvpBaseView;
import com.android.base.activity.mvp.test.activity.MvpTestActivity;
import com.android.base.configs.ConstantKey;
import com.android.base.utils.IntentUtil;
import com.android.base.widget.TitleView;

import java.util.ArrayList;

public class MvpTestFragmentView extends MvpBaseView<MvpTestFragment> {

    /** 标题栏 */
    private TitleView titleview;
    private TextView item0;
    private View item1;
    private View item2;
    private View item3;
    private View item4;
    private View item5;
    private View item6;
    private View item7;
    private View item8;
    private View item9;
    private View item10;
    private View item11;
    private LinearLayout view_heard;

    public MvpTestFragmentView(MvpTestFragment baseUI) {
        super(baseUI);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    public void findViews() {
        titleview = findViewByIds(R.id.title_view);
        item0 = findViewByIds(R.id.item0);
        item1 = findViewByIds(R.id.item1);
        item2 = findViewByIds(R.id.item2);
        item3 = findViewByIds(R.id.item3);
        item4 = findViewByIds(R.id.item4);
        item5 = findViewByIds(R.id.item5);
        item6 = findViewByIds(R.id.item6);
        item7 = findViewByIds(R.id.item7);
        item8 = findViewByIds(R.id.item8);
        item9 = findViewByIds(R.id.item9);
        item10 = findViewByIds(R.id.item10);
        item11 = findViewByIds(R.id.item11);
        view_heard = findViewByIds(R.id.view_heard);
        //        BannerView bannerView = new BannerView(baseUI.getContext());
        //        view_heard.addView(bannerView);
        item0.setText(getClass().getName());
        item1.setVisibility(View.GONE);
        item2.setVisibility(View.GONE);
        item3.setVisibility(View.GONE);
        item4.setVisibility(View.GONE);
        item5.setVisibility(View.GONE);
        item6.setVisibility(View.GONE);
        item7.setVisibility(View.GONE);
        item8.setVisibility(View.GONE);
        item9.setVisibility(View.GONE);
        item10.setVisibility(View.GONE);
        item11.setVisibility(View.GONE);
        view_heard.setVisibility(View.GONE);
    }

    @Override
    public void init(Bundle bundle) {
        titleview.setTitle("MvpTestFragment");
        if (bundle != null) {
            String type = bundle.getString(ConstantKey.INTENT_KEY_TYPE, "");
            titleview.setTitle("MvpTestFragment" + type);
        }
        if (baseUI.getActivity() instanceof TestActivity) {
            ((TestActivity) baseUI.getActivity()).setTintResource(R.color.font_black);
        }
    }

    @Override
    public void widgetListener() {
        titleview.setLeftBtnImg();
        item0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                baseUI.getMvpPresenter().loadData();
                baseUI.getMvpPresenter().loadData1();
                baseUI.test();
                ((TestActivity) baseUI.getActivity()).test();
            }
        });
    }

    private ArrayList<String> arrayList = new ArrayList<>();

    @Override
    public void setViewData(Object object) {
        arrayList.add(String.valueOf(object));
        item0.setText(String.valueOf(arrayList.size()));
        Bundle bundle = new Bundle();
        bundle.putString(ConstantKey.INTENT_KEY_TYPE, "type");
        IntentUtil.gotoActivity(baseUI.getActivity(), MvpTestActivity.class, bundle);
    }

    public String getTest() {
        return item0.getText().toString();
    }

}
