package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

import java.util.HashMap;
import java.util.Map;

/**
 * AssetIssuer SubApp Session
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class AssetIssuerSession extends AbstractFermatSession<InstalledWallet,AssetIssuerWalletSupAppModuleManager,WalletResourcesProviderManager> implements WalletSession {

    private final InstalledWallet installedWallet;
    /**
     * Issuer Manager
     */
    private AssetIssuerWalletSupAppModuleManager manager;

    /**
     * Active objects in wallet session
     */
    private Map<String, Object> data;

    /**
     * Error manager
     */
    private ErrorManager errorManager;

    /**
     * Constructor
     *
     * @param
     * @param errorManager Error Manager
     * @param manager      AssetIssuerWallet Manager
     */
    public AssetIssuerSession(InstalledWallet installedWallet, ErrorManager errorManager, AssetIssuerWalletSupAppModuleManager manager) {
        super(installedWallet.getWalletPublicKey(), installedWallet, errorManager, manager, null);
        this.installedWallet = installedWallet;
        data = new HashMap<String, Object>();
        this.errorManager = errorManager;
        this.manager = manager;
    }



    public InstalledWallet getWalletSessionType() {
        return null;
    }

    @Override
    public void setData(String key, Object object) {
        data.put(key, object);
    }

    @Override
    public String getIdentityConnection() {
        return null;
    }

    @Override
    public Object getData(String key) {
        return data.get(key);
    }

    @Override
    public ErrorManager getErrorManager() {
        return errorManager;
    }

    public WalletResourcesProviderManager getWalletResourcesProviderManager() {
        return null;
    }

    @Override
    public WalletSettings getWalletSettings() {
        return null;
    }


    /**
     * Get Asset Issuer Wallet Manager instance
     *
     * @return AssetIssuerWalletManager object
     */
    public AssetIssuerWalletSupAppModuleManager getManager() {
        return manager;
    }
}
