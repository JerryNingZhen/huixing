package com.android.base.utils.dialog.share;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.base.adapter.ViewPagerAdapter;
import com.android.base.utils.LogUtil;
import com.android.base.utils.ScreenUtil;
import com.hx.huixing.R;
import com.mob.MobSDK;
import com.mob.tools.utils.UIHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * sharesdk分享助手类
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ShareDialogUtil {

    /** gridview 最大的item数 */
    private static final int MAX_ITEM = 12;
    /** 对话框Dialog */
    private Dialog dialog;
    /** 上下文 */
    private Context mContent;
    /** 数据源 */
    private ShareBean shareBean = new ShareBean();
    /** 分享平台名称 */
    private List<String> nameList;
    /** 分享平台图片 */
    private List<Integer> resList;
    /** dialog的背景色 */
    private LinearLayout view_dialog_parent;
    private TextView txt_title;
    /** 装点点的ImageView数组 */
    private List<ImageView> tipImgs;
    /** 当前viewpage选中的页码 */
    private int currentItem = 0;

    public ShareDialogUtil(Context context, ShareBean bean, String[] nameItems, Integer[] resItems) {
        shareBean = bean;
        mContent = context;
        startData(nameItems, resItems);
        initDialog();
    }

    /**
     * 设置分享平台数据源： 图片 和 名字
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-28,下午5:33:27
     * <br> UpdateTime: 2016-12-28,下午5:33:27
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void startData(String[] nameItems, Integer[] resItems) {
        nameList = new ArrayList<>();
        resList = new ArrayList<>();

        if (nameItems == null || resItems == null) {
            nameItems = mContent.getResources().getStringArray(R.array.share_types_default);
            resItems = new Integer[]{R.drawable.share_qq, //
                    R.drawable.share_wechat, //
                    R.drawable.share_sina, //
                    // R.drawable.share_twitter, //
                    // R.drawable.share_facebook, //
                    R.drawable.share_wechatmoments //
            };
        }

        nameList = Arrays.asList(nameItems);
        resList = Arrays.asList(resItems);
    }

    /**
     * 初始化dialog
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-28,下午5:33:35
     * <br> UpdateTime: 2016-12-28,下午5:33:35
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void initDialog() {

        dialog = new Dialog(mContent, R.style.dialog_style);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setTitle(mContent.getString(R.string.share));

        Window window = dialog.getWindow(); // 得到对话框
        if (window != null) {
            window.setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画
        }

        View view = View.inflate(mContent, R.layout.share_dialog_util, null);
        /** 视图切换ViewPager */
        ViewPager viewpager = (ViewPager) view.findViewById(R.id.share_viewpager);
        /** 视图切换进度条 */
        LinearLayout view_slider = (LinearLayout) view.findViewById(R.id.view_slider);
        view_dialog_parent = (LinearLayout) view.findViewById(R.id.view_dialog_partent);
        View btn_cancel = view.findViewById(R.id.btn_cancel);
        txt_title = view.findViewById(R.id.txt_title);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {// WechatMoments
                    dialog.dismiss();
                }
            }
        });
        // 初始化ViewPager，item view
        if (!nameList.isEmpty()) {
            /** 装有item view的list */
            ArrayList<View> itemViews = new ArrayList<>();
            /** 装点点的ImageView数组 */
            tipImgs = new ArrayList<>();
            int index;
            if (nameList.size() / MAX_ITEM >= 1) {
                if (nameList.size() % MAX_ITEM > 0) {
                    index = nameList.size() / MAX_ITEM + 1;
                } else {
                    index = nameList.size() / MAX_ITEM;
                }
                view_slider.setVisibility(View.VISIBLE);
            } else {
                index = 1;
                view_slider.setVisibility(View.GONE);
            }

            for (int i = 0; i < index; i++) {
                // ViewPager itemView
                View pageItemView = View.inflate(mContent, R.layout.share_dialog_util_gridview, null);
                GridView shareGridView = (GridView) pageItemView.findViewById(R.id.share_grid);
                // GridView Adapter
                ShareDialogAdapter adapter;
                if (nameList.size() >= MAX_ITEM * (i + 1)) {
                    adapter = new ShareDialogAdapter(mContent, new ArrayList<>(nameList.subList((MAX_ITEM * i), MAX_ITEM * (i + 1))), resList.subList((MAX_ITEM * i), MAX_ITEM * (i + 1)));
                } else {
                    adapter = new ShareDialogAdapter(mContent, new ArrayList<>(nameList.subList((MAX_ITEM * i), nameList.size())), resList.subList((MAX_ITEM * i), nameList.size()));
                }
                shareGridView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                shareGridView.setOnItemClickListener(itemListener);
                itemViews.add(pageItemView);

                // 将点点加入到ViewGroup中
                ImageView img = new ImageView(mContent);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.leftMargin = ScreenUtil.dip2px(5);
                img.setLayoutParams(layoutParams);
                view_slider.addView(img);
                tipImgs.add(img);
            }

            // ViewPager Adapter
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(itemViews);
            viewpager.setAdapter(viewPagerAdapter);
            viewpager.setOnPageChangeListener(onPageChangeListener);
            viewpager.setCurrentItem(currentItem);
            setImageBackground(currentItem);
        }

        dialog.setContentView(view);
    }

    /**
     * 设置选中的tip的背景
     */
    private void setImageBackground(int selectItems) {
        currentItem = selectItems;
        if (!tipImgs.isEmpty()) {
            for (int i = 0; i < tipImgs.size(); i++) {
                if (i == selectItems) {
                    tipImgs.get(i).setBackgroundResource(R.drawable.img_point_bule);
                } else {
                    tipImgs.get(i).setBackgroundResource(R.drawable.img_point_white);
                }
            }
        }
    }

    /**
     * viewpager 页卡改变监听器
     */
    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            setImageBackground(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    /**
     * GridView itemListener
     */
    private OnItemClickListener itemListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            int resId = resList.get(arg2 + (currentItem * MAX_ITEM));
            if (resId == R.drawable.share_qq) {
                if (dialog.isShowing()) {// QQ
                    dialog.dismiss();
                }
                share(QQ.NAME);
            } else if (resId == R.drawable.share_wechat) {
                if (dialog.isShowing()) {// Wechat
                    dialog.dismiss();
                }
                share(Wechat.NAME);
            } else if (resId == R.drawable.share_sina) {
                if (dialog.isShowing()) { // 新浪微博
                    dialog.dismiss();
                }
                share(SinaWeibo.NAME);
            } else if (resId == R.drawable.share_wechatmoments) {
                if (dialog.isShowing()) {// WechatMoments
                    dialog.dismiss();
                }
                share(WechatMoments.NAME);

            }
        }
    };

    public void share(String platform) {
        MobSDK.init(mContent);
        Platform plat = ShareSDK.getPlatform(platform);

        // if ((platform.equals(Wechat.NAME) ||
        // platform.equals(WechatMoments.NAME)) &&
        // (!plat.isClientValid())) {
        // Toast toast = Toast.makeText(mContent,
        // mContent.getText(R.string.wechat_client_inavailable),
        // Toast.LENGTH_SHORT);
        // toast.show();
        // } else {
        Platform.ShareParams sp = getShareParams(platform);
        plat.setPlatformActionListener(platformActionListener);
        plat.share(sp);

        Toast toast = Toast.makeText(mContent, mContent.getString(R.string.share_wait), Toast.LENGTH_SHORT);
        toast.show();
        // }
    }

    private Platform.ShareParams getShareParams(String platform) {
        Platform.ShareParams sp = new Platform.ShareParams();
        try {
            if (shareBean != null) {
                if (QQ.NAME.equals(platform)) {// qq好友分享

                    if (shareBean.getTextContent() != null) {
                        if (shareBean.getTextContent().contains(" ")) {
                            sp.setText(shareBean.getTextContent().replaceAll(" ", "_"));
                        } else {
                            sp.setText(shareBean.getTextContent());
                        }
                    }
                    if (shareBean.getTitle() != null) {
                        sp.setTitle(shareBean.getTitle());
                    }
                    if (shareBean.getPhotoPath() != null) {
                        sp.setImagePath(shareBean.getPhotoPath());
                    } else if (shareBean.getPhotoUrl() != null) {
                        sp.setImageUrl(shareBean.getPhotoUrl());
                    }
                    if (shareBean.getContentUrl() != null) {
                        sp.setTitleUrl(shareBean.getContentUrl());
                    }

                } else if (Wechat.NAME.equals(platform)) {// 微信好友分享
                    sp.setShareType(Platform.SHARE_WEBPAGE);
                    if (shareBean.getTextContent() != null) {
                        String text = shareBean.getTextContent();
                        if (text.getBytes().length > 1024) {
                            byte[] bytes = text.getBytes();
                            bytes = Arrays.copyOfRange(bytes, 0, 1023);
                            text = new String(bytes);
                        }
                        sp.setText(text);
                    }
                    if (shareBean.getTitle() != null) {
                        String title = shareBean.getTitle();
                        if (title.getBytes().length > 512) {
                            byte[] bytes = title.getBytes();
                            bytes = Arrays.copyOfRange(bytes, 0, 511);
                            title = new String(bytes);
                        }
                        sp.setTitle(title);
                    }
                    if (shareBean.getPhotoPath() != null) {
                        sp.setImagePath(shareBean.getPhotoPath());
                    } else if (shareBean.getPhotoUrl() != null) {
                        sp.setImageUrl(shareBean.getPhotoUrl());
                    } else {
                        Drawable logo = mContent.getResources().getDrawable(R.drawable.icon_launcher);
                        sp.setImageData(((BitmapDrawable) logo).getBitmap());
                    }
                    if (shareBean.getContentUrl() != null) {
                        sp.setUrl(shareBean.getContentUrl());
                    }

                } else if (SinaWeibo.NAME.equals(platform)) {// 新浪微博
                    // 分享图文 text imagePath/imageUrl latitude(可选) longitude(可选)
                    String text = "";
                    if (shareBean.getTitle() != null) {
                        text = shareBean.getTitle();
                        if (shareBean.getTextContent() != null) {
                            text = text + "\n" + shareBean.getTextContent();
                        }

                        if (!TextUtils.isEmpty(text) && text.length() > 100) {
                            text = text.substring(0, 99);
                        }

                        text = text + "\n" + shareBean.getContentUrl();
                    }
                    sp.setText(text);
                    if (shareBean.getPhotoPath() != null) {
                        sp.setImagePath(shareBean.getPhotoPath());
                    } else if (shareBean.getPhotoUrl() != null) {
                        sp.setImageUrl(shareBean.getPhotoUrl());
                    }

                } else if (WechatMoments.NAME.equals(platform)) {// 微信朋友圈分享

                    sp.setShareType(Platform.SHARE_WEBPAGE);
                    if (shareBean.getTextContent() != null) {
                        String text = shareBean.getTextContent();
                        if (text.getBytes().length > 1024) {
                            byte[] bytes = text.getBytes();
                            bytes = Arrays.copyOfRange(bytes, 0, 1023);
                            text = new String(bytes);
                        }
                        sp.setText(text);
                    }

                    if (shareBean.getTitle() != null) {

                        String title = shareBean.getTitle() + " | " + shareBean.getTextContent();
                        if (title.getBytes().length > 512) {
                            byte[] bytes = title.getBytes();
                            bytes = Arrays.copyOfRange(bytes, 0, 511);
                            title = new String(bytes);
                        }
                        sp.setTitle(title);
                    }

                    if (shareBean.getPhotoPath() != null) {
                        sp.setImagePath(shareBean.getPhotoPath());
                    } else if (shareBean.getPhotoUrl() != null) {
                        sp.setImageUrl(shareBean.getPhotoUrl());
                    } else {
                        Drawable logo = mContent.getResources().getDrawable(R.drawable.icon_launcher);
                        sp.setImageData(((BitmapDrawable) logo).getBitmap());
                    }
                    if (shareBean.getContentUrl() != null) {
                        sp.setUrl(shareBean.getContentUrl());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sp;
    }

    /**
     * 平台操作回调
     */
    private PlatformActionListener platformActionListener = new PlatformActionListener() {

        @Override
        public void onComplete(Platform plat, int action, HashMap<String, Object> res) {

            if (shareCallBack != null) {
                shareCallBack.shareOnComplete(plat.getName());
            }
            Message msg = new Message();
            msg.arg1 = 1;
            msg.arg2 = action;
            msg.obj = plat;
            UIHandler.sendMessage(msg, callBack);
        }

        @Override
        public void onCancel(Platform plat, int action) {

            if (shareCallBack != null) {
                shareCallBack.shareOnCancle(plat.getName());
            }
            Message msg = new Message();
            msg.arg1 = 3;
            msg.arg2 = action;
            msg.obj = plat;
            UIHandler.sendMessage(msg, callBack);
        }

        @Override
        public void onError(Platform plat, int action, Throwable t) {
            t.printStackTrace();
            LogUtil.saveErrorLog(t);
            t.printStackTrace();
            if (shareCallBack != null) {
                shareCallBack.shareOnError(plat.getName());
            }
            Message msg = new Message();
            msg.arg1 = 2;
            msg.arg2 = action;
            msg.obj = plat;
            UIHandler.sendMessage(msg, callBack);
        }
    };

    /**
     * 分享回调
     */
    private Callback callBack = new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            Platform plat = (Platform) msg.obj;
            String text = actionToString(msg.arg2);
            switch (msg.arg1) {
                case 1:
                    text = getPlatName(plat.getName()) + mContent.getString(R.string.share_start);// 成功
                    break;
                case 2:
                    text = getPlatName(plat.getName()) + mContent.getString(R.string.share_fail);// 失败
                    break;
                case 3:
                    text = getPlatName(plat.getName()) + mContent.getString(R.string.share_cancel);// 取消
                    break;
            }

            Toast.makeText(mContent, text, Toast.LENGTH_SHORT).show();
            return false;
        }
    };

    private String getPlatName(String plat) {
        String name = "";

        if (Wechat.NAME.equals(plat)) {
            name = mContent.getString(R.string.share_wechat);
        } else if (WechatMoments.NAME.equals(plat)) {
            name = mContent.getString(R.string.share_wechatmoments);
        } else if (QQ.NAME.equals(plat)) {
            name = "QQ";
        } else if (SinaWeibo.NAME.equals(plat)) {
            name = mContent.getString(R.string.share_sina);
        }
        return name;
    }

    /**
     * 将action转换为String
     */
    private String actionToString(int action) {
        switch (action) {
            case Platform.ACTION_AUTHORIZING:
                return mContent.getString(R.string.share_validation);
            case Platform.ACTION_GETTING_FRIEND_LIST:
                return "ACTION_GETTING_FRIEND_LIST";
            case Platform.ACTION_FOLLOWING_USER:
                return "ACTION_FOLLOWING_USER";
            case Platform.ACTION_SENDING_DIRECT_MESSAGE:
                return "ACTION_SENDING_DIRECT_MESSAGE";
            case Platform.ACTION_TIMELINE:
                return "ACTION_TIMELINE";
            case Platform.ACTION_USER_INFOR:
                return "ACTION_USER_INFOR";
            case Platform.ACTION_SHARE:
                return mContent.getString(R.string.share_of);
            default:
                return "UNKNOWN";
        }
    }

    /**
     * dialog显示、默认显示在屏幕中间
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-28,下午5:35:19
     * <br> UpdateTime: 2016-12-28,下午5:35:19
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param shareBean
     *         分享数据
     */
    public void show(ShareBean shareBean) {
        // 确保分享数据不为空
        this.shareBean = shareBean;
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            } else {
                dialog.show();
            }
        }
    }

    /**
     * dialog显示、自定义显示位置
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-28,下午5:35:19
     * <br> UpdateTime: 2016-12-28,下午5:35:19
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param shareBean
     *         分享数据
     * @param gravity
     *         Gravity.LEFT Gravity.RIGHT Gravity.TOP Gravity.BOTTOM
     */
    public void show(ShareBean shareBean, int gravity) {
        // 确保分享数据不为空
        this.shareBean = shareBean;
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            } else {
                setDialogGravity(gravity);
                dialog.show();
            }
        }
    }

    /**
     * 设置dialog的弹出位置。除了默认的弹出方式，dialog的宽度为默认宽度，其它情况的宽度为MATCH_PARENT
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-29,上午9:46:00
     * <br> UpdateTime: 2016-12-29,上午9:46:00
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param gravity
     *         Gravity.LEFT Gravity.RIGHT Gravity.TOP Gravity.BOTTOM
     */
    private void setDialogGravity(int gravity) {
        Window window = dialog.getWindow(); // 得到对话框
        if (window != null) {
            window.setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画
            window.setGravity(gravity);
            WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
            p.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度设置为自适应
            p.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度设置为屏幕的宽度
        }
    }

    /**
     * dialog消失
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-28,下午5:35:11
     * <br> UpdateTime: 2016-12-28,下午5:35:11
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public void dismss() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    /**
     * 获取dialog的显示状态
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-28,下午5:35:05
     * <br> UpdateTime: 2016-12-28,下午5:35:05
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public Boolean isShowing() {
        Boolean isShow = false;
        if (dialog != null) {
            if (dialog.isShowing()) {
                isShow = true;
            }
        }
        return isShow;
    }

    public void setTitle(String str) {
        if (TextUtils.isEmpty(str)) {
            txt_title.setText("分享");
            txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            TextPaint tp = txt_title.getPaint();
            tp.setFakeBoldText(true);
        } else {
            txt_title.setText(str);
            txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            TextPaint tp = txt_title.getPaint();
            tp.setFakeBoldText(false);
        }
    }

    /**
     * 设置dialog背景色
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-29,上午11:27:03
     * <br> UpdateTime: 2016-12-29,上午11:27:03
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param resId
     *         资源id
     */
    public void setDialogBg(int resId) {
        view_dialog_parent.setBackgroundResource(resId);
    }

    public ShareCallBack getShareCallBack() {
        return shareCallBack;
    }

    public void setShareCallBack(ShareCallBack shareCallBack) {
        this.shareCallBack = shareCallBack;
    }

    /**
     * 分享第三方应用回调监听
     */
    private ShareCallBack shareCallBack;

    /**
     * 分享第三方应用回调监听
     * <p>
     * <br> Author: 叶青
     * <br> Version: 1.0.0
     * <br> Date: 2016年12月11日
     * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
     */
    public interface ShareCallBack {

        /**
         * 分享成功
         *
         * @param platform
         *         第三方平台名字
         */
        void shareOnComplete(String platform);

        /**
         * 分享错误
         *
         * @param platform
         *         第三方平台名字
         */
        void shareOnError(String platform);

        /**
         * 分享取消
         *
         * @param platform
         *         第三方平台名字
         */
        void shareOnCancle(String platform);
    }
}