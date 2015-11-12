package com.bitdubai.android_core.app;


import android.support.multidex.MultiDexApplication;

import com.bitdubai.android_core.app.common.version_1.Sessions.SubAppSessionManager;
import com.bitdubai.android_core.app.common.version_1.Sessions.WalletSessionManager;
import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_core.Platform;

import java.io.Serializable;
import java.util.HashMap;


/**
 * Reformated by Matias Furszyfer
 */

/**
 * This class, is created by the Android OS before any Activity. That means its constructor is run before any other code
 * written by ourselves.
 *
 * -- Luis.
 */


public class ApplicationSession extends MultiDexApplication implements Serializable {

    /**
     * Application states
     */
    public static final int STATE_NOT_CREATED=0;
    public static final int STATE_STARTED=1;


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
     *  Application state
     */
    private int applicationState=STATE_NOT_CREATED;

    /**
     *  SubApps fragment factories
     */
    private HashMap<String,FermatSubAppFragmentFactory> subAppsFragmentfFactories;


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

    /**
     *  Method to change the application state from services or activities
     *
     * @param applicationState  is an application state constant from ApplicationSession class
     */

    public void changeApplicationState(int applicationState){
        this.applicationState=applicationState;
    }

    /**
     * Method to get the application state from services or activities
     *
     * @return application state constant from ApplicationSession class
     */

    public int getApplicationState(){
        return applicationState;
    }

    /**
     *  Add supApp fragment factory
     */
    public void addSubAppFragmentFactory(String subAppType,FermatSubAppFragmentFactory fermatSubAppFragmentFactory){
        subAppsFragmentfFactories.put(subAppType,fermatSubAppFragmentFactory);
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
    }

}