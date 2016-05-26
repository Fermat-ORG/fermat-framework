package org.fermat.fermat_dap_android_sub_app_asset_factory.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import org.fermat.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;

/**
 * AssetIssuer SubApp Session
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class AssetFactorySession extends AbstractFermatSession<InstalledSubApp, AssetFactoryModuleManager, SubAppResourcesProviderManager> {

//    private final InstalledSubApp installedSubApp;
    /**
     * Issuer Manager
     */
//    private AssetFactoryModuleManager manager;

    /**
     * Error manager
     */
//    private ErrorManager errorManager;

    /**
     * Wallet Settings
     */
//    private SubAppSettings subAppSettings;

    /**
     * Constructor
     *
     * @param
     * @param errorManager Error Manager
     * @param manager      AssetIssuerWallet Manager
     */
    public AssetFactorySession(String publicKey, InstalledSubApp installedSubApp, ErrorManager errorManager, AssetFactoryModuleManager manager, SubAppResourcesProviderManager subAppResourcesProviderManager) {
        super(publicKey, installedSubApp, errorManager, manager, subAppResourcesProviderManager);
//        this.installedSubApp = installedSubApp;
//        this.errorManager = errorManager;
//        this.manager = manager;
    }

    public AssetFactorySession() {
//        installedSubApp = null;
    }

//    @Override
//    public ErrorManager getErrorManager() {
//        return errorManager;
//    }

    /**
     * Get Asset Issuer Wallet Manager instance
     *
     * @return AssetIssuerWalletManager object
     */
//    public AssetFactoryModuleManager getManager() {
//        return manager;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetFactorySession that = (AssetFactorySession) o;

        return getFermatApp() == that.getFermatApp();
    }

    @Override
    public int hashCode() {
        return getFermatApp().hashCode();
    }
}
