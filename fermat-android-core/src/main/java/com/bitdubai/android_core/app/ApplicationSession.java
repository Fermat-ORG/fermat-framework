package com.bitdubai.android_core.app;


import android.graphics.Typeface;
import android.os.Handler;

import com.bitdubai.android_core.app.common.version_1.Sessions.SubAppSessionManager;
import com.bitdubai.android_core.app.common.version_1.Sessions.WalletSessionManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
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

    public static Typeface mDefaultTypeface;
    public static Object[] mParams;

    /**
     * Fermat platform
     */
    private static Platform fermatPlatform;
    /**
     * Sub App session Manager
     */
    private static SubAppSessionManager subAppSessionManager;
    /**
     * Wallet session manager
     */
    private static WalletSessionManager walletSessionManager;



    public ApplicationSession() {
        super();
        fermatPlatform = new Platform();
        subAppSessionManager=new SubAppSessionManager();
        walletSessionManager = new WalletSessionManager();
    }

    public static Typeface getDefaultTypeface() {
        return mDefaultTypeface;
    }
    public static Platform getFermatPlatform() {
        return fermatPlatform;
    }
    public static AppRuntimeManager getAppRuntimeMiddleware(){
        return (AppRuntimeManager) fermatPlatform.getCorePlatformContext().getPlugin(Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);
    }
    public static WalletRuntimeManager getWalletRuntimeManager(){
        return (WalletRuntimeManager) fermatPlatform.getCorePlatformContext().getPlugin(Plugins.BITDUBAI_WALLET_RUNTIME_MODULE);
    }
    public static ErrorManager getErrorManager(){
        return (ErrorManager) fermatPlatform.getCorePlatformContext().getAddon(Addons.ERROR_MANAGER);
    }
    public static SubAppSessionManager getSubAppSessionManager(){
        return subAppSessionManager;
    }
    public static WalletSessionManager getWalletSessionManager(){
        return walletSessionManager;
    }

}