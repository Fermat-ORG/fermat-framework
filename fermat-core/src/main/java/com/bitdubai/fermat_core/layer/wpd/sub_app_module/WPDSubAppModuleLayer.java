package com.bitdubai.fermat_core.layer.wpd.sub_app_module;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.CantStartLayerException;

import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.CantStartSubsystemException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.WPDSubAppModuleSubsystem;

import com.bitdubai.fermat_core.layer.wpd.sub_app_module.wallet_factory.WalletFactorySubsystem;
import com.bitdubai.fermat_core.layer.wpd.sub_app_module.wallet_publisher.WalletPublisherSubsystem;
import com.bitdubai.fermat_core.layer.wpd.sub_app_module.wallet_store.WalletStoreSubsystem;

/**
 * Created by Nerio on 02/10/15.
 */
public class WPDSubAppModuleLayer implements PlatformLayer {

    Plugin mWalletFactory;

    Plugin mWalletPublisher;

    Plugin mWalletStore;

    /**
     * Each layer is started and by that time has the chance to initialize its services.
     */
    @Override
    public void start() throws CantStartLayerException {

        mWalletFactory = getPlugin(new WalletFactorySubsystem());

        mWalletPublisher = getPlugin(new WalletPublisherSubsystem());

        mWalletStore = getPlugin(new WalletStoreSubsystem());
    }

    private Plugin getPlugin(WPDSubAppModuleSubsystem wpdSubAppModuleSubsystem) throws CantStartLayerException {
        try {
            wpdSubAppModuleSubsystem.start();
            return wpdSubAppModuleSubsystem.getPlugin();
        } catch (CantStartSubsystemException e) {
            throw new CantStartLayerException();
        }
    }

    public Plugin getWalletFactory() {
        return mWalletFactory;
    }

    public Plugin getWalletPublisher() {
        return mWalletPublisher;
    }

    public Plugin getWalletStore() {
        return mWalletStore;
    }

}
