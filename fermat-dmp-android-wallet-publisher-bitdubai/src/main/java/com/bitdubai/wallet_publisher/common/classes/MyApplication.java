package com.bitdubai.wallet_publisher.common.classes;

import android.app.ActionBar;
import android.app.Application;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Handler;

import com.bitdubai.wallet_publisher.R;

/**
 * Created by toshiba on 27/04/2015.
 */
public class MyApplication  extends Application {
    public static Typeface mDefaultTypeface;
    public static ActionBar actionBar;
    private static PagerSlidingTabStrip tabs;
    private static CharSequence mTitle;
    private static Drawable oldBackground = null;
    private static int currentColor = 0xFF666666;
    private final static Handler handler = new Handler();

    public static Typeface getDefaultTypeface() {
        return mDefaultTypeface;
    }

    public static void setDefaultTypeface(Typeface DefaultTypeface) {
        mDefaultTypeface = DefaultTypeface;
    }

    public static ActionBar getActionBar() {
        return actionBar;
    }
    public static void setActionBar(ActionBar actionBar) {
        MyApplication.actionBar = actionBar;
    }

    public static void changeColor(int newColor, Resources context) {



        if(tabs != null)
            tabs.setIndicatorColor(newColor);

        // change ActionBar color just if an ActionBar is available
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            Drawable colorDrawable = new ColorDrawable(newColor);
            Drawable bottomDrawable = context.getDrawable(R.drawable.actionbar_bottom);
            LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });

            if (oldBackground == null) {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    ld.setCallback(drawableCallback);
                } else {
                    actionBar.setBackgroundDrawable(ld);
                }

            } else {

                TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });

                // workaround for broken ActionBarContainer drawable handling on
                // pre-API 17 builds
                // https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    td.setCallback(drawableCallback);
                } else {
                    actionBar.setBackgroundDrawable(td);
                }

                td.startTransition(200);

            }

            oldBackground = ld;

            // http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);

        }

        currentColor = newColor;

    }

    private static Drawable.Callback drawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            actionBar.setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
            handler.postAtTime(what, when);
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
            handler.removeCallbacks(what);
        }
    };
}
