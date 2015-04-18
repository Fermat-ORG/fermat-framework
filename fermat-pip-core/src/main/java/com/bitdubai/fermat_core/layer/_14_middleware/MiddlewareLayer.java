package com.bitdubai.fermat_core.layer._14_middleware;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer._14_middleware.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer._14_middleware.MiddlewareSubsystem;
// import com.bitdubai.wallet_platform_core.layer._10_middleware.shell.ShellSubsystem;
import com.bitdubai.fermat_core.layer._14_middleware.app_runtime.AppRuntimeSubsystem;
import com.bitdubai.fermat_core.layer._14_middleware.bank_notes.BankNotesSubsystem;
import com.bitdubai.fermat_core.layer._14_middleware.discount_wallet.DiscountWalletSubsystem;
import com.bitdubai.fermat_core.layer._14_middleware.wallet_contacts.WalletContactsSubsystem;

/**
 * Created by ciencias on 30.12.14.
 */
public class MiddlewareLayer implements PlatformLayer {

    private Plugin mShellPlugin;
    private Plugin mWalletPlugin;
    private Plugin mAppRuntimePlugin;
    private Plugin mBankNotesPlugin;
    private Plugin mWalletContactsPlugin;


   /*
    public Plugin getShellPlugin() {
        return mShellPlugin;
    }
    */
    
    public Plugin getAppRuntimePlugin() {
        return mAppRuntimePlugin;
    }

    public Plugin getWalletPlugin() {
        return mWalletPlugin;
    }


    public Plugin getBankNotesPlugin() {
        return mBankNotesPlugin;
    }


    public Plugin getWalletContactsPlugin() {
        return mWalletContactsPlugin;
    }


    @Override
    public void start() throws CantStartLayerException {

        /**
         * Let's try to start the shell subsystem.
         */
        /*
        MiddlewareSubsystem shellSubsystem = new ShellSubsystem();

        try {
            shellSubsystem.start();
            mShellPlugin = ((MiddlewareSubsystem) shellSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }
        */


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
         * Let's try to start the wallet subsystem.
         */
        MiddlewareSubsystem walletSubsystem = new DiscountWalletSubsystem();

        try {
            walletSubsystem.start();
            mWalletPlugin = walletSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

        /**
         * Let's try to start the wallet subsystem.
         */
        MiddlewareSubsystem bankNotesSubsystem = new BankNotesSubsystem();

        try {
            bankNotesSubsystem.start();
            mBankNotesPlugin = bankNotesSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

        /**
         * Let's try to start the wallet subsystem.
         */
        MiddlewareSubsystem walletContactsSubsystem = new WalletContactsSubsystem();

        try {
            walletContactsSubsystem.start();
            mWalletContactsPlugin = walletContactsSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }


    }
}
