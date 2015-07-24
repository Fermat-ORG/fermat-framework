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
 * Reformated by Matias Furszyfer
 */

/**
 * This class, is created by the Android OS before any Activity. That means its constructor is run before any other code
 * written by ourselves.
 *
 * -- Luis.
 */


public class ApplicationSession extends android.support.multidex.MultiDexApplication {


    public static Object[] mParams; //TODO : Caso creado a Natalia.


    /**
     *  Fermat platform
     */

    private Platform fermatPlatform;

    /**
     * Sub App session Manager
     */

    private SubAppSessionManager subAppSessionManager;

    /**
     * Wallet session manager
     */

    private WalletSessionManager walletSessionManager;


    /**
     *  Application session constructor
     */

    public ApplicationSession() {
        super();
        fermatPlatform = new Platform();
        subAppSessionManager=new SubAppSessionManager();
        walletSessionManager = new WalletSessionManager();
    }


    /**
     *  Method to get the fermat platform
     * @return Platform
     */
    public Platform getFermatPlatform() {
        return fermatPlatform;
    }

    /**
     * Method to get subAppSessionManager which can manipulate the active session of subApps
     * @return SubAppSessionManager
     */
    public SubAppSessionManager getSubAppSessionManager(){
        return subAppSessionManager;
    }

    /**
     * Method to get subWalletSessionManager which can manipulate the active session of wallets
     * @return WalletSessionManager
     */
    public WalletSessionManager getWalletSessionManager(){
        return walletSessionManager;
    }

}