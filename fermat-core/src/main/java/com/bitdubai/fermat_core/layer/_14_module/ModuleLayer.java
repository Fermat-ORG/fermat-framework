package com.bitdubai.fermat_core.layer._14_module;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer._14_module.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer._14_module.ModuleSubsystem;
import com.bitdubai.fermat_plugin.layer._14_module.wallet_manager.WalletManagerSubsystem;
import com.bitdubai.fermat_plugin.layer._14_module.wallet_runtime.developer.WalletRuntimeSubsystem;
/*
import com.bitdubai.wallet_platform_core.layer._11_module.wallet_manager.WalletManagerSubsystem;
import com.bitdubai.wallet_platform_core.layer._11_module.wallet_publisher.WalletPublisherSubsystem;
import com.bitdubai.wallet_platform_draft.layer._11_module.wallet_runtime.WalletRuntimeSubsystem;
import com.bitdubai.wallet_platform_core.layer._11_module.wallet_store.WalletStoreSubsystem;
*/
/**
 * Created by ciencias on 03.01.15.
 */
public class ModuleLayer implements PlatformLayer {


    Plugin mWalletRuntime;
    Plugin mWalletManager;
 /*
    Plugin mWalletPublisher;
    Plugin mWalletStore;
*/
    public Plugin getWalletRuntime() {
        return mWalletRuntime;
    }
/*

 */
    public Plugin getWalletManager() {
        return mWalletManager;
    }
/*    
    public Plugin getmWalletPublisher() {
        return mWalletPublisher;
    }
    
    public Plugin getmWalletStore() {
        return mWalletStore;      
    }
*/
    @Override
    public void start()  throws CantStartLayerException {

        /**
         * Let's try to start the wallet runtime subsystem.
         */

        ModuleSubsystem walletRuntimeSubsystem = new WalletRuntimeSubsystem();

        try {
            walletRuntimeSubsystem.start();
            mWalletRuntime = walletRuntimeSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

        /**
         * Let's try to start the wallet manager subsystem.
         */

        ModuleSubsystem walletManagerSubsystem = new WalletManagerSubsystem();

        try {
            walletManagerSubsystem.start();
            mWalletManager = walletManagerSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

        /**
         * Let's try to start the wallet publisher subsystem.
         */
        /*
        ModuleSubsystem walletPublisherSubsystem = new WalletPublisherSubsystem();
        
        try {
            walletPublisherSubsystem.start();
            mWalletPublisher = ((ModuleSubsystem) walletPublisherSubsystem).getPlugin();
                        
        }catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
            
        }
        */
        /**
         * Let's try to start the wallet store subsystem.
         */
        /*
        ModuleSubsystem walletStoreSubsystem = new WalletStoreSubsystem();
        
        try {
            walletStoreSubsystem.start();
            mWalletStore  = ((ModuleSubsystem) walletStoreSubsystem).getPlugin();
            
        }catch (CantStartSubsystemException e){
            System.err.println("CantStartSubsystemException: " + e.getMessage());
            
        }
        */

    }


}
