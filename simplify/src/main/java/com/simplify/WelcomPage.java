package com.simplify;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shujian on 14-8-13.
 */
public class WelcomPage {

    private Activity activity;

    private ViewPager viewPager;

    ViewPager.OnPageChangeListener onPageChangeListener;


    public WelcomPage(Activity activity) {
        this.activity = activity;
    }

    private void setTranslucentStatus(boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void show(@DrawableRes int... resIds) {
        show(null, resIds);
    }

    /**
     * display welcome page
     *
     * @param listener
     * @param resIds
     */
    public void show(@Nullable final OnDismissListener listener, @DrawableRes int... resIds) {

        viewPager = new ViewPager(activity);
        viewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        decorView.addView(viewPager);


        final ArrayList<View> views = new ArrayList<View>();

        int[] pics = resIds;
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        //初始化引导图片列表
        for (int i = 0; i < pics.length; i++) {
            ImageView iv = new ImageView(activity);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setLayoutParams(mParams);
            if (i == pics.length - 1) {
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ViewGroup) activity.getWindow().getDecorView()).removeView(viewPager);
                        if (listener != null) {
                            listener.onDismiss();
                        }
                        if (onPageChangeListener != null) {
                            viewPager.removeOnPageChangeListener(onPageChangeListener);
                        }
                    }
                });
            }
            iv.setImageResource(pics[i]);
            views.add(iv);
        }


        onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        viewPager.addOnPageChangeListener(onPageChangeListener);
        viewPager.setAdapter(new WelcomPageAdapter(views));


    }

    /**
     * welcom callback
     */
    public interface OnDismissListener {
        void onDismiss();
    }


    public static class WelcomPageAdapter extends PagerAdapter {
        private List<View> mListViews;

        public WelcomPageAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }
}



