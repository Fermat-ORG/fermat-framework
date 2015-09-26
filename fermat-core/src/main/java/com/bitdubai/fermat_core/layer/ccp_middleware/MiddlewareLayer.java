package com.bitdubai.fermat_core.layer.ccp_middleware;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.dmp_middleware.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.dmp_middleware.MiddlewareSubsystem;
import com.bitdubai.fermat_core.layer.ccp_middleware.app_runtime.AppRuntimeSubsystem;
import com.bitdubai.fermat_core.layer.ccp_middleware.bank_notes.BankNotesSubsystem;
import com.bitdubai.fermat_core.layer.ccp_middleware.wallet_contacts.WalletContactsSubsystem;
import com.bitdubai.fermat_core.layer.ccp_middleware.wallet_factory.WalletFactorySubsystem;
import com.bitdubai.fermat_core.layer.ccp_middleware.wallet_language.WalletLanguageSubsystem;
import com.bitdubai.fermat_core.layer.ccp_middleware.wallet_manager.WalletManagerSubsystem;
import com.bitdubai.fermat_core.layer.ccp_middleware.wallet_navigation_structure.WalletNavigationStructureSubsystem;
import com.bitdubai.fermat_core.layer.ccp_middleware.wallet_publisher.WalletPublisherSubsystem;
import com.bitdubai.fermat_core.layer.ccp_middleware.wallet_skin.WalletSkinSubsystem;
import com.bitdubai.fermat_core.layer.ccp_middleware.wallet_store.WalletStoreSubsystem;
import com.bitdubai.fermat_core.layer.ccp_middleware.wallet_settings.WalletSettingsSubsystem;

/**
 * Created by ciencias on 30.12.14.
 * Modified by Leon Acosta (laion.cj91@gmail.com) on 28/08/2015.
 */
public class MiddlewareLayer implements PlatformLayer {

    private Plugin mAppRuntimePlugin;

    private Plugin mBankNotesPlugin;

    private Plugin mWalletContactsPlugin;

    private Plugin mWalletFactoryPlugin;

    private Plugin mWalletLanguagePlugin;

    private Plugin mWalletManagerPlugin;

    private Plugin mWalletNavigationStructurePlugin;

    private Plugin mWalletPublisherPlugin;

    private Plugin mWalletSettingsPlugin;

    private Plugin mWalletSkinPlugin;

    private Plugin mWalletStorePlugin;

    private Plugin mNotificationPlugin;


    @Override
    public void start() throws CantStartLayerException {

        mAppRuntimePlugin = getPlugin(new AppRuntimeSubsystem());

        mBankNotesPlugin = getPlugin(new BankNotesSubsystem());

        mWalletContactsPlugin = getPlugin(new WalletContactsSubsystem());

        mWalletFactoryPlugin = getPlugin(new WalletFactorySubsystem());

        mWalletLanguagePlugin = getPlugin(new WalletLanguageSubsystem());

        mWalletManagerPlugin = getPlugin(new WalletManagerSubsystem());

        mWalletNavigationStructurePlugin = getPlugin(new WalletNavigationStructureSubsystem());

        mWalletPublisherPlugin = getPlugin(new WalletPublisherSubsystem());

        mWalletSettingsPlugin = getPlugin(new WalletSettingsSubsystem());

        mWalletSkinPlugin = getPlugin(new WalletSkinSubsystem());

        mWalletStorePlugin = getPlugin(new WalletStoreSubsystem());


    }

    private Plugin getPlugin(MiddlewareSubsystem middlewareSubsystem) throws CantStartLayerException {
        try {
            middlewareSubsystem.start();
            return middlewareSubsystem.getPlugin();
        } catch (CantStartSubsystemException e) {
            throw new CantStartLayerException();
        }
    }

    public Plugin getAppRuntimePlugin() {
        return mAppRuntimePlugin;
    }

    public Plugin getBankNotesPlugin() {
        return mBankNotesPlugin;
    }

    public Plugin getWalletContactsPlugin() {
        return mWalletContactsPlugin;
    }

    public Plugin getWalletFactoryPlugin() {
        return mWalletFactoryPlugin;
    }

    public Plugin getWalletLanguagePlugin() {
        return mWalletLanguagePlugin;
    }

    public Plugin getWalletManagerPlugin() {
        return mWalletManagerPlugin;
    }

    public Plugin getWalletNavigationStructurePlugin() {
        return mWalletNavigationStructurePlugin;
    }

    public Plugin getWalletPublisherPlugin() {
        return mWalletPublisherPlugin;
    }

    public Plugin getWalletSettingsPlugin() {
        return mWalletSettingsPlugin;
    }

    public Plugin getWalletSkinPlugin() {
        return mWalletSkinPlugin;
    }

    public Plugin getWalletStorePlugin() {
        return mWalletStorePlugin;
    }

}
