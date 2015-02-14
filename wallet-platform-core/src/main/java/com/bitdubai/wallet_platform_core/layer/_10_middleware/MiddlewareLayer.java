package com.bitdubai.wallet_platform_core.layer._10_middleware;

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.layer.CantStartLayerException;
import com.bitdubai.wallet_platform_api.layer.PlatformLayer;
import com.bitdubai.wallet_platform_api.layer._10_middleware.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._10_middleware.MiddlewareSubsystem;
// import com.bitdubai.wallet_platform_core.layer._10_middleware.shell.ShellSubsystem;
import com.bitdubai.wallet_platform_core.layer._10_middleware.app_runtime.AppRuntimeSubsystem;
import com.bitdubai.wallet_platform_core.layer._10_middleware.wallet.WalletSubsystem;

/**
 * Created by ciencias on 30.12.14.
 */
public class MiddlewareLayer implements PlatformLayer {

    private Plugin mShellPlugin;
    private Plugin mWalletPlugin;
    private Plugin mAppRuntimePlugin;

   /*
    public Plugin getShellPlugin() {
        return mShellPlugin;
    }
    */
    
    public Plugin getmAppRuntimePlugin() {
        return mAppRuntimePlugin;
    }

    public Plugin getWalletPlugin() {
        return mWalletPlugin;
    }

    @Override
    public void start() throws CantStartLayerException {

        /**
         * Let's try to start the shell subsystem.
         */
        /*
        MiddlewareSubsystem shellSubsystem = new ShellSubsystem();

        try {
            shellSubsystem.start();
            mShellPlugin = ((MiddlewareSubsystem) shellSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }
        */


        /**
         * Let's try to start the App Runtime subsystem.
         */

        MiddlewareSubsystem appRuntimeSubsystem = new AppRuntimeSubsystem();

        try {
            appRuntimeSubsystem.start();
            mAppRuntimePlugin = appRuntimeSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

        
        
        /**
         * Let's try to start the wallet subsystem.
         */
        MiddlewareSubsystem walletSubsystem = new WalletSubsystem();

        try {
            walletSubsystem.start();
            mWalletPlugin = walletSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

    }
}
