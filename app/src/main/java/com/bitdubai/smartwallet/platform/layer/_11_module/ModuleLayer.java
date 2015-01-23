package com.bitdubai.smartwallet.platform.layer._11_module;

import com.bitdubai.smartwallet.platform.layer.CantStartLayerException;
import com.bitdubai.smartwallet.platform.layer.PlatformLayer;
import com.bitdubai.smartwallet.platform.layer._11_module.wallet_manager.WalletManagerSubsystem;
import com.bitdubai.smartwallet.platform.layer._11_module.wallet_runtime.WalletRuntimeSubsystem;

/**
 * Created by ciencias on 03.01.15.
 */
public class ModuleLayer implements PlatformLayer {


    Module mWalletRuntime;
    Module mWalletManager;

    public Module getWalletRuntime() {
        return mWalletRuntime;
    }

    public Module getWalletManager() {
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
