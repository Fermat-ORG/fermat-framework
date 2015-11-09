package com.bitdubai.fermat_core.layer.wpd.middleware;

import com.bitdubai.fermat_api.Plugin;

import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;

import com.bitdubai.fermat_core.layer.wpd.middleware.wallet_factory.WalletFactorySubsystem;
import com.bitdubai.fermat_core.layer.wpd.middleware.wallet_publisher.WalletPublisherSubsystem;
import com.bitdubai.fermat_core.layer.wpd.middleware.wallet_settings.WalletSettingsSubsystem;
import com.bitdubai.fermat_core.layer.wpd.middleware.wallet_store.WalletStoreSubsystem;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.CantStartSubsystemException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.WPDMiddlewareSubsystem;

/**
 * Created by Nerio on 02/10/15.
 */
public class WPDMiddlewareLayer implements PlatformLayer {

    private Plugin mWalletFactoryPlugin;

    private Plugin mWalletPublisherPlugin;

    private Plugin mWalletSettingsPlugin;

    private Plugin mWalletStorePlugin;

    /**
     * Each layer is started and by that time has the chance to initialize its services.
     */
    @Override
    public void start() throws CantStartLayerException {

        mWalletFactoryPlugin = getPlugin(new WalletFactorySubsystem());

        mWalletPublisherPlugin = getPlugin(new WalletPublisherSubsystem());

        mWalletSettingsPlugin = getPlugin(new WalletSettingsSubsystem());

        mWalletStorePlugin = getPlugin(new WalletStoreSubsystem());
    }

    private Plugin getPlugin(WPDMiddlewareSubsystem wpdMiddlewareSubsystem) throws CantStartLayerException {
        try {
            wpdMiddlewareSubsystem.start();
            return wpdMiddlewareSubsystem.getPlugin();
        } catch (CantStartSubsystemException e) {
            throw new CantStartLayerException();
        }
    }

    public Plugin getWalletFactoryPlugin() {
        return mWalletFactoryPlugin;
    }

    public Plugin getWalletPublisherPlugin() {
        return mWalletPublisherPlugin;
    }

    public Plugin getWalletSettingsPlugin() {
        return mWalletSettingsPlugin;
    }

    public Plugin getWalletStorePlugin() {
        return mWalletStorePlugin;
    }
}
