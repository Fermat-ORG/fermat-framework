package com.bitdubai.reference_wallet.cash_money_wallet.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.interfaces.CashMoneyWalletModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.CashMoneyWalletPreferenceSettings;

/**
 * Created by Alejandro Bicelis on 12/9/2015.
 */
public class CashMoneyWalletSession extends AbstractFermatSession<InstalledWallet,CashMoneyWalletModuleManager, WalletResourcesProviderManager> implements WalletSession {

    public CashMoneyWalletSession() {}

    @Override
    public WalletSettings getWalletSettings() {
        return new CashMoneyWalletPreferenceSettings();
    }

    @Override
    public String getIdentityConnection() {
        return null;
    }
}
