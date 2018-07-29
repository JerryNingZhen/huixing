package com.hx.huixing.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ListView;

import com.amos.smartrefresh.layout.SmartRefreshLayout;
import com.amos.smartrefresh.layout.api.RefreshLayout;
import com.amos.smartrefresh.layout.listener.OnLoadMoreListener;
import com.amos.smartrefresh.layout.listener.OnRefreshListener;
import com.android.base.BaseApplication;
import com.android.base.configs.ConfigFile;
import com.android.base.configs.ConstantKey;
import com.android.base.utils.FileUtil;
import com.android.base.utils.NetWorkUtil;
import com.android.base.utils.PreferencesUtil;
import com.android.base.utils.ToastUtil;
import com.google.gson.Gson;
import com.hx.huixing.R;
import com.hx.huixing.activity.BaseActivity;
import com.hx.huixing.activity.MainActivity;
import com.hx.huixing.adapter.CareAdapter;
import com.hx.huixing.bean.CareArticleBean;
import com.hx.huixing.bean.UserBean;
import com.hx.huixing.common.biz.Nets;
import com.hx.huixing.common.net.JsonCallBack;
import com.hx.huixing.common.net.RetrofitUtils;
import com.hx.huixing.uitl.Constant;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * <br> Description
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.fragment
 * <br> Date: 2018/7/18
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
@RuntimePermissions
public class CareFragment extends BaseFragment {

    private ArrayList<CareArticleBean.DatasBean> listDatas = new ArrayList<>();
    private MainActivity mActivity;
    private ListView listView;
    private SmartRefreshLayout refresh_view;

    private CareAdapter mAdapter;
    private String articleType = "4";
    private String userId;

    /** 当前页 从1开始 */
    private int curPage = 1;
    /** 每页数量 */
    private String pageSize = "10";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) getActivity();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_care;
    }

    @Override
    protected void findViews() {
        listView = findViewByIds(R.id.list_view);
        refresh_view = findViewByIds(R.id.refresh_view);
    }

    @Override
    public void initGetData() {
        UserBean bean = BaseApplication.getInstance().getUserInfoBean();
        userId = bean.getId();

    }

    @Override
    protected void init() {
        mAdapter = new CareAdapter(mActivity, listDatas);
        listView.setAdapter(mAdapter);
        if (NetWorkUtil.isNetworkAvailable()) {
            ((BaseActivity) getActivity()).showProgress(false);
            getArticleList(String.valueOf(curPage), pageSize, userId, articleType);
        } else {
            ArrayList<CareArticleBean.DatasBean> listDatas1 = (ArrayList<CareArticleBean.DatasBean>) FileUtil.readObject(ConfigFile.PATH_LOG + "care/" + "CareFragment.txt");
            listDatas.addAll(listDatas1);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void widgetListener() {
        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getArticleList(String.valueOf(curPage), pageSize, userId, articleType);
                curPage = 1;

            }
        });

        refresh_view.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                curPage = curPage + 1;
                getArticleList(String.valueOf(curPage), pageSize, userId, articleType);
            }
        });

    }

    /**
     * like为空就是关注，1就是推荐
     *
     * @param currentPage
     * @param pageSize
     * @param loginUser
     * @param type
     */
    private void getArticleList(final String currentPage, final String pageSize, String loginUser, String type) {
        final Map map = new TreeMap();
        map.put("currentPage", currentPage);
        map.put("pageSize", pageSize);
        map.put("like", "");
        map.put("loginUser", loginUser);
        map.put("type", type);

        RetrofitUtils.getInstance().normalGet(Constant.BASE_URL + Nets.quaryReviewByUser, map, new JsonCallBack() {
            @Override
            public void next(String response) {
                ArrayList<CareArticleBean.DatasBean> articles = new ArrayList<>();

                CareArticleBean bean = new Gson().fromJson(response, CareArticleBean.class);
                articles = bean.getDatas();

                if (articles.size() > 0) {
                    if (curPage == 0) {
                        listDatas.clear();
                    }
                    listDatas.addAll(articles);
                    CareFragmentPermissionsDispatcher.ShowPermissionWithPermissionCheck(CareFragment.this);
                }
                mAdapter.notifyDataSetChanged();
                ((BaseActivity) getActivity()).dismissProgress();

                refresh_view.finishLoadMore();
                refresh_view.finishRefresh();
            }

            @Override
            public void error(Throwable e) {
                //((BaseActivity) getActivity()).dismissProgress();
                mActivity.dismissProgress();
                refresh_view.finishLoadMore();
                refresh_view.finishRefresh();
            }

            @Override
            public void startLoading() {

            }

            @Override
            public void closeLoading() {

            }
        });

    }


    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void ShowPermission() {
        FileUtil.createAllFile();
        FileUtil.deleteFolderFile(ConfigFile.PATH_LOG + "care", true);
        FileUtil.writeObject(ConfigFile.PATH_LOG + "care/", "CareFragment.txt", listDatas);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CareFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRation(final PermissionRequest request) {
        new AlertDialog.Builder(mActivity)
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

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showDenied() {
        ToastUtil.showToast(mActivity, "该权限被拒绝，可能会导致功能不可用！");
    }


    public void onActivityRestart() {
        getArticleList(String.valueOf(1), String.valueOf(listDatas.size()), userId, articleType);
    }
}
