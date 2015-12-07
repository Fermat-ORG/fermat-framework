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


    private AssetRedeemPointWalletSubAppModule manager;

    private ErrorManager errorManager;
    private InstalledWallet wallet;
    private WalletSettings settings;
    private WalletResourcesProviderManager resourceManager;

    private Map<String, Object> data;


    public RedeemPointSession(WalletResourcesProviderManager resourceManager, InstalledWallet installedWallet, ErrorManager errorManager, AssetRedeemPointWalletSubAppModule manager) {
        super(installedWallet.getWalletPublicKey(), installedWallet, errorManager, manager, null);
        this.resourceManager = resourceManager;
        this.wallet = installedWallet;
        this.errorManager = errorManager;
        this.manager = manager;
    }



    public InstalledWallet getWalletSessionType() {
        return wallet;
    }

    @Override
    public void setData(String key, Object object) {
        if (data == null)
            data = new HashMap<>();
        data.put(key, object);
    }

    @Override
    public String getIdentityConnection() {
        return null;
    }

    @Override
    public Object getData(String key) {
        return data;
    }

    @Override
    public ErrorManager getErrorManager() {
        return errorManager;
    }

    public WalletResourcesProviderManager getWalletResourcesProviderManager() {
        return resourceManager;
    }

    @Override
    public WalletSettings getWalletSettings() {
        return settings;
    }

    public AssetRedeemPointWalletSubAppModule getRedeemManager() {
        return manager;
    }

    public void setSettings(WalletSettings settings) {
        this.settings = settings;
    }
}
