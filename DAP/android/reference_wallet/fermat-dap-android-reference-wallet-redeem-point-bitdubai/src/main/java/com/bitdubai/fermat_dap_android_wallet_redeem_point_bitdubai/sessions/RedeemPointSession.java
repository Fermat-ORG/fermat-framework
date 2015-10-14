package com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentityManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
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
public class RedeemPointSession implements WalletSession {

    private ErrorManager errorManager;
    private RedeemPointIdentityManager redeemManager;
    private InstalledWallet wallet;
    private WalletSettings settings;
    private WalletResourcesProviderManager resourceManager;

    private Map<String, Object> data;


    public RedeemPointSession(WalletResourcesProviderManager resourceManager, InstalledWallet installedWallet, ErrorManager errorManager, RedeemPointIdentityManager manager) {
        this.resourceManager = resourceManager;
        this.wallet = installedWallet;
        this.errorManager = errorManager;
        this.redeemManager = manager;
    }


    @Override
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
    public Object getData(String key) {
        return data;
    }

    @Override
    public ErrorManager getErrorManager() {
        return errorManager;
    }

    @Override
    public WalletResourcesProviderManager getWalletResourcesProviderManager() {
        return resourceManager;
    }

    @Override
    public WalletSettings getWalletSettings() {
        return settings;
    }

    public RedeemPointIdentityManager getRedeemManager() {
        return redeemManager;
    }

    public void setSettings(WalletSettings settings) {
        this.settings = settings;
    }
}
