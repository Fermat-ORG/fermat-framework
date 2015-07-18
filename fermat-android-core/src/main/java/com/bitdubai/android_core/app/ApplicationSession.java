package com.bitdubai.android_core.app;


import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Window;
import android.widget.TextView;

import com.bitdubai.android_core.app.common.version_1.classes.MyTypefaceSpan;
import com.bitdubai.android_core.app.common.version_1.tabbed_dialog.PagerSlidingTabStrip;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.AppRuntimeManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.TitleBar;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.WalletRuntimeManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_core.Platform;


/**
 * This class, is created by the Android OS before any Activity. That means its constructor is run before any other code
 * written by ourselves.
 *
 * -- Luis.
 */


public class ApplicationSession extends android.support.multidex.MultiDexApplication {

    private final static Handler handler = new Handler();
    public static Typeface mDefaultTypeface;
    public static int mTAGID;
    public static int mID;
    public static Object[] mParams;
    public static String mContact;
    public static int mWalletId;
    public static String mActivityId;
    public static String mCHILDID = "";
    public static String mTicketID;
    private static CharSequence mTitle;
    private static Drawable oldBackground = null;
    private static int currentColor = 0xFF666666;
    public static ActionBar actionBar;
    private static String walletStyle = "";
    private static PagerSlidingTabStrip tabs;

    private static AppRuntimeManager appRuntimeMiddleware;
    private static WalletRuntimeManager walletRuntimeMiddleware;

    private static ErrorManager errorManager;

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
        ApplicationSession.actionBar = actionBar;
    }
    public static void setWalletId(int WalletId) {
        mWalletId = WalletId;
    }
    public static void setAppRuntime(AppRuntimeManager appRuntime) {
        appRuntimeMiddleware = appRuntime;
    }

    public static AppRuntimeManager getAppRuntime() {
        return appRuntimeMiddleware;
    }

    public static void setWalletRuntime(WalletRuntimeManager walletRuntime) {
        walletRuntimeMiddleware = walletRuntime;
    }

    public static void setErrorManager(ErrorManager errorManager) {
        errorManager = errorManager;
    }

    public static ErrorManager getErrorManager() {
        return errorManager;
    }


    public static WalletRuntimeManager getwalletRuntime() {
        return walletRuntimeMiddleware;
    }

    public static Typeface getDefaultTypeface() {
        return mDefaultTypeface;
    }
    public static int getTagId() {
        return mTAGID;
    }
    public static int getId() {
        return mID;
    }
    public static String getTicketId() {
        return mTicketID;
    }
    public static String getContact() {
        return mContact;
    }
    public static String getChildId() {
        return mCHILDID;
    }
    public static Object[] getParams() {
        return mParams;
    }
    public static void setChildId(String childId) { mCHILDID = childId; }
    public static void setTagId(int TagId) { mTAGID = TagId; }
    public static void setId(int TagId) { mID = TagId; }
    public static void setTicketId(String TagId) { mTicketID = TagId; }
    public static void setParams(Object[] params) { mParams = params; }
    public static void setContact(String contact_name) { mContact = contact_name; }
    public static void setActivityId(String activity_name) { mActivityId = activity_name; }
    public static void setDefaultTypeface(Typeface DefaultTypeface) {
        mDefaultTypeface = DefaultTypeface;
    }


    private static Platform fermatPlatform;


    public static Platform getFermatPlatform() {
        return fermatPlatform;
    }

    public ApplicationSession() {
        super();

        fermatPlatform = new Platform();
        //Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/CaviarDreams.ttf");
        //setDefaultTypeface(tf);


    }




    // TODO: Mover este metodo a un lugar mas apropiado. No debería estar en esta clase que representa la Sesión de toda la APP
    // TODO: Junto con esto mover el call back que sale justo abajo de este metodo.

    /**
     * This method is used to change the background color of the current activity.
     *
     * -- Luis.
     */



    // acá se cambia el color del actionBar
    public static void changeColor(int newColor, Resources context,ActionBar actionBar) {

        try
        {
            // change ActionBar color just if an ActionBar is available
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                Drawable colorDrawable = new ColorDrawable(newColor);
                Drawable bottomDrawable = context.getDrawable(R.drawable.actionbar_bottom);
                LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});

                //if (oldBackground == null) {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    ld.setCallback(drawableCallback);
                } else {
                    actionBar.setBackgroundDrawable(ld);
                }

                /*} else {

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
                }*/

                //oldBackground = ld;

                // http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
                //actionBar.setDisplayShowTitleEnabled(false);
                //actionBar.setDisplayShowTitleEnabled(true);

                //}
            //}

            //currentColor = newColor;
        }
        catch (Exception e)
        {
            // TODO: Si ocurre un error intentando setear el color se debe reportar en el error manager pero no propagar la excepcion porque no es critico. Las actividades debieran tener un colorpor defecto para que si falla la asignacion quede el color por defecto.

             e.printStackTrace();
        }


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