package platform.layer._11_module;

import platform.layer.CantStartLayerException;
import platform.layer.PlatformLayer;
import platform.layer._11_module.wallet_manager.WalletManagerSubsystem;
import platform.layer._11_module.wallet_runtime.WalletRuntimeSubsystem;

/**
 * Created by ciencias on 03.01.15.
 */
public class ModuleLayer implements PlatformLayer {


    ModuleService mWalletRuntime;
    ModuleService mWalletManager;

    public ModuleService getWalletRuntime() {
        return mWalletRuntime;
    }

    public ModuleService getWalletManager() {
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
