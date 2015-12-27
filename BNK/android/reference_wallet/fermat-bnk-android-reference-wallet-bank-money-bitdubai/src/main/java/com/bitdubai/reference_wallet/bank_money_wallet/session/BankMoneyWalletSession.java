package com.bitdubai.reference_wallet.bank_money_wallet.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.bank_money_wallet.preference_settings.BankMoneyWalletPreferenceSettings;

/**
 * Created by memo on 04/12/15.
 */
public class BankMoneyWalletSession extends AbstractFermatSession<InstalledWallet,BankMoneyWalletModuleManager,WalletResourcesProviderManager> implements WalletSession {


    public BankMoneyWalletSession() {
    }

    @Override
    public String getIdentityConnection() {
        return null;
    }

    @Override
    public WalletSettings getWalletSettings() {
        return new BankMoneyWalletPreferenceSettings();
    }
}
