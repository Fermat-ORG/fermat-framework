package com.bitdubai.fermat_core.layer.dmp_module;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.dmp_module.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.dmp_module.ModuleSubsystem;
import com.bitdubai.fermat_core.layer.dmp_module.intra_user.IntraUserSubsystem;
import com.bitdubai.fermat_core.layer.dmp_module.notification.NotificationSubSystem;
import com.bitdubai.fermat_core.layer.dmp_module.wallet_factory.WalletFactorySubsystem;
import com.bitdubai.fermat_core.layer.dmp_module.wallet_manager.WalletManagerSubsystem;
import com.bitdubai.fermat_core.layer.dmp_module.wallet_publisher.WalletPublisherSubsystem;
import com.bitdubai.fermat_core.layer.dmp_module.wallet_runtime.WalletRuntimeSubsystem;
import com.bitdubai.fermat_core.layer.dmp_module.wallet_store.WalletStoreSubsystem;

/**
 * Created by ciencias on 03.01.15.
 * Updated by Francisco on 08.27.15.
 * Modified by Leon Acosta (laion.cj91@gmail.com) on 28/08/2015.
 */
public class ModuleLayer implements PlatformLayer {

    Plugin mIntraUser;

    Plugin mWalletFactory;

    Plugin mWalletManager;

    Plugin mWalletPublisher;

    Plugin mWalletRuntime;

    Plugin mWalletStore;

    Plugin mNotification;

    @Override
    public void start() throws CantStartLayerException {

        mIntraUser = getPlugin(new IntraUserSubsystem());

        mWalletFactory = getPlugin(new WalletFactorySubsystem());

        mWalletManager = getPlugin(new WalletManagerSubsystem());

        mWalletPublisher = getPlugin(new WalletPublisherSubsystem());

        mWalletRuntime = getPlugin(new WalletRuntimeSubsystem());

        mWalletStore = getPlugin(new WalletStoreSubsystem());

        mNotification = getPlugin(new NotificationSubSystem());

    }

    private Plugin getPlugin(ModuleSubsystem moduleSubsystem) throws CantStartLayerException {
        try {
            moduleSubsystem.start();
            return moduleSubsystem.getPlugin();
        } catch (CantStartSubsystemException e) {
            throw new CantStartLayerException();
        }
    }

    public Plugin getIntraUser() {
        return mIntraUser;
    }

    public Plugin getWalletFactory() {
        return mWalletFactory;
    }

    public Plugin getWalletManager() {
        return mWalletManager;
    }

    public Plugin getWalletPublisher() {
        return mWalletPublisher;
    }

    public Plugin getWalletRuntime() {
        return mWalletRuntime;
    }

    public Plugin getWalletStore() {
        return mWalletStore;
    }

    public Plugin getNotification() {
        return mNotification;
    }
}
