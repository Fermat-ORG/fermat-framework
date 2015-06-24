package com.bitdubai.android_core.app.common.version_1.classes;


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

import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.AppRuntimeManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.TitleBar;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_runtime.WalletRuntimeManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.smartwallet.R;
import com.bitdubai.fermat_core.Platform;


/**
 * This class, is created by the Android OS before any Activity. That means its constructor is run before any other code
 * written by ourselves.
 *
 * -- Luis.
 */


public class MyApplication extends android.support.multidex.MultiDexApplication {

    private final static Handler handler = new Handler();
    public static Typeface mDefaultTypeface;
    public static int mTAGID;
    public static int mID;
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
        MyApplication.actionBar = actionBar;
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
    public static void setChildId(String childId) { mCHILDID = childId; }
    public static void setTagId(int TagId) { mTAGID = TagId; }
    public static void setId(int TagId) { mID = TagId; }
    public static void setTicketId(String TagId) { mTicketID = TagId; }
    public static void setContact(String contact_name) { mContact = contact_name; }
    public static void setActivityId(String activity_name) { mActivityId = activity_name; }
    public static void setDefaultTypeface(Typeface DefaultTypeface) {
        mDefaultTypeface = DefaultTypeface;
    }


    private static Platform fermatPlatform;


    public static Platform getFermatPlatform() {
        return fermatPlatform;
    }

    public MyApplication () {
        super();

        fermatPlatform = new Platform();


    }




// TODO: De nuevo este método pretende modificar las propiedades de la Actividad que esta corriendo. Lo que hay que hacer es crear el objeto AndroidActivity con estas propiedades y luego poder setear una a una de acuerdo a lo que haya configurado en el plugin walletruntime y appruntime.


    public static void setActivityProperties(Activity activity, Window window,Resources context,PagerSlidingTabStrip tabStrip,ActionBar actionBar,TitleBar titleBar,TextView abTitle, CharSequence Title)
    {
        try
        {

            Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/CaviarDreams.ttf");
            setDefaultTypeface(tf);

            if(titleBar !=null){

                Title = titleBar.getLabel();

                if(abTitle !=null) {
                    abTitle.setTextColor(Color.WHITE);
                    abTitle.setTypeface(MyApplication.getDefaultTypeface());
                }
                actionBar.setTitle(Title);
                actionBar.show();
                setActionBarProperties(activity,window,tabStrip, actionBar,context,abTitle, Title.toString());
                if (tabStrip != null){

                    tabStrip.setTypeface(tf,1 );
                    tabStrip.setBackgroundResource(R.drawable.background_tiled_diagonal_light);

                }


            /*if (walletId == 2 || walletId == 1){
                getActionBar().setDisplayHomeAsUpEnabled(false);
                DrawerLayout draw = (DrawerLayout) findViewById(R.id.drawer_layout);
                draw.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }else
            {
                actionBar.setDisplayShowTitleEnabled(true);
            }*/
            }
            else
            {
                actionBar.hide();
            }

        }
        catch (Exception e)
        {
            throw e;
        }



    }



    // TODO: Mover esto a una clase AndroidActionBar a la cual se le pueda setear las propiedades tomadas directamente del AppRuntime o Wallet Runtime.

    /**
     * This method is used to change the properties of the action bar.
     *
     * -- Luis.
     */


    public static void setActionBarProperties(Activity activity, Window window,  PagerSlidingTabStrip pTabs, ActionBar pActionBar, Resources context,  TextView abTitle, String pTitle) {

        try{
            actionBar = pActionBar;
            tabs = pTabs;
            // Change the title of the action bar and the typeface
            SpannableString s = new SpannableString("");
            String color = "#F0E173";
            mTitle = pTitle;

            Drawable bg = context.getDrawable(R.drawable.transparent);
            bg.setVisible(false,false);

            Drawable wallpaper = context.getDrawable(R.drawable.transparent);

            s = new SpannableString(mTitle);

            switch (getWalletId() )
            {
                case 1:

                    color = "#FFC2F1";
                    actionBar.setIcon(context.getDrawable(R.drawable.icono_piggy_pink));
                    bg = context.getDrawable(R.drawable.wallet_wallpaper_pink);
                    bg.setVisible(true,false);
                    wallpaper = context.getDrawable(R.drawable.wallet_wallpaper_pink);
                    abTitle.setTextColor(Color.BLACK);
                    walletStyle = "Kids";
                    break;

                case 2:
                    color = "#84DCF5";
                    actionBar.setIcon(context.getDrawable(R.drawable.icono_piggy_yellow));
                    bg = context.getDrawable(R.drawable.banner_kid_yellow_blue);
                    bg.setVisible(true,false);
                    wallpaper = context.getDrawable(R.drawable.wallet_wallpaper_yellow);

                    abTitle.setTextColor(Color.BLACK);
                    walletStyle = "Kids";
                    break;

                case 3:
                    color = "#F0E173";
                    actionBar.setIcon(context.getDrawable(R.drawable.wallet_1));
                    wallpaper = context.getDrawable(R.drawable.background_tabs_diagonal_rotated);
                    abTitle.setTextColor(Color.BLACK);
                    walletStyle = "Young";
                    break;

                case 4:
                    color = "#d07b62";
                    actionBar.setIcon(context.getDrawable(R.drawable.fermat));
                    wallpaper = context.getDrawable(R.drawable.background_tabs_diagonal_rotated);
                    if(abTitle !=null) {
                        abTitle.setTextColor(Color.BLACK);
                    }
                    walletStyle = "Young";
                    break;

                case 5:

                    color = "#F0C64A";
                    actionBar.setIcon(context.getDrawable(R.drawable.wallet_3));
                    wallpaper = context.getDrawable(R.drawable.background_tiled_diagonal_light);
                    abTitle.setTextColor(Color.BLACK);
                    walletStyle = "Club";
                    break;

                case 6:

                    color = "#9B80FF";
                    actionBar.setIcon(context.getDrawable(R.drawable.wallet_3));
                    wallpaper = context.getDrawable(R.drawable.background_tiled_diagonal_light);
                    abTitle.setTextColor(Color.BLACK);
                    walletStyle = "Club";
                    break;

                case 7:
                    color = "#E8E8E8";
                    actionBar.setIcon(context.getDrawable(R.drawable.icono_retailer_1));
                    wallpaper = context.getDrawable(R.drawable.background_tiled_diagonal_light);
                    abTitle.setTextColor(Color.BLUE);
                    walletStyle = "Club";
                    break;

                case 8:
                    color = "#AB0A80";
                    actionBar.setIcon(context.getDrawable(R.drawable.icono_banco_1));
                    wallpaper = context.getDrawable(R.drawable.background_tiled_diagonal_light);
                    abTitle.setTextColor(Color.YELLOW);
                    walletStyle = "Club";
                    break;

                case 9:
                    color = "#FF0004";
                    actionBar.setIcon(context.getDrawable(R.drawable.icono_banco_2));
                    wallpaper = context.getDrawable(R.drawable.background_tiled_diagonal_light);
                    abTitle.setTextColor(Color.WHITE);
                    walletStyle = "Club";
                    break;

                case 10:
                    color = "#3864F5";
                    actionBar.setIcon(context.getDrawable(R.drawable.icono_club_1));
                    wallpaper = context.getDrawable(R.drawable.background_tiled_diagonal_light);
                    abTitle.setTextColor(Color.YELLOW);
                    bg = context.getDrawable(R.drawable.banner_club_1);
                    bg.setVisible(true,false);
                    walletStyle = "Club";
                    break;

                case 11:

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
        catch (Exception e)
        {
            throw e;
        }


    }


    // TODO: Mover este metodo a un lugar mas apropiado: una clase llamada AndroidActivity. No debería estar en esta clase que representa la Sesión de toda la APP
    // TODO: Junto con esto mover el call back que sale justo abajo de este metodo.

    /**
     * This method is used to change the background color of the current activity.
     *
     * -- Luis.
     */

    public static void changeColor(int newColor, Resources context) {

        try
        {

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
        catch (Exception e)
        {
            // TODO: Si ocurre un error intentando setear el color se debe reportar en el error manager pero no propagar la excepcion porque no es critico. Las actividades debieran tener un colorpor defecto para que si falla la asignacion quede el color por defecto.
            throw e;
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