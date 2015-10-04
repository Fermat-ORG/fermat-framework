package com.bitdubai.fermat_core.layer.dmp_middleware;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.dmp_middleware.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.dmp_middleware.MiddlewareSubsystem;
import com.bitdubai.fermat_core.layer.dmp_middleware.app_runtime.AppRuntimeSubsystem;
import com.bitdubai.fermat_core.layer.dmp_middleware.bank_notes.BankNotesSubsystem;
import com.bitdubai.fermat_core.layer.dmp_middleware.wallet_language.WalletLanguageSubsystem;
import com.bitdubai.fermat_core.layer.dmp_middleware.wallet_navigation_structure.WalletNavigationStructureSubsystem;
import com.bitdubai.fermat_core.layer.dmp_middleware.wallet_skin.WalletSkinSubsystem;

/**
 * Created by ciencias on 30.12.14.
 * Modified by Leon Acosta (laion.cj91@gmail.com) on 28/08/2015.
 */
public class MiddlewareLayer implements PlatformLayer {

    private Plugin mAppRuntimePlugin;

    private Plugin mBankNotesPlugin;

    private Plugin mWalletLanguagePlugin;

    private Plugin mWalletNavigationStructurePlugin;

    private Plugin mWalletSkinPlugin;

    private Plugin mNotificationPlugin;


    @Override
    public void start() throws CantStartLayerException {

        mAppRuntimePlugin = getPlugin(new AppRuntimeSubsystem());

        mBankNotesPlugin = getPlugin(new BankNotesSubsystem());

        mWalletLanguagePlugin = getPlugin(new WalletLanguageSubsystem());

        mWalletNavigationStructurePlugin = getPlugin(new WalletNavigationStructureSubsystem());

        mWalletSkinPlugin = getPlugin(new WalletSkinSubsystem());

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

    public Plugin getWalletLanguagePlugin() {
        return mWalletLanguagePlugin;
    }

    public Plugin getWalletNavigationStructurePlugin() {
        return mWalletNavigationStructurePlugin;
    }

    public Plugin getWalletSkinPlugin() {
        return mWalletSkinPlugin;
    }


}
