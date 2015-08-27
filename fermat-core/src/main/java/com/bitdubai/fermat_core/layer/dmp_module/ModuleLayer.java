package com.bitdubai.fermat_core.layer.dmp_module;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.dmp_module.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.dmp_module.ModuleSubsystem;
import com.bitdubai.fermat_core.layer.dmp_module.intra_user.IntraUserSubsystem;
import com.bitdubai.fermat_core.layer.dmp_module.wallet_factory.WalletFactorySubsystem;
import com.bitdubai.fermat_core.layer.dmp_module.wallet_manager.WalletManagerSubsystem;
import com.bitdubai.fermat_core.layer.dmp_module.wallet_publisher.WalletPublisherSubsystem;
import com.bitdubai.fermat_core.layer.dmp_module.wallet_runtime.WalletRuntimeSubsystem;
import com.bitdubai.fermat_core.layer.dmp_module.wallet_store.WalletStoreSubsystem;
/*
import com.bitdubai.wallet_platform_core.layer._11_module.wallet_manager.WalletManagerSubsystem;
import com.bitdubai.wallet_platform_core.layer._11_module.wallet_publisher.WalletPublisherSubsystem;
import com.bitdubai.wallet_platform_draft.layer._11_module.wallet_runtime.WalletRuntimeSubsystem;
import com.bitdubai.wallet_platform_core.layer._11_module.wallet_store.WalletStoreSubsystem;
*/

/**
 * Created by ciencias on 03.01.15.
 * Updated by Francisco on 08.27.15.
 */
public class ModuleLayer implements PlatformLayer {


    Plugin mWalletRuntime;
    Plugin mWalletManager;
    Plugin mWalletFactory;
    Plugin mIntraUser;

    Plugin mWalletPublisher;
    Plugin mWalletStore;

    public Plugin getWalletRuntime() {
        return mWalletRuntime;
    }

    public Plugin getWalletFactory() {
        return mWalletFactory;
    }

    public Plugin getWalletManager() {
        return mWalletManager;
    }

    public Plugin getIntraUser() {
        return mIntraUser;
    }

    public Plugin getWalletPublisher() {
        return mWalletPublisher;
    }

    public Plugin getWalletStore() {
        return mWalletStore;
    }

    @Override
    public void start() throws CantStartLayerException {

        /**
         * Let's try to start the wallet factory subsystem.
         */

        ModuleSubsystem walletFactorySubsystem = new WalletFactorySubsystem();

        try {
            walletFactorySubsystem.start();
            mWalletFactory = walletFactorySubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }


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
         * Let's try to start the intra user subsystem.
         */

        ModuleSubsystem intraUserSubsystem = new IntraUserSubsystem();

        try {
            intraUserSubsystem.start();
            mIntraUser = intraUserSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

        /**
         * Let's try to start the wallet publisher subsystem.
         */

        ModuleSubsystem walletPublisherSubsystem = new WalletPublisherSubsystem();

        try {
            walletPublisherSubsystem.start();
            mWalletPublisher = ((ModuleSubsystem) walletPublisherSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

        }


        /**
         * Let's try to start the wallet store subsystem.
         */

        ModuleSubsystem walletStoreSubsystem = new WalletStoreSubsystem();

        try {
            walletStoreSubsystem.start();
            mWalletStore = ((ModuleSubsystem) walletStoreSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

        }


    }


}
