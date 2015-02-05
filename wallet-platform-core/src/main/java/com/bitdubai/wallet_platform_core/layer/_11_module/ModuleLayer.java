package com.bitdubai.wallet_platform_core.layer._11_module;

import com.bitdubai.wallet_platform_api.layer.CantStartLayerException;
import com.bitdubai.wallet_platform_api.layer.PlatformLayer;
import com.bitdubai.wallet_platform_api.layer._11_module.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer._11_module.ModuleSubsystem;
import com.bitdubai.wallet_platform_core.layer._11_module.wallet_manager.WalletManagerSubsystem;
import com.bitdubai.wallet_platform_core.layer._11_module.wallet_runtime.WalletRuntimeSubsystem;

/**
 * Created by ciencias on 03.01.15.
 */
public class ModuleLayer implements PlatformLayer {


    Service mWalletRuntime;
    Service mWalletManager;

    public Service getWalletRuntime() {
        return mWalletRuntime;
    }

    public Service getWalletManager() {
        return mWalletManager;
    }

    @Override
    public void start()  throws CantStartLayerException {

        /**
         * Let's try to start the wallet runtime subsystem.
         */
        ModuleSubsystem walletRuntimeSubsystem = new WalletRuntimeSubsystem();

        try {
            walletRuntimeSubsystem.start();
            mWalletRuntime = ((ModuleSubsystem) walletRuntimeSubsystem).getModule();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

        /**
         * Let's try to start the wallet manager subsystem.
         */
        ModuleSubsystem walletManagerSubsystem = new WalletManagerSubsystem();

        try {
            walletRuntimeSubsystem.start();
            mWalletManager = ((ModuleSubsystem) walletManagerSubsystem).getModule();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

    }


}
