package com.bitdubai.smartwallet.ui.os.android.app.common.version_1.classes;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Window;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;


public class MyApplication extends Application {
    private final static Handler handler = new Handler();
    public static Typeface mDefaultTypeface;
    public static int mTAGID;
    public static String mContact;
    public  static int mWalletId;
    public  static String mActivityId;
    public static String mCHILDID;
    private static CharSequence mTitle;
    private static Drawable oldBackground = null;
    private static int currentColor = 0xFF666666;
    public static ActionBar actionBar;
    private static String walletStyle = "";
    private static PagerSlidingTabStrip tabs;
    public static String getActivityId() {
        return mActivityId;
    }
    public static int  getWalletId() {
        return mWalletId;
    }

    public static ActionBar getActionBar() {
        return actionBar;
    }

    public static void setActionBar(ActionBar actionBar) {
        MyApplication.actionBar = actionBar;
    }

    public  static void setWalletId(int WalletId) {
        mWalletId = WalletId;
    }

    public static Typeface getDefaultTypeface() {
        return mDefaultTypeface;
    }

    public static int getTagId() {
        return mTAGID;
    }

    public static String getContact() {
        return mContact;
    }

    public static String getChildId() {
        return mCHILDID;
    }
    public static void setChildId(String childId) { mCHILDID = childId; }
    public static void setTagId(int TagId) { mTAGID = TagId; }
    public static void setContact(String contact_name) { mContact = contact_name; }
    public static void setActivityId(String activity_name) { mActivityId = activity_name; }
    public static void setDefaultTypeface(Typeface DefaultTypeface) {
        mDefaultTypeface = DefaultTypeface;
    }


    public static void setActionBarProperties(Activity activity, Window window,  PagerSlidingTabStrip pTabs, ActionBar pActionBar, Resources context,  TextView abTitle, String pTitle) {
        actionBar = pActionBar;
        tabs = pTabs;
        // Change the title of the action bar and the typeface
        SpannableString s = new SpannableString("Unidentified wallet");
        String color = "";
        mTitle = pTitle;
        Drawable bg = context.getDrawable(R.drawable.transparent);
        bg.setVisible(false,false);

        Drawable wallpaper = context.getDrawable(R.drawable.transparent);
        switch (getWalletId() )
        {
            case 1:

                s = new SpannableString(mTitle);
                color = "#FFC2F1";

                actionBar.setIcon(context.getDrawable(R.drawable.icono_piggy_pink));
                bg = context.getDrawable(R.drawable.wallet_wallpaper_pink);
                bg.setVisible(true,false);
                wallpaper = context.getDrawable(R.drawable.wallet_wallpaper_pink);
                abTitle.setTextColor(Color.BLACK);
                walletStyle = "Kids";
                break;

            case 2:

                s = new SpannableString(mTitle);
                color = "#84DCF5";
                actionBar.setIcon(context.getDrawable(R.drawable.icono_piggy_yellow));
                bg = context.getDrawable(R.drawable.banner_kid_yellow_blue);
                bg.setVisible(true,false);
                wallpaper = context.getDrawable(R.drawable.wallet_wallpaper_yellow);
                abTitle.setTextColor(Color.BLACK);
                walletStyle = "Kids";
                break;

            case 3:

                s = new SpannableString(mTitle);
                color = "#F0E173";
                actionBar.setIcon(context.getDrawable(R.drawable.wallet_1));
                wallpaper = context.getDrawable(R.drawable.background_tabs_diagonal_rotated);
                abTitle.setTextColor(Color.BLACK);
                walletStyle = "Young";
                break;

            case 4:

                s  = new SpannableString(mTitle);
                color = "#1EE635";
                actionBar.setIcon(context.getDrawable(R.drawable.wallet_3));
                wallpaper = context.getDrawable(R.drawable.background_tiled_diagonal_light);
                abTitle.setTextColor(Color.BLACK);
                walletStyle = "Club";
                break;

            case 5:

                s = new SpannableString(mTitle);
                color = "#F0C64A";
                actionBar.setIcon(context.getDrawable(R.drawable.wallet_3));
                wallpaper = context.getDrawable(R.drawable.background_tiled_diagonal_light);
                abTitle.setTextColor(Color.BLACK);
                walletStyle = "Club";
                break;

            case 6:

                s = new SpannableString(mTitle);
                color = "#9B80FF";
                actionBar.setIcon(context.getDrawable(R.drawable.wallet_3));
                wallpaper = context.getDrawable(R.drawable.background_tiled_diagonal_light);
                abTitle.setTextColor(Color.BLACK);
                walletStyle = "Club";
                break;

            case 7:

                s = new SpannableString(mTitle);
                color = "#E8E8E8";
                actionBar.setIcon(context.getDrawable(R.drawable.icono_retailer_1));
                wallpaper = context.getDrawable(R.drawable.background_tiled_diagonal_light);
                abTitle.setTextColor(Color.BLUE);
                walletStyle = "Club";
                break;

            case 8:

                s = new SpannableString(mTitle);
                color = "#AB0A80";
                actionBar.setIcon(context.getDrawable(R.drawable.icono_banco_1));
                wallpaper = context.getDrawable(R.drawable.background_tiled_diagonal_light);
                abTitle.setTextColor(Color.YELLOW);
                walletStyle = "Club";
                break;

            case 9:

                s = new SpannableString(mTitle);
                color = "#FF0004";
                actionBar.setIcon(context.getDrawable(R.drawable.icono_banco_2));
                wallpaper = context.getDrawable(R.drawable.background_tiled_diagonal_light);
                abTitle.setTextColor(Color.WHITE);
                walletStyle = "Club";
                break;

            case 10:

                s = new SpannableString(mTitle);
                color = "#3864F5";
                actionBar.setIcon(context.getDrawable(R.drawable.icono_club_1));
                wallpaper = context.getDrawable(R.drawable.background_tiled_diagonal_light);
                abTitle.setTextColor(Color.YELLOW);
                bg = context.getDrawable(R.drawable.banner_club_1);
                bg.setVisible(true,false);
                walletStyle = "Club";
                break;

            case 11:

                s = new SpannableString(mTitle);
                color = "#DE186B";
                actionBar.setIcon(context.getDrawable(R.drawable.icono_club_2));
                abTitle.setTextColor(Color.WHITE);
                wallpaper = context.getDrawable(R.drawable.wallet_wallpaper_club_2);
                bg = context.getDrawable(R.drawable.banner_club_2);
                bg.setVisible(true,false);
                walletStyle = "Club";
                break;
        }


        s.setSpan(new MyTypefaceSpan( activity, "CaviarDreams.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance
        actionBar.setTitle(s);

        changeColor(Color.parseColor(color), context);

        window.getDecorView().setBackground(wallpaper);
        if (bg.isVisible() == true) {actionBar.setBackgroundDrawable(bg);}
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