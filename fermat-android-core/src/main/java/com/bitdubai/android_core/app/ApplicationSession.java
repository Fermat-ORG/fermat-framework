package com.bitdubai.android_core.app;


import android.graphics.Typeface;
import android.os.Handler;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.AppRuntimeManager;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.WalletRuntimeManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_core.Platform;


/**
 * Reformated by Matias
 */

/**
 * This class, is created by the Android OS before any Activity. That means its constructor is run before any other code
 * written by ourselves.
 *
 * -- Luis.
 */


public class ApplicationSession extends android.support.multidex.MultiDexApplication {

    private final static Handler handler = new Handler();
    public static Typeface mDefaultTypeface;
    public static Object[] mParams;

    public static AppRuntimeManager appRuntimeMiddleware;
    public static WalletRuntimeManager walletRuntimeMiddleware;

    public static ErrorManager errorManager;


    public static Typeface getDefaultTypeface() {
        return mDefaultTypeface;
    }

    private static Platform fermatPlatform;


    public static Platform getFermatPlatform() {
        return fermatPlatform;
    }

    public ApplicationSession() {
        super();
        fermatPlatform = new Platform();
    }


}