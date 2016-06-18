package com.bitdubai.reference_wallet.bank_money_wallet.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

/**
 * Created by memo on 04/12/15.
 */
public class BankMoneyWalletSessionReferenceApp extends AbstractReferenceAppFermatSession<InstalledWallet,BankMoneyWalletModuleManager,WalletResourcesProviderManager> {


    public BankMoneyWalletSessionReferenceApp() {
    }
}
