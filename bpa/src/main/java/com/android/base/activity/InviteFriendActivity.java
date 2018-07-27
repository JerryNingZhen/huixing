package com.android.base.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.android.base.mvp.baseclass.BaseActivity;
import com.android.base.mvp.baseclass.BaseView;
import com.android.base.utils.BitmapUtil;
import com.android.base.utils.dialog.share.ShareBean;
import com.android.base.utils.dialog.share.ShareDialogUtil;
import com.android.base.widget.TitleView;
import com.hx.huixing.R;

/**
 * 邀请好友
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class InviteFriendActivity extends BaseActivity implements BaseView {

    private TitleView title_view;
    private ScrollView scrollView;
    private ImageView img_content;

    private ShareBean shareBean = new ShareBean();
    private ShareDialogUtil popupWindowUtil;

    @Override
    public void initVP() {
        mvpView = this;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_invite_friend;
    }

    @Override
    public void findViews() {
        title_view = findViewByIds(R.id.title_view);
        scrollView = findViewByIds(R.id.scrollView);
        img_content = findViewByIds(R.id.img_content);
    }

    @Override
    public void init(Bundle bundle) {
        if (bundle != null) {// 要查询的用户id
            //            id = bundle.getString(ConstantKey.INTENT_KEY_ID, "");
        }

        shareBean.setPhotoUrl("http://www.mob.com/assets/images/logo-51fcf38a.png");
        shareBean.setTextContent("setTextContent");
        shareBean.setTitle("setTitle");
        shareBean.setContentUrl("www.baidu.com");
        shareBean.setContentId("");
        shareBean.setContentType("");
        String[] nameItems = getResources().getStringArray(R.array.share_types1);
        Integer[] resItems = new Integer[]{R.drawable.share_wechat, //
                R.drawable.share_wechatmoments, //
                R.drawable.share_sina, //
                R.drawable.share_sina, //
                R.drawable.share_sina
        };
        popupWindowUtil = new ShareDialogUtil(this, shareBean, nameItems, resItems);
        String str = "好友注册后你获得%1$s的奖励";
        popupWindowUtil.setTitle(String.format(str, "18HUI"));
        popupWindowUtil.show(shareBean, Gravity.BOTTOM);

        title_view.setTitle("邀请好友");
        //        showProgress(false);
        quaryReviewByUser(10);

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapUtil.takeScreenShot4View1(InviteFriendActivity.this, scrollView);
                if (bitmap != null) {
                    //设置图片充满ImageView控件
                    img_content.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    //等比例缩放
                    img_content.setAdjustViewBounds(true);
                    img_content.setImageBitmap(bitmap);
                    //                    HandlerUtil.sendMessage(mHandler, 1, bitmap);
                }
            }
        });
    }

    //    /**
    //     * 异步处理Handler对象
    //     */
    //    @SuppressLint("HandlerLeak")
    //    private Handler mHandler = new Handler() {
    //
    //        @Override
    //        public void handleMessage(Message msg) {
    //            switch (msg.what) {
    //                case 1: //
    //                    Bitmap bitmap = (Bitmap) msg.obj;
    //                    LogUtil.i(String.valueOf(bitmap.getByteCount() / 1024f));
    //                    //设置图片充满ImageView控件
    //                    img_content.setScaleType(ImageView.ScaleType.CENTER_CROP);
    //                    //等比例缩放
    //                    img_content.setAdjustViewBounds(true);
    //                    img_content.setImageBitmap(bitmap);
    //
    //                    //                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    //                    //                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
    //                    //                    byte[] bytes = bos.toByteArray();
    //                    //                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    //                    //                    LogUtil.i(String.valueOf(bitmap.getByteCount() / 1024f));
    //                    //                    Glide.with(InviteFriendActivity.this).load(bytes).into(img_content);
    //                    break;
    //            }
    //        }
    //    };

    @Override
    public void widgetListener() {
        title_view.setLeftBtnImg();

    }

    @Override
    public void setViewData(Object object) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 他的主页---文章列表
     */
    private void quaryReviewByUser(int pageSize) {

        //        final HashMap<String, String> params = new HashMap<>();
        //        //            currentPage: 1         // 分页信息，从1开始
        //        //            pageSize: 12            // 分页信息
        //        //            creator: 42e7ce4d  // 要查询的人的id
        //        //            type: 4                     // 类型，查询文章类型给4
        //        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.MOTHED_QUARYREVIEWBYUSER);
        //        params.put("creator", id);
        //        params.put("currentPage", currentPage + "");
        //        params.put("pageSize", pageSize + "");
        //        params.put("type", "4");
        //        //        params.put("loginUser", BaseApplication.getInstance().getUserInfoBean().getId());
        //
        //        RequestExecutor.addTask(new BaseTask() {
        //            @Override
        //            public ResponseBean sendRequest() {
        //                return HttpOkBiz.getInstance().sendGet(params);
        //            }
        //
        //            @Override
        //            public void onSuccess(ResponseBean result) {
        //                BaseBean.setResponseObjectList(result, ArticleDetailBean.class, "");
        //                ArrayList<ArticleDetailBean> beans = (ArrayList<ArticleDetailBean>) result.getObject();
        //                //                //                ArrayList<ArticleCommentBean> beans = GsonUtil.getInstance().gson.fromJson((String) result.getObject(), new TypeToken<List<ArticleCommentBean>>() {
        //                //                //                }.getType());
        //                if (currentPage == 1) {
        //                    dataBeans.clear();
        //                }
        //                for (int i = 0; i < beans.size(); i++) {
        //                    PersonalFocusBean bean = new PersonalFocusBean();
        //                    bean.setCreator(beans.get(i).getCreator());
        //                    bean.setHasFollowed(beans.get(i).getHasFollowed());
        //                    bean.setPersonIntro(beans.get(i).getPersonIntro());
        //                    bean.setType(beans.get(i).getType());
        //                    bean.setRealName(beans.get(i).getRealName());
        //                    bean.setUserPic(beans.get(i).getUserPic());
        //                    dataBeans.add(bean);
        //                }
        //                //                dataBeans.addAll(beans);
        //                adapter.notifyDataSetChanged();
        //                dismissProgress();
        //                refreshFinish();
        //            }
        //
        //            @Override
        //            public void onFail(ResponseBean result) {
        //                dismissProgress();
        //                showToast(result.getInfo());
        //                refreshFinish();
        //            }
        //        });
    }
}