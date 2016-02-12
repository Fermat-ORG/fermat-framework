package com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.interfaces.SubAppSettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.HashMap;

/**
 * AssetIssuer SubApp Session
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class AssetFactorySession extends AbstractFermatSession<InstalledSubApp,AssetFactoryModuleManager,SubAppResourcesProviderManager> {

    private final InstalledSubApp installedSubApp;
    /**
     * Issuer Manager
     */
    private AssetFactoryModuleManager manager;

    /**
     * Error manager
     */
    private ErrorManager errorManager;

    /**
     *  Wallet Settings
     */
    private SubAppSettings subAppSettings;

    /**
     * Constructor
     *
     * @param
     * @param errorManager Error Manager
     * @param manager      AssetIssuerWallet Manager
     */
    public AssetFactorySession(InstalledSubApp installedSubApp, ErrorManager errorManager, AssetFactoryModuleManager manager) {
        super(installedSubApp.getAppPublicKey(), installedSubApp, errorManager, manager, null);
        this.installedSubApp = installedSubApp;
        this.errorManager = errorManager;
        this.manager = manager;
    }

    public AssetFactorySession() {
        installedSubApp = null;
    }

    @Override
    public ErrorManager getErrorManager() {
        return errorManager;
    }

    /**
     * Get Asset Issuer Wallet Manager instance
     *
     * @return AssetIssuerWalletManager object
     */
    public AssetFactoryModuleManager getManager() {
        return manager;
    }
}
