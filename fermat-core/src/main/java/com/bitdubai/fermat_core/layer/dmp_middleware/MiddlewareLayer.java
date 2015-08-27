package com.bitdubai.fermat_core.layer.dmp_middleware;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.dmp_middleware.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.dmp_middleware.MiddlewareSubsystem;
// import com.bitdubai.wallet_platform_core.layer._10_middleware.shell.ShellSubsystem;
import com.bitdubai.fermat_core.layer.dmp_middleware.app_runtime.AppRuntimeSubsystem;
import com.bitdubai.fermat_core.layer.dmp_middleware.bank_notes.BankNotesSubsystem;
import com.bitdubai.fermat_core.layer.dmp_middleware.wallet_contacts.WalletContactsSubsystem;
import com.bitdubai.fermat_core.layer.dmp_middleware.wallet_factory.WalletFactorySubsystem;
import com.bitdubai.fermat_core.layer.dmp_middleware.wallet_language.WalletLanguageSubsystem;
import com.bitdubai.fermat_core.layer.dmp_middleware.wallet_manager.WalletManagerSubsystem;
import com.bitdubai.fermat_core.layer.dmp_middleware.wallet_navigation_structure.WalletNavigationStructureSubsystem;
import com.bitdubai.fermat_core.layer.dmp_middleware.wallet_publisher.WalletPublisherSubsystem;
import com.bitdubai.fermat_core.layer.dmp_middleware.wallet_skin.WalletSkinSubsystem;
import com.bitdubai.fermat_core.layer.dmp_middleware.wallet_store.WalletStoreSubsystem;
import com.bitdubai.fermat_core.layer.dmp_middleware.wallet_settings.WalletSettingsSubsystem;
/**
 * Created by ciencias on 30.12.14.
 */
public class MiddlewareLayer implements PlatformLayer {

    private Plugin mAppRuntimePlugin;
    private Plugin mBankNotesPlugin;
    private Plugin mWalletContactsPlugin;
    private Plugin mWalletFactoryPlugin;
    private Plugin mWalletManagerPlugin;
    private Plugin mWalletPublisherPlugin;
    private Plugin mWalletSkinPlugin;
    private Plugin mWalletLanguagePlugin;
    private Plugin mWalletStorePlugin;
    private Plugin mWalletSettingPlugin;
    private Plugin mWalletNavigationStructurePlugin;

    public Plugin getAppRuntimePlugin() {
        return mAppRuntimePlugin;
    }

    public Plugin getBankNotesPlugin() {
        return mBankNotesPlugin;
    }

    public Plugin getWalletContactsPlugin() {
        return mWalletContactsPlugin;
    }

    public Plugin getmWalletFactoryPlugin() {
        return mWalletFactoryPlugin;
    }

    public Plugin getmWalletManagerPlugin() {
        return mWalletManagerPlugin;
    }

    public Plugin getmWalletPublisherPlugin() {
        return mWalletPublisherPlugin;
    }

    public Plugin getmWalletStorePlugin() {
        return mWalletStorePlugin;
    }

    public Plugin getmWalletSkinPlugin() {
        return mWalletStorePlugin;
    }

    public Plugin getmWalletLanguagePlugin() {
        return mWalletStorePlugin;
    }

    public Plugin getmWalletSettingPlugin() { return mWalletSettingPlugin;}

    public Plugin getWalletNavigationStructurePlugin() { return mWalletNavigationStructurePlugin; }




    @Override
    public void start() throws CantStartLayerException {

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
         * Let's try to start the bank notes subsystem.
         */
        MiddlewareSubsystem bankNotesSubsystem = new BankNotesSubsystem();

        try {
            bankNotesSubsystem.start();
            mBankNotesPlugin = bankNotesSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

        /**
         * Let's try to start the wallet contacts subsystem.
         */
        MiddlewareSubsystem walletContactsSubsystem = new WalletContactsSubsystem();

        try {
            walletContactsSubsystem.start();
            mWalletContactsPlugin = walletContactsSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

        /**
         * Let's try to start the wallet factory subsystem.
         */
        MiddlewareSubsystem walletFactorySubsystem = new WalletFactorySubsystem();

        try {
            walletFactorySubsystem.start();
            mWalletFactoryPlugin = walletFactorySubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }
        /**
         * Let's try to start the wallet manager subsystem.
         */
        MiddlewareSubsystem walletManagerSubsystem = new WalletManagerSubsystem();

        try {
            walletManagerSubsystem.start();
            mWalletManagerPlugin = walletManagerSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }
        /**
         * Let's try to start the wallet publisher subsystem.
         */
        MiddlewareSubsystem walletPublisherSubsystem = new WalletPublisherSubsystem();

        try {
            walletPublisherSubsystem.start();
            mWalletPublisherPlugin = walletPublisherSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }
        /**
         * Let's try to start the wallet store subsystem.
         */
        MiddlewareSubsystem walletStoreSubsystem = new WalletStoreSubsystem();

        try {
            walletStoreSubsystem.start();
            mWalletStorePlugin = walletStoreSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

        /**
         * Let's try to start the wallet skin subsystem.
         */
        MiddlewareSubsystem walletSkinSubsystem = new WalletSkinSubsystem();

        try {
            walletSkinSubsystem.start();
            mWalletSkinPlugin = walletSkinSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

        /**
         * Let's try to start the wallet language subsystem.
         */
        MiddlewareSubsystem walletLanguageSubsystem = new WalletLanguageSubsystem();

        try {
            walletLanguageSubsystem.start();
            mWalletLanguagePlugin = walletLanguageSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

/**
 * Let's try to start the wallet settings subsystem.
 */
        MiddlewareSubsystem walletSettingsSubsystem = new WalletSettingsSubsystem();

        try {
            walletSettingsSubsystem.start();
            mWalletSettingPlugin = walletSettingsSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }


        /**
         * Let's try to start the wallet settings subsystem.
         */
        MiddlewareSubsystem walletNavigationStructureSubsystem = new WalletNavigationStructureSubsystem();

        try {
            walletNavigationStructureSubsystem.start();
            mWalletNavigationStructurePlugin = walletNavigationStructureSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

    }
}
