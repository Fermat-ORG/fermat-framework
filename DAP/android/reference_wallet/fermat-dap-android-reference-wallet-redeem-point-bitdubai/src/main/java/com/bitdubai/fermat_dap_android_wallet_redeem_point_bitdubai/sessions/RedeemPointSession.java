package com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Redeem Point Wallet Session
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class RedeemPointSession extends AbstractFermatSession<InstalledWallet,AssetRedeemPointWalletSubAppModule,WalletResourcesProviderManager> implements WalletSession {


    private final InstalledWallet installedWallet;
    /**
     * Issuer Manager
     */
    private AssetRedeemPointWalletSubAppModule manager;

    /**
     * Active objects in wallet session
     */
    private Map<String, Object> data;

    /**
     * Error manager
     */
    private ErrorManager errorManager;


    public RedeemPointSession(WalletResourcesProviderManager resourceManager, InstalledWallet installedWallet, ErrorManager errorManager, AssetRedeemPointWalletSubAppModule manager) {
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
    public AssetRedeemPointWalletSubAppModule getManager() {
        return manager;
    }
}
